package org.sid.saranApp.dto;

import java.util.Date;

/**
 * DTO pour les frais de dédouanement
 */
public class FraisDedouanementDto {
    
    private String uuid;
    private String typeFrais;
    private String libelle;
    private double montant;
    private Date datePaiement;
    private String statutPaiement;
    private String numeroFacture;
    private String uuidLivraisonCommandeFournisseur;
    private String uuidConteneur;
    private String uuidBoutique;
    private String libelleBoutique;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

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

    public String getUuidLivraisonCommandeFournisseur() {
        return uuidLivraisonCommandeFournisseur;
    }

    public void setUuidLivraisonCommandeFournisseur(String uuidLivraisonCommandeFournisseur) {
        this.uuidLivraisonCommandeFournisseur = uuidLivraisonCommandeFournisseur;
    }

    public String getUuidConteneur() {
        return uuidConteneur;
    }

    public void setUuidConteneur(String uuidConteneur) {
        this.uuidConteneur = uuidConteneur;
    }

    public String getUuidBoutique() {
        return uuidBoutique;
    }

    public void setUuidBoutique(String uuidBoutique) {
        this.uuidBoutique = uuidBoutique;
    }

    public String getLibelleBoutique() {
        return libelleBoutique;
    }

    public void setLibelleBoutique(String libelleBoutique) {
        this.libelleBoutique = libelleBoutique;
    }
}
