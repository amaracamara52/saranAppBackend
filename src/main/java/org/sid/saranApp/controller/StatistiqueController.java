/**
 *
 */
package org.sid.saranApp.controller;

import java.util.Date;
import java.util.List;

import org.sid.saranApp.dto.StatistiqueBoutiqueDto;
import org.sid.saranApp.dto.StatistiqueCommandeFournisseurDto;
import org.sid.saranApp.dto.StatistiqueCommandeVenteDto;
import org.sid.saranApp.dto.TopVenteDTO;
import org.sid.saranApp.externeSql.ProduitExterneServiceImpl;
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
public class StatistiqueController  {

	@Autowired
	private ProduitExterneServiceImpl produitServiceImplExterne;

	
	@GetMapping("/produit/topVenteProduit")
	public List<TopVenteDTO> topVenteProduits() {
		return produitServiceImplExterne.topVenteProduit(10);
	}
	

	
	@GetMapping("/produit/reste_a_recouvrer")
	public StatistiqueBoutiqueDto getResteAReouvrer() {
		return produitServiceImplExterne.getStockResteARecouvrer();
	}
	
	@GetMapping("/produit/stock_a_recouvrer")
	public StatistiqueBoutiqueDto getStockARecouvrer() {
		return produitServiceImplExterne.getStockARecouvrer();
	}
	
	
	@GetMapping("/produit/commande_vendu_aujourdhui")
	public StatistiqueBoutiqueDto getCommandeVenduAujourdhui() {
		return produitServiceImplExterne.getCommandeVenduAujourdhui();
	}
	
	@GetMapping("/produit/totalProduitVendu")
	public StatistiqueBoutiqueDto getTotaProduitVendu() {
		return produitServiceImplExterne.getTotaProduitVendu();
	}
	
	@GetMapping("/produit/situationVenteParMois")
	public List<StatistiqueBoutiqueDto> situationVenteParMois() {
		return produitServiceImplExterne.getSituationVenteDesMois();
	}
	
	

}
