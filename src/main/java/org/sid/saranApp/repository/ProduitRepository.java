package org.sid.saranApp.repository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.sid.saranApp.model.Produit;
import org.sid.saranApp.model.Produit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProduitRepository extends JpaRepository<Produit, String> {

	@Query("select p from Produit p where  p.quantiteImage <= :x and p.boutique.uuid =:y")
	public List<Produit> findProduitInferieurA10(@Param("x") int qtite, @Param("y") String uuidBoutique);

	@Query("select p from Produit p where  p.datePeremption - :x=0 and p.boutique.uuid =:y")
	public List<Produit> findProduitPerime(@Param("x") LocalDate date, @Param("y") String uuidBoutique);

	@Query("select p from Produit p where p.datePeremption - :x  <= 95 and p.boutique.uuid =:y")
	public List<Produit> findProduitPerimeBy3mois(@Param("x") LocalDate date, @Param("y") String uuidBoutique);

	@Query("select p from Produit p where p.quantiteImage <= 10 and p.boutique.uuid =:y")
	List<Produit> listeStockInferieurA5(@Param("y") String uuidBoutique);
	
	@Query("select p from Produit p where p.isFinish=true")
	List<Produit> listeStockAVendre(@Param("y") String uuidBoutique);
	
	
	@Query("select e from Produit e where  "
			+ "LOWER(e.livraisonCommandeFournisseur.detailCommandeFournisseur.article.libelle) LIKE LOWER(CONCAT('%', :libelle, '%')) or "
			+ "LOWER(concat(e.emplacement.etagere,e.emplacement.code,e.emplacement.rayon)) LIKE LOWER(CONCAT('%', :empl, '%'))")
	Page<Produit> listeProduitByLibelle(
			@Param("libelle") String libelle,
			@Param("empl") String emplacement,
			Pageable pageable);
	
	
	@Query("select a from Produit a")
	Page<Produit> listeProduit(Pageable pageable);

}
