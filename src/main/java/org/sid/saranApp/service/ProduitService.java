package org.sid.saranApp.service;

import java.util.List;

import org.sid.saranApp.dto.ProduitDto;

public interface ProduitService {

	public ProduitDto add(ProduitDto produitDto);

	public List<ProduitDto> findAll();

	public ProduitDto getById(String uuid);

	public List<ProduitDto> listeStockInferieurA5();

	public List<ProduitDto> listeStockPerime();

	public List<ProduitDto> listeStockPerimeDans3mois();

	public List<ProduitDto> listeTopStock(int limit);

	public ProduitDto supprimer(String uuid);

	public ProduitDto update(ProduitDto produitDto, String uuid);
}
