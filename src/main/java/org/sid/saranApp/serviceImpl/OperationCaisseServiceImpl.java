package org.sid.saranApp.serviceImpl;

import org.sid.saranApp.dto.OperationCaisseDto;
import org.sid.saranApp.dto.SituationCaisseDto;
import org.sid.saranApp.dto.SituationClientPartenaireDto;
import org.sid.saranApp.dto.SituationFournisseurDto;
import org.sid.saranApp.enume.EnumTypeTransaction;
import org.sid.saranApp.model.*;
import org.sid.saranApp.repository.*;
import org.sid.saranApp.service.CaisseJournaliereService;
import org.sid.saranApp.service.OperationCaisseService;
import org.sid.saranApp.service.SituationComptableService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service pour la gestion des opérations de caisse
 */
@Service
public class OperationCaisseServiceImpl implements OperationCaisseService {

    private static final Logger logger = LoggerFactory.getLogger(OperationCaisseServiceImpl.class);

    @Autowired
    private OperationCaisseRepository operationCaisseRepository;
    
    @Autowired
    private CaisseJournaliereRepository caisseJournaliereRepository;
    
    @Autowired
    private CaisseJournaliereService caisseJournaliereService;
    
    @Autowired
    private PaiementCommandeFournisseurRepository paiementCommandeFournisseurRepository;
    
    @Autowired
    private VersementClientPartenaireRepository versementClientPartenaireRepository;
    
    @Autowired
    private CommandeVenteRepository commandeVenteRepository;
    
    @Autowired
    private CommandeFournisseurRepository commandeFournisseurRepository;
    
    @Autowired
    private ClientPartenaireRepository clientPartenaireRepository;
    
    @Autowired
    private FournisseurRepository fournisseurRepository;
    
    @Autowired
    private UtilisateurServiceImpl utilisateurServiceImpl;
    
    @Autowired
    private SituationComptableService situationComptableService;

