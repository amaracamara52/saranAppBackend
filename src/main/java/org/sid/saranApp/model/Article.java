package org.sid.saranApp.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Article extends AbstractDomainClass {
	
	private String libelle;
	private String description;
	
	@ManyToOne
	private Categorie categorie;
	@ManyToOne
	private Boutique boutique;
	@ManyToOne
	private Utilisateur utilisateur;
	
	@OneToMany(mappedBy = "article")
	private List<CaracteristiqueArticle> listeCaracteristiqueArticle;
	
	
	
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
	public Categorie getCategorie() {
		return categorie;
	}
	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}
	public Boutique getBoutique() {
		return boutique;
	}
	public void setBoutique(Boutique boutique) {
		this.boutique = boutique;
	}
	public List<CaracteristiqueArticle> getListeCaracteristiqueArticle() {
		return listeCaracteristiqueArticle;
	}
	public void setListeCaracteristiqueArticle(List<CaracteristiqueArticle> listeCaracteristiqueArticle) {
		this.listeCaracteristiqueArticle = listeCaracteristiqueArticle;
	}
	
	
}
