package org.sid.saranApp.repository;

import org.sid.saranApp.model.Boutique;
import org.sid.saranApp.model.ClientPartenaire;
import org.sid.saranApp.model.CommandeVente;
import org.sid.saranApp.model.VersementClientPartenaire;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository pour la gestion des versements des clients partenaires
 */
public interface VersementClientPartenaireRepository extends JpaRepository<VersementClientPartenaire, String> {
    
    /**
     * Récupère tous les versements d'un client partenaire
     */
    List<VersementClientPartenaire> findByClientPartenaire(ClientPartenaire clientPartenaire);
    
    /**
     * Récupère tous les versements d'une boutique
     */
    List<VersementClientPartenaire> findByBoutique(Boutique boutique);
    
    /**
     * Récupère tous les versements d'une commande vente
     */
    List<VersementClientPartenaire> findByCommandeVente(CommandeVente commandeVente);
}
