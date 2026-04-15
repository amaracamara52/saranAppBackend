package org.sid.saranApp.dto.request;

import java.util.Date;

public class LigneRequestDto {

    private int quantite;
    private String uuidProduit;
    private String uuidTypeUniteDeVente;

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public String getUuidProduit() {
        return uuidProduit;
    }

    public void setUuidProduit(String uuidProduit) {
        this.uuidProduit = uuidProduit;
    }

    public String getUuidTypeUniteDeVente() {
        return uuidTypeUniteDeVente;
    }

    public void setUuidTypeUniteDeVente(String uuidTypeUniteDeVente) {
        this.uuidTypeUniteDeVente = uuidTypeUniteDeVente;
    }
}
