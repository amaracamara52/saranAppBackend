package org.sid.saranApp.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


@Entity
public class Categorie extends AbstractDomainClass {
	
	private String libelle;
	private String description;

	@ManyToOne
	private Boutique boutique;
	@ManyToOne
	private Utilisateur utilisateur;
	
	@OneToMany(mappedBy = "categorie")
	private List<Article> listeArticle;
	
	
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public List<Article> getListeArticle() {
		return listeArticle;
	}
	public void setListeArticle(List<Article> listeArticle) {
		this.listeArticle = listeArticle;
	}
	
	

}
