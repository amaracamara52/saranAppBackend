package org.sid.saranApp.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Résultat de l'import Excel (bon de commande fournisseur + articles + unités de vente + stock).
 */
public class ImportBonCommandeProduitResultDto {

	private int lignesLues;
	private int lignesImportees;
	private int bonsCrees;
	private List<String> commandesCreeesUuids = new ArrayList<>();
	private List<String> erreurs = new ArrayList<>();
	private List<String> avertissements = new ArrayList<>();

	public int getLignesLues() {
		return lignesLues;
	}

	public void setLignesLues(int lignesLues) {
		this.lignesLues = lignesLues;
	}

	public int getLignesImportees() {
		return lignesImportees;
	}

	public void setLignesImportees(int lignesImportees) {
		this.lignesImportees = lignesImportees;
	}

	public int getBonsCrees() {
		return bonsCrees;
	}

	public void setBonsCrees(int bonsCrees) {
		this.bonsCrees = bonsCrees;
	}

	public List<String> getCommandesCreeesUuids() {
		return commandesCreeesUuids;
	}

	public void setCommandesCreeesUuids(List<String> commandesCreeesUuids) {
		this.commandesCreeesUuids = commandesCreeesUuids;
	}

	public List<String> getErreurs() {
		return erreurs;
	}

	public void setErreurs(List<String> erreurs) {
		this.erreurs = erreurs;
	}

	public List<String> getAvertissements() {
		return avertissements;
	}

	public void setAvertissements(List<String> avertissements) {
		this.avertissements = avertissements;
	}
}
