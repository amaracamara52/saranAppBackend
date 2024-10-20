package org.sid.saranApp.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class DetailCommandeFournisseur extends AbstractDomainClass {

	@ManyToOne
	private Article article;
	private int quantite;
	private double prixAchat;
	private String unite;

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

	public String getUnite() {
		return unite;
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

	public void setUnite(String unite) {
		this.unite = unite;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

}
