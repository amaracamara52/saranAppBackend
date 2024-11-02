package org.sid.saranApp.service;

import java.util.List;

import org.sid.saranApp.dto.ArticleDto;
import org.sid.saranApp.dto.ArticleSelectDto;
import org.sid.saranApp.dto.PageDataDto;
import org.springframework.web.multipart.MultipartFile;

public interface ArticleService {

	ArticleDto addArticle(ArticleDto articleDto);

	void deleteArticle(String uuid);

	List<ArticleDto> findAll();

	ArticleDto getArticle(String uuid);

	void importationArticle(MultipartFile file);

	List<ArticleSelectDto> listeSelect();

	ArticleDto updateArticle(ArticleDto articleDto, String uuid);
	
	PageDataDto<ArticleDto> listeArticles(int page,int size,String key);
	
	void delete(String uuid);

}
