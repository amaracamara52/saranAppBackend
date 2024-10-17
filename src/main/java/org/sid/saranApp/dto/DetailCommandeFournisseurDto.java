package org.sid.saranApp.dto;

public class DetailCommandeFournisseurDto {
	
	private String uuid;
	private String numeroCommande;
	private String nomClient;
	private String quantiteCommande;
	private String Prix;
	private String uuidCommandeFournisseur;
	private String uuidBoutique;
	private String uuidUtilisateur;
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getNumeroCommande() {
		return numeroCommande;
	}
	public void setNumeroCommande(String numeroCommande) {
		this.numeroCommande = numeroCommande;
	}
	public String getNomClient() {
		return nomClient;
	}
	public void setNomClient(String nomClient) {
		this.nomClient = nomClient;
	}
	public String getQuantiteCommande() {
		return quantiteCommande;
	}
	public void setQuantiteCommande(String quantiteCommande) {
		this.quantiteCommande = quantiteCommande;
	}
	public String getPrix() {
		return Prix;
	}
	public void setPrix(String prix) {
		Prix = prix;
	}
	public String getUuidCommandeFournisseur() {
		return uuidCommandeFournisseur;
	}
	public void setUuidCommandeFournisseur(String uuidCommandeFournisseur) {
		this.uuidCommandeFournisseur = uuidCommandeFournisseur;
	}
	public String getUuidBoutique() {
		return uuidBoutique;
	}
	public void setUuidBoutique(String uuidBoutique) {
		this.uuidBoutique = uuidBoutique;
	}
	public String getUuidUtilisateur() {
		return uuidUtilisateur;
	}
	public void setUuidUtilisateur(String uuidUtilisateur) {
		this.uuidUtilisateur = uuidUtilisateur;
	}
	
	
	

}
