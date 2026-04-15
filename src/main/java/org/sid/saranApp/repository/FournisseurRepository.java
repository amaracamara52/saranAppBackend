package org.sid.saranApp.repository;

import org.sid.saranApp.model.Fournisseur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FournisseurRepository extends JpaRepository<Fournisseur, String> {
	
	@Query("select f from Fournisseur f where f.boutique.uuid=:x")
	List<Fournisseur> listeFournisseurs(@Param("x") String uuid);

}
