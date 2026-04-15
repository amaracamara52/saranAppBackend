package org.sid.saranApp.service;

import org.sid.saranApp.dto.PageDataDto;
import org.sid.saranApp.dto.ProduitDto;
import org.sid.saranApp.dto.version.ProduitStockDto;

import java.util.List;

public interface ProduitService {

	public ProduitDto add(ProduitDto produitDto);

	public List<ProduitStockDto> findAll();
	
	public List<ProduitDto> listeProduitAVendre();

	public ProduitDto getById(String uuid);

	public List<ProduitDto> listeStockInferieurA5();

	public List<ProduitDto> listeStockPerime();

	public List<ProduitDto> listeStockPerimeDans3mois();

	public List<ProduitDto> listeTopStock(int limit);

	public ProduitDto supprimer(String uuid);

	public ProduitDto update(ProduitDto produitDto, String uuid);
	
	
	PageDataDto<ProduitDto> listeStockInferieurA5(int page,int size,String key);

	PageDataDto<ProduitDto> listeStockPerime(int page,int size,String key);

	PageDataDto<ProduitDto> listeStockPerimeDans3mois(int page,int size,String key);
	PageDataDto<ProduitDto> listeProduits(int page,int size,String key);
	void delete(String uuid);
	
	/**
	 * Vérifie et déclenche l'approvisionnement automatique après une vente
	 * @param uuidProduit l'UUID du produit vendu
	 */
	void verifierApprovisionnementApresVente(String uuidProduit);
	
	
}
