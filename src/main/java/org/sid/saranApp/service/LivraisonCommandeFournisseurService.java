package org.sid.saranApp.service;

import java.util.List;

import org.sid.saranApp.dto.LivraisonCommandeFournisseurDto;

public interface LivraisonCommandeFournisseurService {

	LivraisonCommandeFournisseurDto addLivraisonCommandeFournisseur(LivraisonCommandeFournisseurDto livraisonCommandeFournisseurDto);
	LivraisonCommandeFournisseurDto updateLivraisonCommandeFournisseur(LivraisonCommandeFournisseurDto livraisonCommandeFournisseurDto, String uuid);
	List<LivraisonCommandeFournisseurDto> findAll();
	LivraisonCommandeFournisseurDto getLivraisonCommandeFournisseur(String uuid);
	void deleteLivraisonCommandeFournisseur(String uuid);
}
