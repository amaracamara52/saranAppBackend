package org.sid.saranApp.controller;

import org.sid.saranApp.dto.CaracteristiqueProduitDto;
import org.sid.saranApp.serviceImpl.CaracteristiqueProduitServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
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
