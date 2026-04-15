package org.sid.saranApp.repository;

import org.sid.saranApp.dto.rapport.StatistiquesGeneralesDTO;
import org.sid.saranApp.dto.rapport.TopClientDTO;
import org.sid.saranApp.model.CommandeVente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface CommandeVenteRepository extends JpaRepository<CommandeVente, String> {

	@Query("select c from CommandeVente c where  c.boutique.uuid =:z and c.datePaiement between :x and :y ")
	List<CommandeVente> listeCommandePeriode(@Param("x") Date dateDebut, @Param("y") Date dateFin,
			@Param("z") String uuid);
	
	
	@Query("select c from CommandeVente c where  c.boutique.uuid =:z ")
	List<CommandeVente> listeCommandes(
			@Param("z") String uuid);

	@Query(value = "select sum(c.montantCommade) from CommandeVente c  where c.boutique.uuid =:z and c.datePaiement between :x and :y ", nativeQuery = true)
	int sommeCommandeVente(@Param("x") Date dateDebut, @Param("y") Date dateFin, @Param("z") String uuid);
	
	
	
	
	
	@Query("select c from CommandeVente c where c.boutique.uuid =:z and c.datePaiement between :x and :y ")
	Page<CommandeVente> listeCommandePeriode(@Param("x") Date dateDebut, @Param("y") Date dateFin,
			@Param("z") String uuid, Pageable pageable);
	
	
	
	@Query("select c from CommandeVente c where c.boutique.uuid =:z and c.datePaiement between :x and :y and LOWER(c.numeroCommande) LIKE LOWER(CONCAT('%', :key, '%')) ")
	Page<CommandeVente> listeCommandePeriodeByKey(@Param("x") Date dateDebut, @Param("y") Date dateFin,
			@Param("z") String uuid,@Param("key") String key, Pageable pageable);



	// Top clients par nombre de commandes
	@Query("SELECT new org.sid.saranApp.dto.rapport.TopClientDTO(" +
			"c.uuid, " +
			"c.nom, " +
			"c.prenom, " +
			"c.email, " +
			"COUNT(cv.uuid), " +
			"SUM(cv.montantCommade)) " +
			"FROM CommandeVente cv " +
			"JOIN Client c ON cv.client.uuid = c.uuid " +
			"WHERE cv.isDelete = false AND c.isDelete = false " +
			"GROUP BY c.uuid, c.nom, c.prenom, c.email " +
			"ORDER BY COUNT(cv.uuid) DESC")
	List<TopClientDTO> findTopClientsByNombreCommandes(Pageable pageable);

	// Top clients par montant total dépensé
	@Query("SELECT new org.sid.saranApp.dto.rapport.TopClientDTO(" +
			"c.uuid, " +
			"c.nom, " +
			"c.prenom, " +
			"c.email, " +
			"COUNT(cv.uuid), " +
			"SUM(cv.montantCommade)) " +
			"FROM CommandeVente cv " +
			"JOIN Client c ON cv.client.uuid = c.uuid " +
			"WHERE cv.isDelete = false AND c.isDelete = false " +
			"GROUP BY c.uuid, c.nom, c.prenom, c.email " +
			"ORDER BY SUM(cv.montantCommade) DESC")
	List<TopClientDTO> findTopClientsByMontantTotal(Pageable pageable);

	// Top clients par période
	@Query("SELECT new org.sid.saranApp.dto.rapport.TopClientDTO(" +
			"c.uuid, " +
			"c.nom, " +
			"c.prenom, " +
			"c.email, " +
			"COUNT(cv.uuid), " +
			"SUM(cv.montantCommade)) " +
			"FROM CommandeVente cv " +
			"JOIN Client c ON cv.client.uuid = c.uuid " +
			"WHERE cv.isDelete = false AND c.isDelete = false " +
			"AND cv.dateCreated BETWEEN :dateDebut AND :dateFin " +
			"GROUP BY c.uuid, c.nom, c.prenom, c.email " +
			"ORDER BY SUM(cv.montantCommade) DESC")
	List<TopClientDTO> findTopClientsByPeriode(
			@Param("dateDebut") Date dateDebut,
			@Param("dateFin") Date dateFin,
			Pageable pageable);

	// Top clients par boutique
	@Query("SELECT new org.sid.saranApp.dto.rapport.TopClientDTO(" +
			"c.uuid, " +
			"c.nom, " +
			"c.prenom, " +
			"c.email, " +
			"c.phone, " +
			"COUNT(cv.uuid), " +
			"SUM(cv.montantCommade), " +
			"cv.utilisateur.boutique.uuid) " +
			"FROM CommandeVente cv " +
			"JOIN Client c ON cv.client.uuid = c.uuid " +
			"WHERE cv.isDelete = false AND c.isDelete = false " +
			"AND cv.utilisateur.boutique.uuid = :boutiqueUuid " +
			"GROUP BY c.uuid, c.nom, c.prenom, c.email, c.phone, cv.utilisateur.boutique.uuid " +
			"ORDER BY SUM(cv.montantCommade) DESC")
	List<TopClientDTO> findTopClientsByBoutique(
			@Param("boutiqueUuid") String boutiqueUuid,
			Pageable pageable);

	// Clients les plus fidèles (par nombre de commandes et régularité)
	@Query("SELECT new org.sid.saranApp.dto.rapport.TopClientDTO(" +
			"c.uuid, " +
			"c.nom, " +
			"c.prenom, " +
			"c.email, " +
			"COUNT(cv.uuid), " +
			"SUM(cv.montantCommade)) " +
			"FROM CommandeVente cv " +
			"JOIN Client c ON cv.client.uuid = c.uuid " +
			"WHERE cv.isDelete = false AND c.isDelete = false " +
			"AND cv.dateCreated >= :dateDebut " +
			"GROUP BY c.uuid, c.nom, c.prenom, c.email " +
			"HAVING COUNT(cv.uuid) >= :nombreCommandesMin " +
			"ORDER BY COUNT(cv.uuid) DESC, SUM(cv.montantCommade) DESC")
	List<TopClientDTO> findTopClientsFideles(
			@Param("dateDebut") LocalDateTime dateDebut,
			@Param("nombreCommandesMin") Long nombreCommandesMin,
			Pageable pageable);

	// Requête native pour performance optimisée
	@Query(value = "SELECT " +
			"c.uuid as client.uuid, " +
			"c.nom, " +
			"c.prenom, " +
			"c.email, " +
			"c.phone, " +
			"COUNT(cv.uuid) as nombreCommandes, " +
			"SUM(cv.montant_commade) as montantTotal " +
			"FROM commande_vente cv " +
			"JOIN client c ON cv.client_uuid = c.uuid " +
			"WHERE cv.is_delete = false AND c.is_delete = false " +
			"GROUP BY c.uuid, c.nom, c.prenom, c.email, c.phone " +
			"ORDER BY SUM(cv.montant_commade) DESC " +
			"LIMIT :limit",
			nativeQuery = true)
	List<Object[]> findTopClientsNative(@Param("limit") int limit);

	// Clients avec commandes payées uniquement
	@Query("SELECT new org.sid.saranApp.dto.rapport.TopClientDTO(" +
			"c.uuid, " +
			"c.nom, " +
			"c.prenom, " +
			"c.email, " +
			"COUNT(cv.uuid), " +
			"SUM(cv.montantCommade)) " +
			"FROM CommandeVente cv " +
			"JOIN Client c ON cv.client.uuid = c.uuid " +
			"WHERE cv.isDelete = false AND c.isDelete = false " +
			"AND cv.isPaye = true " +
			"GROUP BY c.uuid, c.nom, c.prenom, c.email " +
			"ORDER BY SUM(cv.montantCommade) DESC")
	List<TopClientDTO> findTopClientsCommandesPayees(Pageable pageable);



	// Requête pour obtenir toutes les statistiques de base
	@Query("SELECT new org.sid.saranApp.dto.rapport.StatistiquesGeneralesDTO(" +
			"SUM(cv.montantCommade), " +
			"COUNT(cv.uuid), " +
			"COUNT(DISTINCT cv.client.uuid)) " +
			"FROM CommandeVente cv " +
			"WHERE cv.isDelete = false")
	StatistiquesGeneralesDTO getStatistiquesGenerales();

	// Statistiques par période
	@Query("SELECT new org.sid.saranApp.dto.rapport.StatistiquesGeneralesDTO(" +
			"SUM(cv.montantCommade), " +
			"COUNT(cv.uuid), " +
			"COUNT(DISTINCT cv.client.uuid)) " +
			"FROM CommandeVente cv " +
			"WHERE cv.isDelete = false " +
			"AND cv.dateCreated BETWEEN :dateDebut AND :dateFin")
	StatistiquesGeneralesDTO getStatistiquesParPeriode(
			@Param("dateDebut") Date dateDebut,
			@Param("dateFin") Date dateFin);

	// Statistiques par boutique
	@Query("SELECT new org.sid.saranApp.dto.rapport.StatistiquesGeneralesDTO(" +
			"SUM(cv.montantCommade), " +
			"COUNT(cv.uuid), " +
			"COUNT(DISTINCT cv.client.uuid)) " +
			"FROM CommandeVente cv " +
			"WHERE cv.isDelete = false " +
			"AND cv.utilisateur.uuid = :boutiqueUuid")
	StatistiquesGeneralesDTO getStatistiquesParBoutique(@Param("boutiqueUuid") String boutiqueUuid);

	// Statistiques avec détails des paiements
	@Query("SELECT new org.sid.saranApp.dto.rapport.StatistiquesGeneralesDTO(" +
			"SUM(cv.montantCommade), " +
			"COUNT(cv.uuid), " +
			"COUNT(DISTINCT cv.client.uuid), " +
			"SUM(CASE WHEN cv.isPaye = true THEN 1 ELSE 0 END), " +
			"SUM(CASE WHEN cv.isPaye = false THEN 1 ELSE 0 END), " +
			"SUM(CASE WHEN cv.isPaye = true THEN cv.montantCommade ELSE 0 END), " +
			"SUM(CASE WHEN cv.isPaye = false THEN cv.montantCommade ELSE 0 END)) " +
			"FROM CommandeVente cv " +
			"WHERE cv.isDelete = false")
	StatistiquesGeneralesDTO getStatistiquesCompletes();

	// Requête native pour performance optimisée
	@Query(value = "SELECT " +
			"SUM(cv.montant_commande) as montantTotal, " +
			"COUNT(cv.uuid) as nombreTotalCommandes, " +
			"COUNT(DISTINCT cv.client_uuid) as clientTotal, " +
			"AVG(cv.montant_commande) as panierMoyen, " +
			"SUM(CASE WHEN cv.is_paye = true THEN 1 ELSE 0 END) as commandesPayees, " +
			"SUM(CASE WHEN cv.is_paye = false THEN 1 ELSE 0 END) as commandesNonPayees " +
			"FROM commande_vente cv " +
			"WHERE cv.is_delete = false",
			nativeQuery = true)
	Object[] getStatistiquesNative();

	// Statistiques par type de commande
	@Query("SELECT cv.typeCommande, " +
			"SUM(cv.montantCommade), " +
			"COUNT(cv.uuid), " +
			"COUNT(DISTINCT cv.client.uuid), " +
			"AVG(cv.montantCommade) " +
			"FROM CommandeVente cv " +
			"WHERE cv.isDelete = false " +
			"GROUP BY cv.typeCommande")
	List<Object[]> getStatistiquesParTypeCommande();

	// Évolution des ventes par mois
	@Query(value = "SELECT " +
			"DATE_FORMAT(cv.date_created, '%Y-%m') as mois, " +
			"SUM(cv.montant_commande) as montantTotal, " +
			"COUNT(cv.uuid) as nombreCommandes, " +
			"COUNT(DISTINCT cv.client_uuid) as clientsUniques " +
			"FROM commande_vente cv " +
			"WHERE cv.is_delete = false " +
			"AND cv.date_created >= :dateDebut " +
			"GROUP BY DATE_FORMAT(cv.date_created, '%Y-%m') " +
			"ORDER BY mois DESC",
			nativeQuery = true)
	List<Object[]> getEvolutionVentesParMois(@Param("dateDebut") LocalDateTime dateDebut);
}
