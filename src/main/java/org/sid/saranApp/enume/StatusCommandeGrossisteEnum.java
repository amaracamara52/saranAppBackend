package org.sid.saranApp.enume;

/**
 * Statuts des commandes entre détaillants et grossistes
 */
public enum StatusCommandeGrossisteEnum {
    EN_ATTENTE,           // Commande créée, en attente de validation
    VALIDEE,              // Commande validée par le grossiste
    EN_PREPARATION,       // Commande en cours de préparation
    PRETE,                // Commande prête pour la livraison
    EN_LIVRAISON,         // Commande en cours de livraison
    LIVREE,               // Commande livrée au détaillant
    ANNULEE               // Commande annulée
}
