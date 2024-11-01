package org.sid.saranApp.service;

import java.util.List;

import org.sid.saranApp.dto.CaracteristiqueArticleDto;
import org.sid.saranApp.dto.CaracteristiqueProduitDto;
import org.sid.saranApp.dto.PageDataDto;

public interface CaracteristiqueProduitService {
	
	public CaracteristiqueProduitDto add(CaracteristiqueProduitDto caracteristiqueProduitDto);
	public CaracteristiqueProduitDto update(CaracteristiqueProduitDto caracteristiqueProduitDto, String uuid);
	public CaracteristiqueProduitDto supprimer(String uuid);
	public List<CaracteristiqueProduitDto> findAll();
	public CaracteristiqueProduitDto getById(String uuid);
	PageDataDto<CaracteristiqueProduitDto> listeCaracteristiqueProduit(int page,int size,String key);

}
