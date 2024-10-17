package org.sid.saranApp.dto;

public class VilleDto {

	private String libelle;
	private String uuid;
	private String uuidPays;
	private String pays;
	private String uuidBoutique;
	private String uuidUtilisateur;
	
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
	public String getUuidPays() {
		return uuidPays;
	}
	public void setUuidPays(String uuidPays) {
		this.uuidPays = uuidPays;
	}
	public String getPays() {
		return pays;
	}
	public void setPays(String pays) {
		this.pays = pays;
	}
	
	
}
