package org.sid.saranApp.dto;

public class PaysDto {
	
	private String libelle;
	private String monnaie;
	private String capitale;
	private int nombreVille;
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
	public String getMonnaie() {
		return monnaie;
	}
	public void setMonnaie(String monnaie) {
		this.monnaie = monnaie;
	}
	public String getCapitale() {
		return capitale;
	}
	public void setCapitale(String capitale) {
		this.capitale = capitale;
	}
	public int getNombreVille() {
		return nombreVille;
	}
	public void setNombreVille(int nombreVille) {
		this.nombreVille = nombreVille;
	}
	
	
	
	

}
