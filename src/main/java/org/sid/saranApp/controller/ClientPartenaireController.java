package org.sid.saranApp.controller;

import org.sid.saranApp.dto.ClientPartenaireDto;
import org.sid.saranApp.service.ClientPartenaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur pour la gestion des partenariats client-boutique
 */
@CrossOrigin(origins = "*")
@RequestMapping("/api/clientPartenaire")
@RestController
public class ClientPartenaireController {

    @Autowired
    private ClientPartenaireService clientPartenaireService;

    /**
     * Crée un nouveau partenariat entre un client et une boutique
     * @param clientPartenaireDto Les données du partenariat
     * @return Le partenariat créé
     */
    @PostMapping
    public ClientPartenaireDto creerPartenaire(@RequestBody ClientPartenaireDto clientPartenaireDto) {
        return clientPartenaireService.creerPartenaire(clientPartenaireDto);
    }

    /**
     * Met à jour le statut d'un partenariat
     * @param uuid L'UUID du partenariat
     * @param statut Le nouveau statut
     * @return Le partenariat mis à jour
     */
    @PutMapping("/{uuid}/statut")
    public ClientPartenaireDto mettreAJourStatut(
            @PathVariable String uuid,
            @RequestParam String statut) {
        return clientPartenaireService.mettreAJourStatut(uuid, statut);
    }

    /**
     * Vérifie si un client est partenaire d'une boutique
     * @param uuidClient L'UUID du client
     * @param uuidBoutique L'UUID de la boutique
     * @return true si le client est partenaire actif
     */
    @GetMapping("/verifier")
    public boolean estClientPartenaire(
            @RequestParam String uuidClient,
            @RequestParam String uuidBoutique) {
        return clientPartenaireService.estClientPartenaire(uuidClient, uuidBoutique);
    }

    /**
     * Récupère tous les partenariats d'un client
     * @param uuidClient L'UUID du client
     * @return Liste des partenariats
     */
    @GetMapping("/client/{uuidClient}")
    public List<ClientPartenaireDto> getPartenariatsByClient(@PathVariable String uuidClient) {
        return clientPartenaireService.getPartenariatsByClient(uuidClient);
    }

    /**
     * Récupère tous les partenariats d'une boutique
     * @param uuidBoutique L'UUID de la boutique
     * @return Liste des partenariats
     */
    @GetMapping("/boutique/{uuidBoutique}")
    public List<ClientPartenaireDto> getPartenariatsByBoutique(@PathVariable String uuidBoutique) {
        return clientPartenaireService.getPartenariatsByBoutique(uuidBoutique);
    }

    /**
     * Récupère un partenariat par son UUID
     * @param uuid L'UUID du partenariat
     * @return Le partenariat
     */
    @GetMapping("/{uuid}")
    public ClientPartenaireDto getPartenaireById(@PathVariable String uuid) {
        return clientPartenaireService.getPartenaireById(uuid);
    }
}
