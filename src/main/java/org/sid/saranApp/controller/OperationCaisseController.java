package org.sid.saranApp.controller;

import org.sid.saranApp.dto.OperationCaisseDto;
import org.sid.saranApp.dto.SituationCaisseDto;
import org.sid.saranApp.service.OperationCaisseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur pour la gestion des opérations de caisse
 */
@CrossOrigin(origins = "*")
@RequestMapping("/api/operationCaisse")
@RestController
public class OperationCaisseController {

    @Autowired
    private OperationCaisseService operationCaisseService;

    /**
     * Enregistre une opération de caisse
     * @param operationDto Les données de l'opération
     * @return L'opération enregistrée
     */
    @PostMapping
    public OperationCaisseDto enregistrerOperation(@RequestBody OperationCaisseDto operationDto) {
        return operationCaisseService.enregistrerOperation(operationDto);
    }

    /**
     * Récupère toutes les opérations de la caisse actuelle
     * @return Liste des opérations
     */
    @GetMapping("/caisseActuelle")
    public List<OperationCaisseDto> getOperationsCaisseActuelle() {
        return operationCaisseService.getOperationsCaisseActuelle();
    }

    /**
     * Récupère les opérations d'une caisse journalière
     * @param uuidCaisse L'UUID de la caisse
     * @return Liste des opérations
     */
    @GetMapping("/caisse/{uuidCaisse}")
    public List<OperationCaisseDto> getOperationsByCaisse(@PathVariable String uuidCaisse) {
        return operationCaisseService.getOperationsByCaisse(uuidCaisse);
    }

    /**
     * Récupère les opérations d'un client partenaire
     * @param uuidClientPartenaire L'UUID du client partenaire
     * @return Liste des opérations
     */
    @GetMapping("/clientPartenaire/{uuidClientPartenaire}")
    public List<OperationCaisseDto> getOperationsByClientPartenaire(@PathVariable String uuidClientPartenaire) {
        return operationCaisseService.getOperationsByClientPartenaire(uuidClientPartenaire);
    }

    /**
     * Récupère les opérations d'un fournisseur
     * @param uuidFournisseur L'UUID du fournisseur
     * @return Liste des opérations
     */
    @GetMapping("/fournisseur/{uuidFournisseur}")
    public List<OperationCaisseDto> getOperationsByFournisseur(@PathVariable String uuidFournisseur) {
        return operationCaisseService.getOperationsByFournisseur(uuidFournisseur);
    }

    /**
     * Récupère la situation de caisse actuelle
     * @return La situation de caisse
     */
    @GetMapping("/situation")
    public SituationCaisseDto getSituationCaisse() {
        return operationCaisseService.getSituationCaisse();
    }

    /**
     * Récupère la situation de caisse d'un client partenaire
     * @param uuidClientPartenaire L'UUID du client partenaire
     * @return La situation de caisse du client
     */
    @GetMapping("/situation/clientPartenaire/{uuidClientPartenaire}")
    public SituationCaisseDto getSituationCaisseClientPartenaire(@PathVariable String uuidClientPartenaire) {
        return operationCaisseService.getSituationCaisseClientPartenaire(uuidClientPartenaire);
    }

    /**
     * Récupère la situation de caisse d'un fournisseur
     * @param uuidFournisseur L'UUID du fournisseur
     * @return La situation de caisse du fournisseur
     */
    @GetMapping("/situation/fournisseur/{uuidFournisseur}")
    public SituationCaisseDto getSituationCaisseFournisseur(@PathVariable String uuidFournisseur) {
        return operationCaisseService.getSituationCaisseFournisseur(uuidFournisseur);
    }
}
