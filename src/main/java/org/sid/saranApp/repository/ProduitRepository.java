package org.sid.saranApp.repository;

import org.sid.saranApp.model.Boutique;
import org.sid.saranApp.model.LivraisonCommandeFournisseur;
import org.sid.saranApp.model.Produit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository pour la gestion des produits
 * Note: Les quantités et prix sont maintenant gérés via StockUniteVente, pas directement dans Produit
 */
public interface ProduitRepository extends JpaRepository<Produit, String> {
	
	/**
	 * Récupère tous les produits d'une boutique
	 * @param uuidBoutique UUID de la boutique
	 * @return Liste des produits de la boutique
	 */
	@Query("select p from Produit p where p.boutique.uuid = :y")
	public List<Produit> listeProduits(@Param("y") String uuidBoutique);

	/**
	 * Récupère tous les produits d'une boutique
	 * Note: Le paramètre qtite n'est plus utilisé car le filtrage par quantité
	 * se fait maintenant dans le service via StockUniteVenteService
	 * @param uuidBoutique UUID de la boutique
	 * @return Liste des produits de la boutique (le filtrage par quantité se fait dans le service)
	 */
	@Query("select p from Produit p where p.boutique.uuid = :y")
	public List<Produit> findProduitInferieurA10(@Param("y") String uuidBoutique);

	/**
	 * Récupère les produits périmés à une date donnée pour une boutique
	 * @param date Date de péremption
	 * @param uuidBoutique UUID de la boutique
	 * @return Liste des produits périmés
	 */
	@Query("select p from Produit p where p.datePeremption - :x = 0 and p.boutique.uuid = :y")
	public List<Produit> findProduitPerime(@Param("x") LocalDate date, @Param("y") String uuidBoutique);

	/**
	 * Récupère les produits qui seront périmés dans les 3 prochains mois
	 * @param date Date de référence
	 * @param uuidBoutique UUID de la boutique
	 * @return Liste des produits qui seront périmés dans 3 mois
	 */
	@Query("select p from Produit p where p.datePeremption - :x <= 95 and p.boutique.uuid = :y")
	public List<Produit> findProduitPerimeBy3mois(@Param("x") LocalDate date, @Param("y") String uuidBoutique);

	/**
	 * Récupère tous les produits d'une boutique
	 * Note: Le filtrage par quantité (stock <= 10) se fait maintenant dans le service
	 * via StockUniteVenteService.calculateTotalBaseQuantity()
	 * @param uuidBoutique UUID de la boutique
	 * @return Liste des produits de la boutique (le filtrage par quantité se fait dans le service)
	 */
	@Query("select p from Produit p where p.boutique.uuid = :y")
	List<Produit> listeStockInferieurA5(@Param("y") String uuidBoutique);
	
	/**
	 * Récupère les produits finis d'une boutique
	 * Note: Le filtrage par quantité (stock > 0) se fait maintenant dans le service
	 * via StockUniteVenteService.calculateTotalBaseQuantity()
	 * @param uuidBoutique UUID de la boutique
	 * @return Liste des produits finis de la boutique (le filtrage par quantité se fait dans le service)
	 */
	@Query("select p from Produit p where p.isFinish = true and p.boutique.uuid = :y")
	List<Produit> listeStockAVendre(@Param("y") String uuidBoutique);
	
	/**
	 * Recherche paginée de produits par libellé d'article ou emplacement
	 * @param libelle Libellé de l'article à rechercher
	 * @param emplacement Emplacement à rechercher
	 * @param pageable Paramètres de pagination
	 * @return Page de produits correspondant aux critères
	 */
	@Query("select e from Produit e where "
			+ "LOWER(e.article.libelle) LIKE LOWER(CONCAT('%', :libelle, '%')) or "
			+ "LOWER(concat(e.emplacement.etagere, e.emplacement.code, e.emplacement.rayon)) LIKE LOWER(CONCAT('%', :empl, '%'))")
	Page<Produit> listeProduitByLibelle(
			@Param("libelle") String libelle,
			@Param("empl") String emplacement,
			Pageable pageable);
	
	/**
	 * Récupère tous les produits avec pagination
	 * @param pageable Paramètres de pagination
	 * @return Page de produits
	 */
	@Query("select a from Produit a")
	Page<Produit> listeProduit(Pageable pageable);

	/**
	 * Récupère tous les produits d'une boutique
	 * @param boutique La boutique
	 * @return Liste des produits de la boutique
	 */
	List<Produit> findByBoutique(Boutique boutique);
	
	/**
	 * Récupère tous les produits d'une livraison
	 * @param livraison La livraison
	 * @return Liste des produits de la livraison
	 */
	List<Produit> findByLivraisonCommandeFournisseur(LivraisonCommandeFournisseur livraison);

	/** Premier produit catalogue pour cet article dans la boutique (réception achat sans fiche livraison). */
	Optional<Produit> findFirstByBoutique_UuidAndArticle_Uuid(String boutiqueUuid, String articleUuid);
}
