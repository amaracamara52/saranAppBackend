package org.sid.saranApp.dto;

import java.util.Date;

public class LivraisonCommandeFournisseurDto {
	
	private String uuid;
	private Date dateLivraison;
	private String Heure;
	private String uuidDetailCommandeFournisseur;
	private String uuidBoutique;
	private String uuidCommandeFournisseur;
	private String qte;
	private double prix;
	private String uuidUtilisateur;
	public String getUuidUtilisateur() {
		return uuidUtilisateur;
	}
	public void setUuidUtilisateur(String uuidUtilisateur) {
		this.uuidUtilisateur = uuidUtilisateur;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public Date getDateLivraison() {
		return dateLivraison;
	}
	public void setDateLivraison(Date dateLivraison) {
		this.dateLivraison = dateLivraison;
	}
	public String getHeure() {
		return Heure;
	}
	public void setHeure(String heure) {
		Heure = heure;
	}
	public String getUuidDetailCommandeFournisseur() {
		return uuidDetailCommandeFournisseur;
	}
	public void setUuidDetailCommandeFournisseur(String uuidDetailCommandeFournisseur) {
		this.uuidDetailCommandeFournisseur = uuidDetailCommandeFournisseur;
	}
	public String getUuidBoutique() {
		return uuidBoutique;
	}
	public void setUuidBoutique(String uuidBoutique) {
		this.uuidBoutique = uuidBoutique;
	}
	public String getUuidCommandeFournisseur() {
		return uuidCommandeFournisseur;
	}
	public void setUuidCommandeFournisseur(String uuidCommandeFournisseur) {
		this.uuidCommandeFournisseur = uuidCommandeFournisseur;
	}
	public String getQte() {
		return qte;
	}
	public void setQte(String qte) {
		this.qte = qte;
	}
	public double getPrix() {
		return prix;
	}
	public void setPrix(double prix) {
		this.prix = prix;
	}
	
	
	

}
