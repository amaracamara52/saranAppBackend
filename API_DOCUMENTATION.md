# Documentation des APIs - Boutique Backend

## Base URL
```
http://localhost:8091
```

## Authentification
Toutes les APIs (sauf `/authenticate`) nécessitent un token JWT dans le header :
```
Authorization: Bearer <token>
```

---

## 1. Authentification

### POST /authenticate
Authentification et génération du token JWT
- **Body**: `JwtRequest` (username, password)
- **Response**: `JwtResponse` (token)

---

## 2. Articles

### POST /api/article
Créer un article
- **Body**: `ArticleDto`

### GET /api/article
Liste de tous les articles

### GET /api/article/{uuid}
Récupérer un article par UUID

### PUT /api/article/{uuid}
Mettre à jour un article
- **Body**: `ArticleDto`

### DELETE /api/article/{uuid}
Supprimer un article

### GET /api/article/page_article
Liste paginée des articles
- **Query params**: `key` (recherche), `page` (défaut: 0), `size` (défaut: 10)

### GET /api/article/article-select
Liste des articles pour sélection

### POST /api/importation/article
Importer des articles depuis un fichier Excel
- **Form data**: `file` (MultipartFile)

---

## 3. Produits

### GET /api/produit
Liste de tous les produits avec stock

### GET /api/produit/perime
Liste des produits périmés

### GET /api/produit/inferieurA5
Liste des produits avec stock inférieur à 5

### GET /api/produit/perimeDans3mois
Liste des produits qui expirent dans 3 mois

### GET /api/produit/vente
Liste des produits disponibles à la vente

### GET /api/produit/page_produit
Liste paginée des produits
- **Query params**: `key` (recherche), `page` (défaut: 0), `size` (défaut: 10)

### POST /api/produitStock
Créer un produit stock
- **Body**: `ProduitStockDto`

### PUT /api/produitStock/{uuid}
Mettre à jour un produit stock
- **Body**: `ProduitStockDto`

---

## 4. Catégories

### POST /api/categorie
Créer une catégorie
- **Body**: `CategorieDto`

### GET /api/categorie
Liste de toutes les catégories

### GET /api/categorie/{uuid}
Récupérer une catégorie par UUID

### PUT /api/categorie/{uuid}
Mettre à jour une catégorie
- **Body**: `CategorieDto`

### DELETE /api/categorie/{uuid}
Supprimer une catégorie

---

## 5. Clients

### POST /api/client
Créer un client
- **Body**: `ClientDto`

### GET /api/client
Liste de tous les clients

### GET /api/client/getById/{uuid}
Récupérer un client par UUID

### PUT /api/client/{uuid}
Mettre à jour un client
- **Body**: `ClientDto`

### DELETE /api/client/{uuid}
Supprimer un client

### DELETE /api/client/delete/{uuid}
Supprimer un client (alternative)

---

## 6. Clients Partenaires

### POST /api/clientPartenaire
Créer un partenariat entre un client et une boutique
- **Body**: `ClientPartenaireDto`

### GET /api/clientPartenaire/{uuid}
Récupérer un partenariat par UUID

### GET /api/clientPartenaire/verifier
Vérifier si un client est partenaire d'une boutique
- **Query params**: `uuidClient`, `uuidBoutique`
- **Response**: `boolean`

### GET /api/clientPartenaire/client/{uuidClient}
Récupérer tous les partenariats d'un client

### GET /api/clientPartenaire/boutique/{uuidBoutique}
Récupérer tous les partenariats d'une boutique

### PUT /api/clientPartenaire/{uuid}/statut
Mettre à jour le statut d'un partenariat
- **Query params**: `statut`

---

## 7. Fournisseurs

### POST /api/fournisseur
Créer un fournisseur
- **Body**: `FournisseurDto`

### GET /api/fournisseur
Liste de tous les fournisseurs

### GET /api/fournisseur/{uuid}
Récupérer un fournisseur par UUID

### PUT /api/fournisseur/{uuid}
Mettre à jour un fournisseur
- **Body**: `FournisseurDto`

### DELETE /api/fournisseur/{uuid}
Supprimer un fournisseur

---

## 8. Commandes Fournisseur

### POST /api/commandeFournisseur
Créer une commande fournisseur (uniquement pour grossistes)
- **Body**: `CommandeFournisseurDto` (avec `detailCommandeFournisseurDtos`)

### GET /api/commandeFournisseur
Liste de toutes les commandes fournisseur

### GET /api/commandeFournisseur/{uuid}
Récupérer une commande fournisseur par UUID

### GET /api/commandeFournisseur/{uuid}/complete
Récupérer une commande complète avec détails, livraisons, paiements

### GET /api/commandeFournisseur/page_commandeFournisseur
Liste paginée des commandes fournisseur
- **Query params**: `key` (StatusCommandeFournisseurEnum), `page` (défaut: 0), `size` (défaut: 10)

### GET /api/commandeFournisseur/page_commandeFournisseur_historique/{dateDebut}/{dateFin}
Historique des commandes par période
- **Path params**: `dateDebut`, `dateFin` (format: yyyy-MM-dd)
- **Query params**: `page` (défaut: 0), `size` (défaut: 10)

### GET /api/commandeFournisseur/search
Recherche avancée de commandes fournisseur
- **Query params**: 
  - `status` (StatusCommandeFournisseurEnum)
  - `uuidFournisseur`
  - `dateDebut` (yyyy-MM-dd)
  - `dateFin` (yyyy-MM-dd)
  - `search` (recherche texte)
  - `page` (défaut: 0)
  - `size` (défaut: 10)

### PUT /api/commandeFournisseur/{uuid}
Mettre à jour une commande fournisseur
- **Body**: `CommandeFournisseurDto`

### DELETE /api/CommandeFournisseur/{uuid}
Supprimer une commande fournisseur

### POST /api/commandeFournisseur/livraison
Ajouter une livraison à une commande fournisseur
- **Body**: `CommandeFournisseurDto`

### POST /api/commandeFournisseur/stock
Enregistrer le stock depuis une livraison
- **Body**: `CommandeFournisseurDto`

---

## 9. Livraisons Commande Fournisseur

### POST /api/livraisonCommandeFournisseur
Créer une livraison commande fournisseur
- **Body**: `LivraisonCommandeFournisseurDto`

### GET /api/livraisonCommandeFournisseur
Liste de toutes les livraisons

### GET /api/livraisonCommandeFournisseur/{uuid}
Récupérer une livraison par UUID

### PUT /api/livraisonCommandeFournisseur/{uuid}
Mettre à jour une livraison
- **Body**: `LivraisonCommandeFournisseurDto`

### DELETE /api/livraisonCommandeFournisseur/{uuid}
Supprimer une livraison

### PUT /api/livraisonCommandeFournisseur/{uuidLivraison}/verifier
Vérifier une livraison (enregistre automatiquement le stock)
- **Query params**: `commentaire` (optionnel)

### PUT /api/livraisonCommandeFournisseur/{uuidLivraison}/rejeter
Rejeter une livraison
- **Query params**: `commentaire` (obligatoire)

---

## 10. Commandes Grossiste

### POST /api/commandeGrossiste
Créer une commande grossiste (détaillant vers grossiste ou client partenaire)
- **Body**: `CommandeGrossisteDto` (peut inclure `uuidClientPartenaire` et `detailsCommande`)

### GET /api/commandeGrossiste/{uuid}
Récupérer une commande grossiste par UUID

### GET /api/commandeGrossiste/{uuid}/complete
Récupérer une commande complète avec détails, livraisons

### GET /api/commandeGrossiste/detaillant
Liste des commandes d'un détaillant

### GET /api/commandeGrossiste/grossiste
Liste des commandes reçues par un grossiste

### GET /api/commandeGrossiste/clientPartenaire/{uuidClientPartenaire}
Liste des commandes d'un client partenaire

### GET /api/commandeGrossiste/page
Liste paginée des commandes
- **Query params**: `page` (défaut: 0), `size` (défaut: 10), `key` (recherche)

### GET /api/commandeGrossiste/search
Recherche avancée de commandes grossiste
- **Query params**: 
  - `statut` (StatusCommandeGrossisteEnum)
  - `uuidClientPartenaire`
  - `uuidBoutiqueGrossiste`
  - `search` (recherche texte)
  - `page` (défaut: 0)
  - `size` (défaut: 10)

### PUT /api/commandeGrossiste/{uuid}/valider
Valider une commande (grossiste uniquement)

### PUT /api/commandeGrossiste/{uuid}/annuler
Annuler une commande

---

## 11. Livraisons Commande Grossiste

### POST /api/livraisonCommandeGrossiste
Créer une livraison commande grossiste
- **Body**: `LivraisonCommandeGrossisteDto`

