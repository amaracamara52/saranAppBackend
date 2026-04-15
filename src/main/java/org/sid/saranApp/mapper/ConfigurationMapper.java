package org.sid.saranApp.mapper;

import org.sid.saranApp.dto.DomainCategorieDto;
import org.sid.saranApp.dto.DomainCategorieParamDto;
import org.sid.saranApp.dto.DomainDto;
import org.sid.saranApp.model.Domain;
import org.sid.saranApp.model.DomainCategorie;
import org.sid.saranApp.model.DomainCategorieParam;

public class ConfigurationMapper {
	
	
	public static DomainDto toDomainDto(Domain domain) {
		DomainDto dto = new DomainDto();
		dto.setDatePeremption(domain.isDatePeremption());
		dto.setDescription(domain.getDescription());
		dto.setLibelle(domain.getLibelle());
		dto.setUuid(domain.getUuid());
		dto.setType(domain.getType());
		return dto;
	}
	
	
	public static DomainCategorieDto toDomainCategorieDto(DomainCategorie domainCategorie) {
		DomainCategorieDto dto = new DomainCategorieDto();
		dto.setDatePeremptionDomain(domainCategorie.getDomaine().isDatePeremption());
		dto.setDescription(domainCategorie.getDescription());
		dto.setDescriptionDomain(domainCategorie.getDomaine().getDescription());
		dto.setLibelle(domainCategorie.getLibelle());
		dto.setLibelleDomain(domainCategorie.getDomaine().getLibelle());
		dto.setUuid(domainCategorie.getUuid());
		dto.setUuidDomain(domainCategorie.getDomaine().getUuid());
		dto.setCheck(false);	
		return dto;
	}
	
	public static DomainCategorieParamDto toDomainCategorieParamDto(DomainCategorieParam domainCategorieParam) {
		DomainCategorieParamDto dto = new DomainCategorieParamDto();
		dto.setDatePeremptionDomain(domainCategorieParam.getDomainCategorie().getDomaine().isDatePeremption());
		dto.setDescription(domainCategorieParam.getDescription());
		dto.setDescriptionDomain(domainCategorieParam.getDomainCategorie().getDomaine().getDescription());
		dto.setLibelle(domainCategorieParam.getLibelle());
		dto.setLibelleDomain(domainCategorieParam.getDomainCategorie().getDomaine().getLibelle());
		dto.setLibelleDomainCategorie(domainCategorieParam.getDomainCategorie().getLibelle());
		dto.setUuidDomain(domainCategorieParam.getDomainCategorie().getDomaine().getUuid());
		dto.setUuidDomainCategorie(domainCategorieParam.getDomainCategorie().getUuid());
		return dto;
	}

}
