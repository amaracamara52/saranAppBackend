package org.sid.saranApp.serviceImpl;

import org.sid.saranApp.dto.CommandeFournisseurDto;
import org.sid.saranApp.dto.DetailCommandeFournisseurDto;
import org.sid.saranApp.enume.StatusCommandeFournisseurEnum;
import org.sid.saranApp.model.*;
import org.sid.saranApp.repository.BoutiqueRepository;
import org.sid.saranApp.repository.CommandeFournisseurRepository;
import org.sid.saranApp.repository.DetailCommandeFournisseurRepository;
import org.sid.saranApp.repository.ProduitRepository;
import org.sid.saranApp.service.ApprovisionnementAutomatiqueService;
import org.sid.saranApp.service.CommandeFournisseurService;
import org.sid.saranApp.service.StockUniteVenteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ApprovisionnementAutomatiqueServiceImpl implements ApprovisionnementAutomatiqueService {

    private static final Logger logger = LoggerFactory.getLogger(ApprovisionnementAutomatiqueServiceImpl.class);

    @Autowired
    private BoutiqueRepository boutiqueRepository;

    @Autowired
    private ProduitRepository produitRepository;

    @Autowired
    private CommandeFournisseurService commandeFournisseurService;

    @Autowired
    private CommandeFournisseurRepository commandeFournisseurRepository;

    @Autowired
    private DetailCommandeFournisseurRepository detailCommandeFournisseurRepository;

    @Autowired
    private StockUniteVenteService stockUniteVenteService;

    /**
     * Vérifie et déclenche l'approvisionnement automatique pour une boutique
     * @param boutique La boutique à vérifier (ne doit pas être null)
     * @throws IllegalArgumentException si boutique est null
     */
    @Override
    public void verifierEtDeclencherApprovisionnement(Boutique boutique) {
        if (boutique == null) {
            throw new IllegalArgumentException("La boutique ne peut pas être null");
        }
        
        // Vérifier si l'approvisionnement automatique est activé pour cette boutique
        if (!boutique.isApprovisionnementAutomatique()) {
            logger.debug("Approvisionnement automatique désactivé pour la boutique: {}", 
                boutique.getLibelleBoutique());
            return;
        }

        logger.info("Vérification de l'approvisionnement automatique pour la boutique: {}", 
            boutique.getLibelleBoutique());

        try {
        // Trouver les produits qui ont atteint le seuil d'alerte
        List<Produit> produitsAReapprovisionner = trouverProduitsAReapprovisionner(boutique);

            if (produitsAReapprovisionner == null || produitsAReapprovisionner.isEmpty()) {
                logger.info("Aucun produit à réapprovisionner pour la boutique: {}", 
                    boutique.getLibelleBoutique());
            return;
        }

        // Créer des commandes automatiques pour chaque produit
        for (Produit produit : produitsAReapprovisionner) {
            try {
                    if (produit == null) {
                        logger.warn("Produit null trouvé dans la liste, ignoré");
                        continue;
                    }
                    
                creerCommandeAutomatique(produit, boutique.getQuantiteACommander());
                logger.info("Commande automatique créée pour le produit: {} - Quantité: {}", 
                        produit.getArticle() != null ? produit.getArticle().getLibelle() : "N/A", 
                        boutique.getQuantiteACommander());
                } catch (IllegalArgumentException e) {
                    logger.error("Paramètre invalide lors de la création de la commande automatique pour le produit {}: {}", 
                        produit != null ? produit.getUuid() : "N/A", e.getMessage());
            } catch (Exception e) {
                    logger.error("Erreur lors de la création de la commande automatique pour le produit {}: {}", 
                        produit != null && produit.getArticle() != null ? produit.getArticle().getLibelle() : "N/A", 
                        e.getMessage(), e);
            }
            }
        } catch (Exception e) {
            logger.error("Erreur lors de la vérification de l'approvisionnement pour la boutique {}: {}", 
                boutique.getLibelleBoutique(), e.getMessage(), e);
        }
    }

    @Override
    public void verifierEtDeclencherApprovisionnementPourToutesBoutiques() {
        logger.info("Début de la vérification de l'approvisionnement automatique pour toutes les boutiques");
        
        List<Boutique> boutiques = boutiqueRepository.findAll();
        
        for (Boutique boutique : boutiques) {
            try {
                verifierEtDeclencherApprovisionnement(boutique);
            } catch (Exception e) {
                logger.error("Erreur lors de la vérification de l'approvisionnement pour la boutique: {}", 
                    boutique.getLibelleBoutique(), e);
            }
        }
        
        logger.info("Fin de la vérification de l'approvisionnement automatique");
    }

    /**
     * Trouve les produits qui ont atteint le seuil d'alerte et nécessitent un réapprovisionnement
     * @param boutique La boutique pour laquelle vérifier les produits
     * @return Liste des produits à réapprovisionner
     * @throws IllegalArgumentException si boutique est null
     */
    @Override
    public List<Produit> trouverProduitsAReapprovisionner(Boutique boutique) {
        if (boutique == null) {
            throw new IllegalArgumentException("La boutique ne peut pas être null");
        }
        
        List<Produit> produitsAReapprovisionner = new ArrayList<>();
        
        try {
        // Récupérer tous les produits de la boutique
        List<Produit> produits = produitRepository.findByBoutique(boutique);
            
            if (produits == null || produits.isEmpty()) {
                logger.debug("Aucun produit trouvé pour la boutique: {}", boutique.getLibelleBoutique());
                return produitsAReapprovisionner;
            }
        
        for (Produit produit : produits) {
                try {
                    // Calculer la quantité totale en unité de base
                    int quantiteTotale = stockUniteVenteService.calculateTotalBaseQuantity(produit);
                    
            // Vérifier si le stock est inférieur ou égal au seuil d'alerte
                    if (quantiteTotale <= boutique.getSeuilAlerteStock()) {
                produitsAReapprovisionner.add(produit);
                logger.info("Produit à réapprovisionner: {} - Stock actuel: {} - Seuil d'alerte: {}", 
                            produit.getArticle() != null ? produit.getArticle().getLibelle() : "N/A", 
                            quantiteTotale, boutique.getSeuilAlerteStock());
                    }
                } catch (Exception e) {
                    logger.error("Erreur lors du calcul du stock pour le produit {}: {}", 
                        produit.getUuid(), e.getMessage());
                    // Continuer avec le produit suivant
                }
            }
        } catch (Exception e) {
            logger.error("Erreur lors de la récupération des produits pour la boutique {}: {}", 
                boutique.getLibelleBoutique(), e.getMessage());
        }
        
        return produitsAReapprovisionner;
    }

    /**
     * Crée une commande automatique pour un produit
     * @param produit Le produit pour lequel créer la commande (ne doit pas être null)
     * @param quantiteACommander La quantité à commander (doit être > 0)
     * @throws IllegalArgumentException si produit est null ou quantiteACommander <= 0
     * @throws RuntimeException si une erreur survient lors de la création de la commande
     */
    @Override
    public void creerCommandeAutomatique(Produit produit, int quantiteACommander) {
        if (produit == null) {
            throw new IllegalArgumentException("Le produit ne peut pas être null");
        }
        if (quantiteACommander <= 0) {
            throw new IllegalArgumentException("La quantité à commander doit être supérieure à 0");
        }
        
        try {
            // Vérifier que le produit a les informations nécessaires
            if (produit.getBoutique() == null) {
                throw new IllegalArgumentException("Le produit doit avoir une boutique associée");
            }
            if (produit.getFournisseur() == null) {
                throw new IllegalArgumentException("Le produit doit avoir un fournisseur associé");
            }
            if (produit.getArticle() == null) {
                throw new IllegalArgumentException("Le produit doit avoir un article associé");
            }
            
            // Créer une nouvelle commande fournisseur
            CommandeFournisseur commandeFournisseur = new CommandeFournisseur();
            commandeFournisseur.setUuid(UUID.randomUUID().toString().replace("-", "").toUpperCase());
            commandeFournisseur.setRefCommande("AUTO-" + System.currentTimeMillis());
            commandeFournisseur.setCommandeFournisseurEnum(StatusCommandeFournisseurEnum.EN_COURS);
            commandeFournisseur.setPaie(false);
            commandeFournisseur.setValeurMarchandise("");
            commandeFournisseur.setBoutique(produit.getBoutique());
            commandeFournisseur.setFournisseur(produit.getFournisseur());
            commandeFournisseur.setUtilisateur(produit.getUtilisateur());
            
            // Sauvegarder la commande
            commandeFournisseur = commandeFournisseurRepository.save(commandeFournisseur);
            
            // Créer le détail de la commande
            DetailCommandeFournisseur detailCommande = new DetailCommandeFournisseur();
            detailCommande.setUuid(UUID.randomUUID().toString().replace("-", "").toUpperCase());
            detailCommande.setCommandeFournisseur(commandeFournisseur);
            detailCommande.setArticle(produit.getArticle());
            
            // Récupérer le prix d'achat depuis StockUniteVente (prix de l'unité de base)
            double prixAchat = 0.0;
            try {
                List<org.sid.saranApp.model.StockUniteVente> stocks = stockUniteVenteService.getStocksForProduit(produit);
                if (stocks != null && !stocks.isEmpty()) {
                    // Prendre le prix de la première unité disponible (ou chercher l'unité de base)
                    org.sid.saranApp.model.StockUniteVente premierStock = stocks.get(0);
                    if (premierStock != null) {
                        prixAchat = premierStock.getPrice();
                    }
                }
            } catch (Exception e) {
                logger.error("Erreur lors de la récupération du prix d'achat pour le produit {}: {}", 
                    produit.getArticle().getLibelle(), e.getMessage());
                // Utiliser un prix par défaut de 0 si erreur
            }
            
            detailCommande.setPrixAchat(prixAchat);
            detailCommande.setQuantite(quantiteACommander);
            detailCommande.setUnite("PIECE"); // Unité de base par défaut
            detailCommande.setBoutique(produit.getBoutique());
            detailCommande.setUtilisateur(produit.getUtilisateur());
            
            // Sauvegarder le détail
            detailCommandeFournisseurRepository.save(detailCommande);
            
            logger.info("Commande automatique créée avec succès - Référence: {} - Produit: {} - Quantité: {}", 
                commandeFournisseur.getRefCommande(), produit.getArticle().getLibelle(), quantiteACommander);
                
        } catch (Exception e) {
            logger.error("Erreur lors de la création de la commande automatique", e);
            throw new RuntimeException("Impossible de créer la commande automatique", e);
        }
    }

    /**
     * Méthode programmée pour vérifier l'approvisionnement automatique toutes les heures
     */
    @Scheduled(fixedRate = 3600000) // 1 heure = 3600000 ms
    public void verifierApprovisionnementAutomatique() {
        logger.info("Exécution de la vérification automatique de l'approvisionnement");
        verifierEtDeclencherApprovisionnementPourToutesBoutiques();
    }

    // Exemple concret de conversion Date -> LocalDate à utiliser pour les méthodes du repository qui attendent un LocalDate :
    // Date date = new Date();
    // LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    // produitRepository.findProduitPerime(localDate, boutique.getUuid());
} 