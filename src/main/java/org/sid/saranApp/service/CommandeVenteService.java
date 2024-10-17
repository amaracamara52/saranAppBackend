package org.sid.saranApp.service;

import java.util.List;

import org.sid.saranApp.dto.CommandeVenteDto;

public interface CommandeVenteService {
	
	public CommandeVenteDto add(CommandeVenteDto commandeVenteDto);
	public CommandeVenteDto update(CommandeVenteDto commandeVenteDto, String uuid);
	public CommandeVenteDto supprimer(String uuid);
	public List<CommandeVenteDto> findAll();
	public CommandeVenteDto getById(String uuid);

}
