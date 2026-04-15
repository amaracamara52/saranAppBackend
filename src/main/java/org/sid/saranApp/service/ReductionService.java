package org.sid.saranApp.service;

import org.sid.saranApp.dto.ReductionDto;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface ReductionService {
    
    // CRUD de base
    ReductionDto addReduction(ReductionDto reductionDto, String boutiqueUuid);
    ReductionDto updateReduction(ReductionDto reductionDto, String uuid);
    void deleteReduction(String uuid);
    ReductionDto getReduction(String uuid);
    List<ReductionDto> findAll();
    
    // Méthodes spécifiques par boutique
    List<ReductionDto> findByBoutique(String boutiqueUuid);
    List<ReductionDto> findActiveByBoutique(String boutiqueUuid);
    List<ReductionDto> findByBoutiqueAndCategorie(String boutiqueUuid, String categorie);
    
    // Méthodes de recherche avancées
    List<ReductionDto> findValidReductions(String boutiqueUuid);
    List<ReductionDto> findByBoutiqueAndTauxRemiseMin(String boutiqueUuid, double tauxRemise);
    List<ReductionDto> findByBoutiqueAndLibelle(String boutiqueUuid, String libelle);
    
    // Méthodes de calcul
    double calculateRemise(String reductionUuid, double montantOriginal);
    List<ReductionDto> findApplicableReductions(String boutiqueUuid, double montant, String categorie);
    
    // Méthodes de validation
    boolean isReductionValid(String reductionUuid);
    boolean isReductionActive(String reductionUuid);
} 