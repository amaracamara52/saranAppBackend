package org.sid.saranApp.model;

import org.sid.saranApp.enume.EnumTypeCommande;
import org.sid.saranApp.enume.StatusCommandeVenteEnum;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class CommandeVente extends AbstractDomainClass {

	private double montantCommade;
	private double montantCommadeImage;
	private String numeroCommande;
	private boolean isPaye;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyy-MM-dd")
	private Date datePaiement;
	@ManyToOne
	private Client client;
	@ManyToOne
	private Boutique boutique;
	@ManyToOne
	private Utilisateur utilisateur;
	@OneToMany(mappedBy = "commandeVente", fetch = FetchType.LAZY)
	private List<LigneCommande> listeLigneCommande = new ArrayList<LigneCommande>();
	@Enumerated(EnumType.STRING)
	private StatusCommandeVenteEnum commandeVenteEnum;
	@Enumerated(EnumType.STRING)
	private EnumTypeCommande typeCommande;
	@OneToOne(mappedBy = "commandeVente")
	private LivraisonCommandeVente livraisonCommandeVente;
	@ManyToOne
	private ModePaiement modePaiement;
	
	/**
	 * Indique si la commande est validée (pour les commandes en gros)
	 * Seuls les grossistes peuvent valider les commandes en gros
	 */
	@Transient
	private boolean validee;
	
	/**
	 * Date de validation de la commande
	 */
	@Transient
	private Date dateValidation;
	
	/**
	 * Utilisateur grossiste qui a validé la commande (si typeCommande = EN_GROS)
	 */
	@Transient
	private Utilisateur utilisateurValidateur;



	public Boutique getBoutique() {
		return boutique;
	}

	public Client getClient() {
		return client;
	}

	public StatusCommandeVenteEnum getCommandeVenteEnum() {
		return commandeVenteEnum;
	}

	public Date getDatePaiement() {
		return datePaiement;
	}

	public List<LigneCommande> getListeLigneCommande() {
		return listeLigneCommande;
	}

	
	public String getNumeroCommande() {
		return numeroCommande;
	}

	
	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public boolean isPaye() {
		return isPaye;
	}

	public void setBoutique(Boutique boutique) {
		this.boutique = boutique;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public void setCommandeVenteEnum(StatusCommandeVenteEnum commandeVenteEnum) {
		this.commandeVenteEnum = commandeVenteEnum;
	}

	public void setDatePaiement(Date datePaiement) {
		this.datePaiement = datePaiement;
	}

	public void setListeLigneCommande(List<LigneCommande> listeLigneCommande) {
		this.listeLigneCommande = listeLigneCommande;
	}



	public void setNumeroCommande(String numeroCommande) {
		this.numeroCommande = numeroCommande;
	}

	public void setPaye(boolean isPaye) {
		this.isPaye = isPaye;
	}


	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public double getMontantCommade() {
		return montantCommade;
	}

	public void setMontantCommade(double montantCommade) {
		this.montantCommade = montantCommade;
	}

	public EnumTypeCommande getTypeCommande() {
		return typeCommande;
	}

	public void setTypeCommande(EnumTypeCommande typeCommande) {
		this.typeCommande = typeCommande;
	}

	public LivraisonCommandeVente getLivraisonCommandeVente() {
		return livraisonCommandeVente;
	}

	public void setLivraisonCommandeVente(LivraisonCommandeVente livraisonCommandeVente) {
		this.livraisonCommandeVente = livraisonCommandeVente;
	}

	public ModePaiement getModePaiement() {
		return modePaiement;
	}

	public void setModePaiement(ModePaiement modePaiement) {
		this.modePaiement = modePaiement;
	}

	public double getMontantCommadeImage() {
		return montantCommadeImage;
	}

	public void setMontantCommadeImage(double montantCommadeImage) {
		this.montantCommadeImage = montantCommadeImage;
	}

	public boolean isValidee() {
		return validee;
	}

	public void setValidee(boolean validee) {
		this.validee = validee;
	}

	public Date getDateValidation() {
		return dateValidation;
	}

	public void setDateValidation(Date dateValidation) {
		this.dateValidation = dateValidation;
	}

	public Utilisateur getUtilisateurValidateur() {
		return utilisateurValidateur;
	}

	public void setUtilisateurValidateur(Utilisateur utilisateurValidateur) {
		this.utilisateurValidateur = utilisateurValidateur;
	}
}
