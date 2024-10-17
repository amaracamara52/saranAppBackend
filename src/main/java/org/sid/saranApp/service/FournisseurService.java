package org.sid.saranApp.service;

import java.util.List;

import org.sid.saranApp.dto.FournisseurDto;

public interface FournisseurService {
	
	FournisseurDto addFournisseur(FournisseurDto fournisseurDto);
	FournisseurDto updateFournisseur(FournisseurDto fournisseurDto, String uuid);
	FournisseurDto getFournisseur(String uuid);
	List<FournisseurDto> findAll();
	void deleteFournisseur(String uuid);

}
