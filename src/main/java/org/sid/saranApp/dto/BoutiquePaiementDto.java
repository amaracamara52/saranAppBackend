package org.sid.saranApp.dto;

import java.util.Date;

public class BoutiquePaiementDto {

	private String uuid;
	private String refPaiement;
	private String typePaiment;
	private Date dateDebut;
	private Date dateFin;
	private String key;
	private boolean isValide;
	private String libelleBoutique;
	private String uuidBoutique;
	private String uuidModePaiement;
	private String modePaiement;
	private String uuidUtilisateur;
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getRefPaiement() {
		return refPaiement;
	}
	public void setRefPaiement(String refPaiement) {
		this.refPaiement = refPaiement;
	}
	public String getTypePaiment() {
		return typePaiment;
	}
	public void setTypePaiment(String typePaiment) {
		this.typePaiment = typePaiment;
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
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
	public boolean isValide() {
		return isValide;
	}
	public void setValide(boolean isValide) {
		this.isValide = isValide;
	}
	public String getLibelleBoutique() {
		return libelleBoutique;
	}
	public void setLibelleBoutique(String libelleBoutique) {
		this.libelleBoutique = libelleBoutique;
	}
	public String getUuidBoutique() {
		return uuidBoutique;
	}
	public void setUuidBoutique(String uuidBoutique) {
		this.uuidBoutique = uuidBoutique;
	}
	public String getUuidModePaiement() {
		return uuidModePaiement;
	}
	public void setUuidModePaiement(String uuidModePaiement) {
		this.uuidModePaiement = uuidModePaiement;
	}
	public String getModePaiement() {
		return modePaiement;
	}
	public void setModePaiement(String modePaiement) {
		this.modePaiement = modePaiement;
	}
	public String getUuidUtilisateur() {
		return uuidUtilisateur;
	}
	public void setUuidUtilisateur(String uuidUtilisateur) {
		this.uuidUtilisateur = uuidUtilisateur;
	}
	
	
	

}
