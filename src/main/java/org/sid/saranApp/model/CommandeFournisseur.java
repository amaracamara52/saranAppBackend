package org.sid.saranApp.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.sid.saranApp.enume.StatusCommandeFournisseurEnum;

@Entity
public class CommandeFournisseur extends AbstractDomainClass {

	private String valeurMarchandise;
	private String dateCommandeFournisseur;
	private String isPaye;
	@ManyToOne
	private Boutique boutique;
	@ManyToOne
	private Utilisateur utilisateur;
	@ManyToOne
	private Fournisseur fournisseur;
	@OneToMany(mappedBy = "commandeFournisseur")
	private List<DetailCommandeFournisseur> listeDetailCommandeFournisseur;
	@OneToMany(mappedBy = "commandeFournisseur")
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

	public String getIsPaye() {
		return isPaye;
	}

	public List<DetailCommandeFournisseur> getListeDetailCommandeFournisseur() {
		return listeDetailCommandeFournisseur;
	}

	public List<LivraisonCommandeFournisseur> getListeLivraisonCommandeFournisseur() {
		return listeLivraisonCommandeFournisseur;
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public String getValeurMarchandise() {
		return valeurMarchandise;
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

	public void setIsPaye(String isPaye) {
		this.isPaye = isPaye;
	}

	public void setListeDetailCommandeFournisseur(List<DetailCommandeFournisseur> listeDetailCommandeFournisseur) {
		this.listeDetailCommandeFournisseur = listeDetailCommandeFournisseur;
	}

	public void setListeLivraisonCommandeFournisseur(
			List<LivraisonCommandeFournisseur> listeLivraisonCommandeFournisseur) {
		this.listeLivraisonCommandeFournisseur = listeLivraisonCommandeFournisseur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public void setValeurMarchandise(String valeurMarchandise) {
		this.valeurMarchandise = valeurMarchandise;
	}

}
