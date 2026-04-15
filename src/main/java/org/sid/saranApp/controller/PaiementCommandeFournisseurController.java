package org.sid.saranApp.controller;

import org.sid.saranApp.dto.PaiementCommandeFournisseurDto;
import org.sid.saranApp.service.PaiementCommandeFournisseurService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur pour la gestion des paiements des commandes fournisseur
 */
@CrossOrigin(origins = "*")
@RequestMapping("/api/paiementCommandeFournisseur")
@RestController
public class PaiementCommandeFournisseurController {

    @Autowired
    private PaiementCommandeFournisseurService paiementCommandeFournisseurService;

    /**
     * Enregistre un paiement pour une commande fournisseur
     * @param paiementDto Les données du paiement
     * @return Le paiement enregistré
     */
    @PostMapping
    public PaiementCommandeFournisseurDto enregistrerPaiement(@RequestBody PaiementCommandeFournisseurDto paiementDto) {
        return paiementCommandeFournisseurService.enregistrerPaiement(paiementDto);
    }

    /**
     * Récupère tous les paiements d'une commande fournisseur
     * @param uuidCommande L'UUID de la commande
     * @return Liste des paiements
     */
    @GetMapping("/commande/{uuidCommande}")
    public List<PaiementCommandeFournisseurDto> getPaiementsByCommande(@PathVariable String uuidCommande) {
        return paiementCommandeFournisseurService.getPaiementsByCommande(uuidCommande);
    }

    /**
     * Récupère tous les paiements d'un fournisseur
     * @param uuidFournisseur L'UUID du fournisseur
     * @return Liste des paiements
     */
    @GetMapping("/fournisseur/{uuidFournisseur}")
    public List<PaiementCommandeFournisseurDto> getPaiementsByFournisseur(@PathVariable String uuidFournisseur) {
        return paiementCommandeFournisseurService.getPaiementsByFournisseur(uuidFournisseur);
    }

    /**
     * Récupère un paiement par son UUID
     * @param uuid L'UUID du paiement
     * @return Le paiement
     */
    @GetMapping("/{uuid}")
    public PaiementCommandeFournisseurDto getPaiementById(@PathVariable String uuid) {
        return paiementCommandeFournisseurService.getPaiementById(uuid);
    }
}
