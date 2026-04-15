package org.sid.saranApp.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Date;

/**
 * Entité pour gérer les partenariats entre clients et boutiques
 * Un client partenaire peut passer des commandes en gros auprès d'une boutique
 */
@Entity
public class ClientPartenaire extends AbstractDomainClass {

    /**
     * Client partenaire
     */
    @ManyToOne
    private Client client;
    
    /**
     * Boutique partenaire (grossiste)
     */
    @ManyToOne
    private Boutique boutique;
    
    /**
     * Date de création du partenariat
     */
    private Date dateCreation;
    
    /**
     * Date d'expiration du partenariat (optionnel)
     */
    private Date dateExpiration;
    
    /**
     * Statut du partenariat (ACTIF, SUSPENDU, EXPIRE)
     */
    private String statut;
    
    /**
     * Numéro de compte partenaire
     */
    private String numeroCompte;
    
    /**
     * Conditions spéciales du partenariat (remises, crédit, etc.)
     */
    private String conditionsSpeciales;
    
    /**
     * Limite de crédit pour ce partenaire (optionnel)
     */
    private Double limiteCredit;
    
	/**
	 * Utilisateur qui a créé le partenariat
	 */
	@ManyToOne
	private Utilisateur utilisateur;
	
	/**
	 * Liste des versements effectués par ce client partenaire
	 */
	@OneToMany(mappedBy = "clientPartenaire")
	private java.util.List<VersementClientPartenaire> versements = new java.util.ArrayList<>();
	
	/**
	 * Pays de provenance du client partenaire
	 */
	@ManyToOne
	private Pays paysProvenance;

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Boutique getBoutique() {
        return boutique;
    }

    public void setBoutique(Boutique boutique) {
        this.boutique = boutique;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Date getDateExpiration() {
        return dateExpiration;
    }

    public void setDateExpiration(Date dateExpiration) {
        this.dateExpiration = dateExpiration;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public String getNumeroCompte() {
        return numeroCompte;
    }

    public void setNumeroCompte(String numeroCompte) {
        this.numeroCompte = numeroCompte;
    }

    public String getConditionsSpeciales() {
        return conditionsSpeciales;
    }

    public void setConditionsSpeciales(String conditionsSpeciales) {
        this.conditionsSpeciales = conditionsSpeciales;
    }

    public Double getLimiteCredit() {
        return limiteCredit;
    }

    public void setLimiteCredit(Double limiteCredit) {
        this.limiteCredit = limiteCredit;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}

	public java.util.List<VersementClientPartenaire> getVersements() {
		return versements;
	}

	public void setVersements(java.util.List<VersementClientPartenaire> versements) {
		this.versements = versements;
	}

	public Pays getPaysProvenance() {
		return paysProvenance;
	}

	public void setPaysProvenance(Pays paysProvenance) {
		this.paysProvenance = paysProvenance;
	}
}
