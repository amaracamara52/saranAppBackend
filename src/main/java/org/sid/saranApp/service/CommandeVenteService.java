package org.sid.saranApp.service;

import java.util.Date;
import java.util.List;

import org.sid.saranApp.dto.CommandeFournisseurDto;
import org.sid.saranApp.dto.CommandeVenteDto;
import org.sid.saranApp.dto.PageDataDto;

public interface CommandeVenteService {
	
	public CommandeVenteDto add(CommandeVenteDto commandeVenteDto);
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

}
