package org.sid.saranApp.service;

import java.util.List;

import org.sid.saranApp.dto.PageDataDto;
import org.sid.saranApp.dto.ParametreDto;
import org.sid.saranApp.dto.PaysDto;
import org.springframework.web.multipart.MultipartFile;

public interface PaysService {
	
	PaysDto addPays(PaysDto paysDto);
	PaysDto upadtePays(PaysDto paysDto,String uuid);
	List<PaysDto> findAll();
	PaysDto getPays(String uuid);
	void deletePays(String uuid);
	PageDataDto<PaysDto> listePays(int page,int size,String key);
	
	void importationPays(MultipartFile file);

}
