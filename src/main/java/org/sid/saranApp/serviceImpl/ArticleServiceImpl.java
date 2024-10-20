package org.sid.saranApp.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.sid.saranApp.dto.ArticleDto;
import org.sid.saranApp.dto.ArticleSelectDto;
import org.sid.saranApp.mapper.Mapper;
import org.sid.saranApp.model.Article;
import org.sid.saranApp.model.Categorie;
import org.sid.saranApp.model.Utilisateur;
import org.sid.saranApp.repository.ArticleRepository;
import org.sid.saranApp.repository.CategorieRepository;
import org.sid.saranApp.repository.UtilisateurRepository;
import org.sid.saranApp.service.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	private ArticleRepository articleRepository;
	@Autowired
	private CategorieRepository categorieRepository;
	@Autowired
	private UtilisateurRepository utilisateurRepository;

	Logger logger = LoggerFactory.getLogger(ArticleServiceImpl.class);

	@Override
	public ArticleDto addArticle(ArticleDto articleDto) {
		// TODO Auto-generated method stub
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		logger.info("hello {}", auth.getName());
		Article article = new Article();

		Categorie categorie = categorieRepository.findById(articleDto.getUuidCategorie()).orElseThrow(null);
		Utilisateur utilisateur = utilisateurRepository.findByEmail(auth.getName()).orElseThrow(null);
		article.setLibelle(articleDto.getLibelle());
		article.setDescription(articleDto.getDescription());
		article.setBoutique(utilisateur.getBoutique());
		article.setCategorie(categorie);
		article.setUtilisateur(utilisateur);
		Article articleSave = articleRepository.save(article);
		return Mapper.toArticleDto(articleSave);
	}

	@Override
	public void deleteArticle(String uuid) {
		// TODO Auto-generated method stub
		Article article = articleRepository.findById(uuid).orElseThrow(null);
		articleRepository.delete(article);

	}

	@Override
	public List<ArticleDto> findAll() {
		// TODO Auto-generated method stub
		List<Article> articles = articleRepository.findAll();
		List<ArticleDto> articleDtos = new ArrayList<ArticleDto>();
		articles.forEach(article -> articleDtos.add(Mapper.toArticleDto(article)));
		return articleDtos;
	}

	@Override
	public ArticleDto getArticle(String uuid) {
		// TODO Auto-generated method stub
		Article article = articleRepository.findById(uuid).orElseThrow(null);
		return Mapper.toArticleDto(article);
	}

	@Override
	public List<ArticleSelectDto> listeSelect() {
		// TODO Auto-generated method stub
		List<Article> articles = articleRepository.findAll();
		List<ArticleSelectDto> articleSelectDtos = new ArrayList<ArticleSelectDto>();
		articles.forEach(article -> articleSelectDtos.add(Mapper.toArticleSelectDto(article)));
		return articleSelectDtos;
	}

	@Override
	public ArticleDto updateArticle(ArticleDto articleDto, String uuid) {
		// TODO Auto-generated method stub
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Categorie categorie = categorieRepository.findById(articleDto.getUuidCategorie()).orElseThrow(null);
		Utilisateur utilisateur = utilisateurRepository.findByEmail(auth.getName()).orElseThrow(null);
		Article article = articleRepository.findById(uuid).orElseThrow(null);
		article.setLibelle(articleDto.getLibelle());
		article.setDescription(articleDto.getDescription());
		article.setBoutique(utilisateur.getBoutique());
		article.setCategorie(categorie);
		article.setUtilisateur(utilisateur);
		Article articleSave = articleRepository.save(article);
		return Mapper.toArticleDto(articleSave);
	}

}
