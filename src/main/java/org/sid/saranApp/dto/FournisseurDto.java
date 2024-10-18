package org.sid.saranApp.dto;

public class FournisseurDto {
	private String uuid;
	private String nom;
	private String prenom;
	private String telephone;
	private String email;
	private String uuidVille;
	private String ville;
	private String uuidBoutique;
	private String uuidUtilisateur;

	public String getEmail() {
		return email;
	}

	public String getNom() {
		return nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public String getTelephone() {
		return telephone;
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

	public String getUuidVille() {
		return uuidVille;
	}

	public String getVille() {
		return ville;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
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

	public void setUuidVille(String uuidVille) {
		this.uuidVille = uuidVille;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

}
