package org.sid.saranApp.model;

import org.sid.saranApp.enume.TypeGestionUniteProduitEnum;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
@Entity
public class Article extends AbstractDomainClass {

	private String libelle;
	/** Référence / code article unique par boutique (recherche rapide). */
	private String codeProduit;
	private String description;
	private int quantiteDansCarton;
	@ManyToOne
	private Categorie categorie;
	@ManyToOne
	private Boutique boutique;
	@ManyToOne
	private Utilisateur utilisateur;
	
	/**
	 * Liste des unités de vente définies pour cet article
	 */
	@OneToMany(mappedBy = "article")
	private List<TypeUniteDeVente> typeUniteDeVentes = new ArrayList<>();

	/**
	 * NORMAL = une unité simple (×1) ; MIXTE = plusieurs unités avec conversion, stock interne toujours en base.
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "type_gestion_unite")
	private TypeGestionUniteProduitEnum typeGestionUnite;

	public TypeGestionUniteProduitEnum getTypeGestionUnite() {
		return typeGestionUnite;
	}

	public void setTypeGestionUnite(TypeGestionUniteProduitEnum typeGestionUnite) {
		this.typeGestionUnite = typeGestionUnite;
	}

	public Boutique getBoutique() {
		return boutique;
	}

	public Categorie getCategorie() {
		return categorie;
	}

	public String getCodeProduit() {
		return codeProduit;
	}

	public void setCodeProduit(String codeProduit) {
		this.codeProduit = codeProduit;
	}

	public String getDescription() {
		return description;
	}

	public String getLibelle() {
		return libelle;
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

	

	public void setQuantiteDansCarton(int quantiteDansCarton) {
		this.quantiteDansCarton = quantiteDansCarton;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public List<TypeUniteDeVente> getTypeUniteDeVentes() {
		return typeUniteDeVentes;
	}

	public void setTypeUniteDeVentes(List<TypeUniteDeVente> typeUniteDeVentes) {
		this.typeUniteDeVentes = typeUniteDeVentes;
	}

}
