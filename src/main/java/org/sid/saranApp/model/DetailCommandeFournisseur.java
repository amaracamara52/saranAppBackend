package org.sid.saranApp.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class DetailCommandeFournisseur extends AbstractDomainClass {
	
	private String numeroCommande;
	private String nomClient;
	private String quantiteCommande;
	private String Prix;
	@ManyToOne
	private CommandeFournisseur commandeFournisseur;
	@ManyToOne
	private Boutique boutique;
	@ManyToOne
	private Utilisateur utilisateur;
	@OneToMany(mappedBy ="detailCommandeFournisseur")
	private List<LivraisonCommandeFournisseur> listeLivraisonCommandeFournisseur;
	
	public Utilisateur getUtilisateur() {
		return utilisateur;
	}
	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}
	public String getNumeroCommande() {
		return numeroCommande;
	}
	public void setNumeroCommande(String numeroCommande) {
		this.numeroCommande = numeroCommande;
	}
	public String getNomClient() {
		return nomClient;
	}
	public void setNomClient(String nomClient) {
		this.nomClient = nomClient;
	}
	public String getQuantiteCommande() {
		return quantiteCommande;
	}
	public void setQuantiteCommande(String quantiteCommande) {
		this.quantiteCommande = quantiteCommande;
	}
	public String getPrix() {
		return Prix;
	}
	public void setPrix(String prix) {
		Prix = prix;
	}
	public CommandeFournisseur getCommandeFournisseur() {
		return commandeFournisseur;
	}
	public void setCommandeFournisseur(CommandeFournisseur commandeFournisseur) {
		this.commandeFournisseur = commandeFournisseur;
	}
	public Boutique getBoutique() {
		return boutique;
	}
	public void setBoutique(Boutique boutique) {
		this.boutique = boutique;
	}
	public List<LivraisonCommandeFournisseur> getListeLivraisonCommandeFournisseur() {
		return listeLivraisonCommandeFournisseur;
	}
	public void setListeLivraisonCommandeFournisseur(List<LivraisonCommandeFournisseur> listeLivraisonCommandeFournisseur) {
		this.listeLivraisonCommandeFournisseur = listeLivraisonCommandeFournisseur;
	}
	
}
