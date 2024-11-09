package org.sid.saranApp.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Pays extends AbstractDomainClass {
	
	@OneToMany(mappedBy = "pays")
	private List<Ville> listeVille;
//	@ManyToOne
//	private Boutique boutique;
	@ManyToOne
	private Utilisateur utilisateur;
	private String libelle;
	private String monnaie;
	private String capitale;
	private int nombreVille;
	
	
//	public Boutique getBoutique() {
//		return boutique;
//	}
//
//	public void setBoutique(Boutique boutique) {
//		this.boutique = boutique;
//	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public List<Ville> getListeVille() {
		return listeVille;
	}

	public void setListeVille(List<Ville> listeVille) {
		this.listeVille = listeVille;
	}

	public String getMonnaie() {
		return monnaie;
	}

	public void setMonnaie(String monnaie) {
		this.monnaie = monnaie;
	}

	public String getCapitale() {
		return capitale;
	}

	public void setCapitale(String capitale) {
		this.capitale = capitale;
	}

	public int getNombreVille() {
		return nombreVille;
	}

	public void setNombreVille(int nombreVille) {
		this.nombreVille = nombreVille;
	}
	
	


}
