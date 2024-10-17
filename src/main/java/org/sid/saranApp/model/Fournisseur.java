package org.sid.saranApp.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;



@Entity
public class Fournisseur extends AbstractDomainClass{
	
	private String nom;
	private String prenom;
	private String telephone;
	private String Email;
	@ManyToOne
	private Quartier quartier;
	@ManyToOne
	private Boutique boutique;
	@ManyToOne
	private Utilisateur utilisateur;
	@OneToMany(mappedBy = "fournisseur")
	private List<CommandeFournisseur> listeCommandeFournisseur;
	
	
	public Utilisateur getUtilisateur() {
		return utilisateur;
	}
	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public Quartier getQuartier() {
		return quartier;
	}
	public void setQuartier(Quartier quartier) {
		this.quartier = quartier;
	}
	public Boutique getBoutique() {
		return boutique;
	}
	public void setBoutique(Boutique boutique) {
		this.boutique = boutique;
	}
	
	public List<CommandeFournisseur> getListeCommandeFournisseur() {
		return listeCommandeFournisseur;
	}
	public void setListeCommandeFournisseur(List<CommandeFournisseur> listeCommandeFournisseur) {
		this.listeCommandeFournisseur = listeCommandeFournisseur;
	}
	
	
	

}
