package org.sid.saranApp.dto;

import java.util.Date;

import org.sid.saranApp.enume.StatusCommandeFournisseurEnum;

public class ProduitDto extends ResponseDto {

	private String uuid;
	private int prixAchat;
	private int prixVente;
	private int quantite;
	private int quantiteImage;
	private Date datePeremption;
	private String article;
	private String categorie;
	private String emplacement;
	private String uuidEmplacement;
	private String dateCommande;
	private String fournisseur;
	private StatusCommandeFournisseurEnum statusCommandeFournisseurEnum;
	private String uuidLivraisonCommandeFournisseur;
	private String uuidBoutique;
	private String uuidUtilisateur;
	private String unite;
	private boolean isFinish;

	public String getArticle() {
		return article;
	}

	public String getCategorie() {
		return categorie;
	}

	public String getDateCommande() {
		return dateCommande;
	}

	public Date getDatePeremption() {
		return datePeremption;
	}

	public String getEmplacement() {
		return emplacement;
	}

	public String getFournisseur() {
		return fournisseur;
	}

	public int getPrixAchat() {
		return prixAchat;
	}

	public int getPrixVente() {
		return prixVente;
	}

	public int getQuantite() {
		return quantite;
	}

	public int getQuantiteImage() {
		return quantiteImage;
	}

	public StatusCommandeFournisseurEnum getStatusCommandeFournisseurEnum() {
		return statusCommandeFournisseurEnum;
	}

	public String getUnite() {
		return unite;
	}

	public String getUuid() {
		return uuid;
	}

	public String getUuidBoutique() {
		return uuidBoutique;
	}

	public String getUuidEmplacement() {
		return uuidEmplacement;
	}

	public String getUuidLivraisonCommandeFournisseur() {
		return uuidLivraisonCommandeFournisseur;
	}

	public String getUuidUtilisateur() {
		return uuidUtilisateur;
	}

	public boolean isFinish() {
		return isFinish;
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

	public void setDatePeremption(Date datePeremption) {
		this.datePeremption = datePeremption;
	}

	public void setEmplacement(String emplacement) {
		this.emplacement = emplacement;
	}

	public void setFinish(boolean isFinish) {
		this.isFinish = isFinish;
	}

	public void setFournisseur(String fournisseur) {
		this.fournisseur = fournisseur;
	}

	public void setPrixAchat(int prixAchat) {
		this.prixAchat = prixAchat;
	}

	public void setPrixVente(int prixVente) {
		this.prixVente = prixVente;
	}

	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}

	public void setQuantiteImage(int quantiteImage) {
		this.quantiteImage = quantiteImage;
	}

	public void setStatusCommandeFournisseurEnum(StatusCommandeFournisseurEnum statusCommandeFournisseurEnum) {
		this.statusCommandeFournisseurEnum = statusCommandeFournisseurEnum;
	}

	public void setUnite(String unite) {
		this.unite = unite;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public void setUuidBoutique(String uuidBoutique) {
		this.uuidBoutique = uuidBoutique;
	}

	public void setUuidEmplacement(String uuidEmplacement) {
		this.uuidEmplacement = uuidEmplacement;
	}

	public void setUuidLivraisonCommandeFournisseur(String uuidLivraisonCommandeFournisseur) {
		this.uuidLivraisonCommandeFournisseur = uuidLivraisonCommandeFournisseur;
	}

	public void setUuidUtilisateur(String uuidUtilisateur) {
		this.uuidUtilisateur = uuidUtilisateur;
	}

}
