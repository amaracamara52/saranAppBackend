package org.sid.saranApp.controller;

import org.sid.saranApp.dto.CommandeGrossisteDto;
import org.sid.saranApp.dto.PageDataDto;
import org.sid.saranApp.service.CommandeGrossisteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur pour la gestion des commandes entre détaillants/clients partenaires et grossistes
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/commandeGrossiste")
public class CommandeGrossisteController {

    private static final Logger logger = LoggerFactory.getLogger(CommandeGrossisteController.class);

    @Autowired
    private CommandeGrossisteService commandeGrossisteService;

    /**
     * Crée une nouvelle commande d'un détaillant ou client partenaire vers un grossiste
     * POST /api/commandeGrossiste
     * 
     * @param commandeGrossisteDto Les données de la commande (peut inclure uuidClientPartenaire pour les clients partenaires)
     * @return La commande créée
     */
    @PostMapping
    public CommandeGrossisteDto creerCommande(@RequestBody CommandeGrossisteDto commandeGrossisteDto) {
        logger.info("Création d'une commande grossiste");
        return commandeGrossisteService.creerCommande(commandeGrossisteDto);
    }

    /**
     * Valide une commande (uniquement pour les grossistes)
     * PUT /api/commandeGrossiste/{uuid}/valider
     * 
     * @param uuid L'UUID de la commande à valider
     * @return La commande validée
     */
    @PutMapping("/{uuid}/valider")
    public CommandeGrossisteDto validerCommande(@PathVariable String uuid) {
        logger.info("Validation de la commande grossiste: {}", uuid);
        return commandeGrossisteService.validerCommande(uuid);
    }

    /**
     * Annule une commande
     * PUT /api/commandeGrossiste/{uuid}/annuler
     * 
     * @param uuid L'UUID de la commande à annuler
     * @return La commande annulée
     */
    @PutMapping("/{uuid}/annuler")
    public CommandeGrossisteDto annulerCommande(@PathVariable String uuid) {
        logger.info("Annulation de la commande grossiste: {}", uuid);
        return commandeGrossisteService.annulerCommande(uuid);
    }

    /**
     * Récupère toutes les commandes d'un détaillant
     * GET /api/commandeGrossiste/detaillant
     * 
     * @return Liste des commandes du détaillant
     */
    @GetMapping("/detaillant")
    public List<CommandeGrossisteDto> getCommandesDetaillee() {
        logger.info("Récupération des commandes détaillant");
        return commandeGrossisteService.getCommandesDetaillee();
    }

    /**
     * Récupère toutes les commandes reçues par un grossiste
     * GET /api/commandeGrossiste/grossiste
     * 
     * @return Liste des commandes reçues par le grossiste
     */
    @GetMapping("/grossiste")
    public List<CommandeGrossisteDto> getCommandesGrossiste() {
        logger.info("Récupération des commandes grossiste");
        return commandeGrossisteService.getCommandesGrossiste();
    }

    /**
     * Récupère toutes les commandes d'un client partenaire
     * GET /api/commandeGrossiste/clientPartenaire/{uuidClientPartenaire}
     * 
     * @param uuidClientPartenaire L'UUID du client partenaire
     * @return Liste des commandes du client partenaire
     */
    @GetMapping("/clientPartenaire/{uuidClientPartenaire}")
    public List<CommandeGrossisteDto> getCommandesClientPartenaire(@PathVariable String uuidClientPartenaire) {
        logger.info("Récupération des commandes du client partenaire: {}", uuidClientPartenaire);
        return commandeGrossisteService.getCommandesClientPartenaire(uuidClientPartenaire);
    }

    /**
     * Récupère une commande par son UUID
     * GET /api/commandeGrossiste/{uuid}
     * 
     * @param uuid L'UUID de la commande
     * @return La commande
     */
    @GetMapping("/{uuid}")
    public CommandeGrossisteDto getCommandeById(@PathVariable String uuid) {
        logger.info("Récupération de la commande: {}", uuid);
        return commandeGrossisteService.getCommandeById(uuid);
    }

    /**
     * Récupère les commandes avec pagination
     * GET /api/commandeGrossiste/page
     * 
     * @param page Numéro de page (défaut: 0)
     * @param size Taille de la page (défaut: 10)
     * @param key Mot-clé de recherche (optionnel)
     * @return Page de commandes
     */
    @GetMapping("/page")
    public PageDataDto<CommandeGrossisteDto> getCommandes(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size,
            @RequestParam(required = false) String key) {
        logger.info("Récupération paginée des commandes - page: {}, size: {}, key: {}", page, size, key);
        return commandeGrossisteService.getCommandes(page, size, key);
    }

    /**
     * Endpoint optimisé inspiré d'Odoo - Retourne une commande complète avec tous ses détails
     * GET /api/commandeGrossiste/{uuid}/complete
     * 
     * @param uuid L'UUID de la commande
     * @return La commande complète avec détails, livraisons, client partenaire
     */
    @GetMapping("/{uuid}/complete")
    public CommandeGrossisteDto getCommandeComplete(@PathVariable String uuid) {
        logger.info("Récupération de la commande complète: {}", uuid);
        return commandeGrossisteService.getCommandeById(uuid);
    }

    /**
     * Endpoint optimisé inspiré d'Odoo - Recherche avancée avec filtres multiples
     * GET /api/commandeGrossiste/search
     * 
     * @param statut Statut de la commande (optionnel)
     * @param uuidClientPartenaire UUID du client partenaire (optionnel)
     * @param uuidBoutiqueGrossiste UUID de la boutique grossiste (optionnel)
     * @param search Terme de recherche (numéro commande, nom client) (optionnel)
     * @param page Numéro de page (défaut: 0)
     * @param size Taille de la page (défaut: 10)
     * @return Page de commandes filtrées
     */
    @GetMapping("/search")
    public PageDataDto<CommandeGrossisteDto> searchCommandes(
            @RequestParam(required = false) String statut,
            @RequestParam(required = false) String uuidClientPartenaire,
            @RequestParam(required = false) String uuidBoutiqueGrossiste,
            @RequestParam(required = false) String search,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size) {
        logger.info("Recherche de commandes - statut: {}, clientPartenaire: {}, search: {}", 
            statut, uuidClientPartenaire, search);
        // Pour l'instant, utiliser la méthode existante
        // TODO: Implémenter la recherche avancée dans le service
        return commandeGrossisteService.getCommandes(page, size, search);
    }

}
