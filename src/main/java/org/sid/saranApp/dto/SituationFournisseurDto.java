package org.sid.saranApp.dto;

import java.util.List;

/**
 * DTO pour la situation comptable d'un fournisseur
 */
public class SituationFournisseurDto {
    
    private String uuidFournisseur;
    private String nomFournisseur;
    private String emailFournisseur;
    private String telephoneFournisseur;
    
    /**
     * Montant total des commandes non payées
     */
    private double montantTotalCommandes;
    
    /**
     * Montant total des paiements effectués
     */
    private double montantTotalPaiements;
    
    /**
     * Dette restante (montantTotalCommandes - montantTotalPaiements)
     */
    private double detteRestante;
    
    /**
     * Liste des commandes avec leur statut de paiement
     */
    private List<CommandeFournisseurSoldeDto> commandes;

    public String getUuidFournisseur() {
        return uuidFournisseur;
    }

    public void setUuidFournisseur(String uuidFournisseur) {
        this.uuidFournisseur = uuidFournisseur;
    }

    public String getNomFournisseur() {
        return nomFournisseur;
    }

    public void setNomFournisseur(String nomFournisseur) {
        this.nomFournisseur = nomFournisseur;
    }

    public String getEmailFournisseur() {
        return emailFournisseur;
    }

    public void setEmailFournisseur(String emailFournisseur) {
        this.emailFournisseur = emailFournisseur;
    }

    public String getTelephoneFournisseur() {
        return telephoneFournisseur;
    }

    public void setTelephoneFournisseur(String telephoneFournisseur) {
        this.telephoneFournisseur = telephoneFournisseur;
    }

    public double getMontantTotalCommandes() {
        return montantTotalCommandes;
    }

    public void setMontantTotalCommandes(double montantTotalCommandes) {
        this.montantTotalCommandes = montantTotalCommandes;
    }

    public double getMontantTotalPaiements() {
        return montantTotalPaiements;
    }

    public void setMontantTotalPaiements(double montantTotalPaiements) {
        this.montantTotalPaiements = montantTotalPaiements;
    }

    public double getDetteRestante() {
        return detteRestante;
    }

    public void setDetteRestante(double detteRestante) {
        this.detteRestante = detteRestante;
    }

    public List<CommandeFournisseurSoldeDto> getCommandes() {
        return commandes;
    }

    public void setCommandes(List<CommandeFournisseurSoldeDto> commandes) {
        this.commandes = commandes;
    }
}
