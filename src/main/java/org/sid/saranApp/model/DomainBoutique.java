package org.sid.saranApp.model;

import javax.persistence.Entity;

@Entity
public class DomainBoutique extends AbstractDomainClass {
	
	private String libelle;
	private String code;
	
	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	
	

}
