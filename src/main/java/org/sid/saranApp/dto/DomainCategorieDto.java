package org.sid.saranApp.dto;

public class DomainCategorieDto {
	
	private String uuid;
	private String libelle;
	private String description;
	private boolean isParam;
	private String uuidDomain;
	private boolean isCheck;
	
	private String libelleDomain;
	private String descriptionDomain;
	private boolean isDatePeremptionDomain;
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	public String getUuidDomain() {
		return uuidDomain;
	}
	public void setUuidDomain(String uuidDomain) {
		this.uuidDomain = uuidDomain;
	}
	public String getLibelleDomain() {
		return libelleDomain;
	}
	public void setLibelleDomain(String libelleDomain) {
		this.libelleDomain = libelleDomain;
	}
	public String getDescriptionDomain() {
		return descriptionDomain;
	}
	public void setDescriptionDomain(String descriptionDomain) {
		this.descriptionDomain = descriptionDomain;
	}
	public boolean isDatePeremptionDomain() {
		return isDatePeremptionDomain;
	}
	public void setDatePeremptionDomain(boolean isDatePeremptionDomain) {
		this.isDatePeremptionDomain = isDatePeremptionDomain;
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
	public boolean isCheck() {
		return isCheck;
	}
	public void setCheck(boolean isCheck) {
		this.isCheck = isCheck;
	}
	
	
	

}
