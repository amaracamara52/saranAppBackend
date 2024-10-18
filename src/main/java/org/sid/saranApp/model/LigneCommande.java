package org.sid.saranApp.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class LigneCommande extends AbstractDomainClass {

	private String qte;
	@ManyToOne
	private CommandeVente commandeVente;
	@ManyToOne
	private Produit produit;
	@ManyToOne
	private Boutique boutique;
	@ManyToOne
	private UniteProduit uniteSortie;
	@ManyToOne
	private Utilisateur utilisateur;

	public Boutique getBoutique() {
		return boutique;
	}

	public CommandeVente getCommandeVente() {
		return commandeVente;
	}

	public Produit getProduit() {
		return produit;
	}

	public String getQte() {
		return qte;
	}

	public UniteProduit getUniteSortie() {
		return uniteSortie;
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setBoutique(Boutique boutique) {
		this.boutique = boutique;
	}

	public void setCommandeVente(CommandeVente commandeVente) {
		this.commandeVente = commandeVente;
	}

	public void setProduit(Produit produit) {
		this.produit = produit;
	}

	public void setQte(String qte) {
		this.qte = qte;
	}

	public void setUniteSortie(UniteProduit uniteSortie) {
		this.uniteSortie = uniteSortie;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

}
