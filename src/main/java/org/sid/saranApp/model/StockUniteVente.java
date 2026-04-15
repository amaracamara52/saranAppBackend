package org.sid.saranApp.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * Entité pour gérer les quantités en stock par produit et par unité de vente
 * Les unités de vente sont définies au niveau de l'Article, mais les quantités sont gérées par Produit
 */
@Entity
public class StockUniteVente extends AbstractDomainClass {

    @ManyToOne
    private Produit produit;
    
    @ManyToOne
    private TypeUniteDeVente typeUniteDeVente;
    
    /**
     * Quantité en stock pour ce produit dans cette unité de vente
     */
    private int qtite;

    private double price;
 



    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public TypeUniteDeVente getTypeUniteDeVente() {
        return typeUniteDeVente;
    }

    public void setTypeUniteDeVente(TypeUniteDeVente typeUniteDeVente) {
        this.typeUniteDeVente = typeUniteDeVente;
    }

    public int getQtite() {
        return qtite;
    }

    public void setQtite(int qtite) {
        this.qtite = qtite;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

  
}
