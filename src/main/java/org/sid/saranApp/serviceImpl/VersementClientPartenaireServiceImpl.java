package org.sid.saranApp.serviceImpl;

import org.sid.saranApp.dto.VersementClientPartenaireDto;
import org.sid.saranApp.model.*;
import org.sid.saranApp.repository.*;
import org.sid.saranApp.service.OperationCaisseService;
import org.sid.saranApp.service.VersementClientPartenaireService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Service pour la gestion des versements des clients partenaires
 */
@Service
public class VersementClientPartenaireServiceImpl implements VersementClientPartenaireService {

    private static final Logger logger = LoggerFactory.getLogger(VersementClientPartenaireServiceImpl.class);

    @Autowired
    private VersementClientPartenaireRepository versementClientPartenaireRepository;
    
    @Autowired
    private ClientPartenaireRepository clientPartenaireRepository;
    
    @Autowired
    private CommandeVenteRepository commandeVenteRepository;
    
    @Autowired
    private ModePaiementRepository modePaiementRepository;
    
    @Autowired
    private StoredFileRepository storedFileRepository;
    
    @Autowired
    private UtilisateurServiceImpl utilisateurServiceImpl;
    
    @Autowired
    private OperationCaisseService operationCaisseService;

    @Override
    public VersementClientPartenaireDto enregistrerVersement(VersementClientPartenaireDto versementDto) {
        logger.info("Enregistrement d'un versement client partenaire");
        
        try {
            Utilisateur utilisateur = utilisateurServiceImpl.getCurentUtilisateur();
            if (utilisateur == null || utilisateur.getBoutique() == null) {
                throw new RuntimeException("Utilisateur ou boutique non trouvé");
            }
            
            ClientPartenaire clientPartenaire = clientPartenaireRepository.findById(versementDto.getUuidClientPartenaire())
                .orElseThrow(() -> new RuntimeException("Client partenaire non trouvé"));
            
            ModePaiement modePaiement = modePaiementRepository.findById(versementDto.getUuidModePaiement())
                .orElseThrow(() -> new RuntimeException("Mode de paiement non trouvé"));
            
            VersementClientPartenaire versement = new VersementClientPartenaire();
            versement.setUuid(UUID.randomUUID().toString().replace("-", "").toUpperCase());
            versement.setClientPartenaire(clientPartenaire);
            versement.setMontantVerse(versementDto.getMontantVerse());
            versement.setDateVersement(versementDto.getDateVersement() != null ? versementDto.getDateVersement() : new Date());
            versement.setModePaiement(modePaiement);
            versement.setNumeroReference(versementDto.getNumeroReference());
            versement.setCommentaire(versementDto.getCommentaire());
            versement.setBoutique(utilisateur.getBoutique());
            versement.setUtilisateur(utilisateur);
            
            if (versementDto.getUuidCommandeVente() != null && !versementDto.getUuidCommandeVente().isEmpty()) {
                CommandeVente commandeVente = commandeVenteRepository.findById(versementDto.getUuidCommandeVente())
                    .orElse(null);
                versement.setCommandeVente(commandeVente);
            }
            
            if (versementDto.getUuidRecuVersement() != null && !versementDto.getUuidRecuVersement().isEmpty()) {
                StoredFile recu = storedFileRepository.findById(versementDto.getUuidRecuVersement())
                    .orElse(null);
                versement.setRecuVersement(recu);
            }
            
            VersementClientPartenaire versementSave = versementClientPartenaireRepository.save(versement);
            
            // Créer automatiquement une opération de caisse
            try {
                operationCaisseService.enregistrerOperationVersementClient(versementSave.getUuid());
            } catch (java.lang.Exception e) {
                logger.warn("Erreur lors de la création de l'opération de caisse: {}", e.getMessage());
                // Ne pas bloquer le versement si l'opération de caisse échoue
            }
            
            logger.info("Versement enregistré avec succès: {}", versementSave.getUuid());
            return toDto(versementSave);
            
        } catch (java.lang.Exception e) {
            logger.error("Erreur lors de l'enregistrement du versement: {}", e.getMessage(), e);
            throw new RuntimeException("Erreur lors de l'enregistrement du versement: " + e.getMessage());
        }
    }

    @Override
    public List<VersementClientPartenaireDto> getVersementsByClientPartenaire(String uuidClientPartenaire) {
        try {
            ClientPartenaire clientPartenaire = clientPartenaireRepository.findById(uuidClientPartenaire)
                .orElseThrow(() -> new RuntimeException("Client partenaire non trouvé"));
            
            List<VersementClientPartenaire> versements = versementClientPartenaireRepository
                .findByClientPartenaire(clientPartenaire);
            
            List<VersementClientPartenaireDto> dtos = new ArrayList<>();
            for (VersementClientPartenaire versement : versements) {
                dtos.add(toDto(versement));
            }
            return dtos;
            
        } catch (java.lang.Exception e) {
            logger.error("Erreur lors de la récupération des versements: {}", e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    @Override
    public List<VersementClientPartenaireDto> getVersementsByCommande(String uuidCommande) {
        try {
            CommandeVente commandeVente = commandeVenteRepository.findById(uuidCommande)
                .orElseThrow(() -> new RuntimeException("Commande non trouvée"));
            
            List<VersementClientPartenaire> versements = versementClientPartenaireRepository
                .findByCommandeVente(commandeVente);
            
            List<VersementClientPartenaireDto> dtos = new ArrayList<>();
            for (VersementClientPartenaire versement : versements) {
                dtos.add(toDto(versement));
            }
            return dtos;
            
        } catch (java.lang.Exception e) {
            logger.error("Erreur lors de la récupération des versements: {}", e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    @Override
    public VersementClientPartenaireDto getVersementById(String uuid) {
        VersementClientPartenaire versement = versementClientPartenaireRepository.findById(uuid)
            .orElseThrow(() -> new RuntimeException("Versement non trouvé"));
        return toDto(versement);
    }

    /**
     * Convertit une entité en DTO
     */
    private VersementClientPartenaireDto toDto(VersementClientPartenaire versement) {
        VersementClientPartenaireDto dto = new VersementClientPartenaireDto();
        dto.setUuid(versement.getUuid());
        dto.setUuidClientPartenaire(versement.getClientPartenaire().getUuid());
        dto.setMontantVerse(versement.getMontantVerse());
        dto.setDateVersement(versement.getDateVersement());
        dto.setNumeroReference(versement.getNumeroReference());
        dto.setCommentaire(versement.getCommentaire());
        dto.setNomClient(versement.getClientPartenaire().getClient().getNom());
        dto.setNumeroCompte(versement.getClientPartenaire().getNumeroCompte());
        
        if (versement.getCommandeVente() != null) {
            dto.setUuidCommandeVente(versement.getCommandeVente().getUuid());
        }
        
        if (versement.getModePaiement() != null) {
            dto.setUuidModePaiement(versement.getModePaiement().getUuid());
            dto.setLibelleModePaiement(versement.getModePaiement().getLibelle());
        }
        
        if (versement.getRecuVersement() != null) {
            dto.setUuidRecuVersement(versement.getRecuVersement().getUuid());
        }
        
        return dto;
    }
}
