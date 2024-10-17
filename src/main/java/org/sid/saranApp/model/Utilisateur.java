package org.sid.saranApp.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.sid.saranApp.enume.EnumRole;

@Entity
public class Utilisateur extends AbstractDomainClass {

	private String username;
	private String phone;
	private String email;
	private String adresse;

	@OneToMany(mappedBy = "utilisateur")
	private List<BoutiquePaiement> listeBoutiquePaiement;
	@OneToMany(mappedBy = "utilisateur")
	private List<CommandeFournisseur> listeCommandeFournisseur;
	@OneToMany(mappedBy = "utilisateur")
	private List<DetailCommandeFournisseur> listeDetailCommandeFournisseur;
	@OneToMany(mappedBy = "utilisateur")
	private List<Fournisseur> listeFournisseur;
	@OneToMany(mappedBy = "utilisateur")
	private List<LivraisonCommandeFournisseur> listeLivraisonCommandeFournisseur;
	@OneToMany(mappedBy = "utilisateur")
	private List<PaiementCommandeFournisseur> listePaiementCommandeFournisseur;
	@OneToMany(mappedBy = "utilisateur")
	private List<Article> listeArticle;
	@OneToMany(mappedBy = "utilisateur")
	private List<CaracteristiqueProduit> listeCaracteristiqueProduit;
	@OneToMany(mappedBy = "utilisateur")
	private List<Categorie> listeCategorie;
	@OneToMany(mappedBy = "utilisateur")
	private List<Client> listeClient;
	@OneToMany(mappedBy = "utilisateur")
	private List<CommandeVente> listeCommandeVente;
	@OneToMany(mappedBy = "utilisateur")
	private List<Commune> listeCommune;

	@OneToMany(mappedBy = "utilisateur")
	private List<LigneCommande> listeLigneCommande;
	@OneToMany(mappedBy = "utilisateur")
	private List<ModePaiement> listeModePaiement;
	@OneToMany(mappedBy = "utilisateur")
	private List<Pays> listePays;
	@OneToMany(mappedBy = "utilisateur")
	private List<Produit> listeProduit;
	@OneToMany(mappedBy = "utilisateur")
	private List<Quartier> listeQuartier;
	@OneToMany(mappedBy = "utilisateur")
	private List<Ville> listeVille;

	@ElementCollection
	@CollectionTable(name = "UTILISATEUR_ROLES", joinColumns = @JoinColumn(name = "UUID"))
	@Enumerated(EnumType.STRING)
	@Column(name = "ROLE")
	private List<EnumRole> role = new ArrayList<EnumRole>();

	private boolean online;
	@Column(name = "HASHED_PASSWORD", nullable = true)
	private String password;

	boolean nonExpired = true;

	boolean nonLocked = true;

	boolean Enabled = false;

	@ManyToOne
	private Boutique boutique;

	public String getAdresse() {
		return adresse;
	}

	public Boutique getBoutique() {
		return boutique;
	}

	public String getEmail() {
		return email;
	}

	public List<Article> getListeArticle() {
		return listeArticle;
	}

	public List<BoutiquePaiement> getListeBoutiquePaiement() {
		return listeBoutiquePaiement;
	}

	public List<CaracteristiqueProduit> getListeCaracteristiqueProduit() {
		return listeCaracteristiqueProduit;
	}

	public List<Categorie> getListeCategorie() {
		return listeCategorie;
	}

	public List<Client> getListeClient() {
		return listeClient;
	}

	public List<CommandeFournisseur> getListeCommandeFournisseur() {
		return listeCommandeFournisseur;
	}

	public List<CommandeVente> getListeCommandeVente() {
		return listeCommandeVente;
	}

	public List<Commune> getListeCommune() {
		return listeCommune;
	}

	public List<DetailCommandeFournisseur> getListeDetailCommandeFournisseur() {
		return listeDetailCommandeFournisseur;
	}

	public List<Fournisseur> getListeFournisseur() {
		return listeFournisseur;
	}

	public List<LigneCommande> getListeLigneCommande() {
		return listeLigneCommande;
	}

