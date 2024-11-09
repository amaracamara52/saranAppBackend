package org.sid.saranApp.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.sid.saranApp.enume.EnumTypeBoutique;

@Entity
public class Boutique extends AbstractDomainClass {

	private String libelleBoutique;
	private String descriptionBoutique;
	private String emailBoutique;
	private String phoneBoutique;
	private String siteBoutique;

	private String adresse;

	@OneToMany(mappedBy = "boutique")
	private List<BoutiquePaiement> listeBoutiquePaiement;
	@OneToMany(mappedBy = "boutique")
	private List<CommandeFournisseur> listeCommandeFournisseur;
	@OneToMany(mappedBy = "boutique")
	private List<DetailCommandeFournisseur> listeDetailCommandeFournisseur;
	@OneToMany(mappedBy = "boutique")
	private List<Fournisseur> listeFournisseur;
	@OneToMany(mappedBy = "boutique")
	private List<LivraisonCommandeFournisseur> listeLivraisonCommandeFournisseur;
	@OneToMany(mappedBy = "boutique")
	private List<PaiementCommandeFournisseur> listePaiementCommandeFournisseur;
	@OneToMany(mappedBy = "boutique")
	private List<Article> listeArticle;
	@OneToMany(mappedBy = "boutique")
	private List<CaracteristiqueProduit> listeCaracteristiqueProduit;
	@OneToMany(mappedBy = "boutique")
	private List<Categorie> listeCategorie;
	@OneToMany(mappedBy = "boutique")
	private List<Client> listeClient;
	@OneToMany(mappedBy = "boutique")
	private List<CommandeVente> listeCommandeVente;
//	@OneToMany(mappedBy = "boutique")
//	private List<Commune> listeCommune;

	@OneToMany(mappedBy = "boutique")
	private List<LigneCommande> listeLigneCommande;
	@OneToMany(mappedBy = "boutique")
	private List<ModePaiement> listeModePaiement;
//	@OneToMany(mappedBy = "boutique")
//	private List<Pays> listePays;
	@OneToMany(mappedBy = "boutique")
	private List<Produit> listeProduit;
	@OneToMany(mappedBy = "boutique")
	private List<Quartier> listeQuartier;
//	@OneToMany(mappedBy = "boutique")
//	private List<Ville> listeVille;
	@OneToMany(mappedBy = "boutique")
	private List<Parametre> listeParametre;

	@Enumerated(EnumType.STRING)
	@Column(name = "TYPEBOUTIQUE")
	private EnumTypeBoutique typeBoutique;
	
	@OneToOne
	private StoredFile storedFile;
	
	private String pays;

	public String getLibelleBoutique() {
		return libelleBoutique;
	}

	public void setLibelleBoutique(String libelleBoutique) {
		this.libelleBoutique = libelleBoutique;
	}

	public String getDescriptionBoutique() {
		return descriptionBoutique;
	}

	public void setDescriptionBoutique(String descriptionBoutique) {
		this.descriptionBoutique = descriptionBoutique;
	}

	public String getEmailBoutique() {
		return emailBoutique;
	}

	public void setEmailBoutique(String emailBoutique) {
		this.emailBoutique = emailBoutique;
	}

	public String getPhoneBoutique() {
		return phoneBoutique;
	}

	public void setPhoneBoutique(String phoneBoutique) {
		this.phoneBoutique = phoneBoutique;
	}

	public String getSiteBoutique() {
		return siteBoutique;
	}

