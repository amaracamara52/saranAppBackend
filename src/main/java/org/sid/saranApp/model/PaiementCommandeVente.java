package org.sid.saranApp.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class PaiementCommandeVente extends AbstractDomainClass {
	
	
	private String montantVerse;
	private String dateVersement;
	@ManyToOne
	private Boutique boutique;
	@ManyToOne
	private CommandeVente commandeVente;
	@ManyToOne
	private Utilisateur utilisateur;
	
	@OneToOne
	private StoredFile recuVersement;
	
	public Utilisateur getUtilisateur() {
		return utilisateur;
	}
	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	
	public Boutique getBoutique() {
		return boutique;
	}
	public void setBoutique(Boutique boutique) {
		this.boutique = boutique;
	}
	public CommandeVente getCommandeVente() {
		return commandeVente;
	}
	public void setCommandeVente(CommandeVente commandeVente) {
		this.commandeVente = commandeVente;
	}
	public String getMontantVerse() {
		return montantVerse;
	}
	public void setMontantVerse(String montantVerse) {
		this.montantVerse = montantVerse;
	}
	public String getDateVersement() {
		return dateVersement;
	}
	public void setDateVersement(String dateVersement) {
		this.dateVersement = dateVersement;
	}
	public StoredFile getRecuVersement() {
		return recuVersement;
	}
	public void setRecuVersement(StoredFile recuVersement) {
		this.recuVersement = recuVersement;
	}
	
	
	

}
