package org.sid.saranApp.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.sid.saranApp.enume.StatusCommandeVenteEnum;

public class CommandeVenteDto extends ResponseDto {

	private String uuid;
	private String montantCommande;
	private String numeroCommande;
	private boolean isPaye;
	private Date datePaiement;
	private String id_client;
	private String uuidBoutique;
	private String uuidUtilisateur;
	private StatusCommandeVenteEnum status;
	private int nombreArticle;
	List<LigneCommandeDto> ligneCommandeDtos = new ArrayList<LigneCommandeDto>();

	public Date getDatePaiement() {
		return datePaiement;
	}

	public String getId_client() {
		return id_client;
	}

	public List<LigneCommandeDto> getLigneCommandeDtos() {
		return ligneCommandeDtos;
	}

	public String getMontantCommande() {
		return montantCommande;
	}

	public int getNombreArticle() {
		return nombreArticle;
	}

	public String getNumeroCommande() {
		return numeroCommande;
	}

	public StatusCommandeVenteEnum getStatus() {
		return status;
	}

	public String getUuid() {
		return uuid;
	}

	public String getUuidBoutique() {
		return uuidBoutique;
	}

	public String getUuidUtilisateur() {
		return uuidUtilisateur;
	}

	public boolean isPaye() {
		return isPaye;
	}

	public void setDatePaiement(Date datePaiement) {
		this.datePaiement = datePaiement;
	}

	public void setId_client(String id_client) {
		this.id_client = id_client;
	}

	public void setLigneCommandeDtos(List<LigneCommandeDto> ligneCommandeDtos) {
		this.ligneCommandeDtos = ligneCommandeDtos;
	}

	public void setMontantCommande(String montantCommande) {
		this.montantCommande = montantCommande;
	}

	public void setNombreArticle(int nombreArticle) {
		this.nombreArticle = nombreArticle;
	}

	public void setNumeroCommande(String numeroCommande) {
		this.numeroCommande = numeroCommande;
	}

	public void setPaye(boolean isPaye) {
		this.isPaye = isPaye;
	}

	public void setStatus(StatusCommandeVenteEnum status) {
		this.status = status;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public void setUuidBoutique(String uuidBoutique) {
		this.uuidBoutique = uuidBoutique;
	}

	public void setUuidUtilisateur(String uuidUtilisateur) {
		this.uuidUtilisateur = uuidUtilisateur;
	}

}
