package org.sid.saranApp.controller;

import org.sid.saranApp.dto.CommandeVenteDto;
import org.sid.saranApp.dto.ImportVenteHistoriqueResultDto;
import org.sid.saranApp.dto.PageDataDto;
import org.sid.saranApp.dto.request.CommandeVenteRequestDto;
import org.sid.saranApp.dto.request.ImportVenteHistoriqueVenteDto;
import org.sid.saranApp.service.CommandeVenteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class CommandeVenteController {
	
	@Autowired
	private CommandeVenteService commandeVenteService;
	Logger logger = LoggerFactory.getLogger(CommandeVenteController.class);
	@PostMapping("/commandeVente")
	public CommandeVenteDto add(@RequestBody CommandeVenteRequestDto commandeVenteDto) {
		logger.info("hello");
		return commandeVenteService.add(commandeVenteDto);
	}

	/**
	 * Import JSON de ventes historiques : pas de mouvement de stock, pas de transaction ni caisse.
	 * Corps : tableau de ventes (uuid produit/unité optionnels si {@code article} et unités catalogue existent).
	 */
	@PostMapping("/commandeVente/import-historique-json")
	public ImportVenteHistoriqueResultDto importVentesHistoriqueJson(
			@RequestBody List<ImportVenteHistoriqueVenteDto> ventes) {
		return commandeVenteService.importVentesHistoriqueJson(ventes);
	}
	
	@PutMapping("/commandeVente/{uuid}")
	public CommandeVenteDto update(@RequestBody CommandeVenteDto commandeVenteDto,@PathVariable String uuid) {
		return commandeVenteService.update(commandeVenteDto, uuid);
	}
	
	@DeleteMapping("/commandeVente/delete/{uuid}")
	public CommandeVenteDto supprimer(@PathVariable String uuid) {
		return commandeVenteService.supprimer(uuid);
	}
	
	@GetMapping("/commandeVente/listeCommandeVente")
	public List<CommandeVenteDto> findAll(){
		return commandeVenteService.findAll();
	}
	
	@GetMapping("/commandeVente/listeCommandeVenteJour")
	public List<CommandeVenteDto> listeCommandeVenteJour(){
		return commandeVenteService.listeCommandeVenteByJour();
	}
	
	
	@GetMapping("/commandeVente/historique/{dateDebut}/{dateFin}")
	public List<CommandeVenteDto> historique(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd")  Date dateDebut,@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd")  Date dateFin){
		return commandeVenteService.historiqueCommandeVente(dateDebut, dateFin);
	}
	
	@GetMapping("/commandeVente/getById/{uuid}")
	public CommandeVenteDto getById(@PathVariable String uuid) {
		return commandeVenteService.getById(uuid);
	}
	
	
	
	@GetMapping("/commandeVente/page_historique/{dateDebut}/{dateFin}")
    public PageDataDto<CommandeVenteDto> historiqueCommandeVentes(
        @RequestParam(required = false) String key,
        @RequestParam int page,
        @RequestParam int size,
        @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd")  Date dateDebut,
        @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd")  Date dateFin
    ) {
        return commandeVenteService.historiqueCommandeVente(dateDebut, dateFin, page, size, key);
    }
	
	@GetMapping("/commandeVente/page_commande")
    public PageDataDto<CommandeVenteDto> getCommandeVentes(
        @RequestParam(required = false) String key,
        @RequestParam(required = true,defaultValue = "0") int page,
        @RequestParam(required = true,defaultValue = "10") int size
    ) {
        return commandeVenteService.listeCommandeVenteByJour(page, size, key);
    }
	
	@DeleteMapping("/commandeVente/{uuid}")
	void deleteCommandeVente(@PathVariable String uuid) {
		commandeVenteService.delete(uuid);
	}

	/**
	 * Valide une commande de vente en gros (uniquement pour les grossistes)
	 * @param uuid L'UUID de la commande à valider
	 * @return La commande validée
	 */
	@PutMapping("/commandeVente/{uuid}/valider")
	public CommandeVenteDto validerCommandeEnGros(@PathVariable String uuid) {
		return commandeVenteService.validerCommandeEnGros(uuid);
	}

}
