package org.sid.saranApp.serviceImpl;

import org.sid.saranApp.dto.LivraisonCommandeFournisseurDto;
import org.sid.saranApp.dto.PageDataDto;
import org.sid.saranApp.mapper.Mapper;
import org.sid.saranApp.model.*;
import org.sid.saranApp.repository.*;
import org.sid.saranApp.service.LivraisonCommandeFournisseurService;
import org.sid.saranApp.service.StockUniteVenteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class LivraisonCommandeFournisseurServiceImpl implements LivraisonCommandeFournisseurService {

	@Autowired
	private LivraisonCommandeFournisseurRepository livraisonCommandeFournisseurRepository;
	@Autowired
	private BoutiqueRepository boutiqueRepository;
	@Autowired
	private DetailCommandeFournisseurRepository detailCommandeFournisseurRepository;
	@Autowired
	private CommandeFournisseurRepository commandeFournisseurRepository;
	@Autowired
	private UtilisateurRepository utilisateurRepository;
	@Autowired
	private ProduitRepository produitRepository;
	@Autowired
	private TypeUniteDeVenteRepository typeUniteDeVenteRepository;
	@Autowired
	private StockUniteVenteService stockUniteVenteService;
	@Autowired
	private StockUniteVenteRepository stockUniteVenteRepository;

	Logger logger = LoggerFactory.getLogger(LivraisonCommandeFournisseurServiceImpl.class);

	@Override
	public LivraisonCommandeFournisseurDto addLivraisonCommandeFournisseur(
			LivraisonCommandeFournisseurDto livraisonCommandeFournisseurDto) {
		// TODO Auto-generated method stub
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		logger.info("hello {}", auth.getName());
		LivraisonCommandeFournisseur livraisonCommandeFournisseur = new LivraisonCommandeFournisseur();
		Boutique boutique = boutiqueRepository.findById(livraisonCommandeFournisseurDto.getUuidBoutique())
				.orElseThrow(null);
		Utilisateur utilisateur = utilisateurRepository.findByEmail(auth.getName()).orElseThrow(null);
		DetailCommandeFournisseur detailCommandeFournisseur = detailCommandeFournisseurRepository
				.findById(livraisonCommandeFournisseurDto.getUuidDetailCommandeFournisseur()).orElseThrow(null);
		CommandeFournisseur commandeFournisseur = commandeFournisseurRepository
				.findById(livraisonCommandeFournisseurDto.getUuidCommandeFournisseur()).orElseThrow(null);
		livraisonCommandeFournisseur.setDateLivraison(livraisonCommandeFournisseurDto.getDateLivraison());
		livraisonCommandeFournisseur.setHeure(livraisonCommandeFournisseurDto.getHeure());
		livraisonCommandeFournisseur.setPrix(livraisonCommandeFournisseurDto.getPrix());
		livraisonCommandeFournisseur.setQuantite(livraisonCommandeFournisseurDto.getQuantite());
		livraisonCommandeFournisseur.setBoutique(boutique);
		livraisonCommandeFournisseur.setUtilisateur(utilisateur);
		livraisonCommandeFournisseur.setCommandeFournisseur(commandeFournisseur);
		livraisonCommandeFournisseur.setDetailCommandeFournisseur(detailCommandeFournisseur);
		LivraisonCommandeFournisseur livraisonCommandeFournisseurSave = livraisonCommandeFournisseurRepository
				.save(livraisonCommandeFournisseur);
		return Mapper.toLivraisonCommandeFournisseurDto(livraisonCommandeFournisseurSave);
	}

	@Override
	public void deleteLivraisonCommandeFournisseur(String uuid) {
		// TODO Auto-generated method stub
		LivraisonCommandeFournisseur livraisonCommandeFournisseur = livraisonCommandeFournisseurRepository
				.findById(uuid).orElseThrow(null);
		livraisonCommandeFournisseurRepository.delete(livraisonCommandeFournisseur);

	}

	@Override
	public List<LivraisonCommandeFournisseurDto> findAll() {
		// TODO Auto-generated method stub
		List<LivraisonCommandeFournisseur> livraisonCommandeFournisseurs = livraisonCommandeFournisseurRepository
				.findAll();
		List<LivraisonCommandeFournisseurDto> livraisonCommandeFournisseurDtos = new ArrayList<LivraisonCommandeFournisseurDto>();
		livraisonCommandeFournisseurs.forEach(livraisonCommandeFournisseur -> livraisonCommandeFournisseurDtos
				.add(Mapper.toLivraisonCommandeFournisseurDto(livraisonCommandeFournisseur)));
		return livraisonCommandeFournisseurDtos;
	}

	@Override
	public LivraisonCommandeFournisseurDto getLivraisonCommandeFournisseur(String uuid) {
		// TODO Auto-generated method stub
		LivraisonCommandeFournisseur livraisonCommandeFournisseur = livraisonCommandeFournisseurRepository
				.findById(uuid).orElseThrow(null);
		return Mapper.toLivraisonCommandeFournisseurDto(livraisonCommandeFournisseur);
	}

	@Override
	public LivraisonCommandeFournisseurDto updateLivraisonCommandeFournisseur(
			LivraisonCommandeFournisseurDto livraisonCommandeFournisseurDto, String uuid) {
		// TODO Auto-generated method stub
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		LivraisonCommandeFournisseur livraisonCommandeFournisseur = livraisonCommandeFournisseurRepository
				.findById(uuid).orElseThrow(null);
		Boutique boutique = boutiqueRepository.findById(livraisonCommandeFournisseurDto.getUuidBoutique())
				.orElseThrow(null);
		CommandeFournisseur commandeFournisseur = commandeFournisseurRepository
				.findById(livraisonCommandeFournisseurDto.getUuidCommandeFournisseur()).orElseThrow(null);
		Utilisateur utilisateur = utilisateurRepository.findByEmail(auth.getName()).orElseThrow(null);
		DetailCommandeFournisseur detailCommandeFournisseur = detailCommandeFournisseurRepository
				.findById(livraisonCommandeFournisseurDto.getUuidDetailCommandeFournisseur()).orElseThrow(null);
		livraisonCommandeFournisseur.setHeure(livraisonCommandeFournisseurDto.getHeure());
		livraisonCommandeFournisseur.setPrix(livraisonCommandeFournisseurDto.getPrix());
		livraisonCommandeFournisseur.setQuantite(livraisonCommandeFournisseurDto.getQuantite());
		livraisonCommandeFournisseur.setDateLivraison(livraisonCommandeFournisseurDto.getDateLivraison());
		livraisonCommandeFournisseur.setBoutique(boutique);
		livraisonCommandeFournisseur.setUtilisateur(utilisateur);
		livraisonCommandeFournisseur.setCommandeFournisseur(commandeFournisseur);
		livraisonCommandeFournisseur.setDetailCommandeFournisseur(detailCommandeFournisseur);
		LivraisonCommandeFournisseur livraisonCommandeFournisseurSave = livraisonCommandeFournisseurRepository
				.save(livraisonCommandeFournisseur);
		return Mapper.toLivraisonCommandeFournisseurDto(livraisonCommandeFournisseurSave);
	}

	@Override
	public PageDataDto<LivraisonCommandeFournisseurDto> listeLivraisonCommandeFournisseurs(int page, int size,
			String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(String uuid) {
		// TODO Auto-generated method stub
		livraisonCommandeFournisseurRepository.deleteById(uuid);
	}

	@Override
	public LivraisonCommandeFournisseurDto verifierLivraison(String uuidLivraison, String commentaire) {
		logger.info("Vérification de la livraison: {}", uuidLivraison);
		
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			Utilisateur utilisateur = utilisateurRepository.findByEmail(auth.getName())
				.orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
			
			LivraisonCommandeFournisseur livraison = livraisonCommandeFournisseurRepository.findById(uuidLivraison)
				.orElseThrow(() -> new RuntimeException("Livraison non trouvée"));
			
			if (livraison.isVerifiee()) {
				throw new IllegalArgumentException("Cette livraison a déjà été vérifiée");
			}
			
			livraison.setVerifiee(true);
			livraison.setStatutVerification("VERIFIEE");
			livraison.setDateVerification(new java.util.Date());
			livraison.setUtilisateurVerificateur(utilisateur);
			livraison.setCommentaireVerification(commentaire);
			
			LivraisonCommandeFournisseur livraisonSave = livraisonCommandeFournisseurRepository.save(livraison);
			
			// Enregistrer automatiquement le stock lors de la vérification
			try {
				enregistrerStockDepuisLivraison(livraisonSave);
				logger.info("Stock enregistré automatiquement pour la livraison vérifiée: {}", uuidLivraison);
			} catch (Exception e) {
				logger.warn("Erreur lors de l'enregistrement automatique du stock: {}", e.getMessage());
				// Ne pas faire échouer la vérification si l'enregistrement du stock échoue
			}
			
			logger.info("Livraison vérifiée avec succès: {}", uuidLivraison);
			return Mapper.toLivraisonCommandeFournisseurDto(livraisonSave);
			
		} catch (java.lang.Exception e) {
			logger.error("Erreur lors de la vérification de la livraison: {}", e.getMessage(), e);
			throw new RuntimeException("Erreur lors de la vérification: " + e.getMessage());
		}
	}

	@Override
	public LivraisonCommandeFournisseurDto rejeterLivraison(String uuidLivraison, String commentaire) {
		logger.info("Rejet de la livraison: {}", uuidLivraison);
		
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			Utilisateur utilisateur = utilisateurRepository.findByEmail(auth.getName())
				.orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
			
			LivraisonCommandeFournisseur livraison = livraisonCommandeFournisseurRepository.findById(uuidLivraison)
				.orElseThrow(() -> new RuntimeException("Livraison non trouvée"));
			
			if (livraison.isVerifiee() && "VERIFIEE".equals(livraison.getStatutVerification())) {
				throw new IllegalArgumentException("Cette livraison a déjà été vérifiée et ne peut pas être rejetée");
			}
			
			livraison.setVerifiee(false);
			livraison.setStatutVerification("REJETEE");
			livraison.setDateVerification(new java.util.Date());
			livraison.setUtilisateurVerificateur(utilisateur);
			livraison.setCommentaireVerification(commentaire);
			
			LivraisonCommandeFournisseur livraisonSave = livraisonCommandeFournisseurRepository.save(livraison);
			
			logger.info("Livraison rejetée: {}", uuidLivraison);
			return Mapper.toLivraisonCommandeFournisseurDto(livraisonSave);
			
		} catch (java.lang.Exception e) {
			logger.error("Erreur lors du rejet de la livraison: {}", e.getMessage(), e);
			throw new RuntimeException("Erreur lors du rejet: " + e.getMessage());
		}
	}

	/**
	 * Enregistre automatiquement le stock depuis une livraison vérifiée
	 * Cette méthode est appelée automatiquement lors de la vérification d'une livraison
	 */
	private void enregistrerStockDepuisLivraison(LivraisonCommandeFournisseur livraison) {
		DetailCommandeFournisseur detail = livraison.getDetailCommandeFournisseur();
		Article article = detail.getArticle();
		CommandeFournisseur commande = livraison.getCommandeFournisseur();
		Boutique boutique = livraison.getBoutique();
		
		// Vérifier si un produit existe déjà pour cette livraison
		List<Produit> produits = produitRepository.findByBoutique(boutique);
		Produit produit = produits.stream()
			.filter(p -> p.getArticle() != null && p.getArticle().getUuid().equals(article.getUuid()) 
				&& p.getLivraisonCommandeFournisseur() != null 
				&& p.getLivraisonCommandeFournisseur().getUuid().equals(livraison.getUuid()))
			.findFirst()
			.orElse(null);
		
		// Si le produit n'existe pas, le créer
		if (produit == null) {
			produit = new Produit();
			produit.setUuid(UUID.randomUUID().toString().replace("-", "").toUpperCase());
			produit.setArticle(article);
			produit.setFournisseur(commande.getFournisseur());
			produit.setBoutique(boutique);
			produit.setUtilisateur(livraison.getUtilisateur());
			produit.setDateEnregistrement(new Date());
			produit.setLivraisonCommandeFournisseur(livraison);
			produit = produitRepository.save(produit);
			
			// Initialiser les stocks pour toutes les unités de vente de l'article
			stockUniteVenteService.initializeStocksForProduit(produit);
		}
		
		// Trouver ou utiliser l'unité de vente
		TypeUniteDeVente typeUniteDeVente = null;
		if (detail.getTypeUniteDeVente() != null) {
			typeUniteDeVente = detail.getTypeUniteDeVente();
		} else {
			// Chercher l'unité par nom (fallback)
			List<TypeUniteDeVente> unites = typeUniteDeVenteRepository.findByArticle(article);
			String uniteNom = detail.getUnite();
			if (uniteNom != null) {
				typeUniteDeVente = unites.stream()
					.filter(u -> u.getTypeUniteEnum().name().equalsIgnoreCase(uniteNom))
					.findFirst()
					.orElse(null);
			}
			
			// Si l'unité n'existe pas, utiliser la première unité disponible ou créer une unité de base
			if (typeUniteDeVente == null) {
				if (!unites.isEmpty()) {
					typeUniteDeVente = unites.get(0);
				} else {
					typeUniteDeVente = new TypeUniteDeVente();
					typeUniteDeVente.setArticle(article);
					typeUniteDeVente.setUnite(1);
					typeUniteDeVente.setTypeUniteEnum(org.sid.saranApp.enume.TypeUniteEnum.PIECE);
					typeUniteDeVente.setPrice(0d);
					typeUniteDeVente = typeUniteDeVenteRepository.save(typeUniteDeVente);
				}
			}
		}
		
		// Ajouter le stock pour l'unité de vente utilisée
		int quantiteLivree = livraison.getQuantite();
		List<StockUniteVenteService.TypeUniteDeVenteQuantite> unitesAvecQuantites = new ArrayList<>();
		unitesAvecQuantites.add(new StockUniteVenteService.TypeUniteDeVenteQuantite(typeUniteDeVente, quantiteLivree));
		
		stockUniteVenteService.addStockMultipleUnits(produit, unitesAvecQuantites);
		
		// Mettre à jour le prix dans StockUniteVente
		StockUniteVente stockUniteVente = stockUniteVenteRepository
			.findByProduitAndTypeUniteDeVente(produit, typeUniteDeVente)
			.orElse(null);
		
		if (stockUniteVente != null) {
			// Calculer le prix unitaire selon l'unité
			double prixUnitaire = livraison.getPrix();
			if (typeUniteDeVente.getUnite() > 1) {
				// Si c'est une unité composite (ex: CARTON), diviser par le facteur
				prixUnitaire = livraison.getPrix() / typeUniteDeVente.getUnite();
			}
			stockUniteVente.setPrice(prixUnitaire);
			stockUniteVenteRepository.save(stockUniteVente);
		}
		
		produitRepository.save(produit);
		logger.info("Stock enregistré pour le produit {} depuis la livraison {}", article.getLibelle(), livraison.getUuid());
	}

}
