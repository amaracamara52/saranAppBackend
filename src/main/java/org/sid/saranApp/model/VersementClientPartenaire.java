package org.sid.saranApp.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Date;

/**
 * Entité pour gérer les versements des clients partenaires
 * (paiements reçus des clients pour leurs commandes en gros)
 */
@Entity
public class VersementClientPartenaire extends AbstractDomainClass {

    /**
     * Client partenaire qui effectue le versement
     */
    @ManyToOne
    private ClientPartenaire clientPartenaire;
    
    /**
     * Commande vente associée (optionnel, peut être un versement anticipé)
     */
    @ManyToOne
    private CommandeVente commandeVente;
    
    /**
     * Montant versé
     */
    private double montantVerse;
    
    /**
     * Date du versement
     */
    private Date dateVersement;
    
    /**
     * Mode de paiement utilisé
     */
    @ManyToOne
    private ModePaiement modePaiement;
    
    /**
     * Numéro de référence du versement (chèque, virement, etc.)
     */
    private String numeroReference;
    
    /**
     * Commentaire ou note sur le versement
     */
    private String commentaire;
    
    /**
     * Reçu de versement (fichier joint)
     */
    @ManyToOne
    private StoredFile recuVersement;
    
    /**
     * Boutique qui reçoit le versement
     */
    @ManyToOne
    private Boutique boutique;
    
    /**
     * Utilisateur qui a enregistré le versement
     */
    @ManyToOne
    private Utilisateur utilisateur;

    public ClientPartenaire getClientPartenaire() {
        return clientPartenaire;
    }

    public void setClientPartenaire(ClientPartenaire clientPartenaire) {
        this.clientPartenaire = clientPartenaire;
    }

    public CommandeVente getCommandeVente() {
        return commandeVente;
    }

    public void setCommandeVente(CommandeVente commandeVente) {
        this.commandeVente = commandeVente;
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

    public StoredFile getRecuVersement() {
        return recuVersement;
    }

    public void setRecuVersement(StoredFile recuVersement) {
        this.recuVersement = recuVersement;
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
