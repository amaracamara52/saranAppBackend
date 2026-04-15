package org.sid.saranApp.dto;

import org.sid.saranApp.enume.EnumStatutCaisse;
import org.sid.saranApp.enume.EnumStatutTransaction;
import org.sid.saranApp.enume.EnumTypeTransaction;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
import java.util.Date;

public class TransactionDto {
    private String uuid;
    private ModePaiementDto modePaiement;
    private BigDecimal montant;
    private Date dateTransaction;
    private String reference;
    private String uuidReceipt;
    private String receipt;
    private String uuidCommandeVente;
    private String numeroCommande;

    @Enumerated(EnumType.STRING)
    private EnumStatutTransaction statutTransaction;
    @Enumerated(EnumType.STRING)
    private EnumTypeTransaction typeTransaction;


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public ModePaiementDto getModePaiement() {
        return modePaiement;
    }

    public void setModePaiement(ModePaiementDto modePaiement) {
        this.modePaiement = modePaiement;
    }

    public BigDecimal getMontant() {
        return montant;
    }

    public void setMontant(BigDecimal montant) {
        this.montant = montant;
    }

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

    public String getUuidReceipt() {
        return uuidReceipt;
    }

    public void setUuidReceipt(String uuidReceipt) {
        this.uuidReceipt = uuidReceipt;
    }

    public String getReceipt() {
        return receipt;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }

    public String getUuidCommandeVente() {
        return uuidCommandeVente;
    }

    public void setUuidCommandeVente(String uuidCommandeVente) {
        this.uuidCommandeVente = uuidCommandeVente;
    }

    public EnumStatutTransaction getStatutTransaction() {
        return statutTransaction;
    }

    public void setStatutTransaction(EnumStatutTransaction statutTransaction) {
        this.statutTransaction = statutTransaction;
    }

    public EnumTypeTransaction getTypeTransaction() {
        return typeTransaction;
    }

    public void setTypeTransaction(EnumTypeTransaction typeTransaction) {
        this.typeTransaction = typeTransaction;
    }

    public String getNumeroCommande() {
        return numeroCommande;
    }

    public void setNumeroCommande(String numeroCommande) {
        this.numeroCommande = numeroCommande;
    }


}
