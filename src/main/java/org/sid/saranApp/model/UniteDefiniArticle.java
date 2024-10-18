/**
 *
 */
package org.sid.saranApp.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

import org.sid.saranApp.enume.TypeUniteEnum;

/**
 *
 */
@Entity
public class UniteDefiniArticle extends AbstractDomainClass {

	@Enumerated(EnumType.STRING)
	private TypeUniteEnum typeUnite;
	@ManyToOne
	private Article article;

	public Article getArticle() {
		return article;
	}

	public TypeUniteEnum getTypeUnite() {
		return typeUnite;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public void setTypeUnite(TypeUniteEnum typeUnite) {
		this.typeUnite = typeUnite;
	}

}
