package org.sid.saranApp.dto.version;

import org.sid.saranApp.enume.TypeUniteEnum;
import org.sid.saranApp.jackson.TypeUniteEnumDeserializer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public class TypeUniteDeVenteDto {

    private String uuid;
    @JsonDeserialize(using = TypeUniteEnumDeserializer.class)
    private TypeUniteEnum typeUniteEnum;
    private String uuidProduit;
    private String produit;
    private int unite;
    private int qtite;
    private double price;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public TypeUniteEnum getTypeUniteEnum() {
        return typeUniteEnum;
    }

    public void setTypeUniteEnum(TypeUniteEnum typeUniteEnum) {
        this.typeUniteEnum = typeUniteEnum;
    }

    public String getUuidProduit() {
        return uuidProduit;
    }

    public void setUuidProduit(String uuidProduit) {
        this.uuidProduit = uuidProduit;
    }

    public String getProduit() {
        return produit;
    }

    public void setProduit(String produit) {
        this.produit = produit;
    }

	public int getUnite() {
		return unite;
	}

	public void setUnite(int unite) {
		this.unite = unite;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

    public int getQtite() {
        return qtite;
    }

    public void setQtite(int qtite) {
        this.qtite = qtite;
    }
}
