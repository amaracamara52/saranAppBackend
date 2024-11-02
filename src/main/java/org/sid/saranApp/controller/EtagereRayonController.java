/**
 *
 */
package org.sid.saranApp.controller;

import java.io.IOException;
import java.util.List;

import org.sid.saranApp.dto.ArticleDto;
import org.sid.saranApp.dto.EtagereDto;
import org.sid.saranApp.dto.EtagereRayonDto;
import org.sid.saranApp.dto.PageDataDto;
import org.sid.saranApp.dto.RayonDto;
import org.sid.saranApp.service.EtagereRayonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 */
@RestController
@CrossOrigin("*")
public class EtagereRayonController {

	@Autowired
	private EtagereRayonService etagereRayonService;

	@PostMapping("/etagere")
	public EtagereDto addEtagere(@RequestBody EtagereDto etagereDto) {
		// TODO Auto-generated method stub
		return etagereRayonService.addEtagere(etagereDto);
	}

	@PostMapping("emplacement")
	public EtagereRayonDto addEtagereRayon(@RequestBody EtagereRayonDto etagereRayonDto) {
		// TODO Auto-generated method stub
		return etagereRayonService.addEtagereRayon(etagereRayonDto);
	}

	@PostMapping("/rayon")
	public RayonDto addRayon(@RequestBody RayonDto RayonDto) {
		// TODO Auto-generated method stub
		return etagereRayonService.addRayon(RayonDto);
	}

	@GetMapping("/etagere")
	public List<EtagereDto> findAllEtagere() {
		// TODO Auto-generated method stub
		return etagereRayonService.listeEtagere();
	}

	@GetMapping("emplacement")
	public List<EtagereRayonDto> findAllEtagereRayon() {
		// TODO Auto-generated method stub
		return etagereRayonService.listeEtagereRayon();
	}

	@GetMapping("/rayon")
	public List<RayonDto> findAllRayon() {
		// TODO Auto-generated method stub
		return etagereRayonService.listeRayons();
	}

	@GetMapping("emplacement/{uuid}")
	public EtagereRayonDto getEtagereRayon(@PathVariable String uuid) {
		// TODO Auto-generated method stub
		return etagereRayonService.getEtagereRayon(uuid);
	}

	@PostMapping("/emplacement/fileImportation")
	public ResponseEntity<List<String[]>> importExcelFileEtagere(@RequestParam("file") MultipartFile file)
			throws IOException {
		// List<String[]> data = excelService.readExcelFile(file);
		return ResponseEntity.ok(etagereRayonService.importationEtagere(file));
	}

	@PostMapping("/importation/rayon")
	public ResponseEntity<List<String[]>> importExcelFileRayon(@RequestParam("file") MultipartFile file)
			throws IOException {
		// List<String[]> data = excelService.readExcelFile(file);
		return ResponseEntity.ok(etagereRayonService.importationRayon(file));
	}

	@PutMapping("emplacement/{uuid}")
	public EtagereRayonDto updateEtagereRayon(@RequestBody EtagereRayonDto etagereRayonDto, @PathVariable String uuid) {
		// TODO Auto-generated method stub
		return etagereRayonService.updateEtagereRayon(etagereRayonDto, uuid);
	}

	@PutMapping("/etagere/{uuid}")
	public EtagereDto updateEtagereStock(@RequestBody EtagereDto etagereDto, @PathVariable String uuid) {
		// TODO Auto-generated method stub
		return etagereRayonService.updateEtagere(etagereDto, uuid);
	}

	@PutMapping("/rayon/{uuid}")
	public RayonDto updateRayon(@RequestBody RayonDto RayonDto, @PathVariable String uuid) {
		// TODO Auto-generated method stub
		return etagereRayonService.updateRayon(RayonDto, uuid);
	}
	
	@GetMapping("/emplacement/page_emplacement")
    public PageDataDto<EtagereRayonDto> getEtageres(
        @RequestParam(required = false) String key,
        @RequestParam(required = true,defaultValue = "0") int page,
        @RequestParam(required = true,defaultValue = "10") int size
    ) {
        return etagereRayonService.listeEtagereRayons(page, size, key);
    }
	
	@DeleteMapping("/emplacement/{uuid}")
	void deleteEtagereRayonService(@PathVariable String uuid) {
		etagereRayonService.delete(uuid);
	}

}
