package org.sid.saranApp.model;

import org.sid.saranApp.enume.TypeUniteEnum;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * Définition des unités de vente pour un Article
 * Les quantités en stock sont gérées par StockUniteVente (lié au Produit)
 */
@Entity
public class TypeUniteDeVente extends  AbstractDomainClass {
    @Enumerated(EnumType.STRING)
    private TypeUniteEnum typeUniteEnum;
    
    /**
     * Facteur de conversion vers l'unité de base (ex: 12 pour CARTON signifie 1 carton = 12 pièces)
     */
    private int unite;

    /**
     * Colonne historique encore présente en base (NOT NULL). Le prix courant côté stock est sur {@link StockUniteVente}.
     */
    @Column(name = "price", nullable = false)
    private double price;

    /**
     * Colonne encore exigée en base (NOT NULL). La quantité par produit / boutique est portée par {@link StockUniteVente#qtite}.
     */
    @Column(nullable = false)
    private int qtite;
    
    /**
     * Article auquel cette unité de vente appartient
     */
    @ManyToOne
    private Article article;
    
    /**
     * Liste des stocks pour cette unité de vente (un par produit)
     */
    @OneToMany(mappedBy = "typeUniteDeVente")
    private List<StockUniteVente> stockUniteVentes = new ArrayList<>();

    public TypeUniteEnum getTypeUniteEnum() {
        return typeUniteEnum;
    }

    public void setTypeUniteEnum(TypeUniteEnum typeUniteEnum) {
        this.typeUniteEnum = typeUniteEnum;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

	public int getUnite() {
		return unite;
	}

	public void setUnite(int unite) {
		this.unite = unite;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQtite() {
		return qtite;
	}

	public void setQtite(int qtite) {
		this.qtite = qtite;
	}

    public List<StockUniteVente> getStockUniteVentes() {
        return stockUniteVentes;
    }

    public void setStockUniteVentes(List<StockUniteVente> stockUniteVentes) {
        this.stockUniteVentes = stockUniteVentes;
    }
}
