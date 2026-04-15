package org.sid.saranApp.repository;

import org.sid.saranApp.model.Article;
import org.sid.saranApp.model.TypeUniteDeVente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TypeUniteDeVenteRepository extends JpaRepository<TypeUniteDeVente,String> {
    List<TypeUniteDeVente> findByArticle(Article article);
}
