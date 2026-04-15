package org.sid.saranApp.service.version;

import org.sid.saranApp.dto.PageDataDto;
import org.sid.saranApp.dto.version.ImportProduitJsonResultDto;
import org.sid.saranApp.dto.version.ProduitStockDto;

import java.util.List;

public interface ProduitService {
    ProduitStockDto addProduit(ProduitStockDto produitStockDto);
    ProduitStockDto updateProduit(ProduitStockDto produitStockDto,String uuid);
    ProduitStockDto getProduit(String uuid);
    PageDataDto<ProduitStockDto> liste(int size, int page, String libelle);
    ProduitStockDto updateProduit(String uuid, ProduitStockDto produitStockDto);

    /**
     * Import en masse depuis un tableau JSON (même forme que {@link ProduitStockDto} exporté par le front).
     */
    default ImportProduitJsonResultDto importProduitsJson(List<ProduitStockDto> lignes) {
        return importProduitsJson(lignes, null);
    }

    /**
     * Import JSON catalogue + optionnellement génération de bons fournisseur.
     *
     * @param uuidFournisseurFallbackCommande si non vide, sert de fournisseur pour les lignes sans
     *        {@link ProduitStockDto#getUuidFournisseur()} ; les bons sont groupés par date de commande et par fournisseur.
     *        Aucune réception stock n’est déclenchée (le stock vient déjà des quantités catalogue).
     */
    ImportProduitJsonResultDto importProduitsJson(List<ProduitStockDto> lignes, String uuidFournisseurFallbackCommande);

    /**
     * Normalise une ligne export JSON, résout la catégorie, crée article + produit si absent.
     * Ne crée pas de bon fournisseur. Utile pour l’import joint bon + {@code articleDto}.
     *
     * @return DTO enrichi avec {@link ProduitStockDto#getUuidArticle()} ; produit existant → mapping catalogue actuel.
     */
    ProduitStockDto ensureProduitForJsonImport(ProduitStockDto dto);
}
