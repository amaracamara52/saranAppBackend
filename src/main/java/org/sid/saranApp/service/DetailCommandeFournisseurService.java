package org.sid.saranApp.service;

import java.util.List;

import org.sid.saranApp.dto.CommuneDto;
import org.sid.saranApp.dto.DetailCommandeFournisseurDto;
import org.sid.saranApp.dto.PageDataDto;

public interface DetailCommandeFournisseurService {
	
	DetailCommandeFournisseurDto addDetailCommandeFournisseur(DetailCommandeFournisseurDto detailCommandeFournisseurDto);
	DetailCommandeFournisseurDto updateDetailCommandeFournisseur(DetailCommandeFournisseurDto detailCommandeFournisseurDto, String uuid);
	List<DetailCommandeFournisseurDto> findAll();
	DetailCommandeFournisseurDto getDetailCommandeFournisseur(String uuid);
	void deleteDetailCommandeFournisseur(String uuid);
	PageDataDto<DetailCommandeFournisseurDto> listeDetailCommandeFournisseurs(int page,int size,String key);

}
