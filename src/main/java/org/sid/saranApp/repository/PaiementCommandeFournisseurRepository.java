package org.sid.saranApp.repository;

import org.sid.saranApp.model.Boutique;
import org.sid.saranApp.model.CommandeFournisseur;
import org.sid.saranApp.model.Fournisseur;
import org.sid.saranApp.model.PaiementCommandeFournisseur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository pour la gestion des paiements des commandes fournisseur
 */
public interface PaiementCommandeFournisseurRepository extends JpaRepository<PaiementCommandeFournisseur, String> {
    
    /**
     * Récupère tous les paiements d'une commande fournisseur
     */
    List<PaiementCommandeFournisseur> findByCommandeFournisseur(CommandeFournisseur commandeFournisseur);
    
    /**
     * Récupère tous les paiements d'une boutique
     */
    List<PaiementCommandeFournisseur> findByBoutique(Boutique boutique);
    
    /**
     * Récupère tous les paiements d'un fournisseur (via les commandes)
     */
    List<PaiementCommandeFournisseur> findByCommandeFournisseur_Fournisseur(Fournisseur fournisseur);
}
