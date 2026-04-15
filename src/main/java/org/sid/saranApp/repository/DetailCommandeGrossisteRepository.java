package org.sid.saranApp.repository;

import org.sid.saranApp.model.CommandeGrossiste;
import org.sid.saranApp.model.DetailCommandeGrossiste;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository pour la gestion des détails de commandes grossistes
 */
public interface DetailCommandeGrossisteRepository extends JpaRepository<DetailCommandeGrossiste, String> {
    
    /**
     * Récupère tous les détails d'une commande grossiste
     */
    List<DetailCommandeGrossiste> findByCommandeGrossiste(CommandeGrossiste commandeGrossiste);
}
