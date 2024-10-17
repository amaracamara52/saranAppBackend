package org.sid.saranApp.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Quartier extends AbstractDomainClass {
	
	private String libelle;
	@ManyToOne
	private Commune commune;
	@ManyToOne
	private Boutique boutique;
	@ManyToOne
	private Utilisateur utilisateur;
	@OneToMany(mappedBy = "quartier")
	private List<Fournisseur> listeFournisseur;
	
	
	public Boutique getBoutique() {
		return boutique;
	}
	public void setBoutique(Boutique boutique) {
		this.boutique = boutique;
	}
	public Utilisateur getUtilisateur() {
		return utilisateur;
	}
	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}
	public List<Fournisseur> getListeFournisseur() {
		return listeFournisseur;
	}
	public void setListeFournisseur(List<Fournisseur> listeFournisseur) {
		this.listeFournisseur = listeFournisseur;
	}
	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	public Commune getCommune() {
		return commune;
	}
	public void setCommune(Commune commune) {
		this.commune = commune;
	}
	
}
