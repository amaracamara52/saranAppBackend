package org.sid.saranApp.dto;

import java.util.Date;

/**
 * DTO pour les conteneurs de livraison
 */
public class ConteneurDto {
    
    private String uuid;
    private String numeroConteneur;
    private String typeConteneur;
    private Date dateEnregistrement;
    private Date dateArriveePort;
    private Date dateDedouanement;
    private String statut;
    private String uuidLivraisonCommandeFournisseur;
    private String uuidBoutique;
    private String libelleBoutique;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getNumeroConteneur() {
        return numeroConteneur;
    }

    public void setNumeroConteneur(String numeroConteneur) {
        this.numeroConteneur = numeroConteneur;
    }

    public String getTypeConteneur() {
        return typeConteneur;
    }

    public void setTypeConteneur(String typeConteneur) {
        this.typeConteneur = typeConteneur;
    }

    public Date getDateEnregistrement() {
        return dateEnregistrement;
    }

    public void setDateEnregistrement(Date dateEnregistrement) {
        this.dateEnregistrement = dateEnregistrement;
    }

    public Date getDateArriveePort() {
        return dateArriveePort;
    }

    public void setDateArriveePort(Date dateArriveePort) {
        this.dateArriveePort = dateArriveePort;
    }

    public Date getDateDedouanement() {
        return dateDedouanement;
    }

    public void setDateDedouanement(Date dateDedouanement) {
        this.dateDedouanement = dateDedouanement;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getUuidLivraisonCommandeFournisseur() {
        return uuidLivraisonCommandeFournisseur;
    }

    public void setUuidLivraisonCommandeFournisseur(String uuidLivraisonCommandeFournisseur) {
        this.uuidLivraisonCommandeFournisseur = uuidLivraisonCommandeFournisseur;
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
