package org.sid.saranApp.service;

import java.util.List;

import org.sid.saranApp.dto.FournisseurDto;
import org.sid.saranApp.dto.LigneCommandeDto;
import org.sid.saranApp.dto.PageDataDto;

public interface LigneCommandeService {
	
	public LigneCommandeDto add(LigneCommandeDto ligneCommandeDto);
	public LigneCommandeDto update(LigneCommandeDto ligneCommandeDto, String uuid);
	public LigneCommandeDto supprimer(String uuid);
	public List<LigneCommandeDto> findAll();
	public LigneCommandeDto getById(String uuid);
	PageDataDto<LigneCommandeDto> listeLigneCommandes(int page,int size,String key);
}
