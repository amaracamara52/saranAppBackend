package org.sid.saranApp.controller;

import java.util.List;

import org.sid.saranApp.dto.QuartierDto;
import org.sid.saranApp.service.QuartierService;
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
public class QuartierController {
	
	@Autowired
	private QuartierService quartierService;
	
	@PostMapping("/quartier")
	public QuartierDto addQuartier(@RequestBody QuartierDto quartierDto) {
		return quartierService.addQuartier(quartierDto);
	}
	
	@PutMapping("/quartier/{uuid}")
	public QuartierDto updateQuartier(@RequestBody QuartierDto quartierDto,@PathVariable String uuid) {
		return quartierService.updateQuartier(quartierDto, uuid);
	}
	
	@GetMapping("/quartier/{uuid}")
	public QuartierDto getQuartier(@PathVariable String uuid) {
		return quartierService.getQuartier(uuid);
	}
	
	@GetMapping("/quartier")
	public List<QuartierDto> findAll(){
		return quartierService.findAll();
	}
	@DeleteMapping("/quartier/{uuid}")
	public void deleteQuartier(@PathVariable String uuid) {
		quartierService.deleteQuartier(uuid);
	}

}