### POST /api/livraisonCommandeGrossiste/enregistrerStock
Enregistrer le stock depuis une livraison grossiste (si non vérifiée)
- **Body**: `LivraisonCommandeGrossisteDto`

### GET /api/livraisonCommandeGrossiste/{uuid}
Récupérer une livraison par UUID

### GET /api/livraisonCommandeGrossiste/detaillant
Liste des livraisons reçues par un détaillant

### GET /api/livraisonCommandeGrossiste/grossiste
Liste des livraisons envoyées par un grossiste

### PUT /api/livraisonCommandeGrossiste/{uuidLivraison}/verifier
Vérifier une livraison (enregistre automatiquement le stock)
- **Query params**: `commentaire` (optionnel)

### PUT /api/livraisonCommandeGrossiste/{uuidLivraison}/rejeter
Rejeter une livraison
- **Query params**: `commentaire` (obligatoire)

---

## 12. Commandes Vente

### POST /api/commandeVente
Créer une commande de vente
- **Body**: `CommandeVenteRequestDto` (peut inclure `ligneCommandes`)

### GET /api/commandeVente/listeCommandeVente
Liste de toutes les commandes de vente

### GET /api/commandeVente/listeCommandeVenteJour
Liste des commandes de vente du jour

### GET /api/commandeVente/getById/{uuid}
Récupérer une commande de vente par UUID

### GET /api/commandeVente/historique/{dateDebut}/{dateFin}
Historique des commandes par période
- **Path params**: `dateDebut`, `dateFin` (format: yyyy-MM-dd)

### GET /api/commandeVente/page_historique/{dateDebut}/{dateFin}
Historique paginé des commandes
- **Path params**: `dateDebut`, `dateFin` (format: yyyy-MM-dd)
- **Query params**: `key` (recherche), `page`, `size`

### GET /api/commandeVente/page_commande
Liste paginée des commandes du jour
- **Query params**: `key` (recherche), `page` (défaut: 0), `size` (défaut: 10)

### PUT /api/commandeVente/{uuid}
Mettre à jour une commande de vente
- **Body**: `CommandeVenteDto`

### PUT /api/commandeVente/{uuid}/valider
Valider une commande de vente en gros (uniquement pour grossistes)

### DELETE /api/commandeVente/{uuid}
Supprimer une commande de vente

### DELETE /api/commandeVente/delete/{uuid}
Supprimer une commande de vente (alternative)

---

## 13. Lignes Commande

### POST /api/ligneCommande
Créer une ligne de commande
- **Body**: `LigneCommandeDto`

### GET /api/ligneCommande/listeCommandeVente
Liste de toutes les lignes de commande

### GET /api/ligneCommande/getById/{uuid}
Récupérer une ligne de commande par UUID

### PUT /api/ligneCommande/{uuid}
Mettre à jour une ligne de commande
- **Body**: `LigneCommandeDto`

### DELETE /api/ligneCommande/{uuid}
Supprimer une ligne de commande

### DELETE /api/ligneCommandeDto/delete/{uuid}
Supprimer une ligne de commande (alternative)

---

## 14. Détails Commande Fournisseur

### POST /api/detailCommandeFournisseur
Créer un détail de commande fournisseur
- **Body**: `DetailCommandeFournisseurDto`

### GET /api/detailCommandeFournisseur
Liste de tous les détails de commande fournisseur

### GET /api/detailCommandeFourniseur/{uuid}
Récupérer un détail de commande fournisseur par UUID

### PUT /api/detailCommandeFournisseur/{uuid}
Mettre à jour un détail de commande fournisseur
- **Body**: `DetailCommandeFournisseurDto`

### DELETE /api/detailCommandeFournisseur/{uuid}
Supprimer un détail de commande fournisseur

---

## 15. Paiements Commande Fournisseur

### POST /api/paiementCommandeFournisseur
Enregistrer un paiement pour une commande fournisseur
- **Body**: `PaiementCommandeFournisseurDto`

### GET /api/paiementCommandeFournisseur/{uuid}
Récupérer un paiement par UUID

### GET /api/paiementCommandeFournisseur/commande/{uuidCommande}
Récupérer tous les paiements d'une commande fournisseur

### GET /api/paiementCommandeFournisseur/fournisseur/{uuidFournisseur}
Récupérer tous les paiements d'un fournisseur

---

## 16. Versements Client Partenaire

### POST /api/versementClientPartenaire
Enregistrer un versement d'un client partenaire
- **Body**: `VersementClientPartenaireDto`

### GET /api/versementClientPartenaire/{uuid}
Récupérer un versement par UUID

### GET /api/versementClientPartenaire/clientPartenaire/{uuidClientPartenaire}
Récupérer tous les versements d'un client partenaire

### GET /api/versementClientPartenaire/commande/{uuidCommande}
Récupérer tous les versements d'une commande vente

---

## 17. Situation Comptable

### GET /api/situationComptable
Récupérer la situation comptable complète (dettes et créances)

### GET /api/situationComptable/fournisseur/{uuidFournisseur}
Récupérer la situation comptable d'un fournisseur

### GET /api/situationComptable/clientPartenaire/{uuidClientPartenaire}
Récupérer la situation comptable d'un client partenaire

### GET /api/situationComptable/fournisseurs
Récupérer toutes les situations des fournisseurs

### GET /api/situationComptable/clientsPartenaires
Récupérer toutes les situations des clients partenaires

---

## 18. Opérations Caisse

### POST /api/operationCaisse
Enregistrer une opération de caisse
- **Body**: `OperationCaisseDto`

### GET /api/operationCaisse/caisseActuelle
Récupérer toutes les opérations de la caisse actuelle

### GET /api/operationCaisse/caisse/{uuidCaisse}
Récupérer les opérations d'une caisse journalière

### GET /api/operationCaisse/clientPartenaire/{uuidClientPartenaire}
Récupérer les opérations d'un client partenaire

### GET /api/operationCaisse/fournisseur/{uuidFournisseur}
Récupérer les opérations d'un fournisseur

### GET /api/operationCaisse/situation
Récupérer la situation de caisse actuelle

### GET /api/operationCaisse/situation/clientPartenaire/{uuidClientPartenaire}
Récupérer la situation de caisse d'un client partenaire

### GET /api/operationCaisse/situation/fournisseur/{uuidFournisseur}
Récupérer la situation de caisse d'un fournisseur

---

## 19. Caisse Journalière

### POST /api/caisse/ouvrir
Ouvrir une caisse journalière
- **Query params**: `soldeOuverture` (BigDecimal)

### PUT /api/caisse/fermer
Fermer la caisse journalière actuelle
- **Query params**: `soldeFermeture` (BigDecimal)

### GET /api/caisse/actuelle
Récupérer la caisse journalière actuelle

### GET /api/caisse
Liste de toutes les caisses journalières

---

## 20. Transactions

### POST /api/transactions
Créer une transaction
- **Body**: `TransactionDto`

### GET /api/transactions/{uuid}
Liste des transactions d'une caisse

### GET /api/transactions/journalieres
Récupérer les transactions journalières

### GET /api/transactions/totaux/encaissements
Récupérer le total des encaissements journaliers

### GET /api/transactions/totaux/decaissements
Récupérer le total des décaissements journaliers

### GET /api/transactions/totaux/derniere
Récupérer le total de la dernière transaction

### PUT /api/transactions/{uuid}/valider
Valider une transaction

### PUT /api/transactions/{uuid}/annuler
Annuler une transaction

---

## 21. Boutiques

### POST /api/boutique
Créer une boutique
- **Body**: `BoutiqueDto`

### GET /api/boutique
Liste de toutes les boutiques

### GET /api/boutique/{uuid}
Récupérer une boutique par UUID

### GET /api/getByCode/{code}
Récupérer une boutique par code

### GET /api/sync/{code}
Synchroniser une boutique par code

### PUT /api/boutique/{uuid}
Mettre à jour une boutique
- **Body**: `BoutiqueDto`

### DELETE /api/boutique/{uuid}
Supprimer une boutique

### GET /api/auth
Récupérer l'utilisateur authentifié actuel

---

## 22. Utilisateurs

### POST /api/utilisateur
Créer un utilisateur
- **Body**: `UtilisateurDto`

### GET /api/utilisateur
Liste de tous les utilisateurs

### GET /api/utilisateur/{uuid}
Récupérer un utilisateur par UUID

### GET /api/utilisateur/byEmail/{email}
Récupérer un utilisateur par email

### GET /api/utilisateur/login
Récupérer les informations de l'utilisateur connecté

### PUT /api/utilisateur/{uuid}
Mettre à jour un utilisateur
- **Body**: `UtilisateurDto`

### DELETE /api/utilisateur/{uuuid}
Supprimer un utilisateur

---

## 23. Pays

### POST /api/pays
Créer un pays
- **Body**: `PaysDto`

