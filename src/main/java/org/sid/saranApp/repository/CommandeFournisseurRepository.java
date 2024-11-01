package org.sid.saranApp.repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.sid.saranApp.enume.StatusCommandeFournisseurEnum;
import org.sid.saranApp.model.CommandeFournisseur;
import org.sid.saranApp.model.CommandeVente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommandeFournisseurRepository extends JpaRepository<CommandeFournisseur, String> {

	@Query("select c from CommandeFournisseur c where  c.boutique.uuid =:z and c.dateCommandeFournisseur between :x and :y ")
	List<CommandeFournisseur> listeCommandePeriode(@Param("x") Date dateDebut, @Param("y") Date dateFin,
			@Param("z") String uuid);

	@Query("select c from CommandeFournisseur c where  c.boutique.uuid =:z and c.dateCommandeFournisseur between :x and :y and c.commandeFournisseurEnum =:s ")
	List<CommandeFournisseur> listeCommandePeriodeByStatus(@Param("x") Date dateDebut, @Param("y") Date dateFin,
			@Param("z") String uuid, @Param("s") String status);

//	@Query(value = "select sum(c.montantCommande) from CommandeFournisseur c  where c.boutique.uuid =:z and c.dateCommandeFournisseur between :x and :y ", nativeQuery = true)
//	int sommeCommandeFournisseur(@Param("x") Date dateDebut, @Param("y") Date dateFin, @Param("z") String uuid);
	
	
	@Query("select c from CommandeFournisseur c where c.boutique.uuid =:b and c.commandeFournisseurEnum =:s ")
	Page<CommandeFournisseur> listeCommandePeriodeByStatusPage(@Param("b") String uuid,@Param("s") StatusCommandeFournisseurEnum status,   Pageable pageable);
	
	@Query("select c from CommandeFournisseur c where c.boutique.uuid =:b ")
	Page<CommandeFournisseur> listeCommandePeriodePage(@Param("b") String uuid, Pageable pageable);
	
	
	@Query("select c from CommandeFournisseur c where  c.boutique.uuid =:z and c.dateCommandeFournisseur between :x and :y ")
	Page<CommandeFournisseur> listeCommandePeriode(
			@Param("z") String uuid,@Param("x") LocalDate dateDebut, @Param("y") LocalDate dateFin,Pageable pageable);

}
