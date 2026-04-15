package org.sid.saranApp.dto;

import org.sid.saranApp.enume.EnumTypeCommande;
import org.sid.saranApp.enume.StatusCommandeVenteEnum;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CommandeVenteDto extends ResponseDto {

	private String uuid;
	private double montantCommande;
	private double montantCommandeImage;
	private String numeroCommande;
	private boolean isPaye;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyy-MM-dd")
	private Date datePaiement;
	private String id_client;
	private String client;
	private String uuidBoutique;
	private String uuidUtilisateur;
	private String utilisateur;
	@Enumerated(EnumType.STRING)
	private EnumTypeCommande typeCommande;
	@Enumerated(EnumType.STRING)
	private StatusCommandeVenteEnum status;
	private int nombreArticle;
	List<LigneCommandeDto> ligneCommandeDtos = new ArrayList<LigneCommandeDto>();
	private LivraisonCommandeVenteDto livraisonCommandeVenteDto;

	private String uuidModePaiement;
	private String modePaiement;

	public Date getDatePaiement() {
		return datePaiement;
	}

	public String getId_client() {
		return id_client;
	}

	public List<LigneCommandeDto> getLigneCommandeDtos() {
		return ligneCommandeDtos;
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

	public double getMontantCommande() {
		return montantCommande;
	}

	public void setMontantCommande(double montantCommande) {
		this.montantCommande = montantCommande;
	}

	public String getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(String utilisateur) {
		this.utilisateur = utilisateur;
	}


	public EnumTypeCommande getTypeCommande() {
		return typeCommande;
	}

	public void setTypeCommande(EnumTypeCommande typeCommande) {
		this.typeCommande = typeCommande;
	}

	public LivraisonCommandeVenteDto getLivraisonCommandeVenteDto() {
		return livraisonCommandeVenteDto;
	}

	public void setLivraisonCommandeVenteDto(LivraisonCommandeVenteDto livraisonCommandeVenteDto) {
		this.livraisonCommandeVenteDto = livraisonCommandeVenteDto;
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

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public double getMontantCommandeImage() {
		return montantCommandeImage;
	}

	public void setMontantCommandeImage(double montantCommandeImage) {
		this.montantCommandeImage = montantCommandeImage;
	}
}
