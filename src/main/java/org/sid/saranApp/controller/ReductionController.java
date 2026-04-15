package org.sid.saranApp.controller;

import org.sid.saranApp.dto.ReductionDto;
import org.sid.saranApp.service.ReductionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class ReductionController {
    
    @Autowired
    private ReductionService reductionService;
    
    // CRUD de base
    @PostMapping("/reduction/{boutiqueUuid}")
    public ResponseEntity<ReductionDto> addReduction(@RequestBody ReductionDto reductionDto, 
                                                    @PathVariable String boutiqueUuid) {
        try {
            ReductionDto savedReduction = reductionService.addReduction(reductionDto, boutiqueUuid);
            return ResponseEntity.ok(savedReduction);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PutMapping("/reduction/{uuid}")
    public ResponseEntity<ReductionDto> updateReduction(@RequestBody ReductionDto reductionDto, 
                                                       @PathVariable String uuid) {
        try {
            ReductionDto updatedReduction = reductionService.updateReduction(reductionDto, uuid);
            return ResponseEntity.ok(updatedReduction);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @DeleteMapping("/reduction/{uuid}")
    public ResponseEntity<Void> deleteReduction(@PathVariable String uuid) {
        try {
            reductionService.deleteReduction(uuid);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/reduction/{uuid}")
    public ResponseEntity<ReductionDto> getReduction(@PathVariable String uuid) {
        try {
            ReductionDto reduction = reductionService.getReduction(uuid);
            return ResponseEntity.ok(reduction);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/reduction")
    public ResponseEntity<List<ReductionDto>> getAllReductions() {
        List<ReductionDto> reductions = reductionService.findAll();
        return ResponseEntity.ok(reductions);
    }
    
    // Méthodes spécifiques par boutique
    @GetMapping("/reduction/boutique/{boutiqueUuid}")
    public ResponseEntity<List<ReductionDto>> getReductionsByBoutique(@PathVariable String boutiqueUuid) {
        List<ReductionDto> reductions = reductionService.findByBoutique(boutiqueUuid);
        return ResponseEntity.ok(reductions);
    }
    
    @GetMapping("/reduction/boutique/{boutiqueUuid}/active")
    public ResponseEntity<List<ReductionDto>> getActiveReductionsByBoutique(@PathVariable String boutiqueUuid) {
        List<ReductionDto> reductions = reductionService.findActiveByBoutique(boutiqueUuid);
        return ResponseEntity.ok(reductions);
    }
    
    @GetMapping("/reduction/boutique/{boutiqueUuid}/categorie/{categorie}")
    public ResponseEntity<List<ReductionDto>> getReductionsByBoutiqueAndCategorie(
            @PathVariable String boutiqueUuid,
            @PathVariable String categorie) {
        List<ReductionDto> reductions = reductionService.findByBoutiqueAndCategorie(boutiqueUuid, categorie);
        return ResponseEntity.ok(reductions);
    }
    
    // Méthodes de recherche avancées
    @GetMapping("/reduction/boutique/{boutiqueUuid}/valid")
    public ResponseEntity<List<ReductionDto>> getValidReductions(@PathVariable String boutiqueUuid) {
        List<ReductionDto> reductions = reductionService.findValidReductions(boutiqueUuid);
        return ResponseEntity.ok(reductions);
    }
    
    @GetMapping("/reduction/boutique/{boutiqueUuid}/taux-min/{tauxRemise}")
    public ResponseEntity<List<ReductionDto>> getReductionsByTauxRemiseMin(
            @PathVariable String boutiqueUuid,
            @PathVariable double tauxRemise) {
        List<ReductionDto> reductions = reductionService.findByBoutiqueAndTauxRemiseMin(boutiqueUuid, tauxRemise);
        return ResponseEntity.ok(reductions);
    }
    
    @GetMapping("/reduction/boutique/{boutiqueUuid}/search")
    public ResponseEntity<List<ReductionDto>> searchReductionsByLibelle(
            @PathVariable String boutiqueUuid,
            @RequestParam String libelle) {
        List<ReductionDto> reductions = reductionService.findByBoutiqueAndLibelle(boutiqueUuid, libelle);
        return ResponseEntity.ok(reductions);
    }
    
    // Méthodes de calcul
    @PostMapping("/reduction/{reductionUuid}/calculate")
    public ResponseEntity<Double> calculateRemise(@PathVariable String reductionUuid,
                                                 @RequestParam double montantOriginal) {
        try {
            double remise = reductionService.calculateRemise(reductionUuid, montantOriginal);
            return ResponseEntity.ok(remise);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/reduction/boutique/{boutiqueUuid}/applicable")
    public ResponseEntity<List<ReductionDto>> getApplicableReductions(
            @PathVariable String boutiqueUuid,
            @RequestParam double montant,
            @RequestParam(required = false) String categorie) {
        List<ReductionDto> reductions = reductionService.findApplicableReductions(boutiqueUuid, montant, categorie);
        return ResponseEntity.ok(reductions);
    }
    
    // Méthodes de validation
    @GetMapping("/reduction/{reductionUuid}/valid")
    public ResponseEntity<Boolean> isReductionValid(@PathVariable String reductionUuid) {
        boolean isValid = reductionService.isReductionValid(reductionUuid);
        return ResponseEntity.ok(isValid);
    }
    
    @GetMapping("/reduction/{reductionUuid}/active")
    public ResponseEntity<Boolean> isReductionActive(@PathVariable String reductionUuid) {
        boolean isActive = reductionService.isReductionActive(reductionUuid);
        return ResponseEntity.ok(isActive);
    }
} 