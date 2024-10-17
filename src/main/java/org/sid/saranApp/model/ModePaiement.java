package org.sid.saranApp.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class ModePaiement extends AbstractDomainClass {
	
	@ManyToOne
	private Boutique boutique;
	@ManyToOne
	private Utilisateur utilisateur;
	@OneToMany(mappedBy = "modePaiement")
	private List<BoutiquePaiement> listeBoutiquePaiment;
	
	
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
	public List<BoutiquePaiement> getListeBoutiquePaiment() {
		return listeBoutiquePaiment;
	}
	public void setListeBoutiquePaiment(List<BoutiquePaiement> listeBoutiquePaiment) {
		this.listeBoutiquePaiment = listeBoutiquePaiment;
	}
	private String libelle;
	private String description;
	
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

}
