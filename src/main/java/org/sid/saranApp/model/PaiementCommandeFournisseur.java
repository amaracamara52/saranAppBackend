package org.sid.saranApp.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Date;

/**
 * Entité pour gérer les paiements des commandes fournisseur
 */
@Entity
public class PaiementCommandeFournisseur extends AbstractDomainClass {

    /**
     * Commande fournisseur associée
     */
    @ManyToOne
    private CommandeFournisseur commandeFournisseur;
    
    /**
     * Montant versé
     */
    private double montantVerse;
    
    /**
     * Date du paiement
     */
    private Date datePaiement;
    
    /**
     * Mode de paiement utilisé
     */
    @ManyToOne
    private ModePaiement modePaiement;
    
    /**
     * Numéro de référence du paiement (chèque, virement, etc.)
     */
    private String numeroReference;
    
    /**
     * Commentaire ou note sur le paiement
     */
    private String commentaire;
    
    /**
     * Reçu de paiement (fichier joint)
     */
    @ManyToOne
    private StoredFile recuPaiement;
    
    /**
     * Boutique qui effectue le paiement
     */
    @ManyToOne
    private Boutique boutique;
    
    /**
     * Utilisateur qui a enregistré le paiement
     */
    @ManyToOne
    private Utilisateur utilisateur;

    public CommandeFournisseur getCommandeFournisseur() {
        return commandeFournisseur;
    }

    public void setCommandeFournisseur(CommandeFournisseur commandeFournisseur) {
        this.commandeFournisseur = commandeFournisseur;
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

    public ModePaiement getModePaiement() {
        return modePaiement;
    }

    public void setModePaiement(ModePaiement modePaiement) {
        this.modePaiement = modePaiement;
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

    public StoredFile getRecuPaiement() {
        return recuPaiement;
    }

    public void setRecuPaiement(StoredFile recuPaiement) {
        this.recuPaiement = recuPaiement;
    }

    public Boutique getBoutique() {
        return boutique;
    }

    public void setBoutique(Boutique boutique) {
        this.boutique = boutique;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }
}
