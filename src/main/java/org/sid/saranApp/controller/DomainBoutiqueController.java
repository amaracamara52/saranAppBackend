package org.sid.saranApp.controller;

import org.sid.saranApp.dto.DomainBoutiqueDto;
import org.sid.saranApp.service.DomainBoutiqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class DomainBoutiqueController {
	
	@Autowired
	private DomainBoutiqueService domainBoutiqueService;

	
	@PostMapping("/domaine")
	public DomainBoutiqueDto addDomainBoutique(@RequestBody DomainBoutiqueDto boutiqueDto) {
		return domainBoutiqueService.addDomain(boutiqueDto);
	}
	
	
	@PutMapping("/domaine/{uuid}")
	public DomainBoutiqueDto updateDomainBoutique(@RequestBody DomainBoutiqueDto boutiqueDto,@PathVariable String uuid) {
		return domainBoutiqueService.updateDomain(boutiqueDto, uuid);
	}
	
	
	@GetMapping("/domaine/{uuid}")
	public DomainBoutiqueDto getDomainBoutique(@PathVariable String uuid) {
		return domainBoutiqueService.getDomain(uuid);
	}
	
	
	@GetMapping("/domaine")
	public List<DomainBoutiqueDto> listeDomainBoutique() {
		return domainBoutiqueService.listeDomain();
	}
}
