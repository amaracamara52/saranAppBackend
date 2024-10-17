package org.sid.saranApp.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class LigneCommande extends AbstractDomainClass{

	private String  qte;
	
	@ManyToOne
	private CommandeVente commandeVente;
	
	@ManyToOne
	private Produit produit;
	@ManyToOne
	private Boutique boutique;
	@ManyToOne
	private Utilisateur utilisateur;
	

	
	public Boutique getBoutique() {
		return boutique;
	}

	public void setBoutique(Boutique boutique) {
		this.boutique = boutique;
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public String getQte() {
		return qte;
	}

	public void setQte(String qte) {
		this.qte = qte;
	}

	public CommandeVente getCommandeVente() {
		return commandeVente;
	}

	public void setCommandeVente(CommandeVente commandeVente) {
		this.commandeVente = commandeVente;
	}

	public Produit getProduit() {
		return produit;
	}

	public void setProduit(Produit produit) {
		this.produit = produit;
	}

}
