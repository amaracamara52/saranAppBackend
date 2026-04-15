/**
 *
 */
package org.sid.saranApp.service;

import org.sid.saranApp.dto.StatistiqueCommandeFournisseurDto;
import org.sid.saranApp.dto.StatistiqueCommandeVenteDto;

import java.util.Date;

/**
 *
 */
public interface StatistiqueService {

	public StatistiqueCommandeFournisseurDto statistiqueCommandeFournisseurByPeriode(Date dateDebut, Date dateFin,
			String status);

	public StatistiqueCommandeVenteDto statistiqueCommandeVenteByPeriode(Date dateDebut, Date dateFin, String status);

}
