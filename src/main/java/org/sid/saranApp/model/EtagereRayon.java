package org.sid.saranApp.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class EtagereRayon extends AbstractDomainClass {

	@ManyToOne
	private Etagere etagere;
	@ManyToOne
	private Rayon rayon;
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

	public Etagere getEtagere() {
		return etagere;
	}

	public Rayon getRayon() {
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

	public void setEtagere(Etagere etagere) {
		this.etagere = etagere;
	}

	public void setRayon(Rayon rayon) {
		this.rayon = rayon;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

}
