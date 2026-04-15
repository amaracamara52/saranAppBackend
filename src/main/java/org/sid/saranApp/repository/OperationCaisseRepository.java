package org.sid.saranApp.repository;

import org.sid.saranApp.enume.EnumTypeTransaction;
import org.sid.saranApp.model.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

/**
 * Repository pour la gestion des opérations de caisse
 */
public interface OperationCaisseRepository extends JpaRepository<OperationCaisse, String> {
    
    /**
     * Récupère toutes les opérations d'une caisse journalière
     */
    List<OperationCaisse> findByCaisseJournaliere(CaisseJournaliere caisseJournaliere);
    
    /**
     * Récupère toutes les opérations d'une boutique
     */
    List<OperationCaisse> findByBoutique(Boutique boutique);
    
    /**
     * Récupère les opérations par type
     */
    List<OperationCaisse> findByTypeOperation(EnumTypeTransaction typeOperation);
    
    /**
     * Récupère les opérations d'une caisse par type
     */
    List<OperationCaisse> findByCaisseJournaliereAndTypeOperation(CaisseJournaliere caisse, EnumTypeTransaction type);
    
    /**
     * Récupère les opérations d'un client partenaire
     */
    List<OperationCaisse> findByClientPartenaire(ClientPartenaire clientPartenaire);
    
    /**
     * Récupère les opérations d'un fournisseur
     */
    List<OperationCaisse> findByFournisseur(Fournisseur fournisseur);
    
    /**
     * Récupère les opérations entre deux dates
     */
    List<OperationCaisse> findByDateOperationBetween(Date dateDebut, Date dateFin);
    
    /**
     * Récupère les opérations d'une caisse entre deux dates
     */
    List<OperationCaisse> findByCaisseJournaliereAndDateOperationBetween(
        CaisseJournaliere caisse, Date dateDebut, Date dateFin);
}
