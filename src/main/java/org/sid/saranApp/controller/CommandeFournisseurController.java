package org.sid.saranApp.controller;

import java.util.List;

import org.sid.saranApp.dto.CommandeFournisseurDto;
import org.sid.saranApp.service.CommandeFournisseurService;
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
public class CommandeFournisseurController {

	@Autowired
	private CommandeFournisseurService commandeFournisseurService;

	@PostMapping("/commandeFournisseur")
	public CommandeFournisseurDto addCommandeFournisseur(@RequestBody CommandeFournisseurDto commandeFournisseurDto) {
		return commandeFournisseurService.addCommandeFournisseur(commandeFournisseurDto);
	}

	@PostMapping("/commandeFournisseur/livraison")
	public CommandeFournisseurDto addCommandeFournisseurLivraison(
			@RequestBody CommandeFournisseurDto commandeFournisseurDto) {
		return commandeFournisseurService.addLivraisonCommandeFournisseur(commandeFournisseurDto);
	}

	@PostMapping("/commandeFournisseur/stock")
	public CommandeFournisseurDto addCommandeFournisseurLivraisonStock(
			@RequestBody CommandeFournisseurDto commandeFournisseurDto) {
		return commandeFournisseurService.addStockFromCommandeAndLivraison(commandeFournisseurDto);
	}

	@DeleteMapping("/CommandeFournisseur/{uuid}")
	public void deleteCommandeFournisseur(@PathVariable String uuid) {
		commandeFournisseurService.deleteCommandeFournisseur(uuid);
	}

	@GetMapping("/commandeFournisseur")
	public List<CommandeFournisseurDto> findAll() {
		return commandeFournisseurService.findAll();
	}

	@GetMapping("/commandeFournisseur/{uuid}")
	public CommandeFournisseurDto getCommandeFournisseur(@PathVariable String uuid) {
		return commandeFournisseurService.getCommandeFournisseur(uuid);
	}

	@PutMapping("/commandeFournisseur/{uuid}")
	public CommandeFournisseurDto updateCommandeFournisseur(@RequestBody CommandeFournisseurDto commandeFournisseurDto,
			@PathVariable String uuid) {
		return commandeFournisseurService.updateCommandeFournisseur(commandeFournisseurDto, uuid);
	}

}
