package org.sid.saranApp.controller;

import org.sid.saranApp.dto.LivraisonCommandeGrossisteDto;
import org.sid.saranApp.service.LivraisonCommandeGrossisteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur pour la gestion des livraisons de commandes grossistes
 */
@CrossOrigin(origins = "*")
@RequestMapping("/api/livraisonCommandeGrossiste")
@RestController
public class LivraisonCommandeGrossisteController {

    @Autowired
    private LivraisonCommandeGrossisteService livraisonCommandeGrossisteService;

    /**
     * Crée une livraison pour une commande grossiste
     * @param livraisonDto Les données de la livraison
     * @return La livraison créée
     */
    @PostMapping
    public LivraisonCommandeGrossisteDto creerLivraison(@RequestBody LivraisonCommandeGrossisteDto livraisonDto) {
        return livraisonCommandeGrossisteService.creerLivraison(livraisonDto);
    }

    /**
     * Enregistre le stock à partir d'une livraison grossiste
     * @param livraisonDto Les données de la livraison
     * @return La livraison avec stock enregistré
     */
    @PostMapping("/enregistrerStock")
    public LivraisonCommandeGrossisteDto enregistrerStock(@RequestBody LivraisonCommandeGrossisteDto livraisonDto) {
        return livraisonCommandeGrossisteService.enregistrerStock(livraisonDto);
    }

    /**
     * Récupère toutes les livraisons reçues par un détaillant
     * @return Liste des livraisons
     */
    @GetMapping("/detaillant")
    public List<LivraisonCommandeGrossisteDto> getLivraisonsDetaillee() {
        return livraisonCommandeGrossisteService.getLivraisonsDetaillee();
    }

    /**
     * Récupère toutes les livraisons envoyées par un grossiste
     * @return Liste des livraisons
     */
    @GetMapping("/grossiste")
    public List<LivraisonCommandeGrossisteDto> getLivraisonsGrossiste() {
        return livraisonCommandeGrossisteService.getLivraisonsGrossiste();
    }

    /**
     * Récupère une livraison par son UUID
     * @param uuid L'UUID de la livraison
     * @return La livraison
     */
    @GetMapping("/{uuid}")
    public LivraisonCommandeGrossisteDto getLivraisonById(@PathVariable String uuid) {
        return livraisonCommandeGrossisteService.getLivraisonById(uuid);
    }

    /**
     * Vérifie une livraison de commande grossiste
     * @param uuidLivraison L'UUID de la livraison
     * @param commentaire Commentaire de vérification (optionnel)
     * @return La livraison vérifiée
     */
    @PutMapping("/{uuidLivraison}/verifier")
    public LivraisonCommandeGrossisteDto verifierLivraison(
            @PathVariable String uuidLivraison,
            @RequestParam(required = false) String commentaire) {
        return livraisonCommandeGrossisteService.verifierLivraison(uuidLivraison, commentaire);
    }

    /**
     * Rejette une livraison de commande grossiste
     * @param uuidLivraison L'UUID de la livraison
     * @param commentaire Commentaire de rejet
     * @return La livraison rejetée
     */
    @PutMapping("/{uuidLivraison}/rejeter")
    public LivraisonCommandeGrossisteDto rejeterLivraison(
            @PathVariable String uuidLivraison,
            @RequestParam(required = true) String commentaire) {
        return livraisonCommandeGrossisteService.rejeterLivraison(uuidLivraison, commentaire);
    }
}
