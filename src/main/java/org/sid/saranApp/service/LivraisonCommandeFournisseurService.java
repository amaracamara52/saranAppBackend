package org.sid.saranApp.service;

import org.sid.saranApp.dto.LivraisonCommandeFournisseurDto;
import org.sid.saranApp.dto.PageDataDto;

import java.util.List;

public interface LivraisonCommandeFournisseurService {

	LivraisonCommandeFournisseurDto addLivraisonCommandeFournisseur(LivraisonCommandeFournisseurDto livraisonCommandeFournisseurDto);
	LivraisonCommandeFournisseurDto updateLivraisonCommandeFournisseur(LivraisonCommandeFournisseurDto livraisonCommandeFournisseurDto, String uuid);
	List<LivraisonCommandeFournisseurDto> findAll();
	LivraisonCommandeFournisseurDto getLivraisonCommandeFournisseur(String uuid);
	void deleteLivraisonCommandeFournisseur(String uuid);
	PageDataDto<LivraisonCommandeFournisseurDto> listeLivraisonCommandeFournisseurs(int page,int size,String key);
	void delete(String uuid);
	
	/**
	 * Vérifie une livraison de commande fournisseur
	 * @param uuidLivraison L'UUID de la livraison
	 * @param commentaire Commentaire de vérification (optionnel)
	 * @return La livraison vérifiée
	 */
	LivraisonCommandeFournisseurDto verifierLivraison(String uuidLivraison, String commentaire);
	
	/**
	 * Rejette une livraison de commande fournisseur
	 * @param uuidLivraison L'UUID de la livraison
	 * @param commentaire Commentaire de rejet
	 * @return La livraison rejetée
	 */
	LivraisonCommandeFournisseurDto rejeterLivraison(String uuidLivraison, String commentaire);
}
