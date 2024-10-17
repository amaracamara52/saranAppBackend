package org.sid.saranApp.controller;

import java.util.List;

import org.sid.saranApp.dto.CaracteristiqueArticleDto;
import org.sid.saranApp.service.CaracteristiqueArticleService;
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
public class CaracteristiqueArticleController {
	
	@Autowired
	private CaracteristiqueArticleService caracteristiqueArticleService;
	
	@PostMapping("/caracteristiqueArticle")
	public CaracteristiqueArticleDto addCaracteristiqueArticle(@RequestBody CaracteristiqueArticleDto caracteristiqueArticleDto) {
		return caracteristiqueArticleService.addCaracteristiqueArticle(caracteristiqueArticleDto);
				
	}
	
	@PutMapping("/caracteristiqueArticle/{uuid}")
	public CaracteristiqueArticleDto updateCaracteristiqueArticle(@RequestBody CaracteristiqueArticleDto caracteristiqueArticleDto, @PathVariable String uuid) {
		return caracteristiqueArticleService.updateCaracteristiqueArticle(caracteristiqueArticleDto, uuid);
	}
	
	@GetMapping("/caracteristiqueArticle")
	public List<CaracteristiqueArticleDto> findAll(){
		return caracteristiqueArticleService.findAll();
				
	}
	

	@GetMapping("/caracteristiqueArticle/{uuid}")
	public CaracteristiqueArticleDto getCaracteristiqueArticle(@PathVariable String uuid) {
		return caracteristiqueArticleService.getCaracteristiqueArticle(uuid);
				
	}
	
	@DeleteMapping("/caracteristiqueArticle/{uuid}")
	public void deleteCaracteristiqueArticle(@PathVariable String uuid) {
		caracteristiqueArticleService.deleteCaracteristiqueArticle(uuid);
	}
	
	
	
	
	
	
}
