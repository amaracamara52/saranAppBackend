package org.sid.saranApp.serviceImpl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.sid.saranApp.dto.ArticleDto;
import org.sid.saranApp.dto.ArticleSelectDto;
import org.sid.saranApp.dto.CommandeFournisseurDto;
import org.sid.saranApp.dto.PageDataDto;
import org.sid.saranApp.mapper.Mapper;
import org.sid.saranApp.model.Article;
import org.sid.saranApp.model.Categorie;
import org.sid.saranApp.model.CommandeFournisseur;
import org.sid.saranApp.model.Utilisateur;
import org.sid.saranApp.repository.ArticleRepository;
import org.sid.saranApp.repository.CategorieRepository;
import org.sid.saranApp.repository.UtilisateurRepository;
import org.sid.saranApp.service.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	private ArticleRepository articleRepository;
	@Autowired
	private CategorieRepository categorieRepository;
	@Autowired
	private UtilisateurRepository utilisateurRepository;
	@Autowired
	private UtilisateurServiceImpl utilisateurServiceImpl;

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
		List<Article> articles = articleRepository.listeArticles(utilisateurServiceImpl.getCurentUtilisateur().getBoutique().getUuid());
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
	public void importationArticle(MultipartFile file) {
		// TODO Auto-generated method stub
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Utilisateur utilisateur = utilisateurRepository.findByEmail(auth.getName()).orElseThrow(null);

		Categorie categorie = new Categorie();
		categorie.setLibelle("AUCUN");
		categorie.setDescription("AUCUN");
		categorie.setBoutique(utilisateur.getBoutique());
		categorie.setUtilisateur(utilisateur);
		categorie = categorieRepository.save(categorie);

		List<ArticleDto> articleDtos = new ArrayList<>();
		try (InputStream inputStream = file.getInputStream()) {
			Workbook workbook = WorkbookFactory.create(inputStream);
			Sheet sheet = workbook.getSheetAt(0);
			Iterator<Row> rowIterator = sheet.rowIterator();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				if (row.getRowNum() == 0) {
					// skip header row
					continue;
				}
				ArticleDto articleDto = new ArticleDto();
				articleDto.setLibelle(row.getCell(0).getStringCellValue());
				articleDto.setDescription(row.getCell(1).getStringCellValue());
				//articleDto.setQuantiteDansCarton((int) row.getCell(2).getNumericCellValue());

				logger.info("value {}", articleDto.getLibelle());

				Article article = new Article();

				article.setBoutique(utilisateur.getBoutique());
				article.setUtilisateur(utilisateur);
				article.setCategorie(categorie);
				article.setLibelle(articleDto.getLibelle());
				article.setDescription(articleDto.getDescription());
				article.setQuantiteDansCarton(articleDto.getQuantiteDansCarton());
				article = articleRepository.save(article);

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public List<ArticleSelectDto> listeSelect() {
		// TODO Auto-generated method stub
		List<Article> articles = articleRepository.listeArticles(utilisateurServiceImpl.getCurentUtilisateur().getBoutique().getUuid());
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

	@Override
	public PageDataDto<ArticleDto> listeArticles(int page, int size, String key) {
		// TODO Auto-generated method stub
		String uuidBoutique = utilisateurServiceImpl.getCurentUtilisateur().getBoutique().getUuid();
		PageDataDto<ArticleDto> pageDataDto = new PageDataDto<ArticleDto>();
		List<ArticleDto> articleDtos = new ArrayList<ArticleDto>();
		Pageable pageable = PageRequest.of(page, size);
		
		Page<Article> articles = null;
		
		if(key != null) {
			articles = articleRepository.listeArticleByLibelle(key, pageable);
			articles.forEach(article -> articleDtos.add(Mapper.toArticleDto(article)));
		}
		
		if(key == null ) {
			
			articles = articleRepository.listeArticle(pageable,utilisateurServiceImpl.getCurentUtilisateur().getBoutique().getUuid());
			articles.forEach(article -> articleDtos.add(Mapper.toArticleDto(article)));
		}
		
		
		pageDataDto.setData(articleDtos);
		pageDataDto.getPage().setPageNumber(page);
		pageDataDto.getPage().setSize(size);
		pageDataDto.getPage().setTotalElements(articles.getTotalElements());
		pageDataDto.getPage().setTotalPages(articles.getTotalPages());
		return pageDataDto;
	}

	@Override
	public void delete(String uuid) {
		// TODO Auto-generated method stub
		articleRepository.deleteById(uuid);
	}

}
