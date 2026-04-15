package org.sid.saranApp.serviceImpl;

import org.sid.saranApp.dto.CommandeGrossisteDto;
import org.sid.saranApp.dto.DetailCommandeGrossisteDto;
import org.sid.saranApp.dto.PageDataDto;
import org.sid.saranApp.dto.PageDto;
import org.sid.saranApp.enume.EnumTypeInstanceBoutique;
import org.sid.saranApp.enume.StatusCommandeGrossisteEnum;
import org.sid.saranApp.exception.Exception;
import org.sid.saranApp.model.*;
import org.sid.saranApp.repository.*;
import org.sid.saranApp.service.CommandeGrossisteService;
import org.sid.saranApp.service.StockUniteVenteService;
import org.sid.saranApp.service.ClientPartenaireService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service pour la gestion des commandes entre détaillants et grossistes
 */
@Service
public class CommandeGrossisteServiceImpl implements CommandeGrossisteService {

    private static final Logger logger = LoggerFactory.getLogger(CommandeGrossisteServiceImpl.class);

    @Autowired
    private CommandeGrossisteRepository commandeGrossisteRepository;
    
    @Autowired
    private DetailCommandeGrossisteRepository detailCommandeGrossisteRepository;
    
    @Autowired
    private BoutiqueRepository boutiqueRepository;
    
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    
    @Autowired
    private ArticleRepository articleRepository;
    
    @Autowired
    private ProduitRepository produitRepository;
    
    @Autowired
    private TypeUniteDeVenteRepository typeUniteDeVenteRepository;
    
    @Autowired
    private UtilisateurServiceImpl utilisateurServiceImpl;
    
    @Autowired
    private StockUniteVenteService stockUniteVenteService;
    
    @Autowired
    private ClientPartenaireRepository clientPartenaireRepository;
    
    @Autowired
    private ClientPartenaireService clientPartenaireService;

