package org.sid.saranApp.service;

import org.sid.saranApp.dto.PageDataDto;
import org.sid.saranApp.dto.QuartierDto;

import java.util.List;

public interface QuartierService {
	
  QuartierDto addQuartier(QuartierDto quartierDto);
  QuartierDto updateQuartier(QuartierDto quartierDto,String uuid);
  List<QuartierDto> findAll();
  QuartierDto getQuartier(String uuid);
  void deleteQuartier(String uuid);
  PageDataDto<QuartierDto> listeQuartiers(int page,int size,String key);
  
}
