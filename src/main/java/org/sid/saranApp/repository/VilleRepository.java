package org.sid.saranApp.repository;

import org.sid.saranApp.model.Ville;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VilleRepository extends JpaRepository<Ville, String>{
	
//	@Query("select v from Ville v where v.pays.uuid=:x")
//	List<Ville> listeVilleByPays(@Param("x") String uuidPays);

}
