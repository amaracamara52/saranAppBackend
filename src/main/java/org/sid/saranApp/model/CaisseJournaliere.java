package org.sid.saranApp.model;

import org.sid.saranApp.enume.EnumStatutCaisse;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
public class CaisseJournaliere extends  AbstractDomainClass {

    private LocalDate dateCaisse;
    private BigDecimal soldeOuverture;
    private BigDecimal soldeFermeture;
    private BigDecimal totalEncaissement;
    private BigDecimal totalDecaissement;
    private LocalDateTime dateFermeture;
    @Enumerated(EnumType.STRING)
    private EnumStatutCaisse statutCaisse;
    @ManyToOne
    private Boutique boutique;
    @ManyToOne
    private Utilisateur utilisateur;
    
    /**
     * Liste des opérations de caisse pour cette caisse journalière
     */
    @OneToMany(mappedBy = "caisseJournaliere")
    private java.util.List<OperationCaisse> operations = new java.util.ArrayList<>();

    public LocalDate getDateCaisse() {
        return dateCaisse;
    }

    public void setDateCaisse(LocalDate dateCaisse) {
        this.dateCaisse = dateCaisse;
    }

    public BigDecimal getSoldeOuverture() {
        return soldeOuverture;
    }

    public void setSoldeOuverture(BigDecimal soldeOuverture) {
        this.soldeOuverture = soldeOuverture;
    }

    public BigDecimal getSoldeFermeture() {
        return soldeFermeture;
    }

    public void setSoldeFermeture(BigDecimal soldeFermeture) {
        this.soldeFermeture = soldeFermeture;
    }

    public BigDecimal getTotalEncaissement() {
        return totalEncaissement;
    }

    public void setTotalEncaissement(BigDecimal totalEncaissement) {
        this.totalEncaissement = totalEncaissement;
    }

    public BigDecimal getTotalDecaissement() {
        return totalDecaissement;
    }

    public void setTotalDecaissement(BigDecimal totalDecaissement) {
        this.totalDecaissement = totalDecaissement;
    }

    public LocalDateTime getDateFermeture() {
        return dateFermeture;
    }

    public void setDateFermeture(LocalDateTime dateFermeture) {
        this.dateFermeture = dateFermeture;
    }

    public EnumStatutCaisse getStatutCaisse() {
        return statutCaisse;
    }

    public void setStatutCaisse(EnumStatutCaisse statutCaisse) {
        this.statutCaisse = statutCaisse;
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

    public List<OperationCaisse> getOperations() {
        return operations;
    }

    public void setOperations(List<OperationCaisse> operations) {
        this.operations = operations;
    }
}
