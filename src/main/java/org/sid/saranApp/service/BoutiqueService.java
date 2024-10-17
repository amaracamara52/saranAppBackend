package org.sid.saranApp.service;

import java.util.List;

import org.sid.saranApp.dto.BoutiqueDto;

public interface BoutiqueService {
	
	BoutiqueDto addBoutique(BoutiqueDto boutiqueDto);
	BoutiqueDto updateBoutique(BoutiqueDto boutiqueDto,String uuid);
	BoutiqueDto getBoutique(String uuid);
	List<BoutiqueDto> findAll();
	void deleteBoutique(String uuid);

}
