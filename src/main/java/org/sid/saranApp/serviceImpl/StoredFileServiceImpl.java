package org.sid.saranApp.serviceImpl;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.sid.saranApp.model.StoredFile;
import org.sid.saranApp.repository.StoredFileRepository;
import org.sid.saranApp.service.StoredFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StoredFileServiceImpl implements StoredFileService{
	
	private final Path root = Paths.get("uploads");
	
	@Autowired
	private StoredFileRepository storedFileRepository;

	@Override
	public StoredFile uploadStoredFile(MultipartFile file) throws IOException {
		// TODO Auto-generated method stub
		 String fileName = StringUtils.cleanPath(file.getOriginalFilename()); 
	        StoredFile storedFile = new StoredFile(fileName, file.getContentType(), file.getBytes());
	        return storedFileRepository.save(storedFile);
	}

	@Override
	public StoredFile getStoredFile(String uuid) {
		// TODO Auto-generated method stub
		return storedFileRepository.getById(uuid);
	}

	@Override
	public StoredFile getStoredFileById(String uuid) {
		// TODO Auto-generated method stub
		return storedFileRepository.getById(uuid);
	}

	@Override
	public List<StoredFile> loadAll() {
		// TODO Auto-generated method stub
		return storedFileRepository.findAll();
	}

	
}
