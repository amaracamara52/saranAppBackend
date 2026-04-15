package org.sid.saranApp.service;

import org.sid.saranApp.dto.PageDataDto;
import org.sid.saranApp.dto.PaiementCommandeVenteDto;

import java.util.List;

public interface PaiementCommandeVenteService {
	
	PaiementCommandeVenteDto addPaiementCommandeFournisseur(PaiementCommandeVenteDto paiementCommandeFournisseurDto);
	PaiementCommandeVenteDto updatePaiementCommandeFournisseur(PaiementCommandeVenteDto paiementCommandeFournisseurDto, String uuid);
	List<PaiementCommandeVenteDto> findAll();
	PaiementCommandeVenteDto getPaiementCommandeFournisseur(String uuid);
	void deletePaiementCommandeFournisseur(String uuid);
	PageDataDto<PaiementCommandeVenteDto> listePaiementCommandeFournisseurs(int page,int size,String key);
	
}
