/**
 *
 */
package org.sid.saranApp.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 *
 */
@Entity
public class UniteProduit extends AbstractDomainClass {

	@ManyToOne
	private UniteDefiniArticle uniteEntree;
	@ManyToOne
	private Produit produit;

	private int qtiteUniteSortie;

	public Produit getProduit() {
		return produit;
	}

	public int getQtiteUniteSortie() {
		return qtiteUniteSortie;
	}

	public UniteDefiniArticle getUniteEntree() {
		return uniteEntree;
	}

	public void setProduit(Produit produit) {
		this.produit = produit;
	}

	public void setQtiteUniteSortie(int qtiteUniteSortie) {
		this.qtiteUniteSortie = qtiteUniteSortie;
	}

	public void setUniteEntree(UniteDefiniArticle uniteEntree) {
		this.uniteEntree = uniteEntree;
	}

}
