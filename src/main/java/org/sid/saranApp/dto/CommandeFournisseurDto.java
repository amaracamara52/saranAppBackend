package org.sid.saranApp.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.sid.saranApp.enume.StatusCommandeFournisseurEnum;
import org.sid.saranApp.jackson.CoerceStringDeserializer;
import org.sid.saranApp.jackson.LenientLocalDateDeserializer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CommandeFournisseurDto {

	private String uuid;
	@JsonDeserialize(using = CoerceStringDeserializer.class)
	private String valeurMarchandise;
	@JsonDeserialize(using = LenientLocalDateDeserializer.class)
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
	
	// Données enrichies pour optimisation frontend (inspiré Odoo)
	private double montantTotal;
	private double montantPaye;
	private double montantRestant;
	private int nombreLivraisons;
	private int nombrePaiements;
	private String libelleBoutique;
	private String libelleFournisseur; // Nom complet du fournisseur
	private String paysProvenanceFournisseur; // Pays de provenance du fournisseur

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

	public double getMontantTotal() {
		return montantTotal;
	}

	public void setMontantTotal(double montantTotal) {
		this.montantTotal = montantTotal;
	}

	public double getMontantPaye() {
		return montantPaye;
	}

	public void setMontantPaye(double montantPaye) {
		this.montantPaye = montantPaye;
	}

	public double getMontantRestant() {
		return montantRestant;
	}

	public void setMontantRestant(double montantRestant) {
		this.montantRestant = montantRestant;
	}

	public int getNombreLivraisons() {
		return nombreLivraisons;
	}

	public void setNombreLivraisons(int nombreLivraisons) {
		this.nombreLivraisons = nombreLivraisons;
	}

	public int getNombrePaiements() {
		return nombrePaiements;
	}

	public void setNombrePaiements(int nombrePaiements) {
		this.nombrePaiements = nombrePaiements;
	}

	public String getLibelleBoutique() {
		return libelleBoutique;
	}

	public void setLibelleBoutique(String libelleBoutique) {
		this.libelleBoutique = libelleBoutique;
	}

	public String getLibelleFournisseur() {
		return libelleFournisseur;
	}

	public void setLibelleFournisseur(String libelleFournisseur) {
		this.libelleFournisseur = libelleFournisseur;
	}

	public String getPaysProvenanceFournisseur() {
		return paysProvenanceFournisseur;
	}

	public void setPaysProvenanceFournisseur(String paysProvenanceFournisseur) {
		this.paysProvenanceFournisseur = paysProvenanceFournisseur;
	}

}