### GET /api/pays
Liste de tous les pays

### GET /api/pays/{uuid}
Récupérer un pays par UUID

### PUT /api/pays/{uuid}
Mettre à jour un pays
- **Body**: `PaysDto`

### DELETE /api/pays/{uuid}
Supprimer un pays

### POST /api/pays/importationFile
Importer des pays depuis un fichier Excel
- **Form data**: `file` (MultipartFile)

---

## 24. Villes

### POST /api/ville
Créer une ville
- **Body**: `VilleDto`

### GET /api/ville
Liste de toutes les villes

### GET /api/ville/{uuid}
Récupérer une ville par UUID

### GET /api/ville/byPays/{uuidPays}
Liste des villes d'un pays

### PUT /api/ville/{uuid}
Mettre à jour une ville
- **Body**: `VilleDto`

### DELETE /api/ville
Supprimer une ville

---

## 25. Communes

### POST /api/commune
Créer une commune
- **Body**: `CommuneDto`

### GET /api/commune
Liste de toutes les communes

### GET /api/commune/{uuid}
Récupérer une commune par UUID

### GET /api/commune/byVille/{uuidVille}
Liste des communes d'une ville

### PUT /api/commune/{uuid}
Mettre à jour une commune
- **Body**: `CommuneDto`

### DELETE /api/commune
Supprimer une commune

---

## 26. Quartiers

### POST /api/quartier
Créer un quartier
- **Body**: `QuartierDto`

### GET /api/quartier
Liste de tous les quartiers

### GET /api/quartier/{uuid}
Récupérer un quartier par UUID

### PUT /api/quartier/{uuid}
Mettre à jour un quartier
- **Body**: `QuartierDto`

### DELETE /api/quartier/{uuid}
Supprimer un quartier

---

## 27. Modes de Paiement

### POST /api/modePaiement
Créer un mode de paiement
- **Body**: `ModePaiementDto`

### GET /api/modePaiement
Liste de tous les modes de paiement

### GET /api/modePaiement/{uuid}
Récupérer un mode de paiement par UUID

### PUT /api/modePaiement/{uuid}
Mettre à jour un mode de paiement
- **Body**: `ModePaiementDto`

### DELETE /api/modePaiement
Supprimer un mode de paiement

---

## 28. Réductions

### POST /api/reduction/{boutiqueUuid}
Créer une réduction pour une boutique
- **Body**: `ReductionDto`

### GET /api/reduction
Liste de toutes les réductions

### GET /api/reduction/{uuid}
Récupérer une réduction par UUID

### GET /api/reduction/boutique/{boutiqueUuid}
Liste des réductions d'une boutique

### GET /api/reduction/boutique/{boutiqueUuid}/active
Liste des réductions actives d'une boutique

### GET /api/reduction/boutique/{boutiqueUuid}/categorie/{categorie}
Liste des réductions d'une boutique par catégorie

### GET /api/reduction/boutique/{boutiqueUuid}/valid
Liste des réductions valides d'une boutique

### GET /api/reduction/boutique/{boutiqueUuid}/taux-min/{tauxRemise}
Liste des réductions avec taux minimum

### GET /api/reduction/boutique/{boutiqueUuid}/search
Rechercher des réductions par libellé
- **Query params**: `libelle`

### GET /api/reduction/boutique/{boutiqueUuid}/applicable
Récupérer les réductions applicables
- **Query params**: `montant`, `categorie` (optionnel)

### GET /api/reduction/{reductionUuid}/valid
Vérifier si une réduction est valide

### GET /api/reduction/{reductionUuid}/active
Vérifier si une réduction est active

### POST /api/reduction/{reductionUuid}/calculate
Calculer le montant de la remise
- **Query params**: `montantOriginal`

### PUT /api/reduction/{uuid}
Mettre à jour une réduction
- **Body**: `ReductionDto`

### DELETE /api/reduction/{uuid}
Supprimer une réduction

---

## 29. Types de Boutique

### POST /api/typeShop
Créer un type de boutique
- **Body**: `TypeShopDto`

### GET /api/typeShop
Liste de tous les types de boutique

### PUT /api/typeShop/{uuid}
Mettre à jour un type de boutique
- **Body**: `TypeShopDto`

---

## 30. Caractéristiques Article

### POST /api/caracteristiqueArticle
Créer une caractéristique d'article
- **Body**: `CaracteristiqueArticleDto`

### GET /api/caracteristiqueArticle
Liste de toutes les caractéristiques d'article

### GET /api/caracteristiqueArticle/{uuid}
Récupérer une caractéristique d'article par UUID

### PUT /api/caracteristiqueArticle/{uuid}
Mettre à jour une caractéristique d'article
- **Body**: `CaracteristiqueArticleDto`

### DELETE /api/caracteristiqueArticle/{uuid}
Supprimer une caractéristique d'article

---

## 31. Caractéristiques Produit

### POST /api/caracteristiqueProduit
Créer une caractéristique de produit
- **Body**: `CaracteristiqueProduitDto`

### GET /api/caracteristiqueProduit/listecaracteristiqueProduit
Liste de toutes les caractéristiques de produit

### GET /api/caracteristiqueProduit/getById/{uuid}
Récupérer une caractéristique de produit par UUID

### PUT /api/caracteristiqueProduit/{uuid}
Mettre à jour une caractéristique de produit
- **Body**: `CaracteristiqueProduitDto`

### DELETE /api/caracteristiqueProduit/{uuid}
Supprimer une caractéristique de produit

### DELETE /api/caracteristique/delete/{uuid}
Supprimer une caractéristique de produit (alternative)

---

## 32. Étagères et Rayons

### POST /api/etagere
Créer une étagère
- **Body**: `EtagereDto`

### POST /api/rayon
Créer un rayon
- **Body**: `RayonDto`

### POST /api/emplacement
Créer un emplacement (étagère-rayon)
- **Body**: `EtagereRayonDto`

### GET /api/etagere
Liste de toutes les étagères

### GET /api/rayon
Liste de tous les rayons

### GET /api/emplacement
Liste de tous les emplacements

### GET /api/emplacement/{uuid}
Récupérer un emplacement par UUID

### GET /api/emplacement/page_emplacement
Liste paginée des emplacements
- **Query params**: `key` (recherche), `page` (défaut: 0), `size` (défaut: 10)

### PUT /api/etagere/{uuid}
Mettre à jour une étagère
- **Body**: `EtagereDto`

### PUT /api/rayon/{uuid}
Mettre à jour un rayon
- **Body**: `RayonDto`

### PUT /api/emplacement/{uuid}
Mettre à jour un emplacement
- **Body**: `EtagereRayonDto`

### DELETE /api/emplacement/{uuid}
Supprimer un emplacement

### POST /api/emplacement/fileImportation
Importer des emplacements depuis un fichier Excel
- **Form data**: `file` (MultipartFile)

### POST /api/importation/rayon
Importer des rayons depuis un fichier Excel
- **Form data**: `file` (MultipartFile)

---

## 33. Paramètres

### POST /api/parametre
Créer un paramètre
- **Body**: `ParametreDto`

### GET /api/parametre
Liste de tous les paramètres

### GET /api/parametre/{uuid}
Récupérer un paramètre par UUID

### PUT /api/parametre/{uuid}
Mettre à jour un paramètre
- **Body**: `ParametreDto`

### DELETE /api/parametre/{uuid}
Supprimer un paramètre

---

## 34. Configuration (Domaines)

### POST /configuration/domain
Créer un domaine
- **Body**: `DomainDto`

### GET /configuration/domain
Liste de tous les domaines

### GET /configuration/domain/{uuid}
Récupérer un domaine par UUID

### PUT /configuration/domain/{uuid}
Mettre à jour un domaine
- **Body**: `DomainDto`

### POST /configuration/domainCategorie
Créer une catégorie de domaine
- **Body**: `DomainCategorieDto`

### GET /configuration/domainCategorie
Liste de toutes les catégories de domaine

### GET /configuration/domainCategorie/{uuid}
Récupérer une catégorie de domaine par UUID

### GET /configuration/domainCategorieByDomain/{uuidDomain}
Liste des catégories d'un domaine

### PUT /configuration/domainCategorie/{uuid}
Mettre à jour une catégorie de domaine
- **Body**: `DomainCategorieDto`

### POST /configuration/domainCategorieParam
Créer un paramètre de catégorie de domaine
- **Body**: `DomainCategorieParamDto`

### GET /configuration/domainCategorieParam
Liste de tous les paramètres de catégorie de domaine

### GET /configuration/domainCategorieParam/{uuid}
Récupérer un paramètre de catégorie de domaine par UUID

### GET /configuration/domainCategorieParam/{uuidDomainCategorie}
Liste des paramètres d'une catégorie de domaine

