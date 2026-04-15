package org.sid.saranApp.dto;

public class DomainDto {
	
	private String uuid;
	private String libelle;
	private String description;
	private boolean isDatePeremption;
	private String type;
	
	
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
	
	
	

}
