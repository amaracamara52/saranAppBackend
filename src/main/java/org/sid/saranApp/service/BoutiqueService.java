package org.sid.saranApp.service;

import org.sid.saranApp.dto.BoutiqueDto;
import org.sid.saranApp.dto.PageDataDto;

import java.util.List;

public interface BoutiqueService {
	
	BoutiqueDto addBoutique(BoutiqueDto boutiqueDto);
	BoutiqueDto updateBoutique(BoutiqueDto boutiqueDto,String uuid);
	BoutiqueDto getBoutique(String uuid);
	List<BoutiqueDto> findAll();
	void deleteBoutique(String uuid);
	BoutiqueDto synchronization(String code);
	BoutiqueDto getByCode(String code);
	PageDataDto<BoutiqueDto> listeBoutiques(int page,int size,String key);

}
