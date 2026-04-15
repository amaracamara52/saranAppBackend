package org.sid.saranApp.controller;

import org.sid.saranApp.dto.CaisseJournaliereDto;
import org.sid.saranApp.service.CaisseJournaliereService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/caisse")
@CrossOrigin(origins = "*")
public class CaisseController {

    private final CaisseJournaliereService caisseService;

    public CaisseController(CaisseJournaliereService caisseService) {
        this.caisseService = caisseService;
    }

    @PostMapping("/ouvrir")
    public ResponseEntity<CaisseJournaliereDto> ouvrirCaisse(@RequestParam BigDecimal soldeOuverture) {
        CaisseJournaliereDto caisse = caisseService.ouvrirCaisse(soldeOuverture);
        return new ResponseEntity<>(caisse, HttpStatus.CREATED);
    }

    @PutMapping("/fermer")
    public ResponseEntity<CaisseJournaliereDto> fermerCaisse(@RequestParam BigDecimal soldeFermeture) {
        CaisseJournaliereDto caisse = caisseService.fermerCaisse(soldeFermeture);
        return ResponseEntity.ok(caisse);
    }

    @GetMapping("/actuelle")
    public ResponseEntity<CaisseJournaliereDto> getCaisseActuelle() {
        CaisseJournaliereDto caisse = caisseService.getCaisseActuelle();
        return ResponseEntity.ok(caisse);
    }

    @GetMapping
    public List<CaisseJournaliereDto> listes(){
        return caisseService.listes();
    }
}
