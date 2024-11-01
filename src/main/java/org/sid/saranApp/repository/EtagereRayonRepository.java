/**
 *
 */
package org.sid.saranApp.repository;

import org.sid.saranApp.model.EtagereRayon;
import org.sid.saranApp.model.EtagereRayon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 */
public interface EtagereRayonRepository extends JpaRepository<EtagereRayon, String> {
	
	
	@Query("select e from EtagereRayon e where  "
			+ "LOWER(e.code) LIKE LOWER(CONCAT('%', :code, '%')) or "
			+ "LOWER(e.etagere) LIKE LOWER(CONCAT('%', :etagere, '%')) or "
			+ "LOWER(e.rayon) LIKE LOWER(CONCAT('%', :rayon, '%'))")
	Page<EtagereRayon> listeEtagereRayonByLibelle(
			@Param("code") String code,
			@Param("etagere") String etagere,
			@Param("rayon") String rayon,
			Pageable pageable);
	
	
	@Query("select a from EtagereRayon a")
	Page<EtagereRayon> listeEtagereRayon(Pageable pageable);

}
