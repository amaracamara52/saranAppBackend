package org.sid.saranApp.controller;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.sid.saranApp.dto.CommandeFournisseurDto;
import org.sid.saranApp.dto.CommandeVenteDto;
import org.sid.saranApp.dto.PageDataDto;
import org.sid.saranApp.enume.StatusCommandeFournisseurEnum;
import org.sid.saranApp.service.CommandeFournisseurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	
	@GetMapping("/commandeFournisseur/page_commandeFournisseur")
    public PageDataDto<CommandeFournisseurDto> getCommandeFournisseur(
        @RequestParam(required = false) StatusCommandeFournisseurEnum key,
        @RequestParam(required = true,defaultValue = "0") int page,
        @RequestParam(required = true,defaultValue = "10") int size
    ) {
        return commandeFournisseurService.listeCommandeFournisseurs(page, size, key);
    }
	
	@GetMapping("/commandeFournisseur/page_commandeFournisseur_historique/{dateDebut}/{dateFin}")
    public PageDataDto<CommandeFournisseurDto> historiqueCommandeFournisseur(
        @RequestParam(required = true,defaultValue = "0") int page,
        @RequestParam(required = true,defaultValue = "10") int size,
        @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd")  LocalDate dateDebut,
        @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd")  LocalDate dateFin
    ) {
        return commandeFournisseurService.listeCommandeFournisseurByDates(page, size, dateDebut, dateFin);
    }
	
	
	

}
