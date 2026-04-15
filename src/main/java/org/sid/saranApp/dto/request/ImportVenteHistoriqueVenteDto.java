package org.sid.saranApp.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.sid.saranApp.enume.EnumTypeCommande;
import org.sid.saranApp.enume.StatusCommandeVenteEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * Une vente historique à importer (sans impact stock ni caisse).
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImportVenteHistoriqueVenteDto {

	private String id_client;
	private double montantCommande;
	/** Alias JSON possible : paye */
	private boolean paye;
	private StatusCommandeVenteEnum status;
	private EnumTypeCommande typeCommande;
	/** Ex. {@code yyyy-MM-dd} ou début ISO-8601 */
	private String dateVente;
	private String uuidModePaiement;
	private List<ImportVenteHistoriqueLigneDto> ligneCommandeDtos = new ArrayList<>();

	public String getId_client() {
		return id_client;
	}

	public void setId_client(String id_client) {
		this.id_client = id_client;
	}

	public double getMontantCommande() {
		return montantCommande;
	}

	public void setMontantCommande(double montantCommande) {
		this.montantCommande = montantCommande;
	}

	public boolean isPaye() {
		return paye;
	}

	public void setPaye(boolean paye) {
		this.paye = paye;
	}

	public StatusCommandeVenteEnum getStatus() {
		return status;
	}

	public void setStatus(StatusCommandeVenteEnum status) {
		this.status = status;
	}

	public EnumTypeCommande getTypeCommande() {
		return typeCommande;
	}

	public void setTypeCommande(EnumTypeCommande typeCommande) {
		this.typeCommande = typeCommande;
	}

	public String getDateVente() {
		return dateVente;
	}

	public void setDateVente(String dateVente) {
		this.dateVente = dateVente;
	}

	public String getUuidModePaiement() {
		return uuidModePaiement;
	}

	public void setUuidModePaiement(String uuidModePaiement) {
		this.uuidModePaiement = uuidModePaiement;
	}

	public List<ImportVenteHistoriqueLigneDto> getLigneCommandeDtos() {
		return ligneCommandeDtos;
	}

	public void setLigneCommandeDtos(List<ImportVenteHistoriqueLigneDto> ligneCommandeDtos) {
		this.ligneCommandeDtos = ligneCommandeDtos;
	}
}
