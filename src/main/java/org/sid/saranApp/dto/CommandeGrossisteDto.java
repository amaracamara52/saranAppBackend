package org.sid.saranApp.dto;

import org.sid.saranApp.enume.StatusCommandeGrossisteEnum;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO pour les commandes entre détaillants et grossistes
 */
public class CommandeGrossisteDto {
    
    private String uuid;
    private String numeroCommande;
    private LocalDate dateCommande;
    private double montantTotal;
    private StatusCommandeGrossisteEnum statut;
    private String uuidBoutiqueDetaillee; // Détaillant
    private String uuidBoutiqueGrossiste; // Grossiste
    private String uuidUtilisateurDetaillee;
    private String uuidUtilisateurGrossiste;
    private LocalDate dateValidation;
    private String libelleBoutiqueDetaillee;
    private String libelleBoutiqueGrossiste;
    private String uuidClientPartenaire; // UUID du client partenaire (si c'est une commande d'un client partenaire)
    private String numeroCompteClientPartenaire; // Numéro de compte du client partenaire
    private String nomClientPartenaire; // Nom du client partenaire
    private List<DetailCommandeGrossisteDto> detailsCommande = new ArrayList<>();
    
    // Données enrichies pour optimisation frontend (inspiré Odoo)
    private int nombreLivraisons;
    private int nombreArticles; // Nombre total d'articles dans la commande
    private boolean peutEtreValidee; // Si la commande peut être validée (stock suffisant)
    private String paysProvenanceClientPartenaire; // Pays de provenance si client partenaire

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getNumeroCommande() {
        return numeroCommande;
    }

    public void setNumeroCommande(String numeroCommande) {
        this.numeroCommande = numeroCommande;
    }

    public LocalDate getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(LocalDate dateCommande) {
        this.dateCommande = dateCommande;
    }

    public double getMontantTotal() {
        return montantTotal;
    }

    public void setMontantTotal(double montantTotal) {
        this.montantTotal = montantTotal;
    }

    public StatusCommandeGrossisteEnum getStatut() {
        return statut;
    }

    public void setStatut(StatusCommandeGrossisteEnum statut) {
        this.statut = statut;
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

    public String getUuidUtilisateurDetaillee() {
        return uuidUtilisateurDetaillee;
    }

    public void setUuidUtilisateurDetaillee(String uuidUtilisateurDetaillee) {
        this.uuidUtilisateurDetaillee = uuidUtilisateurDetaillee;
    }

    public String getUuidUtilisateurGrossiste() {
        return uuidUtilisateurGrossiste;
    }

    public void setUuidUtilisateurGrossiste(String uuidUtilisateurGrossiste) {
        this.uuidUtilisateurGrossiste = uuidUtilisateurGrossiste;
    }

    public LocalDate getDateValidation() {
        return dateValidation;
    }

    public void setDateValidation(LocalDate dateValidation) {
        this.dateValidation = dateValidation;
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

    public List<DetailCommandeGrossisteDto> getDetailsCommande() {
        return detailsCommande;
    }

    public void setDetailsCommande(List<DetailCommandeGrossisteDto> detailsCommande) {
        this.detailsCommande = detailsCommande;
    }

    public String getUuidClientPartenaire() {
        return uuidClientPartenaire;
    }

    public void setUuidClientPartenaire(String uuidClientPartenaire) {
        this.uuidClientPartenaire = uuidClientPartenaire;
    }

    public String getNumeroCompteClientPartenaire() {
        return numeroCompteClientPartenaire;
    }

    public void setNumeroCompteClientPartenaire(String numeroCompteClientPartenaire) {
        this.numeroCompteClientPartenaire = numeroCompteClientPartenaire;
    }

    public String getNomClientPartenaire() {
        return nomClientPartenaire;
    }

    public void setNomClientPartenaire(String nomClientPartenaire) {
        this.nomClientPartenaire = nomClientPartenaire;
    }

    public int getNombreLivraisons() {
        return nombreLivraisons;
    }

    public void setNombreLivraisons(int nombreLivraisons) {
        this.nombreLivraisons = nombreLivraisons;
    }

    public int getNombreArticles() {
        return nombreArticles;
    }

    public void setNombreArticles(int nombreArticles) {
        this.nombreArticles = nombreArticles;
    }

    public boolean isPeutEtreValidee() {
        return peutEtreValidee;
    }

    public void setPeutEtreValidee(boolean peutEtreValidee) {
        this.peutEtreValidee = peutEtreValidee;
    }

    public String getPaysProvenanceClientPartenaire() {
        return paysProvenanceClientPartenaire;
    }

    public void setPaysProvenanceClientPartenaire(String paysProvenanceClientPartenaire) {
        this.paysProvenanceClientPartenaire = paysProvenanceClientPartenaire;
    }
}
