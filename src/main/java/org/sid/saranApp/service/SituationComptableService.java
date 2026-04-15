package org.sid.saranApp.service;

import org.sid.saranApp.dto.SituationComptableDto;
import org.sid.saranApp.dto.SituationClientPartenaireDto;
import org.sid.saranApp.dto.SituationFournisseurDto;

import java.util.List;

/**
 * Service pour la gestion de la situation comptable
 */
public interface SituationComptableService {
    
    /**
     * Récupère la situation comptable complète (dettes et créances)
     * @return La situation comptable
     */
    SituationComptableDto getSituationComptable();
    
    /**
     * Récupère la situation comptable d'un fournisseur
     * @param uuidFournisseur L'UUID du fournisseur
     * @return La situation du fournisseur
     */
    SituationFournisseurDto getSituationFournisseur(String uuidFournisseur);
    
    /**
     * Récupère la situation comptable d'un client partenaire
     * @param uuidClientPartenaire L'UUID du client partenaire
     * @return La situation du client partenaire
     */
    SituationClientPartenaireDto getSituationClientPartenaire(String uuidClientPartenaire);
    
    /**
     * Récupère toutes les situations des fournisseurs
     * @return Liste des situations fournisseurs
     */
    List<SituationFournisseurDto> getSituationsFournisseurs();
    
    /**
     * Récupère toutes les situations des clients partenaires
     * @return Liste des situations clients partenaires
     */
    List<SituationClientPartenaireDto> getSituationsClientsPartenaires();
}
