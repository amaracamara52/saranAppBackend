package org.sid.saranApp.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class EtagereRayon extends AbstractDomainClass {

	private String etagere;

	private String rayon;
	@ManyToOne
	private Boutique boutique;
	@ManyToOne
	private Utilisateur utilisateur;

	private String color;

	public Boutique getBoutique() {
		return boutique;
	}

	public String getColor() {
		return color;
	}

	public String getEtagere() {
		return etagere;
	}

	public String getRayon() {
		return rayon;
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setBoutique(Boutique boutique) {
		this.boutique = boutique;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public void setEtagere(String etagere) {
		this.etagere = etagere;
	}

	public void setRayon(String rayon) {
		this.rayon = rayon;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

}
