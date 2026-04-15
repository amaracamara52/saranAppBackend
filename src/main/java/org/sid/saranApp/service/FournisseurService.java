package org.sid.saranApp.service;

import org.sid.saranApp.dto.FournisseurDto;
import org.sid.saranApp.dto.PageDataDto;

import java.util.List;

public interface FournisseurService {
	
	FournisseurDto addFournisseur(FournisseurDto fournisseurDto);
	FournisseurDto updateFournisseur(FournisseurDto fournisseurDto, String uuid);
	FournisseurDto getFournisseur(String uuid);
	List<FournisseurDto> findAll();
	void deleteFournisseur(String uuid);
	PageDataDto<FournisseurDto> listeFournisseurs(int page,int size,String key);
	void delete(String uuid);
}
