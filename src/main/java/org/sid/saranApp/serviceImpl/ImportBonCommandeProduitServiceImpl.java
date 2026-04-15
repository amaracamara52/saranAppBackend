package org.sid.saranApp.serviceImpl;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.sid.saranApp.dto.CommandeFournisseurDto;
import org.sid.saranApp.dto.DetailCommandeFournisseurDto;
import org.sid.saranApp.dto.ImportBonCommandeProduitResultDto;
import org.sid.saranApp.dto.version.ProduitStockDto;
import org.sid.saranApp.dto.version.TypeUniteDeVenteDto;
import org.sid.saranApp.enume.TypeGestionUniteProduitEnum;
import org.sid.saranApp.enume.TypeUniteEnum;
import org.sid.saranApp.model.Article;
import org.sid.saranApp.model.Boutique;
import org.sid.saranApp.model.Categorie;
import org.sid.saranApp.model.Produit;
import org.sid.saranApp.model.TypeUniteDeVente;
import org.sid.saranApp.model.Utilisateur;
import org.sid.saranApp.repository.ArticleRepository;
import org.sid.saranApp.repository.CategorieRepository;
import org.sid.saranApp.repository.ProduitRepository;
import org.sid.saranApp.repository.TypeUniteDeVenteRepository;
import org.sid.saranApp.service.CommandeFournisseurService;
import org.sid.saranApp.service.ImportBonCommandeProduitService;
import org.sid.saranApp.service.StockUniteVenteService;
import org.sid.saranApp.service.version.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.text.Normalizer;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ImportBonCommandeProduitServiceImpl implements ImportBonCommandeProduitService {

	private static final Pattern QTE_COMMANDE = Pattern.compile("^\\s*(\\d+)\\s+(.+?)\\s*$");
	private static final DateTimeFormatter[] DATE_FORMATS = new DateTimeFormatter[] {
			DateTimeFormatter.ofPattern("d/M/yyyy"),
			DateTimeFormatter.ofPattern("dd/MM/yyyy"),
			DateTimeFormatter.ISO_LOCAL_DATE
	};

	@Autowired
	private CommandeFournisseurService commandeFournisseurService;
	@Autowired
	private ArticleRepository articleRepository;
	@Autowired
	private TypeUniteDeVenteRepository typeUniteDeVenteRepository;
	@Autowired
	private CategorieRepository categorieRepository;
	@Autowired
	private UtilisateurServiceImpl utilisateurServiceImpl;
	@Autowired
	private ProduitRepository produitRepository;
	@Autowired
	private StockUniteVenteService stockUniteVenteService;
	@Autowired
	private ProduitService produitService;

	@Override
	@Transactional
	public ImportBonCommandeProduitResultDto importExcel(MultipartFile file, String uuidFournisseur, String uuidCategorie) {
		ImportBonCommandeProduitResultDto result = new ImportBonCommandeProduitResultDto();
		if (file == null || file.isEmpty()) {
			result.getErreurs().add("Fichier vide.");
			return result;
		}
		if (uuidFournisseur == null || uuidFournisseur.isBlank()) {
			result.getErreurs().add("uuidFournisseur est obligatoire.");
			return result;
		}

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null || auth.getName() == null) {
			result.getErreurs().add("Utilisateur non authentifié.");
			return result;
		}

		Utilisateur utilisateur = utilisateurServiceImpl.getCurentUtilisateur();
		Boutique boutique = utilisateur.getBoutique();
		if (boutique == null) {
			result.getErreurs().add("Aucune boutique associée à l'utilisateur.");
			return result;
		}

		Categorie categorie;
		try {
			categorie = resolveCategorie(boutique, utilisateur, uuidCategorie, result);
		} catch (IllegalArgumentException e) {
			result.getErreurs().add(e.getMessage());
			return result;
		}

		List<ParsedRow> rows;
		try (InputStream in = file.getInputStream(); Workbook wb = WorkbookFactory.create(in)) {
			Sheet sheet = wb.getSheetAt(0);
			DataFormatter fmt = new DataFormatter();
			int headerRowIdx = findHeaderRowIndex(sheet, fmt);
			if (headerRowIdx < 0) {
				result.getErreurs().add("Ligne d'en-têtes introuvable (colonne DESIGNATION attendue).");
				return result;
			}
			Map<String, Integer> col = detectColumns(sheet.getRow(headerRowIdx), fmt, result);
			if (col.get("DESIGNATION") == null) {
				result.getErreurs().add("Colonne DESIGNATION introuvable (vérifier la ligne d'en-têtes).");
				return result;
			}
			rows = new ArrayList<>();
			int first = headerRowIdx + 1;
			for (int i = first; i <= sheet.getLastRowNum(); i++) {
				Row row = sheet.getRow(i);
				if (row == null) {
					continue;
				}
				ParsedRow pr = parseRow(i + 1, row, col, fmt);
				if (pr == null) {
					continue;
				}
				rows.add(pr);
			}
		} catch (Exception e) {
			result.getErreurs().add("Lecture Excel : " + e.getMessage());
			return result;
		}

		result.setLignesLues(rows.size());

		Map<String, List<ParsedRow>> byBon = new LinkedHashMap<>();
		for (ParsedRow r : rows) {
			if (!r.validForCommande()) {
				continue;
			}
			String key = r.dateCommande.toString() + "|" + r.dateLivraison.toString();
			byBon.computeIfAbsent(key, k -> new ArrayList<>()).add(r);
		}

		for (Map.Entry<String, List<ParsedRow>> e : byBon.entrySet()) {
			List<ParsedRow> group = e.getValue();
			try {
				String cmdUuid = importOneBon(group, uuidFournisseur, boutique, utilisateur, categorie);
				result.getCommandesCreeesUuids().add(cmdUuid);
				result.setBonsCrees(result.getBonsCrees() + 1);
				result.setLignesImportees(result.getLignesImportees() + group.size());
				afterReceptionApplyPrixEtStock(group, boutique);
			} catch (Exception ex) {
				StringBuilder lignesExcel = new StringBuilder();
				for (ParsedRow pr : group) {
					if (lignesExcel.length() > 0) {
						lignesExcel.append(", ");
					}
					lignesExcel.append(pr.excelRow);
				}
				result.getErreurs().add(
						"Bon " + e.getKey() + " (lignes Excel " + lignesExcel + ") : " + ex.getMessage());
			}
		}

		return result;
	}

	@Override
	@Transactional
	public ImportBonCommandeProduitResultDto importJson(List<CommandeFournisseurDto> bons, String uuidFournisseurParam,
			String uuidCategorie) {
		ImportBonCommandeProduitResultDto result = new ImportBonCommandeProduitResultDto();
		if (bons == null) {
			result.getErreurs().add("Liste nulle.");
			return result;
		}
		result.setLignesLues(bons.size());

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null || auth.getName() == null) {
			result.getErreurs().add("Utilisateur non authentifié.");
			return result;
		}
		Utilisateur utilisateur = utilisateurServiceImpl.getCurentUtilisateur();
		Boutique boutique = utilisateur.getBoutique();
		if (boutique == null) {
			result.getErreurs().add("Aucune boutique associée à l'utilisateur.");
			return result;
		}

		String catParam = uuidCategorie != null ? uuidCategorie.trim() : "";
		if (!catParam.isEmpty()) {
			try {
				categorieRepository.findById(catParam)
						.orElseThrow(() -> new IllegalArgumentException("Catégorie introuvable : " + catParam));
			} catch (IllegalArgumentException e) {
				result.getErreurs().add(e.getMessage());
				return result;
			}
		}

		for (CommandeFournisseurDto bon : bons) {
			if (!isBonImportableJson(bon)) {
				continue;
			}
			String uuidFour = firstNonBlank(bon.getUuidFournisseur(), uuidFournisseurParam);
			if (uuidFour == null || uuidFour.isBlank()) {
				result.getErreurs().add("Bon du " + bon.getDateCommandeFournisseur()
						+ " : uuidFournisseur obligatoire (sur le bon ou paramètre d'API).");
				continue;
			}
			try {
				importOneBonJson(bon, uuidFour, catParam.isEmpty() ? null : catParam, boutique, result);
			} catch (Exception ex) {
				result.getErreurs().add("Bon du " + bon.getDateCommandeFournisseur() + " : " + ex.getMessage());
			}
		}

		return result;
	}

	private static String firstNonBlank(String a, String b) {
		if (a != null && !a.isBlank()) {
			return a.trim();
		}
		if (b != null && !b.isBlank()) {
			return b.trim();
		}
		return null;
	}

	private static boolean isBonImportableJson(CommandeFournisseurDto bon) {
		if (bon.getDateCommandeFournisseur() == null) {
			return false;
		}
		List<DetailCommandeFournisseurDto> lines = bon.getDetailCommandeFournisseurDtos();
		if (lines == null || lines.isEmpty()) {
			return false;
		}
		for (DetailCommandeFournisseurDto d : lines) {
			if (d.getArticleDto() == null) {
				continue;
			}
			if (d.getQuantite() > 0 && d.getPrixAchat() > 0) {
				return true;
			}
		}
		return false;
	}

	private void importOneBonJson(CommandeFournisseurDto bon, String uuidFournisseur, String defaultUuidCategorie,
			Boutique boutique, ImportBonCommandeProduitResultDto result) {

		List<DetailCommandeFournisseurDto> detailsOut = new ArrayList<>();
		List<JsonStockApplyRow> stockApplies = new ArrayList<>();

		for (DetailCommandeFournisseurDto line : bon.getDetailCommandeFournisseurDtos()) {
			if (line.getArticleDto() == null || line.getQuantite() <= 0 || line.getPrixAchat() <= 0) {
				continue;
			}
			ProduitStockDto merged = mergeLineIntoProduitStock(line, bon, uuidFournisseur, defaultUuidCategorie);
			double pxVente = line.getArticleDto().getPrixVente();
			int qStock = line.getArticleDto().getQuantiteStock();

			ProduitStockDto ensured = produitService.ensureProduitForJsonImport(merged);
			String artUuid = ensured.getUuidArticle();
			if (artUuid == null || artUuid.isBlank()) {
				throw new IllegalStateException("UUID article manquant après import produit : " + merged.getLibelleArticle());
			}
			Article article = articleRepository.findById(artUuid)
					.orElseThrow(() -> new IllegalStateException("Article introuvable : " + artUuid));

			TypeUniteEnum cmdUnite = parseUniteEnum(line.getUnite());
			int facteur = resolveFacteurCommande(line.getArticleDto(), cmdUnite);
			ensureTypeUniteDeVenteExists(article, cmdUnite, facteur);
			TypeUniteDeVente tuv = resolveTypeUniteForCommande(article, cmdUnite, facteur);

			DetailCommandeFournisseurDto d = new DetailCommandeFournisseurDto();
			d.setUuidArticle(artUuid);
			d.setQuantite(line.getQuantite());
			d.setPrixAchat(line.getPrixAchat());
			d.setUnite(cmdUnite.name());
			d.setUuidTypeUniteDeVente(tuv.getUuid());
			detailsOut.add(d);

			stockApplies.add(new JsonStockApplyRow(artUuid, pxVente, qStock));
			result.setLignesImportees(result.getLignesImportees() + 1);
		}

		if (detailsOut.isEmpty()) {
			throw new IllegalArgumentException("Aucune ligne importable (articleDto + quantite > 0 + prix > 0).");
		}

		CommandeFournisseurDto cmd = new CommandeFournisseurDto();
		cmd.setUuidFournisseur(uuidFournisseur);
		cmd.setDateCommandeFournisseur(bon.getDateCommandeFournisseur());
		cmd.setPaye(bon.isPaye());
		String vm = bon.getValeurMarchandise();
		if (vm == null || vm.isBlank()) {
			vm = "Import JSON — " + bon.getDateCommandeFournisseur();
		}
		cmd.setValeurMarchandise(vm);
		cmd.setDetailCommandeFournisseurDtos(detailsOut);

		CommandeFournisseurDto saved = commandeFournisseurService.addCommandeFournisseur(cmd);

		CommandeFournisseurDto reception = new CommandeFournisseurDto();
		reception.setUuid(saved.getUuid());
		List<DetailCommandeFournisseurDto> recDetails = new ArrayList<>();
		if (saved.getDetailCommandeFournisseurDtos() != null) {
			for (DetailCommandeFournisseurDto line : saved.getDetailCommandeFournisseurDtos()) {
				DetailCommandeFournisseurDto rd = new DetailCommandeFournisseurDto();
				rd.setUuid(line.getUuid());
				rd.setQuantiteLivraison(line.getQuantite());
				recDetails.add(rd);
			}
		}
		reception.setDetailCommandeFournisseurDtos(recDetails);
		commandeFournisseurService.addStockFromCommandeAndLivraison(reception);

		afterJsonApplyPrixEtStock(boutique, stockApplies);
		result.getCommandesCreeesUuids().add(saved.getUuid());
		result.setBonsCrees(result.getBonsCrees() + 1);
	}

	private static final class JsonStockApplyRow {
		final String articleUuid;
		final double prixVente;
		final int quantiteStock;

		JsonStockApplyRow(String articleUuid, double prixVente, int quantiteStock) {
			this.articleUuid = articleUuid;
			this.prixVente = prixVente;
			this.quantiteStock = quantiteStock;
		}
	}

	private void afterJsonApplyPrixEtStock(Boutique boutique, List<JsonStockApplyRow> applies) {
		for (JsonStockApplyRow r : applies) {
			Optional<Produit> pOpt = produitRepository.findFirstByBoutique_UuidAndArticle_Uuid(boutique.getUuid(),
					r.articleUuid);
			if (!pOpt.isPresent()) {
				continue;
			}
			Produit produit = pOpt.get();
			if (r.prixVente > 0) {
				stockUniteVenteService.applyRetailPricesFromBaseUnit(produit, r.prixVente);
				produit.setPrixVente(r.prixVente);
			}
			if (r.quantiteStock > 0) {
				stockUniteVenteService.updateAllUnitsFromBaseQuantity(produit, r.quantiteStock);
			}
			produitRepository.save(produit);
		}
	}

	private void ensureTypeUniteDeVenteExists(Article article, TypeUniteEnum en, int factor) {
		if (en == null) {
			en = TypeUniteEnum.PIECE;
		}
		int f = en == TypeUniteEnum.PIECE ? 1 : Math.max(1, factor);
		List<TypeUniteDeVente> list = typeUniteDeVenteRepository.findByArticle(article);
		for (TypeUniteDeVente t : list) {
			if (t.getTypeUniteEnum() == en && t.getUnite() == f) {
				return;
			}
		}
		TypeUniteDeVente t = new TypeUniteDeVente();
		t.setArticle(article);
		t.setTypeUniteEnum(en);
		t.setUnite(f);
		t.setPrice(0d);
		t.setQtite(0);
		typeUniteDeVenteRepository.save(t);
	}

	private static int resolveFacteurCommande(ProduitStockDto ad, TypeUniteEnum cmdUnite) {
		if (cmdUnite == null || cmdUnite == TypeUniteEnum.PIECE) {
			return 1;
		}
		if (ad.getTypeUniteDeVenteDtos() != null) {
			for (TypeUniteDeVenteDto u : ad.getTypeUniteDeVenteDtos()) {
				if (u.getTypeUniteEnum() == cmdUnite && u.getUnite() > 0) {
					return u.getUnite();
				}
			}
		}
		int sf = ad.getSubUniteFactor();
		if (sf > 0) {
			return sf;
		}
		return 1;
	}

	private ProduitStockDto mergeLineIntoProduitStock(DetailCommandeFournisseurDto line, CommandeFournisseurDto bon,
			String uuidFour, String defaultUuidCategorie) {
		ProduitStockDto p = line.getArticleDto();
		if (p.getLibelleArticle() == null || p.getLibelleArticle().isBlank()) {
			p.setLibelleArticle(line.getArticle());
		}
		p.setQuantiteCommande(line.getQuantite());
		p.setPrixAchat(line.getPrixAchat());
		LocalDate d = line.getDateCommande() != null ? line.getDateCommande() : bon.getDateCommandeFournisseur();
		if (d != null) {
			p.setDateCommande(java.sql.Date.valueOf(d));
		}
		p.setUuidFournisseur(uuidFour);
		if (line.getCategorie() != null && !line.getCategorie().isBlank()) {
			p.setLibelleCategorie(line.getCategorie());
		}
		if (defaultUuidCategorie != null && !defaultUuidCategorie.isBlank()) {
			if ((p.getUuidCategorie() == null || p.getUuidCategorie().isBlank())
					&& (p.getLibelleCategorie() == null || p.getLibelleCategorie().isBlank())) {
				p.setUuidCategorie(defaultUuidCategorie);
			}
		}
		TypeUniteEnum cmdU = parseUniteEnum(line.getUnite());
		if (cmdU != null && cmdU != TypeUniteEnum.PIECE) {
			p.setMainUniteEnum(cmdU);
		}
		return p;
	}

	private void afterReceptionApplyPrixEtStock(List<ParsedRow> group, Boutique boutique) {
		for (ParsedRow r : group) {
			Optional<Produit> pOpt = produitRepository.findFirstByBoutique_UuidAndArticle_Uuid(boutique.getUuid(),
					r.articleUuid);
			if (!pOpt.isPresent()) {
				continue;
			}
			Produit produit = pOpt.get();
			if (r.prixVente > 0) {
				stockUniteVenteService.applyRetailPricesFromBaseUnit(produit, r.prixVente);
				produit.setPrixVente(r.prixVente);
			}
			if (r.quantiteRestantePieces > 0) {
				stockUniteVenteService.updateAllUnitsFromBaseQuantity(produit, r.quantiteRestantePieces);
			}
			produitRepository.save(produit);
		}
	}

	private String importOneBon(List<ParsedRow> group, String uuidFournisseur, Boutique boutique, Utilisateur utilisateur,
			Categorie categorie) {
		ParsedRow first = group.get(0);
		CommandeFournisseurDto cmd = new CommandeFournisseurDto();
		cmd.setUuidFournisseur(uuidFournisseur);
		cmd.setDateCommandeFournisseur(first.dateCommande);
		cmd.setPaye(false);
		cmd.setValeurMarchandise("Import Excel — livraison " + first.dateLivraison);

		List<DetailCommandeFournisseurDto> details = new ArrayList<>();
		for (ParsedRow r : group) {
			Article article = findOrCreateArticle(boutique, utilisateur, categorie, r);
			r.articleUuid = article.getUuid();
			TypeUniteDeVente tuv = resolveTypeUniteForCommande(article, r.uniteCommandeEnum, r.facteurPieces);
			DetailCommandeFournisseurDto d = new DetailCommandeFournisseurDto();
			d.setUuidArticle(article.getUuid());
			d.setQuantite(r.quantiteCommandee);
			d.setPrixAchat(r.prixAchat);
			d.setUnite(r.uniteCommandeEnum.name());
			d.setUuidTypeUniteDeVente(tuv.getUuid());
			details.add(d);
		}
		cmd.setDetailCommandeFournisseurDtos(details);

		CommandeFournisseurDto saved = commandeFournisseurService.addCommandeFournisseur(cmd);

		CommandeFournisseurDto reception = new CommandeFournisseurDto();
		reception.setUuid(saved.getUuid());
		List<DetailCommandeFournisseurDto> recDetails = new ArrayList<>();
		if (saved.getDetailCommandeFournisseurDtos() != null) {
			for (DetailCommandeFournisseurDto line : saved.getDetailCommandeFournisseurDtos()) {
				DetailCommandeFournisseurDto rd = new DetailCommandeFournisseurDto();
				rd.setUuid(line.getUuid());
				rd.setQuantiteLivraison(line.getQuantite());
				recDetails.add(rd);
			}
		}
		reception.setDetailCommandeFournisseurDtos(recDetails);
		commandeFournisseurService.addStockFromCommandeAndLivraison(reception);
		return saved.getUuid();
	}

	private TypeUniteDeVente resolveTypeUniteForCommande(Article article, TypeUniteEnum uniteCommande, int facteur) {
		List<TypeUniteDeVente> list = typeUniteDeVenteRepository.findByArticle(article);
		int factor = uniteCommande == TypeUniteEnum.PIECE ? 1 : Math.max(1, facteur);
		for (TypeUniteDeVente t : list) {
			if (t.getTypeUniteEnum() == uniteCommande && t.getUnite() == factor) {
				return t;
			}
		}
		throw new IllegalStateException("Unité de vente introuvable pour l'article « " + article.getLibelle()
				+ " » : " + uniteCommande + " ×" + factor);
	}

	private Article findOrCreateArticle(Boutique boutique, Utilisateur utilisateur, Categorie categorieDefaut,
			ParsedRow r) {
		Categorie categorieLigne = findOrCreateCategoriePourLigne(boutique, utilisateur, r.categorieLibelle,
				categorieDefaut);
		Optional<Article> existing = articleRepository.findFirstByBoutique_UuidAndLibelleIgnoreCase(boutique.getUuid(),
				r.designation.trim());
		if (existing.isPresent()) {
			Article a = existing.get();
			ensureTypeUnites(a, r);
			if (r.facteurPieces > 0) {
				a.setQuantiteDansCarton(Math.max(a.getQuantiteDansCarton(), r.facteurPieces));
			}
			return articleRepository.save(a);
		}

		Article article = new Article();
		article.setLibelle(r.designation.trim());
		article.setCodeProduit(safeCodeFromLibelle(r.designation));
		article.setDescription("Import Excel");
		article.setQuantiteDansCarton(Math.max(1, r.facteurPieces > 0 ? r.facteurPieces : 1));
		article.setBoutique(boutique);
		article.setCategorie(categorieLigne);
		article.setUtilisateur(utilisateur);
		boolean mixte = r.uniteAchatEnum != TypeUniteEnum.PIECE && r.facteurPieces > 1;
		article.setTypeGestionUnite(mixte ? TypeGestionUniteProduitEnum.MIXTE : TypeGestionUniteProduitEnum.NORMAL);
		article = articleRepository.save(article);
		ensureTypeUnites(article, r);
		return articleRepository.findById(article.getUuid()).orElse(article);
	}

	private void ensureTypeUnites(Article article, ParsedRow r) {
		List<TypeUniteDeVente> existing = new ArrayList<>(typeUniteDeVenteRepository.findByArticle(article));
		int factor = Math.max(1, r.facteurPieces);

		ensureOneUnit(article, existing, TypeUniteEnum.PIECE, 1);
		if (r.uniteAchatEnum != TypeUniteEnum.PIECE) {
			ensureOneUnit(article, existing, r.uniteAchatEnum, factor);
		}
		if (r.uniteCommandeEnum != TypeUniteEnum.PIECE
				&& (r.uniteCommandeEnum != r.uniteAchatEnum || r.uniteAchatEnum == TypeUniteEnum.PIECE)) {
			ensureOneUnit(article, existing, r.uniteCommandeEnum, factor);
		}
	}

	private void ensureOneUnit(Article article, List<TypeUniteDeVente> existing, TypeUniteEnum en, int factor) {
		for (TypeUniteDeVente t : existing) {
			if (t.getTypeUniteEnum() == en && t.getUnite() == factor) {
				return;
			}
		}
		TypeUniteDeVente t = new TypeUniteDeVente();
		t.setArticle(article);
		t.setTypeUniteEnum(en);
		t.setUnite(factor);
		t.setPrice(0d);
		t.setQtite(0);
		typeUniteDeVenteRepository.save(t);
		existing.add(t);
	}

	private static String safeCodeFromLibelle(String libelle) {
		String s = libelle.trim().toUpperCase(Locale.ROOT).replaceAll("[^A-Z0-9]", "_");
		if (s.length() > 80) {
			s = s.substring(0, 80);
		}
		return s.isEmpty() ? "IMP" : s;
	}

	private Categorie findOrCreateCategoriePourLigne(Boutique boutique, Utilisateur utilisateur, String libelleExcel,
			Categorie fallback) {
		if (libelleExcel == null || libelleExcel.isBlank()) {
			return fallback;
		}
		String key = libelleExcel.trim();
		return categorieRepository.findFirstByBoutique_UuidAndLibelleIgnoreCase(boutique.getUuid(), key)
				.orElseGet(() -> {
					Categorie c = new Categorie();
					c.setLibelle(key);
					c.setDescription("Créée automatiquement — import bon de commande / produits");
					c.setBoutique(boutique);
					c.setUtilisateur(utilisateur);
					return categorieRepository.save(c);
				});
	}

	private Categorie resolveCategorie(Boutique boutique, Utilisateur utilisateur, String uuidCategorieOpt,
			ImportBonCommandeProduitResultDto result) {
		if (uuidCategorieOpt != null && !uuidCategorieOpt.isBlank()) {
			return categorieRepository.findById(uuidCategorieOpt.trim())
					.orElseThrow(() -> new IllegalArgumentException("Catégorie introuvable : " + uuidCategorieOpt));
		}
		List<Categorie> cats = categorieRepository.getCategorieByBoutique(boutique.getUuid());
		if (cats != null && !cats.isEmpty()) {
			result.getAvertissements().add("uuidCategorie non fourni — utilisation de la catégorie « "
					+ cats.get(0).getLibelle() + " ».");
			return cats.get(0);
		}
		Categorie c = new Categorie();
		c.setLibelle("Import");
		c.setDescription("Créée automatiquement — import bon de commande / produits");
		c.setBoutique(boutique);
		c.setUtilisateur(utilisateur);
		result.getAvertissements().add("Aucune catégorie en boutique — création de la catégorie « Import ».");
		return categorieRepository.save(c);
	}

	private static Map<String, Integer> detectColumns(Row headerRow, DataFormatter fmt, ImportBonCommandeProduitResultDto result) {
		Map<String, Integer> map = new HashMap<>();
		if (headerRow == null) {
			return map;
		}
		for (int c = headerRow.getFirstCellNum(); c < headerRow.getLastCellNum(); c++) {
			Cell cell = headerRow.getCell(c);
			String raw = cell == null ? "" : fmt.formatCellValue(cell);
			String h = normalizeHeader(raw);
			if (h.isEmpty()) {
				continue;
			}
			if (h.contains("DESIGNATION")) {
				map.put("DESIGNATION", c);
			} else if (h.contains("PRIX") && h.contains("ACHAT")) {
				map.put("PRIX_ACHAT", c);
			} else if (h.contains("PRIX") && h.contains("VENTE")) {
				map.put("PRIX_VENTE", c);
			} else if (h.contains("QUANTITE") && h.contains("RESTANTE")) {
				map.put("Q_RESTANTE", c);
			} else if (h.contains("QUANTITE") && h.contains("COMMANDE")) {
				map.put("Q_COMMANDE", c);
			} else if (h.contains("QUANTITE") && h.contains("CARTON")) {
				map.put("Q_DANS_CARTON", c);
			} else if (h.contains("DATE") && h.contains("COMMANDE")) {
				map.put("DATE_COMMANDE", c);
			} else if (h.contains("DATE") && h.contains("LIVRAISON")) {
				map.put("DATE_LIVRAISON", c);
			} else if (h.equals("UNITE") || h.equals("UNITE CONDITIONNEMENT")) {
				map.put("UNITE_ACHAT", c);
			} else if (h.contains("UNITE") && h.contains("VENTE")) {
				map.put("UNITE_VENTE", c);
			} else if (h.contains("CATEGORIE") || h.contains("CATEGORY")
					|| h.equals("RAYON") || h.startsWith("RAYON ")) {
				map.put("CATEGORIE", c);
			}
		}
		return map;
	}

	private static String normalizeHeader(String s) {
		if (s == null) {
			return "";
		}
		String t = Normalizer.normalize(s.trim(), Normalizer.Form.NFD).replaceAll("\\p{M}", "");
		return t.toUpperCase(Locale.ROOT).replaceAll("\\s+", " ").trim();
	}

	private ParsedRow parseRow(int excelRowNum, Row row, Map<String, Integer> col, DataFormatter fmt) {
		String designation = cell(row, col.get("DESIGNATION"), fmt);
		if (designation.isEmpty()) {
			return null;
		}
		ParsedRow r = new ParsedRow();
		r.excelRow = excelRowNum;
		r.designation = designation;
		r.uniteAchatEnum = parseUniteEnum(cell(row, col.get("UNITE_ACHAT"), fmt));
		r.prixAchat = parseDouble(cell(row, col.get("PRIX_ACHAT"), fmt));
		r.prixVente = parseDouble(cell(row, col.get("PRIX_VENTE"), fmt));
		r.quantiteRestantePieces = parseInt(cell(row, col.get("Q_RESTANTE"), fmt), 0);
		r.facteurPieces = parseInt(cell(row, col.get("Q_DANS_CARTON"), fmt), 0);
		String qCom = cell(row, col.get("Q_COMMANDE"), fmt);
		parseQuantiteCommande(qCom, r);
		r.dateCommande = parseDate(cell(row, col.get("DATE_COMMANDE"), fmt));
		r.dateLivraison = parseDate(cell(row, col.get("DATE_LIVRAISON"), fmt));
		r.categorieLibelle = cell(row, col.get("CATEGORIE"), fmt);
		return r;
	}

	private static void parseQuantiteCommande(String raw, ParsedRow r) {
		if (raw == null || raw.isBlank()) {
			r.quantiteCommandee = 0;
			r.uniteCommandeEnum = r.uniteAchatEnum;
			return;
		}
		Matcher m = QTE_COMMANDE.matcher(raw.trim());
		if (m.matches()) {
			r.quantiteCommandee = Integer.parseInt(m.group(1));
			r.uniteCommandeEnum = parseUniteEnum(m.group(2));
			return;
		}
		r.quantiteCommandee = parseInt(raw, 0);
		r.uniteCommandeEnum = r.uniteAchatEnum;
	}

	private static TypeUniteEnum parseUniteEnum(String raw) {
		if (raw == null || raw.isBlank()) {
			return TypeUniteEnum.PIECE;
		}
		String u = raw.trim().toUpperCase(Locale.ROOT);
		if (u.endsWith("S") && u.length() > 2) {
			u = u.substring(0, u.length() - 1);
		}
		try {
			return TypeUniteEnum.valueOf(u);
		} catch (IllegalArgumentException e) {
			return TypeUniteEnum.PIECE;
		}
	}

	private static String cell(Row row, Integer colIdx, DataFormatter fmt) {
		if (colIdx == null || row == null) {
			return "";
		}
		Cell cell = row.getCell(colIdx);
		return cell == null ? "" : fmt.formatCellValue(cell).trim();
	}

	private static double parseDouble(String s) {
		if (s == null || s.isBlank()) {
			return 0d;
		}
		String t = s.replace(" ", "").replace(',', '.');
		try {
			return Double.parseDouble(t);
		} catch (NumberFormatException e) {
			return 0d;
		}
	}

	private static int parseInt(String s, int def) {
		if (s == null || s.isBlank()) {
			return def;
		}
		String t = s.replace(" ", "").replace(',', '.');
		int dot = t.indexOf('.');
		if (dot > 0) {
			t = t.substring(0, dot);
		}
		try {
			return Integer.parseInt(t);
		} catch (NumberFormatException e) {
			return def;
		}
	}

	private static LocalDate parseDate(String s) {
		if (s == null || s.isBlank()) {
			return null;
		}
		String t = s.trim();
		for (DateTimeFormatter f : DATE_FORMATS) {
			try {
				return LocalDate.parse(t, f);
			} catch (DateTimeParseException ignored) {
			}
		}
		return null;
	}

	private static class ParsedRow {
		int excelRow;
		String designation;
		TypeUniteEnum uniteAchatEnum = TypeUniteEnum.PIECE;
		TypeUniteEnum uniteCommandeEnum = TypeUniteEnum.PIECE;
		double prixAchat;
		double prixVente;
		int quantiteRestantePieces;
		int facteurPieces;
		int quantiteCommandee;
		LocalDate dateCommande;
		LocalDate dateLivraison;
		String categorieLibelle = "";
		String articleUuid;

		boolean validForCommande() {
			if (quantiteCommandee <= 0 || prixAchat <= 0) {
				return false;
			}
			if (dateCommande == null || dateLivraison == null) {
				return false;
			}
			if (uniteAchatEnum != TypeUniteEnum.PIECE && facteurPieces <= 0) {
				return false;
			}
			if (uniteCommandeEnum != TypeUniteEnum.PIECE && facteurPieces <= 0) {
				return false;
			}
			return true;
		}
	}

	private static int findHeaderRowIndex(Sheet sheet, DataFormatter fmt) {
		int last = Math.min(sheet.getLastRowNum(), 20);
		for (int r = sheet.getFirstRowNum(); r <= last; r++) {
			Row row = sheet.getRow(r);
			if (row == null) {
				continue;
			}
			for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
				Cell cell = row.getCell(c);
				String v = cell == null ? "" : fmt.formatCellValue(cell);
				if (normalizeHeader(v).contains("DESIGNATION")) {
					return r;
				}
			}
		}
		return -1;
	}
}
