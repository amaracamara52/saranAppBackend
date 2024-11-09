package org.sid.saranApp.repository;

import org.sid.saranApp.model.Boutique;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoutiqueRepository extends JpaRepository<Boutique, String> {
	
	@Query("select b from Boutique b where b.libelleBoutique=:x")
	public Boutique getBoutique(@Param("x") String libelle);

}
