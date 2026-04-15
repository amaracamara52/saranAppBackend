package org.sid.saranApp.model;

import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Pays extends AbstractDomainClass {

//	@ManyToOne
//	private Boutique boutique;

	private String libelle;
	private String monnaie;
	private String capitale;
	@Column(unique = true)
	private String countryCode;

	
	
//	public Boutique getBoutiquePrincipale() {
//		return boutique;
//	}
//
//	public void setBoutique(Boutique boutique) {
//		this.boutique = boutique;
//	}



	

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}



	public String getMonnaie() {
		return monnaie;
	}

	public void setMonnaie(String monnaie) {
		this.monnaie = monnaie;
	}

	public String getCapitale() {
		return capitale;
	}

	public void setCapitale(String capitale) {
		this.capitale = capitale;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
}
