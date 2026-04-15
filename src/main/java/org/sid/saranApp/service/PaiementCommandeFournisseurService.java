package org.sid.saranApp.service;

import org.sid.saranApp.dto.PaiementCommandeFournisseurDto;

import java.util.List;

/**
 * Service pour la gestion des paiements des commandes fournisseur
 */
public interface PaiementCommandeFournisseurService {
    
    /**
     * Enregistre un paiement pour une commande fournisseur
     * @param paiementDto Les données du paiement
     * @return Le paiement enregistré
     */
    PaiementCommandeFournisseurDto enregistrerPaiement(PaiementCommandeFournisseurDto paiementDto);
    
    /**
     * Récupère tous les paiements d'une commande fournisseur
     * @param uuidCommande L'UUID de la commande
     * @return Liste des paiements
     */
    List<PaiementCommandeFournisseurDto> getPaiementsByCommande(String uuidCommande);
    
    /**
     * Récupère tous les paiements d'un fournisseur
     * @param uuidFournisseur L'UUID du fournisseur
     * @return Liste des paiements
     */
    List<PaiementCommandeFournisseurDto> getPaiementsByFournisseur(String uuidFournisseur);
    
    /**
     * Récupère un paiement par son UUID
     * @param uuid L'UUID du paiement
     * @return Le paiement
     */
    PaiementCommandeFournisseurDto getPaiementById(String uuid);
}
