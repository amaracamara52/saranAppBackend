package org.sid.saranApp.dto;

/**
 * Mise à jour partielle d'un bon fournisseur (statut métier et/ou indicateur payé).
 */
public class CommandeFournisseurPatchDto {

	private String status;
	private Boolean paye;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Boolean getPaye() {
		return paye;
	}

	public void setPaye(Boolean paye) {
		this.paye = paye;
	}
}
