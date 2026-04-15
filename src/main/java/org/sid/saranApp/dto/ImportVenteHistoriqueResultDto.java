package org.sid.saranApp.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Résultat de l'import JSON des ventes historiques (sans mouvement de stock ni caisse).
 */
public class ImportVenteHistoriqueResultDto {

	private int ventesLues;
	private int ventesImportees;
	private List<String> commandesCreeesUuids = new ArrayList<>();
	private List<String> erreurs = new ArrayList<>();

	public int getVentesLues() {
		return ventesLues;
	}

	public void setVentesLues(int ventesLues) {
		this.ventesLues = ventesLues;
	}

	public int getVentesImportees() {
		return ventesImportees;
	}

	public void setVentesImportees(int ventesImportees) {
		this.ventesImportees = ventesImportees;
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

}
