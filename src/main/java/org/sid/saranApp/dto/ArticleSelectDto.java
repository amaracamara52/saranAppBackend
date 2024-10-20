/**
 *
 */
package org.sid.saranApp.dto;

/**
 *
 */
public class ArticleSelectDto {

	private String uuid;
	private String name;
	private String categorie;

	public String getCategorie() {
		return categorie;
	}

	public String getName() {
		return name;
	}

	public String getUuid() {
		return uuid;
	}

	public void setCategorie(String categorie) {
		this.categorie = categorie;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

}
