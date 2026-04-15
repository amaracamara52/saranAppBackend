package org.sid.saranApp.controller;

import org.sid.saranApp.dto.CommandeFournisseurDto;
import org.sid.saranApp.dto.ImportBonCommandeProduitResultDto;
import org.sid.saranApp.service.ImportBonCommandeProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/import")
public class ImportBonCommandeProduitController {

	@Autowired
	private ImportBonCommandeProduitService importBonCommandeProduitService;

	/**
	 * Import Excel : articles (unités de vente), bons de commande fournisseur groupés par dates,
	 * réception complète puis application du prix de vente (pièce) et optionnellement du stock « quantité restante ».
	 *
	 * @param file            .xlsx / .xls — ligne d'en-têtes avec notamment DESIGNATION, UNITE, PRIX ACHAT,
	 *                        PRIX VENTE, UNITE DE VENTE, QUANTITE RESTANTE…, QUANTITE COMMANDE (ex. 2 CARTONS),
	 *                        QUANTITE DANS LE CARTON, DATE COMMANDE, DATE LIVRAISON (jj/mm/aaaa),
	 *                        colonne optionnelle CATEGORIE / CATEGORY / RAYON (création en boutique si absente)
	 * @param uuidFournisseur obligatoire
	 * @param uuidCategorie   optionnel — repli par ligne si la cellule catégorie est vide ; sinon première catégorie ou « Import »
	 */
	@PostMapping(value = "/bon-commande-produits", consumes = "multipart/form-data")
	public ImportBonCommandeProduitResultDto importBonCommandeProduits(
			@RequestParam("file") MultipartFile file,
			@RequestParam("uuidFournisseur") String uuidFournisseur,
			@RequestParam(value = "uuidCategorie", required = false) String uuidCategorie) {
		return importBonCommandeProduitService.importExcel(file, uuidFournisseur, uuidCategorie);
	}

	/**
	 * Import JSON : tableau de bons (export joint avec {@code detailCommandeFournisseurDtos[].articleDto}).
	 * {@code uuidFournisseur} peut être passé en query si absent sur chaque bon.
	 */
	@PostMapping(value = "/bon-commande-produits-json", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ImportBonCommandeProduitResultDto importBonCommandeProduitsJson(
			@RequestBody List<CommandeFournisseurDto> bons,
			@RequestParam(value = "uuidFournisseur", required = false) String uuidFournisseur,
			@RequestParam(value = "uuidCategorie", required = false) String uuidCategorie) {
		return importBonCommandeProduitService.importJson(bons, uuidFournisseur, uuidCategorie);
	}
}
