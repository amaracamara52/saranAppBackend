package org.sid.saranApp.controller;

import org.sid.saranApp.dto.LigneCommandeDto;
import org.sid.saranApp.serviceImpl.LigneCommandeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
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
