package org.sid.saranApp.dto;

public class StatistiqueBoutiqueDto {

	private String libelle;
	private double montant;
	private int nombre;
	
	
	
	
	public StatistiqueBoutiqueDto(String libelle, double montant, int nombre) {
		super();
		this.libelle = libelle;
		this.montant = montant;
		this.nombre = nombre;
	}
	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	public double getMontant() {
		return montant;
	}
	public void setMontant(double montant) {
		this.montant = montant;
	}
	public int getNombre() {
		return nombre;
	}
	public void setNombre(int nombre) {
		this.nombre = nombre;
	}
	
	
}
