package org.sid.saranApp.controller.rapport;

import org.sid.saranApp.dto.rapport.TopProduitDTO;
import org.sid.saranApp.serviceImpl.UtilisateurServiceImpl;
import org.sid.saranApp.serviceImpl.rapport.ProduitStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/stats/produits")
public class ProduitStatsController {

    @Autowired
    private ProduitStatsService produitStatsService;

    @Autowired
    private UtilisateurServiceImpl utilisateurServiceImpl;

    @GetMapping("/top-vendus")
    public ResponseEntity<List<TopProduitDTO>> getTopProduitsVendus(
            @RequestParam(defaultValue = "10") int limit) {
        List<TopProduitDTO> topProduits = produitStatsService.getTopProduitsVendus(limit);
        return ResponseEntity.ok(topProduits);
    }

    @GetMapping("/top-vendus/avec-categorie")
    public ResponseEntity<List<TopProduitDTO>> getTopProduitsVendusAvecCategorie(
            @RequestParam(defaultValue = "10") int limit) {
        List<TopProduitDTO> topProduits = produitStatsService.getTopProduitsVendusAvecCategorie(limit);
        return ResponseEntity.ok(topProduits);
    }

    @GetMapping("/top-vendus/periode")
    public ResponseEntity<List<TopProduitDTO>> getTopProduitsVendusByPeriode(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateDebut,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateFin,
            @RequestParam(defaultValue = "10") int limit) {
        List<TopProduitDTO> topProduits = produitStatsService.getTopProduitsVendusByPeriode(
                dateDebut, dateFin, limit);
        return ResponseEntity.ok(topProduits);
    }

    @GetMapping("/top-vendus/boutique/{boutiqueUuid}")
    public ResponseEntity<List<TopProduitDTO>> getTopProduitsVendusByBoutique(
            @PathVariable String boutiqueUuid,
            @RequestParam(defaultValue = "10") int limit) {
        List<TopProduitDTO> topProduits = produitStatsService.getTopProduitsVendusByBoutique(
                boutiqueUuid, limit);
        return ResponseEntity.ok(topProduits);
    }

    /** Top produits sur la période pour la boutique de l'utilisateur connecté. */
    @GetMapping("/top-vendus/ma-boutique/periode")
    public ResponseEntity<List<TopProduitDTO>> getTopProduitsVendusMaBoutiquePeriode(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateDebut,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateFin,
            @RequestParam(defaultValue = "12") int limit) {
        String boutiqueUuid = utilisateurServiceImpl.getCurentUtilisateur().getBoutique().getUuid();
        List<TopProduitDTO> top = produitStatsService.getTopProduitsVendusByBoutiqueAndPeriode(
                boutiqueUuid, dateDebut, dateFin, limit);
        return ResponseEntity.ok(top);
    }

    @GetMapping("/top-vendus/categorie")
    public ResponseEntity<List<TopProduitDTO>> getTopProduitsVendusByCategorie(
            @RequestParam String categorieLibelle,
            @RequestParam(defaultValue = "10") int limit) {
        List<TopProduitDTO> topProduits = produitStatsService.getTopProduitsVendusByCategorie(
                categorieLibelle, limit);
        return ResponseEntity.ok(topProduits);
    }

    @GetMapping("/top-vendus/native")
    public ResponseEntity<List<TopProduitDTO>> getTopProduitsVendusNative(
            @RequestParam(defaultValue = "10") int limit) {
        List<TopProduitDTO> topProduits = produitStatsService.getTopProduitsVendusNative(limit);
        return ResponseEntity.ok(topProduits);
    }
}


