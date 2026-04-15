package org.sid.saranApp.dto;

import org.sid.saranApp.enume.StatusCommandeFournisseurEnum;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.sid.saranApp.dto.version.TypeUniteDeVenteDto;

public class ProduitDto extends ResponseDto {

	private String uuid;
	private double prixAchat;
	private double prixVente;
	private int quantite;
	private int quantiteImage;
	private int quantiteCommande;
	private int quantiteLivraison;
	private int quantitePublish;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyy-MM-dd")
	private Date datePeremption;
	private String article;
	private String uuidArticle;
	private String categorie;
	private String emplacement;
	private String uuidEmplacement;
	private Date dateCommande;
	private String fournisseur;
	private String uuidFournisseur;
	private String uuidBoutique;
	private String uuidUtilisateur;
	private String unite;
	private boolean isFinish;
	private int quantiteVendu;
	private String etagere;
	
	private List<CaracteristiqueProduitDto> caracteristiqueArticleDtos = new ArrayList<CaracteristiqueProduitDto>();
	private List<ProduitStoredDto> produitStoredDtos = new ArrayList<>();
	/** Unités de vente + stock / prix (point de vente). */
	private List<TypeUniteDeVenteDto> typeUniteDeVenteDtos = new ArrayList<>();

	public String getArticle() {
		return article;
	}

	public String getCategorie() {
		return categorie;
	}

	public Date getDatePeremption() {
		return datePeremption;
	}

	public void setDatePeremption(Date datePeremption) {
		this.datePeremption = datePeremption;
	}

	public Date getDateCommande() {
		return dateCommande;
	}

	public void setDateCommande(Date dateCommande) {
		this.dateCommande = dateCommande;
	}

	public String getEmplacement() {
		return emplacement;
	}

	public String getEtagere() {
		return etagere;
	}

	public String getFournisseur() {
		return fournisseur;
	}

	public double getPrixAchat() {
		return prixAchat;
	}

	public double getPrixVente() {
		return prixVente;
	}

	public int getQuantite() {
		return quantite;
	}

	public int getQuantiteImage() {
		return quantiteImage;
	}

	public int getQuantiteVendu() {
		return quantiteVendu;
	}



	public String getUnite() {
		return unite;
	}

	public String getUuid() {
		return uuid;
	}

	public String getUuidBoutique() {
		return uuidBoutique;
	}

	public String getUuidEmplacement() {
		return uuidEmplacement;
	}


	public String getUuidUtilisateur() {
		return uuidUtilisateur;
	}

	public boolean isFinish() {
		return isFinish;
	}

	public void setArticle(String article) {
		this.article = article;
	}

	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}

	



	public void setEmplacement(String emplacement) {
		this.emplacement = emplacement;
	}

	public void setEtagere(String etagere) {
		this.etagere = etagere;
	}

	public void setFinish(boolean isFinish) {
		this.isFinish = isFinish;
	}

	public void setFournisseur(String fournisseur) {
		this.fournisseur = fournisseur;
	}

	public void setPrixAchat(double prixAchat) {
		this.prixAchat = prixAchat;
	}

	public void setPrixVente(double prixVente) {
		this.prixVente = prixVente;
	}

	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}

	public void setQuantiteImage(int quantiteImage) {
		this.quantiteImage = quantiteImage;
	}

	public void setQuantiteVendu(int quantiteVendu) {
		this.quantiteVendu = quantiteVendu;
	}



	public void setUnite(String unite) {
		this.unite = unite;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public void setUuidBoutique(String uuidBoutique) {
		this.uuidBoutique = uuidBoutique;
	}

	public void setUuidEmplacement(String uuidEmplacement) {
		this.uuidEmplacement = uuidEmplacement;
	}



	public void setUuidUtilisateur(String uuidUtilisateur) {
		this.uuidUtilisateur = uuidUtilisateur;
	}

	public List<CaracteristiqueProduitDto> getCaracteristiqueArticleDtos() {
		return caracteristiqueArticleDtos;
	}

	public void setCaracteristiqueArticleDtos(List<CaracteristiqueProduitDto> caracteristiqueArticleDtos) {
		this.caracteristiqueArticleDtos = caracteristiqueArticleDtos;
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

	public String getUuidFournisseur() {
		return uuidFournisseur;
	}

	public void setUuidFournisseur(String uuidFournisseur) {
		this.uuidFournisseur = uuidFournisseur;
	}

	public String getUuidArticle() {
		return uuidArticle;
	}

	public void setUuidArticle(String uuidArticle) {
		this.uuidArticle = uuidArticle;
	}

	public List<ProduitStoredDto> getProduitStoredDtos() {
		return produitStoredDtos;
	}

	public void setProduitStoredDtos(List<ProduitStoredDto> produitStoredDtos) {
		this.produitStoredDtos = produitStoredDtos;
	}

	public List<TypeUniteDeVenteDto> getTypeUniteDeVenteDtos() {
		return typeUniteDeVenteDtos;
	}

	public void setTypeUniteDeVenteDtos(List<TypeUniteDeVenteDto> typeUniteDeVenteDtos) {
		this.typeUniteDeVenteDtos = typeUniteDeVenteDtos != null ? typeUniteDeVenteDtos : new ArrayList<>();
	}
}
