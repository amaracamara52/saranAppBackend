package org.sid.saranApp.dto;

public class ProduitStoredDto {

    private String uuid;
    private String uuidProduit;
    private String uuidStored;
    private String image;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUuidProduit() {
        return uuidProduit;
    }

    public void setUuidProduit(String uuidProduit) {
        this.uuidProduit = uuidProduit;
    }

    public String getUuidStored() {
        return uuidStored;
    }

    public void setUuidStored(String uuidStored) {
        this.uuidStored = uuidStored;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
