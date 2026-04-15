package org.sid.saranApp.service;

import org.sid.saranApp.dto.CommandeGrossisteDto;
import org.sid.saranApp.dto.PageDataDto;

import java.util.List;

/**
 * Service pour la gestion des commandes entre détaillants et grossistes
 */
public interface CommandeGrossisteService {
    
    /**
     * Crée une nouvelle commande d'un détaillant vers un grossiste
     * @param commandeGrossisteDto Les données de la commande
     * @return La commande créée
     */
    CommandeGrossisteDto creerCommande(CommandeGrossisteDto commandeGrossisteDto);
    
    /**
     * Valide une commande (uniquement pour les grossistes)
     * @param uuidCommande L'UUID de la commande à valider
     * @return La commande validée
     */
    CommandeGrossisteDto validerCommande(String uuidCommande);
    
    /**
     * Annule une commande
     * @param uuidCommande L'UUID de la commande à annuler
     * @return La commande annulée
     */
    CommandeGrossisteDto annulerCommande(String uuidCommande);
    
    /**
     * Récupère toutes les commandes d'un détaillant
     * @return Liste des commandes
     */
    List<CommandeGrossisteDto> getCommandesDetaillee();
    
    /**
     * Récupère toutes les commandes reçues par un grossiste
     * @return Liste des commandes
     */
    List<CommandeGrossisteDto> getCommandesGrossiste();
    
    /**
     * Récupère une commande par son UUID
     * @param uuid L'UUID de la commande
     * @return La commande
     */
    CommandeGrossisteDto getCommandeById(String uuid);
    
    /**
     * Récupère les commandes avec pagination
     * @param page Numéro de page
     * @param size Taille de la page
     * @param key Mot-clé de recherche
     * @return Page de commandes
     */
    PageDataDto<CommandeGrossisteDto> getCommandes(int page, int size, String key);
    
    /**
     * Récupère toutes les commandes d'un client partenaire
     * @param uuidClientPartenaire L'UUID du client partenaire
     * @return Liste des commandes du client partenaire
     */
    List<CommandeGrossisteDto> getCommandesClientPartenaire(String uuidClientPartenaire);
}
