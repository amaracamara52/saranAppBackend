package org.sid.saranApp.service;

import org.sid.saranApp.model.StoredFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;



public interface StoredFileService {
	
	 StoredFile uploadStoredFile(MultipartFile file) throws IOException;

	    StoredFile getStoredFile(String uuid);

	    StoredFile getStoredFileById(String uuid);
	    
	    List<StoredFile>  loadAll();
	    void delete(String uuid);

}
