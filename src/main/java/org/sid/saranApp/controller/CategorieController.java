package org.sid.saranApp.controller;

import org.sid.saranApp.dto.CategorieDto;
import org.sid.saranApp.service.CategorieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class CategorieController {
	
	@Autowired
	private CategorieService categorieService;
	
	@PostMapping("/categorie")
	public CategorieDto addCategorie(@RequestBody CategorieDto categorieDto) {
		return categorieService.addCategorie(categorieDto);
	}
	@PutMapping("/categorie/{uuid}")
	public CategorieDto updateCategorie(@RequestBody CategorieDto categorieDto, @PathVariable String uuid) {
		return categorieService.updateCategorie(categorieDto, uuid); 
	}
	@GetMapping("/categorie")
	public List<CategorieDto> findAll(){
		return categorieService.findAll();
	}
	@GetMapping("/categorie/{uuid}")
	public CategorieDto getCategorie(@PathVariable String uuid) {
		return categorieService.getCategorie(uuid);
	}
	@DeleteMapping("/categorie/{uuid}")
	public void deleteCategorie(@PathVariable String uuid) {
		categorieService.deleteCategorie(uuid);
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