    /**
     * Crée une nouvelle commande d'un détaillant vers un grossiste
     * Seuls les détaillants peuvent créer des commandes
     */
    @Override
    public CommandeGrossisteDto creerCommande(CommandeGrossisteDto commandeGrossisteDto) {
        logger.info("Création d'une nouvelle commande grossiste");
        
        try {
            Utilisateur utilisateur = utilisateurServiceImpl.getCurentUtilisateur();
            if (utilisateur == null || utilisateur.getBoutique() == null) {
                throw new RuntimeException("Utilisateur ou boutique non trouvé");
            }
            
            Boutique boutiqueDetaillee = utilisateur.getBoutique();
            
            // Vérifier que la boutique est bien un détaillant OU que c'est une commande d'un client partenaire
            // Si c'est une commande d'un client partenaire, on peut accepter même si la boutique n'est pas un détaillant
            boolean isClientPartenaire = commandeGrossisteDto.getUuidClientPartenaire() != null && 
                !commandeGrossisteDto.getUuidClientPartenaire().isEmpty();
            
            if (!isClientPartenaire && boutiqueDetaillee.getEnumTypeInstanceBoutique() != EnumTypeInstanceBoutique.DETAILLANT) {
                throw new IllegalArgumentException("Seuls les détaillants ou les clients partenaires peuvent créer des commandes auprès des grossistes");
            }
            
            // Récupérer le grossiste
            Boutique boutiqueGrossiste = boutiqueRepository.findById(commandeGrossisteDto.getUuidBoutiqueGrossiste())
                .orElseThrow(() -> new RuntimeException("Boutique grossiste non trouvée"));
            
            // Vérifier que la boutique destinataire est bien un grossiste
            if (boutiqueGrossiste.getEnumTypeInstanceBoutique() != EnumTypeInstanceBoutique.GROSSISTE) {
                throw new IllegalArgumentException("La boutique destinataire doit être un grossiste");
            }
            
            // Vérifier si c'est une commande d'un client partenaire
            ClientPartenaire clientPartenaire = null;
            if (commandeGrossisteDto.getUuidClientPartenaire() != null && !commandeGrossisteDto.getUuidClientPartenaire().isEmpty()) {
                clientPartenaire = clientPartenaireRepository.findById(commandeGrossisteDto.getUuidClientPartenaire())
                    .orElseThrow(() -> new RuntimeException("Client partenaire non trouvé"));
                
                // Vérifier que le client partenaire est actif
                if (!"ACTIF".equals(clientPartenaire.getStatut())) {
                    throw new IllegalArgumentException("Le client partenaire n'est pas actif");
                }
                
                // Vérifier que le client partenaire est bien associé au grossiste
                if (!clientPartenaire.getBoutique().getUuid().equals(boutiqueGrossiste.getUuid())) {
                    throw new IllegalArgumentException("Le client partenaire n'est pas associé à ce grossiste");
                }
            }
            
            // Créer la commande
            CommandeGrossiste commande = new CommandeGrossiste();
            commande.setUuid(UUID.randomUUID().toString().replace("-", "").toUpperCase());
            commande.setNumeroCommande("CMD-GR-" + System.currentTimeMillis());
            commande.setDateCommande(LocalDate.now());
            commande.setStatut(StatusCommandeGrossisteEnum.EN_ATTENTE);
            commande.setBoutiqueDetaillee(boutiqueDetaillee);
            commande.setBoutiqueGrossiste(boutiqueGrossiste);
            commande.setUtilisateurDetaillee(utilisateur);
            commande.setClientPartenaire(clientPartenaire);
            
            double montantTotal = 0.0;
            List<DetailCommandeGrossiste> details = new ArrayList<>();
            
            // Traiter les détails de la commande AVANT la sauvegarde
            if (commandeGrossisteDto.getDetailsCommande() != null && !commandeGrossisteDto.getDetailsCommande().isEmpty()) {
                for (DetailCommandeGrossisteDto detailDto : commandeGrossisteDto.getDetailsCommande()) {
                    Article article = articleRepository.findById(detailDto.getUuidArticle())
                        .orElseThrow(() -> new RuntimeException("Article non trouvé: " + detailDto.getUuidArticle()));
                    
                    TypeUniteDeVente typeUniteDeVente = null;
                    if (detailDto.getUuidTypeUniteDeVente() != null) {
                        typeUniteDeVente = typeUniteDeVenteRepository.findById(detailDto.getUuidTypeUniteDeVente())
                            .orElseThrow(() -> new RuntimeException("Unité de vente non trouvée: " + detailDto.getUuidTypeUniteDeVente()));
                    }
                    
                    DetailCommandeGrossiste detail = new DetailCommandeGrossiste();
                    detail.setUuid(UUID.randomUUID().toString().replace("-", "").toUpperCase());
                    detail.setArticle(article);
                    detail.setQuantite(detailDto.getQuantite());
                    detail.setPrixUnitaire(detailDto.getPrixUnitaire());
                    detail.setTypeUniteDeVente(typeUniteDeVente);
                    detail.setCommandeGrossiste(commande);
                    detail.setBoutique(boutiqueDetaillee != null ? boutiqueDetaillee : utilisateur.getBoutique());
                    detail.setUtilisateur(utilisateur);
                    
                    details.add(detail);
                    montantTotal += detailDto.getPrixUnitaire() * detailDto.getQuantite();
                }
            }
            
            // Définir le montant total et les détails AVANT la sauvegarde
            commande.setMontantTotal(montantTotal);
            commande.setDetailsCommande(details);
            
            // Sauvegarder la commande avec ses détails (CascadeType.ALL dans l'entité)
            CommandeGrossiste commandeSave = commandeGrossisteRepository.save(commande);
            
            // Sauvegarder explicitement les détails pour s'assurer qu'ils sont bien persistés
            if (!details.isEmpty()) {
                detailCommandeGrossisteRepository.saveAll(details);
            }
            
            // Recharger la commande avec tous ses détails pour éviter les problèmes de lazy loading
            CommandeGrossiste commandeComplete = commandeGrossisteRepository.findById(commandeSave.getUuid())
                .orElse(commandeSave);
            
            // Forcer le chargement des détails
            if (commandeComplete.getDetailsCommande() != null) {
                commandeComplete.getDetailsCommande().size(); // Force le chargement
            }
            
            logger.info("Commande grossiste créée avec succès: {}", commandeComplete.getNumeroCommande());
            return toDto(commandeComplete);
            
        } catch (java.lang.Exception e) {
            logger.error("Erreur lors de la création de la commande grossiste: {}", e.getMessage(), e);
            throw new RuntimeException("Erreur lors de la création de la commande: " + e.getMessage());
        }
    }

