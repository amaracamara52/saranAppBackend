package org.sid.saranApp.service;

import java.util.List;

import org.sid.saranApp.dto.ArticleDto;

public interface ArticleService {
	
	ArticleDto addArticle(ArticleDto articleDto);
	ArticleDto updateArticle(ArticleDto articleDto, String uuid);
	List<ArticleDto> findAll();
	ArticleDto getArticle(String uuid);
	void deleteArticle(String uuid);

}
