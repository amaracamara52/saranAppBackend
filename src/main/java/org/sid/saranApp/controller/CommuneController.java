package org.sid.saranApp.controller;

import org.sid.saranApp.dto.CommuneDto;
import org.sid.saranApp.service.CommuneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
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
