/**
 *
 */
package org.sid.saranApp.controller;

import java.util.Date;

import org.sid.saranApp.dto.StatistiqueCommandeFournisseurDto;
import org.sid.saranApp.dto.StatistiqueCommandeVenteDto;
import org.sid.saranApp.service.StatistiqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@RestController
@CrossOrigin
public class StatistiqueController implements StatistiqueService {

	@Autowired
	private StatistiqueService statistiqueService;

	@Override
	@GetMapping("/statistique/commandeFournisseur")
	public StatistiqueCommandeFournisseurDto statistiqueCommandeFournisseurByPeriode(@RequestParam Date dateDebut,
			@RequestParam Date dateFin, @RequestParam String status) {
		// TODO Auto-generated method stub
		return statistiqueCommandeFournisseurByPeriode(dateDebut, dateFin, status);
	}

	@Override
	@GetMapping("/statistique/commandeVente")
	public StatistiqueCommandeVenteDto statistiqueCommandeVenteByPeriode(@RequestParam Date dateDebut,
			@RequestParam Date dateFin, @RequestParam String status) {
		// TODO Auto-generated method stub
		return statistiqueCommandeVenteByPeriode(dateDebut, dateFin, status);
	}

}
