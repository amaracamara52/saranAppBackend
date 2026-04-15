package org.sid.saranApp.dto.mobile;

public class StatistiqueDto {
    private int nombreTotalDeVente;
    private int nombreTotalDeArticle;
    private double montantTotalDeVente;
    private double montantTotalDeArticle;

    public int getNombreTotalDeVente() {
        return nombreTotalDeVente;
    }

    public void setNombreTotalDeVente(int nombreTotalDeVente) {
        this.nombreTotalDeVente = nombreTotalDeVente;
    }

    public int getNombreTotalDeArticle() {
        return nombreTotalDeArticle;
    }

    public void setNombreTotalDeArticle(int nombreTotalDeArticle) {
        this.nombreTotalDeArticle = nombreTotalDeArticle;
    }

    public double getMontantTotalDeVente() {
        return montantTotalDeVente;
    }

    public void setMontantTotalDeVente(double montantTotalDeVente) {
        this.montantTotalDeVente = montantTotalDeVente;
    }

    public double getMontantTotalDeArticle() {
        return montantTotalDeArticle;
    }

    public void setMontantTotalDeArticle(double montantTotalDeArticle) {
        this.montantTotalDeArticle = montantTotalDeArticle;
    }
}
