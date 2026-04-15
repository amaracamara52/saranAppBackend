package org.sid.saranApp.repository;

import org.sid.saranApp.model.Boutique;
import org.sid.saranApp.model.Conteneur;
import org.sid.saranApp.model.FraisDedouanement;
import org.sid.saranApp.model.LivraisonCommandeFournisseur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository pour la gestion des frais de dédouanement
 */
public interface FraisDedouanementRepository extends JpaRepository<FraisDedouanement, String> {
    
    /**
     * Récupère tous les frais d'une boutique
     */
    List<FraisDedouanement> findByBoutique(Boutique boutique);
    
    /**
     * Récupère tous les frais d'une livraison
     */
    List<FraisDedouanement> findByLivraisonCommandeFournisseur(LivraisonCommandeFournisseur livraison);
    
    /**
     * Récupère tous les frais d'un conteneur
     */
    List<FraisDedouanement> findByConteneur(Conteneur conteneur);
}
