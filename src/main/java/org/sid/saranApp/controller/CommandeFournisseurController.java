package org.sid.saranApp.controller;

import org.sid.saranApp.dto.CommandeFournisseurDto;
import org.sid.saranApp.dto.CommandeFournisseurPatchDto;
import org.sid.saranApp.dto.PageDataDto;
import org.sid.saranApp.enume.StatusCommandeFournisseurEnum;
import org.sid.saranApp.service.CommandeFournisseurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
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

	/**
	 * Corps JSON : au moins un des champs {@code status} (nom d'énum
	 * {@link StatusCommandeFournisseurEnum}) ou {@code paye} (booléen).
	 */
	@PatchMapping("/commandeFournisseur/{uuid}/status")
	public CommandeFournisseurDto patchCommandeFournisseur(@PathVariable String uuid,
			@RequestBody CommandeFournisseurPatchDto body) {
		return commandeFournisseurService.patchCommandeFournisseur(uuid, body);
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
	
	/**
	 * Endpoint optimisé inspiré d'Odoo - Retourne une commande complète avec tous ses détails
	 * GET /api/commandeFournisseur/{uuid}/complete
	 * 
	 * @param uuid L'UUID de la commande
	 * @return La commande complète avec détails, livraisons, paiements
	 */
	@GetMapping("/commandeFournisseur/{uuid}/complete")
	public CommandeFournisseurDto getCommandeFournisseurComplete(@PathVariable String uuid) {
		return commandeFournisseurService.getCommandeFournisseur(uuid);
	}
	
	/**
	 * Endpoint optimisé inspiré d'Odoo - Recherche avancée avec filtres multiples
	 * GET /api/commandeFournisseur/search
	 * 
	 * @param status Statut de la commande (optionnel)
	 * @param uuidFournisseur UUID du fournisseur (optionnel)
	 * @param dateDebut Date de début (optionnel)
	 * @param dateFin Date de fin (optionnel)
	 * @param search Terme de recherche (nom fournisseur, référence) (optionnel)
	 * @param page Numéro de page (défaut: 0)
	 * @param size Taille de la page (défaut: 10)
	 * @return Page de commandes filtrées
	 */
	@GetMapping("/commandeFournisseur/search")
	public PageDataDto<CommandeFournisseurDto> searchCommandesFournisseur(
			@RequestParam(required = false) StatusCommandeFournisseurEnum status,
			@RequestParam(required = false) String uuidFournisseur,
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateDebut,
			@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateFin,
			@RequestParam(required = false) String search,
			@RequestParam(required = false, defaultValue = "0") int page,
			@RequestParam(required = false, defaultValue = "10") int size) {
		// Utiliser le service existant avec filtres
		if (dateDebut != null && dateFin != null) {
			return commandeFournisseurService.listeCommandeFournisseurByDates(page, size, dateDebut, dateFin);
		}
		return commandeFournisseurService.listeCommandeFournisseurs(page, size, status);
	}

}
