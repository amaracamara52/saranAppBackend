package org.sid.saranApp.dto;



public class ProduitDto extends ResponseDto{

	private String uuid;
	private String prixAchat;
	private String prixVente;
	private int qte;
	private String uuidLivraisonCommandeFournisseur;
	private String uuidBoutique;
	private String uuidUtilisateur;
	
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
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getPrixAchat() {
		return prixAchat;
	}
	public void setPrixAchat(String prixAchat) {
		this.prixAchat = prixAchat;
	}
	public String getPrixVente() {
		return prixVente;
	}
	public void setPrixVente(String prixVente) {
		this.prixVente = prixVente;
	}
	
	public int getQte() {
		return qte;
	}
	public void setQte(int qte) {
		this.qte = qte;
	}
	public String getUuidLivraisonCommandeFournisseur() {
		return uuidLivraisonCommandeFournisseur;
	}
	public void setUuidLivraisonCommandeFournisseur(String uuidLivraisonCommandeFournisseur) {
		this.uuidLivraisonCommandeFournisseur = uuidLivraisonCommandeFournisseur;
	}
	
	
	
}
