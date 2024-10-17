package org.sid.saranApp.service;

import java.util.List;

import org.sid.saranApp.dto.VilleDto;

public interface VilleService {

	VilleDto addVille(VilleDto villeDto);
	VilleDto updateVille(VilleDto villeDto,String uuid);
	List<VilleDto> findAll();
	VilleDto getVille(String uuid);
	void deleteVille(String uuid);
	List<VilleDto> listeVilleByPays(String uuidPays);
}
