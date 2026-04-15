package org.sid.saranApp.serviceImpl;

import org.sid.saranApp.dto.CommandeFournisseurDto;
import org.sid.saranApp.dto.CommandeFournisseurPatchDto;
import org.sid.saranApp.dto.DetailCommandeFournisseurDto;
import org.sid.saranApp.dto.PageDataDto;
import org.sid.saranApp.enume.StatusCommandeFournisseurEnum;
import org.sid.saranApp.mapper.Mapper;
import org.sid.saranApp.model.*;
import org.sid.saranApp.repository.*;
import org.sid.saranApp.repository.ArticleRepository;
import org.sid.saranApp.service.CommandeFournisseurService;
import org.sid.saranApp.service.StockUniteVenteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class CommandeFournisseurServiceImpl implements CommandeFournisseurService {

	@Autowired
	private CommandeFournisseurRepository commandeFournisseurRepository;
	@Autowired
	private FournisseurRepository fournisseurRepository;
	@Autowired
	private BoutiqueRepository boutiqueRepository;
	@Autowired
	private UtilisateurRepository utilisateurRepository;
	@Autowired
	private DetailCommandeFournisseurRepository detailCommandeFournisseurRepository;
	@Autowired
	private ArticleRepository articleRepository;
	@Autowired
	private LivraisonCommandeFournisseurRepository livraisonCommandeFournisseurRepository;
	@Autowired
	private ProduitRepository produitRepository;
	@Autowired
	private UtilisateurServiceImpl utilisateurServiceImpl;
	@Autowired
	private CaracteristiqueProduitRepository caracteristiqueProduitRepository;
	@Autowired
	private StockUniteVenteService stockUniteVenteService;
	@Autowired
	private TypeUniteDeVenteRepository typeUniteDeVenteRepository;
	@Autowired
	private StockUniteVenteRepository stockUniteVenteRepository;

	Logger logger = LoggerFactory.getLogger(CommandeFournisseurServiceImpl.class);

	@Override
	public CommandeFournisseurDto addCommandeFournisseur(CommandeFournisseurDto commandeFournisseurDto) {
		// TODO Auto-generated method stub
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		logger.info("Création d'une commande fournisseur par: {}", auth.getName());
		CommandeFournisseur commandeFournisseur = new CommandeFournisseur();
		Utilisateur utilisateur = utilisateurRepository.findByEmail(auth.getName()).orElseThrow(null);
		
		if (utilisateur.getBoutique() == null) {
			throw new RuntimeException("Boutique non trouvée pour l'utilisateur");
		}

		Fournisseur fournisseur = fournisseurRepository.findById(commandeFournisseurDto.getUuidFournisseur())
				.orElseThrow(null);
		commandeFournisseur.setValeurMarchandise(commandeFournisseurDto.getValeurMarchandise());
		commandeFournisseur.setDateCommandeFournisseur(commandeFournisseurDto.getDateCommandeFournisseur());
		commandeFournisseur.setPaie(commandeFournisseurDto.isPaye());
		commandeFournisseur.setCommandeFournisseurEnum(StatusCommandeFournisseurEnum.ENREGISTRER);
		commandeFournisseur.setRefCommande("");
		commandeFournisseur.setBoutique(utilisateur.getBoutique());
		commandeFournisseur.setUtilisateur(utilisateur);
		commandeFournisseur.setFournisseur(fournisseur);
		
		// Préparer les détails AVANT la sauvegarde de la commande
		List<DetailCommandeFournisseurDto> detailCommandeFournisseurDtos = commandeFournisseurDto
				.getDetailCommandeFournisseurDtos();
		
		double montantTotal = 0.0;
		List<DetailCommandeFournisseur> details = new ArrayList<>();
		
		if (detailCommandeFournisseurDtos != null && !detailCommandeFournisseurDtos.isEmpty()) {
			for (DetailCommandeFournisseurDto detailDto : detailCommandeFournisseurDtos) {
				Article article = articleRepository.findById(detailDto.getUuidArticle())
						.orElseThrow(() -> new RuntimeException("Article non trouvé: " + detailDto.getUuidArticle()));

				TypeUniteDeVente typeUniteDeVente = null;
				if (detailDto.getUuidTypeUniteDeVente() != null) {
					typeUniteDeVente = typeUniteDeVenteRepository.findById(detailDto.getUuidTypeUniteDeVente())
							.orElseThrow(() -> new RuntimeException("Unité de vente non trouvée: " + detailDto.getUuidTypeUniteDeVente()));
				}

				DetailCommandeFournisseur detailCommandeFournisseur = new DetailCommandeFournisseur();
				detailCommandeFournisseur.setArticle(article);
				detailCommandeFournisseur.setCommandeFournisseur(commandeFournisseur);
				detailCommandeFournisseur.setBoutique(utilisateur.getBoutique());
				detailCommandeFournisseur.setUnite(detailDto.getUnite()); // Ancien champ pour compatibilité
				detailCommandeFournisseur.setTypeUniteDeVente(typeUniteDeVente); // Nouveau champ
				detailCommandeFournisseur.setUtilisateur(utilisateur);
				detailCommandeFournisseur.setPrixAchat(detailDto.getPrixAchat());
				detailCommandeFournisseur.setQuantite(detailDto.getQuantite());
				
				details.add(detailCommandeFournisseur);
				
				// Calculer le montant total
				montantTotal += detailDto.getPrixAchat() * detailDto.getQuantite();
			}
		}
		
		// Définir le montant total et les détails AVANT la sauvegarde
		commandeFournisseur.setMontantTotal(montantTotal);
		commandeFournisseur.setListeDetailCommandeFournisseur(details);
		
		// Sauvegarder la commande avec ses détails (CascadeType.ALL dans l'entité)
		CommandeFournisseur commandeFournisseurSave = commandeFournisseurRepository.save(commandeFournisseur);
		
		// Sauvegarder explicitement les détails pour s'assurer qu'ils sont bien persistés
		if (!details.isEmpty()) {
			detailCommandeFournisseurRepository.saveAll(details);
		}
		
		// Recharger la commande avec tous ses détails pour éviter les problèmes de lazy loading
		CommandeFournisseur commandeComplete = commandeFournisseurRepository.findById(commandeFournisseurSave.getUuid())
			.orElse(commandeFournisseurSave);
		
		// Forcer le chargement des détails
		if (commandeComplete.getListeDetailCommandeFournisseur() != null) {
			commandeComplete.getListeDetailCommandeFournisseur().size(); // Force le chargement
		}
		
		return Mapper.toCommandeFournisseurDto(commandeComplete);
	}

	@Override
	public CommandeFournisseurDto addLivraisonCommandeFournisseur(CommandeFournisseurDto commandeFournisseurDto) {
		// TODO Auto-generated method stub
		LocalTime time = LocalTime.now();
		CommandeFournisseur commandeFournisseur = commandeFournisseurRepository
				.findById(commandeFournisseurDto.getUuid()).orElseThrow(null);
		List<DetailCommandeFournisseurDto> detailCommandeFournisseurs = commandeFournisseurDto
				.getDetailCommandeFournisseurDtos();
		for (Iterator iterator = detailCommandeFournisseurs.iterator(); iterator.hasNext();) {
			DetailCommandeFournisseurDto detailCommandeFournisseurDto = (DetailCommandeFournisseurDto) iterator.next();
			DetailCommandeFournisseur detailCommandeFournisseur = detailCommandeFournisseurRepository
					.findById(detailCommandeFournisseurDto.getUuid()).orElseThrow(null);
			LivraisonCommandeFournisseur livraison = new LivraisonCommandeFournisseur();
			livraison.setBoutique(commandeFournisseur.getBoutique());
			livraison.setCommandeFournisseur(commandeFournisseur);
			livraison.setDateLivraison(new Date());
			livraison.setHeure(time.toString());
			livraison.setQuantite(detailCommandeFournisseurDto.getQuantiteLivraison());
			livraison.setDetailCommandeFournisseur(detailCommandeFournisseur);
			livraison.setUtilisateur(commandeFournisseur.getUtilisateur());
			livraisonCommandeFournisseurRepository.save(livraison);
		}
		commandeFournisseur.setCommandeFournisseurEnum(StatusCommandeFournisseurEnum.RECU);
		commandeFournisseurRepository.save(commandeFournisseur);
		return Mapper.toCommandeFournisseurDto(commandeFournisseur);
	}

	/**
	 * Réception d'achat : met à jour le stock à partir des lignes du bon, sans exiger de fiche
	 * {@link LivraisonCommandeFournisseur}. Quantité réceptionnée = {@code quantiteLivraison} dans le DTO si > 0,
	 * sinon quantité commandée sur la ligne.
	 */
	@Override
	public CommandeFournisseurDto addStockFromCommandeAndLivraison(CommandeFournisseurDto commandeFournisseurDto) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Utilisateur utilisateur = utilisateurRepository.findByEmail(auth.getName()).orElseThrow(null);

		CommandeFournisseur commandeFournisseur = commandeFournisseurRepository
				.findById(commandeFournisseurDto.getUuid()).orElseThrow(null);

		List<DetailCommandeFournisseurDto> detailCommandeFournisseurs = commandeFournisseurDto
				.getDetailCommandeFournisseurDtos();
		if (detailCommandeFournisseurs == null || detailCommandeFournisseurs.isEmpty()) {
			throw new IllegalArgumentException("Aucune ligne de commande à réceptionner.");
		}

		Boutique boutique = commandeFournisseur.getBoutique();
		int traitees = 0;
		Map<String, Integer> qteRecueParLigne = new HashMap<>();

		for (DetailCommandeFournisseurDto detailDto : detailCommandeFournisseurs) {
			DetailCommandeFournisseur detailCommandeFournisseur = detailCommandeFournisseurRepository
					.findById(detailDto.getUuid())
					.orElseThrow(() -> new RuntimeException("Détail de commande introuvable: " + detailDto.getUuid()));

			int qteCommandee = detailCommandeFournisseur.getQuantite();
			int qteDto = detailDto.getQuantiteLivraison();
			int quantiteReception = qteDto > 0 ? Math.min(qteDto, qteCommandee) : qteCommandee;
			if (quantiteReception <= 0) {
				continue;
			}
			traitees++;
			qteRecueParLigne.put(detailCommandeFournisseur.getUuid(), quantiteReception);

			Article article = detailCommandeFournisseur.getArticle();
			if (article == null) {
				throw new RuntimeException("Article manquant sur le détail " + detailCommandeFournisseur.getUuid());
			}

			Produit produit = produitRepository
					.findFirstByBoutique_UuidAndArticle_Uuid(boutique.getUuid(), article.getUuid())
					.orElse(null);

			if (produit == null) {
				produit = new Produit();
				produit.setArticle(article);
				produit.setFournisseur(commandeFournisseur.getFournisseur());
				produit.setBoutique(boutique);
				produit.setUtilisateur(utilisateur);
				produit.setDateEnregistrement(new Date());
				produit.setLivraisonCommandeFournisseur(null);
				produit = produitRepository.save(produit);
			}

			TypeUniteDeVente typeUniteDeVente = null;
			if (detailCommandeFournisseur.getTypeUniteDeVente() != null) {
				typeUniteDeVente = detailCommandeFournisseur.getTypeUniteDeVente();
			} else {
				List<TypeUniteDeVente> unites = typeUniteDeVenteRepository.findByArticle(article);
				String uniteNom = detailCommandeFournisseur.getUnite();
				if (uniteNom != null) {
					typeUniteDeVente = unites.stream()
							.filter(u -> u.getTypeUniteEnum().name().equalsIgnoreCase(uniteNom))
							.findFirst()
							.orElse(null);
				}
				if (typeUniteDeVente == null) {
					typeUniteDeVente = new TypeUniteDeVente();
					typeUniteDeVente.setArticle(article);
					typeUniteDeVente.setUnite(1);
					typeUniteDeVente.setTypeUniteEnum(org.sid.saranApp.enume.TypeUniteEnum.PIECE);
					typeUniteDeVente.setPrice(0d);
					typeUniteDeVente = typeUniteDeVenteRepository.save(typeUniteDeVente);
				}
			}

			stockUniteVenteService.initializeStocksForProduit(produit);

			List<StockUniteVenteService.TypeUniteDeVenteQuantite> unitesAvecQuantites = new ArrayList<>();
			unitesAvecQuantites
					.add(new StockUniteVenteService.TypeUniteDeVenteQuantite(typeUniteDeVente, quantiteReception));

			stockUniteVenteService.addStockMultipleUnits(produit, unitesAvecQuantites);

			StockUniteVente stockUniteVente = stockUniteVenteRepository
					.findByProduitAndTypeUniteDeVente(produit, typeUniteDeVente)
					.orElse(null);

			if (stockUniteVente != null) {
				double prixUnitaire = detailCommandeFournisseur.getPrixAchat();
				if (typeUniteDeVente.getUnite() > 1) {
					prixUnitaire = detailCommandeFournisseur.getPrixAchat() / typeUniteDeVente.getUnite();
				}
				stockUniteVente.setPrice(prixUnitaire);
				stockUniteVenteRepository.save(stockUniteVente);
			}

			produit.setPrixAchat(detailCommandeFournisseur.getPrixAchat());
			produit.setFournisseur(commandeFournisseur.getFournisseur());
			produitRepository.save(produit);
		}

		if (traitees == 0) {
			throw new IllegalArgumentException(
					"Aucune quantité à réceptionner : cochez des lignes et indiquez une quantité > 0.");
		}

		commandeFournisseur = commandeFournisseurRepository.findById(commandeFournisseur.getUuid()).orElseThrow(null);
		List<DetailCommandeFournisseur> toutesLignes = commandeFournisseur.getListeDetailCommandeFournisseur();
		if (toutesLignes != null) {
			toutesLignes.size();
		}
		boolean receptionComplete = true;
		if (toutesLignes == null || toutesLignes.isEmpty()) {
			receptionComplete = false;
		} else {
			for (DetailCommandeFournisseur ligne : toutesLignes) {
				int commandee = ligne.getQuantite();
				int recue = qteRecueParLigne.getOrDefault(ligne.getUuid(), 0);
				if (recue < commandee) {
					receptionComplete = false;
					break;
				}
			}
		}
		commandeFournisseur.setCommandeFournisseurEnum(receptionComplete ? StatusCommandeFournisseurEnum.STOCK_APPROVISIONNEE
				: StatusCommandeFournisseurEnum.EXPEDIE);
		commandeFournisseurRepository.save(commandeFournisseur);

		return Mapper.toCommandeFournisseurDto(commandeFournisseur);
	}

	@Override
	public CommandeFournisseurDto patchCommandeFournisseur(String uuid, CommandeFournisseurPatchDto patch) {
		if (patch == null) {
			throw new IllegalArgumentException("Corps de requête requis.");
		}
		boolean hasStatus = patch.getStatus() != null && !patch.getStatus().isBlank();
		if (!hasStatus && patch.getPaye() == null) {
			throw new IllegalArgumentException("Fournissez au moins \"status\" ou \"paye\".");
		}
		CommandeFournisseur commandeFournisseur = commandeFournisseurRepository.findById(uuid).orElseThrow(null);
		if (hasStatus) {
			commandeFournisseur.setCommandeFournisseurEnum(
					StatusCommandeFournisseurEnum.valueOf(patch.getStatus().trim()));
		}
		if (patch.getPaye() != null) {
			commandeFournisseur.setPaie(patch.getPaye());
		}
		return Mapper.toCommandeFournisseurDto(commandeFournisseurRepository.save(commandeFournisseur));
	}

	@Override
	public void deleteCommandeFournisseur(String uuid) {
		// TODO Auto-generated method stub
		CommandeFournisseur commandeFournisseur = commandeFournisseurRepository.findById(uuid).orElseThrow(null);
		commandeFournisseurRepository.delete(commandeFournisseur);

	}

	@Override
	public List<CommandeFournisseurDto> findAll() {
		// TODO Auto-generated method stub
		List<CommandeFournisseur> commandeFournisseurs = commandeFournisseurRepository
				.listeCommandes(utilisateurServiceImpl.getCurentUtilisateur().getBoutique().getUuid());
		List<CommandeFournisseurDto> commandeFournisseurDtos = new ArrayList<CommandeFournisseurDto>();
		commandeFournisseurs.forEach(commandeFournisseur -> commandeFournisseurDtos
				.add(Mapper.toCommandeFournisseurDto(commandeFournisseur)));
		return commandeFournisseurDtos;
	}

	@Override
	public CommandeFournisseurDto getCommandeFournisseur(String uuid) {
		// TODO Auto-generated method stub
		CommandeFournisseur commandeFournisseur = commandeFournisseurRepository.findById(uuid).orElseThrow(null);
		
		// Forcer le chargement des relations pour éviter les problèmes de lazy loading
		if (commandeFournisseur != null) {
			// Charger explicitement les détails
			if (commandeFournisseur.getListeDetailCommandeFournisseur() != null) {
				commandeFournisseur.getListeDetailCommandeFournisseur().size(); // Force le chargement
			}
			// Charger explicitement les livraisons
			if (commandeFournisseur.getListeLivraisonCommandeFournisseur() != null) {
				commandeFournisseur.getListeLivraisonCommandeFournisseur().size(); // Force le chargement
			}
			// Charger explicitement les paiements
			if (commandeFournisseur.getPaiements() != null) {
				commandeFournisseur.getPaiements().size(); // Force le chargement
			}
		}
		
		return Mapper.toCommandeFournisseurDto(commandeFournisseur);
	}

	@Override
	public CommandeFournisseurDto updateCommandeFournisseur(CommandeFournisseurDto commandeFournisseurDto,
			String uuid) {
		// TODO Auto-generated method stub
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Utilisateur utilisateur = utilisateurRepository.findByEmail(auth.getName()).orElseThrow(null);
		Fournisseur fournisseur = fournisseurRepository.findById(commandeFournisseurDto.getUuidFournisseur())
				.orElseThrow(null);
		Boutique boutique = boutiqueRepository.findById(commandeFournisseurDto.getUuidBoutique()).orElseThrow(null);
		CommandeFournisseur commandeFournisseur = commandeFournisseurRepository.findById(uuid).orElseThrow(null);
		commandeFournisseur.setDateCommandeFournisseur(commandeFournisseurDto.getDateCommandeFournisseur());
		commandeFournisseur.setPaie(commandeFournisseurDto.isPaye());
		commandeFournisseur.setCommandeFournisseurEnum(commandeFournisseurDto.getStatus());
		commandeFournisseur.setRefCommande(commandeFournisseurDto.getRefCommande());
		commandeFournisseur.setValeurMarchandise(commandeFournisseurDto.getValeurMarchandise());
		commandeFournisseur.setBoutique(boutique);
		commandeFournisseur.setUtilisateur(utilisateur);
		commandeFournisseur.setFournisseur(fournisseur);
		CommandeFournisseur commandeFournisseurSave = commandeFournisseurRepository.save(commandeFournisseur);
		return Mapper.toCommandeFournisseurDto(commandeFournisseurSave);
	}

	@Override
	public PageDataDto<CommandeFournisseurDto> listeCommandeFournisseurs(int page, int size, StatusCommandeFournisseurEnum key) {
		// TODO Auto-generated method stub
		String uuidBoutique = utilisateurServiceImpl.getCurentUtilisateur().getBoutique().getUuid();
		PageDataDto<CommandeFournisseurDto> pageDataDto = new PageDataDto<CommandeFournisseurDto>();
		List<CommandeFournisseurDto> commandeFournisseurDtos = new ArrayList<CommandeFournisseurDto>();
		Pageable pageable = PageRequest.of(page, size);
		
		Page<CommandeFournisseur> commandeFournisseurs = null;
		
		if(key != null) {
			commandeFournisseurs = commandeFournisseurRepository.listeCommandePeriodeByStatusPage(uuidBoutique, key, pageable);
			commandeFournisseurs.forEach(cmd -> commandeFournisseurDtos.add(Mapper.toCommandeFournisseurDto(cmd)));
		}
		
		if(key == null ) {
			
			commandeFournisseurs = commandeFournisseurRepository.listeCommandePeriodePage(uuidBoutique, pageable);
			commandeFournisseurs.forEach(cmd -> commandeFournisseurDtos.add(Mapper.toCommandeFournisseurDto(cmd)));
		}
		
		
		pageDataDto.setData(commandeFournisseurDtos);
		pageDataDto.getPage().setPageNumber(page);
		pageDataDto.getPage().setSize(size);
		pageDataDto.getPage().setTotalElements(commandeFournisseurs.getTotalElements());
		pageDataDto.getPage().setTotalPages(commandeFournisseurs.getTotalPages());
		return pageDataDto;
	}

	@Override
	public PageDataDto<CommandeFournisseurDto> listeCommandeFournisseurByDates(int page, int size, LocalDate dateDebut,
			LocalDate dateFin) {
		logger.info("date : {}",dateDebut);
		// TODO Auto-generated method stub
		String uuidBoutique = utilisateurServiceImpl.getCurentUtilisateur().getBoutique().getUuid();
		PageDataDto<CommandeFournisseurDto> pageDataDto = new PageDataDto<CommandeFournisseurDto>();
		List<CommandeFournisseurDto> commandeFournisseurDtos = new ArrayList<CommandeFournisseurDto>();
		Pageable pageable = PageRequest.of(page, size);
		
		Page<CommandeFournisseur> commandeFournisseurs = null;
		
		commandeFournisseurs = commandeFournisseurRepository.listeCommandePeriode(uuidBoutique, dateDebut, dateFin, pageable);
		commandeFournisseurs.forEach(cmd -> commandeFournisseurDtos.add(Mapper.toCommandeFournisseurDto(cmd)));
	
		pageDataDto.setData(commandeFournisseurDtos);
		pageDataDto.getPage().setPageNumber(page);
		pageDataDto.getPage().setSize(size);
		pageDataDto.getPage().setTotalElements(commandeFournisseurs.getTotalElements());
		pageDataDto.getPage().setTotalPages(commandeFournisseurs.getTotalPages());
		return pageDataDto;
	}

	@Override
	public void delete(String uuid) {
		// TODO Auto-generated method stub
		commandeFournisseurRepository.deleteById(uuid);
	}

}
