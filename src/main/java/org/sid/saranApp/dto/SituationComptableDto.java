package org.sid.saranApp.dto;

import java.util.List;

/**
 * DTO pour la situation comptable (dettes et créances)
 */
public class SituationComptableDto {
    
    /**
     * Situation des fournisseurs (dettes)
     */
    private List<SituationFournisseurDto> situationsFournisseurs;
    
    /**
     * Situation des clients partenaires (créances)
     */
    private List<SituationClientPartenaireDto> situationsClientsPartenaires;
    
    /**
     * Total des dettes envers les fournisseurs
     */
    private double totalDettesFournisseurs;
    
    /**
     * Total des créances des clients partenaires
     */
    private double totalCreancesClientsPartenaires;
    
    /**
     * Solde net (créances - dettes)
     */
    private double soldeNet;

    public List<SituationFournisseurDto> getSituationsFournisseurs() {
        return situationsFournisseurs;
    }

    public void setSituationsFournisseurs(List<SituationFournisseurDto> situationsFournisseurs) {
        this.situationsFournisseurs = situationsFournisseurs;
    }

    public List<SituationClientPartenaireDto> getSituationsClientsPartenaires() {
        return situationsClientsPartenaires;
    }

    public void setSituationsClientsPartenaires(List<SituationClientPartenaireDto> situationsClientsPartenaires) {
        this.situationsClientsPartenaires = situationsClientsPartenaires;
    }

    public double getTotalDettesFournisseurs() {
        return totalDettesFournisseurs;
    }

    public void setTotalDettesFournisseurs(double totalDettesFournisseurs) {
        this.totalDettesFournisseurs = totalDettesFournisseurs;
    }

    public double getTotalCreancesClientsPartenaires() {
        return totalCreancesClientsPartenaires;
    }

    public void setTotalCreancesClientsPartenaires(double totalCreancesClientsPartenaires) {
        this.totalCreancesClientsPartenaires = totalCreancesClientsPartenaires;
    }

    public double getSoldeNet() {
        return soldeNet;
    }

    public void setSoldeNet(double soldeNet) {
        this.soldeNet = soldeNet;
    }
}
