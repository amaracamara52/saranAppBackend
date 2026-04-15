package org.sid.saranApp.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Entité pour gérer les livraisons de commandes grossistes
 * (livraisons du grossiste vers le détaillant)
 */
@Entity
public class LivraisonCommandeGrossiste extends AbstractDomainClass {

    /**
     * Date de livraison
     */
    private Date dateLivraison;
    
    /**
     * Heure de livraison
     */
    private String heure;
    
    /**
     * Quantité livrée
     */
    private int quantite;
    
    /**
     * Prix unitaire de livraison
     */
    private double prix;
    
    /**
     * Numéro de suivi de livraison
     */
    private String numeroSuivi;
    
    /**
     * Transporteur
     */
    private String transporteur;
    
    /**
     * Commande grossiste associée
     */
    @ManyToOne
    private CommandeGrossiste commandeGrossiste;
    
    /**
     * Détail de commande associé
     */
    @ManyToOne
    private DetailCommandeGrossiste detailCommandeGrossiste;
    
    /**
     * Boutique détaillante destinataire
     */
    @ManyToOne
    private Boutique boutiqueDetaillee;
    
    /**
     * Boutique grossiste expéditrice
     */
    @ManyToOne
    private Boutique boutiqueGrossiste;
    
    /**
     * Utilisateur qui effectue la livraison
     */
    @ManyToOne
    private Utilisateur utilisateur;
    
    /**
     * Liste des produits créés à partir de cette livraison (traçabilité)
     */
    @OneToMany(mappedBy = "livraisonCommandeGrossiste")
    private List<Produit> produits = new ArrayList<>();
    
    /**
     * Statut de vérification de la livraison (EN_ATTENTE, VERIFIEE, REJETEE)
     */
    private String statutVerification;
    
    /**
     * Indique si la livraison a été vérifiée
     */
    private boolean verifiee;
    
    /**
     * Date de vérification de la livraison
     */
    private Date dateVerification;
    
    /**
     * Utilisateur qui a vérifié la livraison
     */
    @ManyToOne
    private Utilisateur utilisateurVerificateur;
    
    /**
     * Commentaire de vérification
     */
    private String commentaireVerification;

    public Date getDateLivraison() {
        return dateLivraison;
    }

    public void setDateLivraison(Date dateLivraison) {
        this.dateLivraison = dateLivraison;
    }

    public String getHeure() {
        return heure;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public String getNumeroSuivi() {
        return numeroSuivi;
    }

    public void setNumeroSuivi(String numeroSuivi) {
        this.numeroSuivi = numeroSuivi;
    }

    public String getTransporteur() {
        return transporteur;
    }

    public void setTransporteur(String transporteur) {
        this.transporteur = transporteur;
    }

    public CommandeGrossiste getCommandeGrossiste() {
        return commandeGrossiste;
    }

    public void setCommandeGrossiste(CommandeGrossiste commandeGrossiste) {
        this.commandeGrossiste = commandeGrossiste;
    }

    public DetailCommandeGrossiste getDetailCommandeGrossiste() {
        return detailCommandeGrossiste;
    }

    public void setDetailCommandeGrossiste(DetailCommandeGrossiste detailCommandeGrossiste) {
        this.detailCommandeGrossiste = detailCommandeGrossiste;
    }

    public Boutique getBoutiqueDetaillee() {
        return boutiqueDetaillee;
    }

    public void setBoutiqueDetaillee(Boutique boutiqueDetaillee) {
        this.boutiqueDetaillee = boutiqueDetaillee;
    }

    public Boutique getBoutiqueGrossiste() {
        return boutiqueGrossiste;
    }

    public void setBoutiqueGrossiste(Boutique boutiqueGrossiste) {
        this.boutiqueGrossiste = boutiqueGrossiste;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public List<Produit> getProduits() {
        return produits;
    }

    public void setProduits(List<Produit> produits) {
        this.produits = produits;
    }

    public String getStatutVerification() {
        return statutVerification;
    }

    public void setStatutVerification(String statutVerification) {
        this.statutVerification = statutVerification;
    }

    public boolean isVerifiee() {
        return verifiee;
    }

    public void setVerifiee(boolean verifiee) {
        this.verifiee = verifiee;
    }

    public Date getDateVerification() {
        return dateVerification;
    }

    public void setDateVerification(Date dateVerification) {
        this.dateVerification = dateVerification;
    }

    public Utilisateur getUtilisateurVerificateur() {
        return utilisateurVerificateur;
    }

    public void setUtilisateurVerificateur(Utilisateur utilisateurVerificateur) {
        this.utilisateurVerificateur = utilisateurVerificateur;
    }

    public String getCommentaireVerification() {
        return commentaireVerification;
    }

    public void setCommentaireVerification(String commentaireVerification) {
        this.commentaireVerification = commentaireVerification;
    }
}
