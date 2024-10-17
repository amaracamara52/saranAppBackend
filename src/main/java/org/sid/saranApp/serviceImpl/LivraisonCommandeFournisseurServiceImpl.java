package org.sid.saranApp.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.sid.saranApp.dto.LivraisonCommandeFournisseurDto;
import org.sid.saranApp.mapper.Mapper;
import org.sid.saranApp.model.Boutique;
import org.sid.saranApp.model.CommandeFournisseur;
import org.sid.saranApp.model.DetailCommandeFournisseur;
import org.sid.saranApp.model.LivraisonCommandeFournisseur;
import org.sid.saranApp.model.Utilisateur;
import org.sid.saranApp.repository.BoutiqueRepository;
import org.sid.saranApp.repository.CommandeFournisseurRepository;
import org.sid.saranApp.repository.DetailCommandeFournisseurRepository;
import org.sid.saranApp.repository.LivraisonCommandeFournisseurRepository;
import org.sid.saranApp.repository.UtilisateurRepository;
import org.sid.saranApp.service.LivraisonCommandeFournisseurService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class LivraisonCommandeFournisseurServiceImpl implements LivraisonCommandeFournisseurService {
	
	@Autowired
	private LivraisonCommandeFournisseurRepository livraisonCommandeFournisseurRepository;
	@Autowired
	private BoutiqueRepository boutiqueRepository;
	@Autowired
	private DetailCommandeFournisseurRepository detailCommandeFournisseurRepository;
	@Autowired
	private CommandeFournisseurRepository commandeFournisseurRepository;
	@Autowired
	private UtilisateurRepository utilisateurRepository;
	
	 Logger logger = LoggerFactory.getLogger(LivraisonCommandeFournisseurServiceImpl.class);
	
	@Override
	public LivraisonCommandeFournisseurDto addLivraisonCommandeFournisseur(LivraisonCommandeFournisseurDto livraisonCommandeFournisseurDto) {
		// TODO Auto-generated method stub
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		logger.info("hello {}",auth.getName());
		LivraisonCommandeFournisseur livraisonCommandeFournisseur = new LivraisonCommandeFournisseur();
		Boutique boutique = boutiqueRepository.findById(livraisonCommandeFournisseurDto.getUuidBoutique()).orElseThrow(null);
		Utilisateur utilisateur = utilisateurRepository.findByEmail(auth.getName()).orElseThrow(null);
		DetailCommandeFournisseur detailCommandeFournisseur = detailCommandeFournisseurRepository.findById(livraisonCommandeFournisseurDto.getUuidDetailCommandeFournisseur()).orElseThrow(null);
		CommandeFournisseur commandeFournisseur = commandeFournisseurRepository.findById(livraisonCommandeFournisseurDto.getUuidCommandeFournisseur()).orElseThrow(null);
		livraisonCommandeFournisseur.setDateLivraison(livraisonCommandeFournisseurDto.getDateLivraison());
		livraisonCommandeFournisseur.setHeure(livraisonCommandeFournisseurDto.getHeure());
		livraisonCommandeFournisseur.setPrix(livraisonCommandeFournisseurDto.getPrix());
		livraisonCommandeFournisseur.setQte(livraisonCommandeFournisseurDto.getQte());
		livraisonCommandeFournisseur.setBoutique(boutique);
		livraisonCommandeFournisseur.setUtilisateur(utilisateur);
		livraisonCommandeFournisseur.setCommandeFournisseur(commandeFournisseur);
		livraisonCommandeFournisseur.setDetailCommandeFournisseur(detailCommandeFournisseur);
		LivraisonCommandeFournisseur livraisonCommandeFournisseurSave = livraisonCommandeFournisseurRepository.save(livraisonCommandeFournisseur);
		return Mapper.toLivraisonCommandeFournisseurDto(livraisonCommandeFournisseurSave);
	}

	@Override
	public LivraisonCommandeFournisseurDto updateLivraisonCommandeFournisseur(LivraisonCommandeFournisseurDto livraisonCommandeFournisseurDto, String uuid) {
		// TODO Auto-generated method stub
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		LivraisonCommandeFournisseur livraisonCommandeFournisseur = livraisonCommandeFournisseurRepository.findById(uuid).orElseThrow(null);
		Boutique boutique = boutiqueRepository.findById(livraisonCommandeFournisseurDto.getUuidBoutique()).orElseThrow(null);
		CommandeFournisseur commandeFournisseur = commandeFournisseurRepository.findById(livraisonCommandeFournisseurDto.getUuidCommandeFournisseur()).orElseThrow(null);
		Utilisateur utilisateur = utilisateurRepository.findByEmail(auth.getName()).orElseThrow(null);
		DetailCommandeFournisseur detailCommandeFournisseur = detailCommandeFournisseurRepository.findById(livraisonCommandeFournisseurDto.getUuidDetailCommandeFournisseur()).orElseThrow(null);
		livraisonCommandeFournisseur.setHeure(livraisonCommandeFournisseurDto.getHeure());
		livraisonCommandeFournisseur.setPrix(livraisonCommandeFournisseurDto.getPrix());
		livraisonCommandeFournisseur.setQte(livraisonCommandeFournisseurDto.getQte());
		livraisonCommandeFournisseur.setDateLivraison(livraisonCommandeFournisseurDto.getDateLivraison());
		livraisonCommandeFournisseur.setBoutique(boutique);
		livraisonCommandeFournisseur.setUtilisateur(utilisateur);
		livraisonCommandeFournisseur.setCommandeFournisseur(commandeFournisseur);
		livraisonCommandeFournisseur.setDetailCommandeFournisseur(detailCommandeFournisseur);
		LivraisonCommandeFournisseur livraisonCommandeFournisseurSave = livraisonCommandeFournisseurRepository.save(livraisonCommandeFournisseur);
		return Mapper.toLivraisonCommandeFournisseurDto(livraisonCommandeFournisseurSave);
	}

	@Override
	public List<LivraisonCommandeFournisseurDto> findAll() {
		// TODO Auto-generated method stub
		List<LivraisonCommandeFournisseur> livraisonCommandeFournisseurs = livraisonCommandeFournisseurRepository.findAll();
		List<LivraisonCommandeFournisseurDto> livraisonCommandeFournisseurDtos = new ArrayList<LivraisonCommandeFournisseurDto>();
		livraisonCommandeFournisseurs.forEach(livraisonCommandeFournisseur -> livraisonCommandeFournisseurDtos.add(Mapper.toLivraisonCommandeFournisseurDto(livraisonCommandeFournisseur)));
		return livraisonCommandeFournisseurDtos;
	}

	@Override
	public LivraisonCommandeFournisseurDto getLivraisonCommandeFournisseur(String uuid) {
		// TODO Auto-generated method stub
		LivraisonCommandeFournisseur livraisonCommandeFournisseur = livraisonCommandeFournisseurRepository.findById(uuid).orElseThrow(null);
		return Mapper.toLivraisonCommandeFournisseurDto(livraisonCommandeFournisseur);
	}

	@Override
	public void deleteLivraisonCommandeFournisseur(String uuid) {
		// TODO Auto-generated method stub
		LivraisonCommandeFournisseur livraisonCommandeFournisseur = livraisonCommandeFournisseurRepository.findById(uuid).orElseThrow(null);
		livraisonCommandeFournisseurRepository.delete(livraisonCommandeFournisseur);
		
	}

}
