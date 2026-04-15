package org.sid.saranApp.dto.version;

import org.sid.saranApp.dto.CaracteristiqueProduitDto;
import org.sid.saranApp.dto.ProduitStoredDto;
import org.sid.saranApp.enume.TypeGestionUniteProduitEnum;
import org.sid.saranApp.enume.TypeUniteEnum;
import org.sid.saranApp.model.CaracteristiqueProduit;

import org.sid.saranApp.jackson.TypeUniteEnumDeserializer;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProduitStockDto {
    private String uuid;
    private Date dateCommande;
    private String libelleArticle;
    /** Référence / code produit (unique par boutique). Alias JSON {@code barcode}. */
    @JsonAlias("barcode")
    private String codeProduit;
    private String descriptionArticle;
    private String uuidArticle;
    private String libelleCategorie;
    private String uuidCategorie;
    @Enumerated(EnumType.STRING)
    @JsonAlias({ "baseUniteEnum" })
    @JsonDeserialize(using = TypeUniteEnumDeserializer.class)
    private TypeUniteEnum typeUniteEnum;
    /** NORMAL = une unité ×1 ; MIXTE = plusieurs unités avec conversion, stock interne en base. */
    @Enumerated(EnumType.STRING)
    private TypeGestionUniteProduitEnum typeGestionUnite;
    /** Unité « gros » dans l’export JSON (bon de commande / conditionnement achat). */
    @Enumerated(EnumType.STRING)
    @JsonDeserialize(using = TypeUniteEnumDeserializer.class)
    private TypeUniteEnum mainUniteEnum;
    private int mainUniteFactor;
    @Enumerated(EnumType.STRING)
    @JsonDeserialize(using = TypeUniteEnumDeserializer.class)
    private TypeUniteEnum subUniteEnum;
    /** Ex. pièces par carton pour l’unité {@link #mainUniteEnum}. */
    private int subUniteFactor;
    private int quantiteCommande;
    private int quantiteLivraison;
    private int quantitePublish;
    private int quantiteStock;
    private int quantiteStockImage;
    private int quantiteImageLivraison;
    private int quantiteVendu;
    /** Seuil minimum (alerte) en unité de base. */
    private int seuilMinimum;
    private Date datePeremption;
    private double prixAchat;
    private double prixVente;
    private boolean isFinish;
    private String uuidFournisseur;
    private String fournisseur;
    private String boutique;
    private String uuidBoutique;
    private String boutiquePrincipale;
    private String uuidBoutiquePrincipale;
    private String utilisateur;
    private String uuidUtilisateur;
    private String uuidEmplacement;
    private String emplacement;

    private List<CaracteristiqueProduitDto> caracteristiqueProduitDtos = new ArrayList<>();
    private List<ProduitStoredDto> produitStoredDtos = new ArrayList<>();
    private List<TypeUniteDeVenteDto> typeUniteDeVenteDtos = new ArrayList<>();


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Date getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(Date dateCommande) {
        this.dateCommande = dateCommande;
    }

    public String getLibelleArticle() {
        return libelleArticle;
    }

    public void setLibelleArticle(String libelleArticle) {
        this.libelleArticle = libelleArticle;
    }

    public String getCodeProduit() {
        return codeProduit;
    }

    public void setCodeProduit(String codeProduit) {
        this.codeProduit = codeProduit;
    }

    public String getDescriptionArticle() {
        return descriptionArticle;
    }

    public void setDescriptionArticle(String descriptionArticle) {
        this.descriptionArticle = descriptionArticle;
    }

    public String getUuidArticle() {
        return uuidArticle;
    }

    public void setUuidArticle(String uuidArticle) {
        this.uuidArticle = uuidArticle;
    }

    public String getLibelleCategorie() {
        return libelleCategorie;
    }

    public void setLibelleCategorie(String libelleCategorie) {
        this.libelleCategorie = libelleCategorie;
    }

    public String getUuidCategorie() {
        return uuidCategorie;
    }

    public void setUuidCategorie(String uuidCategorie) {
        this.uuidCategorie = uuidCategorie;
    }

    public TypeUniteEnum getTypeUniteEnum() {
        return typeUniteEnum;
    }

    public void setTypeUniteEnum(TypeUniteEnum typeUniteEnum) {
        this.typeUniteEnum = typeUniteEnum;
    }

    public TypeGestionUniteProduitEnum getTypeGestionUnite() {
        return typeGestionUnite;
    }

    public void setTypeGestionUnite(TypeGestionUniteProduitEnum typeGestionUnite) {
        this.typeGestionUnite = typeGestionUnite;
    }

    public TypeUniteEnum getMainUniteEnum() {
        return mainUniteEnum;
    }

    public void setMainUniteEnum(TypeUniteEnum mainUniteEnum) {
        this.mainUniteEnum = mainUniteEnum;
    }

    public int getMainUniteFactor() {
        return mainUniteFactor;
    }

    public void setMainUniteFactor(int mainUniteFactor) {
        this.mainUniteFactor = mainUniteFactor;
    }

    public TypeUniteEnum getSubUniteEnum() {
        return subUniteEnum;
    }

    public void setSubUniteEnum(TypeUniteEnum subUniteEnum) {
        this.subUniteEnum = subUniteEnum;
    }

    public int getSubUniteFactor() {
        return subUniteFactor;
    }

    public void setSubUniteFactor(int subUniteFactor) {
        this.subUniteFactor = subUniteFactor;
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

    public int getQuantiteStock() {
        return quantiteStock;
    }

    public void setQuantiteStock(int quantiteStock) {
        this.quantiteStock = quantiteStock;
    }

    public int getQuantiteStockImage() {
        return quantiteStockImage;
    }

    public void setQuantiteStockImage(int quantiteStockImage) {
        this.quantiteStockImage = quantiteStockImage;
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

    public Date getDatePeremption() {
        return datePeremption;
    }

    public void setDatePeremption(Date datePeremption) {
        this.datePeremption = datePeremption;
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

    public boolean isFinish() {
        return isFinish;
    }

    public void setFinish(boolean finish) {
        isFinish = finish;
    }

    public String getUuidFournisseur() {
        return uuidFournisseur;
    }

    public void setUuidFournisseur(String uuidFournisseur) {
        this.uuidFournisseur = uuidFournisseur;
    }

    public String getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(String fournisseur) {
        this.fournisseur = fournisseur;
    }

    public String getBoutique() {
        return boutique;
    }

    public void setBoutique(String boutique) {
        this.boutique = boutique;
    }

    public String getUuidBoutique() {
        return uuidBoutique;
    }

    public void setUuidBoutique(String uuidBoutique) {
        this.uuidBoutique = uuidBoutique;
    }

    public String getBoutiquePrincipale() {
        return boutiquePrincipale;
    }

    public void setBoutiquePrincipale(String boutiquePrincipale) {
        this.boutiquePrincipale = boutiquePrincipale;
    }

    public String getUuidBoutiquePrincipale() {
        return uuidBoutiquePrincipale;
    }

    public void setUuidBoutiquePrincipale(String uuidBoutiquePrincipale) {
        this.uuidBoutiquePrincipale = uuidBoutiquePrincipale;
    }

    public String getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(String utilisateur) {
        this.utilisateur = utilisateur;
    }

    public String getUuidUtilisateur() {
        return uuidUtilisateur;
    }

    public void setUuidUtilisateur(String uuidUtilisateur) {
        this.uuidUtilisateur = uuidUtilisateur;
    }

    public List<CaracteristiqueProduitDto> getCaracteristiqueProduitDtos() {
        return caracteristiqueProduitDtos;
    }

    public void setCaracteristiqueProduitDtos(List<CaracteristiqueProduitDto> caracteristiqueProduitDtos) {
        this.caracteristiqueProduitDtos = caracteristiqueProduitDtos;
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
        this.typeUniteDeVenteDtos = typeUniteDeVenteDtos;
    }

    public String getUuidEmplacement() {
        return uuidEmplacement;
    }

    public void setUuidEmplacement(String uuidEmplacement) {
        this.uuidEmplacement = uuidEmplacement;
    }

    public String getEmplacement() {
        return emplacement;
    }

    public void setEmplacement(String emplacement) {
        this.emplacement = emplacement;
    }
}
