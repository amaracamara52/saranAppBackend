package org.sid.saranApp.serviceImpl;

import org.sid.saranApp.dto.LivraisonCommandeGrossisteDto;
import org.sid.saranApp.enume.EnumTypeInstanceBoutique;
import org.sid.saranApp.model.*;
import org.sid.saranApp.repository.*;
import org.sid.saranApp.service.LivraisonCommandeGrossisteService;
import org.sid.saranApp.model.StockUniteVente;
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

/**
 * Service pour la gestion des livraisons de commandes grossistes
 */
@Service
public class LivraisonCommandeGrossisteServiceImpl implements LivraisonCommandeGrossisteService {

    private static final Logger logger = LoggerFactory.getLogger(LivraisonCommandeGrossisteServiceImpl.class);

    @Autowired
    private LivraisonCommandeGrossisteRepository livraisonCommandeGrossisteRepository;
    
    @Autowired
    private CommandeGrossisteRepository commandeGrossisteRepository;
    
    @Autowired
    private DetailCommandeGrossisteRepository detailCommandeGrossisteRepository;
    
    @Autowired
    private BoutiqueRepository boutiqueRepository;
    
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    
    @Autowired
    private UtilisateurServiceImpl utilisateurServiceImpl;
    
    @Autowired
    private ArticleRepository articleRepository;
    
    @Autowired
    private ProduitRepository produitRepository;
    
    @Autowired
    private StockUniteVenteService stockUniteVenteService;
    
    @Autowired
    private StockUniteVenteRepository stockUniteVenteRepository;
    
    @Autowired
    private TypeUniteDeVenteRepository typeUniteDeVenteRepository;

    @Override
    public LivraisonCommandeGrossisteDto creerLivraison(LivraisonCommandeGrossisteDto livraisonDto) {
        logger.info("Création d'une livraison de commande grossiste");
        
        try {
            Utilisateur utilisateur = utilisateurServiceImpl.getCurentUtilisateur();
            if (utilisateur == null || utilisateur.getBoutique() == null) {
                throw new RuntimeException("Utilisateur ou boutique non trouvé");
            }
            
            Boutique boutiqueGrossiste = utilisateur.getBoutique();
            
            // Vérifier que la boutique est bien un grossiste
            if (boutiqueGrossiste.getEnumTypeInstanceBoutique() != EnumTypeInstanceBoutique.GROSSISTE) {
                throw new IllegalArgumentException("Seuls les grossistes peuvent créer des livraisons");
            }
            
            CommandeGrossiste commandeGrossiste = commandeGrossisteRepository.findById(livraisonDto.getUuidCommandeGrossiste())
                .orElseThrow(() -> new RuntimeException("Commande grossiste non trouvée"));
            
            DetailCommandeGrossiste detailCommandeGrossiste = detailCommandeGrossisteRepository
                .findById(livraisonDto.getUuidDetailCommandeGrossiste())
                .orElseThrow(() -> new RuntimeException("Détail de commande non trouvé"));
            
            Boutique boutiqueDetaillee = boutiqueRepository.findById(livraisonDto.getUuidBoutiqueDetaillee())
                .orElseThrow(() -> new RuntimeException("Boutique détaillante non trouvée"));
            
            LivraisonCommandeGrossiste livraison = new LivraisonCommandeGrossiste();
            livraison.setUuid(UUID.randomUUID().toString().replace("-", "").toUpperCase());
            livraison.setDateLivraison(livraisonDto.getDateLivraison() != null ? livraisonDto.getDateLivraison() : new Date());
            livraison.setHeure(livraisonDto.getHeure());
            livraison.setQuantite(livraisonDto.getQuantite());
            livraison.setPrix(livraisonDto.getPrix());
            livraison.setNumeroSuivi(livraisonDto.getNumeroSuivi());
            livraison.setTransporteur(livraisonDto.getTransporteur());
            livraison.setCommandeGrossiste(commandeGrossiste);
            livraison.setDetailCommandeGrossiste(detailCommandeGrossiste);
            livraison.setBoutiqueGrossiste(boutiqueGrossiste);
            livraison.setBoutiqueDetaillee(boutiqueDetaillee);
            livraison.setUtilisateur(utilisateur);
            
            // Initialiser le statut de vérification
            livraison.setStatutVerification("EN_ATTENTE");
            livraison.setVerifiee(false);
            
            LivraisonCommandeGrossiste livraisonSave = livraisonCommandeGrossisteRepository.save(livraison);
            
            logger.info("Livraison créée avec succès: {}", livraisonSave.getUuid());
            return toDto(livraisonSave);
            
        } catch (java.lang.Exception e) {
            logger.error("Erreur lors de la création de la livraison: {}", e.getMessage(), e);
            throw new RuntimeException("Erreur lors de la création: " + e.getMessage());
        }
    }

