package org.sid.saranApp.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.sid.saranApp.dto.CaracteristiqueArticleDto;
import org.sid.saranApp.dto.PageDataDto;
import org.sid.saranApp.mapper.Mapper;
import org.sid.saranApp.model.Article;
import org.sid.saranApp.model.CaracteristiqueArticle;
import org.sid.saranApp.model.CaracteristiqueProduit;
import org.sid.saranApp.repository.ArticleRepository;
import org.sid.saranApp.repository.CaracteristiqueArticleRepository;
import org.sid.saranApp.repository.CaracteristiqueProduitRepository;
import org.sid.saranApp.service.CaracteristiqueArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CaracteristiqueArticleServiceImpl implements CaracteristiqueArticleService {
	
	@Autowired
	private CaracteristiqueArticleRepository caracteristiqueArticleRepository;
	@Autowired
	private ArticleRepository articleRepository;
	@Autowired
	private CaracteristiqueProduitRepository caracteristiqueProduitRepository;
	
	 Logger logger = LoggerFactory.getLogger(CaracteristiqueArticleServiceImpl.class);

	@Override
	public CaracteristiqueArticleDto addCaracteristiqueArticle(CaracteristiqueArticleDto caracteristiqueArticleDto) {
		// TODO Auto-generated method stub
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		logger.info("hello {}",auth.getName());
		CaracteristiqueArticle caracteristiqueArticle = new CaracteristiqueArticle();
		Article article = articleRepository.findById(caracteristiqueArticleDto.getUuidArticle()).orElseThrow(null);
		CaracteristiqueProduit caracteristiqueProduit = caracteristiqueProduitRepository.findById(caracteristiqueArticleDto.getUuidCaracteristiqueProduit()).orElseThrow(null);
		caracteristiqueArticle.setLibelle(caracteristiqueArticleDto.getLibelle());
		caracteristiqueArticle.setArticle(article);
		//caracteristiqueArticle.setCaracteristiqueProduit(caracteristiqueProduit);
		CaracteristiqueArticle caracteristiqueArticleSave = caracteristiqueArticleRepository.save(caracteristiqueArticle);
		return Mapper.toCaracteristiqueArticleDto(caracteristiqueArticleSave);
	}

	@Override
	public CaracteristiqueArticleDto updateCaracteristiqueArticle(CaracteristiqueArticleDto caracteristiqueArticleDto, String uuid) {
		// TODO Auto-generated method stub
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Article article = articleRepository.findById(caracteristiqueArticleDto.getUuidArticle()).orElseThrow(null);
		CaracteristiqueProduit caracteristiqueProduit = caracteristiqueProduitRepository.findById(caracteristiqueArticleDto.getUuidCaracteristiqueProduit()).orElseThrow(null);
        CaracteristiqueArticle caracteristiqueArticle = caracteristiqueArticleRepository.findById(uuid).orElseThrow(null);
		caracteristiqueArticle.setLibelle(caracteristiqueArticleDto.getLibelle());
		caracteristiqueArticle.setArticle(article);
		//caracteristiqueArticle.setCaracteristiqueProduit(caracteristiqueProduit);
		CaracteristiqueArticle caracteristiqueArticleSave = caracteristiqueArticleRepository.save(caracteristiqueArticle);
		return Mapper.toCaracteristiqueArticleDto(caracteristiqueArticleSave);
	}

	@Override
	public List<CaracteristiqueArticleDto> findAll() {
		// TODO Auto-generated method stub
		List<CaracteristiqueArticle> caracteristiqueArticles = caracteristiqueArticleRepository.findAll();
		List<CaracteristiqueArticleDto> caracteristiqueArticleDtos = new ArrayList<CaracteristiqueArticleDto>();
		caracteristiqueArticles.forEach(caracteristiqueArticle -> caracteristiqueArticleDtos.add(Mapper.toCaracteristiqueArticleDto(caracteristiqueArticle)));
		return caracteristiqueArticleDtos;
	}

	@Override
	public CaracteristiqueArticleDto getCaracteristiqueArticle(String uuid) {
		// TODO Auto-generated method stub
		CaracteristiqueArticle caracteristiqueArticle = caracteristiqueArticleRepository.findById(uuid).orElseThrow(null);
		return Mapper.toCaracteristiqueArticleDto(caracteristiqueArticle);
	}

	@Override
	public void deleteCaracteristiqueArticle(String uuid) {
		// TODO Auto-generated method stub
		CaracteristiqueArticle caracteristiqueArticle = caracteristiqueArticleRepository.findById(uuid).orElseThrow(null);
		caracteristiqueArticleRepository.delete(caracteristiqueArticle);
		
	}

	@Override
	public PageDataDto<CaracteristiqueArticleDto> listeCaracteristiqueArticle(int page, int size, String key) {
		// TODO Auto-generated method stub
		return null;
	} 

	
}
