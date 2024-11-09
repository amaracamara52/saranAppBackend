package org.sid.saranApp.repository;

import org.sid.saranApp.model.Pays;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PaysRepository extends JpaRepository<Pays, String> {
	
	@Query("select p from Pays p where p.libelle=:x")
	public Pays getPays(@Param("x") String pays);

}
