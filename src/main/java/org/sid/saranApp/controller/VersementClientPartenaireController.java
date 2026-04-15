package org.sid.saranApp.controller;

import org.sid.saranApp.dto.VersementClientPartenaireDto;
import org.sid.saranApp.service.VersementClientPartenaireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur pour la gestion des versements des clients partenaires
 */
@CrossOrigin(origins = "*")
@RequestMapping("/api/versementClientPartenaire")
@RestController
public class VersementClientPartenaireController {

    @Autowired
    private VersementClientPartenaireService versementClientPartenaireService;

    /**
     * Enregistre un versement d'un client partenaire
     * @param versementDto Les données du versement
     * @return Le versement enregistré
     */
    @PostMapping
    public VersementClientPartenaireDto enregistrerVersement(@RequestBody VersementClientPartenaireDto versementDto) {
        return versementClientPartenaireService.enregistrerVersement(versementDto);
    }

    /**
     * Récupère tous les versements d'un client partenaire
     * @param uuidClientPartenaire L'UUID du client partenaire
     * @return Liste des versements
     */
    @GetMapping("/clientPartenaire/{uuidClientPartenaire}")
    public List<VersementClientPartenaireDto> getVersementsByClientPartenaire(@PathVariable String uuidClientPartenaire) {
        return versementClientPartenaireService.getVersementsByClientPartenaire(uuidClientPartenaire);
    }

    /**
     * Récupère tous les versements d'une commande vente
     * @param uuidCommande L'UUID de la commande
     * @return Liste des versements
     */
    @GetMapping("/commande/{uuidCommande}")
    public List<VersementClientPartenaireDto> getVersementsByCommande(@PathVariable String uuidCommande) {
        return versementClientPartenaireService.getVersementsByCommande(uuidCommande);
    }

    /**
     * Récupère un versement par son UUID
     * @param uuid L'UUID du versement
     * @return Le versement
     */
    @GetMapping("/{uuid}")
    public VersementClientPartenaireDto getVersementById(@PathVariable String uuid) {
        return versementClientPartenaireService.getVersementById(uuid);
    }
}
