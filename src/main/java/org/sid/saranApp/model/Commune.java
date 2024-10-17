package org.sid.saranApp.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Commune extends AbstractDomainClass {
	
	private String libelle;
	@ManyToOne
	private Ville ville;
	@ManyToOne
	private Boutique boutique;
	@ManyToOne
	private Utilisateur utilisateur;
	
	@OneToMany(mappedBy = "commune")
	private List<Quartier> listeQuartier;
	
	
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
	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	public Ville getVille() {
		return ville;
	}
	public void setVille(Ville ville) {
		this.ville = ville;
	}
	public List<Quartier> getListeQuartier() {
		return listeQuartier;
	}
	public void setListeQuartier(List<Quartier> listeQuartier) {
		this.listeQuartier = listeQuartier;
	}
	

}
