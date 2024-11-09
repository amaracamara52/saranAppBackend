package org.sid.saranApp.controller;

import java.util.ArrayList;
import java.util.List;

import org.sid.saranApp.dto.StoredFileDto;
import org.sid.saranApp.dto.StoredFileInfoDto;
import org.sid.saranApp.mapper.Mapper;
import org.sid.saranApp.model.StoredFile;
import org.sid.saranApp.repository.StoredFileRepository;
import org.sid.saranApp.service.StoredFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin(origins = "*")
public class StoredFileController {
	
StoredFile fileDB = new StoredFile();
    
    @Autowired
    private StoredFileService storedFileService;
    @Autowired
    private StoredFileRepository storedFileRepository; 
    
    Logger logger = LoggerFactory.getLogger(StoredFileController.class);

    @PostMapping("/upload")
    public ResponseEntity<StoredFileInfoDto> uploadStoredFile(@RequestParam("file") MultipartFile file) {
    	System.err.println("Entré");
    	System.err.println(file);
    	String message = "";
      logger.info("file: {}",file);
      try {
          StoredFile storedFile =  storedFileService.uploadStoredFile(file);

        message = "Uploaded the file successfully: " + file.getOriginalFilename();
        
        
        return ResponseEntity.status(HttpStatus.OK).body(Mapper.toStoredFileInfoDto(storedFile));
      } catch (Exception e) {
        message = "Could not upload the file: " + file.getOriginalFilename() + "!";
        System.err.println(message);
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(null);
      }
    }

    @GetMapping("/file/{uuid}")
    public ResponseEntity<StoredFileDto> getStoredFileById(@PathVariable String uuid) {
        StoredFile fileDB = storedFileService.getStoredFileById(uuid);

      return ResponseEntity.ok()
//          .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getName() + "\"")
          .body(Mapper.toStoredFileDto(fileDB));
    }
    
    @GetMapping("/listefile")
    public ResponseEntity<StoredFileInfoDto> getListStoredFile() {
    	System.err.println("Bien entrée dans la fonction");
    	List<StoredFile> listeStoredFile = storedFileRepository.findAll();
		List<StoredFileDto> listeStoredFileDto = new ArrayList<>();
		listeStoredFile.forEach(val->{
			listeStoredFileDto.add(Mapper.toStoredFileDto(val));
			fileDB = val;
			System.err.println(fileDB);
		});
    	
    	

        return ResponseEntity.ok()
//            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getName() + "\"")
        		.body(Mapper.toStoredFileInfoDto(fileDB));
    }
    
    @DeleteMapping("/storeFile/{uuid}")
	void deleteFile(@PathVariable String uuid) {
    	storedFileService.delete(uuid);
	}

}
