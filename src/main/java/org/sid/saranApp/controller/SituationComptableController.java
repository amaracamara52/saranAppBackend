package org.sid.saranApp.controller;

import org.sid.saranApp.dto.SituationClientPartenaireDto;
import org.sid.saranApp.dto.SituationComptableDto;
import org.sid.saranApp.dto.SituationFournisseurDto;
import org.sid.saranApp.service.SituationComptableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur pour la gestion de la situation comptable
 */
@CrossOrigin(origins = "*")
@RequestMapping("/api/situationComptable")
@RestController
public class SituationComptableController {

    @Autowired
    private SituationComptableService situationComptableService;

    /**
     * Récupère la situation comptable complète (dettes et créances)
     * @return La situation comptable
     */
    @GetMapping
    public SituationComptableDto getSituationComptable() {
        return situationComptableService.getSituationComptable();
    }

    /**
     * Récupère la situation comptable d'un fournisseur
     * @param uuidFournisseur L'UUID du fournisseur
     * @return La situation du fournisseur
     */
    @GetMapping("/fournisseur/{uuidFournisseur}")
    public SituationFournisseurDto getSituationFournisseur(@PathVariable String uuidFournisseur) {
        return situationComptableService.getSituationFournisseur(uuidFournisseur);
    }

    /**
     * Récupère la situation comptable d'un client partenaire
     * @param uuidClientPartenaire L'UUID du client partenaire
     * @return La situation du client partenaire
     */
    @GetMapping("/clientPartenaire/{uuidClientPartenaire}")
    public SituationClientPartenaireDto getSituationClientPartenaire(@PathVariable String uuidClientPartenaire) {
        return situationComptableService.getSituationClientPartenaire(uuidClientPartenaire);
    }

    /**
     * Récupère toutes les situations des fournisseurs
     * @return Liste des situations fournisseurs
     */
    @GetMapping("/fournisseurs")
    public List<SituationFournisseurDto> getSituationsFournisseurs() {
        return situationComptableService.getSituationsFournisseurs();
    }

    /**
     * Récupère toutes les situations des clients partenaires
     * @return Liste des situations clients partenaires
     */
    @GetMapping("/clientsPartenaires")
    public List<SituationClientPartenaireDto> getSituationsClientsPartenaires() {
        return situationComptableService.getSituationsClientsPartenaires();
    }
}
