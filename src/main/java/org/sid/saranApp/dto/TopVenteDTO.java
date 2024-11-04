package org.sid.saranApp.dto;

public class TopVenteDTO {
	
	private String libelle;
	private String description;
	private String code;
	private String etagere;
	private String rayon;
	private int quantite;
	private String uuid;
	public TopVenteDTO(String libelle, String description, String code, String etagere, String rayon, int quantite,
			String uuid) {
		super();
		this.libelle = libelle;
		this.description = description;
		this.code = code;
		this.etagere = etagere;
		this.rayon = rayon;
		this.quantite = quantite;
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getEtagere() {
		return etagere;
	}
	public void setEtagere(String etagere) {
		this.etagere = etagere;
	}
	public String getRayon() {
		return rayon;
	}
	public void setRayon(String rayon) {
		this.rayon = rayon;
	}
	public int getQuantite() {
		return quantite;
	}
	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	
	
	
	
	


}
