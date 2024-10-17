package org.sid.saranApp.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.sid.saranApp.dto.DetailCommandeFournisseurDto;
import org.sid.saranApp.mapper.Mapper;
import org.sid.saranApp.model.Boutique;
import org.sid.saranApp.model.CommandeFournisseur;
import org.sid.saranApp.model.DetailCommandeFournisseur;
import org.sid.saranApp.model.Utilisateur;
import org.sid.saranApp.repository.BoutiqueRepository;
import org.sid.saranApp.repository.CommandeFournisseurRepository;
import org.sid.saranApp.repository.DetailCommandeFournisseurRepository;
import org.sid.saranApp.repository.UtilisateurRepository;
import org.sid.saranApp.service.DetailCommandeFournisseurService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class DetailCommandeFournisseurServiceImpl implements DetailCommandeFournisseurService {
	
	@Autowired
	private DetailCommandeFournisseurRepository detailCommandeFournisseurRepository;
	@Autowired
	private CommandeFournisseurRepository commandeFournisseurRepository;
	@Autowired
	private BoutiqueRepository boutiqueRepository;
	@Autowired
	private UtilisateurRepository utilisateurRepository;
	
	 Logger logger = LoggerFactory.getLogger(DetailCommandeFournisseurServiceImpl.class);
	
	@Override
	public DetailCommandeFournisseurDto addDetailCommandeFournisseur(DetailCommandeFournisseurDto detailCommandeFournisseurDto) {
		// TODO Auto-generated method stub
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		logger.info("hello {}",auth.getName());
		DetailCommandeFournisseur detailCommandeFournisseur = new DetailCommandeFournisseur();
		CommandeFournisseur commandeFournisseur = commandeFournisseurRepository.findById(detailCommandeFournisseurDto.getUuidCommandeFournisseur()).orElseThrow(null);
		Boutique boutique = boutiqueRepository.findById(detailCommandeFournisseurDto.getUuidBoutique()).orElseThrow(null);
		Utilisateur utilisateur = utilisateurRepository.findByEmail(auth.getName()).orElseThrow(null);
		detailCommandeFournisseur.setNomClient(detailCommandeFournisseurDto.getNomClient());
		detailCommandeFournisseur.setNumeroCommande(detailCommandeFournisseurDto.getNumeroCommande());
		detailCommandeFournisseur.setPrix(detailCommandeFournisseurDto.getPrix());
		detailCommandeFournisseur.setQuantiteCommande(detailCommandeFournisseurDto.getQuantiteCommande());
		detailCommandeFournisseur.setBoutique(boutique);
		detailCommandeFournisseur.setUtilisateur(utilisateur);
		detailCommandeFournisseur.setCommandeFournisseur(commandeFournisseur);
		DetailCommandeFournisseur detailCommandeFournisseurSave = detailCommandeFournisseurRepository.save(detailCommandeFournisseur);
		return Mapper.toDetailCommandeFournisseurDto(detailCommandeFournisseurSave);
	}

	@Override
	public DetailCommandeFournisseurDto updateDetailCommandeFournisseur(DetailCommandeFournisseurDto detailCommandeFournisseurDto, String uuid) {
		// TODO Auto-generated method stub
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		CommandeFournisseur commandeFournisseur = commandeFournisseurRepository.findById(detailCommandeFournisseurDto.getUuidCommandeFournisseur()).orElseThrow(null);
		Boutique boutique = boutiqueRepository.findById(detailCommandeFournisseurDto.getUuidBoutique()).orElseThrow(null);
		DetailCommandeFournisseur detailCommandeFournisseur = detailCommandeFournisseurRepository.findById(uuid).orElseThrow(null);
		Utilisateur utilisateur = utilisateurRepository.findByEmail(auth.getName()).orElseThrow(null);
		detailCommandeFournisseur.setNomClient(detailCommandeFournisseurDto.getNomClient());
		detailCommandeFournisseur.setNumeroCommande(detailCommandeFournisseurDto.getNumeroCommande());
		detailCommandeFournisseur.setPrix(detailCommandeFournisseurDto.getPrix());
		detailCommandeFournisseur.setQuantiteCommande(detailCommandeFournisseurDto.getQuantiteCommande());
		detailCommandeFournisseur.setBoutique(boutique);
		detailCommandeFournisseur.setUtilisateur(utilisateur);
		detailCommandeFournisseur.setCommandeFournisseur(commandeFournisseur);
		DetailCommandeFournisseur detailCommandeFournisseurSave = detailCommandeFournisseurRepository.save(detailCommandeFournisseur);
		return Mapper.toDetailCommandeFournisseurDto(detailCommandeFournisseurSave);
	}

	@Override
	public List<DetailCommandeFournisseurDto> findAll() {
		// TODO Auto-generated method stub
		List<DetailCommandeFournisseur> detailCommandeFournisseurs = detailCommandeFournisseurRepository.findAll();
		List<DetailCommandeFournisseurDto> detailCommandeFournisseurDtos = new ArrayList<DetailCommandeFournisseurDto>();
		detailCommandeFournisseurs.forEach(detailCommandeFournisseur -> detailCommandeFournisseurDtos.add(Mapper.toDetailCommandeFournisseurDto(detailCommandeFournisseur)));
		return detailCommandeFournisseurDtos;
	}

	@Override
	public DetailCommandeFournisseurDto getDetailCommandeFournisseur(String uuid) {
		// TODO Auto-generated method stub
		DetailCommandeFournisseur detailCommandeFournisseur = detailCommandeFournisseurRepository.findById(uuid).orElseThrow(null);
		return Mapper.toDetailCommandeFournisseurDto(detailCommandeFournisseur);
	}

	@Override
	public void deleteDetailCommandeFournisseur(String uuid) {
		// TODO Auto-generated method stub
		DetailCommandeFournisseur detailCommandeFournisseur = detailCommandeFournisseurRepository.findById(uuid).orElseThrow(null);
		detailCommandeFournisseurRepository.delete(detailCommandeFournisseur);
	}

}
