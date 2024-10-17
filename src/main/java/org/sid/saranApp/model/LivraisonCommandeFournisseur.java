package org.sid.saranApp.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


@Entity
public class LivraisonCommandeFournisseur extends AbstractDomainClass {
	
	private Date dateLivraison;
	private String Heure;
	private String qte;
	private double prix;
	
	@OneToMany(mappedBy = "livraisonCommandeFournisseur")
	private List<Produit> listeProduit;

	@ManyToOne
	private DetailCommandeFournisseur detailCommandeFournisseur;
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
	public Date getDateLivraison() {
		return dateLivraison;
	}
	public void setDateLivraison(Date dateLivraison) {
		this.dateLivraison = dateLivraison;
	}
	public String getHeure() {
		return Heure;
	}
	public void setHeure(String heure) {
		Heure = heure;
	}
	public DetailCommandeFournisseur getDetailCommandeFournisseur() {
		return detailCommandeFournisseur;
	}
	public void setDetailCommandeFournisseur(DetailCommandeFournisseur detailCommandeFournisseur) {
		this.detailCommandeFournisseur = detailCommandeFournisseur;
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
	public String getQte() {
		return qte;
	}
	public void setQte(String qte) {
		this.qte = qte;
	}
	public double getPrix() {
		return prix;
	}
	public void setPrix(double prix) {
		this.prix = prix;
	}
	public List<Produit> getListeProduit() {
		return listeProduit;
	}
	public void setListeProduit(List<Produit> listeProduit) {
		this.listeProduit = listeProduit;
	}
	
}
