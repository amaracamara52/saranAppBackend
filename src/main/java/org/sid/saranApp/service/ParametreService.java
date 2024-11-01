package org.sid.saranApp.service;

import java.util.List;

import org.sid.saranApp.dto.PageDataDto;
import org.sid.saranApp.dto.PaiementCommandeFournisseurDto;
import org.sid.saranApp.dto.ParametreDto;

public interface ParametreService {
	
	ParametreDto addParametre(ParametreDto parametreDto);
	ParametreDto updateParametre(ParametreDto parametreDto, String uuid);
	List<ParametreDto> findAll();
	ParametreDto getParametre(String uuid);
	void deleteParametre(String uuid);
	PageDataDto<ParametreDto> listeParametres(int page,int size,String key);

}
