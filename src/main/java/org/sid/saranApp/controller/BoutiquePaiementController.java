package org.sid.saranApp.controller;

import org.sid.saranApp.dto.BoutiquePaiementDto;
import org.sid.saranApp.service.BoutiquePaiementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
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
