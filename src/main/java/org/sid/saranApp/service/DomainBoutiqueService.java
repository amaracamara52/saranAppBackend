package org.sid.saranApp.service;

import org.sid.saranApp.dto.DomainBoutiqueDto;

import java.util.List;

public interface DomainBoutiqueService {
	
	DomainBoutiqueDto addDomain(DomainBoutiqueDto domainBoutiqueDto);
	
	DomainBoutiqueDto updateDomain(DomainBoutiqueDto domainBoutiqueDto,String uuid);
	
	DomainBoutiqueDto getDomain(String uuid);
	
	List<DomainBoutiqueDto> listeDomain();
	

}
