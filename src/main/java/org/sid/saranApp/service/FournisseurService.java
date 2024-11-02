package org.sid.saranApp.service;

import java.util.List;

import org.sid.saranApp.dto.EtagereRayonDto;
import org.sid.saranApp.dto.FournisseurDto;
import org.sid.saranApp.dto.PageDataDto;

public interface FournisseurService {
	
	FournisseurDto addFournisseur(FournisseurDto fournisseurDto);
	FournisseurDto updateFournisseur(FournisseurDto fournisseurDto, String uuid);
	FournisseurDto getFournisseur(String uuid);
	List<FournisseurDto> findAll();
	void deleteFournisseur(String uuid);
	PageDataDto<FournisseurDto> listeFournisseurs(int page,int size,String key);
	void delete(String uuid);
}
