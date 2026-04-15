package org.sid.saranApp.serviceImpl;

import org.sid.saranApp.dto.PageDataDto;
import org.sid.saranApp.dto.PaiementCommandeVenteDto;
import org.sid.saranApp.mapper.Mapper;
import org.sid.saranApp.model.Boutique;
import org.sid.saranApp.model.CommandeFournisseur;
import org.sid.saranApp.model.CommandeVente;
import org.sid.saranApp.model.PaiementCommandeVente;
import org.sid.saranApp.model.StoredFile;
import org.sid.saranApp.model.Utilisateur;
import org.sid.saranApp.repository.BoutiqueRepository;
import org.sid.saranApp.repository.CommandeFournisseurRepository;
import org.sid.saranApp.repository.CommandeVenteRepository;
import org.sid.saranApp.repository.PaiementCommandeVenteRepository;
import org.sid.saranApp.repository.StoredFileRepository;
import org.sid.saranApp.repository.UtilisateurRepository;
import org.sid.saranApp.service.PaiementCommandeVenteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaiementCommandeVenteServiceImpl implements PaiementCommandeVenteService{
	
	@Autowired
	private BoutiqueRepository boutiqueRepository;
	@Autowired
	private CommandeVenteRepository commandeVenteRepository;
	@Autowired
	private PaiementCommandeVenteRepository paiementCommandeVenteRepository;
	@Autowired
	private UtilisateurRepository utilisateurRepository;
	@Autowired
	private StoredFileRepository storedFileRepository;
	
	 Logger logger = LoggerFactory.getLogger(PaiementCommandeVenteServiceImpl.class);
	
	@Override
	public PaiementCommandeVenteDto addPaiementCommandeFournisseur(PaiementCommandeVenteDto paiementCommandeVenteDto) {
		// TODO Auto-generated method stub
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		logger.info("hello {}",auth.getName());
		PaiementCommandeVente paiementCommandeVente = new PaiementCommandeVente();
		//Boutique boutique = boutiqueRepository.findById(paiementCommandeVenteDto.getUuidCommandeFournisseur()).orElseThrow(null);
		Utilisateur utilisateur = utilisateurRepository.findByEmail(auth.getName()).orElseThrow(null);
		CommandeVente commandeVente = commandeVenteRepository.findById(paiementCommandeVenteDto.getUuidCommandeVente()).orElseThrow(()->new RuntimeException(""));
		StoredFile storedFile = storedFileRepository.findById(paiementCommandeVenteDto.getUuidRecuVersement()).orElseThrow(()->new RuntimeException(""));
		paiementCommandeVente.setBoutique(utilisateur.getBoutique());
		paiementCommandeVente.setUtilisateur(utilisateur);
		paiementCommandeVente.setCommandeVente(commandeVente);
		paiementCommandeVente.setMontantVerse(paiementCommandeVenteDto.getMontantVerse());
		paiementCommandeVente.setRecuVersement(storedFile);
	
		
		PaiementCommandeVente paiementCommandeFournisseurSave = paiementCommandeVenteRepository.save(paiementCommandeVente);
		return Mapper.topaiementCommandeVenteDto(paiementCommandeFournisseurSave);
	}

	@Override
	public PaiementCommandeVenteDto updatePaiementCommandeFournisseur(PaiementCommandeVenteDto paiementCommandeVenteDto, String uuid) {
		// TODO Auto-generated method stub
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		logger.info("hello {}",auth.getName());
		PaiementCommandeVente paiementCommandeVente = new PaiementCommandeVente();
		//Boutique boutique = boutiqueRepository.findById(paiementCommandeVenteDto.getUuidCommandeFournisseur()).orElseThrow(null);
		Utilisateur utilisateur = utilisateurRepository.findByEmail(auth.getName()).orElseThrow(null);
		CommandeVente commandeVente = commandeVenteRepository.findById(paiementCommandeVenteDto.getUuidCommandeVente()).orElseThrow(()->new RuntimeException(""));
		StoredFile storedFile = storedFileRepository.findById(paiementCommandeVenteDto.getUuidRecuVersement()).orElseThrow(()->new RuntimeException(""));
		paiementCommandeVente.setBoutique(utilisateur.getBoutique());
		paiementCommandeVente.setUtilisateur(utilisateur);
		paiementCommandeVente.setCommandeVente(commandeVente);
		paiementCommandeVente.setMontantVerse(paiementCommandeVenteDto.getMontantVerse());
		paiementCommandeVente.setRecuVersement(storedFile);
	
		
		PaiementCommandeVente paiementCommandeFournisseurSave = paiementCommandeVenteRepository.save(paiementCommandeVente);
		return Mapper.topaiementCommandeVenteDto(paiementCommandeFournisseurSave);
	}

	@Override
	public List<PaiementCommandeVenteDto> findAll() {
		// TODO Auto-generated method stub
		List<PaiementCommandeVente> paiementCommandeFournisseurs = paiementCommandeVenteRepository.findAll();
		List<PaiementCommandeVenteDto> paiementCommandeFournisseurDtos = new ArrayList<PaiementCommandeVenteDto>();
		paiementCommandeFournisseurs.forEach(paiementCommandeFournisseur -> paiementCommandeFournisseurDtos.add(Mapper.topaiementCommandeVenteDto(paiementCommandeFournisseur)));
		return paiementCommandeFournisseurDtos;
	}

	@Override
	public PaiementCommandeVenteDto getPaiementCommandeFournisseur(String uuid) {
		// TODO Auto-generated method stub
		PaiementCommandeVente paiementCommandeFournisseur = paiementCommandeVenteRepository.findById(uuid).orElseThrow(null);
		return Mapper.topaiementCommandeVenteDto(paiementCommandeFournisseur);
	}

	@Override
	public void deletePaiementCommandeFournisseur(String uuid) {
		// TODO Auto-generated method stub
		paiementCommandeVenteRepository.deleteById(uuid);
		
	}

	@Override
	public PageDataDto<PaiementCommandeVenteDto> listePaiementCommandeFournisseurs(int page, int size,
			String key) {
		// TODO Auto-generated method stub
		return null;
	}

}
 