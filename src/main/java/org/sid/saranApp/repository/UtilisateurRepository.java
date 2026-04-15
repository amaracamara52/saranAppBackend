package org.sid.saranApp.repository;

import org.sid.saranApp.model.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, String> {
	
	
	 @Query("select u from Utilisateur u where u.email=:x or u.phone=:x")
	 Optional<Utilisateur> findByEmail(@Param("x") String email);

	@Query("select u from Utilisateur u left join fetch u.boutique where u.email = :x or u.phone = :x")
	Optional<Utilisateur> findByEmailWithBoutique(@Param("x") String x);

	@Query("select u from Utilisateur u where u.boutique.uuid=:x")
	 List<Utilisateur> listeByBoutique(@Param("x") String uuidBoutique);

	@Query("select u from Utilisateur u where u.boutique.uuid in :uuids")
	List<Utilisateur> listeByBoutiques(@Param("uuids") List<String> uuids);

}
