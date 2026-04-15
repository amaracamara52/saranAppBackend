package org.sid.saranApp.dto.version;

import java.util.ArrayList;
import java.util.List;

/**
 * Résultat de l’import JSON catalogue (plusieurs {@link ProduitStockDto}).
 */
public class ImportProduitJsonResultDto {

	private int lignesRecues;
	private int lignesImportees;
	private int lignesIgnorees;
	private final List<String> avertissements = new ArrayList<>();
	private final List<String> erreurs = new ArrayList<>();
	/** UUID des commandes fournisseur créées (si {@code uuidFournisseurCommande} passé à l’import). */
	private final List<String> commandesCreeesUuids = new ArrayList<>();

	public int getLignesRecues() {
		return lignesRecues;
	}

	public void setLignesRecues(int lignesRecues) {
		this.lignesRecues = lignesRecues;
	}

	public int getLignesImportees() {
		return lignesImportees;
	}

	public void setLignesImportees(int lignesImportees) {
		this.lignesImportees = lignesImportees;
	}

	public int getLignesIgnorees() {
		return lignesIgnorees;
	}

	public void setLignesIgnorees(int lignesIgnorees) {
		this.lignesIgnorees = lignesIgnorees;
	}

	public List<String> getAvertissements() {
		return avertissements;
	}

	public List<String> getErreurs() {
		return erreurs;
	}

	public List<String> getCommandesCreeesUuids() {
		return commandesCreeesUuids;
	}
}
