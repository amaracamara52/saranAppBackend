package org.sid.saranApp.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.sid.saranApp.dto.DomainBoutiqueDto;
import org.sid.saranApp.mapper.Mapper;
import org.sid.saranApp.model.DomainBoutique;
import org.sid.saranApp.repository.DomainBoutiqueRepository;
import org.sid.saranApp.service.DomainBoutiqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DomainServiceImpl implements DomainBoutiqueService {

	@Autowired
	private DomainBoutiqueRepository domainBoutiqueRepository;
	
	@Override
	public DomainBoutiqueDto addDomain(DomainBoutiqueDto domainBoutiqueDto) {
		// TODO Auto-generated method stub
		DomainBoutique domainBoutique = new DomainBoutique();
		domainBoutique.setCode(domainBoutiqueDto.getCode());
		domainBoutique.setLibelle(domainBoutiqueDto.getLibelle());
		domainBoutique = domainBoutiqueRepository.save(domainBoutique);
		return Mapper.toDomainBoutiqueDto(domainBoutique);
	}

	@Override
	public DomainBoutiqueDto updateDomain(DomainBoutiqueDto domainBoutiqueDto, String uuid) {
		// TODO Auto-generated method stub
		DomainBoutique domainBoutique = domainBoutiqueRepository.findById(uuid).orElseThrow(null);
		domainBoutique.setCode(domainBoutiqueDto.getCode());
		domainBoutique.setLibelle(domainBoutiqueDto.getLibelle());
		domainBoutique = domainBoutiqueRepository.save(domainBoutique);
		return Mapper.toDomainBoutiqueDto(domainBoutique);
	}

	@Override
	public DomainBoutiqueDto getDomain(String uuid) {
		// TODO Auto-generated method stub
		DomainBoutique domainBoutique = domainBoutiqueRepository.findById(uuid).orElseThrow(null);
		return Mapper.toDomainBoutiqueDto(domainBoutique);
	}

	@Override
	public List<DomainBoutiqueDto> listeDomain() {
		// TODO Auto-generated method stub
		List<DomainBoutique> domainBoutiques = domainBoutiqueRepository.findAll();
		List<DomainBoutiqueDto> domainBoutiqueDtos = new ArrayList<DomainBoutiqueDto>();
		domainBoutiques.forEach(domain -> domainBoutiqueDtos.add(Mapper.toDomainBoutiqueDto(domain)));
		return domainBoutiqueDtos;
	}

}
