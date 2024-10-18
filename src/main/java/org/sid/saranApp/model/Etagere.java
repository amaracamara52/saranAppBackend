package org.sid.saranApp.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

@Entity
public class Etagere extends AbstractDomainClass {

	private String libelle;
	private int capacite;
	private boolean isFull;
	@OneToMany(mappedBy = "etagere", fetch = FetchType.EAGER)
	private List<EtagereRayon> etagereRayons = new ArrayList<EtagereRayon>();

	public int getCapacite() {
		return capacite;
	}

	public List<EtagereRayon> getEtagereRayons() {
		return etagereRayons;
	}

	public String getLibelle() {
		return libelle;
	}

	public boolean isFull() {
		return isFull;
	}

	public void setCapacite(int capacite) {
		this.capacite = capacite;
	}

	public void setEtagereRayons(List<EtagereRayon> etagereRayons) {
		this.etagereRayons = etagereRayons;
	}

	public void setFull(boolean isFull) {
		this.isFull = isFull;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

}
