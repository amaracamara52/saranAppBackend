package org.sid.saranApp.dto.rapport;

public class StatistiquesGeneralesDTO {
    private Double montantTotal;
    private Long nombreTotalCommandes;
    private Long clientTotal;
    private Double panierMoyen;
    private Long commandesPayees;
    private Long commandesNonPayees;
    private Double montantCommandesPayees;
    private Double montantCommandesNonPayees;

    // Constructeur principal
    public StatistiquesGeneralesDTO(Double montantTotal, Long nombreTotalCommandes, Long clientTotal) {
        this.montantTotal = montantTotal != null ? montantTotal : 0.0;
        this.nombreTotalCommandes = nombreTotalCommandes != null ? nombreTotalCommandes : 0L;
        this.clientTotal = clientTotal != null ? clientTotal : 0L;
        this.panierMoyen = this.nombreTotalCommandes > 0 ? this.montantTotal / this.nombreTotalCommandes : 0.0;
    }

    // Constructeur complet avec détails des paiements
    public StatistiquesGeneralesDTO(Double montantTotal, Long nombreTotalCommandes, Long clientTotal,
                                    Long commandesPayees, Long commandesNonPayees,
                                    Double montantCommandesPayees, Double montantCommandesNonPayees) {
        this.montantTotal = montantTotal != null ? montantTotal : 0.0;
        this.nombreTotalCommandes = nombreTotalCommandes != null ? nombreTotalCommandes : 0L;
        this.clientTotal = clientTotal != null ? clientTotal : 0L;
        this.panierMoyen = this.nombreTotalCommandes > 0 ? this.montantTotal / this.nombreTotalCommandes : 0.0;
        this.commandesPayees = commandesPayees != null ? commandesPayees : 0L;
        this.commandesNonPayees = commandesNonPayees != null ? commandesNonPayees : 0L;
        this.montantCommandesPayees = montantCommandesPayees != null ? montantCommandesPayees : 0.0;
        this.montantCommandesNonPayees = montantCommandesNonPayees != null ? montantCommandesNonPayees : 0.0;
    }

    // Getters et setters
    public Double getMontantTotal() { return montantTotal; }
    public void setMontantTotal(Double montantTotal) {
        this.montantTotal = montantTotal;
        calculerPanierMoyen();
    }

    public Long getNombreTotalCommandes() { return nombreTotalCommandes; }
    public void setNombreTotalCommandes(Long nombreTotalCommandes) {
        this.nombreTotalCommandes = nombreTotalCommandes;
        calculerPanierMoyen();
    }

    public Long getClientTotal() { return clientTotal; }
    public void setClientTotal(Long clientTotal) { this.clientTotal = clientTotal; }

    public Double getPanierMoyen() { return panierMoyen; }
    public void setPanierMoyen(Double panierMoyen) { this.panierMoyen = panierMoyen; }

    public Long getCommandesPayees() { return commandesPayees; }
    public void setCommandesPayees(Long commandesPayees) { this.commandesPayees = commandesPayees; }

    public Long getCommandesNonPayees() { return commandesNonPayees; }
    public void setCommandesNonPayees(Long commandesNonPayees) { this.commandesNonPayees = commandesNonPayees; }

    public Double getMontantCommandesPayees() { return montantCommandesPayees; }
    public void setMontantCommandesPayees(Double montantCommandesPayees) { this.montantCommandesPayees = montantCommandesPayees; }

    public Double getMontantCommandesNonPayees() { return montantCommandesNonPayees; }
    public void setMontantCommandesNonPayees(Double montantCommandesNonPayees) { this.montantCommandesNonPayees = montantCommandesNonPayees; }

    // Méthode utilitaire pour recalculer le panier moyen
    private void calculerPanierMoyen() {
        this.panierMoyen = this.nombreTotalCommandes != null && this.nombreTotalCommandes > 0 && this.montantTotal != null
                ? this.montantTotal / this.nombreTotalCommandes
                : 0.0;
    }

    // Méthodes utilitaires pour obtenir des pourcentages
    public Double getPourcentageCommandesPayees() {
        return nombreTotalCommandes > 0 ? (commandesPayees.doubleValue() / nombreTotalCommandes.doubleValue()) * 100 : 0.0;
    }

    public Double getPourcentageMontantPaye() {
        return montantTotal > 0 ? (montantCommandesPayees / montantTotal) * 100 : 0.0;
    }
}
