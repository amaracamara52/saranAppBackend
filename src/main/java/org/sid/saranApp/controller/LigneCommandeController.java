package org.sid.saranApp.controller;

import java.util.List;

import org.sid.saranApp.dto.LigneCommandeDto;
import org.sid.saranApp.serviceImpl.LigneCommandeServiceImpl;
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
public class LigneCommandeController {
	
	@Autowired
	private LigneCommandeServiceImpl ligneCommandeServiceImpl;
	
	@PostMapping("/ligneCommande")
	public LigneCommandeDto add(@RequestBody LigneCommandeDto ligneCommandeDto) {
		return ligneCommandeServiceImpl.add(ligneCommandeDto);
	}
	
	@PutMapping("/ligneCommande/{uuid}")
	public LigneCommandeDto update(@RequestBody LigneCommandeDto ligneCommandeDto, @PathVariable String uuid) {
		return ligneCommandeServiceImpl.update(ligneCommandeDto, uuid);
	}
	
	@DeleteMapping("/ligneCommandeDto/delete/{uuid}")
	public LigneCommandeDto supprimer(@PathVariable String uuid) {
		return ligneCommandeServiceImpl.supprimer(uuid);
	}
	
	@GetMapping("/ligneCommande/listeCommandeVente")
	public List<LigneCommandeDto> findAll(){
		return ligneCommandeServiceImpl.findAll();
	}
	
	@GetMapping("/ligneCommande/getById/{uuid}")
	public LigneCommandeDto getById(@PathVariable String uuid) {
		return ligneCommandeServiceImpl.getById(uuid);
	}
	
	@DeleteMapping("/ligneCommande/{uuid}")
	void deleteLigneCommande(@PathVariable String uuid) {
		ligneCommandeServiceImpl.delete(uuid);
	}

}
