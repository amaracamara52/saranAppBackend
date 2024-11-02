package org.sid.saranApp.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.sid.saranApp.dto.FournisseurDto;
import org.sid.saranApp.dto.PageDataDto;
import org.sid.saranApp.mapper.Mapper;
import org.sid.saranApp.model.Fournisseur;
import org.sid.saranApp.model.Utilisateur;
import org.sid.saranApp.model.Ville;
import org.sid.saranApp.repository.BoutiqueRepository;
import org.sid.saranApp.repository.FournisseurRepository;
import org.sid.saranApp.repository.UtilisateurRepository;
import org.sid.saranApp.repository.VilleRepository;
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
	private VilleRepository villeRepository;
	@Autowired
	private UtilisateurRepository utilisateurRepository;

	Logger logger = LoggerFactory.getLogger(FournisseurServiceImpl.class);

	@Override
	public FournisseurDto addFournisseur(FournisseurDto fournisseurDto) {
		// TODO Auto-generated method stub
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		logger.info("hello {}", auth.getName());
		Fournisseur fournisseur = new Fournisseur();
		Utilisateur utilisateur = utilisateurRepository.findByEmail(auth.getName()).orElseThrow(null);
		Ville ville = villeRepository.findById(fournisseurDto.getUuidVille()).orElseThrow(null);

		fournisseur.setNom(fournisseurDto.getNom());
		fournisseur.setPrenom(fournisseurDto.getPrenom());
		fournisseur.setEmail(fournisseurDto.getEmail());
		fournisseur.setTelephone(fournisseurDto.getTelephone());
		fournisseur.setBoutique(utilisateur.getBoutique());
		fournisseur.setUtilisateur(utilisateur);
		fournisseur.setVille(ville);
		Fournisseur fournisseurSave = fournisseurRepository.save(fournisseur);
		return Mapper.toFournisseurDto(fournisseurSave);

	}

	@Override
	public void deleteFournisseur(String uuid) {
		// TODO Auto-generated method stub
		Fournisseur fournisseur = fournisseurRepository.findById(uuid).orElseThrow(null);
		fournisseurRepository.delete(fournisseur);

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
	public FournisseurDto getFournisseur(String uuid) {
		// TODO Auto-generated method stub
		Fournisseur fournisseur = fournisseurRepository.findById(uuid).orElseThrow(null);
		return Mapper.toFournisseurDto(fournisseur);
	}

	@Override
	public FournisseurDto updateFournisseur(FournisseurDto fournisseurDto, String uuid) {
		// TODO Auto-generated method stub
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		Utilisateur utilisateur = utilisateurRepository.findByEmail(auth.getName()).orElseThrow(null);
		Ville ville = villeRepository.findById(fournisseurDto.getUuidVille()).orElseThrow(null);
		Fournisseur fournisseur = fournisseurRepository.findById(uuid).orElseThrow(null);
		fournisseur.setNom(fournisseurDto.getNom());
		fournisseur.setPrenom(fournisseurDto.getPrenom());
		fournisseur.setEmail(fournisseurDto.getEmail());
		fournisseur.setTelephone(fournisseurDto.getTelephone());
		fournisseur.setBoutique(utilisateur.getBoutique());
		fournisseur.setUtilisateur(utilisateur);
		fournisseur.setVille(ville);
		Fournisseur fournisseurSave = fournisseurRepository.save(fournisseur);
		return Mapper.toFournisseurDto(fournisseurSave);
	}

	@Override
	public PageDataDto<FournisseurDto> listeFournisseurs(int page, int size, String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(String uuid) {
		// TODO Auto-generated method stub
		fournisseurRepository.deleteById(uuid);
	}

}
