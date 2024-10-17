package org.sid.saranApp.dto;

public class PaiementCommandeFournisseurDto {

	private String uuid;
	private String prixPayes;
	private String datePaiement;
	private String uuidUtilisateur;
	private String uuidBoutique;
	private String uuidCommandeFournisseur;
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getPrixPayes() {
		return prixPayes;
	}
	public void setPrixPayes(String prixPayes) {
		this.prixPayes = prixPayes;
	}
	public String getDatePaiement() {
		return datePaiement;
	}
	public void setDatePaiement(String datePaiement) {
		this.datePaiement = datePaiement;
	}
	public String getUuidUtilisateur() {
		return uuidUtilisateur;
	}
	public void setUuidUtilisateur(String uuidUtilisateur) {
		this.uuidUtilisateur = uuidUtilisateur;
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
	
	
	
}
