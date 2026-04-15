package org.sid.saranApp.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class DomainCategorieParam extends AbstractDomainClass {
	
	private String libelle;
	@ManyToOne
	private DomainCategorie domainCategorie;
	private String description;
	
	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	public DomainCategorie getDomainCategorie() {
		return domainCategorie;
	}
	public void setDomainCategorie(DomainCategorie domainCategorie) {
		this.domainCategorie = domainCategorie;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