### PUT /configuration/domainCategorieParam/{uuid}
Mettre à jour un paramètre de catégorie de domaine
- **Body**: `DomainCategorieParamDto`

---

## 35. Domaines Boutique

### POST /api/domaine
Créer un domaine boutique
- **Body**: `DomainBoutiqueDto`

### GET /api/domaine
Liste de tous les domaines boutique

### GET /api/domaine/{uuid}
Récupérer un domaine boutique par UUID

### PUT /api/domaine/{uuid}
Mettre à jour un domaine boutique
- **Body**: `DomainBoutiqueDto`

---

## 36. Boutique Paiement

### POST /api/boutiquePaiment
Créer un paiement de boutique
- **Body**: `BoutiquePaiementDto`

### GET /api/boutiquePaiement
Liste de tous les paiements de boutique

### GET /api/boutiquePaiement/{uuid}
Récupérer un paiement de boutique par UUID

### PUT /api/boutiquePaiement/{uuid}
Mettre à jour un paiement de boutique
- **Body**: `BoutiquePaiementDto`

### DELETE /api/boutiquePaiement/{uuid}
Supprimer un paiement de boutique

---

## 37. Fichiers Stockés

### POST /upload
Uploader un fichier
- **Form data**: `file` (MultipartFile)
- **Response**: `StoredFileInfoDto`

### GET /file/{uuid}
Récupérer un fichier par UUID
- **Response**: `StoredFileDto`

### GET /listefile
Récupérer la liste des fichiers

### DELETE /storeFile/{uuid}
Supprimer un fichier

---

## 38. Enums

### GET /enum/typeBoutique
Liste des types de boutique

### GET /enum/genre
Liste des genres

### GET /enum/role
Liste des rôles

### GET /enum/statutOnlineOrder
Liste des statuts de commande en ligne

### GET /enum/typeInstanceBoutique
Liste des types d'instance de boutique (GROSSISTE, DETAILLANT)

### GET /enum/typeShop
Liste des types de shop

### GET /enum/statutCommandeVente
Liste des statuts de commande de vente

### GET /enum/typeUnite
Liste des types d'unité

---

## 39. Statistiques

### GET /api/statistique/produit/topVenteProduit
Top des produits les plus vendus

### GET /api/statistique/produit/reste_a_recouvrer
Reste à recouvrer

### GET /api/statistique/produit/stock_a_recouvrer
Stock à recouvrer

### GET /api/statistique/produit/commande_vendu_aujourdhui
Commandes vendues aujourd'hui

### GET /api/statistique/produit/totalProduitVendu
Total des produits vendus

### GET /api/statistique/produit/situationVenteParMois
Situation des ventes par mois

---

## 40. Statistiques Ventes

### GET /api/stats/ventes/generales
Statistiques générales des ventes

### GET /api/stats/ventes/completes
Statistiques complètes des ventes

### GET /api/stats/ventes/periode
Statistiques des ventes par période
- **Query params**: `dateDebut` (yyyy-MM-dd), `dateFin` (yyyy-MM-dd)

### GET /api/stats/ventes/boutique/{boutiqueUuid}
Statistiques des ventes par boutique

### GET /api/stats/ventes/par-type
Statistiques des ventes par type de commande

### GET /api/stats/ventes/evolution/{derniersMois}
Évolution des ventes par mois
- **Path params**: `derniersMois` (int)

### GET /api/stats/ventes/comparer
Comparer deux périodes
- **Query params**: 
  - `periode1Debut` (yyyy-MM-dd)
  - `periode1Fin` (yyyy-MM-dd)
  - `periode2Debut` (yyyy-MM-dd)
  - `periode2Fin` (yyyy-MM-dd)

### GET /api/stats/ventes/native
Statistiques natives des ventes

---

## 41. Statistiques Clients

### GET /api/stats/clients/top-par-commandes
Top clients par nombre de commandes
- **Query params**: `limit` (défaut: 10)

### GET /api/stats/clients/top-par-montant
Top clients par montant total
- **Query params**: `limit` (défaut: 10)

### GET /api/stats/clients/top-par-periode
Top clients par période
- **Query params**: 
  - `dateDebut` (ISO DateTime)
  - `dateFin` (ISO DateTime)
  - `limit` (défaut: 10)

### GET /api/stats/clients/top-par-boutique/{boutiqueUuid}
Top clients par boutique
- **Query params**: `limit` (défaut: 10)

### GET /api/stats/clients/top-fideles
Top clients fidèles
- **Query params**: 
  - `derniersDays` (défaut: 30)
  - `nombreCommandesMin` (défaut: 3)
  - `limit` (défaut: 10)

### GET /api/stats/clients/top-payees
Top clients avec commandes payées
- **Query params**: `limit` (défaut: 10)

### GET /api/stats/clients/top-native
Top clients (méthode native)
- **Query params**: `limit` (défaut: 10)

### GET /api/stats/clients/{clientUuid}/statistiques
Statistiques d'un client spécifique

---

## 42. Statistiques Produits

### GET /api/stats/produits/top-vendus
Top produits les plus vendus
- **Query params**: `limit` (défaut: 10)

### GET /api/stats/produits/top-vendus/avec-categorie
Top produits vendus avec catégorie
- **Query params**: `limit` (défaut: 10)

### GET /api/stats/produits/top-vendus/periode
Top produits vendus par période
- **Query params**: 
  - `dateDebut` (yyyy-MM-dd)
  - `dateFin` (yyyy-MM-dd)
  - `limit` (défaut: 10)

### GET /api/stats/produits/top-vendus/boutique/{boutiqueUuid}
Top produits vendus par boutique
- **Query params**: `limit` (défaut: 10)

### GET /api/stats/produits/top-vendus/categorie
Top produits vendus par catégorie
- **Query params**: 
  - `categorieLibelle`
  - `limit` (défaut: 10)

### GET /api/stats/produits/top-vendus/native
Top produits vendus (méthode native)
- **Query params**: `limit` (défaut: 10)

---

## 43. Mobile

### POST /api/registration
Créer une inscription mobile
- **Body**: `RegistrationDto`

### GET /api/get-connected-user
Récupérer l'utilisateur connecté
- **Response**: `UserDto`

### GET /api/shops-mounted
Récupérer les boutiques montées
- **Response**: `ShopMountedDto`

### GET /api/getRegistration-connected-user
Récupérer l'inscription de l'utilisateur connecté
- **Response**: `RegistrationDto`

### POST /api/registration/{uuidImageProfile}/{uuidLogo}/{monnaie}
Finaliser la configuration du profil
- **Path params**: `uuidImageProfile`, `uuidLogo`, `monnaie`
- **Body**: `List<DomainDto>`

---

## 44. Publicité Mobile

### POST /product
Créer une publicité produit
- **Body**: `PubliciteDto`

### GET /product
Liste paginée des produits publicitaires
- **Query params**: 
  - `name` (défaut: "")
  - `uuidCategorie` (optionnel)
  - `minPrice` (défaut: 1000)
  - `maxPrice` (défaut: 10000000)
  - `page` (défaut: 0)
  - `size` (défaut: 10)

---

## Notes Importantes

1. **Authentification**: Tous les endpoints (sauf `/authenticate`) nécessitent un token JWT dans le header `Authorization: Bearer <token>`

2. **Port par défaut**: Le serveur écoute sur le port `8091` (configuré dans `application.properties`)

3. **Format des dates**: 
   - Pour les endpoints avec dates dans le path: `yyyy-MM-dd`
   - Pour les query params: `yyyy-MM-dd` ou ISO DateTime selon l'endpoint

4. **Pagination**: La plupart des endpoints de liste supportent la pagination avec:
   - `page`: Numéro de page (défaut: 0)
   - `size`: Taille de la page (défaut: 10)
   - `key`: Terme de recherche (optionnel)

5. **Commandes avec détails**: Les endpoints `POST /api/commandeFournisseur` et `POST /api/commandeGrossiste` acceptent maintenant les détails directement dans le body, permettant d'enregistrer la commande et ses détails simultanément.

6. **Vérification des livraisons**: Les endpoints de vérification de livraison (`/verifier`) enregistrent automatiquement le stock dans le système.

7. **Types d'instance**: Les boutiques peuvent être de type `GROSSISTE` ou `DETAILLANT`, ce qui détermine les fonctionnalités disponibles.

---

## Codes de Réponse HTTP

- `200 OK`: Requête réussie
- `201 Created`: Ressource créée avec succès
- `400 Bad Request`: Requête invalide
- `401 Unauthorized`: Non authentifié
- `403 Forbidden`: Non autorisé
- `404 Not Found`: Ressource non trouvée
- `500 Internal Server Error`: Erreur serveur

---

## Objets (DTOs)

Cette section documente tous les objets (DTOs) utilisés dans les APIs.

