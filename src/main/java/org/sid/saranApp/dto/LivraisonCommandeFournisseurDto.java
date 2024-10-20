package org.sid.saranApp.dto;

import java.util.Date;

public class LivraisonCommandeFournisseurDto {

	private String uuid;
	private Date dateLivraison;
	private String Heure;
	private String uuidDetailCommandeFournisseur;
	private String uuidBoutique;
	private String uuidCommandeFournisseur;
	private int quantite;
	private double prix;
	private String uuidUtilisateur;

	public Date getDateLivraison() {
		return dateLivraison;
	}

	public String getHeure() {
		return Heure;
	}

	public double getPrix() {
		return prix;
	}

	public int getQuantite() {
		return quantite;
	}

	public String getUuid() {
		return uuid;
	}

	public String getUuidBoutique() {
		return uuidBoutique;
	}

	public String getUuidCommandeFournisseur() {
		return uuidCommandeFournisseur;
	}

	public String getUuidDetailCommandeFournisseur() {
		return uuidDetailCommandeFournisseur;
	}

	public String getUuidUtilisateur() {
		return uuidUtilisateur;
	}

	public void setDateLivraison(Date dateLivraison) {
		this.dateLivraison = dateLivraison;
	}

	public void setHeure(String heure) {
		Heure = heure;
	}

	public void setPrix(double prix) {
		this.prix = prix;
	}

	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public void setUuidBoutique(String uuidBoutique) {
		this.uuidBoutique = uuidBoutique;
	}

	public void setUuidCommandeFournisseur(String uuidCommandeFournisseur) {
		this.uuidCommandeFournisseur = uuidCommandeFournisseur;
	}

	public void setUuidDetailCommandeFournisseur(String uuidDetailCommandeFournisseur) {
		this.uuidDetailCommandeFournisseur = uuidDetailCommandeFournisseur;
	}

	public void setUuidUtilisateur(String uuidUtilisateur) {
		this.uuidUtilisateur = uuidUtilisateur;
	}

}
