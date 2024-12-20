/**
 *
 */
package org.sid.saranApp.service;

import java.util.List;

import org.sid.saranApp.dto.DetailCommandeFournisseurDto;
import org.sid.saranApp.dto.EtagereDto;
import org.sid.saranApp.dto.EtagereRayonDto;
import org.sid.saranApp.dto.PageDataDto;
import org.sid.saranApp.dto.RayonDto;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 */
public interface EtagereRayonService {

	EtagereDto addEtagere(EtagereDto etagereDto);

	EtagereRayonDto addEtagereRayon(EtagereRayonDto etagereRayonDto);

	RayonDto addRayon(RayonDto rayonDto);

	EtagereRayonDto getEtagereRayon(String uuid);

	List<String[]> importationEtagere(MultipartFile file);

	List<String[]> importationRayon(MultipartFile file);

	List<EtagereDto> listeEtagere();

	List<EtagereRayonDto> listeEtagereRayon();

	List<RayonDto> listeRayons();

	EtagereDto updateEtagere(EtagereDto etagereDto, String uuid);

	EtagereRayonDto updateEtagereRayon(EtagereRayonDto etagereRayonDto, String uuid);

	RayonDto updateRayon(RayonDto rayonDto, String uuid);
	
	PageDataDto<EtagereRayonDto> listeEtagereRayons(int page,int size,String key);
	void delete(String uuid);
}
