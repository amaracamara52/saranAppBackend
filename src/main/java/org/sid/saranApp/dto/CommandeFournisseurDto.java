package org.sid.saranApp.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.sid.saranApp.enume.StatusCommandeFournisseurEnum;

public class CommandeFournisseurDto {

	private String uuid;
	private String valeurMarchandise;
	private LocalDate dateCommandeFournisseur;
	private boolean isPaye;
	private String uuidBoutique;
	private String uuidFournisseur;
	private String uuidUtilisateur;
	private String refCommande;
	private String nom;
	private String prenom;
	private String telephone;
	private String email;
	private String adresse;
	private StatusCommandeFournisseurEnum Status;
	private List<DetailCommandeFournisseurDto> detailCommandeFournisseurDtos = new ArrayList<DetailCommandeFournisseurDto>();

	public String getAdresse() {
		return adresse;
	}

	
	public List<DetailCommandeFournisseurDto> getDetailCommandeFournisseurDtos() {
		return detailCommandeFournisseurDtos;
	}

	public String getEmail() {
		return email;
	}

	public String getNom() {
		return nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public String getRefCommande() {
		return refCommande;
	}

	public StatusCommandeFournisseurEnum getStatus() {
		return Status;
	}

	public String getTelephone() {
		return telephone;
	}

	public String getUuid() {
		return uuid;
	}

	public String getUuidBoutique() {
		return uuidBoutique;
	}

	public String getUuidFournisseur() {
		return uuidFournisseur;
	}

	public String getUuidUtilisateur() {
		return uuidUtilisateur;
	}

	public String getValeurMarchandise() {
		return valeurMarchandise;
	}

	public boolean isPaye() {
		return isPaye;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	

	public LocalDate getDateCommandeFournisseur() {
		return dateCommandeFournisseur;
	}


	public void setDateCommandeFournisseur(LocalDate dateCommandeFournisseur) {
		this.dateCommandeFournisseur = dateCommandeFournisseur;
	}


	public void setDetailCommandeFournisseurDtos(List<DetailCommandeFournisseurDto> detailCommandeFournisseurDtos) {
		this.detailCommandeFournisseurDtos = detailCommandeFournisseurDtos;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public void setPaye(boolean isPaye) {
		this.isPaye = isPaye;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public void setRefCommande(String refCommande) {
		this.refCommande = refCommande;
	}

	public void setStatus(StatusCommandeFournisseurEnum status) {
		Status = status;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public void setUuidBoutique(String uuidBoutique) {
		this.uuidBoutique = uuidBoutique;
	}

	public void setUuidFournisseur(String uuidFournisseur) {
		this.uuidFournisseur = uuidFournisseur;
	}

	public void setUuidUtilisateur(String uuidUtilisateur) {
		this.uuidUtilisateur = uuidUtilisateur;
	}

	public void setValeurMarchandise(String valeurMarchandise) {
		this.valeurMarchandise = valeurMarchandise;
	}

}
