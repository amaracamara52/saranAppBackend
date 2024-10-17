package org.sid.saranApp.controller;

import java.util.List;

import org.sid.saranApp.dto.CategorieDto;
import org.sid.saranApp.service.CategorieService;
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
