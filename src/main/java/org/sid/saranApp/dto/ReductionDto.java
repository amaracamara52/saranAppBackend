package org.sid.saranApp.dto;

import java.util.Date;
import java.util.UUID;

public class ReductionDto {
    
    private String uuid;
    private String libelle;
    private double tauxRemise;
    private double montantRemise;
    private int quantite;
    private String categorie;
    private boolean isPay;
    private int percent;
    private boolean isActive;
    private Date dateDebut;
    private Date dateFin;
    private String boutiqueUuid;
    private String boutiqueLibelle;
    
    // Constructeurs
    public ReductionDto() {}
    
    public ReductionDto(String uuid, String libelle, double tauxRemise, double montantRemise,
                       int quantite, String categorie, boolean isPay, int percent, 
                       boolean isActive, Date dateDebut, Date dateFin, String boutiqueUuid) {
        this.uuid = uuid;
        this.libelle = libelle;
        this.tauxRemise = tauxRemise;
        this.montantRemise = montantRemise;
        this.quantite = quantite;
        this.categorie = categorie;
        this.isPay = isPay;
        this.percent = percent;
        this.isActive = isActive;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.boutiqueUuid = boutiqueUuid;
    }
    
    // Getters et Setters
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
    
    public double getTauxRemise() {
        return tauxRemise;
    }
    
    public void setTauxRemise(double tauxRemise) {
        this.tauxRemise = tauxRemise;
    }
    
    public double getMontantRemise() {
        return montantRemise;
    }
    
    public void setMontantRemise(double montantRemise) {
        this.montantRemise = montantRemise;
    }
    
    public int getQuantite() {
        return quantite;
    }
    
    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }
    
    public String getCategorie() {
        return categorie;
    }
    
    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }
    
    public boolean isPay() {
        return isPay;
    }
    
    public void setPay(boolean pay) {
        isPay = pay;
    }
    
    public int getPercent() {
        return percent;
    }
    
    public void setPercent(int percent) {
        this.percent = percent;
    }
    
    public boolean isActive() {
        return isActive;
    }
    
    public void setActive(boolean active) {
        isActive = active;
    }
    
    public Date getDateDebut() {
        return dateDebut;
    }
    
    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }
    
    public Date getDateFin() {
        return dateFin;
    }
    
    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }
    
    public String getBoutiqueUuid() {
        return boutiqueUuid;
    }
    
    public void setBoutiqueUuid(String boutiqueUuid) {
        this.boutiqueUuid = boutiqueUuid;
    }
    
    public String getBoutiqueLibelle() {
        return boutiqueLibelle;
    }
    
    public void setBoutiqueLibelle(String boutiqueLibelle) {
        this.boutiqueLibelle = boutiqueLibelle;
    }
} 