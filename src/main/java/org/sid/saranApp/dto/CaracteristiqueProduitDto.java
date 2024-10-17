package org.sid.saranApp.dto;



public class CaracteristiqueProduitDto extends ResponseDto{

	private String uuid;
	private String value;
	private String uuidProduit;
	private String uuidUtilisateur;
	private String uuidBoutique;
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getUuidProduit() {
		return uuidProduit;
	}
	public void setUuidProduit(String uuidProduit) {
		this.uuidProduit = uuidProduit;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
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
