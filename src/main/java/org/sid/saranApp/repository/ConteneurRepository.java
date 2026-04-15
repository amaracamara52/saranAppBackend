package org.sid.saranApp.repository;

import org.sid.saranApp.model.Boutique;
import org.sid.saranApp.model.Conteneur;
import org.sid.saranApp.model.LivraisonCommandeFournisseur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository pour la gestion des conteneurs
 */
public interface ConteneurRepository extends JpaRepository<Conteneur, String> {
    
    /**
     * Récupère tous les conteneurs d'une boutique
     */
    List<Conteneur> findByBoutique(Boutique boutique);
    
    /**
     * Récupère tous les conteneurs d'une livraison
     */
    List<Conteneur> findByLivraisonCommandeFournisseur(LivraisonCommandeFournisseur livraison);
    
    /**
     * Récupère un conteneur par son numéro
     */
    Conteneur findByNumeroConteneur(String numeroConteneur);
}
