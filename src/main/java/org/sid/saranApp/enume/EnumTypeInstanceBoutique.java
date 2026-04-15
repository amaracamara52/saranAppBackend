package org.sid.saranApp.enume;

/**
 * Types d'instance de boutique
 * - PRINCIPALE: Boutique principale
 * - AUXILIAIRE: Boutique auxiliaire
 * - GROSSISTE: Boutique grossiste (peut commander aux fournisseurs et vendre aux détaillants)
 * - DETAILLANT: Boutique détaillante (commande au grossiste et vend au détail)
 */
public enum EnumTypeInstanceBoutique {
    PRINCIPALE, AUXILIAIRE, GROSSISTE, DETAILLANT
}
