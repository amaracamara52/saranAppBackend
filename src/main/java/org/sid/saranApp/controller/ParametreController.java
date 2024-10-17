package org.sid.saranApp.controller;

import java.util.List;

import org.sid.saranApp.dto.ParametreDto;
import org.sid.saranApp.service.ParametreService;
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
