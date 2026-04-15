package org.sid.saranApp.dto.mobile;

import org.sid.saranApp.enume.Boutique;
import org.sid.saranApp.enume.EnumTypeShop;
import org.sid.saranApp.enume.MonnaieEnum;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public class BoutiqueMobileDto {
    private String uuid;
    private String boutiqueName;
    private String boutiqueAdress;
    private String boutiqueContact;
    private String countryCode;
    private String pays;
    private String uuidStoredFile;
    private String image;
    private String uuidBoutique;
    @Enumerated(EnumType.STRING)
    private EnumTypeShop  boutique;
    private String typeShop;
    private String uuidTypeShop;
    @Enumerated(EnumType.STRING)
    private MonnaieEnum monnaieEnum;
    private String code;
    
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
    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }



    public String getBoutiqueName() {
        return boutiqueName;
    }

    public void setBoutiqueName(String boutiqueName) {
        this.boutiqueName = boutiqueName;
    }

    public String getBoutiqueAdress() {
        return boutiqueAdress;
    }

    public void setBoutiqueAdress(String boutiqueAdress) {
        this.boutiqueAdress = boutiqueAdress;
    }

    public String getBoutiqueContact() {
        return boutiqueContact;
    }

    public void setBoutiqueContact(String boutiqueContact) {
        this.boutiqueContact = boutiqueContact;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getUuidStoredFile() {
        return uuidStoredFile;
    }

    public void setUuidStoredFile(String uuidStoredFile) {
        this.uuidStoredFile = uuidStoredFile;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUuidBoutique() {
        return uuidBoutique;
    }

    public void setUuidBoutique(String uuidBoutique) {
        this.uuidBoutique = uuidBoutique;
    }

    public EnumTypeShop getBoutique() {
        return boutique;
    }

    public void setBoutique(EnumTypeShop boutique) {
        this.boutique = boutique;
    }

	public String getTypeShop() {
		return typeShop;
	}

	public void setTypeShop(String typeShop) {
		this.typeShop = typeShop;
	}

	public String getUuidTypeShop() {
		return uuidTypeShop;
	}

	public void setUuidTypeShop(String uuidTypeShop) {
		this.uuidTypeShop = uuidTypeShop;
	}

    public MonnaieEnum getMonnaieEnum() {
        return monnaieEnum;
    }

    public void setMonnaieEnum(MonnaieEnum monnaieEnum) {
        this.monnaieEnum = monnaieEnum;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
