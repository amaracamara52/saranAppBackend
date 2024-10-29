package org.sid.saranApp.dto;

import java.util.Date;

public class LigneCommandeDto extends ResponseDto {

	private String uuid;
	private int quantite;
	private String uuidCommandeVente;
	private String uuidProduit;
	private String uuidBoutique;
	private String uuidUtilisateur;
	private String article;
	private int quantiteRestant;
	private String emplacement;
	private String utilisateur;
	private double prixVente;
	private Date datePeremption;
	private int quantiteStock;


	public String getArticle() {
		return article;
	}

	public Date getDatePeremption() {
		return datePeremption;
	}

	public String getEmplacement() {
		return emplacement;
	}



	public int getQuantite() {
		return quantite;
	}

	public int getQuantiteRestant() {
		return quantiteRestant;
	}

	public String getUtilisateur() {
		return utilisateur;
	}

	public String getUuid() {
		return uuid;
	}

	public String getUuidBoutique() {
		return uuidBoutique;
	}

	public String getUuidCommandeVente() {
		return uuidCommandeVente;
	}

	public String getUuidProduit() {
		return uuidProduit;
	}

	public String getUuidUtilisateur() {
		return uuidUtilisateur;
	}

	public void setArticle(String article) {
		this.article = article;
	}

	public void setDatePeremption(Date datePeremption) {
		this.datePeremption = datePeremption;
	}

	public void setEmplacement(String emplacement) {
		this.emplacement = emplacement;
	}


	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}

	public void setQuantiteRestant(int quantiteRestant) {
		this.quantiteRestant = quantiteRestant;
	}

	public void setUtilisateur(String utilisateur) {
		this.utilisateur = utilisateur;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public void setUuidBoutique(String uuidBoutique) {
		this.uuidBoutique = uuidBoutique;
	}

	public void setUuidCommandeVente(String uuidCommandeVente) {
		this.uuidCommandeVente = uuidCommandeVente;
	}

	public void setUuidProduit(String uuidProduit) {
		this.uuidProduit = uuidProduit;
	}

	public void setUuidUtilisateur(String uuidUtilisateur) {
		this.uuidUtilisateur = uuidUtilisateur;
	}

	public double getPrixVente() {
		return prixVente;
	}

	public void setPrixVente(double prixVente) {
		this.prixVente = prixVente;
	}

	public int getQuantiteStock() {
		return quantiteStock;
	}

	public void setQuantiteStock(int quantiteStock) {
		this.quantiteStock = quantiteStock;
	}
	
	
	
	

}
