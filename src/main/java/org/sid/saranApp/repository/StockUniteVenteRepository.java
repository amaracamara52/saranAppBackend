package org.sid.saranApp.repository;

import org.sid.saranApp.model.Produit;
import org.sid.saranApp.model.StockUniteVente;
import org.sid.saranApp.model.TypeUniteDeVente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StockUniteVenteRepository extends JpaRepository<StockUniteVente, String> {
    List<StockUniteVente> findByProduit(Produit produit);
    Optional<StockUniteVente> findByProduitAndTypeUniteDeVente(Produit produit, TypeUniteDeVente typeUniteDeVente);
}
