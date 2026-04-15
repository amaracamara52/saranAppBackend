package org.sid.saranApp.serviceImpl.version;

import java.util.Date;

import org.sid.saranApp.dto.CommandeFournisseurDto;
import org.sid.saranApp.dto.DetailCommandeFournisseurDto;
import org.sid.saranApp.dto.PageDataDto;
import org.sid.saranApp.dto.version.ImportProduitJsonResultDto;
import org.sid.saranApp.dto.version.ProduitStockDto;
import org.sid.saranApp.dto.version.TypeUniteDeVenteDto;
import org.sid.saranApp.enume.TypeGestionUniteProduitEnum;
import org.sid.saranApp.enume.TypeUniteEnum;
import org.sid.saranApp.mapper.Mapper;
import org.sid.saranApp.model.*;
import org.sid.saranApp.repository.*;
import org.sid.saranApp.service.CommandeFournisseurService;
import org.sid.saranApp.service.StockUniteVenteService;
import org.sid.saranApp.service.version.ProduitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class ProduitStockServiceImpl implements ProduitService {

    /** Seuil d’alerte stock unique pour toutes les fiches (non modifiable via le formulaire catalogue). */
    private static final int SEUIL_MINIMUM_DEFAUT = 3;
    @Autowired
    private ProduitStoredRepository produitStoredRepository;
    @Autowired
    private ProduitRepository produitRepository;
    @Autowired
    private CategorieRepository categorieRepository;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private CaracteristiqueArticleRepository caracteristiqueArticleRepository;
    @Autowired
    private CaracteristiqueProduitRepository caracteristiqueProduitRepository;
    @Autowired
    private TypeUniteDeVenteRepository typeUniteDeVenteRepository;
    @Autowired
    private FournisseurRepository fournisseurRepository;
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private EtagereRayonRepository etagereRayonRepository;
    @Autowired
    private StoredFileRepository storedFileRepository;
    @Autowired
    private StockUniteVenteService stockUniteVenteService;
    @Autowired
    private CommandeFournisseurService commandeFournisseurService;

    @Override
    public ProduitStockDto addProduit(ProduitStockDto produitStockDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Produit produit = new Produit();
        Article article = resolveOrCreateArticleForStock(produitStockDto, auth);
        double prixAchatEarly = Math.max(0d, produitStockDto.getPrixAchat());
        double prixVenteEarly = Math.max(0d, produitStockDto.getPrixVente());
        ensureArticleSaleUnits(article, produitStockDto, prixVenteEarly);

        Fournisseur fournisseur = null;
        String uuidFournisseur = produitStockDto.getUuidFournisseur();
        if (uuidFournisseur != null && !uuidFournisseur.isBlank()) {
            fournisseur = fournisseurRepository.findById(uuidFournisseur)
                    .orElseThrow(() -> new IllegalArgumentException("Fournisseur inconnu"));
        }

        Utilisateur utilisateur = utilisateurRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new IllegalStateException("Utilisateur non trouvé"));
        produit.setFournisseur(fournisseur);
        produit.setArticle(article);
        produit.setBoutique(utilisateur.getBoutique());
        produit.setUtilisateur(utilisateur);
        produit.setDateEnregistrement(new Date());
        int qtyBaseInit = Math.max(0, produitStockDto.getQuantiteStock());
        double prixAchat = prixAchatEarly;
        double prixVente = prixVenteEarly;
        produit.setPrixAchat(prixAchat);
        produit.setPrixVente(prixVente);
        produit.setQuantite(qtyBaseInit);
        produit.setQuantiteCommande(0);
        produit.setQuantiteLivraison(0);
        produit.setQuantitePublish(0);
        produit.setQuantiteImage(0);
        produit.setQuantiteImageLivraison(0);
        produit.setQuantiteVendu(0);
        produit.setDatePeremption(null);
        produit.setSeuilMinimum(SEUIL_MINIMUM_DEFAUT);
        produit.setFinish(true);
        // Stock détaillé : StockUniteVente ; les achats / réceptions ajoutent ou mettent à jour prix d’achat au niveau ligne.

        if (produitStockDto.getUuidEmplacement() != null && !produitStockDto.getUuidEmplacement().isBlank()) {
            EtagereRayon etagereRayon = etagereRayonRepository.findById(produitStockDto.getUuidEmplacement())
                    .orElseThrow(() -> new IllegalArgumentException("Emplacement inconnu"));
            produit.setEmplacement(etagereRayon);
        } else {
            produit.setEmplacement(null);
        }

        Produit produitSave = produitRepository.save(produit);

       if(produitStockDto.getCaracteristiqueProduitDtos() !=null){
           produitStockDto.getCaracteristiqueProduitDtos().forEach(c -> {
               CaracteristiqueProduit  caracteristiqueProduit = new CaracteristiqueProduit();
               caracteristiqueProduit.setProduit(produitSave);
               caracteristiqueProduit.setBoutique(produitSave.getBoutique());
               caracteristiqueProduit.setUtilisateur(produit.getUtilisateur());
               caracteristiqueProduit.setLibelle(c.getLibelle());
               caracteristiqueProduit.setValue(c.getValue());
               caracteristiqueProduitRepository.save(caracteristiqueProduit);
           });
       }

       if(produitStockDto.getProduitStoredDtos() != null){
           produitStockDto.getProduitStoredDtos().forEach(ps->{
               StoredFile storedFile = storedFileRepository.findById(ps.getUuidStored())
                       .orElseThrow(() -> new IllegalArgumentException("Fichier stocké inconnu"));
               ProduitStored produitStored = new ProduitStored();
               produitStored.setProduit(produitSave);
               produitStored.setStoredFile(storedFile);
               produitStoredRepository.save(produitStored);
           });
       }

        stockUniteVenteService.initializeStocksForProduit(produitSave);
        stockUniteVenteService.updateAllUnitsFromBaseQuantity(produitSave, qtyBaseInit);
        stockUniteVenteService.applyRetailPricesFromBaseUnit(produitSave, prixVente);

        return Mapper.toProduitStockDto(produitSave, stockUniteVenteService);
    }

    /**
     * Article existant (uuidArticle) ou création depuis le formulaire catalogue (libelle + uuidCategorie).
     */
    private Article resolveOrCreateArticleForStock(ProduitStockDto dto, Authentication auth) {
        if (dto.getUuidArticle() != null && !dto.getUuidArticle().isBlank()) {
            Article existing = articleRepository.findById(dto.getUuidArticle())
                    .orElseThrow(() -> new IllegalArgumentException("Article inconnu"));
            if (dto.getTypeGestionUnite() != null) {
                existing.setTypeGestionUnite(dto.getTypeGestionUnite());
                return articleRepository.save(existing);
            }
            return existing;
        }
        if (dto.getUuidCategorie() == null || dto.getUuidCategorie().isBlank()) {
            throw new IllegalArgumentException("Catégorie requise pour créer un article");
        }
        Categorie categorie = categorieRepository.findById(dto.getUuidCategorie())
                .orElseThrow(() -> new IllegalArgumentException("Catégorie inconnue"));
        Utilisateur utilisateur = utilisateurRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new IllegalStateException("Utilisateur non trouvé"));

        Article article = new Article();
        article.setLibelle(dto.getLibelleArticle() != null && !dto.getLibelleArticle().isBlank()
                ? dto.getLibelleArticle().trim()
                : "Article");
        article.setDescription(dto.getDescriptionArticle() != null ? dto.getDescriptionArticle().trim() : "");
        String code = dto.getCodeProduit() != null ? dto.getCodeProduit().trim() : "";
        if (!code.isEmpty()) {
            if (articleRepository.existsByBoutiqueUuidAndCodeProduitIgnoreCase(utilisateur.getBoutique().getUuid(), code)) {
                throw new IllegalArgumentException("Ce code produit existe déjà pour cette boutique.");
            }
            article.setCodeProduit(code);
        } else {
            article.setCodeProduit(generateUniqueCodeProduit(article.getLibelle(), utilisateur.getBoutique().getUuid()));
        }
        article.setCategorie(categorie);
        article.setBoutique(utilisateur.getBoutique());
        article.setUtilisateur(utilisateur);
        article.setQuantiteDansCarton(0);
        article.setTypeGestionUnite(dto.getTypeGestionUnite() != null
                ? dto.getTypeGestionUnite()
                : TypeGestionUniteProduitEnum.MIXTE);
        return articleRepository.save(article);
    }

    private String generateUniqueCodeProduit(String libelle, String boutiqueUuid) {
        String base = slugForCodeProduit(libelle);
        for (int i = 0; i < 24; i++) {
            String suffix = Integer.toHexString(ThreadLocalRandom.current().nextInt(0x1000000)).toUpperCase(Locale.ROOT);
            String candidate = base + "-" + suffix;
            if (!articleRepository.existsByBoutiqueUuidAndCodeProduitIgnoreCase(boutiqueUuid, candidate)) {
                return candidate;
            }
        }
        String fallback = "REF-" + UUID.randomUUID().toString().replace("-", "").substring(0, 12).toUpperCase(Locale.ROOT);
        if (!articleRepository.existsByBoutiqueUuidAndCodeProduitIgnoreCase(boutiqueUuid, fallback)) {
            return fallback;
        }
        return "REF-" + UUID.randomUUID().toString().replace("-", "").toUpperCase(Locale.ROOT);
    }

    private static String slugForCodeProduit(String libelle) {
        if (libelle == null || libelle.isBlank()) {
            return "REF";
        }
        String n = Normalizer.normalize(libelle.trim(), Normalizer.Form.NFD).replaceAll("\\p{M}+", "");
        String s = n.toUpperCase(Locale.ROOT).replaceAll("[^A-Z0-9]+", "-");
        s = s.replaceAll("^-+", "").replaceAll("-+$", "");
        if (s.length() > 20) {
            s = s.substring(0, 20);
        }
        return s.isEmpty() ? "REF" : s;
    }

    private TypeGestionUniteProduitEnum resolveUnitMode(ProduitStockDto dto) {
        return dto.getTypeGestionUnite() != null ? dto.getTypeGestionUnite() : TypeGestionUniteProduitEnum.MIXTE;
    }

    /**
     * Pour NORMAL : une seule ligne ×1 (ignore les autres lignes du formulaire).
     */
    private List<TypeUniteDeVenteDto> normalizedTypeUniteDtosForPersistence(ProduitStockDto dto) {
        List<TypeUniteDeVenteDto> raw = dto.getTypeUniteDeVenteDtos();
        if (raw == null || raw.isEmpty()) {
            return Collections.emptyList();
        }
        if (resolveUnitMode(dto) == TypeGestionUniteProduitEnum.NORMAL) {
            TypeUniteDeVenteDto first = raw.get(0);
            TypeUniteDeVenteDto one = new TypeUniteDeVenteDto();
            one.setUuid(first.getUuid());
            one.setQtite(first.getQtite());
            one.setPrice(first.getPrice());
            TypeUniteEnum en = first.getTypeUniteEnum() != null
                    ? first.getTypeUniteEnum()
                    : (dto.getTypeUniteEnum() != null ? dto.getTypeUniteEnum() : TypeUniteEnum.PIECE);
            one.setTypeUniteEnum(en);
            one.setUnite(1);
            return Collections.singletonList(one);
        }
        return raw;
    }

    /**
     * Crée les {@link TypeUniteDeVente} manquants pour l’article (à partir du DTO) ou une unité par défaut.
     * @param defaultUnitPrice prix unitaire de référence (0 = fiche catalogue sans prix ; les achats renseignent les prix stock).
     */
    private void ensureArticleSaleUnits(Article article, ProduitStockDto dto, double defaultUnitPrice) {
        List<TypeUniteDeVente> existing = new ArrayList<>(typeUniteDeVenteRepository.findByArticle(article));

        if (resolveUnitMode(dto) == TypeGestionUniteProduitEnum.NORMAL) {
            TypeUniteEnum en = TypeUniteEnum.PIECE;
            double pxRow = 0d;
            if (dto.getTypeUniteDeVenteDtos() != null && !dto.getTypeUniteDeVenteDtos().isEmpty()) {
                TypeUniteDeVenteDto first = dto.getTypeUniteDeVenteDtos().get(0);
                if (first.getTypeUniteEnum() != null) {
                    en = first.getTypeUniteEnum();
                }
                if (first.getPrice() >= 0) {
                    pxRow = first.getPrice();
                }
            } else if (dto.getTypeUniteEnum() != null) {
                en = dto.getTypeUniteEnum();
            }
            final TypeUniteEnum enNormal = en;
            boolean has = existing.stream().anyMatch(e -> e.getTypeUniteEnum() == enNormal && e.getUnite() == 1);
            if (!has) {
                double px = defaultUnitPrice > 0
                        ? (pxRow >= 0 ? pxRow : defaultUnitPrice)
                        : (pxRow >= 0 ? pxRow : 0d);
                TypeUniteDeVente t = new TypeUniteDeVente();
                t.setArticle(article);
                t.setTypeUniteEnum(enNormal);
                t.setUnite(1);
                t.setPrice(px);
                typeUniteDeVenteRepository.save(t);
            }
            return;
        }

        if (dto.getTypeUniteDeVenteDtos() != null && !dto.getTypeUniteDeVenteDtos().isEmpty()) {
            for (TypeUniteDeVenteDto u : dto.getTypeUniteDeVenteDtos()) {
                if (u.getTypeUniteEnum() == null) {
                    continue;
                }
                int factor = u.getUnite() > 0 ? u.getUnite() : 1;
                TypeUniteEnum en = u.getTypeUniteEnum();
                boolean has = existing.stream().anyMatch(e -> e.getTypeUniteEnum() == en && e.getUnite() == factor);
                if (!has) {
                    TypeUniteDeVente t = new TypeUniteDeVente();
                    t.setArticle(article);
                    t.setTypeUniteEnum(en);
                    t.setUnite(factor);
                    double px;
                    if (u.getPrice() > 0) {
                        px = u.getPrice();
                    } else if (defaultUnitPrice > 0) {
                        px = defaultUnitPrice * factor;
                    } else {
                        px = 0d;
                    }
                    t.setPrice(px);
                    t = typeUniteDeVenteRepository.save(t);
                    existing.add(t);
                }
            }
            return;
        }
        if (existing.isEmpty()) {
            TypeUniteEnum def = dto.getTypeUniteEnum() != null ? dto.getTypeUniteEnum() : TypeUniteEnum.PIECE;
            TypeUniteDeVente tuv = new TypeUniteDeVente();
            tuv.setArticle(article);
            tuv.setTypeUniteEnum(def);
            tuv.setUnite(1);
            tuv.setPrice(defaultUnitPrice > 0 ? defaultUnitPrice : 0d);
            typeUniteDeVenteRepository.save(tuv);
        }
    }

    @Override
    public ProduitStockDto updateProduit(ProduitStockDto produitStockDto, String uuid) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Produit produit = produitRepository.findById(uuid)
                .orElseThrow(() -> new IllegalArgumentException("Produit inconnu"));
        Article article = articleRepository.findById(produitStockDto.getUuidArticle())
                .orElseThrow(() -> new IllegalArgumentException("Article inconnu"));
        if (produitStockDto.getTypeGestionUnite() != null) {
            article.setTypeGestionUnite(produitStockDto.getTypeGestionUnite());
            articleRepository.save(article);
        }

        Fournisseur fournisseur = null;
        if (produitStockDto.getUuidFournisseur() != null && !produitStockDto.getUuidFournisseur().isBlank()) {
            fournisseur = fournisseurRepository.findById(produitStockDto.getUuidFournisseur())
                    .orElseThrow(() -> new IllegalArgumentException("Fournisseur inconnu"));
        }

        Utilisateur utilisateur = utilisateurRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new IllegalStateException("Utilisateur non trouvé"));
        produit.setFournisseur(fournisseur);
        produit.setArticle(article);
        produit.setBoutique(utilisateur.getBoutique());
        produit.setUtilisateur(utilisateur);
        produit.setDateEnregistrement(new Date());
        produit.setPrixAchat(produitStockDto.getPrixAchat());
        produit.setPrixVente(produitStockDto.getPrixVente());
        produit.setQuantite(produitStockDto.getQuantiteStock());
        produit.setQuantiteCommande(produitStockDto.getQuantiteCommande());
        produit.setQuantiteLivraison(produitStockDto.getQuantiteLivraison());
        produit.setQuantitePublish(produitStockDto.getQuantitePublish());
        produit.setQuantiteImage(produitStockDto.getQuantiteStockImage());
        produit.setQuantiteImageLivraison(produitStockDto.getQuantiteImageLivraison());
        produit.setQuantiteVendu(produitStockDto.getQuantiteVendu());
        produit.setDatePeremption(produitStockDto.getDatePeremption());
        produit.setSeuilMinimum(SEUIL_MINIMUM_DEFAUT);
        // Les quantités et prix sont maintenant gérés via StockUniteVente

        if (produitStockDto.getUuidEmplacement() != null && !produitStockDto.getUuidEmplacement().isBlank()) {
            EtagereRayon etagereRayon = etagereRayonRepository.findById(produitStockDto.getUuidEmplacement())
                    .orElseThrow(() -> new IllegalArgumentException("Emplacement inconnu"));
            produit.setEmplacement(etagereRayon);
        } else {
            produit.setEmplacement(null);
        }

        Produit produitSave = produitRepository.save(produit);

        if(produitStockDto.getCaracteristiqueProduitDtos() !=null){
            produitStockDto.getCaracteristiqueProduitDtos().forEach(c -> {
                CaracteristiqueProduit  caracteristiqueProduit = caracteristiqueProduitRepository.findById(c.getUuid())
                        .orElseThrow(() -> new IllegalArgumentException("Caractéristique produit inconnue"));
                caracteristiqueProduit.setProduit(produitSave);
                caracteristiqueProduit.setBoutique(produitSave.getBoutique());
                caracteristiqueProduit.setUtilisateur(produit.getUtilisateur());
                caracteristiqueProduit.setLibelle(c.getLibelle());
                caracteristiqueProduit.setValue(c.getValue());
                caracteristiqueProduitRepository.save(caracteristiqueProduit);
            });
        }

        if(produitStockDto.getProduitStoredDtos() != null){
            produitStockDto.getProduitStoredDtos().forEach(ps->{
                StoredFile storedFile = storedFileRepository.findById(ps.getUuidStored())
                       .orElseThrow(() -> new IllegalArgumentException("Fichier stocké inconnu"));
                ProduitStored produitStored = produitStoredRepository.findById(ps.getUuid())
                        .orElseThrow(() -> new IllegalArgumentException("Ligne produit stockée inconnue"));
                produitStored.setProduit(produitSave);
                produitStored.setStoredFile(storedFile);
                produitStoredRepository.save(produitStored);
            });
        }

        // Gestion des unités de vente avec logique de conversion pour la mise à jour
        // Les unités de vente sont définies au niveau de l'Article
        List<TypeUniteDeVenteDto> unitDtosUpd = normalizedTypeUniteDtosForPersistence(produitStockDto);
        if (!unitDtosUpd.isEmpty()) {
            List<StockUniteVenteService.TypeUniteDeVenteQuantite> unitesAvecQuantites = new ArrayList<>();
            
            for (TypeUniteDeVenteDto u : unitDtosUpd) {
                // Vérifier si l'unité existe déjà pour cet article
                List<TypeUniteDeVente> unitesExistantes = typeUniteDeVenteRepository.findByArticle(article);
                int factorUpd = u.getUnite() > 0 ? u.getUnite() : 1;
                TypeUniteDeVente typeUniteDeVente = unitesExistantes.stream()
                    .filter(unite -> unite.getTypeUniteEnum() == u.getTypeUniteEnum() && unite.getUnite() == factorUpd)
                    .findFirst()
                    .orElse(null);
                
                // Si l'unité n'existe pas, la créer pour l'article
                if(typeUniteDeVente == null){
                    typeUniteDeVente = new TypeUniteDeVente();
                    typeUniteDeVente.setTypeUniteEnum(u.getTypeUniteEnum());
                    typeUniteDeVente.setUnite(factorUpd);
                    typeUniteDeVente.setPrice(u.getPrice() >= 0 ? u.getPrice() : 0d);
                    typeUniteDeVente.setArticle(article);
                    typeUniteDeVente = typeUniteDeVenteRepository.save(typeUniteDeVente);
                }
                
                // Si une quantité a été fournie, l'ajouter à la liste pour le calcul
                if(u.getQtite() > 0){
                    unitesAvecQuantites.add(new StockUniteVenteService.TypeUniteDeVenteQuantite(typeUniteDeVente, u.getQtite()));
                }
            }
            
            // S'assurer que les stocks sont initialisés
            stockUniteVenteService.initializeStocksForProduit(produitSave);
            
            // Si des quantités ont été fournies, recalculer et mettre à jour toutes les unités
            if(!unitesAvecQuantites.isEmpty()){
                stockUniteVenteService.addStockMultipleUnits(produitSave, unitesAvecQuantites);
                produitRepository.save(produitSave);
            } else {
                // Sinon, mettre à jour toutes les unités en fonction de la quantité totale actuelle
                int quantiteBaseTotale = stockUniteVenteService.calculateTotalBaseQuantity(produitSave);
                stockUniteVenteService.updateAllUnitsFromBaseQuantity(produitSave, quantiteBaseTotale);
            }
        } else {
            // Même sans unités fournies, s'assurer que les stocks sont initialisés
            stockUniteVenteService.initializeStocksForProduit(produitSave);
        }

        return Mapper.toProduitStockDto(produitSave, stockUniteVenteService);
    }

    @Override
    public ProduitStockDto getProduit(String uuid) {
        Produit produit = produitRepository.findById(uuid)
                .orElseThrow(() -> new IllegalArgumentException("Produit inconnu"));
        return Mapper.toProduitStockDto(produit, stockUniteVenteService);
    }

    @Override
    public PageDataDto<ProduitStockDto> liste(int size, int page, String libelle) {
    	
        return null;
    }

    @Override
    public ProduitStockDto updateProduit(String uuid, ProduitStockDto produitStockDto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Produit produit = produitRepository.findById(uuid)
                .orElseThrow(() -> new IllegalArgumentException("Produit inconnu"));
        Article article = articleRepository.findById(produitStockDto.getUuidArticle())
                .orElseThrow(() -> new IllegalArgumentException("Article inconnu"));
        if (produitStockDto.getTypeGestionUnite() != null) {
            article.setTypeGestionUnite(produitStockDto.getTypeGestionUnite());
            articleRepository.save(article);
        }

        Fournisseur fournisseur = null;
        if (produitStockDto.getUuidFournisseur() != null && !produitStockDto.getUuidFournisseur().isBlank()) {
            fournisseur = fournisseurRepository.findById(produitStockDto.getUuidFournisseur())
                    .orElseThrow(() -> new IllegalArgumentException("Fournisseur inconnu"));
        }

        EtagereRayon etagereRayon = null;
        if (produitStockDto.getUuidEmplacement() != null && !produitStockDto.getUuidEmplacement().isBlank()) {
            etagereRayon = etagereRayonRepository.findById(produitStockDto.getUuidEmplacement())
                    .orElseThrow(() -> new IllegalArgumentException("Emplacement inconnu"));
        }

        Utilisateur utilisateur = utilisateurRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new IllegalStateException("Utilisateur non trouvé"));
        produit.setFournisseur(fournisseur);
        produit.setArticle(article);

        produit.setBoutique(utilisateur.getBoutique());
        // Les quantités et prix sont maintenant gérés via StockUniteVente
        produit.setPrixAchat(produitStockDto.getPrixAchat());
        produit.setPrixVente(produitStockDto.getPrixVente());
        produit.setQuantite(produitStockDto.getQuantiteStock());
        produit.setQuantiteCommande(produitStockDto.getQuantiteCommande());
        produit.setQuantiteLivraison(produitStockDto.getQuantiteLivraison());
        produit.setQuantitePublish(produitStockDto.getQuantitePublish());
        produit.setQuantiteImage(produitStockDto.getQuantiteStockImage());
        produit.setQuantiteImageLivraison(produitStockDto.getQuantiteImageLivraison());
        produit.setQuantiteVendu(produitStockDto.getQuantiteVendu());
        produit.setDatePeremption(produitStockDto.getDatePeremption());
        produit.setSeuilMinimum(SEUIL_MINIMUM_DEFAUT);
        produit.setEmplacement(etagereRayon);

        Produit produitSave = produitRepository.save(produit);

        if (produitStockDto.getCaracteristiqueProduitDtos() != null) {
            produitStockDto.getCaracteristiqueProduitDtos().forEach(c -> {
                CaracteristiqueProduit caracteristiqueProduit = new CaracteristiqueProduit();
                caracteristiqueProduit.setProduit(produitSave);
                caracteristiqueProduit.setBoutique(produitSave.getBoutique());
                caracteristiqueProduit.setUtilisateur(produit.getUtilisateur());
                caracteristiqueProduit.setLibelle(c.getLibelle());
                caracteristiqueProduit.setValue(c.getValue());
                caracteristiqueProduitRepository.save(caracteristiqueProduit);
            });
        }

        if (produitStockDto.getProduitStoredDtos() != null) {
            produitStockDto.getProduitStoredDtos().forEach(ps -> {
                StoredFile storedFile = storedFileRepository.findById(ps.getUuidStored())
                        .orElseThrow(() -> new IllegalArgumentException("Fichier stocké inconnu"));
                ProduitStored produitStored = new ProduitStored();
                produitStored.setProduit(produitSave);
                produitStored.setStoredFile(storedFile);
                produitStoredRepository.save(produitStored);
            });
        }

        // Gestion des unités de vente - les unités sont définies au niveau de l'article
        List<TypeUniteDeVenteDto> unitDtosUpd2 = normalizedTypeUniteDtosForPersistence(produitStockDto);
        if (!unitDtosUpd2.isEmpty()) {
            for (TypeUniteDeVenteDto u : unitDtosUpd2) {
                // Vérifier si l'unité existe déjà pour cet article
                List<TypeUniteDeVente> unitesExistantes = typeUniteDeVenteRepository.findByArticle(article);
                int factor = u.getUnite() > 0 ? u.getUnite() : 1;
                TypeUniteDeVente typeUniteDeVente = unitesExistantes.stream()
                    .filter(unite -> unite.getTypeUniteEnum() == u.getTypeUniteEnum() && unite.getUnite() == factor)
                    .findFirst()
                    .orElse(null);
                
                // Si l'unité n'existe pas, la créer pour l'article
                if(typeUniteDeVente == null){
                    typeUniteDeVente = new TypeUniteDeVente();
                    typeUniteDeVente.setTypeUniteEnum(u.getTypeUniteEnum());
                    typeUniteDeVente.setUnite(factor);
                    typeUniteDeVente.setPrice(u.getPrice() >= 0 ? u.getPrice() : 0d);
                    typeUniteDeVente.setArticle(article);
                    typeUniteDeVenteRepository.save(typeUniteDeVente);
                }
            }
            
            // Initialiser les stocks pour ce produit
            stockUniteVenteService.initializeStocksForProduit(produitSave);
        }


        return Mapper.toProduitStockDto(produitSave, stockUniteVenteService);
    }

    @Override
    public ImportProduitJsonResultDto importProduitsJson(List<ProduitStockDto> lignes,
            String uuidFournisseurFallbackCommande) {
        ImportProduitJsonResultDto result = new ImportProduitJsonResultDto();
        List<PendingBonLine> pendingBons = new ArrayList<>();
        if (lignes == null) {
            result.getErreurs().add("Liste nulle.");
            return result;
        }
        result.setLignesRecues(lignes.size());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getName() == null) {
            result.getErreurs().add("Utilisateur non authentifié.");
            return result;
        }
        Utilisateur utilisateur = utilisateurRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new IllegalStateException("Utilisateur non trouvé"));
        String boutiqueUuid = utilisateur.getBoutique().getUuid();

        for (int i = 0; i < lignes.size(); i++) {
            ProduitStockDto dto = lignes.get(i);
            int lineNo = i + 1;
            if (dto.getLibelleArticle() == null || dto.getLibelleArticle().isBlank()) {
                result.getErreurs().add("Ligne " + lineNo + " : libellé article vide.");
                continue;
            }
            try {
                JsonBonSnapshot bonSnap = JsonBonSnapshot.from(dto);
                normalizeProduitJsonPourImport(dto);

                String catUuid = resolveUuidCategorieForImport(dto, utilisateur);
                dto.setUuidCategorie(catUuid);

                if (dto.getUuidArticle() == null || dto.getUuidArticle().isBlank()) {
                    articleRepository
                            .findFirstByBoutique_UuidAndLibelleIgnoreCase(boutiqueUuid, dto.getLibelleArticle().trim())
                            .ifPresent(a -> dto.setUuidArticle(a.getUuid()));
                }

                if (dto.getUuidArticle() != null && !dto.getUuidArticle().isBlank()) {
                    Optional<Produit> existant = produitRepository.findFirstByBoutique_UuidAndArticle_Uuid(boutiqueUuid,
                            dto.getUuidArticle().trim());
                    if (existant.isPresent()) {
                        result.getAvertissements().add("Ligne " + lineNo + " (« " + dto.getLibelleArticle()
                                + " ») : produit déjà présent pour cet article — ignorée.");
                        result.setLignesIgnorees(result.getLignesIgnorees() + 1);
                        tryEnqueueBonLigne(dto, bonSnap, uuidFournisseurFallbackCommande, pendingBons);
                        continue;
                    }
                }

                ProduitStockDto saved = addProduit(dto);
                tryEnqueueBonLigne(saved, bonSnap, uuidFournisseurFallbackCommande, pendingBons);
                result.setLignesImportees(result.getLignesImportees() + 1);
            } catch (Exception e) {
                result.getErreurs().add("Ligne " + lineNo + " (« " + dto.getLibelleArticle() + " ») : " + e.getMessage());
            }
        }
        flushPendingCommandesFournisseur(pendingBons, result);
        return result;
    }

    @Override
    public ProduitStockDto ensureProduitForJsonImport(ProduitStockDto dto) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getName() == null) {
            throw new IllegalStateException("Utilisateur non authentifié.");
        }
        Utilisateur utilisateur = utilisateurRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new IllegalStateException("Utilisateur non trouvé"));
        String boutiqueUuid = utilisateur.getBoutique().getUuid();

        if (dto.getLibelleArticle() == null || dto.getLibelleArticle().isBlank()) {
            throw new IllegalArgumentException("Libellé article vide.");
        }
        normalizeProduitJsonPourImport(dto);
        String catUuid = resolveUuidCategorieForImport(dto, utilisateur);
        dto.setUuidCategorie(catUuid);

        if (dto.getUuidArticle() == null || dto.getUuidArticle().isBlank()) {
            articleRepository
                    .findFirstByBoutique_UuidAndLibelleIgnoreCase(boutiqueUuid, dto.getLibelleArticle().trim())
                    .ifPresent(a -> dto.setUuidArticle(a.getUuid()));
        }

        if (dto.getUuidArticle() != null && !dto.getUuidArticle().isBlank()) {
            Optional<Produit> existant = produitRepository.findFirstByBoutique_UuidAndArticle_Uuid(boutiqueUuid,
                    dto.getUuidArticle().trim());
            if (existant.isPresent()) {
                return Mapper.toProduitStockDto(existant.get(), stockUniteVenteService);
            }
        }

        return addProduit(dto);
    }

    /**
     * Import JSON : si {@code typeGestionUnite == NORMAL}, une seule unité de vente PIECE ×1 au prix catalogue
     * {@code prixVente} (les autres lignes du JSON sont ignorées pour la fiche article).
     */
    private void normalizeProduitJsonPourImport(ProduitStockDto dto) {
        if (dto.getTypeGestionUnite() != TypeGestionUniteProduitEnum.NORMAL) {
            return;
        }
        TypeUniteDeVenteDto piece = new TypeUniteDeVenteDto();
        piece.setTypeUniteEnum(TypeUniteEnum.PIECE);
        piece.setUnite(1);
        piece.setPrice(Math.max(0d, dto.getPrixVente()));
        piece.setQtite(0);
        List<TypeUniteDeVenteDto> u = new ArrayList<>();
        u.add(piece);
        dto.setTypeUniteDeVenteDtos(u);
        if (dto.getTypeUniteEnum() == null) {
            dto.setTypeUniteEnum(TypeUniteEnum.PIECE);
        }
    }

    private static final class JsonBonSnapshot {
        final Date dateCommande;
        final int quantiteCommande;
        final double prixAchat;
        final TypeUniteEnum mainUniteEnum;
        final int subUniteFactor;
        final TypeGestionUniteProduitEnum typeGestionUnite;

        JsonBonSnapshot(Date d, int q, double p, TypeUniteEnum m, int sf, TypeGestionUniteProduitEnum tg) {
            this.dateCommande = d;
            this.quantiteCommande = q;
            this.prixAchat = p;
            this.mainUniteEnum = m;
            this.subUniteFactor = sf;
            this.typeGestionUnite = tg;
        }

        static JsonBonSnapshot from(ProduitStockDto dto) {
            return new JsonBonSnapshot(
                    dto.getDateCommande(),
                    dto.getQuantiteCommande(),
                    dto.getPrixAchat(),
                    dto.getMainUniteEnum(),
                    dto.getSubUniteFactor(),
                    dto.getTypeGestionUnite());
        }
    }

    private static final class PendingBonLine {
        final LocalDate date;
        final String uuidFournisseur;
        final String uuidArticle;
        final int quantite;
        final double prixAchat;
        final TypeUniteEnum uniteAchat;
        final int facteurAchat;

        PendingBonLine(LocalDate date, String uuidFournisseur, String uuidArticle, int quantite, double prixAchat,
                TypeUniteEnum uniteAchat, int facteurAchat) {
            this.date = date;
            this.uuidFournisseur = uuidFournisseur;
            this.uuidArticle = uuidArticle;
            this.quantite = quantite;
            this.prixAchat = prixAchat;
            this.uniteAchat = uniteAchat;
            this.facteurAchat = facteurAchat;
        }
    }

    private static LocalDate toLocalDateImport(Date date) {
        if (date == null) {
            return null;
        }
        return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }

    private void resolveUniteEnumFacteurPourBon(JsonBonSnapshot snap, TypeUniteEnum[] outEnum, int[] outFactor) {
        if (snap.typeGestionUnite == TypeGestionUniteProduitEnum.NORMAL) {
            outEnum[0] = TypeUniteEnum.PIECE;
            outFactor[0] = 1;
            return;
        }
        TypeUniteEnum m = snap.mainUniteEnum;
        if (m == null || m == TypeUniteEnum.PIECE) {
            outEnum[0] = TypeUniteEnum.PIECE;
            outFactor[0] = 1;
            return;
        }
        outEnum[0] = m;
        outFactor[0] = snap.subUniteFactor > 0 ? snap.subUniteFactor : 1;
    }

    private TypeUniteDeVente resolveTypeUniteDeVentePourBon(Article article, TypeUniteEnum uniteCommande, int facteur) {
        List<TypeUniteDeVente> list = typeUniteDeVenteRepository.findByArticle(article);
        int factor = uniteCommande == TypeUniteEnum.PIECE ? 1 : Math.max(1, facteur);
        for (TypeUniteDeVente t : list) {
            if (t.getTypeUniteEnum() == uniteCommande && t.getUnite() == factor) {
                return t;
            }
        }
        throw new IllegalStateException("Unité de vente introuvable pour l’article « " + article.getLibelle()
                + " » : " + uniteCommande + " ×" + factor);
    }

    private void tryEnqueueBonLigne(ProduitStockDto produitDto, JsonBonSnapshot snap,
            String uuidFournisseurFallback, List<PendingBonLine> pending) {
        String uuidFour = produitDto.getUuidFournisseur();
        if (uuidFour == null || uuidFour.isBlank()) {
            uuidFour = uuidFournisseurFallback;
        }
        if (uuidFour == null || uuidFour.isBlank()) {
            return;
        }
        if (snap.dateCommande == null || snap.quantiteCommande <= 0 || snap.prixAchat <= 0) {
            return;
        }
        String art = produitDto.getUuidArticle();
        if (art == null || art.isBlank()) {
            return;
        }
        LocalDate d = toLocalDateImport(snap.dateCommande);
        if (d == null) {
            return;
        }
        TypeUniteEnum[] ue = new TypeUniteEnum[1];
        int[] fc = new int[1];
        resolveUniteEnumFacteurPourBon(snap, ue, fc);
        pending.add(new PendingBonLine(d, uuidFour.trim(), art.trim(), snap.quantiteCommande, snap.prixAchat, ue[0],
                fc[0]));
    }

    private void flushPendingCommandesFournisseur(List<PendingBonLine> pending, ImportProduitJsonResultDto result) {
        if (pending.isEmpty()) {
            return;
        }
        Map<String, List<PendingBonLine>> byKey = new LinkedHashMap<>();
        for (PendingBonLine p : pending) {
            String key = p.date.toString() + "|" + p.uuidFournisseur;
            byKey.computeIfAbsent(key, k -> new ArrayList<>()).add(p);
        }
        for (Map.Entry<String, List<PendingBonLine>> e : byKey.entrySet()) {
            List<PendingBonLine> group = e.getValue();
            try {
                PendingBonLine first = group.get(0);
                CommandeFournisseurDto cmd = new CommandeFournisseurDto();
                cmd.setUuidFournisseur(first.uuidFournisseur);
                cmd.setDateCommandeFournisseur(first.date);
                cmd.setPaye(false);
                cmd.setValeurMarchandise("Import JSON produits — " + first.date);
                List<DetailCommandeFournisseurDto> details = new ArrayList<>();
                for (PendingBonLine pl : group) {
                    Article article = articleRepository.findById(pl.uuidArticle)
                            .orElseThrow(() -> new IllegalStateException("Article introuvable : " + pl.uuidArticle));
                    TypeUniteDeVente tuv = resolveTypeUniteDeVentePourBon(article, pl.uniteAchat, pl.facteurAchat);
                    DetailCommandeFournisseurDto d = new DetailCommandeFournisseurDto();
                    d.setUuidArticle(pl.uuidArticle);
                    d.setQuantite(pl.quantite);
                    d.setPrixAchat(pl.prixAchat);
                    d.setUnite(pl.uniteAchat.name());
                    d.setUuidTypeUniteDeVente(tuv.getUuid());
                    details.add(d);
                }
                cmd.setDetailCommandeFournisseurDtos(details);
                CommandeFournisseurDto savedCmd = commandeFournisseurService.addCommandeFournisseur(cmd);
                result.getCommandesCreeesUuids().add(savedCmd.getUuid());
            } catch (Exception ex) {
                result.getErreurs().add("Bon commande « " + e.getKey() + " » : " + ex.getMessage());
            }
        }
    }

    private String resolveUuidCategorieForImport(ProduitStockDto dto, Utilisateur u) {
        String uuidCat = dto.getUuidCategorie();
        if (uuidCat != null && !uuidCat.isBlank()) {
            categorieRepository.findById(uuidCat.trim())
                    .orElseThrow(() -> new IllegalArgumentException("Catégorie inconnue : " + uuidCat));
            return uuidCat.trim();
        }
        String lib = dto.getLibelleCategorie();
        if (lib != null && !lib.isBlank()) {
            String key = lib.trim();
            return categorieRepository
                    .findFirstByBoutique_UuidAndLibelleIgnoreCase(u.getBoutique().getUuid(), key)
                    .map(Categorie::getUuid)
                    .orElseGet(() -> {
                        Categorie c = new Categorie();
                        c.setLibelle(key);
                        c.setDescription("Créée automatiquement — import JSON produits");
                        c.setBoutique(u.getBoutique());
                        c.setUtilisateur(u);
                        return categorieRepository.save(c).getUuid();
                    });
        }
        return findOrCreateCategorieAucunJson(u).getUuid();
    }

    private Categorie findOrCreateCategorieAucunJson(Utilisateur u) {
        return categorieRepository
                .findFirstByBoutique_UuidAndLibelleIgnoreCase(u.getBoutique().getUuid(), "AUCUN")
                .orElseGet(() -> {
                    Categorie c = new Categorie();
                    c.setLibelle("AUCUN");
                    c.setDescription("Non classé (import JSON produits)");
                    c.setBoutique(u.getBoutique());
                    c.setUtilisateur(u);
                    return categorieRepository.save(c);
                });
    }
}
