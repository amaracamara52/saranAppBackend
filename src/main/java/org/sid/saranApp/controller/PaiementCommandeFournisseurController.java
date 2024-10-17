package org.sid.saranApp.controller;

import java.util.List;

import org.sid.saranApp.dto.PaiementCommandeFournisseurDto;
import org.sid.saranApp.service.PaiementCommandeFournisseurService;
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
public class PaiementCommandeFournisseurController {
	
	@Autowired
	private PaiementCommandeFournisseurService paiementCommandeFournisseurService;
	
	@PostMapping("/paiementCommandeFournisseur")
	public PaiementCommandeFournisseurDto addPaiementCommandeFournisseur(@RequestBody PaiementCommandeFournisseurDto paiementCommandeFournisseurDto) {
		return paiementCommandeFournisseurService.addPaiementCommandeFournisseur(paiementCommandeFournisseurDto);
	}
	@PutMapping("/paiementCommandeFournisseur/{uuid}")
	public PaiementCommandeFournisseurDto updatePaiementCommandeFournisseur(@RequestBody PaiementCommandeFournisseurDto paiementCommandeFournisseurDto, @PathVariable String uuid) {
		return paiementCommandeFournisseurService.updatePaiementCommandeFournisseur(paiementCommandeFournisseurDto, uuid);
	}
	@GetMapping("/paiementCommandeFournisseur")
	List<PaiementCommandeFournisseurDto> findAll(){
		return paiementCommandeFournisseurService.findAll();
	}
	@GetMapping("/paiementCommandeFournisseur/{uuid}")
	public PaiementCommandeFournisseurDto getPaiementCommandeFournisseur(@PathVariable String uuid) {
		return paiementCommandeFournisseurService.getPaiementCommandeFournisseur(uuid);
	}
	@DeleteMapping("/paiementCommandeFournisseur/{uuid}")
	public void deletePaiementCommandeFournisseur(@PathVariable String uuid) {
		paiementCommandeFournisseurService.deletePaiementCommandeFournisseur(uuid);
	}

	
	
	
	
	
	
	
	
}
