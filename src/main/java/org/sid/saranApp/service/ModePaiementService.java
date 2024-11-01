package org.sid.saranApp.service;

import java.util.List;

import org.sid.saranApp.dto.LivraisonCommandeFournisseurDto;
import org.sid.saranApp.dto.ModePaiementDto;
import org.sid.saranApp.dto.PageDataDto;

public interface ModePaiementService {
	
	ModePaiementDto addModePaiement(ModePaiementDto modePaiementDto);
	ModePaiementDto updModePaiement(ModePaiementDto modePaiementDto,String uuid);
	List<ModePaiementDto> findAll();
	ModePaiementDto getModePaiement(String uuid);
	void deleteModePaiement(String uuid);
	PageDataDto<ModePaiementDto> listeModePaiements(int page,int size,String key);

}
