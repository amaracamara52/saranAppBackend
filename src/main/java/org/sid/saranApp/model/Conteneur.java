package org.sid.saranApp.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Date;

/**
 * Entité pour gérer les conteneurs de livraison
 * Un conteneur peut contenir plusieurs produits d'une livraison fournisseur
 */
@Entity
public class Conteneur extends AbstractDomainClass {

    /**
     * Numéro du conteneur (ex: CONT-2024-001)
     */
    private String numeroConteneur;
    
    /**
     * Type de conteneur (20 pieds, 40 pieds, etc.)
     */
    private String typeConteneur;
    
    /**
     * Date d'enregistrement du conteneur
     */
    private Date dateEnregistrement;
    
    /**
     * Date d'arrivée du conteneur au port
     */
    private Date dateArriveePort;
    
    /**
     * Date de dédouanement
     */
    private Date dateDedouanement;
    
    /**
     * Statut du conteneur (EN_TRANSIT, AU_PORT, DEDOUANE, LIVRE)
     */
    private String statut;
    
    /**
     * Livraison fournisseur associée à ce conteneur
     */
    @ManyToOne
    private LivraisonCommandeFournisseur livraisonCommandeFournisseur;
    
    /**
     * Boutique propriétaire du conteneur
     */
    @ManyToOne
    private Boutique boutique;
    
    /**
     * Utilisateur qui a enregistré le conteneur
     */
    @ManyToOne
    private Utilisateur utilisateur;

    public String getNumeroConteneur() {
        return numeroConteneur;
    }

    public void setNumeroConteneur(String numeroConteneur) {
        this.numeroConteneur = numeroConteneur;
    }

    public String getTypeConteneur() {
        return typeConteneur;
    }

    public void setTypeConteneur(String typeConteneur) {
        this.typeConteneur = typeConteneur;
    }

    public Date getDateEnregistrement() {
        return dateEnregistrement;
    }

    public void setDateEnregistrement(Date dateEnregistrement) {
        this.dateEnregistrement = dateEnregistrement;
    }

    public Date getDateArriveePort() {
        return dateArriveePort;
    }

    public void setDateArriveePort(Date dateArriveePort) {
        this.dateArriveePort = dateArriveePort;
    }

    public Date getDateDedouanement() {
        return dateDedouanement;
    }

    public void setDateDedouanement(Date dateDedouanement) {
        this.dateDedouanement = dateDedouanement;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public LivraisonCommandeFournisseur getLivraisonCommandeFournisseur() {
        return livraisonCommandeFournisseur;
    }

    public void setLivraisonCommandeFournisseur(LivraisonCommandeFournisseur livraisonCommandeFournisseur) {
        this.livraisonCommandeFournisseur = livraisonCommandeFournisseur;
    }

    public Boutique getBoutique() {
        return boutique;
    }

    public void setBoutique(Boutique boutique) {
        this.boutique = boutique;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }
}
