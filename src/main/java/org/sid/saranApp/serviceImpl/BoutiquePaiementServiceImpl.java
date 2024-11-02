package org.sid.saranApp.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.sid.saranApp.dto.BoutiquePaiementDto;
import org.sid.saranApp.dto.PageDataDto;
import org.sid.saranApp.mapper.Mapper;
import org.sid.saranApp.model.Boutique;
import org.sid.saranApp.model.BoutiquePaiement;
import org.sid.saranApp.model.ModePaiement;
import org.sid.saranApp.model.Utilisateur;
import org.sid.saranApp.repository.BoutiquePaiementRepository;
import org.sid.saranApp.repository.BoutiqueRepository;
import org.sid.saranApp.repository.ModePaiementRepository;
import org.sid.saranApp.repository.UtilisateurRepository;
import org.sid.saranApp.service.BoutiquePaiementService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class BoutiquePaiementServiceImpl implements BoutiquePaiementService {
	
	@Autowired
	private BoutiquePaiementRepository boutiquePaiementRepository;
	@Autowired
	private BoutiqueRepository boutiqueRepository;
	@Autowired
	private ModePaiementRepository modePaiementRepository;
	@Autowired
	private UtilisateurRepository utilisateurRepository;
	
	 Logger logger = LoggerFactory.getLogger(BoutiquePaiementServiceImpl.class);
	
	@Override
	public BoutiquePaiementDto addBoutiquePaiement(BoutiquePaiementDto boutiquePaiementDto) {
		// TODO Auto-generated method stub
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		logger.info("hello {}",auth.getName());
		Boutique boutique = boutiqueRepository.findById(boutiquePaiementDto.getUuidBoutique()).orElseThrow(null);
		ModePaiement modePaiement = modePaiementRepository.findById(boutiquePaiementDto.getUuidModePaiement()).orElseThrow(null);
		Utilisateur utilisateur = utilisateurRepository.findByEmail(auth.getName()).orElseThrow(null);
		BoutiquePaiement boutiquePaiement = new BoutiquePaiement();
		boutiquePaiement.setBoutique(boutique);
		boutiquePaiement.setDateDebut(boutiquePaiementDto.getDateDebut());
		boutiquePaiement.setDateFin(boutiquePaiementDto.getDateFin());
		boutiquePaiement.setValide(boutiquePaiementDto.isValide());
		boutiquePaiement.setKeyToken(boutiquePaiementDto.getKey());
		boutiquePaiement.setModePaiement(modePaiement);
		boutiquePaiement.setRefPaiement(boutiquePaiement.getRefPaiement());
		boutiquePaiement.setTypePaiment(boutiquePaiement.getTypePaiment());
		boutiquePaiement.setBoutique(boutique);
		boutiquePaiement.setModePaiement(modePaiement);
		boutiquePaiement.setUtilisateur(utilisateur);
		BoutiquePaiement boutiquePaiementSave = boutiquePaiementRepository.save(boutiquePaiement);
		return Mapper.toBoutiquePaiementDto(boutiquePaiementSave);
	}

	@Override
	public BoutiquePaiementDto updateBoutiquePaiement(BoutiquePaiementDto boutiquePaiementDto, String uuid) {
		// TODO Auto-generated method stub
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Boutique boutique = boutiqueRepository.findById(boutiquePaiementDto.getUuidBoutique()).orElseThrow(null);
		ModePaiement modePaiement = modePaiementRepository.findById(boutiquePaiementDto.getUuidModePaiement()).orElseThrow(null);
		Utilisateur utilisateur = utilisateurRepository.findByEmail(auth.getName()).orElseThrow(null);
		BoutiquePaiement boutiquePaiement =boutiquePaiementRepository.findById(uuid).orElseThrow(null);
		boutiquePaiement.setBoutique(boutique);
		boutiquePaiement.setDateDebut(boutiquePaiementDto.getDateDebut());
		boutiquePaiement.setDateFin(boutiquePaiementDto.getDateFin());
		boutiquePaiement.setValide(boutiquePaiementDto.isValide());
		boutiquePaiement.setKeyToken(boutiquePaiementDto.getKey());
		boutiquePaiement.setModePaiement(modePaiement);
		boutiquePaiement.setRefPaiement(boutiquePaiement.getRefPaiement());
		boutiquePaiement.setTypePaiment(boutiquePaiement.getTypePaiment());
		boutiquePaiement.setBoutique(boutique);
		boutiquePaiement.setModePaiement(modePaiement);
		boutiquePaiement.setUtilisateur(utilisateur);
		BoutiquePaiement boutiquePaiementSave = boutiquePaiementRepository.save(boutiquePaiement);
		return Mapper.toBoutiquePaiementDto(boutiquePaiementSave);
	}

	@Override
	public List<BoutiquePaiementDto> findAll() {
		// TODO Auto-generated method stub
		List<BoutiquePaiement> boutiquePaiements = boutiquePaiementRepository.findAll();
		List<BoutiquePaiementDto>  boutiquePaiementDtos = new ArrayList<BoutiquePaiementDto>();
		boutiquePaiements.forEach(boutiquePaiement -> boutiquePaiementDtos.add(Mapper.toBoutiquePaiementDto(boutiquePaiement)));
		return boutiquePaiementDtos;
	}

	@Override
	public BoutiquePaiementDto getBoutiquePaiement(String uuid) {
		// TODO Auto-generated method stub
		BoutiquePaiement boutiquePaiement =boutiquePaiementRepository.findById(uuid).orElseThrow(null);
		return Mapper.toBoutiquePaiementDto(boutiquePaiement);
	}

	@Override
	public void deleteBoutiquePaiement(String uuid) {
		// TODO Auto-generated method stub
		boutiquePaiementRepository.deleteById(uuid);
		
		
	}

	@Override
	public PageDataDto<BoutiquePaiementDto> listeBoutiquePaiements(int page, int size, String key) {
		// TODO Auto-generated method stub
		return null;
	}

}
