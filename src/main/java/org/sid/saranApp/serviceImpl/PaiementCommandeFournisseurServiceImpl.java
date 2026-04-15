package org.sid.saranApp.serviceImpl;

import org.sid.saranApp.dto.PaiementCommandeFournisseurDto;
import org.sid.saranApp.model.*;
import org.sid.saranApp.repository.*;
import org.sid.saranApp.service.OperationCaisseService;
import org.sid.saranApp.service.PaiementCommandeFournisseurService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Service pour la gestion des paiements des commandes fournisseur
 */
@Service
public class PaiementCommandeFournisseurServiceImpl implements PaiementCommandeFournisseurService {

    private static final Logger logger = LoggerFactory.getLogger(PaiementCommandeFournisseurServiceImpl.class);

    @Autowired
    private PaiementCommandeFournisseurRepository paiementCommandeFournisseurRepository;
    
    @Autowired
    private CommandeFournisseurRepository commandeFournisseurRepository;
    
    @Autowired
    private ModePaiementRepository modePaiementRepository;
    
    @Autowired
    private StoredFileRepository storedFileRepository;
    
    @Autowired
    private UtilisateurServiceImpl utilisateurServiceImpl;
    
    @Autowired
    private OperationCaisseService operationCaisseService;

    @Override
    public PaiementCommandeFournisseurDto enregistrerPaiement(PaiementCommandeFournisseurDto paiementDto) {
        logger.info("Enregistrement d'un paiement pour commande fournisseur");
        
        try {
            Utilisateur utilisateur = utilisateurServiceImpl.getCurentUtilisateur();
            if (utilisateur == null || utilisateur.getBoutique() == null) {
                throw new RuntimeException("Utilisateur ou boutique non trouvé");
            }
            
            CommandeFournisseur commande = commandeFournisseurRepository.findById(paiementDto.getUuidCommandeFournisseur())
                .orElseThrow(() -> new RuntimeException("Commande fournisseur non trouvée"));
            
            ModePaiement modePaiement = modePaiementRepository.findById(paiementDto.getUuidModePaiement())
                .orElseThrow(() -> new RuntimeException("Mode de paiement non trouvé"));
            
            PaiementCommandeFournisseur paiement = new PaiementCommandeFournisseur();
            paiement.setUuid(UUID.randomUUID().toString().replace("-", "").toUpperCase());
            paiement.setCommandeFournisseur(commande);
            paiement.setMontantVerse(paiementDto.getMontantVerse());
            paiement.setDatePaiement(paiementDto.getDatePaiement() != null ? paiementDto.getDatePaiement() : new Date());
            paiement.setModePaiement(modePaiement);
            paiement.setNumeroReference(paiementDto.getNumeroReference());
            paiement.setCommentaire(paiementDto.getCommentaire());
            paiement.setBoutique(utilisateur.getBoutique());
            paiement.setUtilisateur(utilisateur);
            
            if (paiementDto.getUuidRecuPaiement() != null && !paiementDto.getUuidRecuPaiement().isEmpty()) {
                StoredFile recu = storedFileRepository.findById(paiementDto.getUuidRecuPaiement())
                    .orElse(null);
                paiement.setRecuPaiement(recu);
            }
            
            PaiementCommandeFournisseur paiementSave = paiementCommandeFournisseurRepository.save(paiement);
            
            // Mettre à jour le statut de paiement de la commande si nécessaire
            double montantTotalPaye = calculerMontantTotalPaye(commande);
            double montantTotalCommande = calculerMontantTotalCommande(commande);
            
            if (montantTotalPaye >= montantTotalCommande) {
                commande.setPaie(true);
                commandeFournisseurRepository.save(commande);
            }
            
            // Créer automatiquement une opération de caisse
            try {
                operationCaisseService.enregistrerOperationPaiementFournisseur(paiementSave.getUuid());
            } catch (java.lang.Exception e) {
                logger.warn("Erreur lors de la création de l'opération de caisse: {}", e.getMessage());
                // Ne pas bloquer le paiement si l'opération de caisse échoue
            }
            
            logger.info("Paiement enregistré avec succès: {}", paiementSave.getUuid());
            return toDto(paiementSave);
            
        } catch (java.lang.Exception e) {
            logger.error("Erreur lors de l'enregistrement du paiement: {}", e.getMessage(), e);
            throw new RuntimeException("Erreur lors de l'enregistrement du paiement: " + e.getMessage());
        }
    }