    @Override
    @Transactional
    public OperationCaisseDto enregistrerOperation(OperationCaisseDto operationDto) {
        logger.info("Enregistrement d'une opération de caisse: {}", operationDto.getTypeOperation());
        
        try {
            Utilisateur utilisateur = utilisateurServiceImpl.getCurentUtilisateur();
            if (utilisateur == null || utilisateur.getBoutique() == null) {
                throw new RuntimeException("Utilisateur ou boutique non trouvé");
            }
            
            // Récupérer la caisse actuelle
            CaisseJournaliere caisse = caisseJournaliereRepository
                .findByStatutCaisseAndBoutique(
                    org.sid.saranApp.enume.EnumStatutCaisse.OUVERT,
                    utilisateur.getBoutique()
                )
                .orElseThrow(() -> new RuntimeException("Aucune caisse ouverte"));
            
            OperationCaisse operation = new OperationCaisse();
            operation.setUuid(UUID.randomUUID().toString().replace("-", "").toUpperCase());
            operation.setTypeOperation(operationDto.getTypeOperation());
            operation.setMontant(operationDto.getMontant());
            operation.setDateOperation(operationDto.getDateOperation() != null ? operationDto.getDateOperation() : new Date());
            operation.setLibelle(operationDto.getLibelle());
            operation.setTypeDetail(operationDto.getTypeDetail());
            operation.setCaisseJournaliere(caisse);
            operation.setBoutique(utilisateur.getBoutique());
            operation.setUtilisateur(utilisateur);
            operation.setCommentaire(operationDto.getCommentaire());
            
            // Lier les entités si fournies
            if (operationDto.getUuidPaiementCommandeFournisseur() != null) {
                PaiementCommandeFournisseur paiement = paiementCommandeFournisseurRepository
                    .findById(operationDto.getUuidPaiementCommandeFournisseur())
                    .orElse(null);
                if (paiement != null) {
                    operation.setPaiementCommandeFournisseur(paiement);
                    operation.setCommandeFournisseur(paiement.getCommandeFournisseur());
                    operation.setFournisseur(paiement.getCommandeFournisseur().getFournisseur());
                }
            }
            
            if (operationDto.getUuidVersementClientPartenaire() != null) {
                VersementClientPartenaire versement = versementClientPartenaireRepository
                    .findById(operationDto.getUuidVersementClientPartenaire())
                    .orElse(null);
                if (versement != null) {
                    operation.setVersementClientPartenaire(versement);
                    operation.setClientPartenaire(versement.getClientPartenaire());
                    if (versement.getCommandeVente() != null) {
                        operation.setCommandeVente(versement.getCommandeVente());
                    }
                }
            }
            
            if (operationDto.getUuidCommandeVente() != null) {
                CommandeVente commandeVente = commandeVenteRepository
                    .findById(operationDto.getUuidCommandeVente())
                    .orElse(null);
                if (commandeVente != null) {
                    operation.setCommandeVente(commandeVente);
                    if (commandeVente.getClient() != null) {
                        // Trouver le client partenaire si c'est une commande en gros
                        if (commandeVente.getTypeCommande() == org.sid.saranApp.enume.EnumTypeCommande.EN_GROS) {
                            ClientPartenaire clientPartenaire = clientPartenaireRepository
                                .findByClientAndBoutique(commandeVente.getClient(), utilisateur.getBoutique())
                                .orElse(null);
                            operation.setClientPartenaire(clientPartenaire);
                        }
                    }
                }
            }
            
            OperationCaisse operationSave = operationCaisseRepository.save(operation);
            
            // Mettre à jour la caisse journalière
            mettreAJourCaisse(operation);
            
            logger.info("Opération de caisse enregistrée: {}", operationSave.getUuid());
            return toDto(operationSave);
            
        } catch (java.lang.Exception e) {
            logger.error("Erreur lors de l'enregistrement de l'opération: {}", e.getMessage(), e);
            throw new RuntimeException("Erreur lors de l'enregistrement: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public OperationCaisseDto enregistrerOperationPaiementFournisseur(String uuidPaiement) {
        logger.info("Enregistrement automatique d'une opération pour paiement fournisseur: {}", uuidPaiement);
        
        try {
            PaiementCommandeFournisseur paiement = paiementCommandeFournisseurRepository.findById(uuidPaiement)
                .orElseThrow(() -> new RuntimeException("Paiement non trouvé"));
            
            OperationCaisseDto operationDto = new OperationCaisseDto();
            operationDto.setTypeOperation(EnumTypeTransaction.DECAISSEMENT);
            operationDto.setMontant(BigDecimal.valueOf(paiement.getMontantVerse()));
            operationDto.setLibelle("Paiement fournisseur - " + paiement.getCommandeFournisseur().getRefCommande());
            operationDto.setTypeDetail("PAIEMENT_FOURNISSEUR");
            operationDto.setUuidPaiementCommandeFournisseur(uuidPaiement);
            operationDto.setCommentaire("Paiement automatique pour commande " + paiement.getCommandeFournisseur().getRefCommande());
            
            return enregistrerOperation(operationDto);
            
        } catch (java.lang.Exception e) {
            logger.error("Erreur lors de l'enregistrement automatique: {}", e.getMessage(), e);
            throw new RuntimeException("Erreur lors de l'enregistrement automatique: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public OperationCaisseDto enregistrerOperationVersementClient(String uuidVersement) {
        logger.info("Enregistrement automatique d'une opération pour versement client: {}", uuidVersement);
        
        try {
            VersementClientPartenaire versement = versementClientPartenaireRepository.findById(uuidVersement)
                .orElseThrow(() -> new RuntimeException("Versement non trouvé"));
            
            OperationCaisseDto operationDto = new OperationCaisseDto();
            operationDto.setTypeOperation(EnumTypeTransaction.ENCAISSEMENT);
            operationDto.setMontant(BigDecimal.valueOf(versement.getMontantVerse()));
            operationDto.setLibelle("Versement client partenaire - " + versement.getClientPartenaire().getClient().getNom());
            operationDto.setTypeDetail("VERSEMENT_CLIENT_PARTENAIRE");
            operationDto.setUuidVersementClientPartenaire(uuidVersement);
            operationDto.setCommentaire("Versement automatique du client " + versement.getClientPartenaire().getClient().getNom());
            
            return enregistrerOperation(operationDto);
            
        } catch (java.lang.Exception e) {
            logger.error("Erreur lors de l'enregistrement automatique: {}", e.getMessage(), e);
            throw new RuntimeException("Erreur lors de l'enregistrement automatique: " + e.getMessage());
        }
    }

    @Override
    public List<OperationCaisseDto> getOperationsCaisseActuelle() {
        try {
            Utilisateur utilisateur = utilisateurServiceImpl.getCurentUtilisateur();
            Boutique boutique = utilisateur.getBoutique();
            
            CaisseJournaliere caisse = caisseJournaliereRepository
                .findByStatutCaisseAndBoutique(
                    org.sid.saranApp.enume.EnumStatutCaisse.OUVERT,
                    boutique
                )
                .orElse(null);
            
            if (caisse == null) {
                return new ArrayList<>();
            }
            
            List<OperationCaisse> operations = operationCaisseRepository.findByCaisseJournaliere(caisse);
            return operations.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
            
        } catch (java.lang.Exception e) {
            logger.error("Erreur lors de la récupération des opérations: {}", e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    @Override
    public List<OperationCaisseDto> getOperationsByCaisse(String uuidCaisse) {
        try {
            CaisseJournaliere caisse = caisseJournaliereRepository.findById(uuidCaisse)
                .orElseThrow(() -> new RuntimeException("Caisse non trouvée"));
            
            List<OperationCaisse> operations = operationCaisseRepository.findByCaisseJournaliere(caisse);
            return operations.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
            
        } catch (java.lang.Exception e) {
            logger.error("Erreur lors de la récupération des opérations: {}", e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    @Override
    public List<OperationCaisseDto> getOperationsByClientPartenaire(String uuidClientPartenaire) {
        try {
            ClientPartenaire clientPartenaire = clientPartenaireRepository.findById(uuidClientPartenaire)
                .orElseThrow(() -> new RuntimeException("Client partenaire non trouvé"));
            
            List<OperationCaisse> operations = operationCaisseRepository.findByClientPartenaire(clientPartenaire);
            return operations.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
            
        } catch (java.lang.Exception e) {
            logger.error("Erreur lors de la récupération des opérations: {}", e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    @Override
    public List<OperationCaisseDto> getOperationsByFournisseur(String uuidFournisseur) {
        try {
            Fournisseur fournisseur = fournisseurRepository.findById(uuidFournisseur)
                .orElseThrow(() -> new RuntimeException("Fournisseur non trouvé"));
            
            List<OperationCaisse> operations = operationCaisseRepository.findByFournisseur(fournisseur);
            return operations.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
            
        } catch (java.lang.Exception e) {
            logger.error("Erreur lors de la récupération des opérations: {}", e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    @Override
    public SituationCaisseDto getSituationCaisse() {
        logger.info("Calcul de la situation de caisse");
        
        try {
            Utilisateur utilisateur = utilisateurServiceImpl.getCurentUtilisateur();
            Boutique boutique = utilisateur.getBoutique();
            
            CaisseJournaliere caisse = caisseJournaliereRepository
                .findByStatutCaisseAndBoutique(
                    org.sid.saranApp.enume.EnumStatutCaisse.OUVERT,
                    boutique
                )
                .orElseThrow(() -> new RuntimeException("Aucune caisse ouverte"));
            
            List<OperationCaisse> operations = operationCaisseRepository.findByCaisseJournaliere(caisse);
            
            SituationCaisseDto situation = new SituationCaisseDto();
            situation.setSoldeOuverture(caisse.getSoldeOuverture());
            
            BigDecimal totalEncaissements = operations.stream()
                .filter(op -> op.getTypeOperation() == EnumTypeTransaction.ENCAISSEMENT)
                .map(OperationCaisse::getMontant)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
            situation.setTotalEncaissements(totalEncaissements);
            
            BigDecimal totalDecaissements = operations.stream()
                .filter(op -> op.getTypeOperation() == EnumTypeTransaction.DECAISSEMENT)
                .map(OperationCaisse::getMontant)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
            situation.setTotalDecaissements(totalDecaissements);
            
            BigDecimal soldeActuel = caisse.getSoldeOuverture()
                .add(totalEncaissements)
                .subtract(totalDecaissements);
            situation.setSoldeActuel(soldeActuel);
            
            situation.setEncaissements(operations.stream()
                .filter(op -> op.getTypeOperation() == EnumTypeTransaction.ENCAISSEMENT)
                .map(this::toDto)
                .collect(Collectors.toList()));
            
            situation.setDecaissements(operations.stream()
                .filter(op -> op.getTypeOperation() == EnumTypeTransaction.DECAISSEMENT)
                .map(this::toDto)
                .collect(Collectors.toList()));
            
            situation.setToutesOperations(operations.stream()
                .map(this::toDto)
                .collect(Collectors.toList()));
            
            situation.setNombreOperations(operations.size());
            
            return situation;
            
        } catch (java.lang.Exception e) {
            logger.error("Erreur lors du calcul de la situation de caisse: {}", e.getMessage(), e);
            throw new RuntimeException("Erreur lors du calcul: " + e.getMessage());
        }
    }

    @Override
    public SituationCaisseDto getSituationCaisseClientPartenaire(String uuidClientPartenaire) {
        logger.info("Calcul de la situation de caisse pour client partenaire: {}", uuidClientPartenaire);
        
        try {
            ClientPartenaire clientPartenaire = clientPartenaireRepository.findById(uuidClientPartenaire)
                .orElseThrow(() -> new RuntimeException("Client partenaire non trouvé"));
            
            // Récupérer la situation comptable du client
            SituationClientPartenaireDto situationComptable = situationComptableService
                .getSituationClientPartenaire(uuidClientPartenaire);
            
            // Récupérer les opérations de caisse liées à ce client
            List<OperationCaisse> operations = operationCaisseRepository.findByClientPartenaire(clientPartenaire);
            
            SituationCaisseDto situation = new SituationCaisseDto();
            
            BigDecimal totalEncaissements = operations.stream()
                .filter(op -> op.getTypeOperation() == EnumTypeTransaction.ENCAISSEMENT)
                .map(OperationCaisse::getMontant)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
            situation.setTotalEncaissements(totalEncaissements);
            
            BigDecimal totalDecaissements = operations.stream()
                .filter(op -> op.getTypeOperation() == EnumTypeTransaction.DECAISSEMENT)
                .map(OperationCaisse::getMontant)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
            situation.setTotalDecaissements(totalDecaissements);
            
            situation.setCreanceRestante(BigDecimal.valueOf(situationComptable.getCreanceRestante()));
            situation.setSoldeActuel(totalEncaissements.subtract(totalDecaissements));
            
            situation.setEncaissements(operations.stream()
                .filter(op -> op.getTypeOperation() == EnumTypeTransaction.ENCAISSEMENT)
                .map(this::toDto)
                .collect(Collectors.toList()));
            
            situation.setDecaissements(operations.stream()
                .filter(op -> op.getTypeOperation() == EnumTypeTransaction.DECAISSEMENT)
                .map(this::toDto)
                .collect(Collectors.toList()));
            
            situation.setToutesOperations(operations.stream()
                .map(this::toDto)
                .collect(Collectors.toList()));
            
            situation.setNombreOperations(operations.size());
            
            return situation;
            
        } catch (java.lang.Exception e) {
            logger.error("Erreur lors du calcul de la situation: {}", e.getMessage(), e);
            throw new RuntimeException("Erreur lors du calcul: " + e.getMessage());
        }
    }

    @Override
    public SituationCaisseDto getSituationCaisseFournisseur(String uuidFournisseur) {
        logger.info("Calcul de la situation de caisse pour fournisseur: {}", uuidFournisseur);
        
        try {
            Fournisseur fournisseur = fournisseurRepository.findById(uuidFournisseur)
                .orElseThrow(() -> new RuntimeException("Fournisseur non trouvé"));
            
            // Récupérer la situation comptable du fournisseur
            SituationFournisseurDto situationComptable = situationComptableService
                .getSituationFournisseur(uuidFournisseur);
            
            // Récupérer les opérations de caisse liées à ce fournisseur
            List<OperationCaisse> operations = operationCaisseRepository.findByFournisseur(fournisseur);
            
            SituationCaisseDto situation = new SituationCaisseDto();
            
            BigDecimal totalEncaissements = operations.stream()
                .filter(op -> op.getTypeOperation() == EnumTypeTransaction.ENCAISSEMENT)
                .map(OperationCaisse::getMontant)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
            situation.setTotalEncaissements(totalEncaissements);
            
            BigDecimal totalDecaissements = operations.stream()
                .filter(op -> op.getTypeOperation() == EnumTypeTransaction.DECAISSEMENT)
                .map(OperationCaisse::getMontant)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
            situation.setTotalDecaissements(totalDecaissements);
            
            situation.setDetteRestante(BigDecimal.valueOf(situationComptable.getDetteRestante()));
            situation.setSoldeActuel(totalDecaissements.subtract(totalEncaissements));
            
            situation.setEncaissements(operations.stream()
                .filter(op -> op.getTypeOperation() == EnumTypeTransaction.ENCAISSEMENT)
                .map(this::toDto)
                .collect(Collectors.toList()));
            
            situation.setDecaissements(operations.stream()
                .filter(op -> op.getTypeOperation() == EnumTypeTransaction.DECAISSEMENT)
                .map(this::toDto)
                .collect(Collectors.toList()));
            
            situation.setToutesOperations(operations.stream()
                .map(this::toDto)
                .collect(Collectors.toList()));
            
            situation.setNombreOperations(operations.size());
            
            return situation;
            
        } catch (java.lang.Exception e) {
            logger.error("Erreur lors du calcul de la situation: {}", e.getMessage(), e);
            throw new RuntimeException("Erreur lors du calcul: " + e.getMessage());
        }
    }

    /**
     * Met à jour la caisse journalière avec une nouvelle opération
     */
    private void mettreAJourCaisse(OperationCaisse operation) {
        CaisseJournaliere caisse = operation.getCaisseJournaliere();
        
        if (operation.getTypeOperation() == EnumTypeTransaction.ENCAISSEMENT) {
            caisse.setTotalEncaissement(
                caisse.getTotalEncaissement().add(operation.getMontant()));
        } else {
            caisse.setTotalDecaissement(
                caisse.getTotalDecaissement().add(operation.getMontant()));
        }
        
        caisseJournaliereRepository.save(caisse);
    }

    /**
     * Convertit une entité en DTO
     */
    private OperationCaisseDto toDto(OperationCaisse operation) {
        OperationCaisseDto dto = new OperationCaisseDto();
        dto.setUuid(operation.getUuid());
        dto.setTypeOperation(operation.getTypeOperation());
        dto.setMontant(operation.getMontant());
        dto.setDateOperation(operation.getDateOperation());
        dto.setLibelle(operation.getLibelle());
        dto.setTypeDetail(operation.getTypeDetail());
        dto.setCommentaire(operation.getCommentaire());
        
        if (operation.getCaisseJournaliere() != null) {
            dto.setUuidCaisseJournaliere(operation.getCaisseJournaliere().getUuid());
        }
        
        if (operation.getTransaction() != null) {
            dto.setUuidTransaction(operation.getTransaction().getUuid());
        }
        
        if (operation.getPaiementCommandeFournisseur() != null) {
            dto.setUuidPaiementCommandeFournisseur(operation.getPaiementCommandeFournisseur().getUuid());
        }
        
        if (operation.getVersementClientPartenaire() != null) {
            dto.setUuidVersementClientPartenaire(operation.getVersementClientPartenaire().getUuid());
        }
        
        if (operation.getCommandeVente() != null) {
            dto.setUuidCommandeVente(operation.getCommandeVente().getUuid());
            dto.setNumeroCommande(operation.getCommandeVente().getNumeroCommande());
        }
        
        if (operation.getCommandeFournisseur() != null) {
            dto.setUuidCommandeFournisseur(operation.getCommandeFournisseur().getUuid());
        }
        
        if (operation.getClientPartenaire() != null) {
            dto.setUuidClientPartenaire(operation.getClientPartenaire().getUuid());
            dto.setNomClient(operation.getClientPartenaire().getClient().getNom() + " " + 
                operation.getClientPartenaire().getClient().getPrenom());
        }
        
        if (operation.getFournisseur() != null) {
            dto.setUuidFournisseur(operation.getFournisseur().getUuid());
            dto.setNomFournisseur(operation.getFournisseur().getNom() + " " + 
                operation.getFournisseur().getPrenom());
        }
        
        return dto;
    }
}
