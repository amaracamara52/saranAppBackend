package org.sid.saranApp.service;

import org.sid.saranApp.model.Boutique;
import org.sid.saranApp.model.Produit;

import java.util.List;

public interface ApprovisionnementAutomatiqueService {
    
    /**
     * Vérifie et déclenche l'approvisionnement automatique pour une boutique
     * @param boutique la boutique à vérifier
     */
    void verifierEtDeclencherApprovisionnement(Boutique boutique);
    
    /**
     * Vérifie et déclenche l'approvisionnement automatique pour toutes les boutiques
     */
    void verifierEtDeclencherApprovisionnementPourToutesBoutiques();
    
    /**
     * Trouve les produits qui ont atteint le seuil d'alerte pour une boutique
     * @param boutique la boutique
     * @return liste des produits à réapprovisionner
     */
    List<Produit> trouverProduitsAReapprovisionner(Boutique boutique);
    
    /**
     * Crée une commande fournisseur automatique pour un produit
     * @param produit le produit à commander
     * @param quantiteACommander la quantité à commander
     */
    void creerCommandeAutomatique(Produit produit, int quantiteACommander);
} 