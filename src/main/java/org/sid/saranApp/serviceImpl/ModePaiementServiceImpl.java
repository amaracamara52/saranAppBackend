package org.sid.saranApp.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.sid.saranApp.dto.ModePaiementDto;
import org.sid.saranApp.dto.PageDataDto;
import org.sid.saranApp.mapper.Mapper;
import org.sid.saranApp.model.Boutique;
import org.sid.saranApp.model.ModePaiement;
import org.sid.saranApp.model.Utilisateur;
import org.sid.saranApp.repository.BoutiqueRepository;
import org.sid.saranApp.repository.ModePaiementRepository;
import org.sid.saranApp.repository.UtilisateurRepository;
import org.sid.saranApp.service.ModePaiementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ModePaiementServiceImpl implements ModePaiementService {
	
	@Autowired
	private ModePaiementRepository modePaiementRepository;
	@Autowired 
	private UtilisateurRepository utilisateurRepository;
	@Autowired
	private BoutiqueRepository boutiqueRepository;

	 Logger logger = LoggerFactory.getLogger(ModePaiementServiceImpl.class);
	
	@Override
	public ModePaiementDto addModePaiement(ModePaiementDto modePaiementDto) {
		// TODO Auto-generated method stub
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		logger.info("hello {}",auth.getName());
		ModePaiement modePaiement = new ModePaiement();
		Boutique boutique = boutiqueRepository.findById(modePaiementDto.getUuidBoutique()).orElseThrow(null);
		Utilisateur utilisateur = utilisateurRepository.findByEmail(auth.getName()).orElseThrow(null);
		modePaiement.setDescription(modePaiementDto.getDescription());
		modePaiement.setLibelle(modePaiementDto.getLibelle());
		modePaiement.setUtilisateur(utilisateur);
		modePaiement.setBoutique(boutique);
		ModePaiement modePaiementSave = modePaiementRepository.save(modePaiement);
		return Mapper.toModePaiementDto(modePaiementSave);
	}

	@Override
	public ModePaiementDto updModePaiement(ModePaiementDto modePaiementDto, String uuid) {
		// TODO Auto-generated method stub
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Boutique boutique = boutiqueRepository.findById(modePaiementDto.getUuidBoutique()).orElseThrow(null);
		Utilisateur utilisateur = utilisateurRepository.findByEmail(auth.getName()).orElseThrow(null);
		ModePaiement modePaiement = modePaiementRepository.findById(uuid).orElseThrow(null);
		modePaiement.setDescription(modePaiementDto.getDescription());
		modePaiement.setLibelle(modePaiementDto.getLibelle());
		modePaiement.setUtilisateur(utilisateur);
		modePaiement.setBoutique(boutique);
		ModePaiement modePaiementSave = modePaiementRepository.save(modePaiement);
		return Mapper.toModePaiementDto(modePaiementSave);
	}

	@Override
	public List<ModePaiementDto> findAll() {
		// TODO Auto-generated method stub
		List<ModePaiement> modePaiements = modePaiementRepository.findAll();
		List<ModePaiementDto>  modePaiementDtos = new ArrayList<ModePaiementDto>();
		modePaiements.forEach(mode -> modePaiementDtos.add(Mapper.toModePaiementDto(mode)));
		return modePaiementDtos;
	}

	@Override
	public ModePaiementDto getModePaiement(String uuid) {
		// TODO Auto-generated method stub
		ModePaiement modePaiement = modePaiementRepository.findById(uuid).orElseThrow(null);
		return Mapper.toModePaiementDto(modePaiement);
	}

	@Override
	public void deleteModePaiement(String uuid) {
		// TODO Auto-generated method stub
		ModePaiement modePaiement  = modePaiementRepository.findById(uuid).orElseThrow(null);
		modePaiementRepository.delete(modePaiement);
		
	}

	@Override
	public PageDataDto<ModePaiementDto> listeModePaiements(int page, int size, String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(String uuid) {
		// TODO Auto-generated method stub
		modePaiementRepository.deleteById(uuid);
	}

}
