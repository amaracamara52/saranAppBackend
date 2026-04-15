package org.sid.saranApp.repository;

import org.sid.saranApp.model.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategorieRepository extends JpaRepository<Categorie, String> {
	
	@Query("select c from Categorie c where c.boutique.uuid =:x")
	public List<Categorie> getCategorieByBoutique(@Param("x") String uuid);


	@Query("select c from Categorie c where c.libelle =:x")
	public Categorie getCategorieByLibelle(@Param("x") String libelle);

	Optional<Categorie> findFirstByBoutique_UuidAndLibelleIgnoreCase(String boutiqueUuid, String libelle);

}
