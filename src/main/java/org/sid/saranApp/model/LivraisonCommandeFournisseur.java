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
	private int quantite;
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

	public Boutique getBoutique() {
		return boutique;
	}

	public CommandeFournisseur getCommandeFournisseur() {
		return commandeFournisseur;
	}

	public Date getDateLivraison() {
		return dateLivraison;
	}

	public DetailCommandeFournisseur getDetailCommandeFournisseur() {
		return detailCommandeFournisseur;
	}

	public String getHeure() {
		return Heure;
	}

	public List<Produit> getListeProduit() {
		return listeProduit;
	}

	public double getPrix() {
		return prix;
	}

	public int getQuantite() {
		return quantite;
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public void setBoutique(Boutique boutique) {
		this.boutique = boutique;
	}

	public void setCommandeFournisseur(CommandeFournisseur commandeFournisseur) {
		this.commandeFournisseur = commandeFournisseur;
	}

	public void setDateLivraison(Date dateLivraison) {
		this.dateLivraison = dateLivraison;
	}

	public void setDetailCommandeFournisseur(DetailCommandeFournisseur detailCommandeFournisseur) {
		this.detailCommandeFournisseur = detailCommandeFournisseur;
	}

	public void setHeure(String heure) {
		Heure = heure;
	}

	public void setListeProduit(List<Produit> listeProduit) {
		this.listeProduit = listeProduit;
	}

	public void setPrix(double prix) {
		this.prix = prix;
	}

	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

}
