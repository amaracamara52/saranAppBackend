package org.sid.saranApp.controller;

import org.sid.saranApp.dto.LivraisonCommandeFournisseurDto;
import org.sid.saranApp.service.LivraisonCommandeFournisseurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class LivraisonCommandeFournisseurController {
	
	@Autowired
	private LivraisonCommandeFournisseurService livraisonCommandeFournisseurService;
	
	@PostMapping("/livraisonCommandeFournisseur")
	public LivraisonCommandeFournisseurDto addLivraisonCommandeFournisseur(@RequestBody LivraisonCommandeFournisseurDto livraisonCommandeFournisseurDto) {
		return livraisonCommandeFournisseurService.addLivraisonCommandeFournisseur(livraisonCommandeFournisseurDto);
	}
	@PutMapping("/livraisonCommandeFournisseur/{uuid}")
	public LivraisonCommandeFournisseurDto updateLivraisonCommandeFournisseur(@RequestBody LivraisonCommandeFournisseurDto livraisonCommandeFournisseurDto, @PathVariable String uuid) {
		return  livraisonCommandeFournisseurService.updateLivraisonCommandeFournisseur(livraisonCommandeFournisseurDto, uuid);
	}
	@GetMapping("/livraisonCommandeFournisseur")
	public List<LivraisonCommandeFournisseurDto> findAll(){
		return livraisonCommandeFournisseurService.findAll();
	}
	@GetMapping("/livraisonCommandeFournisseur/{uuid}")
	public LivraisonCommandeFournisseurDto getLivraisonCommandeFournisseur(@PathVariable String uuid) {
		return livraisonCommandeFournisseurService.getLivraisonCommandeFournisseur(uuid);
	}
	@DeleteMapping("/livraisonCommandeFournisseur/{uuid}")
	public void deleteLivraisonCommandeFournisseur(@PathVariable String uuid) {
		livraisonCommandeFournisseurService.deleteLivraisonCommandeFournisseur(uuid);
		
	}

	
	
	
	
	
	
	
	
}
