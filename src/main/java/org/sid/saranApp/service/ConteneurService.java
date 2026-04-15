package org.sid.saranApp.service;

import org.sid.saranApp.dto.ConteneurDto;

import java.util.List;

/**
 * Service pour la gestion des conteneurs de livraison
 */
public interface ConteneurService {
    
    /**
     * Crée un nouveau conteneur
     * @param conteneurDto Les données du conteneur
     * @return Le conteneur créé
     */
    ConteneurDto creerConteneur(ConteneurDto conteneurDto);
    
    /**
     * Met à jour le statut d'un conteneur
     * @param uuidConteneur L'UUID du conteneur
     * @param nouveauStatut Le nouveau statut
     * @return Le conteneur mis à jour
     */
    ConteneurDto mettreAJourStatut(String uuidConteneur, String nouveauStatut);
    
    /**
     * Récupère tous les conteneurs d'une boutique
     * @return Liste des conteneurs
     */
    List<ConteneurDto> getConteneurs();
    
    /**
     * Récupère un conteneur par son UUID
     * @param uuid L'UUID du conteneur
     * @return Le conteneur
     */
    ConteneurDto getConteneurById(String uuid);
    
    /**
     * Récupère les conteneurs d'une livraison
     * @param uuidLivraison L'UUID de la livraison
     * @return Liste des conteneurs
     */
    List<ConteneurDto> getConteneursByLivraison(String uuidLivraison);
}
