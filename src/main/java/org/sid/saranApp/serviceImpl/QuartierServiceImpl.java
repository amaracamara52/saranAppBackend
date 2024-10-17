package org.sid.saranApp.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.sid.saranApp.dto.QuartierDto;
import org.sid.saranApp.mapper.Mapper;
import org.sid.saranApp.model.Boutique;
import org.sid.saranApp.model.Commune;
import org.sid.saranApp.model.Quartier;
import org.sid.saranApp.model.Utilisateur;
import org.sid.saranApp.repository.BoutiqueRepository;
import org.sid.saranApp.repository.CommuneRepository;
import org.sid.saranApp.repository.QuartierRepository;
import org.sid.saranApp.repository.UtilisateurRepository;
import org.sid.saranApp.service.QuartierService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class QuartierServiceImpl implements QuartierService{
	
	@Autowired
	private QuartierRepository quartierRepository;
	@Autowired
	private CommuneRepository communeRepository;
	@Autowired
	private BoutiqueRepository boutiqueRepository;
	@Autowired
	private UtilisateurRepository utilisateurRepository;
	
	 Logger logger = LoggerFactory.getLogger(QuartierServiceImpl.class);
	
	@Override
	public QuartierDto addQuartier(QuartierDto quartierDto) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		logger.info("hello {}",auth.getName());
		// TODO Auto-generated method stub
		Commune commune = communeRepository.findById(quartierDto.getUuidCommune()).orElseThrow(null);
		Quartier quartier = new Quartier();
		Utilisateur utilisateur = utilisateurRepository.findByEmail(auth.getName()).orElseThrow(null);
		quartier.setLibelle(quartierDto.getLibelle());
		quartier.setCommune(commune);
		quartier.setBoutique(utilisateur.getBoutique());
		quartier.setUtilisateur(utilisateur);
		Quartier quartierSave = quartierRepository.save(quartier);
		return Mapper.toQuartierDto(quartierSave);
	}

	@Override
	public QuartierDto updateQuartier(QuartierDto quartierDto, String uuid) {
		// TODO Auto-generated method stub
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Utilisateur utilisateur = utilisateurRepository.findByEmail(auth.getName()).orElseThrow(null);
		Commune commune = communeRepository.findById(quartierDto.getUuidCommune()).orElseThrow(null);
		Quartier quartier = quartierRepository.findById(uuid).orElseThrow(null);
		quartier.setLibelle(quartierDto.getLibelle());
		quartier.setCommune(commune);
		quartier.setBoutique(utilisateur.getBoutique());
		quartier.setUtilisateur(utilisateur);
		Quartier quartierSave = quartierRepository.save(quartier);
		return Mapper.toQuartierDto(quartierSave);
	}

	@Override
	public List<QuartierDto> findAll() {
		// TODO Auto-generated method stub
		List<Quartier> quartiers = quartierRepository.findAll();
		List<QuartierDto> quartierDtos = new ArrayList<QuartierDto>();
		quartiers.forEach(quartier -> quartierDtos.add(Mapper.toQuartierDto(quartier)));
		return quartierDtos;
	}

	@Override
	public QuartierDto getQuartier(String uuid) {
		// TODO Auto-generated method stub
		Quartier quartier = quartierRepository.findById(uuid).orElseThrow(null);
		return Mapper.toQuartierDto(quartier);
	}

	@Override
	public void deleteQuartier(String uuid) {
		// TODO Auto-generated method stub
		Quartier quartier = quartierRepository.findById(uuid).orElseThrow(null);
		quartierRepository.delete(quartier);
		
	}

}
