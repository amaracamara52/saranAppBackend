package org.sid.saranApp.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.sid.saranApp.dto.CommandeFournisseurDto;
import org.sid.saranApp.mapper.Mapper;
import org.sid.saranApp.model.Boutique;
import org.sid.saranApp.model.CommandeFournisseur;
import org.sid.saranApp.model.Fournisseur;
import org.sid.saranApp.model.Utilisateur;
import org.sid.saranApp.repository.BoutiqueRepository;
import org.sid.saranApp.repository.CommandeFournisseurRepository;
import org.sid.saranApp.repository.FournisseurRepository;
import org.sid.saranApp.repository.UtilisateurRepository;
import org.sid.saranApp.service.CommandeFournisseurService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CommandeFournisseurServiceImpl implements CommandeFournisseurService {
	
	@Autowired
	private CommandeFournisseurRepository commandeFournisseurRepository;
	@Autowired
	private FournisseurRepository fournisseurRepository;
	@Autowired
	private BoutiqueRepository boutiqueRepository;
	@Autowired
	private UtilisateurRepository utilisateurRepository;
	
	 Logger logger = LoggerFactory.getLogger(CommandeFournisseurServiceImpl.class);

	@Override
	public CommandeFournisseurDto addCommandeFournisseur(CommandeFournisseurDto commandeFournisseurDto) {
		// TODO Auto-generated method stub
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		logger.info("hello {}",auth.getName());
		CommandeFournisseur commandeFournisseur = new CommandeFournisseur();
		Utilisateur utilisateur = utilisateurRepository.findByEmail(auth.getName()).orElseThrow(null);
		Fournisseur fournisseur = fournisseurRepository.findById(commandeFournisseurDto.getUuidFournisseur()).orElseThrow(null);
		Boutique boutique = boutiqueRepository.findById(commandeFournisseurDto.getUuidBoutique()).orElseThrow(null);
		commandeFournisseur.setValeurMarchandise(commandeFournisseurDto.getValeurMarchandise());
		commandeFournisseur.setDateCommandeFournisseur(commandeFournisseurDto.getDateCommandeFournisseur());
		commandeFournisseur.setIsPaye(commandeFournisseurDto.getIsPaye());
		commandeFournisseur.setBoutique(boutique);
		commandeFournisseur.setUtilisateur(utilisateur);
		commandeFournisseur.setFournisseur(fournisseur);
		CommandeFournisseur commandeFournisseurSave = commandeFournisseurRepository.save(commandeFournisseur);
		return Mapper.toCommandeFournisseurDto(commandeFournisseurSave);
	}

	@Override
	public CommandeFournisseurDto updateCommandeFournisseur(CommandeFournisseurDto commandeFournisseurDto,String uuid) {
		// TODO Auto-generated method stub
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Utilisateur utilisateur = utilisateurRepository.findByEmail(auth.getName()).orElseThrow(null);
		Fournisseur fournisseur = fournisseurRepository.findById(commandeFournisseurDto.getUuidFournisseur()).orElseThrow(null);
		Boutique boutique = boutiqueRepository.findById(commandeFournisseurDto.getUuidBoutique()).orElseThrow(null);
		CommandeFournisseur commandeFournisseur = commandeFournisseurRepository.findById(uuid).orElseThrow(null);
		commandeFournisseur.setDateCommandeFournisseur(commandeFournisseurDto.getDateCommandeFournisseur());
		commandeFournisseur.setIsPaye(commandeFournisseurDto.getIsPaye());
		commandeFournisseur.setValeurMarchandise(commandeFournisseurDto.getValeurMarchandise());
		commandeFournisseur.setBoutique(boutique);
		commandeFournisseur.setUtilisateur(utilisateur);
		commandeFournisseur.setFournisseur(fournisseur);
		CommandeFournisseur commandeFournisseurSave = commandeFournisseurRepository.save(commandeFournisseur);
		return Mapper.toCommandeFournisseurDto(commandeFournisseurSave);
	}

	@Override
	public List<CommandeFournisseurDto> findAll() {
		// TODO Auto-generated method stub
		List<CommandeFournisseur> commandeFournisseurs = commandeFournisseurRepository.findAll();
		List<CommandeFournisseurDto> commandeFournisseurDtos = new ArrayList<CommandeFournisseurDto>();
		commandeFournisseurs.forEach(commandeFournisseur -> commandeFournisseurDtos.add(Mapper.toCommandeFournisseurDto(commandeFournisseur)));
		return commandeFournisseurDtos;
	}

	@Override
	public CommandeFournisseurDto getCommandeFournisseur(String uuid) {
		// TODO Auto-generated method stub
		CommandeFournisseur commandeFournisseur = commandeFournisseurRepository.findById(uuid).orElseThrow(null);
		return Mapper.toCommandeFournisseurDto(commandeFournisseur);
	}

	@Override
	public void deleteCommandeFournisseur(String uuid) {
		// TODO Auto-generated method stub
		CommandeFournisseur commandeFournisseur = commandeFournisseurRepository.findById(uuid).orElseThrow(null);
		commandeFournisseurRepository.delete(commandeFournisseur);
		
	}

}
