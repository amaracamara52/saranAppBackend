package org.sid.saranApp.dto;

public class EtagereRayonDto {

	private String uuid;
	private String uuidEtagere;
	private String libelle;
	private int capacite;
	private boolean isFull;
	private String rayon;
	private String uuidRayon;
	private String boutique;
	private String uuidBoutique;
	private String code;
	private String color;
	private String utilisateur;
	private String uuidUtilisateur;

	public String getBoutique() {
		return boutique;
	}

	public int getCapacite() {
		return capacite;
	}

	public String getCode() {
		return code;
	}

	public String getColor() {
		return color;
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

	public String getUuidEtagere() {
		return uuidEtagere;
	}

	public String getUuidRayon() {
		return uuidRayon;
	}

	public String getUuidUtilisateur() {
		return uuidUtilisateur;
	}

	public boolean isFull() {
		return isFull;
	}

	public void setBoutique(String boutique) {
		this.boutique = boutique;
	}

	public void setCapacite(int capacite) {
		this.capacite = capacite;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public void setFull(boolean isFull) {
		this.isFull = isFull;
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

	public void setUuidEtagere(String uuidEtagere) {
		this.uuidEtagere = uuidEtagere;
	}

	public void setUuidRayon(String uuidRayon) {
		this.uuidRayon = uuidRayon;
	}

	public void setUuidUtilisateur(String uuidUtilisateur) {
		this.uuidUtilisateur = uuidUtilisateur;
	}

}
