package org.sid.saranApp.model;

import org.sid.saranApp.enume.StatusCommandeFournisseurEnum;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class CommandeFournisseur extends AbstractDomainClass {

	private String refCommande;
	private String valeurMarchandise;
	private LocalDate dateCommandeFournisseur;
	private boolean isPaie;
	@ManyToOne
	private Boutique boutique;
	@ManyToOne
	private Utilisateur utilisateur;
	@ManyToOne
	private Fournisseur fournisseur;
	@OneToMany(mappedBy = "commandeFournisseur")
	private List<DetailCommandeFournisseur> listeDetailCommandeFournisseur = new ArrayList<DetailCommandeFournisseur>();
	@OneToMany(mappedBy = "commandeFournisseur", fetch = FetchType.LAZY)
	private List<LivraisonCommandeFournisseur> listeLivraisonCommandeFournisseur;
	@Enumerated(EnumType.STRING)
	private StatusCommandeFournisseurEnum commandeFournisseurEnum;
	
	/**
	 * Liste des paiements effectués pour cette commande
	 */
	@OneToMany(mappedBy = "commandeFournisseur")
	private List<PaiementCommandeFournisseur> paiements = new ArrayList<>();
	
	/**
	 * Montant total de la commande (calculé à partir des détails)
	 */
	private double montantTotal;

	public Boutique getBoutique() {
		return boutique;
	}

	public StatusCommandeFournisseurEnum getCommandeFournisseurEnum() {
		return commandeFournisseurEnum;
	}

	

	public Fournisseur getFournisseur() {
		return fournisseur;
	}

	public List<DetailCommandeFournisseur> getListeDetailCommandeFournisseur() {
		return listeDetailCommandeFournisseur;
	}

	public List<LivraisonCommandeFournisseur> getListeLivraisonCommandeFournisseur() {
		return listeLivraisonCommandeFournisseur;
	}

	public String getRefCommande() {
		return refCommande;
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public String getValeurMarchandise() {
		return valeurMarchandise;
	}

	public boolean isPaie() {
		return isPaie;
	}

	public void setBoutique(Boutique boutique) {
		this.boutique = boutique;
	}

	public void setCommandeFournisseurEnum(StatusCommandeFournisseurEnum commandeFournisseurEnum) {
		this.commandeFournisseurEnum = commandeFournisseurEnum;
	}

	

	public LocalDate getDateCommandeFournisseur() {
		return dateCommandeFournisseur;
	}

	public void setDateCommandeFournisseur(LocalDate dateCommandeFournisseur) {
		this.dateCommandeFournisseur = dateCommandeFournisseur;
	}

	public void setFournisseur(Fournisseur fournisseur) {
		this.fournisseur = fournisseur;
	}

	public void setListeDetailCommandeFournisseur(List<DetailCommandeFournisseur> listeDetailCommandeFournisseur) {
		this.listeDetailCommandeFournisseur = listeDetailCommandeFournisseur;
	}

	public void setListeLivraisonCommandeFournisseur(
			List<LivraisonCommandeFournisseur> listeLivraisonCommandeFournisseur) {
		this.listeLivraisonCommandeFournisseur = listeLivraisonCommandeFournisseur;
	}

	public void setPaie(boolean isPaie) {
		this.isPaie = isPaie;
	}

	public void setRefCommande(String refCommande) {
		this.refCommande = refCommande;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public void setValeurMarchandise(String valeurMarchandise) {
		this.valeurMarchandise = valeurMarchandise;
	}

	public List<PaiementCommandeFournisseur> getPaiements() {
		return paiements;
	}

	public void setPaiements(List<PaiementCommandeFournisseur> paiements) {
		this.paiements = paiements;
	}

	public double getMontantTotal() {
		return montantTotal;
	}

	public void setMontantTotal(double montantTotal) {
		this.montantTotal = montantTotal;
	}

}
