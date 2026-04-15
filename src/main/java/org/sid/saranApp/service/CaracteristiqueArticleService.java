package org.sid.saranApp.service;

import org.sid.saranApp.dto.CaracteristiqueArticleDto;
import org.sid.saranApp.dto.PageDataDto;

import java.util.List;

public interface CaracteristiqueArticleService {

	CaracteristiqueArticleDto addCaracteristiqueArticle(CaracteristiqueArticleDto caracteristiqueArticleDto);
	CaracteristiqueArticleDto updateCaracteristiqueArticle(CaracteristiqueArticleDto caracteristiqueArticleDto, String uuid);
	List<CaracteristiqueArticleDto> findAll();
	CaracteristiqueArticleDto getCaracteristiqueArticle(String uuid);
	void deleteCaracteristiqueArticle(String uuid);
	PageDataDto<CaracteristiqueArticleDto> listeCaracteristiqueArticle(int page,int size,String key);
	void delete(String uuid);

}
