package org.sid.saranApp.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.sid.saranApp.dto.CategorieDto;
import org.sid.saranApp.dto.PageDataDto;
import org.sid.saranApp.mapper.Mapper;
import org.sid.saranApp.model.Boutique;
import org.sid.saranApp.model.Categorie;
import org.sid.saranApp.model.Utilisateur;
import org.sid.saranApp.repository.BoutiqueRepository;
import org.sid.saranApp.repository.CategorieRepository;
import org.sid.saranApp.repository.UtilisateurRepository;
import org.sid.saranApp.service.CategorieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CategorieServiceImpl implements CategorieService {
	
	@Autowired
	private CategorieRepository categorieRepository;

	@Autowired
	private UtilisateurRepository utilisateurRepository;
	
	 Logger logger = LoggerFactory.getLogger(CategorieServiceImpl.class);
	@Override
	public CategorieDto addCategorie(CategorieDto categorieDto) {
		// TODO Auto-generated method stub
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		logger.info("hello {}",auth.getName());
		Categorie categorie = new Categorie();
		Utilisateur utilisateur = utilisateurRepository.findByEmail(auth.getName()).orElseThrow(null);
		categorie.setLibelle(categorieDto.getLibelle());
		categorie.setDescription(categorieDto.getDescription());
		categorie.setBoutique(utilisateur.getBoutique());
		categorie.setUtilisateur(utilisateur);
		
		Categorie categorieSave = categorieRepository.save(categorie);
		return Mapper.toCategorieDto(categorieSave);
	}

	@Override
	public CategorieDto updateCategorie(CategorieDto categorieDto, String uuid) {
		// TODO Auto-generated method stub
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		Utilisateur utilisateur = utilisateurRepository.findByEmail(auth.getName()).orElseThrow(null);
		Categorie categorie = categorieRepository.findById(uuid).orElseThrow(null);
		categorie.setLibelle(categorieDto.getLibelle());
		categorie.setDescription(categorieDto.getDescription());
		categorie.setBoutique(utilisateur.getBoutique());
		categorie.setUtilisateur(utilisateur);
		
		Categorie categorieSave = categorieRepository.save(categorie);
		return Mapper.toCategorieDto(categorieSave);
	}

	@Override
	public List<CategorieDto> findAll() {
		// TODO Auto-generated method stub
		List<Categorie> categories = categorieRepository.findAll();
		List<CategorieDto> categorieDtos = new ArrayList<CategorieDto>();
		categories.forEach(categorie -> categorieDtos.add(Mapper.toCategorieDto(categorie)));
		return categorieDtos;
	}

	@Override
	public CategorieDto getCategorie(String uuid) {
		// TODO Auto-generated method stub
		Categorie categorie = categorieRepository.findById(uuid).orElseThrow(null);
		return Mapper.toCategorieDto(categorie);
	}

	@Override
	public void deleteCategorie(String uuid) {
		// TODO Auto-generated method stub
		Categorie categorie = categorieRepository.findById(uuid).orElseThrow(null);
		categorieRepository.delete(categorie);
		
	}

	@Override
	public PageDataDto<CategorieDto> listeCategories(int page, int size, String key) {
		// TODO Auto-generated method stub
		return null;
	}

}
