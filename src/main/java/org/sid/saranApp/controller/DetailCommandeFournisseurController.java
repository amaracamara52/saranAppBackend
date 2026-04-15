package org.sid.saranApp.controller;

import org.sid.saranApp.dto.DetailCommandeFournisseurDto;
import org.sid.saranApp.service.DetailCommandeFournisseurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class DetailCommandeFournisseurController {
	
	@Autowired
	private DetailCommandeFournisseurService detailCommandeFournisseurService;
	
	@PostMapping("/detailCommandeFournisseur")
	public DetailCommandeFournisseurDto addDetailCommandeFournisseur(@RequestBody DetailCommandeFournisseurDto detailCommandeFournisseurDto) {
		return detailCommandeFournisseurService.addDetailCommandeFournisseur(detailCommandeFournisseurDto);
	}
	@PutMapping("/detailCommandeFournisseur/{uuid}")
	public DetailCommandeFournisseurDto updateDetailCommandeFournisseur(@RequestBody DetailCommandeFournisseurDto detailCommandeFournisseurDto, @PathVariable String uuid) {
		return detailCommandeFournisseurService.updateDetailCommandeFournisseur(detailCommandeFournisseurDto, uuid);
	}
	@GetMapping("/detailCommandeFournisseur")
	public List<DetailCommandeFournisseurDto> findAll(){
		return detailCommandeFournisseurService.findAll();
	}
	
	@GetMapping("/detailCommandeFourniseur/{uuid}")
	public DetailCommandeFournisseurDto getDetailCommandeFournisseur(@PathVariable String uuid) {
		return detailCommandeFournisseurService.getDetailCommandeFournisseur(uuid);
	}
	@DeleteMapping("/detailCommandeFournisseur/{uuid}")
	public void deleteDetailCommandeFournisseur(@PathVariable String uuid) {
		detailCommandeFournisseurService.deleteDetailCommandeFournisseur(uuid);
	}
	
	

}
