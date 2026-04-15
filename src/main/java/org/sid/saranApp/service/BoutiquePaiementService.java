package org.sid.saranApp.service;

import org.sid.saranApp.dto.BoutiquePaiementDto;
import org.sid.saranApp.dto.PageDataDto;

import java.util.List;

public interface BoutiquePaiementService {
	
	BoutiquePaiementDto addBoutiquePaiement(BoutiquePaiementDto boutiquePaiementDto);
	BoutiquePaiementDto updateBoutiquePaiement(BoutiquePaiementDto boutiquePaiementDto,String uuid);
	List<BoutiquePaiementDto> findAll();
	BoutiquePaiementDto getBoutiquePaiement(String uuid);
	void deleteBoutiquePaiement(String uuid);
	
	PageDataDto<BoutiquePaiementDto> listeBoutiquePaiements(int page,int size,String key);

}
