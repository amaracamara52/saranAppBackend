package org.sid.saranApp.enume;

/**
 * Mode de gestion des unités pour un article.
 * <ul>
 *   <li><b>NORMAL</b> : une seule unité de vente (facteur ×1). Le stock utilisateur correspond à l’unité de base ;
 *       pas de conversion multi-conditionnements.</li>
 *   <li><b>MIXTE</b> : plusieurs unités liées (ex. carton + pièce) avec facteurs ; tout le stock interne reste en
 *       <b>unité de base</b> ; les écrans convertissent (ventes, réceptions). Les quantités par unité sont recalculées
 *       depuis le total base (ex. cartons « ouverts »).</li>
 * </ul>
 */
public enum TypeGestionUniteProduitEnum {
    NORMAL,
    MIXTE
}
