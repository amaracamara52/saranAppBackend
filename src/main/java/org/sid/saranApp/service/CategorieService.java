package org.sid.saranApp.service;

import org.sid.saranApp.dto.CategorieDto;
import org.sid.saranApp.dto.PageDataDto;

import java.util.List;

public interface CategorieService {
	
	 CategorieDto addCategorie(CategorieDto categorieDto);
	 CategorieDto updateCategorie(CategorieDto categorieDto, String uuid);
	 List<CategorieDto> findAll();
	 CategorieDto getCategorie(String uuid);
	 void deleteCategorie(String uuid);
	 
	 PageDataDto<CategorieDto> listeCategories(int page,int size,String key);

}
