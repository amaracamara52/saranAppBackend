package org.sid.saranApp.mapper;

import org.sid.saranApp.dto.ReductionDto;
import org.sid.saranApp.model.Reduction;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReductionMapper {
    
    public ReductionDto toDto(Reduction reduction) {
        if (reduction == null) {
            return null;
        }
        
        ReductionDto dto = new ReductionDto();
        dto.setUuid(reduction.getUuid());
        dto.setLibelle(reduction.getLibelle());
        dto.setTauxRemise(reduction.getTauxRemise());
        dto.setMontantRemise(reduction.getMontantRemise());
        dto.setQuantite(reduction.getQuantite());
        dto.setCategorie(reduction.getCategorie());
        dto.setPay(reduction.isPay());
        dto.setPercent(reduction.getPercent());
        dto.setActive(reduction.isActive());
        dto.setDateDebut(reduction.getDateDebut());
        dto.setDateFin(reduction.getDateFin());
        
        if (reduction.getBoutique() != null) {
            dto.setBoutiqueUuid(reduction.getBoutique().getUuid());
            dto.setBoutiqueLibelle(reduction.getBoutique().getLibelleBoutique());
        }
        
        return dto;
    }
    
    public Reduction toEntity(ReductionDto dto) {
        if (dto == null) {
            return null;
        }
        
        Reduction reduction = new Reduction();
        reduction.setUuid(dto.getUuid());
        reduction.setLibelle(dto.getLibelle());
        reduction.setTauxRemise(dto.getTauxRemise());
        reduction.setMontantRemise(dto.getMontantRemise());
        reduction.setQuantite(dto.getQuantite());
        reduction.setCategorie(dto.getCategorie());
        reduction.setPay(dto.isPay());
        reduction.setPercent(dto.getPercent());
        reduction.setActive(dto.isActive());
        reduction.setDateDebut(dto.getDateDebut());
        reduction.setDateFin(dto.getDateFin());
        
        return reduction;
    }
    
    public List<ReductionDto> toDtoList(List<Reduction> reductions) {
        if (reductions == null) {
            return null;
        }
        
        return reductions.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
    
    public List<Reduction> toEntityList(List<ReductionDto> dtos) {
        if (dtos == null) {
            return null;
        }
        
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
} 