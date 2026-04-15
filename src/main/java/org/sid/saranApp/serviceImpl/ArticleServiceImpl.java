package org.sid.saranApp.serviceImpl;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.sid.saranApp.dto.ArticleDto;
import org.sid.saranApp.dto.ArticleSelectDto;
import org.sid.saranApp.dto.PageDataDto;
import org.sid.saranApp.enume.TypeGestionUniteProduitEnum;
import org.sid.saranApp.enume.TypeUniteEnum;
import org.sid.saranApp.mapper.Mapper;
import org.sid.saranApp.model.Article;
import org.sid.saranApp.model.Categorie;
import org.sid.saranApp.model.TypeUniteDeVente;
import org.sid.saranApp.model.Utilisateur;
import org.sid.saranApp.repository.ArticleRepository;
import org.sid.saranApp.repository.CategorieRepository;
import org.sid.saranApp.repository.TypeUniteDeVenteRepository;
import org.sid.saranApp.repository.UtilisateurRepository;
import org.sid.saranApp.service.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	private ArticleRepository articleRepository;
	@Autowired
	private CategorieRepository categorieRepository;
	@Autowired
	private UtilisateurRepository utilisateurRepository;
	@Autowired
	private TypeUniteDeVenteRepository typeUniteDeVenteRepository;
	@Autowired
	private UtilisateurServiceImpl utilisateurServiceImpl;

	Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);

	private static final DataFormatter CELL_FORMATTER = new DataFormatter();

	/** Libellé / texte Excel : accepte cellule texte ou numérique (ex. code saisi en nombre). */
	private static String getCellStringValue(Cell cell) {
		if (cell == null) {
			return "";
		}
		return CELL_FORMATTER.formatCellValue(cell).trim();
	}

	private static int getCellIntValue(Cell cell, int defaultValue) {
		if (cell == null) {
			return defaultValue;
		}
		try {
			CellType t = cell.getCellType();
			if (t == CellType.FORMULA) {
				t = cell.getCachedFormulaResultType();
			}
			if (t == CellType.NUMERIC) {
				return (int) Math.round(cell.getNumericCellValue());
			}
		} catch (Exception ignored) {
			// fallback parse texte
		}
		String s = getCellStringValue(cell);
		if (s.isEmpty()) {
			return defaultValue;
		}
		try {
			String normalized = s.replace(',', '.');
			int dot = normalized.indexOf('.');
			if (dot >= 0) {
				normalized = normalized.substring(0, dot);
			}
			return Integer.parseInt(normalized.trim());
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}

	private static double getCellDoubleValue(Cell cell, double defaultValue) {
		if (cell == null) {
			return defaultValue;
		}
		try {
			CellType t = cell.getCellType();
			if (t == CellType.FORMULA) {
				t = cell.getCachedFormulaResultType();
			}
			if (t == CellType.NUMERIC) {
				return cell.getNumericCellValue();
			}
		} catch (Exception ignored) {
		}
		String s = getCellStringValue(cell);
		if (s.isEmpty()) {
			return defaultValue;
		}
		try {
			return Double.parseDouble(s.replace(" ", "").replace(',', '.'));
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}

	private static String normalizeHeaderArticle(String s) {
		if (s == null) {
			return "";
		}
		String t = Normalizer.normalize(s.trim(), Normalizer.Form.NFD).replaceAll("\\p{M}", "");
		return t.toUpperCase(Locale.ROOT).replaceAll("\\s+", " ").trim();
	}

	private static boolean isProbablyArticleHeaderRow(Row row) {
		if (row == null) {
			return false;
		}
		String h = normalizeHeaderArticle(getCellStringValue(row.getCell(0)));
		return h.contains("LIBELLE") || h.contains("DESIGNATION") || h.contains("PRODUIT")
				|| h.contains("ARTICLE") || h.contains("NOM");
	}

	private static Map<String, Integer> detectArticleImportColumns(Row headerRow) {
		Map<String, Integer> map = new HashMap<>();
		if (headerRow == null) {
			return map;
		}
		for (int c = headerRow.getFirstCellNum(); c < headerRow.getLastCellNum(); c++) {
			String h = normalizeHeaderArticle(getCellStringValue(headerRow.getCell(c)));
			if (h.isEmpty()) {
				continue;
			}
			if (h.contains("DESIGNATION") || h.contains("LIBELLE")
					|| (h.contains("PRODUIT") && !h.contains("PRIX") && !h.contains("CODE"))
					|| (h.contains("NOM") && !h.contains("FOURNISSEUR") && !h.contains("CLIENT")
							&& !h.contains("NOMBRE"))) {
				map.put("LIBELLE", c);
			} else if (h.contains("DESCRIPTION") || h.equals("DESC")) {
				map.put("DESCRIPTION", c);
			} else if ((h.contains("CODE") || h.contains("REF")) && !h.contains("BARRE")) {
				map.put("CODE", c);
			} else if ((h.contains("QUANTITE") && h.contains("CARTON")) || (h.contains("QTE") && h.contains("CARTON"))) {
				map.put("QTE_CARTON", c);
			} else if (h.contains("CATEGORIE") || h.contains("CATEGORY") || h.equals("RAYON") || h.startsWith("RAYON ")) {
				map.put("CATEGORIE", c);
			} else if (h.contains("UNITE")) {
				map.put("UNITE", c);
			} else if (h.contains("PRIX")) {
				map.put("PRIX", c);
			}
		}
		return map;
	}

	private static String cellByMap(Row row, Map<String, Integer> col, String key) {
		Integer idx = col.get(key);
		if (idx == null) {
			return "";
		}
		return getCellStringValue(row.getCell(idx));
	}

	private static int intByMap(Row row, Map<String, Integer> col, String key, int def) {
		Integer idx = col.get(key);
		if (idx == null) {
			return def;
		}
		return Math.max(0, getCellIntValue(row.getCell(idx), def));
	}

	private static double doubleByMap(Row row, Map<String, Integer> col, String key, double def) {
		Integer idx = col.get(key);
		if (idx == null) {
			return def;
		}
		return getCellDoubleValue(row.getCell(idx), def);
	}

	private static TypeUniteEnum parseUniteEnumImport(String raw) {
		if (raw == null || raw.isBlank()) {
			return TypeUniteEnum.PIECE;
		}
		String u = Normalizer.normalize(raw.trim(), Normalizer.Form.NFD).replaceAll("\\p{M}+", "");
		u = u.toUpperCase(Locale.ROOT).replace(' ', '_').replace('-', '_');
		switch (u) {
		case "KG":
		case "KGS":
		case "KILO":
			return TypeUniteEnum.KILOGRAMME;
		case "G":
		case "GR":
		case "GRL":
			return TypeUniteEnum.GRAMME;
		case "L":
			return TypeUniteEnum.LITRE;
		case "ML":
		case "CL":
			return TypeUniteEnum.MILLILITRE;
		case "PC":
		case "PCS":
		case "PCE":
		case "P":
		case "U":
		case "UN":
		case "UNIT":
		case "UNITE":
			return TypeUniteEnum.PIECE;
		case "CT":
		case "CTN":
			return TypeUniteEnum.CARTON;
		default:
			break;
		}
		if (u.endsWith("S") && u.length() > 2) {
			u = u.substring(0, u.length() - 1);
		}
		try {
			return TypeUniteEnum.valueOf(u);
		} catch (IllegalArgumentException e) {
			return TypeUniteEnum.PIECE;
		}
	}

	private void ensureUnitesVentePourImport(Article article, TypeUniteEnum uniteFichier, int facteurCarton,
			double prixUnite) {
		List<TypeUniteDeVente> existing = new ArrayList<>(typeUniteDeVenteRepository.findByArticle(article));
		TypeUniteEnum sec = uniteFichier == null ? TypeUniteEnum.PIECE : uniteFichier;
		int factor = Math.max(1, facteurCarton);
		if (sec == TypeUniteEnum.PIECE) {
			ensureOneTypeUnite(existing, article, TypeUniteEnum.PIECE, 1, prixUnite >= 0 ? prixUnite : 0d);
			return;
		}
		ensureOneTypeUnite(existing, article, TypeUniteEnum.PIECE, 1, 0d);
		ensureOneTypeUnite(existing, article, sec, factor, prixUnite >= 0 ? prixUnite : 0d);
	}

	private void ensureOneTypeUnite(List<TypeUniteDeVente> existing, Article article, TypeUniteEnum en, int factor,
			double price) {
		for (TypeUniteDeVente t : existing) {
			if (t.getTypeUniteEnum() == en && t.getUnite() == factor) {
				return;
			}
		}
		TypeUniteDeVente t = new TypeUniteDeVente();
		t.setArticle(article);
		t.setTypeUniteEnum(en);
		t.setUnite(factor);
		t.setPrice(price);
		t.setQtite(0);
		typeUniteDeVenteRepository.save(t);
		existing.add(t);
	}

	@Override
	public ArticleDto addArticle(ArticleDto articleDto) {
		// TODO Auto-generated method stub
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		logger.info("hello {}", auth.getName());
		Article article = new Article();

		Categorie categorie = categorieRepository.findById(articleDto.getUuidCategorie())
				.orElseThrow(() -> new IllegalArgumentException("Catégorie inconnue"));
		Utilisateur utilisateur = utilisateurRepository.findByEmail(auth.getName())
				.orElseThrow(() -> new IllegalStateException("Utilisateur non trouvé"));
		article.setLibelle(articleDto.getLibelle());
		article.setDescription(articleDto.getDescription());
		article.setBoutique(utilisateur.getBoutique());
		article.setCategorie(categorie);
		article.setUtilisateur(utilisateur);
		Article articleSave = articleRepository.save(article);
		return Mapper.toArticleDto(articleSave);
	}

	@Override
	public void deleteArticle(String uuid) {
		// TODO Auto-generated method stub
		Article article = articleRepository.findById(uuid)
				.orElseThrow(() -> new IllegalArgumentException("Article inconnu"));
		articleRepository.delete(article);

	}

	@Override
	public List<ArticleDto> findAll() {
		// TODO Auto-generated method stub
		List<Article> articles = articleRepository.listeArticles(utilisateurServiceImpl.getCurentUtilisateur().getBoutique().getUuid());
		List<ArticleDto> articleDtos = new ArrayList<ArticleDto>();
		articles.forEach(article -> articleDtos.add(Mapper.toArticleDto(article)));
		return articleDtos;
	}

	@Override
	public ArticleDto getArticle(String uuid) {
		// TODO Auto-generated method stub
		Article article = articleRepository.findById(uuid)
				.orElseThrow(() -> new IllegalArgumentException("Article inconnu"));
		return Mapper.toArticleDto(article);
	}

	/**
	 * Import Excel : articles + {@link TypeUniteDeVente} (unités de vente).
	 * <p>
	 * <b>Mode en-têtes</b> (recommandé) : la ligne 1 contient par ex. LIBELLÉ, DESCRIPTION, CODE, QTÉ CARTON,
	 * CATÉGORIE / RAYON, UNITÉ (PIECE, CARTON, KILOGRAMME, KG…), PRIX. Les colonnes sont détectées par le libellé.
	 * </p>
	 * <p>
	 * <b>Mode historique</b> : si la cellule A1 ne ressemble pas à un en-tête, la ligne 1 est ignorée et les colonnes
	 * fixes sont A–E : libellé, description, code, quantité carton, catégorie (pas d’unité dans le fichier :
	 * seule l’unité PIÈCE ×1 est créée).
	 * </p>
	 */
	@Override
	public void importationArticle(MultipartFile file) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Utilisateur utilisateur = utilisateurRepository.findByEmail(auth.getName())
				.orElseThrow(() -> new IllegalStateException("Utilisateur non trouvé"));

		Categorie categorieParDefaut = findOrCreateCategorieParDefaut(utilisateur);
		String boutiqueUuid = utilisateur.getBoutique().getUuid();

		try (InputStream inputStream = file.getInputStream()) {
			Workbook workbook = WorkbookFactory.create(inputStream);
			Sheet sheet = workbook.getSheetAt(0);
			Row row0 = sheet.getRow(0);
			Map<String, Integer> col = null;
			boolean headerMode = isProbablyArticleHeaderRow(row0);
			if (headerMode) {
				col = detectArticleImportColumns(row0);
				if (!col.containsKey("LIBELLE")) {
					col = null;
					headerMode = false;
				}
			}

			int lastRow = sheet.getLastRowNum();
			for (int r = 1; r <= lastRow; r++) {
				Row row = sheet.getRow(r);
				if (row == null) {
					continue;
				}

				String libelle;
				String description;
				String codeSaisi;
				int qteCarton;
				String libelleCategorie;
				TypeUniteEnum uniteFichier = TypeUniteEnum.PIECE;
				double prixUnite = 0d;

				if (headerMode && col != null) {
					libelle = cellByMap(row, col, "LIBELLE");
					description = cellByMap(row, col, "DESCRIPTION");
					codeSaisi = cellByMap(row, col, "CODE");
					qteCarton = intByMap(row, col, "QTE_CARTON", 0);
					libelleCategorie = cellByMap(row, col, "CATEGORIE");
					String uniteRaw = cellByMap(row, col, "UNITE");
					uniteFichier = parseUniteEnumImport(uniteRaw);
					prixUnite = doubleByMap(row, col, "PRIX", 0d);
				} else {
					libelle = getCellStringValue(row.getCell(0));
					description = getCellStringValue(row.getCell(1));
					codeSaisi = getCellStringValue(row.getCell(2));
					qteCarton = Math.max(0, getCellIntValue(row.getCell(3), 0));
					libelleCategorie = getCellStringValue(row.getCell(4));
				}

				if (libelle.isEmpty()) {
					continue;
				}

				Optional<Article> deja = articleRepository.findFirstByBoutique_UuidAndLibelleIgnoreCase(boutiqueUuid,
						libelle);
				if (deja.isPresent()) {
					logger.info("Import article : libellé « {} » déjà présent pour la boutique, ligne ignorée ({})",
							libelle, row.getRowNum() + 1);
					continue;
				}

				Categorie categorie = resolveCategoriePourImport(utilisateur, libelleCategorie, categorieParDefaut);
				String codeProduit = resolveCodeProduitPourImport(codeSaisi, libelle, boutiqueUuid);

				int facteurUnite = Math.max(1, qteCarton > 0 ? qteCarton : 1);
				boolean mixte = qteCarton > 1
						|| (uniteFichier != TypeUniteEnum.PIECE && facteurUnite > 1);

				Article article = new Article();
				article.setLibelle(libelle);
				article.setDescription(description);
				article.setCodeProduit(codeProduit);
				article.setQuantiteDansCarton(qteCarton);
				article.setBoutique(utilisateur.getBoutique());
				article.setUtilisateur(utilisateur);
				article.setCategorie(categorie);
				article.setTypeGestionUnite(mixte ? TypeGestionUniteProduitEnum.MIXTE
						: TypeGestionUniteProduitEnum.NORMAL);

				article = articleRepository.save(article);
				if (headerMode && col != null) {
					ensureUnitesVentePourImport(article, uniteFichier, qteCarton > 0 ? qteCarton : facteurUnite,
							prixUnite);
				} else {
					ensureUnitesVentePourImport(article, TypeUniteEnum.PIECE, 1, 0d);
				}
				logger.debug("Import article : {} / code {}", libelle, codeProduit);
			}
		} catch (IOException e) {
			throw new UncheckedIOException("Lecture du fichier Excel impossible", e);
		}
	}

	private Categorie findOrCreateCategorieParDefaut(Utilisateur utilisateur) {
		String boutiqueUuid = utilisateur.getBoutique().getUuid();
		return categorieRepository.findFirstByBoutique_UuidAndLibelleIgnoreCase(boutiqueUuid, "AUCUN").orElseGet(() -> {
			Categorie c = new Categorie();
			c.setLibelle("AUCUN");
			c.setDescription("Non classé (import Excel)");
			c.setBoutique(utilisateur.getBoutique());
			c.setUtilisateur(utilisateur);
			return categorieRepository.save(c);
		});
	}

	private Categorie resolveCategoriePourImport(Utilisateur utilisateur, String libelleCategorieExcel,
			Categorie defaut) {
		if (libelleCategorieExcel == null || libelleCategorieExcel.isBlank()) {
			return defaut;
		}
		String key = libelleCategorieExcel.trim();
		return categorieRepository.findFirstByBoutique_UuidAndLibelleIgnoreCase(
				utilisateur.getBoutique().getUuid(), key).orElseGet(() -> {
					Categorie c = new Categorie();
					c.setLibelle(key);
					c.setDescription("Créée automatiquement — import Excel articles");
					c.setBoutique(utilisateur.getBoutique());
					c.setUtilisateur(utilisateur);
					Categorie saved = categorieRepository.save(c);
					logger.info("Import article : catégorie « {} » créée pour la boutique", key);
					return saved;
				});
	}

	private String resolveCodeProduitPourImport(String codeSaisi, String libelle, String boutiqueUuid) {
		String code = codeSaisi == null ? "" : codeSaisi.trim();
		if (!code.isEmpty() && !articleRepository.existsByBoutiqueUuidAndCodeProduitIgnoreCase(boutiqueUuid, code)) {
			return code;
		}
		if (!code.isEmpty()) {
			logger.warn("Import article : code produit « {} » déjà utilisé, génération d'un code unique", code);
		}
		return generateUniqueCodeProduit(libelle, boutiqueUuid);
	}

	private String generateUniqueCodeProduit(String libelle, String boutiqueUuid) {
		String base = slugForCodeProduit(libelle);
		for (int i = 0; i < 24; i++) {
			String suffix = Integer.toHexString(ThreadLocalRandom.current().nextInt(0x1000000)).toUpperCase(Locale.ROOT);
			String candidate = base + "-" + suffix;
			if (!articleRepository.existsByBoutiqueUuidAndCodeProduitIgnoreCase(boutiqueUuid, candidate)) {
				return candidate;
			}
		}
		String fallback = "REF-" + UUID.randomUUID().toString().replace("-", "").substring(0, 12).toUpperCase(Locale.ROOT);
		if (!articleRepository.existsByBoutiqueUuidAndCodeProduitIgnoreCase(boutiqueUuid, fallback)) {
			return fallback;
		}
		return "REF-" + UUID.randomUUID().toString().replace("-", "").toUpperCase(Locale.ROOT);
	}

	private static String slugForCodeProduit(String libelle) {
		if (libelle == null || libelle.isBlank()) {
			return "REF";
		}
		String n = Normalizer.normalize(libelle.trim(), Normalizer.Form.NFD).replaceAll("\\p{M}+", "");
		String s = n.toUpperCase(Locale.ROOT).replaceAll("[^A-Z0-9]+", "-");
		s = s.replaceAll("^-+", "").replaceAll("-+$", "");
		if (s.length() > 20) {
			s = s.substring(0, 20);
		}
		return s.isEmpty() ? "REF" : s;
	}

	@Override
	public List<ArticleSelectDto> listeSelect() {
		// TODO Auto-generated method stub
		List<Article> articles = articleRepository.listeArticles(utilisateurServiceImpl.getCurentUtilisateur().getBoutique().getUuid());
		List<ArticleSelectDto> articleSelectDtos = new ArrayList<ArticleSelectDto>();
		articles.forEach(article -> articleSelectDtos.add(Mapper.toArticleSelectDto(article)));
		return articleSelectDtos;
	}

	@Override
	public ArticleDto updateArticle(ArticleDto articleDto, String uuid) {
		// TODO Auto-generated method stub
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Categorie categorie = categorieRepository.findById(articleDto.getUuidCategorie())
				.orElseThrow(() -> new IllegalArgumentException("Catégorie inconnue"));
		Utilisateur utilisateur = utilisateurRepository.findByEmail(auth.getName())
				.orElseThrow(() -> new IllegalStateException("Utilisateur non trouvé"));
		Article article = articleRepository.findById(uuid)
				.orElseThrow(() -> new IllegalArgumentException("Article inconnu"));
		article.setLibelle(articleDto.getLibelle());
		article.setDescription(articleDto.getDescription());
		article.setBoutique(utilisateur.getBoutique());
		article.setCategorie(categorie);
		article.setUtilisateur(utilisateur);
		Article articleSave = articleRepository.save(article);
		return Mapper.toArticleDto(articleSave);
	}

	@Override
	public PageDataDto<ArticleDto> listeArticles(int page, int size, String key) {
		// TODO Auto-generated method stub
		String uuidBoutique = utilisateurServiceImpl.getCurentUtilisateur().getBoutique().getUuid();
		PageDataDto<ArticleDto> pageDataDto = new PageDataDto<ArticleDto>();
		List<ArticleDto> articleDtos = new ArrayList<ArticleDto>();
		Pageable pageable = PageRequest.of(page, size);
		
		Page<Article> articles = null;
		
		if(key != null) {
			articles = articleRepository.listeArticleByLibelle(key, pageable);
			articles.forEach(article -> articleDtos.add(Mapper.toArticleDto(article)));
		}
		
		if(key == null ) {
			
			articles = articleRepository.listeArticle(pageable, uuidBoutique);
			articles.forEach(article -> articleDtos.add(Mapper.toArticleDto(article)));
		}
		
		
		pageDataDto.setData(articleDtos);
		pageDataDto.getPage().setPageNumber(page);
		pageDataDto.getPage().setSize(size);
		pageDataDto.getPage().setTotalElements(articles.getTotalElements());
		pageDataDto.getPage().setTotalPages(articles.getTotalPages());
		return pageDataDto;
	}

	@Override
	public void delete(String uuid) {
		// TODO Auto-generated method stub
		articleRepository.deleteById(uuid);
	}

}