    /**
     * Valide une commande (uniquement pour les grossistes)
     */
    @Override
    public CommandeGrossisteDto validerCommande(String uuidCommande) {
        logger.info("Validation de la commande grossiste: {}", uuidCommande);
        
        try {
            Utilisateur utilisateur = utilisateurServiceImpl.getCurentUtilisateur();
            if (utilisateur == null || utilisateur.getBoutique() == null) {
                throw new RuntimeException("Utilisateur ou boutique non trouvé");
            }
            
            Boutique boutique = utilisateur.getBoutique();
            
            // Vérifier que la boutique est bien un grossiste
            if (boutique.getEnumTypeInstanceBoutique() != EnumTypeInstanceBoutique.GROSSISTE) {
                throw new IllegalArgumentException("Seuls les grossistes peuvent valider les commandes");
            }
            
            CommandeGrossiste commande = commandeGrossisteRepository.findById(uuidCommande)
                .orElseThrow(() -> new RuntimeException("Commande non trouvée"));
            
            // Vérifier que la commande est destinée à ce grossiste
            if (!commande.getBoutiqueGrossiste().getUuid().equals(boutique.getUuid())) {
                throw new IllegalArgumentException("Cette commande n'est pas destinée à votre boutique");
            }
            
            // Vérifier que la commande est en attente
            if (commande.getStatut() != StatusCommandeGrossisteEnum.EN_ATTENTE) {
                throw new IllegalArgumentException("Seules les commandes en attente peuvent être validées");
            }
            
            // Valider la commande
            commande.setStatut(StatusCommandeGrossisteEnum.VALIDEE);
            commande.setDateValidation(LocalDate.now());
            commande.setUtilisateurGrossiste(utilisateur);
            
            CommandeGrossiste commandeSave = commandeGrossisteRepository.save(commande);
            
            logger.info("Commande validée avec succès: {}", commandeSave.getNumeroCommande());
            return toDto(commandeSave);
            
        } catch (java.lang.Exception e) {
            logger.error("Erreur lors de la validation de la commande: {}", e.getMessage(), e);
            throw new RuntimeException("Erreur lors de la validation: " + e.getMessage());
        }
    }

    /**
     * Annule une commande
     */
    @Override
    public CommandeGrossisteDto annulerCommande(String uuidCommande) {
        logger.info("Annulation de la commande grossiste: {}", uuidCommande);
        
        try {
            Utilisateur utilisateur = utilisateurServiceImpl.getCurentUtilisateur();
            CommandeGrossiste commande = commandeGrossisteRepository.findById(uuidCommande)
                .orElseThrow(() -> new RuntimeException("Commande non trouvée"));
            
            // Vérifier que l'utilisateur a le droit d'annuler cette commande
            boolean peutAnnuler = false;
            if (commande.getBoutiqueDetaillee().getUuid().equals(utilisateur.getBoutique().getUuid()) ||
                commande.getBoutiqueGrossiste().getUuid().equals(utilisateur.getBoutique().getUuid())) {
                peutAnnuler = true;
            }
            
            if (!peutAnnuler) {
                throw new IllegalArgumentException("Vous n'avez pas le droit d'annuler cette commande");
            }
            
            // Vérifier que la commande peut être annulée
            if (commande.getStatut() == StatusCommandeGrossisteEnum.LIVREE ||
                commande.getStatut() == StatusCommandeGrossisteEnum.ANNULEE) {
                throw new IllegalArgumentException("Cette commande ne peut pas être annulée");
            }
            
            commande.setStatut(StatusCommandeGrossisteEnum.ANNULEE);
            CommandeGrossiste commandeSave = commandeGrossisteRepository.save(commande);
            
            logger.info("Commande annulée avec succès: {}", commandeSave.getNumeroCommande());
            return toDto(commandeSave);
            
        } catch (java.lang.Exception e) {
            logger.error("Erreur lors de l'annulation de la commande: {}", e.getMessage(), e);
            throw new RuntimeException("Erreur lors de l'annulation: " + e.getMessage());
        }
    }

