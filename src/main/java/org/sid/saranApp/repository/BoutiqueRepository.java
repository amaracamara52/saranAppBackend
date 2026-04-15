package org.sid.saranApp.repository;

import org.sid.saranApp.model.Boutique;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BoutiqueRepository extends JpaRepository<Boutique, String> {
	
	@Query("select b from Boutique b where b.libelleBoutique=:x")
	public Boutique getBoutique(@Param("x") String libelle);

	public Optional<Boutique> findByCode(String code);

	@Query("select b.uuid from Boutique b where b.boutiquePrincipale.uuid in :uuids")
	List<String> findUuidsByBoutiquePrincipaleUuidIn(@Param("uuids") List<String> uuids);

}
