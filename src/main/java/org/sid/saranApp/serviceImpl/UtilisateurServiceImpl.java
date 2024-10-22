package org.sid.saranApp.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.sid.saranApp.dto.UtilisateurDto;
import org.sid.saranApp.mapper.Mapper;
import org.sid.saranApp.model.Boutique;
import org.sid.saranApp.model.Utilisateur;
import org.sid.saranApp.repository.BoutiqueRepository;
import org.sid.saranApp.repository.UtilisateurRepository;
import org.sid.saranApp.service.UtilisateurService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {

	@Autowired
	private UtilisateurRepository utilisateurRepository;
	@Autowired
	private BoutiqueRepository boutiqueRepository;
	@Autowired
	PasswordEncoder passwordEncoder;

	Logger logger = LoggerFactory.getLogger(UtilisateurServiceImpl.class);

	@Override
	public UtilisateurDto addUtilisateur(UtilisateurDto utilisateurDto) {
		Boutique boutique = boutiqueRepository.findById(utilisateurDto.getBoutique()).orElseThrow(null);
		// TODO Auto-generated method stub
		Utilisateur utilisateur = new Utilisateur();
		utilisateur.setBoutique(boutique);
		utilisateur.setUsername(utilisateurDto.getUsername());
		utilisateur.setRole(utilisateurDto.getRole());
		utilisateur.setPhone(utilisateurDto.getPhone());
		utilisateur.setEmail(utilisateurDto.getEmail());
		utilisateur.setPassword(passwordEncoder.encode(utilisateurDto.getMotDePasse()));
		utilisateur.setAdresse(utilisateurDto.getAdresse());
		Utilisateur utilisateurSave = utilisateurRepository.save(utilisateur);
		return Mapper.toUtilisateurDto(utilisateurSave);
	}

	@Override
	public void deleteUtilisateur(String uuid) {
		// TODO Auto-generated method stub
		Utilisateur utilisateur = utilisateurRepository.findById(uuid).orElseThrow(null);
		utilisateurRepository.delete(utilisateur);

	}

	@Override
	public List<UtilisateurDto> findAll() {
		// TODO Auto-generated method stub
		List<Utilisateur> utilisateurs = utilisateurRepository.findAll();
		List<UtilisateurDto> utilisateurDtos = new ArrayList<UtilisateurDto>();
		utilisateurs.forEach(utilisateur -> utilisateurDtos.add(Mapper.toUtilisateurDto(utilisateur)));
		return utilisateurDtos;
	}

	public Utilisateur getCurentUtilisateur() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Utilisateur user = utilisateurRepository.findByEmail(auth.getName()).orElseThrow(null);
		return user;
	}

	@Override
	public UtilisateurDto getUtilisateur(String uuid) {
		// TODO Auto-generated method stub
		Utilisateur utilisateur = utilisateurRepository.findById(uuid).orElseThrow(null);
		return Mapper.toUtilisateurDto(utilisateur);
	}

	@Override
	public UtilisateurDto getUtilisateurByEmail(String email) {
		Optional<Utilisateur> optionalUtilisateur = utilisateurRepository.findByEmail(email);
		Utilisateur utilisateur = optionalUtilisateur.orElseThrow(null);
		return Mapper.toUtilisateurDto(utilisateur);
	}

	@Override
	public UtilisateurDto updateUtilisateur(UtilisateurDto utilisateurDto, String uuid) {
		// TODO Auto-generated method stub
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Utilisateur utilisateur = utilisateurRepository.findById(uuid).orElseThrow(null);
		utilisateur.setUsername(utilisateurDto.getUsername());
		utilisateur.setPhone(utilisateurDto.getPhone());
		utilisateur.setRole(utilisateurDto.getRole());
		utilisateur.setEmail(utilisateurDto.getEmail());
		utilisateur.setPassword(utilisateurDto.getMotDePasse());
		utilisateur.setAdresse(utilisateurDto.getAdresse());
		utilisateur.setBoutique(utilisateur.getBoutique());
		Utilisateur utlilisateurSave = utilisateurRepository.save(utilisateur);
		return Mapper.toUtilisateurDto(utilisateur);
	}

	@Override
	public UtilisateurDto userLogin() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Utilisateur utilisateur = utilisateurRepository.findByEmail(auth.getName()).orElseThrow(null);

		return Mapper.toUtilisateurDto(utilisateur);
	}
}
