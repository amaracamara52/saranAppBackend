package org.sid.saranApp.dto;

import org.sid.saranApp.enume.EnumRole;

import java.util.ArrayList;
import java.util.List;

public class BoutiqueDto {

	private String uuid;
	private String libelleBoutique;
	private String descriptionBoutique;
	private String emailBoutique;
	private String phoneBoutique;
	private String siteBoutique;
	private String adresse;
	private String domaine;
	private String uuidDomain;
	private String typeDomaine;

	private String  image;
	private String utilisateur;
	private String email;
	private String password;
	private String uuidStoreFile;
	private String pays;
	private String uuidPays;
	private EnumRole role;

	private List<DomainBoutiqueDto> domainBoutiqueDtos = new ArrayList<>();
	
	private List<String> domaines = new ArrayList<String>();
	
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
	
	public String getAdresse() {
		return adresse;
	}

	public String getDescriptionBoutique() {
		return descriptionBoutique;
	}

	public String getEmailBoutique() {
		return emailBoutique;
	}

	public String getLibelleBoutique() {
		return libelleBoutique;
	}

	public String getPhoneBoutique() {
		return phoneBoutique;
	}

	public String getSiteBoutique() {
		return siteBoutique;
	}

	
	public String getUuid() {
		return uuid;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public void setDescriptionBoutique(String descriptionBoutique) {
		this.descriptionBoutique = descriptionBoutique;
	}

	public void setEmailBoutique(String emailBoutique) {
		this.emailBoutique = emailBoutique;
	}

	public void setLibelleBoutique(String libelleBoutique) {
		this.libelleBoutique = libelleBoutique;
	}

	public void setPhoneBoutique(String phoneBoutique) {
		this.phoneBoutique = phoneBoutique;
	}

	public void setSiteBoutique(String siteBoutique) {
		this.siteBoutique = siteBoutique;
	}



	public String getDomaine() {
		return domaine;
	}

	public void setDomaine(String domaine) {
		this.domaine = domaine;
	}

	public String getUuidDomain() {
		return uuidDomain;
	}

	public void setUuidDomain(String uuidDomain) {
		this.uuidDomain = uuidDomain;
	}

	public String getTypeDomaine() {
		return typeDomaine;
	}

	public void setTypeDomaine(String typeDomaine) {
		this.typeDomaine = typeDomaine;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(String utilisateur) {
		this.utilisateur = utilisateur;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUuidStoreFile() {
		return uuidStoreFile;
	}

	public void setUuidStoreFile(String uuidStoreFile) {
		this.uuidStoreFile = uuidStoreFile;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<String> getDomaines() {
		return domaines;
	}

	public void setDomaines(List<String> domaines) {
		this.domaines = domaines;
	}

	public String getPays() {
		return pays;
	}

	public void setPays(String pays) {
		this.pays = pays;
	}

	public String getUuidPays() {
		return uuidPays;
	}

	public void setUuidPays(String uuidPays) {
		this.uuidPays = uuidPays;
	}

	public EnumRole getRole() {
		return role;
	}

	public void setRole(EnumRole role) {
		this.role = role;
	}

	public List<DomainBoutiqueDto> getDomainBoutiqueDtos() {
		return domainBoutiqueDtos;
	}

	public void setDomainBoutiqueDtos(List<DomainBoutiqueDto> domainBoutiqueDtos) {
		this.domainBoutiqueDtos = domainBoutiqueDtos;
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
}
