package org.sid.saranApp.dto;


public class CommandeVenteDto extends ResponseDto{
	
	private String uuid;
	private String valeurCom;
	private String numeroCom;
	private boolean isPaye;
	private String datePaiement;
	private String id_client;
	private String uuidBoutique;
	private String uuidUtilisateur;
	
	
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getValeurCom() {
		return valeurCom;
	}
	public void setValeurCom(String valeurCom) {
		this.valeurCom = valeurCom;
	}
	public String getNumeroCom() {
		return numeroCom;
	}
	public void setNumeroCom(String numeroCom) {
		this.numeroCom = numeroCom;
	}
	
	public boolean isPaye() {
		return isPaye;
	}
	public void setPaye(boolean isPaye) {
		this.isPaye = isPaye;
	}
	public String getDatePaiement() {
		return datePaiement;
	}
	public void setDatePaiement(String datePaiement) {
		this.datePaiement = datePaiement;
	}
	public String getId_client() {
		return id_client;
	}
	public void setId_client(String id_client) {
		this.id_client = id_client;
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
