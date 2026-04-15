package org.sid.saranApp.dto;

public class ModePaiementDto {
	
	private String uuid;
	private String libelle;
	private String description;
	private String uuidBoutique;
	private String uuidUtilisateur;
	private String boutique;

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
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public String getBoutique() {
		return boutique;
	}

	public void setBoutique(String boutique) {
		this.boutique = boutique;
	}
}
