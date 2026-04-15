package org.sid.saranApp.dto;

import org.sid.saranApp.enume.EnumRole;

import java.util.ArrayList;
import java.util.List;

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
	private String domaine;
	private String uuidDomain;
	private String typeDomaine;
	private String image;
	
	private String pays;
	private String monnaie;
	
	
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
	
	public String getDomaine() {
		return domaine;
	}
	public void setDomaine(String domaine) {
		this.domaine = domaine;
	}
	public String getUuidDomain() {
		return uuidDomain;
	}
	public void setUuidDomain(String uuidDomain) {
		this.uuidDomain = uuidDomain;
	}
	public String getTypeDomaine() {
		return typeDomaine;
	}
	public void setTypeDomaine(String typeDomaine) {
		this.typeDomaine = typeDomaine;
	}
	public String getPays() {
		return pays;
	}
	public void setPays(String pays) {
		this.pays = pays;
	}
	public String getMonnaie() {
		return monnaie;
	}
	public void setMonnaie(String monnaie) {
		this.monnaie = monnaie;
	}
	
	
	
	
	
	

}
