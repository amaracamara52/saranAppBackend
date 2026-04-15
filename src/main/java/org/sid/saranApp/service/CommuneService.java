package org.sid.saranApp.service;

import org.sid.saranApp.dto.CommuneDto;
import org.sid.saranApp.dto.PageDataDto;

import java.util.List;

public interface CommuneService {
	
	CommuneDto addCommune(CommuneDto communeDto);
	CommuneDto updateCommune(CommuneDto communeDto,String uuid);
	List<CommuneDto> findAll();
	CommuneDto getCommune(String uuid);
	void deleteCommune(String uuid);
	List<CommuneDto> listeCommuneByVille(String uuidVille);
	PageDataDto<CommuneDto> listeCommunes(int page,int size,String key);
	
	
}
