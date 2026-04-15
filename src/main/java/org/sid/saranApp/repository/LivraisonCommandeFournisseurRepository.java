package org.sid.saranApp.repository;

import org.sid.saranApp.model.DetailCommandeFournisseur;
import org.sid.saranApp.model.LivraisonCommandeFournisseur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LivraisonCommandeFournisseurRepository extends JpaRepository<LivraisonCommandeFournisseur, String> {
    List<LivraisonCommandeFournisseur> findByDetailCommandeFournisseur(DetailCommandeFournisseur detailCommandeFournisseur);
}