### JwtRequest
Objet de requête pour l'authentification.

```json
{
  "username": "string",
  "password": "string"
}
```

**Champs:**
- `username` (String): Nom d'utilisateur ou email
- `password` (String): Mot de passe

---

### JwtResponse
Réponse d'authentification contenant le token JWT.

```json
{
  "token": "string"
}
```

**Champs:**
- `token` (String): Token JWT pour l'authentification

---

### ArticleDto
Objet représentant un article.

```json
{
  "uuid": "string",
  "libelle": "string",
  "description": "string",
  "uuidCategorie": "string",
  "categorie": "string",
  "uuidBoutique": "string",
  "uuidUtilisateur": "string",
  "quantiteDansCarton": 0
}
```

**Champs:**
- `uuid` (String): Identifiant unique
- `libelle` (String): Libellé de l'article
- `description` (String): Description de l'article
- `uuidCategorie` (String): UUID de la catégorie
- `categorie` (String): Libellé de la catégorie
- `uuidBoutique` (String): UUID de la boutique
- `uuidUtilisateur` (String): UUID de l'utilisateur
- `quantiteDansCarton` (int): Quantité dans un carton

---

### CommandeFournisseurDto
Objet représentant une commande fournisseur.

```json
{
  "uuid": "string",
  "valeurMarchandise": "string",
  "dateCommandeFournisseur": "2024-01-01",
  "isPaye": false,
  "uuidBoutique": "string",
  "uuidFournisseur": "string",
  "uuidUtilisateur": "string",
  "refCommande": "string",
  "nom": "string",
  "prenom": "string",
  "telephone": "string",
  "email": "string",
  "adresse": "string",
  "Status": "ENREGISTRER",
  "detailCommandeFournisseurDtos": [],
  "montantTotal": 0.0,
  "montantPaye": 0.0,
  "montantRestant": 0.0,
  "nombreLivraisons": 0,
  "nombrePaiements": 0,
  "libelleBoutique": "string",
  "libelleFournisseur": "string",
  "paysProvenanceFournisseur": "string"
}
```

**Champs:**
- `uuid` (String): Identifiant unique
- `valeurMarchandise` (String): Valeur de la marchandise
- `dateCommandeFournisseur` (LocalDate): Date de la commande
- `isPaye` (boolean): Indique si la commande est payée
- `uuidBoutique` (String): UUID de la boutique
- `uuidFournisseur` (String): UUID du fournisseur
- `uuidUtilisateur` (String): UUID de l'utilisateur
- `refCommande` (String): Référence de la commande
- `nom` (String): Nom du fournisseur
- `prenom` (String): Prénom du fournisseur
- `telephone` (String): Téléphone du fournisseur
- `email` (String): Email du fournisseur
- `adresse` (String): Adresse du fournisseur
- `Status` (StatusCommandeFournisseurEnum): Statut de la commande
- `detailCommandeFournisseurDtos` (List<DetailCommandeFournisseurDto>): Liste des détails
- `montantTotal` (double): Montant total de la commande
- `montantPaye` (double): Montant payé
- `montantRestant` (double): Montant restant à payer
- `nombreLivraisons` (int): Nombre de livraisons
- `nombrePaiements` (int): Nombre de paiements
- `libelleBoutique` (String): Libellé de la boutique
- `libelleFournisseur` (String): Libellé du fournisseur
- `paysProvenanceFournisseur` (String): Pays de provenance du fournisseur

---

### DetailCommandeFournisseurDto
Détail d'une commande fournisseur.

```json
{
  "uuid": "string",
  "uuidArticle": "string",
  "article": "string",
  "categorie": "string",
  "description": "string",
  "quantite": 0,
  "prixAchat": 0.0,
  "uuidCommandeFournisseur": "string",
  "dateCommande": "2024-01-01",
  "valeurMarchandise": "string",
  "Status": "ENREGISTRER",
  "uuidBoutique": "string",
  "uuidUtilisateur": "string",
  "utilisateur": "string",
  "unite": "string",
  "uuidTypeUniteDeVente": "string",
  "quantiteLivraison": 0
}
```

**Champs:**
- `uuid` (String): Identifiant unique
- `uuidArticle` (String): UUID de l'article
- `article` (String): Libellé de l'article
- `categorie` (String): Catégorie de l'article
- `description` (String): Description
- `quantite` (int): Quantité commandée
- `prixAchat` (double): Prix d'achat unitaire
- `uuidCommandeFournisseur` (String): UUID de la commande
- `dateCommande` (LocalDate): Date de commande
- `valeurMarchandise` (String): Valeur marchandise
- `Status` (StatusCommandeFournisseurEnum): Statut
- `uuidBoutique` (String): UUID de la boutique
- `uuidUtilisateur` (String): UUID de l'utilisateur
- `utilisateur` (String): Nom de l'utilisateur
- `unite` (String): Unité de vente (ancien champ)
- `uuidTypeUniteDeVente` (String): UUID de l'unité de vente
- `quantiteLivraison` (int): Quantité livrée

---

### CommandeGrossisteDto
Objet représentant une commande grossiste.

```json
{
  "uuid": "string",
  "numeroCommande": "string",
  "dateCommande": "2024-01-01",
  "montantTotal": 0.0,
  "statut": "EN_ATTENTE",
  "uuidBoutiqueDetaillee": "string",
  "uuidBoutiqueGrossiste": "string",
  "uuidUtilisateurDetaillee": "string",
  "uuidUtilisateurGrossiste": "string",
  "dateValidation": "2024-01-01",
  "libelleBoutiqueDetaillee": "string",
  "libelleBoutiqueGrossiste": "string",
  "uuidClientPartenaire": "string",
  "numeroCompteClientPartenaire": "string",
  "nomClientPartenaire": "string",
  "detailsCommande": [],
  "nombreLivraisons": 0,
  "nombreArticles": 0,
  "peutEtreValidee": false,
  "paysProvenanceClientPartenaire": "string"
}
```

**Champs:**
- `uuid` (String): Identifiant unique
- `numeroCommande` (String): Numéro de commande
- `dateCommande` (LocalDate): Date de commande
- `montantTotal` (double): Montant total
- `statut` (StatusCommandeGrossisteEnum): Statut de la commande
- `uuidBoutiqueDetaillee` (String): UUID boutique détaillante
- `uuidBoutiqueGrossiste` (String): UUID boutique grossiste
- `uuidUtilisateurDetaillee` (String): UUID utilisateur détaillant
- `uuidUtilisateurGrossiste` (String): UUID utilisateur grossiste
- `dateValidation` (LocalDate): Date de validation
- `libelleBoutiqueDetaillee` (String): Libellé boutique détaillante
- `libelleBoutiqueGrossiste` (String): Libellé boutique grossiste
- `uuidClientPartenaire` (String): UUID client partenaire (si applicable)
- `numeroCompteClientPartenaire` (String): Numéro de compte client partenaire
- `nomClientPartenaire` (String): Nom du client partenaire
- `detailsCommande` (List<DetailCommandeGrossisteDto>): Liste des détails
- `nombreLivraisons` (int): Nombre de livraisons
- `nombreArticles` (int): Nombre d'articles
- `peutEtreValidee` (boolean): Si la commande peut être validée
- `paysProvenanceClientPartenaire` (String): Pays de provenance

---

### DetailCommandeGrossisteDto
Détail d'une commande grossiste.

```json
{
  "uuid": "string",
  "uuidArticle": "string",
  "uuidProduit": "string",
  "uuidCommandeGrossiste": "string",
  "uuidTypeUniteDeVente": "string",
  "libelleArticle": "string",
  "categorie": "string",
  "quantite": 0,
  "prixUnitaire": 0.0,
  "montantTotal": 0.0,
  "typeUniteEnum": "PIECE",
  "unite": 0
}
```

**Champs:**
- `uuid` (String): Identifiant unique
- `uuidArticle` (String): UUID de l'article
- `uuidProduit` (String): UUID du produit
- `uuidCommandeGrossiste` (String): UUID de la commande
- `uuidTypeUniteDeVente` (String): UUID de l'unité de vente
- `libelleArticle` (String): Libellé de l'article
- `categorie` (String): Catégorie
- `quantite` (int): Quantité
- `prixUnitaire` (double): Prix unitaire
- `montantTotal` (double): Montant total
- `typeUniteEnum` (String): Type d'unité (PIECE, CARTON, etc.)
- `unite` (int): Valeur de l'unité

---

### CommandeVenteDto
Objet représentant une commande de vente.

