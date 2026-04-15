package org.sid.saranApp.repository;

import org.sid.saranApp.model.Commune;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommuneRepository extends JpaRepository<Commune, String> {

	@Query("select v from Commune v where v.ville.uuid=:x")
	List<Commune> listeCommuneByVille(@Param("x") String uuidVille);

}
