package org.sid.saranApp.dto;

import org.sid.saranApp.enume.EnumStatutCaisse;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class CaisseJournaliereDto {

    private String uuid;
    private LocalDate dateCaisse;
    private BigDecimal soldeOuverture;
    private BigDecimal soldeFermeture;
    private BigDecimal totalEncaissement;
    private BigDecimal totalDecaissement;
    private LocalDateTime dateFermeture;
    @Enumerated(EnumType.STRING)
    private EnumStatutCaisse statutCaisse;
    private BigDecimal soldeCalcule;
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

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

    public BigDecimal getSoldeCalcule() {
        return soldeCalcule;
    }

    public void setSoldeCalcule(BigDecimal soldeCalcule) {
        this.soldeCalcule = soldeCalcule;
    }
}
