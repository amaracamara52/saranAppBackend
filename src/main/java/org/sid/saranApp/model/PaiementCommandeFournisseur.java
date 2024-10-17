package org.sid.saranApp.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class PaiementCommandeFournisseur extends AbstractDomainClass {
	
	
	private String prixPayes;
	private String datePaiement;
	@ManyToOne
	private Boutique boutique;
	@ManyToOne
	private CommandeFournisseur commandeFournisseur;
	@ManyToOne
	private Utilisateur utilisateur;
	
	
	public Utilisateur getUtilisateur() {
		return utilisateur;
	}
	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}
	public String getPrixPayes() {
		return prixPayes;
	}
	public void setPrixPayes(String prixPayes) {
		this.prixPayes = prixPayes;
	}
	public String getDatePaiement() {
		return datePaiement;
	}
	public void setDatePaiement(String datePaiement) {
		this.datePaiement = datePaiement;
	}
	public Boutique getBoutique() {
		return boutique;
	}
	public void setBoutique(Boutique boutique) {
		this.boutique = boutique;
	}
	public CommandeFournisseur getCommandeFournisseur() {
		return commandeFournisseur;
	}
	public void setCommandeFournisseur(CommandeFournisseur commandeFournisseur) {
		this.commandeFournisseur = commandeFournisseur;
	}

}
