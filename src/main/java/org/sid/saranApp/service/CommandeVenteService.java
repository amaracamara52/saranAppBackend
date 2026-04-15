package org.sid.saranApp.service;

import org.sid.saranApp.dto.CommandeVenteDto;
import org.sid.saranApp.dto.ImportVenteHistoriqueResultDto;
import org.sid.saranApp.dto.PageDataDto;
import org.sid.saranApp.dto.request.CommandeVenteRequestDto;
import org.sid.saranApp.dto.request.ImportVenteHistoriqueVenteDto;

import java.util.Date;
import java.util.List;

public interface CommandeVenteService {
	
	public CommandeVenteDto add(CommandeVenteRequestDto commandeVenteDto);
	public CommandeVenteDto update(CommandeVenteDto commandeVenteDto, String uuid);
	public CommandeVenteDto supprimer(String uuid);
	public List<CommandeVenteDto> findAll();
	public List<CommandeVenteDto> listeCommandeVenteByJour();
	public List<CommandeVenteDto> historiqueCommandeVente(Date dateDebut,Date dateFin);
	public List<CommandeVenteDto> topCommandeVente();
	public CommandeVenteDto getById(String uuid);
	void delete(String uuid);
	PageDataDto<CommandeVenteDto> listeCommandeVenteByJour(int page,int size,String key);
	PageDataDto<CommandeVenteDto> historiqueCommandeVente(Date dateDebut,Date dateFin,int page,int size,String key);
	PageDataDto<CommandeVenteDto> listeCommandeVentes(int page,int size,String key);
	
	/**
	 * Valide une commande de vente en gros (uniquement pour les grossistes)
	 * @param uuidCommande L'UUID de la commande à valider
	 * @return La commande validée
	 */
	CommandeVenteDto validerCommandeEnGros(String uuidCommande);

	/**
	 * Import JSON de ventes historiques : enregistre commandes et lignes sans retirer le stock
	 * ni créer transactions / opérations de caisse. La caisse peut être fermée.
	 */
	ImportVenteHistoriqueResultDto importVentesHistoriqueJson(List<ImportVenteHistoriqueVenteDto> ventes);

}
