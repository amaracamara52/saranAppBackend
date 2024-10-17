package org.sid.saranApp.service;

import java.util.List;

import org.sid.saranApp.dto.PaysDto;

public interface PaysService {
	
	PaysDto addPays(PaysDto paysDto);
	PaysDto upadtePays(PaysDto paysDto,String uuid);
	List<PaysDto> findAll();
	PaysDto getPays(String uuid);
	void deletePays(String uuid);
	

}
