package org.sid.saranApp.service;

import java.util.List;

import org.sid.saranApp.dto.PaiementCommandeFournisseurDto;

public interface PaiementCommandeFournisseurService {
	
	PaiementCommandeFournisseurDto addPaiementCommandeFournisseur(PaiementCommandeFournisseurDto paiementCommandeFournisseurDto);
	PaiementCommandeFournisseurDto updatePaiementCommandeFournisseur(PaiementCommandeFournisseurDto paiementCommandeFournisseurDto, String uuid);
	List<PaiementCommandeFournisseurDto> findAll();
	PaiementCommandeFournisseurDto getPaiementCommandeFournisseur(String uuid);
	void deletePaiementCommandeFournisseur(String uuid);
}