    @Override
    public List<PaiementCommandeFournisseurDto> getPaiementsByCommande(String uuidCommande) {
        try {
            CommandeFournisseur commande = commandeFournisseurRepository.findById(uuidCommande)
                .orElseThrow(() -> new RuntimeException("Commande non trouvée"));
            
            List<PaiementCommandeFournisseur> paiements = paiementCommandeFournisseurRepository
                .findByCommandeFournisseur(commande);
            
            List<PaiementCommandeFournisseurDto> dtos = new ArrayList<>();
            for (PaiementCommandeFournisseur paiement : paiements) {
                dtos.add(toDto(paiement));
            }
            return dtos;
            
        } catch (java.lang.Exception e) {
            logger.error("Erreur lors de la récupération des paiements: {}", e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    @Override
    public List<PaiementCommandeFournisseurDto> getPaiementsByFournisseur(String uuidFournisseur) {
        try {
            Fournisseur fournisseur = fournisseurRepository.findById(uuidFournisseur)
                .orElseThrow(() -> new RuntimeException("Fournisseur non trouvé"));
            
            List<PaiementCommandeFournisseur> paiements = paiementCommandeFournisseurRepository
                .findByCommandeFournisseur_Fournisseur(fournisseur);
            
            List<PaiementCommandeFournisseurDto> dtos = new ArrayList<>();
            for (PaiementCommandeFournisseur paiement : paiements) {
                dtos.add(toDto(paiement));
            }
            return dtos;
            
        } catch (java.lang.Exception e) {
            logger.error("Erreur lors de la récupération des paiements: {}", e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    @Override
    public PaiementCommandeFournisseurDto getPaiementById(String uuid) {
        PaiementCommandeFournisseur paiement = paiementCommandeFournisseurRepository.findById(uuid)
            .orElseThrow(() -> new RuntimeException("Paiement non trouvé"));
        return toDto(paiement);
    }

    /**
     * Calcule le montant total payé pour une commande
     */
    private double calculerMontantTotalPaye(CommandeFournisseur commande) {
        List<PaiementCommandeFournisseur> paiements = paiementCommandeFournisseurRepository
            .findByCommandeFournisseur(commande);
        return paiements.stream()
            .mapToDouble(PaiementCommandeFournisseur::getMontantVerse)
            .sum();
    }

    /**
     * Calcule le montant total d'une commande à partir de ses détails
     */
    private double calculerMontantTotalCommande(CommandeFournisseur commande) {
        if (commande.getMontantTotal() > 0) {
            return commande.getMontantTotal();
        }
        // Sinon, calculer à partir des détails
        return commande.getListeDetailCommandeFournisseur().stream()
            .mapToDouble(d -> d.getPrixAchat() * d.getQuantite())
            .sum();
    }

    /**
     * Convertit une entité en DTO
     */
    private PaiementCommandeFournisseurDto toDto(PaiementCommandeFournisseur paiement) {
        PaiementCommandeFournisseurDto dto = new PaiementCommandeFournisseurDto();
        dto.setUuid(paiement.getUuid());
        dto.setUuidCommandeFournisseur(paiement.getCommandeFournisseur().getUuid());
        dto.setMontantVerse(paiement.getMontantVerse());
        dto.setDatePaiement(paiement.getDatePaiement());
        dto.setNumeroReference(paiement.getNumeroReference());
        dto.setCommentaire(paiement.getCommentaire());
        dto.setRefCommande(paiement.getCommandeFournisseur().getRefCommande());
        
        if (paiement.getModePaiement() != null) {
            dto.setUuidModePaiement(paiement.getModePaiement().getUuid());
            dto.setLibelleModePaiement(paiement.getModePaiement().getLibelle());
        }
        
        if (paiement.getRecuPaiement() != null) {
            dto.setUuidRecuPaiement(paiement.getRecuPaiement().getUuid());
        }
        
        return dto;
    }
    
    @Autowired
    private FournisseurRepository fournisseurRepository;
}
