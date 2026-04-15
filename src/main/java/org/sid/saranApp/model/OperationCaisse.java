package org.sid.saranApp.model;

import org.sid.saranApp.enume.EnumTypeTransaction;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Entité pour gérer les opérations de caisse
 * Toutes les entrées et sorties de caisse sont enregistrées ici
 */
@Entity
public class OperationCaisse extends AbstractDomainClass {

    /**
     * Type d'opération (ENCAISSEMENT, DECAISSEMENT)
     */
    private EnumTypeTransaction typeOperation;
    
    /**
     * Montant de l'opération
     */
    private BigDecimal montant;
    
    /**
     * Date de l'opération
     */
    private Date dateOperation;
    
    /**
     * Libellé de l'opération
     */
    private String libelle;
    
    /**
     * Type d'opération détaillé (VENTE, PAIEMENT_FOURNISSEUR, VERSEMENT_CLIENT, etc.)
     */
    private String typeDetail;
    
    /**
     * Caisse journalière associée
     */
    @ManyToOne
    private CaisseJournaliere caisseJournaliere;
    
    /**
     * Transaction associée (si l'opération vient d'une transaction)
     */
    @ManyToOne
    private Transaction transaction;
    
    /**
     * Paiement fournisseur associé (si l'opération est un paiement fournisseur)
     */
    @ManyToOne
    private PaiementCommandeFournisseur paiementCommandeFournisseur;
    
    /**
     * Versement client partenaire associé (si l'opération est un versement client)
     */
    @ManyToOne
    private VersementClientPartenaire versementClientPartenaire;
    
    /**
     * Commande vente associée (si l'opération vient d'une vente)
     */
    @ManyToOne
    private CommandeVente commandeVente;
    
    /**
     * Commande fournisseur associée (si l'opération vient d'un paiement fournisseur)
     */
    @ManyToOne
    private CommandeFournisseur commandeFournisseur;
    
    /**
     * Client partenaire associé (si l'opération concerne un client partenaire)
     */
    @ManyToOne
    private ClientPartenaire clientPartenaire;
    
    /**
     * Fournisseur associé (si l'opération concerne un fournisseur)
     */
    @ManyToOne
    private Fournisseur fournisseur;
    
    /**
     * Boutique associée
     */
    @ManyToOne
    private Boutique boutique;
    
    /**
     * Utilisateur qui a effectué l'opération
     */
    @ManyToOne
    private Utilisateur utilisateur;
    
    /**
     * Commentaire sur l'opération
     */
    private String commentaire;

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

    public CaisseJournaliere getCaisseJournaliere() {
        return caisseJournaliere;
    }

    public void setCaisseJournaliere(CaisseJournaliere caisseJournaliere) {
        this.caisseJournaliere = caisseJournaliere;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public PaiementCommandeFournisseur getPaiementCommandeFournisseur() {
        return paiementCommandeFournisseur;
    }

    public void setPaiementCommandeFournisseur(PaiementCommandeFournisseur paiementCommandeFournisseur) {
        this.paiementCommandeFournisseur = paiementCommandeFournisseur;
    }

    public VersementClientPartenaire getVersementClientPartenaire() {
        return versementClientPartenaire;
    }

    public void setVersementClientPartenaire(VersementClientPartenaire versementClientPartenaire) {
        this.versementClientPartenaire = versementClientPartenaire;
    }

    public CommandeVente getCommandeVente() {
        return commandeVente;
    }

    public void setCommandeVente(CommandeVente commandeVente) {
        this.commandeVente = commandeVente;
    }

    public CommandeFournisseur getCommandeFournisseur() {
        return commandeFournisseur;
    }

    public void setCommandeFournisseur(CommandeFournisseur commandeFournisseur) {
        this.commandeFournisseur = commandeFournisseur;
    }

    public ClientPartenaire getClientPartenaire() {
        return clientPartenaire;
    }

    public void setClientPartenaire(ClientPartenaire clientPartenaire) {
        this.clientPartenaire = clientPartenaire;
    }

    public Fournisseur getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(Fournisseur fournisseur) {
        this.fournisseur = fournisseur;
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

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }
}
