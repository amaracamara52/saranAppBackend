package org.sid.saranApp.service;

import org.sid.saranApp.model.Article;
import org.sid.saranApp.model.Produit;
import org.sid.saranApp.model.StockUniteVente;
import org.sid.saranApp.model.TypeUniteDeVente;
import org.sid.saranApp.repository.StockUniteVenteRepository;
import org.sid.saranApp.repository.TypeUniteDeVenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service pour gérer les conversions et mises à jour de stock entre différentes unités de vente
 * Les unités de vente sont définies au niveau de l'Article, les quantités sont gérées par Produit via StockUniteVente
 */
@Service
public class StockUniteVenteService {

    @Autowired
    private TypeUniteDeVenteRepository typeUniteDeVenteRepository;
    
    @Autowired
    private StockUniteVenteRepository stockUniteVenteRepository;

    /**
     * Convertit une quantité d'une unité vers l'unité de base (pièces)
     * @param quantite Quantité dans l'unité source
     * @param uniteFacteur Facteur de conversion de l'unité source (ex: 12 pour CARTON)
     * @return Quantité en unité de base
     */
    public int convertToBaseUnit(int quantite, int uniteFacteur) {
        return quantite * uniteFacteur;
    }

    /**
     * Convertit une quantité de l'unité de base vers une autre unité
     * @param quantiteBase Quantité en unité de base
     * @param uniteFacteur Facteur de conversion de l'unité cible
     * @return Quantité dans l'unité cible
     */
    public int convertFromBaseUnit(int quantiteBase, int uniteFacteur) {
        if (uniteFacteur == 0) {
            return 0;
        }
        return quantiteBase / uniteFacteur;
    }

    /**
     * Récupère ou crée un StockUniteVente pour un produit et une unité de vente
     * @param produit Le produit (ne doit pas être null)
     * @param typeUniteDeVente L'unité de vente (ne doit pas être null)
     * @return Le StockUniteVente existant ou nouvellement créé
     * @throws IllegalArgumentException si produit ou typeUniteDeVente est null
     */
    private StockUniteVente getOrCreateStockUniteVente(Produit produit, TypeUniteDeVente typeUniteDeVente) {
        if (produit == null) {
            throw new IllegalArgumentException("Le produit ne peut pas être null");
        }
        if (typeUniteDeVente == null) {
            throw new IllegalArgumentException("L'unité de vente ne peut pas être null");
        }
        
        Optional<StockUniteVente> stockOpt = stockUniteVenteRepository.findByProduitAndTypeUniteDeVente(produit, typeUniteDeVente);
        if (stockOpt.isPresent()) {
            return stockOpt.get();
        }
        
        // Créer un nouveau stock avec quantité initiale à 0
        StockUniteVente stock = new StockUniteVente();
        stock.setProduit(produit);
        stock.setTypeUniteDeVente(typeUniteDeVente);
        stock.setQtite(0);
        return stockUniteVenteRepository.save(stock);
    }

    /**
     * Initialise les stocks pour un produit en utilisant les unités de vente de son article
     * Crée un StockUniteVente pour chaque unité de vente définie pour l'article
     * @param produit Le produit pour lequel initialiser les stocks (ne doit pas être null)
     * @throws IllegalArgumentException si produit est null
     */
    public void initializeStocksForProduit(Produit produit) {
        if (produit == null) {
            throw new IllegalArgumentException("Le produit ne peut pas être null");
        }
        
        Article article = produit.getArticle();
        if (article == null) {
            return;
        }
        
        List<TypeUniteDeVente> unites = typeUniteDeVenteRepository.findByArticle(article);
        if (unites == null || unites.isEmpty()) {
            return;
        }
        
        for (TypeUniteDeVente unite : unites) {
            if (unite != null) {
                getOrCreateStockUniteVente(produit, unite);
            }
        }
    }

    /**
     * Quantité totale en unité de base pour un produit.
     * Les lignes {@link StockUniteVente} (pièce ×1, carton ×N, etc.) sont des <strong>vues</strong> du même stock :
     * on ne doit pas les additionner (sinon 100 pièces + 2 cartons×50 = 200 au lieu de 100).
     * On prend la ligne dont le facteur d’unité est 1 ; sinon une ligne quelconque convertie en base.
     */
    public int calculateTotalBaseQuantity(Produit produit) {
        if (produit == null) {
            throw new IllegalArgumentException("Le produit ne peut pas être null");
        }
        List<StockUniteVente> stocks = stockUniteVenteRepository.findByProduit(produit);
        if (stocks == null || stocks.isEmpty()) {
            return 0;
        }

        Optional<StockUniteVente> ligneBase = stocks.stream()
                .filter(s -> s.getTypeUniteDeVente() != null && s.getTypeUniteDeVente().getUnite() == 1)
                .findFirst();
        if (ligneBase.isPresent()) {
            return Math.max(0, ligneBase.get().getQtite());
        }

        StockUniteVente ref = stocks.get(0);
        TypeUniteDeVente u = ref.getTypeUniteDeVente();
        if (u == null) {
            return 0;
        }
        return Math.max(0, convertToBaseUnit(ref.getQtite(), u.getUnite()));
    }

