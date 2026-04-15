package org.sid.saranApp.controller;

import org.sid.saranApp.dto.FournisseurDto;
import org.sid.saranApp.service.FournisseurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class FournisseurController {

	@Autowired
	private FournisseurService fournisseurService;

	@PostMapping("/fournisseur")
	public FournisseurDto addFournisseur(@RequestBody FournisseurDto fournisseurDto) {
		return fournisseurService.addFournisseur(fournisseurDto);
	}

	@DeleteMapping("/fournisseur/{uuid}")
	public void deleteFournisseur(@PathVariable String uuid) {
		fournisseurService.deleteFournisseur(uuid);
	}

	@GetMapping("/fournisseur")
	public List<FournisseurDto> findAll() {
		return fournisseurService.findAll();
	}

	@GetMapping("/fournisseur/{uuid}")
	public FournisseurDto getFournisseur(@PathVariable String uuid) {
		return fournisseurService.getFournisseur(uuid);
	}

	@PutMapping("/fournisseur/{uuid}")
	public FournisseurDto updateFournisseur(@RequestBody FournisseurDto fournisseurDto, @PathVariable String uuid) {
		return fournisseurService.updateFournisseur(fournisseurDto, uuid);
	}

}
