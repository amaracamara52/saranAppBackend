package org.sid.saranApp.controller;

import java.util.List;

import org.sid.saranApp.dto.FournisseurDto;
import org.sid.saranApp.service.FournisseurService;
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
public class FournisseurController { 
	
	@Autowired 
	private FournisseurService fournisseurService;
	
	@PostMapping("/fournisseur")
	public FournisseurDto addFournisseur(@RequestBody FournisseurDto fournisseurDto ) {
		return fournisseurService.addFournisseur(fournisseurDto);
	}
	@PutMapping("/fournisseur/{uuid}")
	public FournisseurDto updateFournisseur(@RequestBody FournisseurDto fournisseurDto, @PathVariable String uuid) {
		return fournisseurService.updateFournisseur(fournisseurDto, uuid);
	}
	@GetMapping("/fournisseur")
	public List<FournisseurDto> findAll(){
		return fournisseurService.findAll();
	}
	@GetMapping("/fournisseur/{uuid}")
	public FournisseurDto getFournisseur(@RequestBody FournisseurDto fournisseurDto, @PathVariable String uuid) {
		return fournisseurService.getFournisseur(uuid);
	}
	@DeleteMapping("/fournisseur/{uuid}")
	public void deleteFournisseur(@PathVariable String uuid) {
		fournisseurService.deleteFournisseur(uuid);
	}
	
		
}
	
	


