package org.sid.saranApp.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Produit extends AbstractDomainClass{

	private String prixAchat;
	private String prixVente;
	private int qte;
	
	@ManyToOne
	private LivraisonCommandeFournisseur livraisonCommandeFournisseur;
	@ManyToOne
	private Boutique boutique;
	@ManyToOne
	private Utilisateur utilisateur;
	
	@OneToMany(mappedBy = "produit")
	private List<LigneCommande> listeLigneCommandes;
	@OneToMany(mappedBy = "produit")
	private List<Parametre> listeParametre;

	
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

	public String getPrixAchat() {
		return prixAchat;
	}

	public void setPrixAchat(String prixAchat) {
		this.prixAchat = prixAchat;
	}

	public String getPrixVente() {
		return prixVente;
	}

	public void setPrixVente(String prixVente) {
		this.prixVente = prixVente;
	}

	

	public int getQte() {
		return qte;
	}


	public void setQte(int qte) {
		this.qte = qte;
	}


	public List<Parametre> getListeParametre() {
		return listeParametre;
	}

	public void setListeParametre(List<Parametre> listeParametre) {
		this.listeParametre = listeParametre;
	}

	public List<LigneCommande> getListeLigneCommandes() {
		return listeLigneCommandes;
	}

	public void setListeLigneCommandes(List<LigneCommande> listeLigneCommandes) {
		this.listeLigneCommandes = listeLigneCommandes;
	}

	public LivraisonCommandeFournisseur getLivraisonCommandeFournisseur() {
		return livraisonCommandeFournisseur;
	}

	public void setLivraisonCommandeFournisseur(LivraisonCommandeFournisseur livraisonCommandeFournisseur) {
		this.livraisonCommandeFournisseur = livraisonCommandeFournisseur;
	}

}
