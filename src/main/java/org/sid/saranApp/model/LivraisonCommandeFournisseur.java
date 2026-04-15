package org.sid.saranApp.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class LivraisonCommandeFournisseur extends AbstractDomainClass {

	private Date dateLivraison;
	private String Heure;
	private int quantite;
	private double prix;

	@ManyToOne
	private DetailCommandeFournisseur detailCommandeFournisseur;
	@ManyToOne
	private Boutique boutique;
	@ManyToOne
	private CommandeFournisseur commandeFournisseur;
	@ManyToOne
	private Utilisateur utilisateur;
	
	/**
	 * Liste des produits créés à partir de cette livraison (traçabilité)
	 */
	@OneToMany(mappedBy = "livraisonCommandeFournisseur")
	private List<Produit> produits = new ArrayList<>();
	
	/**
	 * Liste des conteneurs associés à cette livraison
	 */
	@OneToMany(mappedBy = "livraisonCommandeFournisseur")
	private List<Conteneur> conteneurs = new ArrayList<>();
	
	/**
	 * Liste des frais de dédouanement associés à cette livraison
	 */
	@OneToMany(mappedBy = "livraisonCommandeFournisseur")
	private List<FraisDedouanement> fraisDedouanement = new ArrayList<>();
	
	/**
	 * Numéro de suivi de la livraison
	 */
	private String numeroSuivi;
	
	/**
	 * Transporteur
	 */
	private String transporteur;
	
	/**
	 * Date d'arrivée au port
	 */
	private Date dateArriveePort;
	
	/**
	 * Date de dédouanement
	 */
	private Date dateDedouanement;
	
	/**
	 * Statut de vérification de la livraison (EN_ATTENTE, VERIFIEE, REJETEE)
	 */
	private String statutVerification;
	
	/**
	 * Indique si la livraison a été vérifiée
	 */
	private boolean verifiee;
	
	/**
	 * Date de vérification de la livraison
	 */
	private Date dateVerification;
	
	/**
	 * Utilisateur qui a vérifié la livraison
	 */
	@ManyToOne
	private Utilisateur utilisateurVerificateur;
	
	/**
	 * Commentaire de vérification
	 */
	private String commentaireVerification;

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



	public void setPrix(double prix) {
		this.prix = prix;
	}

	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public List<Produit> getProduits() {
		return produits;
	}

	public void setProduits(List<Produit> produits) {
		this.produits = produits;
	}

	public List<Conteneur> getConteneurs() {
		return conteneurs;
	}

	public void setConteneurs(List<Conteneur> conteneurs) {
		this.conteneurs = conteneurs;
	}

	public List<FraisDedouanement> getFraisDedouanement() {
		return fraisDedouanement;
	}

	public void setFraisDedouanement(List<FraisDedouanement> fraisDedouanement) {
		this.fraisDedouanement = fraisDedouanement;
	}

	public String getNumeroSuivi() {
		return numeroSuivi;
	}

	public void setNumeroSuivi(String numeroSuivi) {
		this.numeroSuivi = numeroSuivi;
	}

	public String getTransporteur() {
		return transporteur;
	}

	public void setTransporteur(String transporteur) {
		this.transporteur = transporteur;
	}

	public Date getDateArriveePort() {
		return dateArriveePort;
	}

	public void setDateArriveePort(Date dateArriveePort) {
		this.dateArriveePort = dateArriveePort;
	}

	public Date getDateDedouanement() {
		return dateDedouanement;
	}

	public void setDateDedouanement(Date dateDedouanement) {
		this.dateDedouanement = dateDedouanement;
	}

	public String getStatutVerification() {
		return statutVerification;
	}

	public void setStatutVerification(String statutVerification) {
		this.statutVerification = statutVerification;
	}

	public boolean isVerifiee() {
		return verifiee;
	}

	public void setVerifiee(boolean verifiee) {
		this.verifiee = verifiee;
	}

	public Date getDateVerification() {
		return dateVerification;
	}

	public void setDateVerification(Date dateVerification) {
		this.dateVerification = dateVerification;
	}

	public Utilisateur getUtilisateurVerificateur() {
		return utilisateurVerificateur;
	}

	public void setUtilisateurVerificateur(Utilisateur utilisateurVerificateur) {
		this.utilisateurVerificateur = utilisateurVerificateur;
	}

	public String getCommentaireVerification() {
		return commentaireVerification;
	}

	public void setCommentaireVerification(String commentaireVerification) {
		this.commentaireVerification = commentaireVerification;
	}

}
