package org.sid.saranApp.service;

import org.sid.saranApp.dto.LivraisonCommandeGrossisteDto;

import java.util.List;

/**
 * Service pour la gestion des livraisons de commandes grossistes
 */
public interface LivraisonCommandeGrossisteService {
    
    /**
     * Crée une livraison pour une commande grossiste
     * @param livraisonDto Les données de la livraison
     * @return La livraison créée
     */
    LivraisonCommandeGrossisteDto creerLivraison(LivraisonCommandeGrossisteDto livraisonDto);
    
    /**
     * Enregistre le stock à partir d'une livraison grossiste
     * @param livraisonDto Les données de la livraison
     * @return La livraison avec stock enregistré
     */
    LivraisonCommandeGrossisteDto enregistrerStock(LivraisonCommandeGrossisteDto livraisonDto);
    
    /**
     * Récupère toutes les livraisons d'un détaillant
     * @return Liste des livraisons
     */
    List<LivraisonCommandeGrossisteDto> getLivraisonsDetaillee();
    
    /**
     * Récupère toutes les livraisons envoyées par un grossiste
     * @return Liste des livraisons
     */
    List<LivraisonCommandeGrossisteDto> getLivraisonsGrossiste();
    
    /**
     * Récupère une livraison par son UUID
     * @param uuid L'UUID de la livraison
     * @return La livraison
     */
    LivraisonCommandeGrossisteDto getLivraisonById(String uuid);
    
    /**
     * Vérifie une livraison de commande grossiste
     * @param uuidLivraison L'UUID de la livraison
     * @param commentaire Commentaire de vérification (optionnel)
     * @return La livraison vérifiée
     */
    LivraisonCommandeGrossisteDto verifierLivraison(String uuidLivraison, String commentaire);
    
    /**
     * Rejette une livraison de commande grossiste
     * @param uuidLivraison L'UUID de la livraison
     * @param commentaire Commentaire de rejet
     * @return La livraison rejetée
     */
    LivraisonCommandeGrossisteDto rejeterLivraison(String uuidLivraison, String commentaire);
}
