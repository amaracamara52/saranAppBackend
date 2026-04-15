package org.sid.saranApp.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class Quartier extends AbstractDomainClass {

	private String libelle;
	@ManyToOne
	private Commune commune;
	@ManyToOne
	private Boutique boutique;
	@ManyToOne
	private Utilisateur utilisateur;

	public Boutique getBoutique() {
		return boutique;
	}

	public Commune getCommune() {
		return commune;
	}

	public String getLibelle() {
		return libelle;
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setBoutique(Boutique boutique) {
		this.boutique = boutique;
	}

	public void setCommune(Commune commune) {
		this.commune = commune;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

}