    @Override
    public LivraisonCommandeGrossisteDto enregistrerStock(LivraisonCommandeGrossisteDto livraisonDto) {
        logger.info("Enregistrement du stock pour la livraison: {}", livraisonDto.getUuid());
        
        try {
            LivraisonCommandeGrossiste livraison = livraisonCommandeGrossisteRepository.findById(livraisonDto.getUuid())
                .orElseThrow(() -> new RuntimeException("Livraison non trouvée"));
            
            if (!livraison.isVerifiee()) {
                throw new IllegalArgumentException("La livraison doit être vérifiée avant d'enregistrer le stock");
            }
            
            DetailCommandeGrossiste detail = livraison.getDetailCommandeGrossiste();
            Article article = detail.getArticle();
            Boutique boutiqueDetaillee = livraison.getBoutiqueDetaillee();
            
            // Récupérer ou créer le produit
            List<Produit> produits = produitRepository.findByBoutique(boutiqueDetaillee);
            Produit produit = produits.stream()
                .filter(p -> p.getArticle() != null && p.getArticle().getUuid().equals(article.getUuid()))
                .findFirst()
                .orElse(null);
            
            if (produit == null) {
                produit = new Produit();
                produit.setUuid(UUID.randomUUID().toString().replace("-", "").toUpperCase());
                produit.setArticle(article);
                produit.setBoutique(boutiqueDetaillee);
                produit.setDateEnregistrement(new Date());
                produit.setLivraisonCommandeGrossiste(livraison);
                produit = produitRepository.save(produit);
                // Initialiser les stocks pour toutes les unités de vente de l'article
                stockUniteVenteService.initializeStocksForProduit(produit);
            }
            
            // Ajouter le stock
            TypeUniteDeVente unite = detail.getTypeUniteDeVente();
            List<StockUniteVenteService.TypeUniteDeVenteQuantite> unitesAvecQuantites = new ArrayList<>();
            unitesAvecQuantites.add(new StockUniteVenteService.TypeUniteDeVenteQuantite(unite, livraison.getQuantite()));
            stockUniteVenteService.addStockMultipleUnits(produit, unitesAvecQuantites);
            
            // Mettre à jour le prix dans le StockUniteVente
            List<StockUniteVente> stocks = stockUniteVenteService.getStocksForProduit(produit);
            for (StockUniteVente stock : stocks) {
                if (stock.getTypeUniteDeVente().getUuid().equals(unite.getUuid())) {
                    stock.setPrice(livraison.getPrix());
                    stockUniteVenteRepository.save(stock);
                }
            }
            
            logger.info("Stock enregistré avec succès pour la livraison: {}", livraison.getUuid());
            return toDto(livraison);
            
        } catch (java.lang.Exception e) {
            logger.error("Erreur lors de l'enregistrement du stock: {}", e.getMessage(), e);
            throw new RuntimeException("Erreur lors de l'enregistrement: " + e.getMessage());
        }
    }

