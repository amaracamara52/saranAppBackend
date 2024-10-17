package org.sid.saranApp.dto;

import java.util.ArrayList;
import java.util.List;

import org.sid.saranApp.enume.EnumRole;

public class UtilisateurDto {
	
	private String uuid;
	private String username;
	private String phone;
	private String email;
	private String motDePasse;
	private String adresse;
	private List<EnumRole> role = new ArrayList<EnumRole>();
	private String boutique;
	
	
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
	
	
	

}
