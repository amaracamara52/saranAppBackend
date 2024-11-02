package org.sid.saranApp.service;

import java.util.List;

import org.sid.saranApp.dto.PageDataDto;
import org.sid.saranApp.dto.PaysDto;
import org.sid.saranApp.dto.ProduitDto;

public interface ProduitService {

	public ProduitDto add(ProduitDto produitDto);

	public List<ProduitDto> findAll();
	
	public List<ProduitDto> listeProduitAVendre();

	public ProduitDto getById(String uuid);

	public List<ProduitDto> listeStockInferieurA5();

	public List<ProduitDto> listeStockPerime();

	public List<ProduitDto> listeStockPerimeDans3mois();

	public List<ProduitDto> listeTopStock(int limit);

	public ProduitDto supprimer(String uuid);

	public ProduitDto update(ProduitDto produitDto, String uuid);
	
	
	PageDataDto<ProduitDto> listeStockInferieurA5(int page,int size,String key);

	PageDataDto<ProduitDto> listeStockPerime(int page,int size,String key);

	PageDataDto<ProduitDto> listeStockPerimeDans3mois(int page,int size,String key);
	PageDataDto<ProduitDto> listeProduits(int page,int size,String key);
	void delete(String uuid);
}
