package org.sid.saranApp.dto;

public class ArticleDto {

	private String uuid;
	private String libelle;
	private String description;
	private String uuidCategorie;
	private String categorie;
	private String uuidBoutique;
	private String uuidUtilisateur;
	private int quantiteDansCarton;

	public String getCategorie() {
		return categorie;
	}

	public String getDescription() {
		return description;
	}

	public String getLibelle() {
		return libelle;
	}

	public int getQuantiteDansCarton() {
		return quantiteDansCarton;
	}

	public String getUuid() {
		return uuid;
	}

	public String getUuidBoutique() {
		return uuidBoutique;
	}

	public String getUuidCategorie() {
		return uuidCategorie;
	}

	public String getUuidUtilisateur() {
		return uuidUtilisateur;
	}

	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public void setQuantiteDansCarton(int quantiteDansCarton) {
		this.quantiteDansCarton = quantiteDansCarton;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public void setUuidBoutique(String uuidBoutique) {
		this.uuidBoutique = uuidBoutique;
	}

	public void setUuidCategorie(String uuidCategorie) {
		this.uuidCategorie = uuidCategorie;
	}

	public void setUuidUtilisateur(String uuidUtilisateur) {
		this.uuidUtilisateur = uuidUtilisateur;
	}

}
