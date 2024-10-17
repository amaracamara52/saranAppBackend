package org.sid.saranApp.controller;

import java.util.List;

import org.sid.saranApp.dto.LivraisonCommandeFournisseurDto;
import org.sid.saranApp.service.LivraisonCommandeFournisseurService;
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
