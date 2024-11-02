package org.sid.saranApp.controller;

import java.util.List;

import org.sid.saranApp.dto.CaracteristiqueProduitDto;
import org.sid.saranApp.serviceImpl.CaracteristiqueProduitServiceImpl;
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
public class CaracteristiqueProduitController {
	
	@Autowired
	private CaracteristiqueProduitServiceImpl caracteristiqueProduitServiceImpl;
	
	@PostMapping("/caracteristiqueProduit")
	public CaracteristiqueProduitDto add(@RequestBody CaracteristiqueProduitDto caracteristiqueProduitDto) {
		return caracteristiqueProduitServiceImpl.add(caracteristiqueProduitDto);
	}
	
	@PutMapping("/caracteristiqueProduit/{uuid}")
	public CaracteristiqueProduitDto update(@RequestBody CaracteristiqueProduitDto caracteristiqueProduitDto,@PathVariable String uuid) {
		return caracteristiqueProduitServiceImpl.update(caracteristiqueProduitDto, uuid);
	}
	
	@DeleteMapping("/caracteristique/delete/{uuid}")
	public CaracteristiqueProduitDto supprimer(@PathVariable String uuid) {
		return caracteristiqueProduitServiceImpl.supprimer(uuid);
	}
	
	@GetMapping("/caracteristiqueProduit/listecaracteristiqueProduit")
	public List<CaracteristiqueProduitDto> findAll(){
		return caracteristiqueProduitServiceImpl.findAll();
	}
	
	@GetMapping("/caracteristiqueProduit/getById/{uuid}")
	public CaracteristiqueProduitDto getById(@PathVariable String uuid) {
		return caracteristiqueProduitServiceImpl.getById(uuid);
	}
	
	@DeleteMapping("/caracteristiqueProduit/{uuid}")
	void deleteCaracteristiqueProduit(@PathVariable String uuid) {
		caracteristiqueProduitServiceImpl.delete(uuid);
	}

}
