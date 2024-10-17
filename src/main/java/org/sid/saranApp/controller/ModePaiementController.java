package org.sid.saranApp.controller;

import java.util.List;

import org.sid.saranApp.dto.ModePaiementDto;
import org.sid.saranApp.service.ModePaiementService;
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
public class ModePaiementController {
	
	@Autowired
	private ModePaiementService modePaiementService;
	
	@PostMapping("/modePaiement")
	public ModePaiementDto addModePaiement(@RequestBody ModePaiementDto modePaiementDto) {
		return modePaiementService.addModePaiement(modePaiementDto);
	}
	
	@PutMapping("/modePaiement/{uuid}")
	public ModePaiementDto updateModePaiement(@RequestBody ModePaiementDto modePaiementDto,@PathVariable String uuid) {
		return modePaiementService.updModePaiement(modePaiementDto, uuid);
	}
	
	@GetMapping("/modePaiement/{uuid}")
	public ModePaiementDto getModePaiement(@PathVariable String uuid) {
		return modePaiementService.getModePaiement(uuid);
	}
	
	@GetMapping("/modePaiement")
	public List<ModePaiementDto> findAll(){
		return modePaiementService.findAll();
	}
	@DeleteMapping("/modePaiement")
	public void deleteModePaiement(@PathVariable String uuid) {
		modePaiementService.deleteModePaiement(uuid);
	}

}
