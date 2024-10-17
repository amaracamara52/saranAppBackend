package org.sid.saranApp.controller;

import java.util.List;

import org.sid.saranApp.dto.CommandeVenteDto;
import org.sid.saranApp.serviceImpl.CommandeVenteServiceImpl;
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
public class CommandeVenteController {
	
	@Autowired
	private CommandeVenteServiceImpl commandeVenteServiceImpl;
	
	@PostMapping("/commandeVente")
	public CommandeVenteDto add(@RequestBody CommandeVenteDto commandeVenteDto) {
		return commandeVenteServiceImpl.add(commandeVenteDto);
	}
	
	@PutMapping("/commandeVente/{uuid}")
	public CommandeVenteDto update(@RequestBody CommandeVenteDto commandeVenteDto,@PathVariable String uuid) {
		return commandeVenteServiceImpl.update(commandeVenteDto, uuid);
	}
	
	@DeleteMapping("/commandeVente/delete/{uuid}")
	public CommandeVenteDto supprimer(@PathVariable String uuid) {
		return commandeVenteServiceImpl.supprimer(uuid);
	}
	
	@GetMapping("/commandeVenteDto/listeCommandeVente")
	public List<CommandeVenteDto> findAll(){
		return commandeVenteServiceImpl.findAll();
	}
	
	@GetMapping("/commandeVenteDto/getById/{uuid}")
	public CommandeVenteDto getById(@PathVariable String uuid) {
		return commandeVenteServiceImpl.getById(uuid);
	}

}
