package org.sid.saranApp.serviceImpl;

import org.sid.saranApp.dto.DomainCategorieDto;
import org.sid.saranApp.dto.DomainCategorieParamDto;
import org.sid.saranApp.dto.DomainDto;
import org.sid.saranApp.mapper.ConfigurationMapper;
import org.sid.saranApp.model.*;
import org.sid.saranApp.repository.*;
import org.sid.saranApp.service.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConfigurationServiceImpl implements ConfigurationService {
	
	@Autowired
	private DomainCategorieRepository domainCategorieRepository;
	@Autowired
	private DomainRepository domainRepository;
	@Autowired
	private DomainCategorieParamRepository domainCategorieParamRepository;
	@Autowired
	private CaracteristiqueArticleRepository caracteristiqueArticleRepository;
	@Autowired
	private CategorieRepository categorieRepository;

	@Override
	public DomainDto addDomain(DomainDto domainDto) {
		// TODO Auto-generated method stub
		Domain domain = new Domain();
		domain.setDescription(domainDto.getDescription());
		domain.setLibelle(domainDto.getLibelle());
		domain.setDatePeremption(domainDto.isDatePeremption());
		domain.setType(domainDto.getType());
		domain = domainRepository.save(domain);
		return ConfigurationMapper.toDomainDto(domain);
	}

	@Override
	public DomainDto updateDomain(DomainDto domainDto, String uuid) {
		// TODO Auto-generated method stub
		Domain domain = domainRepository.findById(uuid).orElseThrow(null);
		domain.setDescription(domainDto.getDescription());
		domain.setLibelle(domainDto.getLibelle());
		domain.setType(domainDto.getType());
		domain.setDatePeremption(domainDto.isDatePeremption());
		domain = domainRepository.save(domain);
		return ConfigurationMapper.toDomainDto(domain);
	}

	@Override
	public DomainDto getDomain(String uuid) {
		// TODO Auto-generated method stub
		Domain domain = domainRepository.findById(uuid).orElseThrow(null);
		return ConfigurationMapper.toDomainDto(domain);
	}

	@Override
	public List<DomainDto> listeDomains() {
		// TODO Auto-generated method stub
		List<Domain> domains = domainRepository.findListeWithoutATNS();
		List<DomainDto> domainDtos = new ArrayList<DomainDto>();
		domains.forEach(domain -> domainDtos.add(ConfigurationMapper.toDomainDto(domain)));
		return domainDtos;
	}

	@Override
	public DomainCategorieDto addDomainCategorie(DomainCategorieDto domainCategorieDto) {
		// TODO Auto-generated method stub
		Domain domain = domainRepository.findById(domainCategorieDto.getUuidDomain()).orElseThrow(null);
		DomainCategorie domainCategorie = new DomainCategorie();
		domainCategorie.setDescription(domainCategorieDto.getDescription());
		domainCategorie.setDomaine(domain);
		domainCategorie.setLibelle(domainCategorieDto.getLibelle());
		domainCategorie.setParam(domainCategorieDto.isParam());
		domainCategorie = domainCategorieRepository.save(domainCategorie);
		
		return ConfigurationMapper.toDomainCategorieDto(domainCategorie);
	}

	@Override
	public DomainCategorieDto updateDomainCategorie(DomainCategorieDto domainCategorieDto, String uuid) {
		// TODO Auto-generated method stub
		Domain domain = domainRepository.findById(domainCategorieDto.getUuidDomain()).orElseThrow(null);
		DomainCategorie domainCategorie = domainCategorieRepository.findById(uuid).orElseThrow(null);
		domainCategorie.setDescription(domainCategorieDto.getDescription());
		domainCategorie.setDomaine(domain);
		domainCategorie.setLibelle(domainCategorieDto.getLibelle());
		domainCategorie.setParam(domainCategorieDto.isParam());
		domainCategorie = domainCategorieRepository.save(domainCategorie);
		
		return ConfigurationMapper.toDomainCategorieDto(domainCategorie);
	}

	@Override
	public DomainCategorieDto getDomainCategorie(String uuid) {
		// TODO Auto-generated method stub
		DomainCategorie domainCategorie = domainCategorieRepository.findById(uuid).orElseThrow(null);
		return ConfigurationMapper.toDomainCategorieDto(domainCategorie);
	}

	@Override
	public List<DomainCategorieDto> listeDomainCategories() {
		// TODO Auto-generated method stub
		List<DomainCategorieDto> domainCategorieDtos = new ArrayList<DomainCategorieDto>();
		List<DomainCategorie> domainCategories = domainCategorieRepository.findAll();
		
		domainCategories.forEach(domain -> domainCategorieDtos.add(ConfigurationMapper.toDomainCategorieDto(domain)));
		return domainCategorieDtos;
	}

	@Override
	public DomainCategorieParamDto addDomainCategorieParam(DomainCategorieParamDto domainCategorieParamDto) {
		// TODO Auto-generated method stub
		DomainCategorieParam domainCategorieParam = new  DomainCategorieParam();
		DomainCategorie domainCategorie = domainCategorieRepository.findById(domainCategorieParamDto.getUuidDomainCategorie()).orElseThrow(null);
		
		domainCategorieParam.setDescription(domainCategorieParamDto.getDescription());
		domainCategorieParam.setDomainCategorie(domainCategorie);
		domainCategorieParam.setLibelle(domainCategorieParamDto.getLibelle());
		domainCategorieParam = domainCategorieParamRepository.save(domainCategorieParam);

		Categorie categorie = categorieRepository.getCategorieByLibelle(domainCategorie.getLibelle());
		CaracteristiqueArticle caracteristiqueArticle = new CaracteristiqueArticle();
		caracteristiqueArticle.setCategorie(categorie);
		caracteristiqueArticle.setLibelle(domainCategorieParam.getLibelle());
		caracteristiqueArticle = caracteristiqueArticleRepository.save(caracteristiqueArticle);
		
		return ConfigurationMapper.toDomainCategorieParamDto(domainCategorieParam);
	}

	@Override
	public DomainCategorieParamDto updateDomainCategorieParam(DomainCategorieParamDto domainCategorieParamDto, String uuid) {
		// TODO Auto-generated method stub
		DomainCategorieParam domainCategorieParam = domainCategorieParamRepository.findById(uuid).orElseThrow(null);
		DomainCategorie domainCategorie = domainCategorieRepository.findById(domainCategorieParamDto.getUuidDomainCategorie()).orElseThrow(null);


		domainCategorieParam.setDescription(domainCategorieParamDto.getDescription());
		domainCategorieParam.setDomainCategorie(domainCategorie);
		domainCategorieParam.setLibelle(domainCategorieParamDto.getLibelle());
		domainCategorieParam = domainCategorieParamRepository.save(domainCategorieParam);


		
		return ConfigurationMapper.toDomainCategorieParamDto(domainCategorieParam);
	}

	@Override
	public DomainCategorieParamDto getDomainCategorieParam(String uuid) {
		// TODO Auto-generated method stub
		DomainCategorieParam domainCategorieParam = domainCategorieParamRepository.findById(uuid).orElseThrow(null);
		return ConfigurationMapper.toDomainCategorieParamDto(domainCategorieParam);
	}

	@Override
	public List<DomainCategorieParamDto> listeDomainCategorieParams() {
		// TODO Auto-generated method stub
		List<DomainCategorieParamDto> domainCategorieParamDtos = new ArrayList<DomainCategorieParamDto>();
		List<DomainCategorieParam> domainCategorieParams = domainCategorieParamRepository.findAll();
		domainCategorieParams.forEach(domain -> domainCategorieParamDtos.add(ConfigurationMapper.toDomainCategorieParamDto(domain)));
		return domainCategorieParamDtos;
	}

	@Override
	public List<DomainCategorieDto> listeDomainCategorieByDomain(String uuidDomain) {
		// TODO Auto-generated method stub
		List<DomainCategorieDto> domainCategorieDtos = new ArrayList<DomainCategorieDto>();
		List<DomainCategorie> domainCategories = domainCategorieRepository.listeDomainCategorieByDomain(uuidDomain);
		
		domainCategories.forEach(domain -> domainCategorieDtos.add(ConfigurationMapper.toDomainCategorieDto(domain)));
		return domainCategorieDtos;
	}

	@Override
	public List<DomainCategorieParamDto> listeDomainCategorieParamByDomainCategorie(String uuidDomainCategorie) {
		// TODO Auto-generated method stub
		List<DomainCategorieParamDto> domainCategorieParamDtos = new ArrayList<DomainCategorieParamDto>();
		List<DomainCategorieParam> domainCategorieParams = domainCategorieParamRepository.listeDomainCategorieParamByDomainCategorie(uuidDomainCategorie);
		domainCategorieParams.forEach(domain -> domainCategorieParamDtos.add(ConfigurationMapper.toDomainCategorieParamDto(domain)));
		return domainCategorieParamDtos;
	}

}
