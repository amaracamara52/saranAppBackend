package org.sid.saranApp.controller;

import org.sid.saranApp.dto.UtilisateurDto;
import org.sid.saranApp.service.UtilisateurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class UtilisateurController {

	@Autowired
	private UtilisateurService utilisateurService;

	@PostMapping("/utilisateur")
	public UtilisateurDto addUtilisateur(@RequestBody UtilisateurDto utilisateurDto) {
		return utilisateurService.addUtilisateur(utilisateurDto);
	}

	@DeleteMapping("/utilisateur/{uuuid}")
	public void deleteUtilisateur(@PathVariable String uuid) {
		utilisateurService.deleteUtilisateur(uuid);
	}

	@GetMapping("/utilisateur")
	public List<UtilisateurDto> findAll() {
		return utilisateurService.findAll();
	}

	@GetMapping("/utilisateur/byEmail/{email}")
	public UtilisateurDto getEmail(@PathVariable String email) {
		return utilisateurService.getUtilisateurByEmail(email);
	}

	@GetMapping("/utilisateur/{uuid}")
	public UtilisateurDto getUtilisateur(@PathVariable String uuid) {
		return utilisateurService.getUtilisateur(uuid);
	}

	@PutMapping("/utilisateur/{uuid}")
	public UtilisateurDto updateUtilisateur(@RequestBody UtilisateurDto utilisateurDto, @PathVariable String uuid) {
		return utilisateurService.updateUtilisateur(utilisateurDto, uuid);
	}

	@GetMapping("/utilisateur/login")
	public UtilisateurDto userLogin() {
		return utilisateurService.userLogin();
	}

}
