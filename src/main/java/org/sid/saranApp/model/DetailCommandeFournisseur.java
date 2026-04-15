package org.sid.saranApp.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class DetailCommandeFournisseur extends AbstractDomainClass {

	@ManyToOne
	private Article article;
	private int quantite;
	private double prixAchat;
	
	/**
	 * Unité de vente (String) pour compatibilité avec l'ancien système
	 */
	private String unite;
	
	/**
	 * Unité de vente utilisée pour cette commande (optionnel, pour meilleure intégration)
	 */
	@ManyToOne
	private TypeUniteDeVente typeUniteDeVente;

	@ManyToOne
	private CommandeFournisseur commandeFournisseur;
	@ManyToOne
	private Boutique boutique;
	@ManyToOne
	private Utilisateur utilisateur;

	public Article getArticle() {
		return article;
	}

	public Boutique getBoutique() {
		return boutique;
	}

	public CommandeFournisseur getCommandeFournisseur() {
		return commandeFournisseur;
	}

	public double getPrixAchat() {
		return prixAchat;
	}

	public int getQuantite() {
		return quantite;
	}



	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public void setBoutique(Boutique boutique) {
		this.boutique = boutique;
	}

	public void setCommandeFournisseur(CommandeFournisseur commandeFournisseur) {
		this.commandeFournisseur = commandeFournisseur;
	}

	public void setPrixAchat(double prixAchat) {
		this.prixAchat = prixAchat;
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

	public String getUnite() {
		return unite;
	}

	public void setUnite(String unite) {
		this.unite = unite;
	}

}
