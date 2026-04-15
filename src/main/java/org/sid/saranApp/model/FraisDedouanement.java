package org.sid.saranApp.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Date;

/**
 * Entité pour gérer les frais de dédouanement
 * Les frais de dédouanement sont associés à une livraison fournisseur
 */
@Entity
public class FraisDedouanement extends AbstractDomainClass {

    /**
     * Type de frais (DOUANE, TRANSPORT, STOCKAGE, AUTRE)
     */
    private String typeFrais;
    
    /**
     * Libellé du frais
     */
    private String libelle;
    
    /**
     * Montant du frais
     */
    private double montant;
    
    /**
     * Date de paiement du frais
     */
    private Date datePaiement;
    
    /**
     * Statut du paiement (PAYE, EN_ATTENTE, IMPAYE)
     */
    private String statutPaiement;
    
    /**
     * Numéro de facture ou référence
     */
    private String numeroFacture;
    
    /**
     * Livraison fournisseur associée à ces frais
     */
    @ManyToOne
    private LivraisonCommandeFournisseur livraisonCommandeFournisseur;
    
    /**
     * Conteneur associé (optionnel)
     */
    @ManyToOne
    private Conteneur conteneur;
    
    /**
     * Boutique propriétaire
     */
    @ManyToOne
    private Boutique boutique;
    
    /**
     * Utilisateur qui a enregistré le frais
     */
    @ManyToOne
    private Utilisateur utilisateur;

    public String getTypeFrais() {
        return typeFrais;
    }

    public void setTypeFrais(String typeFrais) {
        this.typeFrais = typeFrais;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public Date getDatePaiement() {
        return datePaiement;
    }

    public void setDatePaiement(Date datePaiement) {
        this.datePaiement = datePaiement;
    }

    public String getStatutPaiement() {
        return statutPaiement;
    }

    public void setStatutPaiement(String statutPaiement) {
        this.statutPaiement = statutPaiement;
    }

    public String getNumeroFacture() {
        return numeroFacture;
    }

    public void setNumeroFacture(String numeroFacture) {
        this.numeroFacture = numeroFacture;
    }

    public LivraisonCommandeFournisseur getLivraisonCommandeFournisseur() {
        return livraisonCommandeFournisseur;
    }

    public void setLivraisonCommandeFournisseur(LivraisonCommandeFournisseur livraisonCommandeFournisseur) {
        this.livraisonCommandeFournisseur = livraisonCommandeFournisseur;
    }

    public Conteneur getConteneur() {
        return conteneur;
    }

    public void setConteneur(Conteneur conteneur) {
        this.conteneur = conteneur;
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
