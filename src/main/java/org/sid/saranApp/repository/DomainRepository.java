package org.sid.saranApp.repository;

import org.sid.saranApp.model.Domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DomainRepository extends JpaRepository<Domain, String> {
	
	@Query("select d from Domain d  where d.libelle <> 'ATNS'")
	public List<Domain> findListeWithoutATNS();

}
