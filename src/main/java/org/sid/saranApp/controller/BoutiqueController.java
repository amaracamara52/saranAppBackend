package org.sid.saranApp.controller;

import java.util.List;

import org.sid.saranApp.dto.BoutiqueDto;
import org.sid.saranApp.service.BoutiqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class BoutiqueController {
	
	@Autowired
	private BoutiqueService boutiqueService;
	
	
	@GetMapping("/auth")
	public String method(@CurrentSecurityContext SecurityContext context) {
		return context.getAuthentication().getName();
	}
	
	@PostMapping("/boutique")
	public BoutiqueDto addBoutique(@RequestBody BoutiqueDto boutiqueDto) {
		return boutiqueService.addBoutique(boutiqueDto);
	}
	
	@PutMapping("/boutique/{uuid}")
	public BoutiqueDto updateBoutique(@RequestBody BoutiqueDto boutiqueDto,@PathVariable String uuid) {
		return boutiqueService.updateBoutique(boutiqueDto, uuid);
	}
	
	@GetMapping("/boutique")
	public List<BoutiqueDto> findAll(){
		return boutiqueService.findAll();
	}
	
	@GetMapping("/boutique/{uuid}")
	public BoutiqueDto getBoutique(@PathVariable String uuid) {
		return boutiqueService.getBoutique(uuid);
	}
	@DeleteMapping("/boutique/{uuid}")
	public void deleteBoutique(@PathVariable String uuid) {
		boutiqueService.deleteBoutique(uuid);
	}
	
	
}
