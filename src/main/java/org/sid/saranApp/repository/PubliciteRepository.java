package org.sid.saranApp.repository;

import org.sid.saranApp.model.Publicite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PubliciteRepository extends JpaRepository<Publicite,String> {

    @Query("SELECT p FROM Publicite p WHERE " +
            "(:name IS NULL OR LOWER(p.libelle) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
            "(:uuidCategorie IS NULL OR p.categorie.uuid = :uuidCategorie) AND " +
            "(p.price >= :minPrice) AND " +
            "(p.price <= :maxPrice) AND " +
            "(:uuidBoutique IS NULL OR p.utilisateur.boutique.uuid = :uuidBoutique)")
    Page<Publicite> findAllPublishByCategory(@Param("name") String name,@Param("uuidCategorie") String uuidCategorie,@Param("minPrice") double minPrice,@Param("maxPrice") double maxPrice,@Param("uuidBoutique") String uuidBoutique, Pageable pageable);
}
