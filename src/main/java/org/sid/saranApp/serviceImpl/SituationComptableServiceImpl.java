package org.sid.saranApp.serviceImpl;

import org.sid.saranApp.dto.*;
import org.sid.saranApp.enume.EnumTypeCommande;
import org.sid.saranApp.model.*;
import org.sid.saranApp.repository.*;
import org.sid.saranApp.service.SituationComptableService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service pour la gestion de la situation comptable (dettes et créances)
 */
@Service
public class SituationComptableServiceImpl implements SituationComptableService {

    private static final Logger logger = LoggerFactory.getLogger(SituationComptableServiceImpl.class);

    @Autowired
    private CommandeFournisseurRepository commandeFournisseurRepository;
    
    @Autowired
    private CommandeVenteRepository commandeVenteRepository;
    
    @Autowired
    private PaiementCommandeFournisseurRepository paiementCommandeFournisseurRepository;
    
    @Autowired
    private VersementClientPartenaireRepository versementClientPartenaireRepository;
    
    @Autowired
    private FournisseurRepository fournisseurRepository;
    
    @Autowired
    private ClientPartenaireRepository clientPartenaireRepository;
    
    @Autowired
    private UtilisateurServiceImpl utilisateurServiceImpl;

    @Override
    public SituationComptableDto getSituationComptable() {
        logger.info("Calcul de la situation comptable");
        
        try {
            Utilisateur utilisateur = utilisateurServiceImpl.getCurentUtilisateur();
            if (utilisateur == null || utilisateur.getBoutique() == null) {
                throw new RuntimeException("Utilisateur ou boutique non trouvé");
            }
            
            Boutique boutique = utilisateur.getBoutique();
            
            SituationComptableDto situation = new SituationComptableDto();
            
            // Calculer les situations des fournisseurs
            List<SituationFournisseurDto> situationsFournisseurs = getSituationsFournisseurs();
            situation.setSituationsFournisseurs(situationsFournisseurs);
            
            // Calculer les situations des clients partenaires
            List<SituationClientPartenaireDto> situationsClientsPartenaires = getSituationsClientsPartenaires();
            situation.setSituationsClientsPartenaires(situationsClientsPartenaires);
            
            // Calculer les totaux
            double totalDettes = situationsFournisseurs.stream()
                .mapToDouble(SituationFournisseurDto::getDetteRestante)
                .sum();
            situation.setTotalDettesFournisseurs(totalDettes);
            
            double totalCreances = situationsClientsPartenaires.stream()
                .mapToDouble(SituationClientPartenaireDto::getCreanceRestante)
                .sum();
            situation.setTotalCreancesClientsPartenaires(totalCreances);
            
            // Calculer le solde net
            situation.setSoldeNet(totalCreances - totalDettes);
            
            logger.info("Situation comptable calculée: Dettes={}, Créances={}, Solde={}", 
                totalDettes, totalCreances, situation.getSoldeNet());
            
            return situation;
            
        } catch (java.lang.Exception e) {
            logger.error("Erreur lors du calcul de la situation comptable: {}", e.getMessage(), e);
            throw new RuntimeException("Erreur lors du calcul de la situation comptable: " + e.getMessage());
        }
    }

    @Override
    public SituationFournisseurDto getSituationFournisseur(String uuidFournisseur) {
        try {
            Fournisseur fournisseur = fournisseurRepository.findById(uuidFournisseur)
                .orElseThrow(() -> new RuntimeException("Fournisseur non trouvé"));
            
            Utilisateur utilisateur = utilisateurServiceImpl.getCurentUtilisateur();
            Boutique boutique = utilisateur.getBoutique();
            
            // Récupérer toutes les commandes du fournisseur pour cette boutique
            List<CommandeFournisseur> commandes = commandeFournisseurRepository.listeCommandes(boutique.getUuid())
                .stream()
                .filter(c -> c.getFournisseur().getUuid().equals(uuidFournisseur))
                .collect(Collectors.toList());
            
            SituationFournisseurDto situation = new SituationFournisseurDto();
            situation.setUuidFournisseur(fournisseur.getUuid());
            situation.setNomFournisseur(fournisseur.getNom() + " " + fournisseur.getPrenom());
            situation.setEmailFournisseur(fournisseur.getEmail());
            situation.setTelephoneFournisseur(fournisseur.getTelephone());
            
            double montantTotalCommandes = 0.0;
            double montantTotalPaiements = 0.0;
            List<CommandeFournisseurSoldeDto> commandesSolde = new ArrayList<>();
            
            for (CommandeFournisseur commande : commandes) {
                double montantCommande = calculerMontantTotalCommande(commande);
                double montantPaye = calculerMontantTotalPaye(commande);
                
                montantTotalCommandes += montantCommande;
                montantTotalPaiements += montantPaye;
                
                CommandeFournisseurSoldeDto soldeDto = new CommandeFournisseurSoldeDto();
                soldeDto.setUuid(commande.getUuid());
                soldeDto.setRefCommande(commande.getRefCommande());
                soldeDto.setDateCommande(commande.getDateCommandeFournisseur());
                soldeDto.setMontantTotal(montantCommande);
                soldeDto.setMontantPaye(montantPaye);
                soldeDto.setMontantRestant(montantCommande - montantPaye);
                soldeDto.setEstPaye(montantPaye >= montantCommande);
                
                commandesSolde.add(soldeDto);
            }
            
            situation.setMontantTotalCommandes(montantTotalCommandes);
            situation.setMontantTotalPaiements(montantTotalPaiements);
            situation.setDetteRestante(montantTotalCommandes - montantTotalPaiements);
            situation.setCommandes(commandesSolde);
            
            return situation;
            
        } catch (java.lang.Exception e) {
            logger.error("Erreur lors du calcul de la situation fournisseur: {}", e.getMessage(), e);
            throw new RuntimeException("Erreur lors du calcul de la situation fournisseur: " + e.getMessage());
        }
    }

