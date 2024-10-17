package org.sid.saranApp.dto;

import java.util.Date;

public class ParametreDto {
	
	private String uuid;
	private String libelle;
	private String description;
	private String valeur;
	private String typeParametre;
	private Date dateDebut;
	private Date dateFin;
	private String uuidProduit;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getValeur() {
		return valeur;
	}
	public void setValeur(String valeur) {
		this.valeur = valeur;
	}
	public String getTypeParametre() {
		return typeParametre;
	}
	public void setTypeParametre(String typeParametre) {
		this.typeParametre = typeParametre;
	}
	public Date getDateDebut() {
		return dateDebut;
	}
	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}
	public Date getDateFin() {
		return dateFin;
	}
	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}
	public String getUuidProduit() {
		return uuidProduit;
	}
	public void setUuidProduit(String uuidProduit) {
		this.uuidProduit = uuidProduit;
	}
	public String getUuidBoutique() {
		return uuidBoutique;
	}
	public void setUuidBoutique(String uuidBoutique) {
		this.uuidBoutique = uuidBoutique;
	}
	
	
	


}
