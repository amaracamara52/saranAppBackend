package org.sid.saranApp.dto;

public class PaysDto {
	
	private String libelle;
	private String uuid;
	private String uuidBouique;
	private String uuidUtilisateur;
	public String getUuidBouique() {
		return uuidBouique;
	}
	public void setUuidBouique(String uuidBouique) {
		this.uuidBouique = uuidBouique;
	}
	public String getUuidUtilisateur() {
		return uuidUtilisateur;
	}
	public void setUuidUtilisateur(String uuidUtilisateur) {
		this.uuidUtilisateur = uuidUtilisateur;
	}
	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	

}