	public List<LivraisonCommandeFournisseur> getListeLivraisonCommandeFournisseur() {
		return listeLivraisonCommandeFournisseur;
	}

	public List<ModePaiement> getListeModePaiement() {
		return listeModePaiement;
	}

	public List<PaiementCommandeFournisseur> getListePaiementCommandeFournisseur() {
		return listePaiementCommandeFournisseur;
	}

	public List<Pays> getListePays() {
		return listePays;
	}

	public List<Produit> getListeProduit() {
		return listeProduit;
	}

	public List<Quartier> getListeQuartier() {
		return listeQuartier;
	}

	public List<Ville> getListeVille() {
		return listeVille;
	}

	public String getPassword() {
		return password;
	}

	public String getPhone() {
		return phone;
	}

	public List<EnumRole> getRole() {
		return role;
	}

	public String getUsername() {
		return username;
	}

	public boolean isEnabled() {
		return Enabled;
	}

	public boolean isNonExpired() {
		return nonExpired;
	}

	public boolean isNonLocked() {
		return nonLocked;
	}

	public boolean isOnline() {
		return online;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public void setBoutique(Boutique boutique) {
		this.boutique = boutique;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setEnabled(boolean enabled) {
		Enabled = enabled;
	}

	public void setListeArticle(List<Article> listeArticle) {
		this.listeArticle = listeArticle;
	}

	public void setListeBoutiquePaiement(List<BoutiquePaiement> listeBoutiquePaiement) {
		this.listeBoutiquePaiement = listeBoutiquePaiement;
	}

	public void setListeCaracteristiqueProduit(List<CaracteristiqueProduit> listeCaracteristiqueProduit) {
		this.listeCaracteristiqueProduit = listeCaracteristiqueProduit;
	}

	public void setListeCategorie(List<Categorie> listeCategorie) {
		this.listeCategorie = listeCategorie;
	}

	public void setListeClient(List<Client> listeClient) {
		this.listeClient = listeClient;
	}

	public void setListeCommandeFournisseur(List<CommandeFournisseur> listeCommandeFournisseur) {
		this.listeCommandeFournisseur = listeCommandeFournisseur;
	}

	public void setListeCommandeVente(List<CommandeVente> listeCommandeVente) {
		this.listeCommandeVente = listeCommandeVente;
	}

	public void setListeCommune(List<Commune> listeCommune) {
		this.listeCommune = listeCommune;
	}

	public void setListeDetailCommandeFournisseur(List<DetailCommandeFournisseur> listeDetailCommandeFournisseur) {
		this.listeDetailCommandeFournisseur = listeDetailCommandeFournisseur;
	}

	public void setListeFournisseur(List<Fournisseur> listeFournisseur) {
		this.listeFournisseur = listeFournisseur;
	}

	public void setListeLigneCommande(List<LigneCommande> listeLigneCommande) {
		this.listeLigneCommande = listeLigneCommande;
	}

	public void setListeLivraisonCommandeFournisseur(
			List<LivraisonCommandeFournisseur> listeLivraisonCommandeFournisseur) {
		this.listeLivraisonCommandeFournisseur = listeLivraisonCommandeFournisseur;
	}

	public void setListeModePaiement(List<ModePaiement> listeModePaiement) {
		this.listeModePaiement = listeModePaiement;
	}

	public void setListePaiementCommandeFournisseur(
			List<PaiementCommandeFournisseur> listePaiementCommandeFournisseur) {
		this.listePaiementCommandeFournisseur = listePaiementCommandeFournisseur;
	}

	public void setListePays(List<Pays> listePays) {
		this.listePays = listePays;
	}

	public void setListeProduit(List<Produit> listeProduit) {
		this.listeProduit = listeProduit;
	}

	public void setListeQuartier(List<Quartier> listeQuartier) {
		this.listeQuartier = listeQuartier;
	}

	public void setListeVille(List<Ville> listeVille) {
		this.listeVille = listeVille;
	}

	public void setNonExpired(boolean nonExpired) {
		this.nonExpired = nonExpired;
	}

	public void setNonLocked(boolean nonLocked) {
		this.nonLocked = nonLocked;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setRole(List<EnumRole> role) {
		this.role = role;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
