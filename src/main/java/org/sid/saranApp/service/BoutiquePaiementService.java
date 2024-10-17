package org.sid.saranApp.service;

import java.util.List;

import org.sid.saranApp.dto.BoutiquePaiementDto;

public interface BoutiquePaiementService {
	
	BoutiquePaiementDto addBoutiquePaiement(BoutiquePaiementDto boutiquePaiementDto);
	BoutiquePaiementDto updateBoutiquePaiement(BoutiquePaiementDto boutiquePaiementDto,String uuid);
	List<BoutiquePaiementDto> findAll();
	BoutiquePaiementDto getBoutiquePaiement(String uuid);
	void deleteBoutiquePaiement(String uuid);

}
