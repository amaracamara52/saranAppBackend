package org.sid.saranApp.repository;

import org.sid.saranApp.enume.StatusCommandeGrossisteEnum;
import org.sid.saranApp.model.Boutique;
import org.sid.saranApp.model.ClientPartenaire;
import org.sid.saranApp.model.CommandeGrossiste;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository pour la gestion des commandes grossistes
 */
public interface CommandeGrossisteRepository extends JpaRepository<CommandeGrossiste, String> {
    
    /**
     * Récupère toutes les commandes d'un détaillant
     */
    List<CommandeGrossiste> findByBoutiqueDetaillee(Boutique boutiqueDetaillee);
    
    /**
     * Récupère toutes les commandes reçues par un grossiste
     */
    List<CommandeGrossiste> findByBoutiqueGrossiste(Boutique boutiqueGrossiste);
    
    /**
     * Récupère les commandes par statut pour un grossiste
     */
    List<CommandeGrossiste> findByBoutiqueGrossisteAndStatut(Boutique boutiqueGrossiste, StatusCommandeGrossisteEnum statut);
    
    /**
     * Récupère les commandes par statut pour un détaillant
     */
    List<CommandeGrossiste> findByBoutiqueDetailleeAndStatut(Boutique boutiqueDetaillee, StatusCommandeGrossisteEnum statut);
    
    /**
     * Récupère une commande par son numéro
     */
    CommandeGrossiste findByNumeroCommande(String numeroCommande);
    
    /**
     * Récupère toutes les commandes d'un client partenaire
     */
    List<CommandeGrossiste> findByClientPartenaire(ClientPartenaire clientPartenaire);
}
