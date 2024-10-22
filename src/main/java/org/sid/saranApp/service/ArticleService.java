package org.sid.saranApp.service;

import java.util.List;

import org.sid.saranApp.dto.ArticleDto;
import org.sid.saranApp.dto.ArticleSelectDto;
import org.springframework.web.multipart.MultipartFile;

public interface ArticleService {

	ArticleDto addArticle(ArticleDto articleDto);

	void deleteArticle(String uuid);

	List<ArticleDto> findAll();

	ArticleDto getArticle(String uuid);

	void importationArticle(MultipartFile file);

	List<ArticleSelectDto> listeSelect();

	ArticleDto updateArticle(ArticleDto articleDto, String uuid);

}
