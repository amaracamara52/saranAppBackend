package org.sid.saranApp.dto.rapport;

public class TopProduitDTO {

    private String produitUuid;
    private String libelleArticle;
    private Long totalQuantiteVendue;
    private Double prixVente;
    private String categorieLibelle;

    // Constructeur avec 4 paramètres (sans catégorie)
    public TopProduitDTO(String produitUuid, String libelleArticle, Long totalQuantiteVendue, Double prixVente) {
        this.produitUuid = produitUuid;
        this.libelleArticle = libelleArticle;
        this.totalQuantiteVendue = totalQuantiteVendue;
        this.prixVente = prixVente;

    }

    // Constructeur avec 5 paramètres (avec catégorie)
    public TopProduitDTO(String produitUuid, String libelleArticle, Long totalQuantiteVendue, Double prixVente, String categorieLibelle) {
        this.produitUuid = produitUuid;
        this.libelleArticle = libelleArticle;
        this.totalQuantiteVendue = totalQuantiteVendue;
        this.prixVente = prixVente;
        this.categorieLibelle = categorieLibelle;
    }

    // Getters et setters
    public String getProduitUuid() { return produitUuid; }
    public void setProduitUuid(String produitUuid) { this.produitUuid = produitUuid; }

    public String getLibelleArticle() { return libelleArticle; }
    public void setLibelleArticle(String libelleArticle) { this.libelleArticle = libelleArticle; }

    public Long getTotalQuantiteVendue() { return totalQuantiteVendue; }
    public void setTotalQuantiteVendue(Long totalQuantiteVendue) { this.totalQuantiteVendue = totalQuantiteVendue; }

    public Double getPrixVente() { return prixVente; }
    public void setPrixVente(Double prixVente) { this.prixVente = prixVente; }

    public String getCategorieLibelle() { return categorieLibelle; }
    public void setCategorieLibelle(String categorieLibelle) { this.categorieLibelle = categorieLibelle; }
}
