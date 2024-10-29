package org.sid.saranApp.service;

import java.util.Date;
import java.util.List;

import org.sid.saranApp.dto.CommandeVenteDto;

public interface CommandeVenteService {
	
	public CommandeVenteDto add(CommandeVenteDto commandeVenteDto);
	public CommandeVenteDto update(CommandeVenteDto commandeVenteDto, String uuid);
	public CommandeVenteDto supprimer(String uuid);
	public List<CommandeVenteDto> findAll();
	public List<CommandeVenteDto> listeCommandeVenteByJour();
	public List<CommandeVenteDto> historiqueCommandeVente(Date dateDebut,Date dateFin);
	public List<CommandeVenteDto> topCommandeVente();
	public CommandeVenteDto getById(String uuid);

}
