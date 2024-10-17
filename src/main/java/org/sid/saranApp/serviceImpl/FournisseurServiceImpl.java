package org.sid.saranApp.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.sid.saranApp.dto.FournisseurDto;
import org.sid.saranApp.mapper.Mapper;
import org.sid.saranApp.model.Boutique;
import org.sid.saranApp.model.Fournisseur;
import org.sid.saranApp.model.Quartier;
import org.sid.saranApp.model.Utilisateur;
import org.sid.saranApp.repository.BoutiqueRepository;
import org.sid.saranApp.repository.FournisseurRepository;
import org.sid.saranApp.repository.QuartierRepository;
import org.sid.saranApp.repository.UtilisateurRepository;
import org.sid.saranApp.service.FournisseurService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class FournisseurServiceImpl implements FournisseurService {
	
	@Autowired
	private FournisseurRepository fournisseurRepository;
	@Autowired
	private BoutiqueRepository boutiqueRepository;
	@Autowired
	private QuartierRepository quartierRepository;
	@Autowired
	private UtilisateurRepository utilisateurRepository;
	
	 Logger logger = LoggerFactory.getLogger(FournisseurServiceImpl.class);
	
	@Override
	public FournisseurDto addFournisseur(FournisseurDto fournisseurDto) {
		// TODO Auto-generated method stub
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		logger.info("hello {}",auth.getName());
		Fournisseur fournisseur = new Fournisseur();
		Utilisateur utilisateur = utilisateurRepository.findByEmail(auth.getName()).orElseThrow(null);
		Quartier quartier = quartierRepository.findById(fournisseurDto.getUuidQuartier()).orElseThrow(null);
		
		fournisseur.setNom(fournisseurDto.getNom());
		fournisseur.setPrenom(fournisseurDto.getPrenom());
		fournisseur.setEmail(fournisseurDto.getEmail());
		fournisseur.setTelephone(fournisseurDto.getTelephone());
		fournisseur.setBoutique(utilisateur.getBoutique());
		fournisseur.setUtilisateur(utilisateur);
		fournisseur.setQuartier(quartier);
		Fournisseur fournisseurSave = fournisseurRepository.save(fournisseur);
		return Mapper.toFournisseurDto(fournisseurSave);
		
	}

	@Override
	public FournisseurDto updateFournisseur(FournisseurDto fournisseurDto, String uuid) {
		// TODO Auto-generated method stub
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	
		Utilisateur utilisateur = utilisateurRepository.findByEmail(auth.getName()).orElseThrow(null);
		Quartier quartier = quartierRepository.findById(fournisseurDto.getUuidQuartier()).orElseThrow(null);
		Fournisseur fournisseur = fournisseurRepository.findById(uuid).orElseThrow(null);
		fournisseur.setNom(fournisseurDto.getNom());
		fournisseur.setPrenom(fournisseurDto.getPrenom());
		fournisseur.setEmail(fournisseurDto.getEmail());
		fournisseur.setTelephone(fournisseurDto.getTelephone());
		fournisseur.setBoutique(utilisateur.getBoutique());
		fournisseur.setUtilisateur(utilisateur);
		fournisseur.setQuartier(quartier);
		Fournisseur fournisseurSave = fournisseurRepository.save(fournisseur);
		return Mapper.toFournisseurDto(fournisseurSave);
	}

	@Override
	public FournisseurDto getFournisseur(String uuid) {
		// TODO Auto-generated method stub
		Fournisseur fournisseur = fournisseurRepository.findById(uuid).orElseThrow(null);
		return Mapper.toFournisseurDto(fournisseur);
	}

	@Override
	public List<FournisseurDto> findAll() {
		// TODO Auto-generated method stub
		List<Fournisseur> fournisseurs = fournisseurRepository.findAll();
		List<FournisseurDto> fournisseurDtos = new ArrayList<FournisseurDto>();
		fournisseurs.forEach(fournisseur -> fournisseurDtos.add(Mapper.toFournisseurDto(fournisseur)));
		return fournisseurDtos;
		
	}

	@Override
	public void deleteFournisseur(String uuid) {
		// TODO Auto-generated method stub
		Fournisseur fournisseur = fournisseurRepository.findById(uuid).orElseThrow(null);
		fournisseurRepository.delete(fournisseur);
		
	}

}
