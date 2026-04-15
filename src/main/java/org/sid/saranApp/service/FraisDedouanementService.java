package org.sid.saranApp.service;

import org.sid.saranApp.dto.FraisDedouanementDto;

import java.util.List;

/**
 * Service pour la gestion des frais de dédouanement
 */
public interface FraisDedouanementService {
    
    /**
     * Crée un nouveau frais de dédouanement
     * @param fraisDto Les données du frais
     * @return Le frais créé
     */
    FraisDedouanementDto creerFrais(FraisDedouanementDto fraisDto);
    
    /**
     * Met à jour le statut de paiement d'un frais
     * @param uuidFrais L'UUID du frais
     * @param statutPaiement Le nouveau statut de paiement
     * @return Le frais mis à jour
     */
    FraisDedouanementDto mettreAJourStatutPaiement(String uuidFrais, String statutPaiement);
    
    /**
     * Récupère tous les frais d'une boutique
     * @return Liste des frais
     */
    List<FraisDedouanementDto> getFrais();
    
    /**
     * Récupère un frais par son UUID
     * @param uuid L'UUID du frais
     * @return Le frais
     */
    FraisDedouanementDto getFraisById(String uuid);
    
    /**
     * Récupère les frais d'une livraison
     * @param uuidLivraison L'UUID de la livraison
     * @return Liste des frais
     */
    List<FraisDedouanementDto> getFraisByLivraison(String uuidLivraison);
    
    /**
     * Récupère les frais d'un conteneur
     * @param uuidConteneur L'UUID du conteneur
     * @return Liste des frais
     */
    List<FraisDedouanementDto> getFraisByConteneur(String uuidConteneur);
}
