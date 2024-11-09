package org.sid.saranApp.dto;

import java.util.ArrayList;
import java.util.List;

import org.sid.saranApp.enume.EnumRole;
import org.sid.saranApp.enume.EnumTypeBoutique;

public class UtilisateurDto {
	
	private String uuid;
	private String username;
	private String phone;
	private String email;
	private String motDePasse;
	private String adresse;
	private List<EnumRole> role = new ArrayList<EnumRole>();
	private String boutique;
	private String libelleBoutique;
	private EnumTypeBoutique typeBoutique;
	private String image;
	
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public List<EnumRole> getRole() {
		return role;
	}
	public void setRole(List<EnumRole> role) {
		this.role = role;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMotDePasse() {
		return motDePasse;
	}
	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	public String getBoutique() {
		return boutique;
	}
	public void setBoutique(String boutique) {
		this.boutique = boutique;
	}
	public String getLibelleBoutique() {
		return libelleBoutique;
	}
	public void setLibelleBoutique(String libelleBoutique) {
		this.libelleBoutique = libelleBoutique;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public EnumTypeBoutique getTypeBoutique() {
		return typeBoutique;
	}
	public void setTypeBoutique(EnumTypeBoutique typeBoutique) {
		this.typeBoutique = typeBoutique;
	}
	
	
	
	
	

}
