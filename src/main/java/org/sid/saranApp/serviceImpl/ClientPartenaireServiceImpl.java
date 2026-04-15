package org.sid.saranApp.serviceImpl;

import org.sid.saranApp.dto.ClientPartenaireDto;
import org.sid.saranApp.enume.EnumTypeInstanceBoutique;
import org.sid.saranApp.exception.Exception;
import org.sid.saranApp.model.*;
import org.sid.saranApp.repository.*;
import org.sid.saranApp.service.ClientPartenaireService;
import org.sid.saranApp.model.Pays;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service pour la gestion des partenariats client-boutique
 */
@Service
public class ClientPartenaireServiceImpl implements ClientPartenaireService {

    private static final Logger logger = LoggerFactory.getLogger(ClientPartenaireServiceImpl.class);

    @Autowired
    private ClientPartenaireRepository clientPartenaireRepository;
    
    @Autowired
    private ClientRepository clientRepository;
    
    @Autowired
    private BoutiqueRepository boutiqueRepository;
    
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    
    @Autowired
    private UtilisateurServiceImpl utilisateurServiceImpl;
    
    @Autowired
    private PaysRepository paysRepository;

    @Override
    public ClientPartenaireDto creerPartenaire(ClientPartenaireDto clientPartenaireDto) {
        logger.info("Création d'un nouveau partenariat client-boutique");
        
        try {
            Utilisateur utilisateur = utilisateurServiceImpl.getCurentUtilisateur();
            if (utilisateur == null || utilisateur.getBoutique() == null) {
                throw new RuntimeException("Utilisateur ou boutique non trouvé");
            }
            
            Boutique boutique = utilisateur.getBoutique();
            
            // Vérifier que la boutique est bien un grossiste
            if (boutique.getEnumTypeInstanceBoutique() != EnumTypeInstanceBoutique.GROSSISTE) {
                throw new IllegalArgumentException("Seuls les grossistes peuvent créer des partenariats avec des clients");
            }
            
            // Récupérer le client
            Client client = clientRepository.findById(clientPartenaireDto.getUuidClient())
                .orElseThrow(() -> new RuntimeException("Client non trouvé"));
            
            // Vérifier si un partenariat existe déjà
            Optional<ClientPartenaire> partenaireExistant = clientPartenaireRepository
                .findByClientAndBoutique(client, boutique);
            
            if (partenaireExistant.isPresent()) {
                throw new IllegalArgumentException("Un partenariat existe déjà entre ce client et cette boutique");
            }
            
            // Créer le partenariat
            ClientPartenaire partenaire = new ClientPartenaire();
            partenaire.setUuid(UUID.randomUUID().toString().replace("-", "").toUpperCase());
            partenaire.setClient(client);
            partenaire.setBoutique(boutique);
            partenaire.setDateCreation(new Date());
            partenaire.setDateExpiration(clientPartenaireDto.getDateExpiration());
            partenaire.setStatut("ACTIF");
            partenaire.setNumeroCompte("CPT-" + System.currentTimeMillis());
            partenaire.setConditionsSpeciales(clientPartenaireDto.getConditionsSpeciales());
            partenaire.setLimiteCredit(clientPartenaireDto.getLimiteCredit());
            partenaire.setUtilisateur(utilisateur);
            
            // Gérer le pays de provenance
            if (clientPartenaireDto.getUuidPaysProvenance() != null && !clientPartenaireDto.getUuidPaysProvenance().isEmpty()) {
                Pays pays = paysRepository.findById(clientPartenaireDto.getUuidPaysProvenance())
                    .orElseThrow(() -> new RuntimeException("Pays de provenance non trouvé"));
                partenaire.setPaysProvenance(pays);
            }
            
            ClientPartenaire partenaireSave = clientPartenaireRepository.save(partenaire);
            
            // Mettre à jour le client pour indiquer qu'il a un compte
            client.setAUnCompte(true);
            clientRepository.save(client);
            
            logger.info("Partenariat créé avec succès: {}", partenaireSave.getNumeroCompte());
            return toDto(partenaireSave);
            
        } catch (java.lang.Exception e) {
            logger.error("Erreur lors de la création du partenariat: {}", e.getMessage(), e);
            throw new RuntimeException("Erreur lors de la création du partenariat: " + e.getMessage());
        }
    }

