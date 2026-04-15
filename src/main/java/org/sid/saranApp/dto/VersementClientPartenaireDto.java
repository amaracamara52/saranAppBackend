package org.sid.saranApp.dto;

import java.util.Date;

/**
 * DTO pour les versements des clients partenaires
 */
public class VersementClientPartenaireDto {
    
    private String uuid;
    private String uuidClientPartenaire;
    private String uuidCommandeVente;
    private String uuidModePaiement;
    private double montantVerse;
    private Date dateVersement;
    private String numeroReference;
    private String commentaire;
    private String uuidRecuVersement;
    private String libelleModePaiement;
    private String nomClient;
    private String numeroCompte;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUuidClientPartenaire() {
        return uuidClientPartenaire;
    }

    public void setUuidClientPartenaire(String uuidClientPartenaire) {
        this.uuidClientPartenaire = uuidClientPartenaire;
    }

    public String getUuidCommandeVente() {
        return uuidCommandeVente;
    }

    public void setUuidCommandeVente(String uuidCommandeVente) {
        this.uuidCommandeVente = uuidCommandeVente;
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

    public Date getDateVersement() {
        return dateVersement;
    }

    public void setDateVersement(Date dateVersement) {
        this.dateVersement = dateVersement;
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

    public String getUuidRecuVersement() {
        return uuidRecuVersement;
    }

    public void setUuidRecuVersement(String uuidRecuVersement) {
        this.uuidRecuVersement = uuidRecuVersement;
    }

    public String getLibelleModePaiement() {
        return libelleModePaiement;
    }

    public void setLibelleModePaiement(String libelleModePaiement) {
        this.libelleModePaiement = libelleModePaiement;
    }

    public String getNomClient() {
        return nomClient;
    }

    public void setNomClient(String nomClient) {
        this.nomClient = nomClient;
    }

    public String getNumeroCompte() {
        return numeroCompte;
    }

    public void setNumeroCompte(String numeroCompte) {
        this.numeroCompte = numeroCompte;
    }
}
