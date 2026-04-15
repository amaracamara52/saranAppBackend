package org.sid.saranApp.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;


@Entity
public class Domain extends AbstractDomainClass {

	private String libelle;
	private String description;
	private String type;
	private boolean isDatePeremption;
	@OneToMany(mappedBy = "domaine",fetch = FetchType.LAZY)
	private List<DomainCategorie> domainCategories = new ArrayList<>();

	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isDatePeremption() {
		return isDatePeremption;
	}
	public void setDatePeremption(boolean isDatePeremption) {
		this.isDatePeremption = isDatePeremption;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public List<DomainCategorie> getDomainCategories() {
		return domainCategories;
	}

	public void setDomainCategories(List<DomainCategorie> domainCategories) {
		this.domainCategories = domainCategories;
	}
}
