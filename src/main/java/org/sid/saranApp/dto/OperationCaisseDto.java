package org.sid.saranApp.dto;

import org.sid.saranApp.enume.EnumTypeTransaction;

import java.math.BigDecimal;
import java.util.Date;

/**
 * DTO pour les opérations de caisse
 */
public class OperationCaisseDto {
    
    private String uuid;
    private EnumTypeTransaction typeOperation;
    private BigDecimal montant;
    private Date dateOperation;
    private String libelle;
    private String typeDetail;
    private String uuidCaisseJournaliere;
    private String uuidTransaction;
    private String uuidPaiementCommandeFournisseur;
    private String uuidVersementClientPartenaire;
    private String uuidCommandeVente;
    private String uuidCommandeFournisseur;
    private String uuidClientPartenaire;
    private String uuidFournisseur;
    private String commentaire;
    private String numeroCommande;
    private String nomClient;
    private String nomFournisseur;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public EnumTypeTransaction getTypeOperation() {
        return typeOperation;
    }

    public void setTypeOperation(EnumTypeTransaction typeOperation) {
        this.typeOperation = typeOperation;
    }

    public BigDecimal getMontant() {
        return montant;
    }

    public void setMontant(BigDecimal montant) {
        this.montant = montant;
    }

    public Date getDateOperation() {
        return dateOperation;
    }

    public void setDateOperation(Date dateOperation) {
        this.dateOperation = dateOperation;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getTypeDetail() {
        return typeDetail;
    }

    public void setTypeDetail(String typeDetail) {
        this.typeDetail = typeDetail;
    }

    public String getUuidCaisseJournaliere() {
        return uuidCaisseJournaliere;
    }

    public void setUuidCaisseJournaliere(String uuidCaisseJournaliere) {
        this.uuidCaisseJournaliere = uuidCaisseJournaliere;
    }

    public String getUuidTransaction() {
        return uuidTransaction;
    }

    public void setUuidTransaction(String uuidTransaction) {
        this.uuidTransaction = uuidTransaction;
    }

    public String getUuidPaiementCommandeFournisseur() {
        return uuidPaiementCommandeFournisseur;
    }

    public void setUuidPaiementCommandeFournisseur(String uuidPaiementCommandeFournisseur) {
        this.uuidPaiementCommandeFournisseur = uuidPaiementCommandeFournisseur;
    }

    public String getUuidVersementClientPartenaire() {
        return uuidVersementClientPartenaire;
    }

    public void setUuidVersementClientPartenaire(String uuidVersementClientPartenaire) {
        this.uuidVersementClientPartenaire = uuidVersementClientPartenaire;
    }

    public String getUuidCommandeVente() {
        return uuidCommandeVente;
    }

    public void setUuidCommandeVente(String uuidCommandeVente) {
        this.uuidCommandeVente = uuidCommandeVente;
    }

    public String getUuidCommandeFournisseur() {
        return uuidCommandeFournisseur;
    }

    public void setUuidCommandeFournisseur(String uuidCommandeFournisseur) {
        this.uuidCommandeFournisseur = uuidCommandeFournisseur;
    }

    public String getUuidClientPartenaire() {
        return uuidClientPartenaire;
    }

    public void setUuidClientPartenaire(String uuidClientPartenaire) {
        this.uuidClientPartenaire = uuidClientPartenaire;
    }

    public String getUuidFournisseur() {
        return uuidFournisseur;
    }

    public void setUuidFournisseur(String uuidFournisseur) {
        this.uuidFournisseur = uuidFournisseur;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public String getNumeroCommande() {
        return numeroCommande;
    }

    public void setNumeroCommande(String numeroCommande) {
        this.numeroCommande = numeroCommande;
    }

    public String getNomClient() {
        return nomClient;
    }

    public void setNomClient(String nomClient) {
        this.nomClient = nomClient;
    }

    public String getNomFournisseur() {
        return nomFournisseur;
    }

    public void setNomFournisseur(String nomFournisseur) {
        this.nomFournisseur = nomFournisseur;
    }
}
