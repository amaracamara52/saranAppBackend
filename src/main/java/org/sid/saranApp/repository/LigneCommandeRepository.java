package org.sid.saranApp.repository;

import org.sid.saranApp.dto.rapport.TopProduitDTO;
import org.sid.saranApp.model.LigneCommande;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface LigneCommandeRepository extends JpaRepository<LigneCommande, String> {

    @Query("select l from LigneCommande l where l.commandeVente.uuid = :commandeUuid")
    List<LigneCommande> findByCommandeVenteUuid(@Param("commandeUuid") String commandeUuid);

//	@Query("select c.produit, SUM(c.quantite) AS total_vendu, c from CommandeVente c where  c.boutique.uuid =:z "
//			+ "groub by c.produit" + "order by total_vendu desc" + "limit :x ")
//	List<LigneCommande> topCommandeVente(@Param("x") int limit, @Param("z") String uuid);

//	@Query("select new org.sid.saranApp.dto.LigneDto(c.produit.uuid, SUM(c.quantite)) c from CommandeVente c where  c.boutique.uuid =:z "
//			+ " groub by c.produit  order by SUM(c.quantite) desc + limit :x ")
//	List<LigneDto> topCommandeVenteDto(@Param("x") int limit, @Param("z") String uuid);



    /**
     * Récupère les top produits vendus avec leur prix depuis StockUniteVente
     * Note: Le prix est récupéré depuis StockUniteVente (prix maximum disponible)
     */
    @Query("SELECT new org.sid.saranApp.dto.rapport.TopProduitDTO(" +
            "lc.produit.uuid, " +
            "p.article.libelle, " +
            "SUM(lc.quantite), " +
            "MAX(suv.price)) " +
            "FROM LigneCommande lc " +
            "JOIN Produit p ON lc.produit.uuid = p.uuid " +
            "LEFT JOIN StockUniteVente suv ON suv.produit.uuid = p.uuid " +
            "WHERE lc.isDelete = false AND p.isDelete = false " +
            "GROUP BY lc.produit.uuid, p.article.libelle " +
            "ORDER BY SUM(lc.quantite) DESC")
    List<TopProduitDTO> findTopProduitsVendus(Pageable pageable);

    /**
     * Récupère les top produits vendus avec catégorie et prix depuis StockUniteVente
     * Note: Le prix est récupéré depuis StockUniteVente (prix maximum disponible)
     */
    @Query("SELECT new org.sid.saranApp.dto.rapport.TopProduitDTO(" +
            "lc.produit.uuid, " +
            "p.article.libelle, " +
            "SUM(lc.quantite), " +
            "MAX(suv.price), " +
            "p.article.categorie.libelle) " +
            "FROM LigneCommande lc " +
            "JOIN Produit p ON lc.produit.uuid = p.uuid " +
            "LEFT JOIN StockUniteVente suv ON suv.produit.uuid = p.uuid " +
            "WHERE lc.isDelete = false AND p.isDelete = false " +
            "GROUP BY lc.produit.uuid, p.article.libelle, p.article.categorie.libelle " +
            "ORDER BY SUM(lc.quantite) DESC")
    List<TopProduitDTO> findTopProduitsVendusAvecCategorie(Pageable pageable);

    /**
     * Requête native SQL alternative pour les top produits vendus
     * Note: Le prix est récupéré depuis stock_unite_vente (prix maximum disponible)
     */
    @Query(value = "SELECT " +
            "p.uuid as produitUuid, " +
            "a.libelle as libelleArticle, " +
            "SUM(lc.quantite) as totalQuantiteVendue, " +
            "COALESCE(MAX(suv.price), 0.0) as prixVente " +
            "FROM ligne_commande lc " +
            "JOIN produit p ON lc.produit_uuid = p.uuid " +
            "JOIN article a ON p.article_uuid = a.uuid " +
            "LEFT JOIN stock_unite_vente suv ON suv.produit_uuid = p.uuid " +
            "WHERE lc.is_delete = false AND p.is_delete = false " +
            "GROUP BY p.uuid, a.libelle " +
            "ORDER BY SUM(lc.quantite) DESC " +
            "LIMIT :limit",
            nativeQuery = true)
    List<Object[]> findTopProduitsVendusNative(@Param("limit") int limit);

    /**
     * Top produits vendus par période avec prix depuis StockUniteVente
     */
    @Query("SELECT new org.sid.saranApp.dto.rapport.TopProduitDTO(" +
            "lc.produit.uuid, " +
            "p.article.libelle, " +
            "SUM(lc.quantite), " +
            "MAX(suv.price), " +
            "p.article.categorie.libelle) " +
            "FROM LigneCommande lc " +
            "JOIN Produit p ON lc.produit.uuid = p.uuid " +
            "LEFT JOIN StockUniteVente suv ON suv.produit.uuid = p.uuid " +
            "WHERE lc.isDelete = false AND p.isDelete = false " +
            "AND lc.dateCreated BETWEEN :dateDebut AND :dateFin " +
            "GROUP BY lc.produit.uuid, p.article.libelle, p.article.categorie.libelle " +
            "ORDER BY SUM(lc.quantite) DESC")
    List<TopProduitDTO> findTopProduitsVendusByPeriode(
            @Param("dateDebut") Date dateDebut,
            @Param("dateFin") Date dateFin,
            Pageable pageable);

    /**
     * Top produits vendus par boutique avec prix depuis StockUniteVente
     */
    @Query("SELECT new org.sid.saranApp.dto.rapport.TopProduitDTO(" +
            "lc.produit.uuid, " +
            "p.article.libelle, " +
            "SUM(lc.quantite), " +
            "MAX(suv.price)) " +
            "FROM LigneCommande lc " +
            "JOIN Produit p ON lc.produit.uuid = p.uuid " +
            "LEFT JOIN StockUniteVente suv ON suv.produit.uuid = p.uuid " +
            "WHERE lc.isDelete = false AND p.isDelete = false " +
            "AND lc.utilisateur.boutique.uuid = :boutiqueUuid " +
            "GROUP BY lc.produit.uuid, p.article.libelle " +
            "ORDER BY SUM(lc.quantite) DESC")
    List<TopProduitDTO> findTopProduitsVendusByBoutique(
            @Param("boutiqueUuid") String boutiqueUuid,
            Pageable pageable);

    /**
     * Top produits vendus pour une boutique sur une période (lignes créées dans l'intervalle).
     */
    @Query("SELECT new org.sid.saranApp.dto.rapport.TopProduitDTO(" +
            "lc.produit.uuid, " +
            "p.article.libelle, " +
            "SUM(lc.quantite), " +
            "MAX(suv.price), " +
            "p.article.categorie.libelle) " +
            "FROM LigneCommande lc " +
            "JOIN Produit p ON lc.produit.uuid = p.uuid " +
            "LEFT JOIN StockUniteVente suv ON suv.produit.uuid = p.uuid " +
            "WHERE lc.isDelete = false AND p.isDelete = false " +
            "AND lc.utilisateur.boutique.uuid = :boutiqueUuid " +
            "AND lc.dateCreated BETWEEN :dateDebut AND :dateFin " +
            "GROUP BY lc.produit.uuid, p.article.libelle, p.article.categorie.libelle " +
            "ORDER BY SUM(lc.quantite) DESC")
    List<TopProduitDTO> findTopProduitsVendusByBoutiqueAndPeriode(
            @Param("boutiqueUuid") String boutiqueUuid,
            @Param("dateDebut") Date dateDebut,
            @Param("dateFin") Date dateFin,
            Pageable pageable);

    /**
     * Top produits vendus par catégorie avec prix depuis StockUniteVente
     */
    @Query("SELECT new org.sid.saranApp.dto.rapport.TopProduitDTO(" +
            "lc.produit.uuid, " +
            "p.article.libelle, " +
            "SUM(lc.quantite), " +
            "MAX(suv.price), " +
            "p.article.categorie.libelle) " +
            "FROM LigneCommande lc " +
            "JOIN Produit p ON lc.produit.uuid = p.uuid " +
            "LEFT JOIN StockUniteVente suv ON suv.produit.uuid = p.uuid " +
            "WHERE lc.isDelete = false AND p.isDelete = false " +
            "AND p.article.categorie.libelle = :categorieLibelle " +
            "GROUP BY lc.produit.uuid, p.article.libelle, p.article.categorie.libelle " +
            "ORDER BY SUM(lc.quantite) DESC")
    List<TopProduitDTO> findTopProduitsVendusByCategorie(
            @Param("categorieLibelle") String categorieLibelle,
            Pageable pageable);

}
