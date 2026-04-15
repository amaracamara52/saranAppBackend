package org.sid.saranApp.controller;

import org.sid.saranApp.dto.DomainCategorieDto;
import org.sid.saranApp.dto.DomainCategorieParamDto;
import org.sid.saranApp.dto.DomainDto;
import org.sid.saranApp.service.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/configuration")
public class ConfigurationController {
	
	@Autowired
	private ConfigurationService configurationService;

	@PostMapping("/domain")
	public DomainDto addDomain(@RequestBody DomainDto domainDto) {
		// TODO Auto-generated method stub
		return configurationService.addDomain(domainDto);
	}

	@PutMapping("/domain/{uuid}")
	public DomainDto updateDomain(@RequestBody DomainDto domainDto,@PathVariable String uuid) {
		// TODO Auto-generated method stub
		return configurationService.updateDomain(domainDto, uuid);
	}

	@GetMapping("/domain/{uuid}")
	public DomainDto getDomain(@PathVariable String uuid) {
		// TODO Auto-generated method stub
		return configurationService.getDomain(uuid);
	}

	@GetMapping("/domain")
	public List<DomainDto> listeDomains() {
		// TODO Auto-generated method stub
		return configurationService.listeDomains();
	}

	@PostMapping("/domainCategorie")
	public DomainCategorieDto addDomainCategorie(@RequestBody DomainCategorieDto domainCategorie) {
		// TODO Auto-generated method stub
		return configurationService.addDomainCategorie(domainCategorie);
	}

	
	@PutMapping("/domainCategorie/{uuid}")
	public DomainCategorieDto updateDomainCategorie(@RequestBody DomainCategorieDto domainCategorie,@PathVariable String uuid) {
		// TODO Auto-generated method stub
		return configurationService.updateDomainCategorie(domainCategorie, uuid);
	}

	
	@GetMapping("/domainCategorie/{uuid}")
	public DomainCategorieDto getDomainCategorie(@PathVariable String uuid) {
		// TODO Auto-generated method stub
		return configurationService.getDomainCategorie(uuid);
	}

	
	@GetMapping("/domainCategorie")
	public List<DomainCategorieDto> listeDomainCategories() {
		// TODO Auto-generated method stub
		return configurationService.listeDomainCategories();
	}

	
	@GetMapping("/domainCategorieByDomain/{uuidDomain}")
	public List<DomainCategorieDto> listeDomainCategorieByDomain(@PathVariable String uuidDomain) {
		// TODO Auto-generated method stub
		return configurationService.listeDomainCategorieByDomain(uuidDomain);
	}

	@PostMapping("/domainCategorieParam")
	public DomainCategorieParamDto addDomainCategorieParam(@RequestBody DomainCategorieParamDto domainCategorieParam) {
		// TODO Auto-generated method stub
		return configurationService.addDomainCategorieParam(domainCategorieParam);
	}

	@PutMapping("/domainCategorieParam/{uuid}")
	public DomainCategorieParamDto updateDomainCategorieParam(@RequestBody DomainCategorieParamDto domainCategorieParam,
			@PathVariable	String uuid) {
		// TODO Auto-generated method stub
		return configurationService.updateDomainCategorieParam(domainCategorieParam, uuid);
	}

	@GetMapping("/domainCategorieParam/{uuid}")
	public DomainCategorieParamDto getDomainCategorieParam(@PathVariable String uuid) {
		// TODO Auto-generated method stub
		return configurationService.getDomainCategorieParam(uuid);
	}

	@GetMapping("/domainCategorieParam")
	public List<DomainCategorieParamDto> listeDomainCategorieParams() {
		// TODO Auto-generated method stub
		return configurationService.listeDomainCategorieParams();
	}

	@GetMapping("/domainCategorieParam/{uuidDomainCategorie}")
	public List<DomainCategorieParamDto> listeDomainCategorieParamByDomainCategorie(@PathVariable String uuidDomainCategorie) {
		// TODO Auto-generated method stub
		return configurationService.listeDomainCategorieParamByDomainCategorie(uuidDomainCategorie);
	}

}
