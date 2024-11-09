package org.sid.saranApp.repository;

import java.util.Date;
import java.util.List;

import org.sid.saranApp.model.CommandeVente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommandeVenteRepository extends JpaRepository<CommandeVente, String> {

	@Query("select c from CommandeVente c where  c.boutique.uuid =:z and c.datePaiement between :x and :y ")
	List<CommandeVente> listeCommandePeriode(@Param("x") Date dateDebut, @Param("y") Date dateFin,
			@Param("z") String uuid);
	
	
	@Query("select c from CommandeVente c where  c.boutique.uuid =:z ")
	List<CommandeVente> listeCommandes(
			@Param("z") String uuid);

	@Query(value = "select sum(c.montantCommande) from CommandeVente c  where c.boutique.uuid =:z and c.datePaiement between :x and :y ", nativeQuery = true)
	int sommeCommandeVente(@Param("x") Date dateDebut, @Param("y") Date dateFin, @Param("z") String uuid);
	
	
	
	
	
	@Query("select c from CommandeVente c where c.boutique.uuid =:z and c.datePaiement between :x and :y ")
	Page<CommandeVente> listeCommandePeriode(@Param("x") Date dateDebut, @Param("y") Date dateFin,
			@Param("z") String uuid, Pageable pageable);
	
	
	
	@Query("select c from CommandeVente c where c.boutique.uuid =:z and c.datePaiement between :x and :y and LOWER(c.numeroCommande) LIKE LOWER(CONCAT('%', :key, '%')) ")
	Page<CommandeVente> listeCommandePeriodeByKey(@Param("x") Date dateDebut, @Param("y") Date dateFin,
			@Param("z") String uuid,@Param("key") String key, Pageable pageable);

}
