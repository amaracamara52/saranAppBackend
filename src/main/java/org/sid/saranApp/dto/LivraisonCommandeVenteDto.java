package org.sid.saranApp.dto;

import org.sid.saranApp.enume.EnumStatutLivraison;

import java.util.Date;

public class LivraisonCommandeVenteDto {

    private String uuid;
    private EnumStatutLivraison statutLivraison;
    private Date dateLivraison;
    private String transporteur;
    private String numeroSuivi;
    private String numeroCommande;
    private String uuidCommandeVente;
    private String client;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public EnumStatutLivraison getStatutLivraison() {
        return statutLivraison;
    }

    public void setStatutLivraison(EnumStatutLivraison statutLivraison) {
        this.statutLivraison = statutLivraison;
    }

    public Date getDateLivraison() {
        return dateLivraison;
    }

    public void setDateLivraison(Date dateLivraison) {
        this.dateLivraison = dateLivraison;
    }

    public String getTransporteur() {
        return transporteur;
    }

    public void setTransporteur(String transporteur) {
        this.transporteur = transporteur;
    }

    public String getNumeroSuivi() {
        return numeroSuivi;
    }

    public void setNumeroSuivi(String numeroSuivi) {
        this.numeroSuivi = numeroSuivi;
    }

    public String getNumeroCommande() {
        return numeroCommande;
    }

    public void setNumeroCommande(String numeroCommande) {
        this.numeroCommande = numeroCommande;
    }

    public String getUuidCommandeVente() {
        return uuidCommandeVente;
    }

    public void setUuidCommandeVente(String uuidCommandeVente) {
        this.uuidCommandeVente = uuidCommandeVente;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }
}
