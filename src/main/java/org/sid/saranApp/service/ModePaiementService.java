package org.sid.saranApp.service;

import org.sid.saranApp.dto.ModePaiementDto;
import org.sid.saranApp.dto.PageDataDto;

import java.util.List;

public interface ModePaiementService {
	
	ModePaiementDto addModePaiement(ModePaiementDto modePaiementDto);
	ModePaiementDto updModePaiement(ModePaiementDto modePaiementDto,String uuid);
	List<ModePaiementDto> findAll();
	ModePaiementDto getModePaiement(String uuid);
	void deleteModePaiement(String uuid);
	PageDataDto<ModePaiementDto> listeModePaiements(int page,int size,String key);
	void delete(String uuid);

}
