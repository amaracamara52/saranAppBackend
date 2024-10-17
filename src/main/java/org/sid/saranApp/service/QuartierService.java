package org.sid.saranApp.service;

import java.util.List;

import org.sid.saranApp.dto.QuartierDto;

public interface QuartierService {
	
  QuartierDto addQuartier(QuartierDto quartierDto);
  QuartierDto updateQuartier(QuartierDto quartierDto,String uuid);
  List<QuartierDto> findAll();
  QuartierDto getQuartier(String uuid);
  void deleteQuartier(String uuid);

}
