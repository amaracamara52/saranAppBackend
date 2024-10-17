package org.sid.saranApp.controller;

import java.util.List;

import org.sid.saranApp.dto.BoutiquePaiementDto;
import org.sid.saranApp.service.BoutiquePaiementService;
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
public class BoutiquePaiementController {
	
	@Autowired
	private BoutiquePaiementService boutiquePaiementService;
	
	@PostMapping("/boutiquePaiment")
	public BoutiquePaiementDto addBoutiquePaiement(@RequestBody BoutiquePaiementDto boutiquePaiementDto) {
		return boutiquePaiementService.addBoutiquePaiement(boutiquePaiementDto);
	}
	
	@PutMapping("/boutiquePaiement/{uuid}")
	public BoutiquePaiementDto updateBoutiquePaiement(@RequestBody BoutiquePaiementDto boutiquePaiementDto,@PathVariable String uuid) {
		return boutiquePaiementService.updateBoutiquePaiement(boutiquePaiementDto, uuid);
	}
	
	@GetMapping("/boutiquePaiement")
	public List<BoutiquePaiementDto> findAll(){
		return boutiquePaiementService.findAll();
	}
	
	@GetMapping("/boutiquePaiement/{uuid}")
	public BoutiquePaiementDto getBoutiquePaiement(@PathVariable String uuid) {
		return boutiquePaiementService.getBoutiquePaiement(uuid);
	}
	@DeleteMapping("/boutiquePaiement/{uuid}")
	public void deleteBoutiquePaiement(@PathVariable String uuid) {
		boutiquePaiementService.deleteBoutiquePaiement(uuid);
	}

}
