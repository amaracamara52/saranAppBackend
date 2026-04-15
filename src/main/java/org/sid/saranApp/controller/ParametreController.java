package org.sid.saranApp.controller;

import org.sid.saranApp.dto.ParametreDto;
import org.sid.saranApp.service.ParametreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class ParametreController {
	
	@Autowired
	private ParametreService parametreService;
	
	@PostMapping("/parametre")
	public ParametreDto addParametre(@RequestBody ParametreDto parametreDto) {
		return parametreService.addParametre(parametreDto);
	}
	@PutMapping("/parametre/{uuid}")
	public ParametreDto updateParametre(@RequestBody ParametreDto parametreDto, @PathVariable String uuid) {
		return parametreService.updateParametre(parametreDto, uuid);
		
	}
	@GetMapping("/parametre")
	public List<ParametreDto> findAll(){
		return parametreService.findAll();
				
	}
	@GetMapping("/parametre/{uuid}")
	public ParametreDto getParametre(@PathVariable String uuid) {
		return parametreService.getParametre(uuid);
	}
	@DeleteMapping("/parametre/{uuid}")
	public void deleteParametre(@PathVariable String uuid) {
		parametreService.deleteParametre(uuid);
	}

	
	
	
}
