package org.sid.saranApp.controller;

import java.util.List;

import org.sid.saranApp.dto.CommandeFournisseurDto;
import org.sid.saranApp.dto.DetailCommandeFournisseurDto;
import org.sid.saranApp.service.DetailCommandeFournisseurService;
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
