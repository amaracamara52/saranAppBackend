package org.sid.saranApp.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.sid.saranApp.dto.CommuneDto;
import org.sid.saranApp.dto.PageDataDto;
import org.sid.saranApp.dto.VilleDto;
import org.sid.saranApp.mapper.Mapper;
import org.sid.saranApp.model.Boutique;
import org.sid.saranApp.model.Commune;
import org.sid.saranApp.model.Utilisateur;
import org.sid.saranApp.model.Ville;
import org.sid.saranApp.repository.BoutiqueRepository;
import org.sid.saranApp.repository.CommuneRepository;
import org.sid.saranApp.repository.UtilisateurRepository;
import org.sid.saranApp.repository.VilleRepository;
import org.sid.saranApp.service.CommuneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CommuneServiceImpl implements CommuneService {
	
	@Autowired
	private VilleRepository  villeRepository;
	@Autowired
	private CommuneRepository communeRepository;
	@Autowired
	private UtilisateurRepository utilisateurRepository;
	
	
	 Logger logger = LoggerFactory.getLogger(CommuneServiceImpl.class);
	
	@Override
	public CommuneDto addCommune(CommuneDto communeDto) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		logger.info("hello {}",auth.getName());
		// TODO Auto-generated method stub
		Utilisateur utilisateur = utilisateurRepository.findByEmail(auth.getName()).orElseThrow(null);
		Ville ville = villeRepository.findById(communeDto.getUuidVille()).orElseThrow(null);
		Commune commune = new Commune();
		commune.setLibelle(communeDto.getLibelle());
		commune.setVille(ville);
		commune.setBoutique(utilisateur.getBoutique());
		commune.setUtilisateur(utilisateur);
		Commune communeSave = communeRepository.save(commune);
		return Mapper.toCommuneDto(communeSave);
	}

	@Override
	public CommuneDto updateCommune(CommuneDto communeDto, String uuid) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		// TODO Auto-generated method stub
		Utilisateur utilisateur = utilisateurRepository.findByEmail(auth.getName()).orElseThrow(null);
		Ville ville = villeRepository.findById(communeDto.getUuidVille()).orElseThrow(null);
		Commune commune = communeRepository.findById(uuid).orElseThrow(null);
		commune.setLibelle(communeDto.getLibelle());
		commune.setVille(ville);
		commune.setBoutique(utilisateur.getBoutique());
		commune.setUtilisateur(utilisateur);
		Commune communeSave = communeRepository.save(commune);
		return Mapper.toCommuneDto(communeSave);
	}

	@Override
	public List<CommuneDto> findAll() {
		// TODO Auto-generated method stub
		List<Commune> communes = communeRepository.findAll();
		List<CommuneDto> communeDtos = new ArrayList<CommuneDto>();
		communes.forEach(commune -> communeDtos.add(Mapper.toCommuneDto(commune)));
		return communeDtos;
	}

	@Override
	public CommuneDto getCommune(String uuid) {
		// TODO Auto-generated method stub
		Commune commune = communeRepository.findById(uuid).orElseThrow(null);
		return Mapper.toCommuneDto(commune);
	}

	@Override
	public void deleteCommune(String uuid) {
		// TODO Auto-generated method stub
		Commune commune = communeRepository.findById(uuid).orElseThrow(null);
		communeRepository.delete(commune);
		
	}
	@Override
	public List<CommuneDto> listeCommuneByVille(String uuidVille) {
		// TODO Auto-generated method stub
		List<Commune> communes = communeRepository.listeCommuneByVille(uuidVille);
		List<CommuneDto> communeDtos = new ArrayList<CommuneDto>();
		communes.forEach(commune -> communeDtos.add(Mapper.toCommuneDto(commune)));
		return communeDtos;
	}

	@Override
	public PageDataDto<CommuneDto> listeCommunes(int page, int size, String key) {
		// TODO Auto-generated method stub
		return null;
	}

}
