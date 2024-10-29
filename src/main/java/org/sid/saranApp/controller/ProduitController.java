package org.sid.saranApp.controller;

import java.util.List;

import org.sid.saranApp.dto.ProduitDto;
import org.sid.saranApp.serviceImpl.ProduitServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class ProduitController {

	@Autowired
	private ProduitServiceImpl produitServiceImpl;

	@PostMapping("/produit")
	public ProduitDto add(@RequestBody ProduitDto produitDto) {
		return produitServiceImpl.add(produitDto);
	}

	@GetMapping("/produit")
	public List<ProduitDto> findAll() {
		return produitServiceImpl.findAll();
	}

	@GetMapping("/produit/getById/{uuid}")
	public ProduitDto getById(@PathVariable String uuid) {
		return produitServiceImpl.getById(uuid);
	}

	@DeleteMapping("/produit/delete/{uuid}")
	public ProduitDto supprimer(@PathVariable String uuid) {
		return produitServiceImpl.supprimer(uuid);
	}

	@PutMapping("/produit/{uuid}")
	public ProduitDto update(@RequestBody ProduitDto produitDto, @PathVariable String uuid) {
		return produitServiceImpl.update(produitDto, uuid);
	}
	
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


}