    /**
     * Met à jour toutes les quantités des unités de vente d'un produit
     * en fonction de la quantité totale en unité de base
     * @param produit Le produit dont les unités doivent être mises à jour (ne doit pas être null)
     * @param quantiteBaseTotale La quantité totale en unité de base (doit être >= 0)
     * @throws IllegalArgumentException si produit est null ou quantiteBaseTotale < 0
     */
    public void updateAllUnitsFromBaseQuantity(Produit produit, int quantiteBaseTotale) {
        if (produit == null) {
            throw new IllegalArgumentException("Le produit ne peut pas être null");
        }
        if (quantiteBaseTotale < 0) {
            throw new IllegalArgumentException("La quantité totale ne peut pas être négative");
        }
        Article article = produit.getArticle();
        if (article == null) {
            return;
        }
        
        List<TypeUniteDeVente> unites = typeUniteDeVenteRepository.findByArticle(article);
        if (unites == null || unites.isEmpty()) {
            return;
        }

        for (TypeUniteDeVente unite : unites) {
            StockUniteVente stock = getOrCreateStockUniteVente(produit, unite);
            int nouvelleQuantite = convertFromBaseUnit(quantiteBaseTotale, unite.getUnite());
            stock.setQtite(nouvelleQuantite);
            stockUniteVenteRepository.save(stock);
        }
    }

    /**
     * Ajoute du stock pour plusieurs unités de vente
     * Calcule la quantité totale en unité de base et met à jour toutes les unités
     * @param produit Le produit (ne doit pas être null)
     * @param unitesAvecQuantites Liste des unités avec leurs quantités à ajouter (ne doit pas être null)
     * @throws IllegalArgumentException si produit ou unitesAvecQuantites est null
     */
    public void addStockMultipleUnits(Produit produit, List<TypeUniteDeVenteQuantite> unitesAvecQuantites) {
        if (produit == null) {
            throw new IllegalArgumentException("Le produit ne peut pas être null");
        }
        if (unitesAvecQuantites == null) {
            throw new IllegalArgumentException("La liste des unités avec quantités ne peut pas être null");
        }
        // S'assurer que tous les stocks sont initialisés
        initializeStocksForProduit(produit);
        
        // Calculer la quantité totale actuelle en unité de base
        int quantiteBaseActuelle = calculateTotalBaseQuantity(produit);
        
        // Calculer la quantité totale à ajouter en unité de base
        int quantiteBaseAjoutee = 0;
        for (TypeUniteDeVenteQuantite uq : unitesAvecQuantites) {
            TypeUniteDeVente unite = uq.getUnite();
            int quantite = uq.getQuantite();
            int quantiteBase = convertToBaseUnit(quantite, unite.getUnite());
            quantiteBaseAjoutee += quantiteBase;
            
            // Ajouter la quantité au stock de cette unité
            StockUniteVente stock = getOrCreateStockUniteVente(produit, unite);
            stock.setQtite(stock.getQtite() + quantite);
            stockUniteVenteRepository.save(stock);
        }
        
        // Calculer la nouvelle quantité totale en unité de base
        int quantiteBaseTotale = quantiteBaseActuelle + quantiteBaseAjoutee;
        
        // Mettre à jour toutes les unités en fonction de la nouvelle quantité totale
        updateAllUnitsFromBaseQuantity(produit, quantiteBaseTotale);
        
        // La quantité du produit n'est plus stockée directement dans Produit
        // Elle est calculée dynamiquement via StockUniteVente
    }