```json
{
  "uuid": "string",
  "montantCommande": 0.0,
  "montantCommandeImage": 0.0,
  "numeroCommande": "string",
  "isPaye": false,
  "datePaiement": "2024-01-01",
  "id_client": "string",
  "client": "string",
  "uuidBoutique": "string",
  "uuidUtilisateur": "string",
  "utilisateur": "string",
  "typeCommande": "GROS",
  "status": "EN_ATTENTE",
  "nombreArticle": 0,
  "ligneCommandeDtos": [],
  "livraisonCommandeVenteDto": {},
  "uuidModePaiement": "string",
  "modePaiement": "string"
}
```

**Champs:**
- `uuid` (String): Identifiant unique
- `montantCommande` (double): Montant de la commande
- `montantCommandeImage` (double): Montant avec image
- `numeroCommande` (String): Numéro de commande
- `isPaye` (boolean): Si payé
- `datePaiement` (Date): Date de paiement
- `id_client` (String): ID du client
- `client` (String): Nom du client
- `uuidBoutique` (String): UUID de la boutique
- `uuidUtilisateur` (String): UUID de l'utilisateur
- `utilisateur` (String): Nom de l'utilisateur
- `typeCommande` (EnumTypeCommande): Type de commande (GROS, DETAILLANT)
- `status` (StatusCommandeVenteEnum): Statut
- `nombreArticle` (int): Nombre d'articles
- `ligneCommandeDtos` (List<LigneCommandeDto>): Lignes de commande
- `livraisonCommandeVenteDto` (LivraisonCommandeVenteDto): Livraison
- `uuidModePaiement` (String): UUID mode de paiement
- `modePaiement` (String): Libellé mode de paiement

---

### LigneCommandeDto
Ligne d'une commande de vente.

```json
{
  "uuid": "string",
  "quantite": 0,
  "uuidCommandeVente": "string",
  "uuidProduit": "string",
  "uuidBoutique": "string",
  "uuidUtilisateur": "string",
  "article": "string",
  "quantiteRestant": 0,
  "emplacement": "string",
  "utilisateur": "string",
  "prixVente": 0.0,
  "datePeremption": "2024-01-01",
  "quantiteStock": 0,
  "uuidTypeUniteDeVente": "string",
  "typeUniteDeVente": "string",
  "caracteristiqueArticleDtos": []
}
```

**Champs:**
- `uuid` (String): Identifiant unique
- `quantite` (int): Quantité
- `uuidCommandeVente` (String): UUID de la commande
- `uuidProduit` (String): UUID du produit
- `uuidBoutique` (String): UUID de la boutique
- `uuidUtilisateur` (String): UUID de l'utilisateur
- `article` (String): Libellé de l'article
- `quantiteRestant` (int): Quantité restante
- `emplacement` (String): Emplacement
- `utilisateur` (String): Nom utilisateur
- `prixVente` (double): Prix de vente
- `datePeremption` (Date): Date de péremption
- `quantiteStock` (int): Quantité en stock
- `uuidTypeUniteDeVente` (String): UUID unité de vente
- `typeUniteDeVente` (String): Type d'unité
- `caracteristiqueArticleDtos` (List<CaracteristiqueProduitDto>): Caractéristiques

---

### LivraisonCommandeFournisseurDto
Objet représentant une livraison de commande fournisseur.

```json
{
  "uuid": "string",
  "dateLivraison": "2024-01-01",
  "Heure": "string",
  "uuidDetailCommandeFournisseur": "string",
  "uuidBoutique": "string",
  "uuidCommandeFournisseur": "string",
  "quantite": 0,
  "prix": 0.0,
  "uuidUtilisateur": "string",
  "statutVerification": "EN_ATTENTE",
  "verifiee": false,
  "dateVerification": "2024-01-01",
  "uuidUtilisateurVerificateur": "string",
  "nomUtilisateurVerificateur": "string",
  "commentaireVerification": "string"
}
```

**Champs:**
- `uuid` (String): Identifiant unique
- `dateLivraison` (Date): Date de livraison
- `Heure` (String): Heure de livraison
- `uuidDetailCommandeFournisseur` (String): UUID du détail
- `uuidBoutique` (String): UUID de la boutique
- `uuidCommandeFournisseur` (String): UUID de la commande
- `quantite` (int): Quantité livrée
- `prix` (double): Prix unitaire
- `uuidUtilisateur` (String): UUID de l'utilisateur
- `statutVerification` (String): Statut de vérification (EN_ATTENTE, VERIFIEE, REJETEE)
- `verifiee` (boolean): Si vérifiée
- `dateVerification` (Date): Date de vérification
- `uuidUtilisateurVerificateur` (String): UUID du vérificateur
- `nomUtilisateurVerificateur` (String): Nom du vérificateur
- `commentaireVerification` (String): Commentaire

---

### LivraisonCommandeGrossisteDto
Objet représentant une livraison de commande grossiste.

```json
{
  "uuid": "string",
  "dateLivraison": "2024-01-01",
  "heure": "string",
  "quantite": 0,
  "prix": 0.0,
  "numeroSuivi": "string",
  "transporteur": "string",
  "uuidCommandeGrossiste": "string",
  "uuidDetailCommandeGrossiste": "string",
  "uuidBoutiqueDetaillee": "string",
  "uuidBoutiqueGrossiste": "string",
  "libelleBoutiqueDetaillee": "string",
  "libelleBoutiqueGrossiste": "string",
  "libelleArticle": "string",
  "statutVerification": "EN_ATTENTE",
  "verifiee": false,
  "dateVerification": "2024-01-01",
  "uuidUtilisateurVerificateur": "string",
  "nomUtilisateurVerificateur": "string",
  "commentaireVerification": "string"
}
```

**Champs:**
- `uuid` (String): Identifiant unique
- `dateLivraison` (Date): Date de livraison
- `heure` (String): Heure de livraison
- `quantite` (int): Quantité livrée
- `prix` (double): Prix unitaire
- `numeroSuivi` (String): Numéro de suivi
- `transporteur` (String): Transporteur
- `uuidCommandeGrossiste` (String): UUID de la commande
- `uuidDetailCommandeGrossiste` (String): UUID du détail
- `uuidBoutiqueDetaillee` (String): UUID boutique détaillante
- `uuidBoutiqueGrossiste` (String): UUID boutique grossiste
- `libelleBoutiqueDetaillee` (String): Libellé boutique détaillante
- `libelleBoutiqueGrossiste` (String): Libellé boutique grossiste
- `libelleArticle` (String): Libellé de l'article
- `statutVerification` (String): Statut de vérification
- `verifiee` (boolean): Si vérifiée
- `dateVerification` (Date): Date de vérification
- `uuidUtilisateurVerificateur` (String): UUID vérificateur
- `nomUtilisateurVerificateur` (String): Nom vérificateur
- `commentaireVerification` (String): Commentaire

---

### ClientPartenaireDto
Objet représentant un client partenaire.

```json
{
  "uuid": "string",
  "uuidClient": "string",
  "uuidBoutique": "string",
  "nomClient": "string",
  "prenomClient": "string",
  "emailClient": "string",
  "libelleBoutique": "string",
  "dateCreation": "2024-01-01",
  "dateExpiration": "2024-01-01",
  "statut": "ACTIF",
  "numeroCompte": "string",
  "conditionsSpeciales": "string",
  "limiteCredit": 0.0,
  "uuidUtilisateur": "string",
  "uuidPaysProvenance": "string",
  "libellePaysProvenance": "string",
  "countryCodePaysProvenance": "string"
}
```

**Champs:**
- `uuid` (String): Identifiant unique
- `uuidClient` (String): UUID du client
- `uuidBoutique` (String): UUID de la boutique
- `nomClient` (String): Nom du client
- `prenomClient` (String): Prénom du client
- `emailClient` (String): Email du client
- `libelleBoutique` (String): Libellé de la boutique
- `dateCreation` (Date): Date de création
- `dateExpiration` (Date): Date d'expiration
- `statut` (String): Statut (ACTIF, INACTIF, SUSPENDU)
- `numeroCompte` (String): Numéro de compte
- `conditionsSpeciales` (String): Conditions spéciales
- `limiteCredit` (Double): Limite de crédit
- `uuidUtilisateur` (String): UUID de l'utilisateur
- `uuidPaysProvenance` (String): UUID du pays de provenance
- `libellePaysProvenance` (String): Libellé du pays
- `countryCodePaysProvenance` (String): Code pays

---

### ClientDto
Objet représentant un client.

```json
{
  "uuid": "string",
  "nom": "string",
  "prenom": "string",
  "phone": "string",
  "email": "string",
  "uuidBoutique": "string",
  "uuidUtilisateur": "string"
}
```

**Champs:**
- `uuid` (String): Identifiant unique
- `nom` (String): Nom
- `prenom` (String): Prénom
- `phone` (String): Téléphone
- `email` (String): Email
- `uuidBoutique` (String): UUID de la boutique
- `uuidUtilisateur` (String): UUID de l'utilisateur

---

