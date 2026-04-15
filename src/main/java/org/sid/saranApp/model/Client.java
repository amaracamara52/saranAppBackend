package org.sid.saranApp.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import java.util.List;


@Entity
public class Client extends AbstractDomainClass{
	
	private String nom;
	private String prenom;
	private String phone;
	private String email;
	@ManyToOne
	private Boutique boutique;
	@ManyToOne
	private Utilisateur utilisateur;
	@OneToMany(mappedBy = "client")
	private List<CommandeVente> commandeVente;
	
	/**
	 * Indique si le client a un compte (peut passer des commandes en gros)
	 */
	@Transient
	private boolean aUnCompte;
	
	/**
	 * Liste des partenariats avec des boutiques (pour les commandes en gros)
	 */
	@OneToMany(mappedBy = "client")
	private List<ClientPartenaire> partenariats = new java.util.ArrayList<>();
	
	
	
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<CommandeVente> getCommandeVente() {
		return commandeVente;
	}

	public void setCommandeVente(List<CommandeVente> commandeVente) {
		this.commandeVente = commandeVente;
	}

	public boolean isAUnCompte() {
		return aUnCompte;
	}

	public void setAUnCompte(boolean aUnCompte) {
		this.aUnCompte = aUnCompte;
	}

	public List<ClientPartenaire> getPartenariats() {
		return partenariats;
	}

	public void setPartenariats(List<ClientPartenaire> partenariats) {
		this.partenariats = partenariats;
	}

	
}
