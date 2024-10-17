package org.sid.saranApp.dto;



public class LigneCommandeDto extends ResponseDto{

	private String uuid;
	private String qte;
	private String uuidCommandeVente;
	private String uuidProduit;
	private String uuidBoutique;
	private String uuidUtilisateur;
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	
	public String getQte() {
		return qte;
	}
	public void setQte(String qte) {
		this.qte = qte;
	}
	
	public String getUuidCommandeVente() {
		return uuidCommandeVente;
	}
	public void setUuidCommandeVente(String uuidCommandeVente) {
		this.uuidCommandeVente = uuidCommandeVente;
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
	public String getUuidUtilisateur() {
		return uuidUtilisateur;
	}
	public void setUuidUtilisateur(String uuidUtilisateur) {
		this.uuidUtilisateur = uuidUtilisateur;
	}
	
	
	
}