    @Override
    public ClientPartenaireDto mettreAJourStatut(String uuidPartenaire, String nouveauStatut) {
        logger.info("Mise à jour du statut du partenariat: {}", uuidPartenaire);
        
        try {
            ClientPartenaire partenaire = clientPartenaireRepository.findById(uuidPartenaire)
                .orElseThrow(() -> new RuntimeException("Partenariat non trouvé"));
            
            partenaire.setStatut(nouveauStatut);
            ClientPartenaire partenaireSave = clientPartenaireRepository.save(partenaire);
            
            logger.info("Statut du partenariat mis à jour: {}", nouveauStatut);
            return toDto(partenaireSave);
            
        } catch (java.lang.Exception e) {
            logger.error("Erreur lors de la mise à jour du statut: {}", e.getMessage(), e);
            throw new RuntimeException("Erreur lors de la mise à jour: " + e.getMessage());
        }
    }

    @Override
    public boolean estClientPartenaire(String uuidClient, String uuidBoutique) {
        try {
            Client client = clientRepository.findById(uuidClient)
                .orElse(null);
            Boutique boutique = boutiqueRepository.findById(uuidBoutique)
                .orElse(null);
            
            if (client == null || boutique == null) {
                return false;
            }
            
            Optional<ClientPartenaire> partenaire = clientPartenaireRepository
                .findByClientAndBoutique(client, boutique);
            
            return partenaire.isPresent() && "ACTIF".equals(partenaire.get().getStatut());
            
        } catch (java.lang.Exception e) {
            logger.error("Erreur lors de la vérification du partenariat: {}", e.getMessage(), e);
            return false;
        }
    }

    @Override
    public List<ClientPartenaireDto> getPartenariatsByClient(String uuidClient) {
        try {
            Client client = clientRepository.findById(uuidClient)
                .orElseThrow(() -> new RuntimeException("Client non trouvé"));
            
            List<ClientPartenaire> partenariats = clientPartenaireRepository.findByClient(client);
            List<ClientPartenaireDto> dtos = new ArrayList<>();
            
            for (ClientPartenaire partenaire : partenariats) {
                dtos.add(toDto(partenaire));
            }
            
            return dtos;
            
        } catch (java.lang.Exception e) {
            logger.error("Erreur lors de la récupération des partenariats: {}", e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    @Override
    public List<ClientPartenaireDto> getPartenariatsByBoutique(String uuidBoutique) {
        try {
            Boutique boutique = boutiqueRepository.findById(uuidBoutique)
                .orElseThrow(() -> new RuntimeException("Boutique non trouvée"));
            
            List<ClientPartenaire> partenariats = clientPartenaireRepository.findByBoutique(boutique);
            List<ClientPartenaireDto> dtos = new ArrayList<>();
            
            for (ClientPartenaire partenaire : partenariats) {
                dtos.add(toDto(partenaire));
            }
            
            return dtos;
            
        } catch (java.lang.Exception e) {
            logger.error("Erreur lors de la récupération des partenariats: {}", e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    @Override
    public ClientPartenaireDto getPartenaireById(String uuid) {
        ClientPartenaire partenaire = clientPartenaireRepository.findById(uuid)
            .orElseThrow(() -> new RuntimeException("Partenariat non trouvé"));
        return toDto(partenaire);
    }

    /**
     * Convertit une entité ClientPartenaire en DTO
     */
    private ClientPartenaireDto toDto(ClientPartenaire partenaire) {
        ClientPartenaireDto dto = new ClientPartenaireDto();
        dto.setUuid(partenaire.getUuid());
        dto.setUuidClient(partenaire.getClient().getUuid());
        dto.setUuidBoutique(partenaire.getBoutique().getUuid());
        dto.setNomClient(partenaire.getClient().getNom());
        dto.setPrenomClient(partenaire.getClient().getPrenom());
        dto.setEmailClient(partenaire.getClient().getEmail());
        dto.setLibelleBoutique(partenaire.getBoutique().getLibelleBoutique());
        dto.setDateCreation(partenaire.getDateCreation());
        dto.setDateExpiration(partenaire.getDateExpiration());
        dto.setStatut(partenaire.getStatut());
        dto.setNumeroCompte(partenaire.getNumeroCompte());
        dto.setConditionsSpeciales(partenaire.getConditionsSpeciales());
        dto.setLimiteCredit(partenaire.getLimiteCredit());
        
        if (partenaire.getUtilisateur() != null) {
            dto.setUuidUtilisateur(partenaire.getUtilisateur().getUuid());
        }
        
        // Gérer le pays de provenance
        if (partenaire.getPaysProvenance() != null) {
            dto.setUuidPaysProvenance(partenaire.getPaysProvenance().getUuid());
            dto.setLibellePaysProvenance(partenaire.getPaysProvenance().getLibelle());
            dto.setCountryCodePaysProvenance(partenaire.getPaysProvenance().getCountryCode());
        }
        
        return dto;
    }
}
