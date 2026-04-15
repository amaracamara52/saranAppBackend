package org.sid.saranApp.dto;

import java.math.BigDecimal;
import java.util.List;

/**
 * DTO pour la situation de caisse
 */
public class SituationCaisseDto {
    
    /**
     * Solde d'ouverture de la caisse
     */
    private BigDecimal soldeOuverture;
    
    /**
     * Total des encaissements
     */
    private BigDecimal totalEncaissements;
    
    /**
     * Total des décaissements
     */
    private BigDecimal totalDecaissements;
    
    /**
     * Solde actuel (soldeOuverture + encaissements - décaissements)
     */
    private BigDecimal soldeActuel;
    
    /**
     * Liste des opérations d'encaissement
     */
    private List<OperationCaisseDto> encaissements;
    
    /**
     * Liste des opérations de décaissement
     */
    private List<OperationCaisseDto> decaissements;
    
    /**
     * Liste de toutes les opérations
     */
    private List<OperationCaisseDto> toutesOperations;
    
    /**
     * Nombre total d'opérations
     */
    private int nombreOperations;
    
    /**
     * Pour un client partenaire : créance restante
     */
    private BigDecimal creanceRestante;
    
    /**
     * Pour un fournisseur : dette restante
     */
    private BigDecimal detteRestante;

    public BigDecimal getSoldeOuverture() {
        return soldeOuverture;
    }

    public void setSoldeOuverture(BigDecimal soldeOuverture) {
        this.soldeOuverture = soldeOuverture;
    }

    public BigDecimal getTotalEncaissements() {
        return totalEncaissements;
    }

    public void setTotalEncaissements(BigDecimal totalEncaissements) {
        this.totalEncaissements = totalEncaissements;
    }

    public BigDecimal getTotalDecaissements() {
        return totalDecaissements;
    }

    public void setTotalDecaissements(BigDecimal totalDecaissements) {
        this.totalDecaissements = totalDecaissements;
    }

    public BigDecimal getSoldeActuel() {
        return soldeActuel;
    }

    public void setSoldeActuel(BigDecimal soldeActuel) {
        this.soldeActuel = soldeActuel;
    }

    public List<OperationCaisseDto> getEncaissements() {
        return encaissements;
    }

    public void setEncaissements(List<OperationCaisseDto> encaissements) {
        this.encaissements = encaissements;
    }

    public List<OperationCaisseDto> getDecaissements() {
        return decaissements;
    }

    public void setDecaissements(List<OperationCaisseDto> decaissements) {
        this.decaissements = decaissements;
    }

    public List<OperationCaisseDto> getToutesOperations() {
        return toutesOperations;
    }

    public void setToutesOperations(List<OperationCaisseDto> toutesOperations) {
        this.toutesOperations = toutesOperations;
    }

    public int getNombreOperations() {
        return nombreOperations;
    }

    public void setNombreOperations(int nombreOperations) {
        this.nombreOperations = nombreOperations;
    }

    public BigDecimal getCreanceRestante() {
        return creanceRestante;
    }

    public void setCreanceRestante(BigDecimal creanceRestante) {
        this.creanceRestante = creanceRestante;
    }

    public BigDecimal getDetteRestante() {
        return detteRestante;
    }

    public void setDetteRestante(BigDecimal detteRestante) {
        this.detteRestante = detteRestante;
    }
}
