package org.sid.saranApp.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.sid.saranApp.dto.PageDataDto;
import org.sid.saranApp.dto.VilleDto;
import org.sid.saranApp.mapper.Mapper;
import org.sid.saranApp.model.Boutique;
import org.sid.saranApp.model.Pays;
import org.sid.saranApp.model.Utilisateur;
import org.sid.saranApp.model.Ville;
import org.sid.saranApp.repository.BoutiqueRepository;
import org.sid.saranApp.repository.PaysRepository;
import org.sid.saranApp.repository.UtilisateurRepository;
import org.sid.saranApp.repository.VilleRepository;
import org.sid.saranApp.service.VilleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class VilleServiceImpl implements VilleService {
	
	@Autowired
	private VilleRepository villeRepository;
	@Autowired
	private PaysRepository paysRepository;

	@Autowired
	private UtilisateurRepository utilisateurRepository;
	
	 Logger logger = LoggerFactory.getLogger(VilleServiceImpl.class);
	
	@Override
	public VilleDto addVille(VilleDto villeDto) {
		// TODO Auto-generated method stub
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		logger.info("hello {}",auth.getName());
		Ville ville = new Ville();
		
		Utilisateur utilisateur = utilisateurRepository.findByEmail(auth.getName()).orElseThrow(null);
		Pays pays = paysRepository.findById(villeDto.getUuidPays()).orElseThrow(null);
		ville.setLibelle(villeDto.getLibelle());
		ville.setPays(pays);
		ville.setBoutique(utilisateur.getBoutique());
		ville.setUtilisateur(utilisateur);
		Ville villeSave  = villeRepository.save(ville);
		return Mapper.toVilleDto(villeSave);
	}

	@Override
	public VilleDto updateVille(VilleDto villeDto, String uuid) {
		// TODO Auto-generated method stub
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Utilisateur utilisateur = utilisateurRepository.findByEmail(auth.getName()).orElseThrow(null);
		Ville ville = villeRepository.findById(uuid).orElseThrow(null);
		Pays pays = paysRepository.findById(villeDto.getUuidPays()).orElseThrow(null);
		ville.setLibelle(villeDto.getLibelle());
		ville.setPays(pays);
		ville.setBoutique(utilisateur.getBoutique());
		ville.setUtilisateur(utilisateur);
		Ville villeSave  = villeRepository.save(ville);
		return Mapper.toVilleDto(villeSave);
	}

	@Override
	public List<VilleDto> findAll() {
		// TODO Auto-generated method stub
		List<Ville> villes = villeRepository.findAll();
		List<VilleDto> villeDtos = new ArrayList<VilleDto>();
		villes.forEach(ville -> villeDtos.add(Mapper.toVilleDto(ville)));
		return villeDtos;
	}

	@Override
	public VilleDto getVille(String uuid) {
		// TODO Auto-generated method stub
		Ville ville = villeRepository.findById(uuid).orElseThrow(null);
		return Mapper.toVilleDto(ville);
	}

	@Override
	public void deleteVille(String uuid) {
		// TODO Auto-generated method stub
		Ville ville = villeRepository.findById(uuid).orElseThrow(null);
		villeRepository.delete(ville);
		
	}

	@Override
	public List<VilleDto> listeVilleByPays(String uuidPays) {
		// TODO Auto-generated method stub
		List<Ville> villes = villeRepository.listeVilleByPays(uuidPays);
		List<VilleDto> villeDtos = new ArrayList<VilleDto>();
		villes.forEach(ville -> villeDtos.add(Mapper.toVilleDto(ville)));
		return villeDtos;
	}

	@Override
	public PageDataDto<VilleDto> listeVilles(int page, int size, String key) {
		// TODO Auto-generated method stub
		return null;
	}

}
