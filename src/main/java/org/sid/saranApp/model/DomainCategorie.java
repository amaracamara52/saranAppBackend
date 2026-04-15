package org.sid.saranApp.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class DomainCategorie extends AbstractDomainClass{
	
	private String libelle;
	@ManyToOne
	private Domain domaine;
	private String description;
	private boolean isParam;
	@OneToMany(mappedBy = "domainCategorie",fetch = FetchType.LAZY)
	private List<DomainCategorieParam> domainCategorieParams = new ArrayList<>();
	
	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	
	public Domain getDomaine() {
		return domaine;
	}
	public void setDomaine(Domain domaine) {
		this.domaine = domaine;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isParam() {
		return isParam;
	}
	public void setParam(boolean isParam) {
		this.isParam = isParam;
	}

	public List<DomainCategorieParam> getDomainCategorieParams() {
		return domainCategorieParams;
	}

	public void setDomainCategorieParams(List<DomainCategorieParam> domainCategorieParams) {
		this.domainCategorieParams = domainCategorieParams;
	}
}
