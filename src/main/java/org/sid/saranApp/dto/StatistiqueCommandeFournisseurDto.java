/**
 *
 */
package org.sid.saranApp.dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class StatistiqueCommandeFournisseurDto {

	private List<CommandeFournisseurDto> commandeFournisseurDtos = new ArrayList<CommandeFournisseurDto>();
	private int montant;

	public List<CommandeFournisseurDto> getCommandeFournisseurDtos() {
		return commandeFournisseurDtos;
	}

	public int getMontant() {
		return montant;
	}

	public void setCommandeFournisseurDtos(List<CommandeFournisseurDto> commandeFournisseurDtos) {
		this.commandeFournisseurDtos = commandeFournisseurDtos;
	}

	public void setMontant(int montant) {
		this.montant = montant;
	}

}
