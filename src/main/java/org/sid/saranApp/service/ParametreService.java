package org.sid.saranApp.service;

import org.sid.saranApp.dto.PageDataDto;
import org.sid.saranApp.dto.ParametreDto;

import java.util.List;

public interface ParametreService {
	
	ParametreDto addParametre(ParametreDto parametreDto);
	ParametreDto updateParametre(ParametreDto parametreDto, String uuid);
	List<ParametreDto> findAll();
	ParametreDto getParametre(String uuid);
	void deleteParametre(String uuid);
	PageDataDto<ParametreDto> listeParametres(int page,int size,String key);

}
