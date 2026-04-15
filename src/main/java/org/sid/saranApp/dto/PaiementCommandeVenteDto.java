package org.sid.saranApp.dto;

public class PaiementCommandeVenteDto {

	private String uuid;
	private String montantVerse;
	private String dateVersement;
	private String uuidUtilisateur;
	private String uuidBoutique;
	private String uuidCommandeVente;
	private String uuidRecuVersement;
	private String fileRecuVersement;
	
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getMontantVerse() {
		return montantVerse;
	}
	public void setMontantVerse(String montantVerse) {
		this.montantVerse = montantVerse;
	}
	public String getDateVersement() {
		return dateVersement;
	}
	public void setDateVersement(String dateVersement) {
		this.dateVersement = dateVersement;
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
	public String getUuidCommandeVente() {
		return uuidCommandeVente;
	}
	public void setUuidCommandeVente(String uuidCommandeVente) {
		this.uuidCommandeVente = uuidCommandeVente;
	}
	public String getUuidRecuVersement() {
		return uuidRecuVersement;
	}
	public void setUuidRecuVersement(String uuidRecuVersement) {
		this.uuidRecuVersement = uuidRecuVersement;
	}
	public String getFileRecuVersement() {
		return fileRecuVersement;
	}
	public void setFileRecuVersement(String fileRecuVersement) {
		this.fileRecuVersement = fileRecuVersement;
	}
	
	
	
	
}