### FournisseurDto
Objet représentant un fournisseur.

```json
{
  "uuid": "string",
  "nom": "string",
  "prenom": "string",
  "telephone": "string",
  "email": "string",
  "uuidBoutique": "string",
  "uuidUtilisateur": "string",
  "nomComplet": "string",
  "uuidPaysProvenance": "string",
  "libellePaysProvenance": "string",
  "countryCodePaysProvenance": "string"
}
```

**Champs:**
- `uuid` (String): Identifiant unique
- `nom` (String): Nom
- `prenom` (String): Prénom
- `telephone` (String): Téléphone
- `email` (String): Email
- `uuidBoutique` (String): UUID de la boutique
- `uuidUtilisateur` (String): UUID de l'utilisateur
- `nomComplet` (String): Nom complet
- `uuidPaysProvenance` (String): UUID du pays de provenance
- `libellePaysProvenance` (String): Libellé du pays
- `countryCodePaysProvenance` (String): Code pays

---

### BoutiqueDto
Objet représentant une boutique.

```json
{
  "uuid": "string",
  "libelleBoutique": "string",
  "descriptionBoutique": "string",
  "emailBoutique": "string",
  "phoneBoutique": "string",
  "siteBoutique": "string",
  "adresse": "string",
  "domaine": "string",
  "uuidDomain": "string",
  "typeDomaine": "string",
  "image": "string",
  "utilisateur": "string",
  "email": "string",
  "password": "string",
  "uuidStoreFile": "string",
  "pays": "string",
  "uuidPays": "string",
  "role": "ADMIN",
  "domainBoutiqueDtos": [],
  "domaines": [],
  "devise": "string",
  "langue": "string",
  "seuilAlerteStock": 0,
  "methodeValorisation": "string",
  "approvisionnementAutomatique": false,
  "quantiteACommander": 0,
  "impressionTicket": false,
  "impressionFacture": false,
  "devis": false,
  "dette": false
}
```

**Champs:**
- `uuid` (String): Identifiant unique
- `libelleBoutique` (String): Libellé de la boutique
- `descriptionBoutique` (String): Description
- `emailBoutique` (String): Email de la boutique
- `phoneBoutique` (String): Téléphone
- `siteBoutique` (String): Site web
- `adresse` (String): Adresse
- `domaine` (String): Domaine
- `uuidDomain` (String): UUID du domaine
- `typeDomaine` (String): Type de domaine
- `image` (String): Image
- `utilisateur` (String): Utilisateur
- `email` (String): Email utilisateur
- `password` (String): Mot de passe
- `uuidStoreFile` (String): UUID fichier stocké
- `pays` (String): Pays
- `uuidPays` (String): UUID du pays
- `role` (EnumRole): Rôle
- `domainBoutiqueDtos` (List<DomainBoutiqueDto>): Domaines
- `domaines` (List<String>): Liste des domaines
- `devise` (String): Devise
- `langue` (String): Langue
- `seuilAlerteStock` (int): Seuil d'alerte stock
- `methodeValorisation` (String): Méthode de valorisation
- `approvisionnementAutomatique` (boolean): Approvisionnement automatique
- `quantiteACommander` (int): Quantité à commander
- `impressionTicket` (boolean): Impression ticket
- `impressionFacture` (boolean): Impression facture
- `devis` (boolean): Devis
- `dette` (boolean): Dette

---

### UtilisateurDto
Objet représentant un utilisateur.

```json
{
  "uuid": "string",
  "username": "string",
  "phone": "string",
  "email": "string",
  "motDePasse": "string",
  "adresse": "string",
  "role": [],
  "boutique": "string",
  "libelleBoutique": "string",
  "domaine": "string",
  "uuidDomain": "string",
  "typeDomaine": "string",
  "image": "string",
  "pays": "string",
  "monnaie": "string"
}
```

**Champs:**
- `uuid` (String): Identifiant unique
- `username` (String): Nom d'utilisateur
- `phone` (String): Téléphone
- `email` (String): Email
- `motDePasse` (String): Mot de passe
- `adresse` (String): Adresse
- `role` (List<EnumRole>): Liste des rôles
- `boutique` (String): UUID de la boutique
- `libelleBoutique` (String): Libellé de la boutique
- `domaine` (String): Domaine
- `uuidDomain` (String): UUID du domaine
- `typeDomaine` (String): Type de domaine
- `image` (String): Image
- `pays` (String): Pays
- `monnaie` (String): Monnaie

---

### PaiementCommandeFournisseurDto
Objet représentant un paiement de commande fournisseur.

```json
{
  "uuid": "string",
  "uuidCommandeFournisseur": "string",
  "uuidModePaiement": "string",
  "montantVerse": 0.0,
  "datePaiement": "2024-01-01",
  "numeroReference": "string",
  "commentaire": "string",
  "uuidRecuPaiement": "string",
  "libelleModePaiement": "string",
  "refCommande": "string"
}
```

**Champs:**
- `uuid` (String): Identifiant unique
- `uuidCommandeFournisseur` (String): UUID de la commande
- `uuidModePaiement` (String): UUID du mode de paiement
- `montantVerse` (double): Montant versé
- `datePaiement` (Date): Date de paiement
- `numeroReference` (String): Numéro de référence
- `commentaire` (String): Commentaire
- `uuidRecuPaiement` (String): UUID du reçu
- `libelleModePaiement` (String): Libellé du mode de paiement
- `refCommande` (String): Référence de la commande

---

### VersementClientPartenaireDto
Objet représentant un versement de client partenaire.

```json
{
  "uuid": "string",
  "uuidClientPartenaire": "string",
  "uuidCommandeVente": "string",
  "uuidModePaiement": "string",
  "montantVerse": 0.0,
  "dateVersement": "2024-01-01",
  "numeroReference": "string",
  "commentaire": "string",
  "uuidRecuVersement": "string",
  "libelleModePaiement": "string",
  "nomClient": "string",
  "numeroCompte": "string"
}
```

**Champs:**
- `uuid` (String): Identifiant unique
- `uuidClientPartenaire` (String): UUID du client partenaire
- `uuidCommandeVente` (String): UUID de la commande
- `uuidModePaiement` (String): UUID du mode de paiement
- `montantVerse` (double): Montant versé
- `dateVersement` (Date): Date de versement
- `numeroReference` (String): Numéro de référence
- `commentaire` (String): Commentaire
- `uuidRecuVersement` (String): UUID du reçu
- `libelleModePaiement` (String): Libellé du mode de paiement
- `nomClient` (String): Nom du client
- `numeroCompte` (String): Numéro de compte

---

### OperationCaisseDto
Objet représentant une opération de caisse.

```json
{
  "uuid": "string",
  "typeOperation": "ENCAISSEMENT",
  "montant": 0.0,
  "dateOperation": "2024-01-01",
  "libelle": "string",
  "typeDetail": "string",
  "uuidCaisseJournaliere": "string",
  "uuidTransaction": "string",
  "uuidPaiementCommandeFournisseur": "string",
  "uuidVersementClientPartenaire": "string",
  "uuidCommandeVente": "string",
  "uuidCommandeFournisseur": "string",
  "uuidClientPartenaire": "string",
  "uuidFournisseur": "string",
  "commentaire": "string",
  "numeroCommande": "string",
  "nomClient": "string",
  "nomFournisseur": "string"
}
```

**Champs:**
- `uuid` (String): Identifiant unique
- `typeOperation` (EnumTypeTransaction): Type (ENCAISSEMENT, DECAISSEMENT)
- `montant` (BigDecimal): Montant
- `dateOperation` (Date): Date de l'opération
- `libelle` (String): Libellé
- `typeDetail` (String): Type de détail
- `uuidCaisseJournaliere` (String): UUID de la caisse journalière
- `uuidTransaction` (String): UUID de la transaction
- `uuidPaiementCommandeFournisseur` (String): UUID paiement commande fournisseur
- `uuidVersementClientPartenaire` (String): UUID versement client partenaire
- `uuidCommandeVente` (String): UUID commande vente
- `uuidCommandeFournisseur` (String): UUID commande fournisseur
- `uuidClientPartenaire` (String): UUID client partenaire
- `uuidFournisseur` (String): UUID fournisseur
- `commentaire` (String): Commentaire
- `numeroCommande` (String): Numéro de commande
- `nomClient` (String): Nom du client
- `nomFournisseur` (String): Nom du fournisseur

---

### SituationComptableDto
Objet représentant la situation comptable globale.

```json
{
  "situationsFournisseurs": [],
  "situationsClientsPartenaires": [],
  "totalDettesFournisseurs": 0.0,
  "totalCreancesClientsPartenaires": 0.0,
  "soldeNet": 0.0
}
```

