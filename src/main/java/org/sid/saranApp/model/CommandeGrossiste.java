package org.sid.saranApp.model;

import org.sid.saranApp.enume.StatusCommandeGrossisteEnum;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Entité pour gérer les commandes passées par les détaillants aux grossistes
 */
@Entity
public class CommandeGrossiste extends AbstractDomainClass {

    /**
     * Numéro de référence de la commande
     */
    private String numeroCommande;
    
    /**
     * Date de la commande
     */
    private LocalDate dateCommande;
    
    /**
     * Montant total de la commande
     */
    private double montantTotal;
    
    /**
     * Statut de la commande
     */
    @Enumerated(EnumType.STRING)
    private StatusCommandeGrossisteEnum statut;
    
    /**
     * Boutique détaillante qui passe la commande
     */
    @ManyToOne
    private Boutique boutiqueDetaillee; // Détaillant
    
    /**
     * Boutique grossiste qui reçoit la commande
     */
    @ManyToOne
    private Boutique boutiqueGrossiste; // Grossiste
    
    /**
     * Client partenaire qui passe la commande (si c'est un client partenaire)
     * Si null, c'est une commande d'un détaillant normal
     */
    @ManyToOne
    private ClientPartenaire clientPartenaire;
    
    /**
     * Utilisateur détaillant qui passe la commande
     */
    @ManyToOne
    private Utilisateur utilisateurDetaillee;
    
    /**
     * Utilisateur grossiste qui valide la commande (optionnel)
     */
    @ManyToOne
    private Utilisateur utilisateurGrossiste;
    
    /**
     * Date de validation de la commande
     */
    private LocalDate dateValidation;
    
    /**
     * Détails de la commande
     */
    @OneToMany(mappedBy = "commandeGrossiste", cascade = CascadeType.ALL)
    private List<DetailCommandeGrossiste> detailsCommande = new ArrayList<>();
    
    /**
     * Livraisons associées à cette commande
     */
    @OneToMany(mappedBy = "commandeGrossiste")
    private List<LivraisonCommandeGrossiste> livraisons = new ArrayList<>();

    public String getNumeroCommande() {
        return numeroCommande;
    }

    public void setNumeroCommande(String numeroCommande) {
        this.numeroCommande = numeroCommande;
    }

    public LocalDate getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(LocalDate dateCommande) {
        this.dateCommande = dateCommande;
    }

    public double getMontantTotal() {
        return montantTotal;
    }

    public void setMontantTotal(double montantTotal) {
        this.montantTotal = montantTotal;
    }

    public StatusCommandeGrossisteEnum getStatut() {
        return statut;
    }

    public void setStatut(StatusCommandeGrossisteEnum statut) {
        this.statut = statut;
    }

    public Boutique getBoutiqueDetaillee() {
        return boutiqueDetaillee;
    }

    public void setBoutiqueDetaillee(Boutique boutiqueDetaillee) {
        this.boutiqueDetaillee = boutiqueDetaillee;
    }

    public Boutique getBoutiqueGrossiste() {
        return boutiqueGrossiste;
    }

    public void setBoutiqueGrossiste(Boutique boutiqueGrossiste) {
        this.boutiqueGrossiste = boutiqueGrossiste;
    }

    public Utilisateur getUtilisateurDetaillee() {
        return utilisateurDetaillee;
    }

    public void setUtilisateurDetaillee(Utilisateur utilisateurDetaillee) {
        this.utilisateurDetaillee = utilisateurDetaillee;
    }

    public Utilisateur getUtilisateurGrossiste() {
        return utilisateurGrossiste;
    }

    public void setUtilisateurGrossiste(Utilisateur utilisateurGrossiste) {
        this.utilisateurGrossiste = utilisateurGrossiste;
    }

    public LocalDate getDateValidation() {
        return dateValidation;
    }

    public void setDateValidation(LocalDate dateValidation) {
        this.dateValidation = dateValidation;
    }

    public List<DetailCommandeGrossiste> getDetailsCommande() {
        return detailsCommande;
    }

    public void setDetailsCommande(List<DetailCommandeGrossiste> detailsCommande) {
        this.detailsCommande = detailsCommande;
    }

    public List<LivraisonCommandeGrossiste> getLivraisons() {
        return livraisons;
    }

    public void setLivraisons(List<LivraisonCommandeGrossiste> livraisons) {
        this.livraisons = livraisons;
    }

    public ClientPartenaire getClientPartenaire() {
        return clientPartenaire;
    }

    public void setClientPartenaire(ClientPartenaire clientPartenaire) {
        this.clientPartenaire = clientPartenaire;
    }
}
