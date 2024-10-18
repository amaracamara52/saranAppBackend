package org.sid.saranApp.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.sid.saranApp.enume.StatusCommandeVenteEnum;

@Entity
public class CommandeVente extends AbstractDomainClass {

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
	@Enumerated(EnumType.STRING)
	private StatusCommandeVenteEnum commandeVenteEnum;

	public Boutique getBoutique() {
		return boutique;
	}

	public Client getClient() {
		return client;
	}

	public StatusCommandeVenteEnum getCommandeVenteEnum() {
		return commandeVenteEnum;
	}

	public String getDatePaiement() {
		return datePaiement;
	}

	public List<LigneCommande> getListeLigneCommande() {
		return listeLigneCommande;
	}

	public String getNumeroCom() {
		return numeroCom;
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public String getValeurCom() {
		return valeurCom;
	}

	public boolean isPaye() {
		return isPaye;
	}

	public void setBoutique(Boutique boutique) {
		this.boutique = boutique;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public void setCommandeVenteEnum(StatusCommandeVenteEnum commandeVenteEnum) {
		this.commandeVenteEnum = commandeVenteEnum;
	}

	public void setDatePaiement(String datePaiement) {
		this.datePaiement = datePaiement;
	}

	public void setListeLigneCommande(List<LigneCommande> listeLigneCommande) {
		this.listeLigneCommande = listeLigneCommande;
	}

	public void setNumeroCom(String numeroCom) {
		this.numeroCom = numeroCom;
	}

	public void setPaye(boolean isPaye) {
		this.isPaye = isPaye;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public void setValeurCom(String valeurCom) {
		this.valeurCom = valeurCom;
	}

}
