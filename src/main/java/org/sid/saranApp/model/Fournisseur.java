package org.sid.saranApp.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Fournisseur extends AbstractDomainClass {

	private String nom;
	private String prenom;
	private String telephone;
	private String Email;
	@ManyToOne
	private Ville ville;
	@ManyToOne
	private Boutique boutique;
	@ManyToOne
	private Utilisateur utilisateur;
	@OneToMany(mappedBy = "fournisseur")
	private List<CommandeFournisseur> listeCommandeFournisseur;

	public Boutique getBoutique() {
		return boutique;
	}

	public String getEmail() {
		return Email;
	}

	public List<CommandeFournisseur> getListeCommandeFournisseur() {
		return listeCommandeFournisseur;
	}

	public String getNom() {
		return nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public String getTelephone() {
		return telephone;
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public Ville getVille() {
		return ville;
	}

	public void setBoutique(Boutique boutique) {
		this.boutique = boutique;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public void setListeCommandeFournisseur(List<CommandeFournisseur> listeCommandeFournisseur) {
		this.listeCommandeFournisseur = listeCommandeFournisseur;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public void setVille(Ville ville) {
		this.ville = ville;
	}

}
