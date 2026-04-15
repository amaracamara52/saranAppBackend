package org.sid.saranApp.serviceImpl;

import org.sid.saranApp.dto.ReductionDto;
import org.sid.saranApp.mapper.ReductionMapper;
import org.sid.saranApp.model.Boutique;
import org.sid.saranApp.model.Reduction;
import org.sid.saranApp.repository.BoutiqueRepository;
import org.sid.saranApp.repository.ReductionRepository;
import org.sid.saranApp.service.ReductionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ReductionServiceImpl implements ReductionService {
    
    @Autowired
    private ReductionRepository reductionRepository;
    
    @Autowired
    private BoutiqueRepository boutiqueRepository;
    
    @Autowired
    private ReductionMapper reductionMapper;
    
    @Override
    public ReductionDto addReduction(ReductionDto reductionDto, String boutiqueUuid) {
        // Vérifier que la boutique existe
        Optional<Boutique> boutiqueOpt = boutiqueRepository.findById(boutiqueUuid);
        if (!boutiqueOpt.isPresent()) {
            throw new RuntimeException("Boutique non trouvée avec l'UUID: " + boutiqueUuid);
        }
        
        Boutique boutique = boutiqueOpt.get();
        Reduction reduction = reductionMapper.toEntity(reductionDto);
        reduction.setBoutique(boutique);
        
        // Validation des dates
        if (reduction.getDateDebut() != null && reduction.getDateFin() != null) {
            if (reduction.getDateDebut().after(reduction.getDateFin())) {
                throw new RuntimeException("La date de début ne peut pas être après la date de fin");
            }
        }
        
        // Validation du taux de remise
        if (reduction.getTauxRemise() < 0 || reduction.getTauxRemise() > 100) {
            throw new RuntimeException("Le taux de remise doit être entre 0 et 100%");
        }
        
        Reduction savedReduction = reductionRepository.save(reduction);
        return reductionMapper.toDto(savedReduction);
    }
    
    @Override
    public ReductionDto updateReduction(ReductionDto reductionDto, String uuid) {
        Optional<Reduction> reductionOpt = reductionRepository.findById(uuid);
        if (!reductionOpt.isPresent()) {
            throw new RuntimeException("Réduction non trouvée avec l'UUID: " + uuid);
        }
        
        Reduction existingReduction = reductionOpt.get();
        
        // Mettre à jour les champs
        existingReduction.setLibelle(reductionDto.getLibelle());
        existingReduction.setTauxRemise(reductionDto.getTauxRemise());
        existingReduction.setMontantRemise(reductionDto.getMontantRemise());
        existingReduction.setQuantite(reductionDto.getQuantite());
        existingReduction.setCategorie(reductionDto.getCategorie());
        existingReduction.setPay(reductionDto.isPay());
        existingReduction.setPercent(reductionDto.getPercent());
        existingReduction.setActive(reductionDto.isActive());
        existingReduction.setDateDebut(reductionDto.getDateDebut());
        existingReduction.setDateFin(reductionDto.getDateFin());
        
        // Validation des dates
        if (existingReduction.getDateDebut() != null && existingReduction.getDateFin() != null) {
            if (existingReduction.getDateDebut().after(existingReduction.getDateFin())) {
                throw new RuntimeException("La date de début ne peut pas être après la date de fin");
            }
        }
        
        Reduction updatedReduction = reductionRepository.save(existingReduction);
        return reductionMapper.toDto(updatedReduction);
    }
    
    @Override
    public void deleteReduction(String uuid) {
        if (!reductionRepository.existsById(uuid)) {
            throw new RuntimeException("Réduction non trouvée avec l'UUID: " + uuid);
        }
        reductionRepository.deleteById(uuid);
    }
    
    @Override
    public ReductionDto getReduction(String uuid) {
        Optional<Reduction> reductionOpt = reductionRepository.findById(uuid);
        if (!reductionOpt.isPresent()) {
            throw new RuntimeException("Réduction non trouvée avec l'UUID: " + uuid);
        }
        return reductionMapper.toDto(reductionOpt.get());
    }
    
    @Override
    public List<ReductionDto> findAll() {
        List<Reduction> reductions = reductionRepository.findAll();
        return reductionMapper.toDtoList(reductions);
    }
    
    @Override
    public List<ReductionDto> findByBoutique(String boutiqueUuid) {
        List<Reduction> reductions = reductionRepository.findByBoutiqueUuid(boutiqueUuid);
        return reductionMapper.toDtoList(reductions);
    }
    
    @Override
    public List<ReductionDto> findActiveByBoutique(String boutiqueUuid) {
        List<Reduction> reductions = reductionRepository.findByBoutiqueUuidAndIsActiveTrue(boutiqueUuid);
        return reductionMapper.toDtoList(reductions);
    }
    
    @Override
    public List<ReductionDto> findByBoutiqueAndCategorie(String boutiqueUuid, String categorie) {
        List<Reduction> reductions = reductionRepository.findByBoutiqueUuidAndCategorie(boutiqueUuid, categorie);
        return reductionMapper.toDtoList(reductions);
    }
    
    @Override
    public List<ReductionDto> findValidReductions(String boutiqueUuid) {
        List<Reduction> reductions = reductionRepository.findValidReductions(boutiqueUuid, new Date());
        return reductionMapper.toDtoList(reductions);
    }
    
    @Override
    public List<ReductionDto> findByBoutiqueAndTauxRemiseMin(String boutiqueUuid, double tauxRemise) {
        List<Reduction> reductions = reductionRepository.findByBoutiqueUuidAndTauxRemiseGreaterThanEqual(boutiqueUuid, tauxRemise);
        return reductionMapper.toDtoList(reductions);
    }
    
    @Override
    public List<ReductionDto> findByBoutiqueAndLibelle(String boutiqueUuid, String libelle) {
        List<Reduction> reductions = reductionRepository.findByBoutiqueUuidAndLibelleContaining(boutiqueUuid, libelle);
        return reductionMapper.toDtoList(reductions);
    }
    
    @Override
    public double calculateRemise(String reductionUuid, double montantOriginal) {
        Optional<Reduction> reductionOpt = reductionRepository.findById(reductionUuid);
        if (!reductionOpt.isPresent()) {
            return 0.0;
        }
        
        Reduction reduction = reductionOpt.get();
        
        // Vérifier si la réduction est valide
        if (!isReductionValid(reductionUuid)) {
            return 0.0;
        }
        
        // Calculer la remise selon le type
        if (reduction.getTauxRemise() > 0) {
            // Remise en pourcentage
            return (montantOriginal * reduction.getTauxRemise()) / 100.0;
        } else if (reduction.getMontantRemise() > 0) {
            // Remise en montant fixe
            return Math.min(reduction.getMontantRemise(), montantOriginal);
        }
        
        return 0.0;
    }
    
    @Override
    public List<ReductionDto> findApplicableReductions(String boutiqueUuid, double montant, String categorie) {
        List<Reduction> validReductions = reductionRepository.findValidReductions(boutiqueUuid, new Date());
        
        return validReductions.stream()
                .filter(reduction -> reduction.isActive())
                .filter(reduction -> categorie == null || categorie.equals(reduction.getCategorie()))
                .filter(reduction -> montant >= reduction.getQuantite()) // Vérifier la quantité minimale
                .map(reductionMapper::toDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public boolean isReductionValid(String reductionUuid) {
        Optional<Reduction> reductionOpt = reductionRepository.findById(reductionUuid);
        if (!reductionOpt.isPresent()) {
            return false;
        }
        
        Reduction reduction = reductionOpt.get();
        Date currentDate = new Date();
        
        // Vérifier si la réduction est active
        if (!reduction.isActive()) {
            return false;
        }
        
        // Vérifier les dates de validité
        if (reduction.getDateDebut() != null && currentDate.before(reduction.getDateDebut())) {
            return false;
        }
        
        if (reduction.getDateFin() != null && currentDate.after(reduction.getDateFin())) {
            return false;
        }
        
        return true;
    }
    
    @Override
    public boolean isReductionActive(String reductionUuid) {
        Optional<Reduction> reductionOpt = reductionRepository.findById(reductionUuid);
        return reductionOpt.isPresent() && reductionOpt.get().isActive();
    }
} 