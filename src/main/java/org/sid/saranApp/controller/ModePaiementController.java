package org.sid.saranApp.controller;

import org.sid.saranApp.dto.ModePaiementDto;
import org.sid.saranApp.service.ModePaiementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
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
