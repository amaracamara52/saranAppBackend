package org.sid.saranApp.controller.version;

import org.sid.saranApp.dto.version.ImportProduitJsonResultDto;
import org.sid.saranApp.dto.version.ProduitStockDto;
import org.sid.saranApp.service.version.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ProduitStockController {
    @Autowired
    private ProduitService produitService;

    @PostMapping("produitStock")
    public ProduitStockDto addProduitStock(@RequestBody ProduitStockDto produitStockDto){
        return produitService.addProduit(produitStockDto);
    }

    @PutMapping("/produitStock/{uuid}")
    public ProduitStockDto updateProduitStock(@RequestBody ProduitStockDto produitStockDto, @PathVariable String uuid){
        return produitService.updateProduit(produitStockDto,uuid);
    }

    /**
     * Corps : tableau JSON d’objets {@link ProduitStockDto} (export front / mobile).
     * Champs reconnus en plus : {@code baseUniteEnum} (alias de {@code typeUniteEnum}),
     * {@code libelleCategorie} sans {@code uuidCategorie} → recherche ou création en boutique, sinon catégorie « AUCUN ».
     * {@code typeGestionUnite} {@code NORMAL} → unité de vente forcée à PIECE ×1 avec {@code prixVente}.
     * Paramètre optionnel {@code uuidFournisseurCommande} : crée des bons fournisseur (par date + fournisseur) pour les lignes
     * avec {@code quantiteCommande} &gt; 0, {@code prixAchat} &gt; 0 et {@code dateCommande} ; sans réception de stock.
     */
    @PostMapping("produitStock/import-json")
    public ImportProduitJsonResultDto importProduitsJson(
            @RequestBody List<ProduitStockDto> lignes,
            @RequestParam(required = false) String uuidFournisseurCommande) {
        return produitService.importProduitsJson(lignes, uuidFournisseurCommande);
    }
}
