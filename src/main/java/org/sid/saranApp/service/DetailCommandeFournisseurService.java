package org.sid.saranApp.service;

import java.util.List;

import org.sid.saranApp.dto.DetailCommandeFournisseurDto;

public interface DetailCommandeFournisseurService {
	
	DetailCommandeFournisseurDto addDetailCommandeFournisseur(DetailCommandeFournisseurDto detailCommandeFournisseurDto);
	DetailCommandeFournisseurDto updateDetailCommandeFournisseur(DetailCommandeFournisseurDto detailCommandeFournisseurDto, String uuid);
	List<DetailCommandeFournisseurDto> findAll();
	DetailCommandeFournisseurDto getDetailCommandeFournisseur(String uuid);
	void deleteDetailCommandeFournisseur(String uuid);

}
