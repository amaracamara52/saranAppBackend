package org.sid.saranApp.service;

import org.sid.saranApp.dto.CommandeFournisseurDto;
import org.sid.saranApp.dto.ImportBonCommandeProduitResultDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Import d'un fichier Excel (colonnes type : désignation, unité d'achat, prix achat, prix vente,
 * quantité commandée, facteur pièces/carton, dates, quantité restante pièces) vers
 * {@link org.sid.saranApp.model.CommandeFournisseur}, {@link org.sid.saranApp.model.Article},
 * {@link org.sid.saranApp.model.TypeUniteDeVente}, {@link org.sid.saranApp.model.Produit} et stock.
 */
public interface ImportBonCommandeProduitService {

	/**
	 * @param file            fichier .xlsx ou .xls
	 * @param uuidFournisseur fournisseur lié aux bons créés
	 * @param uuidCategorie   catégorie des articles créés ; si null, première catégorie de la boutique ou catégorie « Import »
	 */
	ImportBonCommandeProduitResultDto importExcel(MultipartFile file, String uuidFournisseur, String uuidCategorie);

	/**
	 * Import JSON : tableau de bons (même forme que l’export joint), avec {@code articleDto} par ligne.
	 * Crée ou rattache les produits, puis bon + réception complète + ajustement prix / stock catalogue depuis {@code articleDto}.
	 *
	 * @param uuidFournisseurParam utilisé si {@link CommandeFournisseurDto#getUuidFournisseur()} est vide sur un bon
	 */
	ImportBonCommandeProduitResultDto importJson(List<CommandeFournisseurDto> bons, String uuidFournisseurParam,
			String uuidCategorie);
}
