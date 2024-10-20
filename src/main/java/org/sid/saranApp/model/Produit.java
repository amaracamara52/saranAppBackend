package org.sid.saranApp.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Produit extends AbstractDomainClass {

	private int prixAchat;
	private int prixVente;
	private int quantite;
	private int quantiteImage;
	private Date datePeremption;
	private boolean isFinish;

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
	@ManyToOne
	private EtagereRayon emplacement;

	public Boutique getBoutique() {
		return boutique;
	}

	public Date getDatePeremption() {
		return datePeremption;
	}

	public EtagereRayon getEmplacement() {
		return emplacement;
	}

	public List<LigneCommande> getListeLigneCommandes() {
		return listeLigneCommandes;
	}

	public List<Parametre> getListeParametre() {
		return listeParametre;
	}

	public LivraisonCommandeFournisseur getLivraisonCommandeFournisseur() {
		return livraisonCommandeFournisseur;
	}

	public int getPrixAchat() {
		return prixAchat;
	}

	public int getPrixVente() {
		return prixVente;
	}

	public int getQuantite() {
		return quantite;
	}

	public int getQuantiteImage() {
		return quantiteImage;
	}

	public Utilisateur getUtilisateur() {
		return utilisateur;
	}

	public boolean isFinish() {
		return isFinish;
	}

	public void setBoutique(Boutique boutique) {
		this.boutique = boutique;
	}

	public void setDatePeremption(Date datePeremption) {
		this.datePeremption = datePeremption;
	}

	public void setEmplacement(EtagereRayon emplacement) {
		this.emplacement = emplacement;
	}

	public void setFinish(boolean isFinish) {
		this.isFinish = isFinish;
	}

	public void setListeLigneCommandes(List<LigneCommande> listeLigneCommandes) {
		this.listeLigneCommandes = listeLigneCommandes;
	}

	public void setListeParametre(List<Parametre> listeParametre) {
		this.listeParametre = listeParametre;
	}

	public void setLivraisonCommandeFournisseur(LivraisonCommandeFournisseur livraisonCommandeFournisseur) {
		this.livraisonCommandeFournisseur = livraisonCommandeFournisseur;
	}

	public void setPrixAchat(int prixAchat) {
		this.prixAchat = prixAchat;
	}

	public void setPrixVente(int prixVente) {
		this.prixVente = prixVente;
	}

	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}

	public void setQuantiteImage(int quantiteImage) {
		this.quantiteImage = quantiteImage;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

}
