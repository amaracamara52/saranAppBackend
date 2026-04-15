package org.sid.saranApp.dto.request;

import org.sid.saranApp.dto.LigneCommandeDto;
import org.sid.saranApp.dto.LivraisonCommandeVenteDto;
import org.sid.saranApp.enume.EnumTypeCommande;
import org.sid.saranApp.enume.StatusCommandeVenteEnum;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CommandeVenteRequestDto {

    private double montantCommande;
    private boolean isPaye;
    @Temporal(TemporalType.DATE)
    private String id_client;
    @Enumerated(EnumType.STRING)
    private EnumTypeCommande typeCommande;
    @Enumerated(EnumType.STRING)
    private StatusCommandeVenteEnum status;
    private int nombreArticle;
    List<LigneRequestDto> ligneCommandeDtos = new ArrayList<LigneRequestDto>();
    private LivraisonCommandeVenteDto livraisonCommandeVenteDto;

    private String uuidModePaiement;


    public String getId_client() {
        return id_client;
    }


    public int getNombreArticle() {
        return nombreArticle;
    }


    public StatusCommandeVenteEnum getStatus() {
        return status;
    }



    public boolean isPaye() {
        return isPaye;
    }


    public void setId_client(String id_client) {
        this.id_client = id_client;
    }




    public void setNombreArticle(int nombreArticle) {
        this.nombreArticle = nombreArticle;
    }


    public void setPaye(boolean isPaye) {
        this.isPaye = isPaye;
    }

    public void setStatus(StatusCommandeVenteEnum status) {
        this.status = status;
    }



    public double getMontantCommande() {
        return montantCommande;
    }

    public void setMontantCommande(double montantCommande) {
        this.montantCommande = montantCommande;
    }

    public List<LigneRequestDto> getLigneCommandeDtos() {
        return ligneCommandeDtos;
    }

    public void setLigneCommandeDtos(List<LigneRequestDto> ligneCommandeDtos) {
        this.ligneCommandeDtos = ligneCommandeDtos;
    }

    public EnumTypeCommande getTypeCommande() {
        return typeCommande;
    }

    public void setTypeCommande(EnumTypeCommande typeCommande) {
        this.typeCommande = typeCommande;
    }

    public LivraisonCommandeVenteDto getLivraisonCommandeVenteDto() {
        return livraisonCommandeVenteDto;
    }

    public void setLivraisonCommandeVenteDto(LivraisonCommandeVenteDto livraisonCommandeVenteDto) {
        this.livraisonCommandeVenteDto = livraisonCommandeVenteDto;
    }

    public String getUuidModePaiement() {
        return uuidModePaiement;
    }

    public void setUuidModePaiement(String uuidModePaiement) {
        this.uuidModePaiement = uuidModePaiement;
    }

}
