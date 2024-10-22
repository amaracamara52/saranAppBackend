package org.sid.saranApp.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Article extends AbstractDomainClass {

	private String libelle;
	private String description;
	private int quantiteDansCarton;
	@ManyToOne
	private Categorie categorie;
	@ManyToOne
	private Boutique boutique;
	@ManyToOne
	private Utilisateur utilisateur;

	@OneToMany(mappedBy = "article")
	private List<CaracteristiqueArticle> listeCaracteristiqueArticle;

	public Boutique getBoutique() {
		return boutique;
	}

	public Categorie getCategorie() {
		return categorie;
	}

	public String getDescription() {
		return description;
	}

	public String getLibelle() {
		return libelle;
	}

	public List<CaracteristiqueArticle> getListeCaracteristiqueArticle() {
		return listeCaracteristiqueArticle;
	}

	public int getQuantiteDansCarton() {
		return quantiteDansCarton;
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setBoutique(Boutique boutique) {
		this.boutique = boutique;
	}

	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public void setListeCaracteristiqueArticle(List<CaracteristiqueArticle> listeCaracteristiqueArticle) {
		this.listeCaracteristiqueArticle = listeCaracteristiqueArticle;
	}

	public void setQuantiteDansCarton(int quantiteDansCarton) {
		this.quantiteDansCarton = quantiteDansCarton;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

}
