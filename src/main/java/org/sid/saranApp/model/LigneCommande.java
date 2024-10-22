package org.sid.saranApp.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class LigneCommande extends AbstractDomainClass {

	private int quantite;
	@ManyToOne
	private CommandeVente commandeVente;
	@ManyToOne
	private Produit produit;
	@ManyToOne
	private Boutique boutique;
	private String uniteSortie;
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

	public int getQuantite() {
		return quantite;
	}

	public String getUniteSortie() {
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

	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}

	public void setUniteSortie(String uniteSortie) {
		this.uniteSortie = uniteSortie;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

}
