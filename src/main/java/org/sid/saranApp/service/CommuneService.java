package org.sid.saranApp.service;

import java.util.List;

import org.sid.saranApp.dto.CommandeVenteDto;
import org.sid.saranApp.dto.CommuneDto;
import org.sid.saranApp.dto.PageDataDto;
import org.sid.saranApp.dto.VilleDto;

public interface CommuneService {
	
	CommuneDto addCommune(CommuneDto communeDto);
	CommuneDto updateCommune(CommuneDto communeDto,String uuid);
	List<CommuneDto> findAll();
	CommuneDto getCommune(String uuid);
	void deleteCommune(String uuid);
	List<CommuneDto> listeCommuneByVille(String uuidVille);
	PageDataDto<CommuneDto> listeCommunes(int page,int size,String key);
	
	
}
