package org.sid.saranApp.dto;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.sid.saranApp.enume.StatusCommandeFournisseurEnum;
import org.springframework.format.annotation.DateTimeFormat;

public class ProduitDto extends ResponseDto {

	private String uuid;
	private double prixAchat;
	private double prixVente;
	private int quantite;
	private int quantiteImage;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyy-MM-dd")
	private LocalDate datePeremption;
	private String article;
	private String categorie;
	private String emplacement;
	private String uuidEmplacement;
	private LocalDate dateCommande;
	private String fournisseur;
	private StatusCommandeFournisseurEnum statusCommandeFournisseurEnum;
	private String uuidLivraisonCommandeFournisseur;
	private String uuidBoutique;
	private String uuidUtilisateur;
	private String unite;
	private boolean isFinish;
	private int quantiteVendu;
	private String etagere;

	public String getArticle() {
		return article;
	}

	public String getCategorie() {
		return categorie;
	}

	

	public LocalDate getDateCommande() {
		return dateCommande;
	}

	public void setDateCommande(LocalDate dateCommande) {
		this.dateCommande = dateCommande;
	}

	public LocalDate getDatePeremption() {
		return datePeremption;
	}

	public String getEmplacement() {
		return emplacement;
	}

	public String getEtagere() {
		return etagere;
	}

	public String getFournisseur() {
		return fournisseur;
	}

	public double getPrixAchat() {
		return prixAchat;
	}

	public double getPrixVente() {
		return prixVente;
	}

	public int getQuantite() {
		return quantite;
	}

	public int getQuantiteImage() {
		return quantiteImage;
	}

	public int getQuantiteVendu() {
		return quantiteVendu;
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

	

	public void setDatePeremption(LocalDate datePeremption) {
		this.datePeremption = datePeremption;
	}

	public void setEmplacement(String emplacement) {
		this.emplacement = emplacement;
	}

	public void setEtagere(String etagere) {
		this.etagere = etagere;
	}

	public void setFinish(boolean isFinish) {
		this.isFinish = isFinish;
	}

	public void setFournisseur(String fournisseur) {
		this.fournisseur = fournisseur;
	}

	public void setPrixAchat(double prixAchat) {
		this.prixAchat = prixAchat;
	}

	public void setPrixVente(double prixVente) {
		this.prixVente = prixVente;
	}

	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}

	public void setQuantiteImage(int quantiteImage) {
		this.quantiteImage = quantiteImage;
	}

	public void setQuantiteVendu(int quantiteVendu) {
		this.quantiteVendu = quantiteVendu;
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
