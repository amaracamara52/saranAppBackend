package org.sid.saranApp.controller;

import java.util.List;

import org.sid.saranApp.dto.VilleDto;
import org.sid.saranApp.service.VilleService;
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
public class VilleController {
	
	@Autowired
	private VilleService villeService;
	
	
	@PostMapping("/ville")
	public VilleDto addVille(@RequestBody VilleDto VilleDto) {
		return villeService.addVille(VilleDto);
	}
	
	@PutMapping("/ville/{uuid}")
	public VilleDto updateVille(@RequestBody VilleDto VilleDto,@PathVariable String uuid) {
		return villeService.updateVille(VilleDto, uuid);
	}
	
	@GetMapping("/ville/{uuid}")
	public VilleDto getVille(@PathVariable String uuid) {
		return villeService.getVille(uuid);
	}
	
	@GetMapping("/ville")
	public List<VilleDto> findAll(){
		return villeService.findAll();
	}
	
	@GetMapping("/ville/byPays/{uuidPays}")
	public List<VilleDto> listeVilleByPays(@PathVariable String uuidPays){
		return villeService.listeVilleByPays(uuidPays);
	}
	@DeleteMapping("/ville")
	public void deleteVille(@PathVariable String uuid) {
		villeService.deleteVille(uuid);
	}

}
