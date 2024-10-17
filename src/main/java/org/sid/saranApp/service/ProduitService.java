package org.sid.saranApp.service;

import java.util.List;

import org.sid.saranApp.dto.ProduitDto;

public interface ProduitService {

	public ProduitDto add(ProduitDto produitDto);
	public ProduitDto update(ProduitDto produitDto, String uuid);
	public ProduitDto supprimer(String uuid);
	public List<ProduitDto> findAll();
	public ProduitDto getById(String uuid);
}
