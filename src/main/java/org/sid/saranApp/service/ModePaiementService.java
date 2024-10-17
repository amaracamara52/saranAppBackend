package org.sid.saranApp.service;

import java.util.List;

import org.sid.saranApp.dto.ModePaiementDto;

public interface ModePaiementService {
	
	ModePaiementDto addModePaiement(ModePaiementDto modePaiementDto);
	ModePaiementDto updModePaiement(ModePaiementDto modePaiementDto,String uuid);
	List<ModePaiementDto> findAll();
	ModePaiementDto getModePaiement(String uuid);
	void deleteModePaiement(String uuid);

}
