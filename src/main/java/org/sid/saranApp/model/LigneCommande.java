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
	@ManyToOne
	private TypeUniteDeVente typeUniteDeVente;
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

	

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public TypeUniteDeVente getTypeUniteDeVente() {
		return typeUniteDeVente;
	}

	public void setTypeUniteDeVente(TypeUniteDeVente typeUniteDeVente) {
		this.typeUniteDeVente = typeUniteDeVente;
	}

	
}
