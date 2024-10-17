package org.sid.saranApp.service;

import java.util.List;

import org.sid.saranApp.dto.CategorieDto;

public interface CategorieService {
	
	 CategorieDto addCategorie(CategorieDto categorieDto);
	 CategorieDto updateCategorie(CategorieDto categorieDto, String uuid);
	 List<CategorieDto> findAll();
	 CategorieDto getCategorie(String uuid);
	 void deleteCategorie(String uuid);

}
