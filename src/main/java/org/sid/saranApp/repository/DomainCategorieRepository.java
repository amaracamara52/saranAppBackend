package org.sid.saranApp.repository;

import org.sid.saranApp.model.DomainCategorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DomainCategorieRepository extends JpaRepository<DomainCategorie, String>{
	
	@Query("select d from DomainCategorie d where d.domaine.uuid=:x")
	public List<DomainCategorie> listeDomainCategorieByDomain(@Param("x") String uuidDomain);

}
