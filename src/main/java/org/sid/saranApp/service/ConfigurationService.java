package org.sid.saranApp.service;

import org.sid.saranApp.dto.DomainCategorieDto;
import org.sid.saranApp.dto.DomainCategorieParamDto;
import org.sid.saranApp.dto.DomainDto;

import java.util.List;

public interface ConfigurationService {
	
	DomainDto addDomain(DomainDto domainDto);
	DomainDto updateDomain(DomainDto domainDto,String uuid);
	DomainDto getDomain(String uuid);
	List<DomainDto> listeDomains();
	
	
	DomainCategorieDto addDomainCategorie(DomainCategorieDto domainCategorie);
	DomainCategorieDto updateDomainCategorie(DomainCategorieDto domainCategorie,String uuid);
	DomainCategorieDto getDomainCategorie(String uuid);
	List<DomainCategorieDto> listeDomainCategories();
	List<DomainCategorieDto> listeDomainCategorieByDomain(String uuidDomain);
	
	
	DomainCategorieParamDto addDomainCategorieParam(DomainCategorieParamDto domainCategorieParam);
	DomainCategorieParamDto updateDomainCategorieParam(DomainCategorieParamDto domainCategorieParam,String uuid);
	DomainCategorieParamDto getDomainCategorieParam(String uuid);
	List<DomainCategorieParamDto> listeDomainCategorieParams();
	List<DomainCategorieParamDto> listeDomainCategorieParamByDomainCategorie(String uuidDomainCategorie);
	
	
	

}
