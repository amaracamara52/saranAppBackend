package org.sid.saranApp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Produit extends AbstractDomainClass {

	private Date datePeremption;
	private boolean isFinish;
	@Column(name = "prix_achat", nullable = false)
	private double prixAchat;
	@Column(name = "prix_vente", nullable = false)
	private double prixVente;
	@Column(name = "quantite", nullable = false)
	private int quantite;
	@Column(name = "quantite_commande", nullable = false)
	private int quantiteCommande;
	@Column(name = "quantite_livraison", nullable = false)
	private int quantiteLivraison;
	@Column(name = "quantite_publish", nullable = false)
	private int quantitePublish;
	@Column(name = "quantite_image", nullable = false)
	private int quantiteImage;
	@Column(name = "quantite_image_livraison", nullable = false)
	private int quantiteImageLivraison;
	@Column(name = "quantite_vendu", nullable = false)
	private int quantiteVendu;

	/** Seuil minimum (alerte stock) pour ce produit en unité de base. */
	@Column(name = "seuil_minimum", nullable = false)
	private int seuilMinimum;

//	@ManyToOne
//	private LivraisonCommandeFournisseur livraisonCommandeFournisseur;
	@ManyToOne
	private Article article;
	private Date dateEnregistrement;
	@ManyToOne
	private Fournisseur fournisseur;
	@ManyToOne
	private Boutique boutique;
	@ManyToOne
	private Utilisateur utilisateur;

	@OneToMany(mappedBy = "produit")
	private List<LigneCommande> listeLigneCommandes;
	
	@OneToMany(mappedBy = "produit")
	private List<CaracteristiqueProduit> caracteristiqueProduits = new ArrayList<CaracteristiqueProduit>();
	@OneToMany(mappedBy = "produit")
	private List<ProduitStored> produitStoreds = new ArrayList<>();
	@OneToMany(mappedBy = "produit")
	private List<Parametre> listeParametre;

	@ManyToOne
	private EtagereRayon emplacement;
	
	/**
	 * Liste des stocks par unité de vente pour ce produit
	 * Les unités de vente sont définies au niveau de l'Article
	 */
	@OneToMany(mappedBy = "produit")
	private List<StockUniteVente> stockUniteVentes = new ArrayList<>();
	
	/**
	 * Livraison fournisseur à l'origine de ce produit (traçabilité)
	 */
	@ManyToOne
	private LivraisonCommandeFournisseur livraisonCommandeFournisseur;
	
	/**
	 * Livraison grossiste à l'origine de ce produit (si le produit vient d'un grossiste)
	 */
	@ManyToOne
	private LivraisonCommandeGrossiste livraisonCommandeGrossiste;

	public Date getDatePeremption() {
		return datePeremption;
	}

	public void setDatePeremption(Date datePeremption) {
		this.datePeremption = datePeremption;
	}

	public boolean isFinish() {
		return isFinish;
	}

	public void setFinish(boolean finish) {
		isFinish = finish;
	}

	public double getPrixAchat() {
		return prixAchat;
	}

	public void setPrixAchat(double prixAchat) {
		this.prixAchat = prixAchat;
	}

	public double getPrixVente() {
		return prixVente;
	}

	public void setPrixVente(double prixVente) {
		this.prixVente = prixVente;
	}

	public int getQuantite() {
		return quantite;
	}

	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}

	public int getQuantiteCommande() {
		return quantiteCommande;
	}

	public void setQuantiteCommande(int quantiteCommande) {
		this.quantiteCommande = quantiteCommande;
	}

	public int getQuantiteLivraison() {
		return quantiteLivraison;
	}

	public void setQuantiteLivraison(int quantiteLivraison) {
		this.quantiteLivraison = quantiteLivraison;
	}

	public int getQuantitePublish() {
		return quantitePublish;
	}

	public void setQuantitePublish(int quantitePublish) {
		this.quantitePublish = quantitePublish;
	}

	public int getQuantiteImage() {
		return quantiteImage;
	}

	public void setQuantiteImage(int quantiteImage) {
		this.quantiteImage = quantiteImage;
	}

	public int getQuantiteImageLivraison() {
		return quantiteImageLivraison;
	}

	public void setQuantiteImageLivraison(int quantiteImageLivraison) {
		this.quantiteImageLivraison = quantiteImageLivraison;
	}

	public int getQuantiteVendu() {
		return quantiteVendu;
	}

	public void setQuantiteVendu(int quantiteVendu) {
		this.quantiteVendu = quantiteVendu;
	}

	public int getSeuilMinimum() {
		return seuilMinimum;
	}

	public void setSeuilMinimum(int seuilMinimum) {
		this.seuilMinimum = seuilMinimum;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}



	public Date getDateEnregistrement() {
		return dateEnregistrement;
	}

	public void setDateEnregistrement(Date dateEnregistrement) {
		this.dateEnregistrement = dateEnregistrement;
	}

	public Fournisseur getFournisseur() {
		return fournisseur;
	}

	public void setFournisseur(Fournisseur fournisseur) {
		this.fournisseur = fournisseur;
	}

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

	public List<LigneCommande> getListeLigneCommandes() {
		return listeLigneCommandes;
	}

	public void setListeLigneCommandes(List<LigneCommande> listeLigneCommandes) {
		this.listeLigneCommandes = listeLigneCommandes;
	}

	public List<CaracteristiqueProduit> getCaracteristiqueProduits() {
		return caracteristiqueProduits;
	}

	public void setCaracteristiqueProduits(List<CaracteristiqueProduit> caracteristiqueProduits) {
		this.caracteristiqueProduits = caracteristiqueProduits;
	}

	public List<Parametre> getListeParametre() {
		return listeParametre;
	}

	public void setListeParametre(List<Parametre> listeParametre) {
		this.listeParametre = listeParametre;
	}

	public EtagereRayon getEmplacement() {
		return emplacement;
	}

	public void setEmplacement(EtagereRayon emplacement) {
		this.emplacement = emplacement;
	}

	

	public List<ProduitStored> getProduitStoreds() {
		return produitStoreds;
	}

	public void setProduitStoreds(List<ProduitStored> produitStoreds) {
		this.produitStoreds = produitStoreds;
	}

	public List<StockUniteVente> getStockUniteVentes() {
		return stockUniteVentes;
	}

	public void setStockUniteVentes(List<StockUniteVente> stockUniteVentes) {
		this.stockUniteVentes = stockUniteVentes;
	}

	public LivraisonCommandeFournisseur getLivraisonCommandeFournisseur() {
		return livraisonCommandeFournisseur;
	}

	public void setLivraisonCommandeFournisseur(LivraisonCommandeFournisseur livraisonCommandeFournisseur) {
		this.livraisonCommandeFournisseur = livraisonCommandeFournisseur;
	}

	public LivraisonCommandeGrossiste getLivraisonCommandeGrossiste() {
		return livraisonCommandeGrossiste;
	}

	public void setLivraisonCommandeGrossiste(LivraisonCommandeGrossiste livraisonCommandeGrossiste) {
		this.livraisonCommandeGrossiste = livraisonCommandeGrossiste;
	}

}
