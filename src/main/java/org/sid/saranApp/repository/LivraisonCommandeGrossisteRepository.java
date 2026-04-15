package org.sid.saranApp.repository;

import org.sid.saranApp.model.Boutique;
import org.sid.saranApp.model.CommandeGrossiste;
import org.sid.saranApp.model.LivraisonCommandeGrossiste;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository pour la gestion des livraisons de commandes grossistes
 */
public interface LivraisonCommandeGrossisteRepository extends JpaRepository<LivraisonCommandeGrossiste, String> {
    
    /**
     * Récupère toutes les livraisons d'une commande grossiste
     */
    List<LivraisonCommandeGrossiste> findByCommandeGrossiste(CommandeGrossiste commandeGrossiste);
    
    /**
     * Récupère toutes les livraisons reçues par un détaillant
     */
    List<LivraisonCommandeGrossiste> findByBoutiqueDetaillee(Boutique boutiqueDetaillee);
    
    /**
     * Récupère toutes les livraisons envoyées par un grossiste
     */
    List<LivraisonCommandeGrossiste> findByBoutiqueGrossiste(Boutique boutiqueGrossiste);
}
