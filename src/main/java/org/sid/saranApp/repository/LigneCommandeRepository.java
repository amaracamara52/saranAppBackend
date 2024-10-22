package org.sid.saranApp.repository;

import org.sid.saranApp.model.LigneCommande;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LigneCommandeRepository extends JpaRepository<LigneCommande, String> {

//	@Query("select c.produit, SUM(c.quantite) AS total_vendu, c from CommandeVente c where  c.boutique.uuid =:z "
//			+ "groub by c.produit" + "order by total_vendu desc" + "limit :x ")
//	List<LigneCommande> topCommandeVente(@Param("x") int limit, @Param("z") String uuid);

//	@Query("select new org.sid.saranApp.dto.LigneDto(c.produit.uuid, SUM(c.quantite)) c from CommandeVente c where  c.boutique.uuid =:z "
//			+ " groub by c.produit  order by SUM(c.quantite) desc + limit :x ")
//	List<LigneDto> topCommandeVenteDto(@Param("x") int limit, @Param("z") String uuid);

}
