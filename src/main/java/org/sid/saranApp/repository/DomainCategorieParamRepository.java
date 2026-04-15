package org.sid.saranApp.repository;

import org.sid.saranApp.model.DomainCategorieParam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DomainCategorieParamRepository extends JpaRepository<DomainCategorieParam, String> {
	
	@Query("select d from DomainCategorieParam d where d.domainCategorie.uuid=:x")
	public List<DomainCategorieParam> listeDomainCategorieParamByDomainCategorie(@Param("x") String uuidDomainCategorie);
	
	
	@Query("select d from DomainCategorieParam d where d.domainCategorie.libelle=:x")
	public List<DomainCategorieParam> listeDomainCategorieParamByDomainCategorieLibelle(@Param("x") String libelle);

}
