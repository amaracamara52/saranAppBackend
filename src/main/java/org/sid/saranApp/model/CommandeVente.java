package org.sid.saranApp.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class CommandeVente extends AbstractDomainClass{

	private String valeurCom;
	private String numeroCom;
	private boolean isPaye;
	private String datePaiement;
	
	@ManyToOne
	private Client client;
	@ManyToOne
	private Boutique boutique;
	@ManyToOne
	private Utilisateur utilisateur;
	@OneToMany(mappedBy = "commandeVente")
	private List<LigneCommande> listeLigneCommande;
	

	
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

	public List<LigneCommande> getListeLigneCommande() {
		return listeLigneCommande;
	}

	public void setListeLigneCommande(List<LigneCommande> listeLigneCommande) {
		this.listeLigneCommande = listeLigneCommande;
	}

	public String getValeurCom() {
		return valeurCom;
	}

	public void setValeurCom(String valeurCom) {
		this.valeurCom = valeurCom;
	}
	public String getNumeroCom() {
		return numeroCom;
	}

	public void setNumeroCom(String numeroCom) {
		this.numeroCom = numeroCom;
	}
	public boolean isPaye() {
		return isPaye;
	}

	public void setPaye(boolean isPaye) {
		this.isPaye = isPaye;
	}

	public String getDatePaiement() {
		return datePaiement;
	}

	public void setDatePaiement(String datePaiement) {
		this.datePaiement = datePaiement;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}


}
