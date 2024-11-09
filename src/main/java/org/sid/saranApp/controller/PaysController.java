package org.sid.saranApp.controller;

import java.io.IOException;
import java.util.List;

import org.sid.saranApp.dto.PaysDto;
import org.sid.saranApp.service.PaysService;
import org.springframework.beans.factory.annotation.Autowired;
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


@RestController
@CrossOrigin
public class PaysController {
	
	@Autowired
	private PaysService paysService;
	
	@PostMapping("/pays")
	public PaysDto addPays(@RequestBody PaysDto PaysDto) {
		return paysService.addPays(PaysDto);
	}
	
	@PutMapping("/pays/{uuid}")
	public PaysDto updatePays(@RequestBody PaysDto PaysDto,@PathVariable String uuid) {
		return paysService.upadtePays(PaysDto, uuid);
	}
	
	@GetMapping("/pays/{uuid}")
	public PaysDto getPays(@PathVariable String uuid) {
		return paysService.getPays(uuid);
	}
	
	@GetMapping("/pays")
	public List<PaysDto> findAll(){
		return paysService.findAll();
	}
	@DeleteMapping("/pays/{uuid}")
	public void deletePays(@PathVariable String uuid) {
		paysService.deletePays(uuid);
	}

	@PostMapping("/pays/importationFile")
	public void importExcelFilePays(@RequestParam("file") MultipartFile file) throws IOException {
		// List<String[]> data = excelService.readExcelFile(file);
		paysService.importationPays(file);
	}
			
}
