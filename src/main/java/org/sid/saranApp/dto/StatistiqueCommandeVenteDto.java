/**
 *
 */
package org.sid.saranApp.dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class StatistiqueCommandeVenteDto {

	private List<CommandeVenteDto> commandeVenteDtos = new ArrayList<CommandeVenteDto>();
	private int montant;

	public List<CommandeVenteDto> getCommandeVenteDtos() {
		return commandeVenteDtos;
	}

	public int getMontant() {
		return montant;
	}

	public void setCommandeVenteDtos(List<CommandeVenteDto> commandeVenteDtos) {
		this.commandeVenteDtos = commandeVenteDtos;
	}

	public void setMontant(int montant) {
		this.montant = montant;
	}

}
