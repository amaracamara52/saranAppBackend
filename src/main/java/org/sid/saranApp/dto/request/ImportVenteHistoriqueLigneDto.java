package org.sid.saranApp.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Ligne d'une vente importée (JSON historique). Les UUID peuvent être vides si {@link #article} est renseigné.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImportVenteHistoriqueLigneDto {

	private int quantite;
	private String uuidProduit;
	private String uuidTypeUniteDeVente;
	private String article;

	public int getQuantite() {
		return quantite;
	}

	public void setQuantite(int quantite) {
		this.quantite = quantite;
	}

	public String getUuidProduit() {
		return uuidProduit;
	}

	public void setUuidProduit(String uuidProduit) {
		this.uuidProduit = uuidProduit;
	}

	public String getUuidTypeUniteDeVente() {
		return uuidTypeUniteDeVente;
	}

	public void setUuidTypeUniteDeVente(String uuidTypeUniteDeVente) {
		this.uuidTypeUniteDeVente = uuidTypeUniteDeVente;
	}

	public String getArticle() {
		return article;
	}

	public void setArticle(String article) {
		this.article = article;
	}

}
