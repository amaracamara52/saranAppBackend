package org.sid.saranApp.repository;

import org.sid.saranApp.model.Reduction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface ReductionRepository extends JpaRepository<Reduction, String> {
    
    // Trouver toutes les réductions d'une boutique
    List<Reduction> findByBoutiqueUuid(String boutiqueUuid);
    
    // Trouver les réductions actives d'une boutique
    List<Reduction> findByBoutiqueUuidAndIsActiveTrue(String boutiqueUuid);
    
    // Trouver les réductions par catégorie
    List<Reduction> findByBoutiqueUuidAndCategorie(String boutiqueUuid, String categorie);
    
    // Trouver les réductions valides (entre dateDebut et dateFin)
    @Query("SELECT r FROM Reduction r WHERE r.boutique.uuid = :boutiqueUuid " +
           "AND r.isActive = true " +
           "AND (r.dateDebut IS NULL OR r.dateDebut <= :currentDate) " +
           "AND (r.dateFin IS NULL OR r.dateFin >= :currentDate)")
    List<Reduction> findValidReductions(@Param("boutiqueUuid") String boutiqueUuid,
                                       @Param("currentDate") Date currentDate);
    
    // Trouver les réductions par pourcentage minimum
    List<Reduction> findByBoutiqueUuidAndTauxRemiseGreaterThanEqual(String boutiqueUuid, double tauxRemise);
    
    // Trouver les réductions par libellé (recherche partielle)
    @Query("SELECT r FROM Reduction r WHERE r.boutique.uuid = :boutiqueUuid " +
           "AND LOWER(r.libelle) LIKE LOWER(CONCAT('%', :libelle, '%'))")
    List<Reduction> findByBoutiqueUuidAndLibelleContaining(@Param("boutiqueUuid") String boutiqueUuid,
                                                          @Param("libelle") String libelle);
} 