	public void setSiteBoutique(String siteBoutique) {
		this.siteBoutique = siteBoutique;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public List<BoutiquePaiement> getListeBoutiquePaiement() {
		return listeBoutiquePaiement;
	}

	public void setListeBoutiquePaiement(List<BoutiquePaiement> listeBoutiquePaiement) {
		this.listeBoutiquePaiement = listeBoutiquePaiement;
	}

	public List<CommandeFournisseur> getListeCommandeFournisseur() {
		return listeCommandeFournisseur;
	}

	public void setListeCommandeFournisseur(List<CommandeFournisseur> listeCommandeFournisseur) {
		this.listeCommandeFournisseur = listeCommandeFournisseur;
	}

	public List<DetailCommandeFournisseur> getListeDetailCommandeFournisseur() {
		return listeDetailCommandeFournisseur;
	}

	public void setListeDetailCommandeFournisseur(List<DetailCommandeFournisseur> listeDetailCommandeFournisseur) {
		this.listeDetailCommandeFournisseur = listeDetailCommandeFournisseur;
	}

	public List<Fournisseur> getListeFournisseur() {
		return listeFournisseur;
	}

	public void setListeFournisseur(List<Fournisseur> listeFournisseur) {
		this.listeFournisseur = listeFournisseur;
	}

	public List<LivraisonCommandeFournisseur> getListeLivraisonCommandeFournisseur() {
		return listeLivraisonCommandeFournisseur;
	}

	public void setListeLivraisonCommandeFournisseur(List<LivraisonCommandeFournisseur> listeLivraisonCommandeFournisseur) {
		this.listeLivraisonCommandeFournisseur = listeLivraisonCommandeFournisseur;
	}

	public List<PaiementCommandeFournisseur> getListePaiementCommandeFournisseur() {
		return listePaiementCommandeFournisseur;
	}

	public void setListePaiementCommandeFournisseur(List<PaiementCommandeFournisseur> listePaiementCommandeFournisseur) {
		this.listePaiementCommandeFournisseur = listePaiementCommandeFournisseur;
	}

	public List<Article> getListeArticle() {
		return listeArticle;
	}

	public void setListeArticle(List<Article> listeArticle) {
		this.listeArticle = listeArticle;
	}

	public List<CaracteristiqueProduit> getListeCaracteristiqueProduit() {
		return listeCaracteristiqueProduit;
	}

	public void setListeCaracteristiqueProduit(List<CaracteristiqueProduit> listeCaracteristiqueProduit) {
		this.listeCaracteristiqueProduit = listeCaracteristiqueProduit;
	}

	public List<Categorie> getListeCategorie() {
		return listeCategorie;
	}

	public void setListeCategorie(List<Categorie> listeCategorie) {
		this.listeCategorie = listeCategorie;
	}

	public List<Client> getListeClient() {
		return listeClient;
	}

	public void setListeClient(List<Client> listeClient) {
		this.listeClient = listeClient;
	}

	public List<CommandeVente> getListeCommandeVente() {
		return listeCommandeVente;
	}

	public void setListeCommandeVente(List<CommandeVente> listeCommandeVente) {
		this.listeCommandeVente = listeCommandeVente;
	}

	public List<LigneCommande> getListeLigneCommande() {
		return listeLigneCommande;
	}

	public void setListeLigneCommande(List<LigneCommande> listeLigneCommande) {
		this.listeLigneCommande = listeLigneCommande;
	}

	public List<ModePaiement> getListeModePaiement() {
		return listeModePaiement;
	}

	public void setListeModePaiement(List<ModePaiement> listeModePaiement) {
		this.listeModePaiement = listeModePaiement;
	}

	public List<Produit> getListeProduit() {
		return listeProduit;
	}

	public void setListeProduit(List<Produit> listeProduit) {
		this.listeProduit = listeProduit;
	}

	public List<Quartier> getListeQuartier() {
		return listeQuartier;
	}

	public void setListeQuartier(List<Quartier> listeQuartier) {
		this.listeQuartier = listeQuartier;
	}

	public List<Parametre> getListeParametre() {
		return listeParametre;
	}

	public void setListeParametre(List<Parametre> listeParametre) {
		this.listeParametre = listeParametre;
	}

	public EnumTypeBoutique getTypeBoutique() {
		return typeBoutique;
	}

	public void setTypeBoutique(EnumTypeBoutique typeBoutique) {
		this.typeBoutique = typeBoutique;
	}

	public StoredFile getStoredFile() {
		return storedFile;
	}

	public void setStoredFile(StoredFile storedFile) {
		this.storedFile = storedFile;
	}

	public String getPays() {
		return pays;
	}

	public void setPays(String pays) {
		this.pays = pays;
	}
	
	

	
}
