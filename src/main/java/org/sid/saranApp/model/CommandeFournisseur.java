package org.sid.saranApp.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
	
	
	public Utilisateur getUtilisateur() {
		return utilisateur;
	}
	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}
	public String getValeurMarchandise() {
		return valeurMarchandise;
	}
	public void setValeurMarchandise(String valeurMarchandise) {
		this.valeurMarchandise = valeurMarchandise;
	}
	public String getDateCommandeFournisseur() {
		return dateCommandeFournisseur;
	}
	public void setDateCommandeFournisseur(String dateCommandeFournisseur) {
		this.dateCommandeFournisseur = dateCommandeFournisseur;
	}
	public String getIsPaye() {
		return isPaye;
	}
	public void setIsPaye(String isPaye) {
		this.isPaye = isPaye;
	}
	public Boutique getBoutique() {
		return boutique;
	}
	public void setBoutique(Boutique boutique) {
		this.boutique = boutique;
	}
	public Fournisseur getFournisseur() {
		return fournisseur;
	}
	public void setFournisseur(Fournisseur fournisseur) {
		this.fournisseur = fournisseur;
	}
	public List<DetailCommandeFournisseur> getListeDetailCommandeFournisseur() {
		return listeDetailCommandeFournisseur;
	}
	public void setListeDetailCommandeFournisseur(List<DetailCommandeFournisseur> listeDetailCommandeFournisseur) {
		this.listeDetailCommandeFournisseur = listeDetailCommandeFournisseur;
	}
	public List<LivraisonCommandeFournisseur> getListeLivraisonCommandeFournisseur() {
		return listeLivraisonCommandeFournisseur;
	}
	public void setListeLivraisonCommandeFournisseur(List<LivraisonCommandeFournisseur> listeLivraisonCommandeFournisseur) {
		this.listeLivraisonCommandeFournisseur = listeLivraisonCommandeFournisseur;
	}
	
}
