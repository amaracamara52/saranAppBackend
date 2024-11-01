package org.sid.saranApp.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.sid.saranApp.dto.PageDataDto;
import org.sid.saranApp.dto.ParametreDto;
import org.sid.saranApp.mapper.Mapper;
import org.sid.saranApp.model.Boutique;
import org.sid.saranApp.model.Parametre;
import org.sid.saranApp.model.Produit;
import org.sid.saranApp.repository.BoutiqueRepository;
import org.sid.saranApp.repository.ParametreRepository;
import org.sid.saranApp.repository.ProduitRepository;
import org.sid.saranApp.service.ParametreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ParametreServiceImpl implements ParametreService {
	
	@Autowired
	private ParametreRepository parametreRepository;
	@Autowired
	private BoutiqueRepository boutiqueRepository;
	@Autowired
	private ProduitRepository produitRepository;

	 Logger logger = LoggerFactory.getLogger(ParametreServiceImpl.class);
	
	@Override
	public ParametreDto addParametre(ParametreDto parametreDto) {
		// TODO Auto-generated method stub
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		logger.info("hello {}",auth.getName());
		Boutique boutique = boutiqueRepository.findById(parametreDto.getUuidBoutique()).orElseThrow(null);
		Produit produit = produitRepository.findById(parametreDto.getUuidProduit()).orElseThrow(null);
		Parametre parametre = new Parametre();
		parametre.setLibelle(parametreDto.getLibelle());
		parametre.setDateDebut(parametreDto.getDateDebut());
		parametre.setDateFin(parametreDto.getDateFin());
		parametre.setDescription(parametreDto.getDescription());
		parametre.setTypeParametre(parametreDto.getTypeParametre());
		parametre.setValeur(parametreDto.getValeur());
		parametre.setBoutique(boutique);
		parametre.setProduit(produit);
		Parametre parametreSave = parametreRepository.save(parametre);
		return Mapper.toParametreDto(parametreSave);
	}

	@Override
	public ParametreDto updateParametre(ParametreDto parametreDto, String uuid) {
		// TODO Auto-generated method stub
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Boutique boutique = boutiqueRepository.findById(parametreDto.getUuidBoutique()).orElseThrow(null);
		Produit produit = produitRepository.findById(parametreDto.getUuidProduit()).orElseThrow(null);
		Parametre parametre = parametreRepository.findById(uuid).orElseThrow(null);
		parametre.setLibelle(parametreDto.getLibelle());
		parametre.setDateDebut(parametreDto.getDateDebut());
		parametre.setDateFin(parametreDto.getDateFin());
		parametre.setDescription(parametreDto.getDescription());
		parametre.setTypeParametre(parametreDto.getTypeParametre());
		parametre.setValeur(parametreDto.getValeur());
		parametre.setBoutique(boutique);
		parametre.setProduit(produit);
		Parametre parametreSave = parametreRepository.save(parametre);
		return Mapper.toParametreDto(parametre);
	}

	@Override
	public List<ParametreDto> findAll() {
		// TODO Auto-generated method stub
		List<Parametre> parametres = parametreRepository.findAll();
		List<ParametreDto> parametreDtos = new ArrayList<ParametreDto>();
		parametres.forEach(parametre -> parametreDtos.add(Mapper.toParametreDto(parametre)));
		return parametreDtos;
	}

	@Override
	public ParametreDto getParametre(String uuid) {
		Parametre parametre = parametreRepository.findById(uuid).orElseThrow(null);
		// TODO Auto-generated method stub
		return Mapper.toParametreDto(parametre)
				;
	}

	@Override
	public void deleteParametre(String uuid) {
		// TODO Auto-generated method stub
		Parametre parametre = parametreRepository.findById(uuid).orElseThrow(null);
		parametreRepository.delete(parametre);
		
	}

	@Override
	public PageDataDto<ParametreDto> listeParametres(int page, int size, String key) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	

}
