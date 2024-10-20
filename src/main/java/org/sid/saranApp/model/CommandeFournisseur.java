package org.sid.saranApp.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.sid.saranApp.enume.StatusCommandeFournisseurEnum;

@Entity
public class CommandeFournisseur extends AbstractDomainClass {

	private String refCommande;
	private String valeurMarchandise;
	private String dateCommandeFournisseur;
	private boolean isPaie;
	@ManyToOne
	private Boutique boutique;
	@ManyToOne
	private Utilisateur utilisateur;
	@ManyToOne
	private Fournisseur fournisseur;
	@OneToMany(mappedBy = "commandeFournisseur")
	private List<DetailCommandeFournisseur> listeDetailCommandeFournisseur = new ArrayList<DetailCommandeFournisseur>();
	@OneToMany(mappedBy = "commandeFournisseur", fetch = FetchType.EAGER)
	private List<LivraisonCommandeFournisseur> listeLivraisonCommandeFournisseur;
	@Enumerated(EnumType.STRING)
	private StatusCommandeFournisseurEnum commandeFournisseurEnum;

	public Boutique getBoutique() {
		return boutique;
	}

	public StatusCommandeFournisseurEnum getCommandeFournisseurEnum() {
		return commandeFournisseurEnum;
	}

	public String getDateCommandeFournisseur() {
		return dateCommandeFournisseur;
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

	public void setDateCommandeFournisseur(String dateCommandeFournisseur) {
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

}
