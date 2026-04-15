package org.sid.saranApp.dto;

import java.util.Date;

/**
 * DTO pour les partenariats client-boutique
 */
public class ClientPartenaireDto {
    
    private String uuid;
    private String uuidClient;
    private String uuidBoutique;
    private String nomClient;
    private String prenomClient;
    private String emailClient;
    private String libelleBoutique;
    private Date dateCreation;
    private Date dateExpiration;
    private String statut;
    private String numeroCompte;
    private String conditionsSpeciales;
    private Double limiteCredit;
    private String uuidUtilisateur;
    private String uuidPaysProvenance;
    private String libellePaysProvenance;
    private String countryCodePaysProvenance;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUuidClient() {
        return uuidClient;
    }

    public void setUuidClient(String uuidClient) {
        this.uuidClient = uuidClient;
    }

    public String getUuidBoutique() {
        return uuidBoutique;
    }

    public void setUuidBoutique(String uuidBoutique) {
        this.uuidBoutique = uuidBoutique;
    }

    public String getNomClient() {
        return nomClient;
    }

    public void setNomClient(String nomClient) {
        this.nomClient = nomClient;
    }

    public String getPrenomClient() {
        return prenomClient;
    }

    public void setPrenomClient(String prenomClient) {
        this.prenomClient = prenomClient;
    }

    public String getEmailClient() {
        return emailClient;
    }

    public void setEmailClient(String emailClient) {
        this.emailClient = emailClient;
    }

    public String getLibelleBoutique() {
        return libelleBoutique;
    }

    public void setLibelleBoutique(String libelleBoutique) {
        this.libelleBoutique = libelleBoutique;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Date getDateExpiration() {
        return dateExpiration;
    }

    public void setDateExpiration(Date dateExpiration) {
        this.dateExpiration = dateExpiration;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getNumeroCompte() {
        return numeroCompte;
    }

    public void setNumeroCompte(String numeroCompte) {
        this.numeroCompte = numeroCompte;
    }

    public String getConditionsSpeciales() {
        return conditionsSpeciales;
    }

    public void setConditionsSpeciales(String conditionsSpeciales) {
        this.conditionsSpeciales = conditionsSpeciales;
    }

    public Double getLimiteCredit() {
        return limiteCredit;
    }

    public void setLimiteCredit(Double limiteCredit) {
        this.limiteCredit = limiteCredit;
    }

    public String getUuidUtilisateur() {
        return uuidUtilisateur;
    }

    public void setUuidUtilisateur(String uuidUtilisateur) {
        this.uuidUtilisateur = uuidUtilisateur;
    }

    public String getUuidPaysProvenance() {
        return uuidPaysProvenance;
    }

    public void setUuidPaysProvenance(String uuidPaysProvenance) {
        this.uuidPaysProvenance = uuidPaysProvenance;
    }

    public String getLibellePaysProvenance() {
        return libellePaysProvenance;
    }

    public void setLibellePaysProvenance(String libellePaysProvenance) {
        this.libellePaysProvenance = libellePaysProvenance;
    }

    public String getCountryCodePaysProvenance() {
        return countryCodePaysProvenance;
    }

    public void setCountryCodePaysProvenance(String countryCodePaysProvenance) {
        this.countryCodePaysProvenance = countryCodePaysProvenance;
    }
}
