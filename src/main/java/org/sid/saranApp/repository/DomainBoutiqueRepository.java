package org.sid.saranApp.repository;

import org.sid.saranApp.model.DomainBoutique;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DomainBoutiqueRepository extends JpaRepository<DomainBoutique, String>{
	
	@Query("select d from DomainBoutique d where d.libelle =:x")
	public DomainBoutique getDomainBoutiqueByLibelle(@Param("x") String libelle);

}
