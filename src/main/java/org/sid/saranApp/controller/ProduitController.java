package org.sid.saranApp.controller;

import org.sid.saranApp.dto.PageDataDto;
import org.sid.saranApp.dto.ProduitDto;
import org.sid.saranApp.dto.version.ProduitStockDto;
import org.sid.saranApp.serviceImpl.ProduitServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class ProduitController {

	@Autowired
	private ProduitServiceImpl produitServiceImpl;
	

//	@PostMapping("/produit")
//	public ProduitDto add(@RequestBody ProduitDto produitDto) {
//		return produitServiceImpl.add(produitDto);
//	}

	@GetMapping("/produit")
	public List<ProduitStockDto> findAll() {
		return produitServiceImpl.findAll();
	}

//	@GetMapping("/produit/getById/{uuid}")
//	public ProduitDto getById(@PathVariable String uuid) {
//		return produitServiceImpl.getById(uuid);
//	}
//
//	@DeleteMapping("/produit/delete/{uuid}")
//	public ProduitDto supprimer(@PathVariable String uuid) {
//		return produitServiceImpl.supprimer(uuid);
//	}
//
//	@PutMapping("/produit/{uuid}")
//	public ProduitDto update(@RequestBody ProduitDto produitDto, @PathVariable String uuid) {
//		return produitServiceImpl.update(produitDto, uuid);
//	}
	
	@GetMapping("/produit/perime")
	public List<ProduitDto> findAllPerime() {
		return produitServiceImpl.listeStockPerime();
	}
	
	
	
	@GetMapping("/produit/inferieurA5")
	public List<ProduitDto> findAllInferieurA5() {
		return produitServiceImpl.listeStockInferieurA5();
	}
	
	@GetMapping("/produit/perimeDans3mois")
	public List<ProduitDto> findAllPerimeDans3mois() {
		return produitServiceImpl.listeStockPerimeDans3mois();
	}
	
	@GetMapping("/produit/vente")
	public List<ProduitDto> listeProduitAVendre() {
		return produitServiceImpl.listeProduitAVendre();
	}
	
	@GetMapping("/produit/page_produit")
    public PageDataDto<ProduitDto> getProduits(
        @RequestParam(required = false) String key,
        @RequestParam(required = true,defaultValue = "0") int page,
        @RequestParam(required = true,defaultValue = "10") int size
    ) {
        return produitServiceImpl.listeProduits(page, size, key);
    }
	
	
	


}
