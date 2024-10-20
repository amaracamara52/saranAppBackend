package org.sid.saranApp.dto;

import org.sid.saranApp.enume.StatusCommandeFournisseurEnum;

public class DetailCommandeFournisseurDto {
	private String uuid;
	private String uuidArticle;
	private String article;
	private String categorie;
	private String description;
	private int quantite;
	private double prixAchat;
	private String uuidCommandeFournisseur;
	private String dateCommande;
	private String valeurMarchandise;
	private StatusCommandeFournisseurEnum Status;
	private String uuidBoutique;
	private String uuidUtilisateur;
	private String utilisateur;
	private String unite;
	private int quantiteLivraison;

	public String getArticle() {
		return article;
	}

	public String getCategorie() {
		return categorie;
	}

	public String getDateCommande() {
		return dateCommande;
	}

	public String getDescription() {
		return description;
	}

	public double getPrixAchat() {
		return prixAchat;
	}

	public int getQuantite() {
		return quantite;
	}

	public int getQuantiteLivraison() {
		return quantiteLivraison;
	}

	public StatusCommandeFournisseurEnum getStatus() {
		return Status;
	}

	public String getUnite() {
		return unite;
	}

	public String getUtilisateur() {
		return utilisateur;
	}

	public String getUuid() {
		return uuid;
	}

	public String getUuidArticle() {
		return uuidArticle;
	}

	public String getUuidBoutique() {
		return uuidBoutique;
	}

	public String getUuidCommandeFournisseur() {
		return uuidCommandeFournisseur;
	}

	public String getUuidUtilisateur() {
		return uuidUtilisateur;
	}

	public String getValeurMarchandise() {
		return valeurMarchandise;
	}

	public void setArticle(String article) {
		this.article = article;
	}

	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}

	public void setDateCommande(String dateCommande) {
		this.dateCommande = dateCommande;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setPrixAchat(double prixAchat) {
		this.prixAchat = prixAchat;
	}

	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}

	public void setQuantiteLivraison(int quantiteLivraison) {
		this.quantiteLivraison = quantiteLivraison;
	}

	public void setStatus(StatusCommandeFournisseurEnum status) {
		Status = status;
	}

	public void setUnite(String unite) {
		this.unite = unite;
	}

	public void setUtilisateur(String utilisateur) {
		this.utilisateur = utilisateur;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public void setUuidArticle(String uuidArticle) {
		this.uuidArticle = uuidArticle;
	}

	public void setUuidBoutique(String uuidBoutique) {
		this.uuidBoutique = uuidBoutique;
	}

	public void setUuidCommandeFournisseur(String uuidCommandeFournisseur) {
		this.uuidCommandeFournisseur = uuidCommandeFournisseur;
	}

	public void setUuidUtilisateur(String uuidUtilisateur) {
		this.uuidUtilisateur = uuidUtilisateur;
	}

	public void setValeurMarchandise(String valeurMarchandise) {
		this.valeurMarchandise = valeurMarchandise;
	}

}
