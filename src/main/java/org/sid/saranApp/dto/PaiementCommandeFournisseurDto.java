package org.sid.saranApp.dto;

import java.util.Date;

/**
 * DTO pour les paiements des commandes fournisseur
 */
public class PaiementCommandeFournisseurDto {
    
    private String uuid;
    private String uuidCommandeFournisseur;
    private String uuidModePaiement;
    private double montantVerse;
    private Date datePaiement;
    private String numeroReference;
    private String commentaire;
    private String uuidRecuPaiement;
    private String libelleModePaiement;
    private String refCommande;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUuidCommandeFournisseur() {
        return uuidCommandeFournisseur;
    }

    public void setUuidCommandeFournisseur(String uuidCommandeFournisseur) {
        this.uuidCommandeFournisseur = uuidCommandeFournisseur;
    }

    public String getUuidModePaiement() {
        return uuidModePaiement;
    }

    public void setUuidModePaiement(String uuidModePaiement) {
        this.uuidModePaiement = uuidModePaiement;
    }

    public double getMontantVerse() {
        return montantVerse;
    }

    public void setMontantVerse(double montantVerse) {
        this.montantVerse = montantVerse;
    }

    public Date getDatePaiement() {
        return datePaiement;
    }

    public void setDatePaiement(Date datePaiement) {
        this.datePaiement = datePaiement;
    }

    public String getNumeroReference() {
        return numeroReference;
    }

    public void setNumeroReference(String numeroReference) {
        this.numeroReference = numeroReference;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public String getUuidRecuPaiement() {
        return uuidRecuPaiement;
    }

    public void setUuidRecuPaiement(String uuidRecuPaiement) {
        this.uuidRecuPaiement = uuidRecuPaiement;
    }

    public String getLibelleModePaiement() {
        return libelleModePaiement;
    }

    public void setLibelleModePaiement(String libelleModePaiement) {
        this.libelleModePaiement = libelleModePaiement;
    }

    public String getRefCommande() {
        return refCommande;
    }

    public void setRefCommande(String refCommande) {
        this.refCommande = refCommande;
    }
}
