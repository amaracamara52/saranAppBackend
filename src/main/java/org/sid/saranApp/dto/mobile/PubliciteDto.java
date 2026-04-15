package org.sid.saranApp.dto.mobile;

import java.util.ArrayList;
import java.util.List;

public class PubliciteDto extends ResponseDto {

    public String uuid;
    public String libelle;
    public String categorie;
    private String uuidCategorie;
    public double price;
    private int quantite;
    private String description;
    private String utilisateur;
    private String uuidUtilisateur;
    private List<PubliciteOperationDto>  publiciteOperationDtos = new ArrayList<>();
    private List<PubliciteParamDto> publiciteParamDtos = new ArrayList<>();

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getUuidCategorie() {
        return uuidCategorie;
    }

    public void setUuidCategorie(String uuidCategorie) {
        this.uuidCategorie = uuidCategorie;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public List<PubliciteOperationDto> getPubliciteOperationDtos() {
        return publiciteOperationDtos;
    }

    public void setPubliciteOperationDtos(List<PubliciteOperationDto> publiciteOperationDtos) {
        this.publiciteOperationDtos = publiciteOperationDtos;
    }

    public List<PubliciteParamDto> getPubliciteParamDtos() {
        return publiciteParamDtos;
    }

    public void setPubliciteParamDtos(List<PubliciteParamDto> publiciteParamDtos) {
        this.publiciteParamDtos = publiciteParamDtos;
    }
}