**Champs:**
- `situationsFournisseurs` (List<SituationFournisseurDto>): Situations des fournisseurs
- `situationsClientsPartenaires` (List<SituationClientPartenaireDto>): Situations des clients partenaires
- `totalDettesFournisseurs` (double): Total des dettes fournisseurs
- `totalCreancesClientsPartenaires` (double): Total des créances clients partenaires
- `soldeNet` (double): Solde net (créances - dettes)

---

### SituationFournisseurDto
Situation comptable d'un fournisseur.

```json
{
  "uuidFournisseur": "string",
  "nomFournisseur": "string",
  "emailFournisseur": "string",
  "telephoneFournisseur": "string",
  "montantTotalCommandes": 0.0,
  "montantTotalPaiements": 0.0,
  "detteRestante": 0.0,
  "commandes": []
}
```

**Champs:**
- `uuidFournisseur` (String): UUID du fournisseur
- `nomFournisseur` (String): Nom du fournisseur
- `emailFournisseur` (String): Email
- `telephoneFournisseur` (String): Téléphone
- `montantTotalCommandes` (double): Montant total des commandes
- `montantTotalPaiements` (double): Montant total des paiements
- `detteRestante` (double): Dette restante
- `commandes` (List<CommandeFournisseurSoldeDto>): Liste des commandes

---

### SituationClientPartenaireDto
Situation comptable d'un client partenaire.

```json
{
  "uuidClientPartenaire": "string",
  "uuidClient": "string",
  "nomClient": "string",
  "prenomClient": "string",
  "emailClient": "string",
  "numeroCompte": "string",
  "montantTotalCommandes": 0.0,
  "montantTotalVersements": 0.0,
  "creanceRestante": 0.0,
  "commandes": []
}
```

**Champs:**
- `uuidClientPartenaire` (String): UUID du client partenaire
- `uuidClient` (String): UUID du client
- `nomClient` (String): Nom du client
- `prenomClient` (String): Prénom du client
- `emailClient` (String): Email
- `numeroCompte` (String): Numéro de compte
- `montantTotalCommandes` (double): Montant total des commandes
- `montantTotalVersements` (double): Montant total des versements
- `creanceRestante` (double): Créance restante
- `commandes` (List<CommandeVenteSoldeDto>): Liste des commandes

---

### SituationCaisseDto
Situation de la caisse.

```json
{
  "soldeOuverture": 0.0,
  "totalEncaissements": 0.0,
  "totalDecaissements": 0.0,
  "soldeActuel": 0.0,
  "encaissements": [],
  "decaissements": [],
  "toutesOperations": [],
  "nombreOperations": 0,
  "creanceRestante": 0.0,
  "detteRestante": 0.0
}
```

**Champs:**
- `soldeOuverture` (BigDecimal): Solde d'ouverture
- `totalEncaissements` (BigDecimal): Total des encaissements
- `totalDecaissements` (BigDecimal): Total des décaissements
- `soldeActuel` (BigDecimal): Solde actuel
- `encaissements` (List<OperationCaisseDto>): Liste des encaissements
- `decaissements` (List<OperationCaisseDto>): Liste des décaissements
- `toutesOperations` (List<OperationCaisseDto>): Toutes les opérations
- `nombreOperations` (int): Nombre d'opérations
- `creanceRestante` (BigDecimal): Créance restante
- `detteRestante` (BigDecimal): Dette restante

---

### CaisseJournaliereDto
Objet représentant une caisse journalière.

```json
{
  "uuid": "string",
  "dateCaisse": "2024-01-01",
  "soldeOuverture": 0.0,
  "soldeFermeture": 0.0,
  "totalEncaissement": 0.0,
  "totalDecaissement": 0.0,
  "dateFermeture": "2024-01-01T00:00:00",
  "statutCaisse": "OUVERTE",
  "soldeCalcule": 0.0
}
```

**Champs:**
- `uuid` (String): Identifiant unique
- `dateCaisse` (LocalDate): Date de la caisse
- `soldeOuverture` (BigDecimal): Solde d'ouverture
- `soldeFermeture` (BigDecimal): Solde de fermeture
- `totalEncaissement` (BigDecimal): Total encaissements
- `totalDecaissement` (BigDecimal): Total décaissements
- `dateFermeture` (LocalDateTime): Date de fermeture
- `statutCaisse` (EnumStatutCaisse): Statut (OUVERTE, FERMEE)
- `soldeCalcule` (BigDecimal): Solde calculé

---

### ProduitDto
Objet représentant un produit.

```json
{
  "uuid": "string",
  "prixAchat": 0.0,
  "prixVente": 0.0,
  "quantite": 0,
  "quantiteImage": 0,
  "quantiteCommande": 0,
  "quantiteLivraison": 0,
  "quantitePublish": 0,
  "datePeremption": "2024-01-01",
  "article": "string",
  "uuidArticle": "string",
  "categorie": "string",
  "emplacement": "string",
  "uuidEmplacement": "string",
  "dateCommande": "2024-01-01",
  "fournisseur": "string",
  "uuidFournisseur": "string",
  "uuidBoutique": "string",
  "uuidUtilisateur": "string",
  "unite": "string",
  "isFinish": false,
  "quantiteVendu": 0,
  "etagere": "string",
  "caracteristiqueArticleDtos": [],
  "produitStoredDtos": []
}
```

**Champs:**
- `uuid` (String): Identifiant unique
- `prixAchat` (double): Prix d'achat
- `prixVente` (double): Prix de vente
- `quantite` (int): Quantité en stock
- `quantiteImage` (int): Quantité image
- `quantiteCommande` (int): Quantité commandée
- `quantiteLivraison` (int): Quantité livrée
- `quantitePublish` (int): Quantité publiée
- `datePeremption` (Date): Date de péremption
- `article` (String): Libellé de l'article
- `uuidArticle` (String): UUID de l'article
- `categorie` (String): Catégorie
- `emplacement` (String): Emplacement
- `uuidEmplacement` (String): UUID de l'emplacement
- `dateCommande` (Date): Date de commande
- `fournisseur` (String): Libellé du fournisseur
- `uuidFournisseur` (String): UUID du fournisseur
- `uuidBoutique` (String): UUID de la boutique
- `uuidUtilisateur` (String): UUID de l'utilisateur
- `unite` (String): Unité
- `isFinish` (boolean): Si terminé
- `quantiteVendu` (int): Quantité vendue
- `etagere` (String): Étagère
- `caracteristiqueArticleDtos` (List<CaracteristiqueProduitDto>): Caractéristiques
- `produitStoredDtos` (List<ProduitStoredDto>): Produits stockés

---

### CategorieDto
Objet représentant une catégorie.

```json
{
  "uuid": "string",
  "libelle": "string",
  "description": "string",
  "uuidDomain": "string",
  "uuidUtilisateur": "string",
  "uuidBoutique": "string"
}
```

**Champs:**
- `uuid` (String): Identifiant unique
- `libelle` (String): Libellé
- `description` (String): Description
- `uuidDomain` (String): UUID du domaine
- `uuidUtilisateur` (String): UUID de l'utilisateur
- `uuidBoutique` (String): UUID de la boutique

---

### PaysDto
Objet représentant un pays.

```json
{
  "uuid": "string",
  "libelle": "string",
  "monnaie": "string",
  "capitale": "string",
  "countryCode": "string"
}
```

**Champs:**
- `uuid` (String): Identifiant unique
- `libelle` (String): Libellé du pays
- `monnaie` (String): Monnaie
- `capitale` (String): Capitale
- `countryCode` (String): Code pays (ISO)

---

### ModePaiementDto
Objet représentant un mode de paiement.

```json
{
  "uuid": "string",
  "libelle": "string",
  "description": "string",
  "uuidBoutique": "string",
  "uuidUtilisateur": "string",
  "boutique": "string"
}
```

**Champs:**
- `uuid` (String): Identifiant unique
- `libelle` (String): Libellé
- `description` (String): Description
- `uuidBoutique` (String): UUID de la boutique
- `uuidUtilisateur` (String): UUID de l'utilisateur
- `boutique` (String): Libellé de la boutique

---

### PageDataDto
Objet de pagination générique.

```json
{
  "page": {
    "currentPage": 0,
    "totalPages": 0,
    "totalElements": 0,
    "size": 10
  },
  "data": []
}
```

**Champs:**
- `page` (PageDto): Informations de pagination
- `data` (List<T>): Liste des données

---

### PageDto
Informations de pagination.

```json
{
  "currentPage": 0,
  "totalPages": 0,
  "totalElements": 0,
  "size": 10
}
```

**Champs:**
- `currentPage` (int): Page actuelle
- `totalPages` (int): Nombre total de pages
- `totalElements` (long): Nombre total d'éléments
- `size` (int): Taille de la page

---

*Documentation générée le: 2026-02-04*
*Version de l'API: 1.0*
