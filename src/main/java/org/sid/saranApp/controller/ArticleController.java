package org.sid.saranApp.controller;

import java.io.IOException;
import java.util.List;

import org.sid.saranApp.dto.ArticleDto;
import org.sid.saranApp.dto.ArticleSelectDto;
import org.sid.saranApp.dto.CommandeFournisseurDto;
import org.sid.saranApp.dto.PageDataDto;
import org.sid.saranApp.enume.StatusCommandeFournisseurEnum;
import org.sid.saranApp.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin
public class ArticleController {

	@Autowired
	private ArticleService articleService;

	@PostMapping("/article")
	public ArticleDto addArticle(@RequestBody ArticleDto articleDto) {
		return articleService.addArticle(articleDto);
	}

	@DeleteMapping("/article/{uuid}")
	public void deleteArticle(@PathVariable String uuid) {
		articleService.deleteArticle(uuid);
	}

	@GetMapping("/article")
	public List<ArticleDto> findAll() {
		return articleService.findAll();

	}

	@GetMapping("/article/{uuid}")
	public ArticleDto getArticle(@PathVariable String uuid) {
		return articleService.getArticle(uuid);
	}

	@PostMapping("/importation/article")
	public void importExcelFileRayon(@RequestParam("file") MultipartFile file) throws IOException {
		// List<String[]> data = excelService.readExcelFile(file);
		articleService.importationArticle(file);
	}

	@GetMapping("/article/article-select")
	public List<ArticleSelectDto> listeArticleSelect() {
		return articleService.listeSelect();
	}

	@PutMapping("/article/{uuid}")
	public ArticleDto updateArticle(@RequestBody ArticleDto articleDto, @PathVariable String uuid) {
		return articleService.updateArticle(articleDto, uuid);
	}
	
	@GetMapping("/article/page_article")
    public PageDataDto<ArticleDto> getArticles(
        @RequestParam(required = false) String key,
        @RequestParam(required = true,defaultValue = "0") int page,
        @RequestParam(required = true,defaultValue = "10") int size
    ) {
        return articleService.listeArticles(page, size, key);
    }
	
	

}
