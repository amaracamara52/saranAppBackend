package org.sid.saranApp.controller;

import java.util.List;

import org.sid.saranApp.dto.UtilisateurDto;
import org.sid.saranApp.service.UtilisateurService;
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