    @Override
    public SituationClientPartenaireDto getSituationClientPartenaire(String uuidClientPartenaire) {
        try {
            ClientPartenaire clientPartenaire = clientPartenaireRepository.findById(uuidClientPartenaire)
                .orElseThrow(() -> new RuntimeException("Client partenaire non trouvé"));
            
            Utilisateur utilisateur = utilisateurServiceImpl.getCurentUtilisateur();
            Boutique boutique = utilisateur.getBoutique();
            
            // Récupérer toutes les commandes en gros du client partenaire pour cette boutique
            List<CommandeVente> commandes = commandeVenteRepository.listeCommandes(boutique.getUuid())
                .stream()
                .filter(c -> c.getTypeCommande() == EnumTypeCommande.EN_GROS 
                    && c.getClient() != null 
                    && c.getClient().getUuid().equals(clientPartenaire.getClient().getUuid()))
                .collect(Collectors.toList());
            
            SituationClientPartenaireDto situation = new SituationClientPartenaireDto();
            situation.setUuidClientPartenaire(clientPartenaire.getUuid());
            situation.setUuidClient(clientPartenaire.getClient().getUuid());
            situation.setNomClient(clientPartenaire.getClient().getNom());
            situation.setPrenomClient(clientPartenaire.getClient().getPrenom());
            situation.setEmailClient(clientPartenaire.getClient().getEmail());
            situation.setNumeroCompte(clientPartenaire.getNumeroCompte());
            
            double montantTotalCommandes = 0.0;
            double montantTotalVersements = 0.0;
            List<CommandeVenteSoldeDto> commandesSolde = new ArrayList<>();
            
            for (CommandeVente commande : commandes) {
                double montantCommande = commande.getMontantCommade();
                double montantPaye = calculerMontantTotalVersements(commande);
                
                montantTotalCommandes += montantCommande;
                montantTotalVersements += montantPaye;
                
                CommandeVenteSoldeDto soldeDto = new CommandeVenteSoldeDto();
                soldeDto.setUuid(commande.getUuid());
                soldeDto.setNumeroCommande(commande.getNumeroCommande());
                soldeDto.setDatePaiement(commande.getDatePaiement());
                soldeDto.setMontantTotal(montantCommande);
                soldeDto.setMontantPaye(montantPaye);
                soldeDto.setMontantRestant(montantCommande - montantPaye);
                soldeDto.setEstPaye(montantPaye >= montantCommande);
                
                commandesSolde.add(soldeDto);
            }
            
            situation.setMontantTotalCommandes(montantTotalCommandes);
            situation.setMontantTotalVersements(montantTotalVersements);
            situation.setCreanceRestante(montantTotalCommandes - montantTotalVersements);
            situation.setCommandes(commandesSolde);
            
            return situation;
            
        } catch (java.lang.Exception e) {
            logger.error("Erreur lors du calcul de la situation client partenaire: {}", e.getMessage(), e);
            throw new RuntimeException("Erreur lors du calcul de la situation client partenaire: " + e.getMessage());
        }
    }

    @Override
    public List<SituationFournisseurDto> getSituationsFournisseurs() {
        try {
            Utilisateur utilisateur = utilisateurServiceImpl.getCurentUtilisateur();
            Boutique boutique = utilisateur.getBoutique();
            
            // Récupérer tous les fournisseurs de la boutique
            List<Fournisseur> fournisseurs = fournisseurRepository.listeFournisseurs(boutique.getUuid());
            
            List<SituationFournisseurDto> situations = new ArrayList<>();
            
            for (Fournisseur fournisseur : fournisseurs) {
                situations.add(getSituationFournisseur(fournisseur.getUuid()));
            }
            
            return situations;
            
        } catch (java.lang.Exception e) {
            logger.error("Erreur lors de la récupération des situations fournisseurs: {}", e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    @Override
    public List<SituationClientPartenaireDto> getSituationsClientsPartenaires() {
        try {
            Utilisateur utilisateur = utilisateurServiceImpl.getCurentUtilisateur();
            Boutique boutique = utilisateur.getBoutique();
            
            // Récupérer tous les clients partenaires de la boutique
            List<ClientPartenaire> clientsPartenaires = clientPartenaireRepository.findByBoutique(boutique)
                .stream()
                .filter(cp -> "ACTIF".equals(cp.getStatut()))
                .collect(Collectors.toList());
            
            List<SituationClientPartenaireDto> situations = new ArrayList<>();
            
            for (ClientPartenaire clientPartenaire : clientsPartenaires) {
                situations.add(getSituationClientPartenaire(clientPartenaire.getUuid()));
            }
            
            return situations;
            
        } catch (java.lang.Exception e) {
            logger.error("Erreur lors de la récupération des situations clients partenaires: {}", e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    /**
     * Calcule le montant total d'une commande fournisseur
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
     * Calcule le montant total payé pour une commande fournisseur
     */
    private double calculerMontantTotalPaye(CommandeFournisseur commande) {
        List<PaiementCommandeFournisseur> paiements = paiementCommandeFournisseurRepository
            .findByCommandeFournisseur(commande);
        return paiements.stream()
            .mapToDouble(PaiementCommandeFournisseur::getMontantVerse)
            .sum();
    }

    /**
     * Calcule le montant total des versements pour une commande vente
     */
    private double calculerMontantTotalVersements(CommandeVente commande) {
        List<VersementClientPartenaire> versements = versementClientPartenaireRepository
            .findByCommandeVente(commande);
        return versements.stream()
            .mapToDouble(VersementClientPartenaire::getMontantVerse)
            .sum();
    }
}
