package org.sid.saranApp.dto;

public class EtagereRayonDto {

	private String uuid;
	private String libelle;
	private String rayon;
	private String emplacement;
	private String boutique;
	private String uuidBoutique;
	private String utilisateur;
	private String uuidUtilisateur;
	private String code;

	public String getBoutique() {
		return boutique;
	}

	public String getCode() {
		return code;
	}

	public String getEmplacement() {
		return emplacement;
	}

	public String getLibelle() {
		return libelle;
	}

	public String getRayon() {
		return rayon;
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

	public String getUuidUtilisateur() {
		return uuidUtilisateur;
	}

	public void setBoutique(String boutique) {
		this.boutique = boutique;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setEmplacement(String emplacement) {
		this.emplacement = emplacement;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public void setRayon(String rayon) {
		this.rayon = rayon;
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

	public void setUuidUtilisateur(String uuidUtilisateur) {
		this.uuidUtilisateur = uuidUtilisateur;
	}

}
