package org.sid.saranApp.model;

import org.sid.saranApp.enume.EnumStatutTransaction;
import org.sid.saranApp.enume.EnumTypeTransaction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
public class Transaction extends AbstractDomainClass {
    private Date dateTransaction;
    private String reference;
    @OneToOne
    private StoredFile storedFile;
    @OneToOne
    private CommandeVente commandeVente;
    @ManyToOne
    private ModePaiement modePaiement;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EnumTypeTransaction type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EnumStatutTransaction statut;

    private BigDecimal montant;
    @OneToOne
    private Boutique boutique;
    @ManyToOne
    private CaisseJournaliere caisse;


    public Date getDateTransaction() {
        return dateTransaction;
    }

    public void setDateTransaction(Date dateTransaction) {
        this.dateTransaction = dateTransaction;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public StoredFile getStoredFile() {
        return storedFile;
    }

    public void setStoredFile(StoredFile storedFile) {
        this.storedFile = storedFile;
    }

    public CommandeVente getCommandeVente() {
        return commandeVente;
    }

    public void setCommandeVente(CommandeVente commandeVente) {
        this.commandeVente = commandeVente;
    }

    public ModePaiement getModePaiement() {
        return modePaiement;
    }

    public void setModePaiement(ModePaiement modePaiement) {
        this.modePaiement = modePaiement;
    }

    public BigDecimal getMontant() {
        return montant;
    }

    public void setMontant(BigDecimal montant) {
        this.montant = montant;
    }

    public EnumTypeTransaction getType() {
        return type;
    }

    public void setType(EnumTypeTransaction type) {
        this.type = type;
    }

    public EnumStatutTransaction getStatut() {
        return statut;
    }

    public void setStatut(EnumStatutTransaction statut) {
        this.statut = statut;
    }

    public Boutique getBoutique() {
        return boutique;
    }

    public void setBoutique(Boutique boutique) {
        this.boutique = boutique;
    }

    public CaisseJournaliere getCaisse() {
        return caisse;
    }

    public void setCaisse(CaisseJournaliere caisse) {
        this.caisse = caisse;
    }
}
