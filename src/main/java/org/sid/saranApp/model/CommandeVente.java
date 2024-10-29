package org.sid.saranApp.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.sid.saranApp.enume.StatusCommandeVenteEnum;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class CommandeVente extends AbstractDomainClass {

	private double montantCommade;
	private String numeroCommande;
	private boolean isPaye;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyy-MM-dd")
	private Date datePaiement;
	@ManyToOne
	private Client client;
	@ManyToOne
	private Boutique boutique;
	@ManyToOne
	private Utilisateur utilisateur;
	@OneToMany(mappedBy = "commandeVente", fetch = FetchType.EAGER)
	private List<LigneCommande> listeLigneCommande = new ArrayList<LigneCommande>();
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

	public Date getDatePaiement() {
		return datePaiement;
	}

	public List<LigneCommande> getListeLigneCommande() {
		return listeLigneCommande;
	}

	
	public String getNumeroCommande() {
		return numeroCommande;
	}

	
	public Utilisateur getUtilisateur() {
		return utilisateur;
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

	public void setDatePaiement(Date datePaiement) {
		this.datePaiement = datePaiement;
	}

	public void setListeLigneCommande(List<LigneCommande> listeLigneCommande) {
		this.listeLigneCommande = listeLigneCommande;
	}



	public void setNumeroCommande(String numeroCommande) {
		this.numeroCommande = numeroCommande;
	}

	public void setPaye(boolean isPaye) {
		this.isPaye = isPaye;
	}


	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public double getMontantCommade() {
		return montantCommade;
	}

	public void setMontantCommade(double montantCommade) {
		this.montantCommade = montantCommade;
	}
	
	

}
