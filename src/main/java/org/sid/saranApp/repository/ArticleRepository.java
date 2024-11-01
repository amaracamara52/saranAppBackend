package org.sid.saranApp.repository;

import org.sid.saranApp.model.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ArticleRepository extends JpaRepository< Article, String > {
	
	
	@Query("select a from Article a where  LOWER(a.libelle) LIKE LOWER(CONCAT('%', :key, '%'))")
	Page<Article> listeArticleByLibelle(@Param("key") String libelle,Pageable pageable);
	
	
	@Query("select a from Article a")
	Page<Article> listeArticle(Pageable pageable);

}
