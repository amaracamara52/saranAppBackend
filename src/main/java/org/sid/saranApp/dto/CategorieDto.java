package org.sid.saranApp.dto;

public class CategorieDto {
	
	private String uuid;
	private String libelle;
	private String description;
	private String uuidDomain;
	private String uuidUtilisateur;
	private String uuidBoutique;
	
	
	
	
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
	public String getUuidDomain() {
		return uuidDomain;
	}
	public void setUuidDomain(String uuidDomain) {
		this.uuidDomain = uuidDomain;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	
	
}