    /**
     * Retire du stock pour une unité de vente sélectionnée
     * Met à jour automatiquement toutes les autres unités
     * @param produit Le produit (ne doit pas être null)
     * @param uniteVendue L'unité de vente utilisée pour la vente (ne doit pas être null)
     * @param quantiteVendue La quantité vendue dans cette unité (doit être > 0)
     * @return true si le stock est suffisant et la vente réussie, false sinon
     * @throws IllegalArgumentException si produit, uniteVendue est null ou quantiteVendue <= 0
     */
    public boolean removeStockFromUnit(Produit produit, TypeUniteDeVente uniteVendue, int quantiteVendue) {
        if (produit == null) {
            throw new IllegalArgumentException("Le produit ne peut pas être null");
        }
        if (uniteVendue == null) {
            throw new IllegalArgumentException("L'unité de vente ne peut pas être null");
        }
        if (quantiteVendue <= 0) {
            throw new IllegalArgumentException("La quantité vendue doit être supérieure à 0");
        }
        StockUniteVente stock = stockUniteVenteRepository
            .findByProduitAndTypeUniteDeVente(produit, uniteVendue)
            .orElse(null);
        
        if (stock == null || stock.getQtite() < quantiteVendue) {
            return false;
        }

        // Calculer la quantité totale actuelle en unité de base
        int quantiteBaseActuelle = calculateTotalBaseQuantity(produit);
        
        // Convertir la quantité vendue en unité de base
        int quantiteBaseVendue = convertToBaseUnit(quantiteVendue, uniteVendue.getUnite());
        
        // Calculer la nouvelle quantité totale
        int nouvelleQuantiteBase = quantiteBaseActuelle - quantiteBaseVendue;
        
        if (nouvelleQuantiteBase < 0) {
            return false;
        }
        
        // Mettre à jour toutes les unités
        updateAllUnitsFromBaseQuantity(produit, nouvelleQuantiteBase);
        
        // La quantité du produit n'est plus stockée directement dans Produit
        // Elle est calculée dynamiquement via StockUniteVente
        
        return true;
    }

    /**
     * Récupère le stock pour une unité de vente spécifique d'un produit
     * @param produit Le produit (ne doit pas être null)
     * @param typeUniteDeVente L'unité de vente (ne doit pas être null)
     * @return La quantité en stock pour cette unité (0 si non trouvé)
     * @throws IllegalArgumentException si produit ou typeUniteDeVente est null
     */
    public int getStockForUnit(Produit produit, TypeUniteDeVente typeUniteDeVente) {
        if (produit == null) {
            throw new IllegalArgumentException("Le produit ne peut pas être null");
        }
        if (typeUniteDeVente == null) {
            throw new IllegalArgumentException("L'unité de vente ne peut pas être null");
        }
        
        Optional<StockUniteVente> stockOpt = stockUniteVenteRepository
            .findByProduitAndTypeUniteDeVente(produit, typeUniteDeVente);
        
        return stockOpt.map(StockUniteVente::getQtite).orElse(0);
    }

    /**
     * Récupère tous les stocks d'un produit
     * @param produit Le produit (ne doit pas être null)
     * @return Liste des stocks par unité de vente pour ce produit
     * @throws IllegalArgumentException si produit est null
     */
    public List<StockUniteVente> getStocksForProduit(Produit produit) {
        if (produit == null) {
            throw new IllegalArgumentException("Le produit ne peut pas être null");
        }
        return stockUniteVenteRepository.findByProduit(produit);
    }

    /**
     * Applique un prix de vente catalogue par unité de base : chaque ligne de stock reçoit
     * {@code prixVenteUnitaireBase × facteur d’unité} (ex. prix pièce × 24 pour un carton).
     */
    public void applyRetailPricesFromBaseUnit(Produit produit, double prixVenteUnitaireBase) {
        if (produit == null || prixVenteUnitaireBase <= 0) {
            return;
        }
        initializeStocksForProduit(produit);
        List<StockUniteVente> stocks = stockUniteVenteRepository.findByProduit(produit);
        for (StockUniteVente stock : stocks) {
            if (stock.getTypeUniteDeVente() == null) {
                continue;
            }
            int facteur = Math.max(1, stock.getTypeUniteDeVente().getUnite());
            stock.setPrice(prixVenteUnitaireBase * facteur);
            stockUniteVenteRepository.save(stock);
        }
    }

    /**
     * Classe interne pour représenter une unité avec sa quantité
     */
    public static class TypeUniteDeVenteQuantite {
        private TypeUniteDeVente unite;
        private int quantite;

        public TypeUniteDeVenteQuantite(TypeUniteDeVente unite, int quantite) {
            this.unite = unite;
            this.quantite = quantite;
        }

        public TypeUniteDeVente getUnite() {
            return unite;
        }

        public int getQuantite() {
            return quantite;
        }
    }
}
