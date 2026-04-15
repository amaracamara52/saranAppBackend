package org.sid.saranApp.dto;

import java.util.Date;

/**
 * DTO pour les livraisons de commandes grossistes
 */
public class LivraisonCommandeGrossisteDto {
    
    private String uuid;
    private Date dateLivraison;
    private String heure;
    private int quantite;
    private double prix;
    private String numeroSuivi;
    private String transporteur;
    private String uuidCommandeGrossiste;
    private String uuidDetailCommandeGrossiste;
    private String uuidBoutiqueDetaillee;
    private String uuidBoutiqueGrossiste;
    private String libelleBoutiqueDetaillee;
    private String libelleBoutiqueGrossiste;
    private String libelleArticle;
    private String statutVerification;
    private boolean verifiee;
    private Date dateVerification;
    private String uuidUtilisateurVerificateur;
    private String nomUtilisateurVerificateur;
    private String commentaireVerification;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

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

    public String getUuidCommandeGrossiste() {
        return uuidCommandeGrossiste;
    }

    public void setUuidCommandeGrossiste(String uuidCommandeGrossiste) {
        this.uuidCommandeGrossiste = uuidCommandeGrossiste;
    }

    public String getUuidDetailCommandeGrossiste() {
        return uuidDetailCommandeGrossiste;
    }

    public void setUuidDetailCommandeGrossiste(String uuidDetailCommandeGrossiste) {
        this.uuidDetailCommandeGrossiste = uuidDetailCommandeGrossiste;
    }

    public String getUuidBoutiqueDetaillee() {
        return uuidBoutiqueDetaillee;
    }

    public void setUuidBoutiqueDetaillee(String uuidBoutiqueDetaillee) {
        this.uuidBoutiqueDetaillee = uuidBoutiqueDetaillee;
    }

    public String getUuidBoutiqueGrossiste() {
        return uuidBoutiqueGrossiste;
    }

    public void setUuidBoutiqueGrossiste(String uuidBoutiqueGrossiste) {
        this.uuidBoutiqueGrossiste = uuidBoutiqueGrossiste;
    }

    public String getLibelleBoutiqueDetaillee() {
        return libelleBoutiqueDetaillee;
    }

    public void setLibelleBoutiqueDetaillee(String libelleBoutiqueDetaillee) {
        this.libelleBoutiqueDetaillee = libelleBoutiqueDetaillee;
    }

    public String getLibelleBoutiqueGrossiste() {
        return libelleBoutiqueGrossiste;
    }

    public void setLibelleBoutiqueGrossiste(String libelleBoutiqueGrossiste) {
        this.libelleBoutiqueGrossiste = libelleBoutiqueGrossiste;
    }

    public String getLibelleArticle() {
        return libelleArticle;
    }

    public void setLibelleArticle(String libelleArticle) {
        this.libelleArticle = libelleArticle;
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

    public String getUuidUtilisateurVerificateur() {
        return uuidUtilisateurVerificateur;
    }

    public void setUuidUtilisateurVerificateur(String uuidUtilisateurVerificateur) {
        this.uuidUtilisateurVerificateur = uuidUtilisateurVerificateur;
    }

    public String getNomUtilisateurVerificateur() {
        return nomUtilisateurVerificateur;
    }

    public void setNomUtilisateurVerificateur(String nomUtilisateurVerificateur) {
        this.nomUtilisateurVerificateur = nomUtilisateurVerificateur;
    }

    public String getCommentaireVerification() {
        return commentaireVerification;
    }

    public void setCommentaireVerification(String commentaireVerification) {
        this.commentaireVerification = commentaireVerification;
    }
}
