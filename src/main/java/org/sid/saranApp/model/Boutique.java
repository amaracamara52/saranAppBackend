package org.sid.saranApp.model;

import org.sid.saranApp.enume.EnumTypeInstanceBoutique;
import org.sid.saranApp.enume.EnumTypeShop;
import org.sid.saranApp.enume.MonnaieEnum;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Boutique extends AbstractDomainClass {

	@Column(unique = true)
	private String code;
	private String libelleBoutique;
	private String descriptionBoutique;
	private String emailBoutique;
	private String phoneBoutique;
	private String siteBoutique;
	private String adresse;
	@Enumerated(EnumType.STRING)
	private EnumTypeInstanceBoutique enumTypeInstanceBoutique;
	@Enumerated(EnumType.STRING)
	private EnumTypeShop enumTypeShop;
	
	@ManyToOne
	private TypeShop typeShop;

	@OneToMany(mappedBy = "boutique")
	private List<BoutiquePaiement> boutiquePaiements = new ArrayList<>();
	@OneToMany(mappedBy = "boutique")
	private List<Fournisseur> fournisseurs = new ArrayList<>();
	@OneToMany(mappedBy = "boutique")
	private List<Article> articles = new ArrayList<>();
	@OneToMany(mappedBy = "boutique")
	private List<Categorie> categories = new ArrayList<>();
	@OneToMany(mappedBy = "boutique")
	private List<CommandeVente> commandeVentes = new ArrayList<>();
	@OneToMany(mappedBy = "boutique")
	private List<LigneCommande> ligneCommandes = new ArrayList<>();
	@OneToMany(mappedBy = "boutique")
	private List<ModePaiement> modePaiements = new ArrayList<>();
	@OneToMany(mappedBy = "boutique")
	private List<Produit> produits = new ArrayList<>();
	@OneToMany(mappedBy = "boutique")
	private List<Parametre> parametres = new ArrayList<>();
	@OneToMany(mappedBy = "boutique")
	private List<DomainBoutique> domainBoutiques = new ArrayList<>();
	@OneToMany(mappedBy = "boutique")
	private List<Reduction> reductions = new ArrayList<>();
	@OneToMany(mappedBy = "boutique")
	private List<Utilisateur> utilisateurs = new ArrayList<>();
	@OneToMany(mappedBy = "boutique")
	private List<ClientPartenaire> clientsPartenaires = new ArrayList<>();
	@OneToOne
	private StoredFile storedFile;
//	@Column(unique = true)
	private String countryCode;
	@OneToOne
	private Boutique boutiquePrincipale;

	@Enumerated(EnumType.STRING)
	private MonnaieEnum monnaie;

	private boolean isDatePeremption;
	private String heureOuverture;
	private String heureFermeture;
	private int seuilStock;
	
	// Nouveaux champs ajoutés
	private String devise;
	private String langue;
	private int seuilAlerteStock;
	private String methodeValorisation;
	private boolean approvisionnementAutomatique;
	private int quantiteACommander;
	private boolean impressionTicket;
	private boolean impressionFacture;
	private boolean devis;
	private boolean dette;
//	private int qtiteVenteSuperieurAndCash;
//	private int qtiteVenteSuperieurAndPayUnePartie;
//	private int qtiteVenteSuperieurDette;


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

	public List<BoutiquePaiement> getBoutiquePaiements() {
		return boutiquePaiements;
	}

	public void setBoutiquePaiements(List<BoutiquePaiement> boutiquePaiements) {
		this.boutiquePaiements = boutiquePaiements;
	}

	public List<Fournisseur> getFournisseurs() {
		return fournisseurs;
	}

	public void setFournisseurs(List<Fournisseur> fournisseurs) {
		this.fournisseurs = fournisseurs;
	}

	public List<Article> getArticles() {
		return articles;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}

	public List<Categorie> getCategories() {
		return categories;
	}

	public void setCategories(List<Categorie> categories) {
		this.categories = categories;
	}

	public List<CommandeVente> getCommandeVentes() {
		return commandeVentes;
	}

	public void setCommandeVentes(List<CommandeVente> commandeVentes) {
		this.commandeVentes = commandeVentes;
	}

	public List<LigneCommande> getLigneCommandes() {
		return ligneCommandes;
	}

	public void setLigneCommandes(List<LigneCommande> ligneCommandes) {
		this.ligneCommandes = ligneCommandes;
	}

	public List<ModePaiement> getModePaiements() {
		return modePaiements;
	}

	public void setModePaiements(List<ModePaiement> modePaiements) {
		this.modePaiements = modePaiements;
	}

	public List<Produit> getProduits() {
		return produits;
	}

	public void setProduits(List<Produit> produits) {
		this.produits = produits;
	}

	public List<Parametre> getParametres() {
		return parametres;
	}

	public void setParametres(List<Parametre> parametres) {
		this.parametres = parametres;
	}

	public List<DomainBoutique> getDomainBoutiques() {
		return domainBoutiques;
	}

	public void setDomainBoutiques(List<DomainBoutique> domainBoutiques) {
		this.domainBoutiques = domainBoutiques;
	}

	public List<Reduction> getReductions() {
		return reductions;
	}

	public void setReductions(List<Reduction> reductions) {
		this.reductions = reductions;
	}

	public List<Utilisateur> getUtilisateurs() {
		return utilisateurs;
	}

	public void setUtilisateurs(List<Utilisateur> utilisateurs) {
		this.utilisateurs = utilisateurs;
	}

	public StoredFile getStoredFile() {
		return storedFile;
	}

	public void setStoredFile(StoredFile storedFile) {
		this.storedFile = storedFile;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public EnumTypeInstanceBoutique getEnumTypeInstanceBoutique() {
		return enumTypeInstanceBoutique;
	}

	public void setEnumTypeInstanceBoutique(EnumTypeInstanceBoutique enumTypeInstanceBoutique) {
		this.enumTypeInstanceBoutique = enumTypeInstanceBoutique;
	}

	public Boutique getBoutiquePrincipale() {
		return boutiquePrincipale;
	}

	public void setBoutiquePrincipale(Boutique boutiquePrincipale) {
		this.boutiquePrincipale = boutiquePrincipale;
	}

	public EnumTypeShop getEnumTypeShop() {
		return enumTypeShop;
	}

	public void setEnumTypeShop(EnumTypeShop enumTypeShop) {
		this.enumTypeShop = enumTypeShop;
	}

	public TypeShop getTypeShop() {
		return typeShop;
	}

	public void setTypeShop(TypeShop typeShop) {
		this.typeShop = typeShop;
	}

	public MonnaieEnum getMonnaie() {
		return monnaie;
	}

	public void setMonnaie(MonnaieEnum monnaie) {
		this.monnaie = monnaie;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public boolean isDatePeremption() {
		return isDatePeremption;
	}

	public void setDatePeremption(boolean datePeremption) {
		isDatePeremption = datePeremption;
	}

	public String getHeureOuverture() {
		return heureOuverture;
	}

	public void setHeureOuverture(String heureOuverture) {
		this.heureOuverture = heureOuverture;
	}

	public String getHeureFermeture() {
		return heureFermeture;
	}

	public void setHeureFermeture(String heureFermeture) {
		this.heureFermeture = heureFermeture;
	}

	public int getSeuilStock() {
		return seuilStock;
	}

	public void setSeuilStock(int seuilStock) {
		this.seuilStock = seuilStock;
	}

	// Getters et Setters pour les nouveaux champs
	public String getDevise() {
		return devise;
	}

	public void setDevise(String devise) {
		this.devise = devise;
	}

	public String getLangue() {
		return langue;
	}

	public void setLangue(String langue) {
		this.langue = langue;
	}

	public int getSeuilAlerteStock() {
		return seuilAlerteStock;
	}

	public void setSeuilAlerteStock(int seuilAlerteStock) {
		this.seuilAlerteStock = seuilAlerteStock;
	}

	public String getMethodeValorisation() {
		return methodeValorisation;
	}

	public void setMethodeValorisation(String methodeValorisation) {
		this.methodeValorisation = methodeValorisation;
	}

	public boolean isApprovisionnementAutomatique() {
		return approvisionnementAutomatique;
	}

	public void setApprovisionnementAutomatique(boolean approvisionnementAutomatique) {
		this.approvisionnementAutomatique = approvisionnementAutomatique;
	}

	public int getQuantiteACommander() {
		return quantiteACommander;
	}

	public void setQuantiteACommander(int quantiteACommander) {
		this.quantiteACommander = quantiteACommander;
	}

	public boolean isImpressionTicket() {
		return impressionTicket;
	}

	public void setImpressionTicket(boolean impressionTicket) {
		this.impressionTicket = impressionTicket;
	}

	public boolean isImpressionFacture() {
		return impressionFacture;
	}

	public void setImpressionFacture(boolean impressionFacture) {
		this.impressionFacture = impressionFacture;
	}

	public boolean isDevis() {
		return devis;
	}

	public void setDevis(boolean devis) {
		this.devis = devis;
	}

	public boolean isDette() {
		return dette;
	}

	public void setDette(boolean dette) {
		this.dette = dette;
	}

	public List<ClientPartenaire> getClientsPartenaires() {
		return clientsPartenaires;
	}

	public void setClientsPartenaires(List<ClientPartenaire> clientsPartenaires) {
		this.clientsPartenaires = clientsPartenaires;
	}
}
