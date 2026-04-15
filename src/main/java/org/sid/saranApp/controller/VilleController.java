package org.sid.saranApp.controller;

import org.sid.saranApp.dto.VilleDto;
import org.sid.saranApp.service.VilleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
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
