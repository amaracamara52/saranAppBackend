package org.sid.saranApp.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.sid.saranApp.dto.PageDataDto;
import org.sid.saranApp.dto.PaiementCommandeFournisseurDto;
import org.sid.saranApp.mapper.Mapper;
import org.sid.saranApp.model.Boutique;
import org.sid.saranApp.model.CommandeFournisseur;
import org.sid.saranApp.model.PaiementCommandeFournisseur;
import org.sid.saranApp.model.Utilisateur;
import org.sid.saranApp.repository.BoutiqueRepository;
import org.sid.saranApp.repository.CommandeFournisseurRepository;
import org.sid.saranApp.repository.PaiementCommandeFournisseurRepository;
import org.sid.saranApp.repository.UtilisateurRepository;
import org.sid.saranApp.service.PaiementCommandeFournisseurService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class PaiementCommandeFournisseurServiceImpl implements PaiementCommandeFournisseurService{
	
	@Autowired
	private BoutiqueRepository boutiqueRepository;
	@Autowired
	private CommandeFournisseurRepository commandeFournisseurRepository;
	@Autowired
	private PaiementCommandeFournisseurRepository paiementCommandeFournisseurRepository;
	@Autowired
	private UtilisateurRepository utilisateurRepository;
	
	 Logger logger = LoggerFactory.getLogger(PaiementCommandeFournisseurServiceImpl.class);
	
	@Override
	public PaiementCommandeFournisseurDto addPaiementCommandeFournisseur(PaiementCommandeFournisseurDto paiementCommandeFournisseurDto) {
		// TODO Auto-generated method stub
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		logger.info("hello {}",auth.getName());
		PaiementCommandeFournisseur paiementCommandeFournisseur = new PaiementCommandeFournisseur();
		Boutique boutique = boutiqueRepository.findById(paiementCommandeFournisseurDto.getUuidCommandeFournisseur()).orElseThrow(null);
		Utilisateur utilisateur = utilisateurRepository.findByEmail(auth.getName()).orElseThrow(null);
		CommandeFournisseur commandeFournisseur = commandeFournisseurRepository.findById(paiementCommandeFournisseurDto.getUuidCommandeFournisseur()).orElseThrow(null);
		paiementCommandeFournisseur.setDatePaiement(paiementCommandeFournisseurDto.getDatePaiement());
		paiementCommandeFournisseur.setPrixPayes(paiementCommandeFournisseurDto.getPrixPayes());
		paiementCommandeFournisseur.setBoutique(boutique);
		paiementCommandeFournisseur.setUtilisateur(utilisateur);
		paiementCommandeFournisseur.setCommandeFournisseur(commandeFournisseur);
		PaiementCommandeFournisseur paiementCommandeFournisseurSave = paiementCommandeFournisseurRepository.save(paiementCommandeFournisseur);
		return Mapper.toPaiementCommandeFournisseurDto(paiementCommandeFournisseurSave);
	}

	@Override
	public PaiementCommandeFournisseurDto updatePaiementCommandeFournisseur(PaiementCommandeFournisseurDto paiementCommandeFournisseurDto, String uuid) {
		// TODO Auto-generated method stub
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Boutique boutique = boutiqueRepository.findById(paiementCommandeFournisseurDto.getUuidBoutique()).orElseThrow(null);
		Utilisateur utilisateur = utilisateurRepository.findByEmail(auth.getName()).orElseThrow(null);
		CommandeFournisseur commandeFournisseur = commandeFournisseurRepository.findById(paiementCommandeFournisseurDto.getUuidCommandeFournisseur()).orElseThrow(null);
		PaiementCommandeFournisseur paiementCommandeFournisseur = paiementCommandeFournisseurRepository.findById(uuid).orElseThrow(null);
		paiementCommandeFournisseur.setDatePaiement(paiementCommandeFournisseurDto.getDatePaiement());
		paiementCommandeFournisseur.setPrixPayes(paiementCommandeFournisseurDto.getPrixPayes());
		paiementCommandeFournisseur.setBoutique(boutique);
		paiementCommandeFournisseur.setUtilisateur(utilisateur);
		paiementCommandeFournisseur.setCommandeFournisseur(commandeFournisseur);
		PaiementCommandeFournisseur paiementCommandeFournisseurSave = paiementCommandeFournisseurRepository.save(paiementCommandeFournisseur);	
		return Mapper.toPaiementCommandeFournisseurDto(paiementCommandeFournisseurSave);
	}

	@Override
	public List<PaiementCommandeFournisseurDto> findAll() {
		// TODO Auto-generated method stub
		List<PaiementCommandeFournisseur> paiementCommandeFournisseurs = paiementCommandeFournisseurRepository.findAll();
		List<PaiementCommandeFournisseurDto> paiementCommandeFournisseurDtos = new ArrayList<PaiementCommandeFournisseurDto>();
		paiementCommandeFournisseurs.forEach(paiementCommandeFournisseur -> paiementCommandeFournisseurDtos.add(Mapper.toPaiementCommandeFournisseurDto(paiementCommandeFournisseur)));
		return paiementCommandeFournisseurDtos;
	}

	@Override
	public PaiementCommandeFournisseurDto getPaiementCommandeFournisseur(String uuid) {
		// TODO Auto-generated method stub
		PaiementCommandeFournisseur paiementCommandeFournisseur = paiementCommandeFournisseurRepository.findById(uuid).orElseThrow(null);
		return Mapper.toPaiementCommandeFournisseurDto(paiementCommandeFournisseur);
	}

	@Override
	public void deletePaiementCommandeFournisseur(String uuid) {
		// TODO Auto-generated method stub
		//PaiementCommandeFournisseur paiementCommandeFournisseur = paiementCommandeFournisseurRepository.findById(uuid).orElseThrow(null);
		paiementCommandeFournisseurRepository.deleteById(uuid);
		
	}

	@Override
	public PageDataDto<PaiementCommandeFournisseurDto> listePaiementCommandeFournisseurs(int page, int size,
			String key) {
		// TODO Auto-generated method stub
		return null;
	}

}
 