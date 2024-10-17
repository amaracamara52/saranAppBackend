package org.sid.saranApp.repository;

import org.sid.saranApp.model.StoredFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoredFileRepository extends JpaRepository<StoredFile, String> {
	

}
