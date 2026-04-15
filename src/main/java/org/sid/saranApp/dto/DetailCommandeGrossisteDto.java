package org.sid.saranApp.dto;

/**
 * DTO pour les détails d'une commande grossiste
 */
public class DetailCommandeGrossisteDto {
    
    private String uuid;
    private String uuidArticle;
    private String uuidProduit;
    private String uuidCommandeGrossiste;
    private String uuidTypeUniteDeVente;
    private String libelleArticle;
    private String categorie;
    private int quantite;
    private double prixUnitaire;
    private double montantTotal;
    private String typeUniteEnum;
    private int unite;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUuidArticle() {
        return uuidArticle;
    }

    public void setUuidArticle(String uuidArticle) {
        this.uuidArticle = uuidArticle;
    }

    public String getUuidProduit() {
        return uuidProduit;
    }

    public void setUuidProduit(String uuidProduit) {
        this.uuidProduit = uuidProduit;
    }

    public String getUuidCommandeGrossiste() {
        return uuidCommandeGrossiste;
    }

    public void setUuidCommandeGrossiste(String uuidCommandeGrossiste) {
        this.uuidCommandeGrossiste = uuidCommandeGrossiste;
    }

    public String getUuidTypeUniteDeVente() {
        return uuidTypeUniteDeVente;
    }

    public void setUuidTypeUniteDeVente(String uuidTypeUniteDeVente) {
        this.uuidTypeUniteDeVente = uuidTypeUniteDeVente;
    }

    public String getLibelleArticle() {
        return libelleArticle;
    }

    public void setLibelleArticle(String libelleArticle) {
        this.libelleArticle = libelleArticle;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public double getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public double getMontantTotal() {
        return montantTotal;
    }

    public void setMontantTotal(double montantTotal) {
        this.montantTotal = montantTotal;
    }

    public String getTypeUniteEnum() {
        return typeUniteEnum;
    }

    public void setTypeUniteEnum(String typeUniteEnum) {
        this.typeUniteEnum = typeUniteEnum;
    }

    public int getUnite() {
        return unite;
    }

    public void setUnite(int unite) {
        this.unite = unite;
    }
}
