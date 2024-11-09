package org.sid.saranApp.service;

import java.util.List;

import org.sid.saranApp.dto.DomainBoutiqueDto;

public interface DomainBoutiqueService {
	
	DomainBoutiqueDto addDomain(DomainBoutiqueDto domainBoutiqueDto);
	
	DomainBoutiqueDto updateDomain(DomainBoutiqueDto domainBoutiqueDto,String uuid);
	
	DomainBoutiqueDto getDomain(String uuid);
	
	List<DomainBoutiqueDto> listeDomain();
	

}
