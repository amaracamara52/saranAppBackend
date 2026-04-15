package org.sid.saranApp.service;

import org.sid.saranApp.dto.OperationCaisseDto;
import org.sid.saranApp.dto.SituationCaisseDto;

import java.util.Date;
import java.util.List;

/**
 * Service pour la gestion des opérations de caisse
 */
public interface OperationCaisseService {
    
    /**
     * Enregistre une opération de caisse (encaissement ou décaissement)
     * @param operationDto Les données de l'opération
     * @return L'opération enregistrée
     */
    OperationCaisseDto enregistrerOperation(OperationCaisseDto operationDto);
    
    /**
     * Enregistre automatiquement une opération lors d'un paiement fournisseur
     * @param uuidPaiement L'UUID du paiement
     * @return L'opération créée
     */
    OperationCaisseDto enregistrerOperationPaiementFournisseur(String uuidPaiement);
    
    /**
     * Enregistre automatiquement une opération lors d'un versement client partenaire
     * @param uuidVersement L'UUID du versement
     * @return L'opération créée
     */
    OperationCaisseDto enregistrerOperationVersementClient(String uuidVersement);
    
    /**
     * Récupère toutes les opérations de la caisse actuelle
     * @return Liste des opérations
     */
    List<OperationCaisseDto> getOperationsCaisseActuelle();
    
    /**
     * Récupère les opérations d'une caisse journalière
     * @param uuidCaisse L'UUID de la caisse
     * @return Liste des opérations
     */
    List<OperationCaisseDto> getOperationsByCaisse(String uuidCaisse);
    
    /**
     * Récupère les opérations d'un client partenaire
     * @param uuidClientPartenaire L'UUID du client partenaire
     * @return Liste des opérations
     */
    List<OperationCaisseDto> getOperationsByClientPartenaire(String uuidClientPartenaire);
    
    /**
     * Récupère les opérations d'un fournisseur
     * @param uuidFournisseur L'UUID du fournisseur
     * @return Liste des opérations
     */
    List<OperationCaisseDto> getOperationsByFournisseur(String uuidFournisseur);
    
    /**
     * Récupère la situation de caisse (solde actuel, opérations du jour)
     * @return La situation de caisse
     */
    SituationCaisseDto getSituationCaisse();
    
    /**
     * Récupère la situation de caisse d'un client partenaire
     * @param uuidClientPartenaire L'UUID du client partenaire
     * @return La situation de caisse du client
     */
    SituationCaisseDto getSituationCaisseClientPartenaire(String uuidClientPartenaire);
    
    /**
     * Récupère la situation de caisse d'un fournisseur
     * @param uuidFournisseur L'UUID du fournisseur
     * @return La situation de caisse du fournisseur
     */
    SituationCaisseDto getSituationCaisseFournisseur(String uuidFournisseur);
}
