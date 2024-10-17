package org.sid.saranApp.repository;

import org.sid.saranApp.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, String>{
	
	boolean existsByEmail(String email);
	Client existsByUuid(String uuid);
	

}
