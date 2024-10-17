package org.sid.saranApp.controller;

import java.util.List;

import org.sid.saranApp.dto.CommuneDto;
import org.sid.saranApp.service.CommuneService;
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
public class CommuneController {
	
	@Autowired
	private CommuneService communeService;
	
	
	@PostMapping("/commune")
	public CommuneDto addCommune(@RequestBody CommuneDto CommuneDto) {
		return communeService.addCommune(CommuneDto);
	}
	
	@PutMapping("/commune/{uuid}")
	public CommuneDto updateCommune(@RequestBody CommuneDto CommuneDto,@PathVariable String uuid) {
		return communeService.updateCommune(CommuneDto, uuid);
	}
	
	@GetMapping("/commune/{uuid}")
	public CommuneDto getCommune(@PathVariable String uuid) {
		return communeService.getCommune(uuid);
	}
	
	@GetMapping("/commune")
	public List<CommuneDto> findAll(){
		return communeService.findAll();
	}
	@GetMapping("/commune/byVille/{uuidVille}")
	public List<CommuneDto> listeCommuneByVille(@PathVariable String uuidVille){
		return communeService.listeCommuneByVille(uuidVille);
	}
	@DeleteMapping("/commune")
	public void deleteCommune(@PathVariable String uuid) {
		communeService.deleteCommune(uuid);
	}

}