    @Override
    public List<CommandeGrossisteDto> getCommandesDetaillee() {
        try {
            Utilisateur utilisateur = utilisateurServiceImpl.getCurentUtilisateur();
            if (utilisateur == null || utilisateur.getBoutique() == null) {
                return new ArrayList<>();
            }
            
            List<CommandeGrossiste> commandes = commandeGrossisteRepository
                .findByBoutiqueDetaillee(utilisateur.getBoutique());
            
            List<CommandeGrossisteDto> dtos = new ArrayList<>();
            for (CommandeGrossiste commande : commandes) {
                dtos.add(toDto(commande));
            }
            return dtos;
            
        } catch (java.lang.Exception e) {
            logger.error("Erreur lors de la récupération des commandes: {}", e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    @Override
    public List<CommandeGrossisteDto> getCommandesGrossiste() {
        try {
            Utilisateur utilisateur = utilisateurServiceImpl.getCurentUtilisateur();
            if (utilisateur == null || utilisateur.getBoutique() == null) {
                return new ArrayList<>();
            }
            
            List<CommandeGrossiste> commandes = commandeGrossisteRepository
                .findByBoutiqueGrossiste(utilisateur.getBoutique());
            
            List<CommandeGrossisteDto> dtos = new ArrayList<>();
            for (CommandeGrossiste commande : commandes) {
                dtos.add(toDto(commande));
            }
            return dtos;
            
        } catch (java.lang.Exception e) {
            logger.error("Erreur lors de la récupération des commandes: {}", e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    @Override
    public CommandeGrossisteDto getCommandeById(String uuid) {
        CommandeGrossiste commande = commandeGrossisteRepository.findById(uuid)
            .orElseThrow(() -> new RuntimeException("Commande non trouvée"));
        
        // Forcer le chargement des relations pour éviter les problèmes de lazy loading
        if (commande.getDetailsCommande() != null) {
            commande.getDetailsCommande().size(); // Force le chargement
        }
        if (commande.getLivraisons() != null) {
            commande.getLivraisons().size(); // Force le chargement
        }
        
        return toDto(commande);
    }

    @Override
    public PageDataDto<CommandeGrossisteDto> getCommandes(int page, int size, String key) {
        // TODO: Implémenter la recherche avec pagination
        Pageable pageable = PageRequest.of(page, size);
        // Pour l'instant, retourner toutes les commandes
        List<CommandeGrossisteDto> allCommandes = getCommandesDetaillee();
        allCommandes.addAll(getCommandesGrossiste());
        
        PageDataDto<CommandeGrossisteDto> pageData = new PageDataDto<>();
        pageData.setData(allCommandes);
        PageDto pageDto = new PageDto();
        pageDto.setPageNumber(page);
        pageDto.setSize(size);
        pageDto.setTotalElements((long) allCommandes.size());
        pageDto.setTotalPages(1);
        pageData.setPage(pageDto);
        return pageData;
    }

    @Override
    public List<CommandeGrossisteDto> getCommandesClientPartenaire(String uuidClientPartenaire) {
        logger.info("Récupération des commandes du client partenaire: {}", uuidClientPartenaire);
        
        try {
            ClientPartenaire clientPartenaire = clientPartenaireRepository.findById(uuidClientPartenaire)
                .orElseThrow(() -> new RuntimeException("Client partenaire non trouvé"));
            
            List<CommandeGrossiste> commandes = commandeGrossisteRepository.findByClientPartenaire(clientPartenaire);
            
            List<CommandeGrossisteDto> dtos = new ArrayList<>();
            for (CommandeGrossiste commande : commandes) {
                dtos.add(toDto(commande));
            }
            
            return dtos;
            
        } catch (java.lang.Exception e) {
            logger.error("Erreur lors de la récupération des commandes du client partenaire: {}", e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    /**
     * Convertit une entité CommandeGrossiste en DTO
     */
    private CommandeGrossisteDto toDto(CommandeGrossiste commande) {
        CommandeGrossisteDto dto = new CommandeGrossisteDto();
        dto.setUuid(commande.getUuid());
        dto.setNumeroCommande(commande.getNumeroCommande());
        dto.setDateCommande(commande.getDateCommande());
        dto.setMontantTotal(commande.getMontantTotal());
        dto.setStatut(commande.getStatut());
        dto.setUuidBoutiqueDetaillee(commande.getBoutiqueDetaillee().getUuid());
        dto.setUuidBoutiqueGrossiste(commande.getBoutiqueGrossiste().getUuid());
        dto.setLibelleBoutiqueDetaillee(commande.getBoutiqueDetaillee().getLibelleBoutique());
        dto.setLibelleBoutiqueGrossiste(commande.getBoutiqueGrossiste().getLibelleBoutique());
        
        if (commande.getUtilisateurDetaillee() != null) {
            dto.setUuidUtilisateurDetaillee(commande.getUtilisateurDetaillee().getUuid());
        }
        if (commande.getUtilisateurGrossiste() != null) {
            dto.setUuidUtilisateurGrossiste(commande.getUtilisateurGrossiste().getUuid());
        }
        if (commande.getDateValidation() != null) {
            dto.setDateValidation(commande.getDateValidation());
        }
        
        // Gérer le client partenaire
        if (commande.getClientPartenaire() != null) {
            dto.setUuidClientPartenaire(commande.getClientPartenaire().getUuid());
            dto.setNumeroCompteClientPartenaire(commande.getClientPartenaire().getNumeroCompte());
            if (commande.getClientPartenaire().getClient() != null) {
                dto.setNomClientPartenaire(commande.getClientPartenaire().getClient().getNom() + " " + 
                    commande.getClientPartenaire().getClient().getPrenom());
            }
            // Pays de provenance du client partenaire
            if (commande.getClientPartenaire().getPaysProvenance() != null) {
                dto.setPaysProvenanceClientPartenaire(commande.getClientPartenaire().getPaysProvenance().getLibelle());
            }
        }
        
        // Données enrichies (inspiré Odoo)
        if (commande.getLivraisons() != null) {
            dto.setNombreLivraisons(commande.getLivraisons().size());
        } else {
            dto.setNombreLivraisons(0);
        }
        
        if (commande.getDetailsCommande() != null) {
            dto.setNombreArticles(commande.getDetailsCommande().size());
        } else {
            dto.setNombreArticles(0);
        }
        
        // Vérifier si la commande peut être validée (stock suffisant)
        boolean peutEtreValidee = true;
        if (commande.getDetailsCommande() != null && commande.getStatut() == StatusCommandeGrossisteEnum.EN_ATTENTE) {
            try {
                List<Produit> produits = produitRepository.findByBoutique(commande.getBoutiqueGrossiste());
                for (DetailCommandeGrossiste detail : commande.getDetailsCommande()) {
                    Produit produit = produits.stream()
                        .filter(p -> p.getArticle() != null && p.getArticle().getUuid().equals(detail.getArticle().getUuid()))
                        .findFirst()
                        .orElse(null);
                    if (produit == null || detail.getTypeUniteDeVente() == null) {
                        peutEtreValidee = false;
                        break;
                    }
                    int stockDisponible = stockUniteVenteService.getStockForUnit(produit, detail.getTypeUniteDeVente());
                    if (stockDisponible < detail.getQuantite()) {
                        peutEtreValidee = false;
                        break;
                    }
                }
            } catch (java.lang.Exception e) {
                peutEtreValidee = false;
            }
        }
        dto.setPeutEtreValidee(peutEtreValidee);
        
        // Convertir les détails
        List<DetailCommandeGrossisteDto> detailDtos = new ArrayList<>();
        if (commande.getDetailsCommande() != null) {
            for (DetailCommandeGrossiste detail : commande.getDetailsCommande()) {
                DetailCommandeGrossisteDto detailDto = new DetailCommandeGrossisteDto();
                detailDto.setUuid(detail.getUuid());
                detailDto.setUuidArticle(detail.getArticle().getUuid());
                detailDto.setLibelleArticle(detail.getArticle().getLibelle());
                if (detail.getArticle().getCategorie() != null) {
                    detailDto.setCategorie(detail.getArticle().getCategorie().getLibelle());
                }
                detailDto.setQuantite(detail.getQuantite());
                detailDto.setPrixUnitaire(detail.getPrixUnitaire());
                detailDto.setMontantTotal(detail.getPrixUnitaire() * detail.getQuantite());
                if (detail.getTypeUniteDeVente() != null) {
                    detailDto.setUuidTypeUniteDeVente(detail.getTypeUniteDeVente().getUuid());
                    detailDto.setTypeUniteEnum(detail.getTypeUniteDeVente().getTypeUniteEnum().toString());
                    detailDto.setUnite(detail.getTypeUniteDeVente().getUnite());
                }
                detailDtos.add(detailDto);
            }
        }
        dto.setDetailsCommande(detailDtos);
        
        return dto;
    }
}
