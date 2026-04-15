package org.sid.saranApp.dto;

import java.util.ArrayList;
import java.util.List;

public class DomainBoutiqueDto {
	
	private String uuid;
	private String domaine;
	private String boutique;
	private String uuidBoutique;
	private String uuidDomain;
	private List<DomainCategorieDto> domainCategorieDtos = new ArrayList<>();

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getDomaine() {
		return domaine;
	}

	public void setDomaine(String domaine) {
		this.domaine = domaine;
	}

	public String getBoutique() {
		return boutique;
	}

	public void setBoutique(String boutique) {
		this.boutique = boutique;
	}

	public List<DomainCategorieDto> getDomainCategorieDtos() {
		return domainCategorieDtos;
	}

	public void setDomainCategorieDtos(List<DomainCategorieDto> domainCategorieDtos) {
		this.domainCategorieDtos = domainCategorieDtos;
	}

	public String getUuidBoutique() {
		return uuidBoutique;
	}

	public void setUuidBoutique(String uuidBoutique) {
		this.uuidBoutique = uuidBoutique;
	}

	public String getUuidDomain() {
		return uuidDomain;
	}

	public void setUuidDomain(String uuidDomain) {
		this.uuidDomain = uuidDomain;
	}
}
