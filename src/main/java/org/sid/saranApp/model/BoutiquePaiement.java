package org.sid.saranApp.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class BoutiquePaiement extends AbstractDomainClass {
	
	private String refPaiement;
	private String typePaiment;
	private Date dateDebut;
	private Date dateFin;
	private String keyToken;
	private boolean isValide;
	@ManyToOne
	private ModePaiement modePaiement;
	@ManyToOne
	private Boutique boutique;
	@ManyToOne
	private Utilisateur utilisateur;
	
	
	public Utilisateur getUtilisateur() {
		return utilisateur;
	}
	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}
	public String getRefPaiement() {
		return refPaiement;
	}
	public void setRefPaiement(String refPaiement) {
		this.refPaiement = refPaiement;
	}
	public String getTypePaiment() {
		return typePaiment;
	}
	public void setTypePaiment(String typePaiment) {
		this.typePaiment = typePaiment;
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
	public String getKeyToken() {
		return keyToken;
	}
	public void setKeyToken(String keyToken) {
		this.keyToken = keyToken;
	}
	public boolean isValide() {
		return isValide;
	}
	public void setValide(boolean isValide) {
		this.isValide = isValide;
	}
	public ModePaiement getModePaiement() {
		return modePaiement;
	}
	public void setModePaiement(ModePaiement modePaiement) {
		this.modePaiement = modePaiement;
	}
	public Boutique getBoutique() {
		return boutique;
	}
	public void setBoutique(Boutique boutique) {
		this.boutique = boutique;
	}
	
}