    @Override
    public List<LivraisonCommandeGrossisteDto> getLivraisonsDetaillee() {
        try {
            Utilisateur utilisateur = utilisateurServiceImpl.getCurentUtilisateur();
            Boutique boutique = utilisateur.getBoutique();
            
            List<LivraisonCommandeGrossiste> livraisons = livraisonCommandeGrossisteRepository
                .findByBoutiqueDetaillee(boutique);
            
            List<LivraisonCommandeGrossisteDto> dtos = new ArrayList<>();
            for (LivraisonCommandeGrossiste livraison : livraisons) {
                dtos.add(toDto(livraison));
            }
            return dtos;
            
        } catch (java.lang.Exception e) {
            logger.error("Erreur lors de la récupération des livraisons: {}", e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    @Override
    public List<LivraisonCommandeGrossisteDto> getLivraisonsGrossiste() {
        try {
            Utilisateur utilisateur = utilisateurServiceImpl.getCurentUtilisateur();
            Boutique boutique = utilisateur.getBoutique();
            
            List<LivraisonCommandeGrossiste> livraisons = livraisonCommandeGrossisteRepository
                .findByBoutiqueGrossiste(boutique);
            
            List<LivraisonCommandeGrossisteDto> dtos = new ArrayList<>();
            for (LivraisonCommandeGrossiste livraison : livraisons) {
                dtos.add(toDto(livraison));
            }
            return dtos;
            
        } catch (java.lang.Exception e) {
            logger.error("Erreur lors de la récupération des livraisons: {}", e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    @Override
    public LivraisonCommandeGrossisteDto getLivraisonById(String uuid) {
        LivraisonCommandeGrossiste livraison = livraisonCommandeGrossisteRepository.findById(uuid)
            .orElseThrow(() -> new RuntimeException("Livraison non trouvée"));
        return toDto(livraison);
    }

    @Override
    public LivraisonCommandeGrossisteDto verifierLivraison(String uuidLivraison, String commentaire) {
        logger.info("Vérification de la livraison grossiste: {}", uuidLivraison);
        
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Utilisateur utilisateur = utilisateurRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
            
            LivraisonCommandeGrossiste livraison = livraisonCommandeGrossisteRepository.findById(uuidLivraison)
                .orElseThrow(() -> new RuntimeException("Livraison non trouvée"));
            
            if (livraison.isVerifiee()) {
                throw new IllegalArgumentException("Cette livraison a déjà été vérifiée");
            }
            
            livraison.setVerifiee(true);
            livraison.setStatutVerification("VERIFIEE");
            livraison.setDateVerification(new Date());
            livraison.setUtilisateurVerificateur(utilisateur);
            livraison.setCommentaireVerification(commentaire);
            
            LivraisonCommandeGrossiste livraisonSave = livraisonCommandeGrossisteRepository.save(livraison);
            
            // Enregistrer automatiquement le stock lors de la vérification
            try {
                enregistrerStockDepuisLivraison(livraisonSave);
                logger.info("Stock enregistré automatiquement pour la livraison vérifiée: {}", uuidLivraison);
            } catch (Exception e) {
                logger.warn("Erreur lors de l'enregistrement automatique du stock: {}", e.getMessage());
                // Ne pas faire échouer la vérification si l'enregistrement du stock échoue
            }
            
            logger.info("Livraison grossiste vérifiée avec succès: {}", uuidLivraison);
            return toDto(livraisonSave);
            
        } catch (java.lang.Exception e) {
            logger.error("Erreur lors de la vérification de la livraison: {}", e.getMessage(), e);
            throw new RuntimeException("Erreur lors de la vérification: " + e.getMessage());
        }
    }

    @Override
    public LivraisonCommandeGrossisteDto rejeterLivraison(String uuidLivraison, String commentaire) {
        logger.info("Rejet de la livraison grossiste: {}", uuidLivraison);
        
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            Utilisateur utilisateur = utilisateurRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
            
            LivraisonCommandeGrossiste livraison = livraisonCommandeGrossisteRepository.findById(uuidLivraison)
                .orElseThrow(() -> new RuntimeException("Livraison non trouvée"));
            
            if (livraison.isVerifiee() && "VERIFIEE".equals(livraison.getStatutVerification())) {
                throw new IllegalArgumentException("Cette livraison a déjà été vérifiée et ne peut pas être rejetée");
            }
            
            livraison.setVerifiee(false);
            livraison.setStatutVerification("REJETEE");
            livraison.setDateVerification(new Date());
            livraison.setUtilisateurVerificateur(utilisateur);
            livraison.setCommentaireVerification(commentaire);
            
            LivraisonCommandeGrossiste livraisonSave = livraisonCommandeGrossisteRepository.save(livraison);
            
            logger.info("Livraison grossiste rejetée: {}", uuidLivraison);
            return toDto(livraisonSave);
            
        } catch (java.lang.Exception e) {
            logger.error("Erreur lors du rejet de la livraison: {}", e.getMessage(), e);
            throw new RuntimeException("Erreur lors du rejet: " + e.getMessage());
        }
    }

    /**
     * Enregistre automatiquement le stock depuis une livraison vérifiée
     * Cette méthode est appelée automatiquement lors de la vérification d'une livraison
     */
    private void enregistrerStockDepuisLivraison(LivraisonCommandeGrossiste livraison) {
        DetailCommandeGrossiste detail = livraison.getDetailCommandeGrossiste();
        Article article = detail.getArticle();
        Boutique boutiqueDetaillee = livraison.getBoutiqueDetaillee();
        
        // Récupérer ou créer le produit
        List<Produit> produits = produitRepository.findByBoutique(boutiqueDetaillee);
        Produit produit = produits.stream()
            .filter(p -> p.getArticle() != null && p.getArticle().getUuid().equals(article.getUuid())
                && p.getLivraisonCommandeGrossiste() != null
                && p.getLivraisonCommandeGrossiste().getUuid().equals(livraison.getUuid()))
            .findFirst()
            .orElse(null);
        
        if (produit == null) {
            produit = new Produit();
            produit.setUuid(UUID.randomUUID().toString().replace("-", "").toUpperCase());
            produit.setArticle(article);
            produit.setBoutique(boutiqueDetaillee);
            produit.setDateEnregistrement(new Date());
            produit.setLivraisonCommandeGrossiste(livraison);
            produit.setUtilisateur(livraison.getUtilisateur());
            produit = produitRepository.save(produit);
            
            // Initialiser les stocks pour toutes les unités de vente de l'article
            stockUniteVenteService.initializeStocksForProduit(produit);
        }
        
        // Ajouter le stock
        TypeUniteDeVente unite = detail.getTypeUniteDeVente();
        if (unite == null) {
            throw new RuntimeException("L'unité de vente n'est pas définie pour ce détail de commande");
        }
        
        List<StockUniteVenteService.TypeUniteDeVenteQuantite> unitesAvecQuantites = new ArrayList<>();
        unitesAvecQuantites.add(new StockUniteVenteService.TypeUniteDeVenteQuantite(unite, livraison.getQuantite()));
        stockUniteVenteService.addStockMultipleUnits(produit, unitesAvecQuantites);
        
        // Mettre à jour le prix dans le StockUniteVente
        List<StockUniteVente> stocks = stockUniteVenteService.getStocksForProduit(produit);
        for (StockUniteVente stock : stocks) {
            if (stock.getTypeUniteDeVente().getUuid().equals(unite.getUuid())) {
                stock.setPrice(livraison.getPrix());
                stockUniteVenteRepository.save(stock);
            }
        }
        
        produitRepository.save(produit);
        logger.info("Stock enregistré pour le produit {} depuis la livraison grossiste {}", article.getLibelle(), livraison.getUuid());
    }

    /**
     * Convertit une entité en DTO
     */
    private LivraisonCommandeGrossisteDto toDto(LivraisonCommandeGrossiste livraison) {
        LivraisonCommandeGrossisteDto dto = new LivraisonCommandeGrossisteDto();
        dto.setUuid(livraison.getUuid());
        dto.setDateLivraison(livraison.getDateLivraison());
        dto.setHeure(livraison.getHeure());
        dto.setQuantite(livraison.getQuantite());
        dto.setPrix(livraison.getPrix());
        dto.setNumeroSuivi(livraison.getNumeroSuivi());
        dto.setTransporteur(livraison.getTransporteur());
        
        if (livraison.getCommandeGrossiste() != null) {
            dto.setUuidCommandeGrossiste(livraison.getCommandeGrossiste().getUuid());
        }
        
        if (livraison.getDetailCommandeGrossiste() != null) {
            dto.setUuidDetailCommandeGrossiste(livraison.getDetailCommandeGrossiste().getUuid());
            if (livraison.getDetailCommandeGrossiste().getArticle() != null) {
                dto.setLibelleArticle(livraison.getDetailCommandeGrossiste().getArticle().getLibelle());
            }
        }
        
        if (livraison.getBoutiqueDetaillee() != null) {
            dto.setUuidBoutiqueDetaillee(livraison.getBoutiqueDetaillee().getUuid());
            dto.setLibelleBoutiqueDetaillee(livraison.getBoutiqueDetaillee().getLibelleBoutique());
        }
        
        if (livraison.getBoutiqueGrossiste() != null) {
            dto.setUuidBoutiqueGrossiste(livraison.getBoutiqueGrossiste().getUuid());
            dto.setLibelleBoutiqueGrossiste(livraison.getBoutiqueGrossiste().getLibelleBoutique());
        }
        
        // Gérer le statut de vérification
        dto.setStatutVerification(livraison.getStatutVerification());
        dto.setVerifiee(livraison.isVerifiee());
        dto.setDateVerification(livraison.getDateVerification());
        dto.setCommentaireVerification(livraison.getCommentaireVerification());
        
        if (livraison.getUtilisateurVerificateur() != null) {
            dto.setUuidUtilisateurVerificateur(livraison.getUtilisateurVerificateur().getUuid());
            dto.setNomUtilisateurVerificateur(livraison.getUtilisateurVerificateur().getUsername());
        }
        
        return dto;
    }
}
