package org.sid.saranApp.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Détail d'une commande grossiste (ligne de commande)
 */
@Entity
public class DetailCommandeGrossiste extends AbstractDomainClass {

    /**
     * Article commandé
     */
    @ManyToOne
    private Article article;
    
    /**
     * Produit spécifique commandé (optionnel, pour référence)
     */
    @ManyToOne
    private Produit produit;
    
    /**
     * Quantité commandée
     */
    private int quantite;
    
    /**
     * Prix unitaire de vente (prix grossiste)
     */
    private double prixUnitaire;
    
    /**
     * Unité de vente utilisée
     */
    @ManyToOne
    private TypeUniteDeVente typeUniteDeVente;
    
    /**
     * Commande grossiste parente
     */
    @ManyToOne
    private CommandeGrossiste commandeGrossiste;
    
    /**
     * Boutique détaillante
     */
    @ManyToOne
    private Boutique boutique;
    
    /**
     * Utilisateur
     */
    @ManyToOne
    private Utilisateur utilisateur;

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public double getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public TypeUniteDeVente getTypeUniteDeVente() {
        return typeUniteDeVente;
    }

    public void setTypeUniteDeVente(TypeUniteDeVente typeUniteDeVente) {
        this.typeUniteDeVente = typeUniteDeVente;
    }

    public CommandeGrossiste getCommandeGrossiste() {
        return commandeGrossiste;
    }

    public void setCommandeGrossiste(CommandeGrossiste commandeGrossiste) {
        this.commandeGrossiste = commandeGrossiste;
    }

    public Boutique getBoutique() {
        return boutique;
    }

    public void setBoutique(Boutique boutique) {
        this.boutique = boutique;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }
}
