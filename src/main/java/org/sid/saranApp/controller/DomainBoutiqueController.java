package org.sid.saranApp.controller;

import java.util.List;

import org.sid.saranApp.dto.DomainBoutiqueDto;
import org.sid.saranApp.service.DomainBoutiqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
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
