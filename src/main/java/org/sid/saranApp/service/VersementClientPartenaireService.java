package org.sid.saranApp.service;

import org.sid.saranApp.dto.VersementClientPartenaireDto;

import java.util.List;

/**
 * Service pour la gestion des versements des clients partenaires
 */
public interface VersementClientPartenaireService {
    
    /**
     * Enregistre un versement d'un client partenaire
     * @param versementDto Les données du versement
     * @return Le versement enregistré
     */
    VersementClientPartenaireDto enregistrerVersement(VersementClientPartenaireDto versementDto);
    
    /**
     * Récupère tous les versements d'un client partenaire
     * @param uuidClientPartenaire L'UUID du client partenaire
     * @return Liste des versements
     */
    List<VersementClientPartenaireDto> getVersementsByClientPartenaire(String uuidClientPartenaire);
    
    /**
     * Récupère tous les versements d'une commande vente
     * @param uuidCommande L'UUID de la commande
     * @return Liste des versements
     */
    List<VersementClientPartenaireDto> getVersementsByCommande(String uuidCommande);
    
    /**
     * Récupère un versement par son UUID
     * @param uuid L'UUID du versement
     * @return Le versement
     */
    VersementClientPartenaireDto getVersementById(String uuid);
}
