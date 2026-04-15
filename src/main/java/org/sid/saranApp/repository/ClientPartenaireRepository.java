package org.sid.saranApp.repository;

import org.sid.saranApp.model.Boutique;
import org.sid.saranApp.model.Client;
import org.sid.saranApp.model.ClientPartenaire;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository pour la gestion des partenariats client-boutique
 */
public interface ClientPartenaireRepository extends JpaRepository<ClientPartenaire, String> {
    
    /**
     * Récupère tous les partenariats d'un client
     */
    List<ClientPartenaire> findByClient(Client client);
    
    /**
     * Récupère tous les partenariats d'une boutique
     */
    List<ClientPartenaire> findByBoutique(Boutique boutique);
    
    /**
     * Récupère un partenariat spécifique entre un client et une boutique
     */
    Optional<ClientPartenaire> findByClientAndBoutique(Client client, Boutique boutique);
    
    /**
     * Récupère les partenariats actifs d'un client
     */
    List<ClientPartenaire> findByClientAndStatut(Client client, String statut);
    
    /**
     * Récupère les partenariats actifs d'une boutique
     */
    List<ClientPartenaire> findByBoutiqueAndStatut(Boutique boutique, String statut);
}
