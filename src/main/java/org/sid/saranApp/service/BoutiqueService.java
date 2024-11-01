package org.sid.saranApp.service;

import java.util.List;

import org.sid.saranApp.dto.BoutiqueDto;
import org.sid.saranApp.dto.BoutiquePaiementDto;
import org.sid.saranApp.dto.PageDataDto;

public interface BoutiqueService {
	
	BoutiqueDto addBoutique(BoutiqueDto boutiqueDto);
	BoutiqueDto updateBoutique(BoutiqueDto boutiqueDto,String uuid);
	BoutiqueDto getBoutique(String uuid);
	List<BoutiqueDto> findAll();
	void deleteBoutique(String uuid);
	PageDataDto<BoutiqueDto> listeBoutiques(int page,int size,String key);

}
