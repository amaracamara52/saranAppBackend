package org.sid.saranApp.service;

import org.sid.saranApp.dto.ClientPartenaireDto;

import java.util.List;

/**
 * Service pour la gestion des partenariats client-boutique
 */
public interface ClientPartenaireService {
    
    /**
     * Crée un nouveau partenariat entre un client et une boutique
     * @param clientPartenaireDto Les données du partenariat
     * @return Le partenariat créé
     */
    ClientPartenaireDto creerPartenaire(ClientPartenaireDto clientPartenaireDto);
    
    /**
     * Met à jour le statut d'un partenariat
     * @param uuidPartenaire L'UUID du partenariat
     * @param nouveauStatut Le nouveau statut (ACTIF, SUSPENDU, EXPIRE)
     * @return Le partenariat mis à jour
     */
    ClientPartenaireDto mettreAJourStatut(String uuidPartenaire, String nouveauStatut);
    
    /**
     * Vérifie si un client est partenaire d'une boutique
     * @param uuidClient L'UUID du client
     * @param uuidBoutique L'UUID de la boutique
     * @return true si le client est partenaire actif, false sinon
     */
    boolean estClientPartenaire(String uuidClient, String uuidBoutique);
    
    /**
     * Récupère tous les partenariats d'un client
     * @param uuidClient L'UUID du client
     * @return Liste des partenariats
     */
    List<ClientPartenaireDto> getPartenariatsByClient(String uuidClient);
    
    /**
     * Récupère tous les partenariats d'une boutique
     * @param uuidBoutique L'UUID de la boutique
     * @return Liste des partenariats
     */
    List<ClientPartenaireDto> getPartenariatsByBoutique(String uuidBoutique);
    
    /**
     * Récupère un partenariat par son UUID
     * @param uuid L'UUID du partenariat
     * @return Le partenariat
     */
    ClientPartenaireDto getPartenaireById(String uuid);
}
