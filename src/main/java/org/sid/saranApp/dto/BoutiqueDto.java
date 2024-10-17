package org.sid.saranApp.dto;

import org.sid.saranApp.enume.EnumTypeBoutique;

public class BoutiqueDto {

	private String uuid;
	private String libelleBoutique;
	private String descriptionBoutique;
	private String emailBoutique;
	private String phoneBoutique;
	private String siteBoutique;
	private String adresse;
	private EnumTypeBoutique typeBoutique;

	public String getAdresse() {
		return adresse;
	}

	public String getDescriptionBoutique() {
		return descriptionBoutique;
	}

	public String getEmailBoutique() {
		return emailBoutique;
	}

	public String getLibelleBoutique() {
		return libelleBoutique;
	}

	public String getPhoneBoutique() {
		return phoneBoutique;
	}

	public String getSiteBoutique() {
		return siteBoutique;
	}

	public EnumTypeBoutique getTypeBoutique() {
		return typeBoutique;
	}

	public String getUuid() {
		return uuid;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public void setDescriptionBoutique(String descriptionBoutique) {
		this.descriptionBoutique = descriptionBoutique;
	}

	public void setEmailBoutique(String emailBoutique) {
		this.emailBoutique = emailBoutique;
	}

	public void setLibelleBoutique(String libelleBoutique) {
		this.libelleBoutique = libelleBoutique;
	}

	public void setPhoneBoutique(String phoneBoutique) {
		this.phoneBoutique = phoneBoutique;
	}

	public void setSiteBoutique(String siteBoutique) {
		this.siteBoutique = siteBoutique;
	}

	public void setTypeBoutique(EnumTypeBoutique typeBoutique) {
		this.typeBoutique = typeBoutique;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

}
