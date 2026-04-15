package org.sid.saranApp.dto;

import java.util.List;

/**
 * DTO pour la situation comptable d'un client partenaire
 */
public class SituationClientPartenaireDto {
    
    private String uuidClientPartenaire;
    private String uuidClient;
    private String nomClient;
    private String prenomClient;
    private String emailClient;
    private String numeroCompte;
    
    /**
     * Montant total des commandes en gros non payées
     */
    private double montantTotalCommandes;
    
    /**
     * Montant total des versements reçus
     */
    private double montantTotalVersements;
    
    /**
     * Créance restante (montantTotalCommandes - montantTotalVersements)
     */
    private double creanceRestante;
    
    /**
     * Liste des commandes avec leur statut de paiement
     */
    private List<CommandeVenteSoldeDto> commandes;

    public String getUuidClientPartenaire() {
        return uuidClientPartenaire;
    }

    public void setUuidClientPartenaire(String uuidClientPartenaire) {
        this.uuidClientPartenaire = uuidClientPartenaire;
    }

    public String getUuidClient() {
        return uuidClient;
    }

    public void setUuidClient(String uuidClient) {
        this.uuidClient = uuidClient;
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

    public String getNumeroCompte() {
        return numeroCompte;
    }

    public void setNumeroCompte(String numeroCompte) {
        this.numeroCompte = numeroCompte;
    }

    public double getMontantTotalCommandes() {
        return montantTotalCommandes;
    }

    public void setMontantTotalCommandes(double montantTotalCommandes) {
        this.montantTotalCommandes = montantTotalCommandes;
    }

    public double getMontantTotalVersements() {
        return montantTotalVersements;
    }

    public void setMontantTotalVersements(double montantTotalVersements) {
        this.montantTotalVersements = montantTotalVersements;
    }

    public double getCreanceRestante() {
        return creanceRestante;
    }

    public void setCreanceRestante(double creanceRestante) {
        this.creanceRestante = creanceRestante;
    }

    public List<CommandeVenteSoldeDto> getCommandes() {
        return commandes;
    }

    public void setCommandes(List<CommandeVenteSoldeDto> commandes) {
        this.commandes = commandes;
    }
}
