package org.sid.saranApp.dto;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.sid.saranApp.enume.EnumTypeBoutique;

public class BoutiqueDto {

	private String uuid;
	private String libelleBoutique;
	private String descriptionBoutique;
	private String emailBoutique;
	private String phoneBoutique;
	private String siteBoutique;
	private String adresse;
	@Enumerated(EnumType.STRING)
	private EnumTypeBoutique typeBoutique;

	private String  image;
	private String utilisateur;
	private String email;
	private String password;
	private String uuidStoreFile;
	
	private List<String> domaines = new ArrayList<String>();
	
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(String utilisateur) {
		this.utilisateur = utilisateur;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUuidStoreFile() {
		return uuidStoreFile;
	}

	public void setUuidStoreFile(String uuidStoreFile) {
		this.uuidStoreFile = uuidStoreFile;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<String> getDomaines() {
		return domaines;
	}

	public void setDomaines(List<String> domaines) {
		this.domaines = domaines;
	}
	
	
	
	
	
	
	

}
