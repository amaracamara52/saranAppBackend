package org.sid.saranApp.service;

import java.util.List;

import org.sid.saranApp.dto.CommandeFournisseurDto;

public interface CommandeFournisseurService {
	
	CommandeFournisseurDto addCommandeFournisseur(CommandeFournisseurDto commandeFournisseurDto);
	CommandeFournisseurDto updateCommandeFournisseur(CommandeFournisseurDto commandeFournisseurDto, String uuid);
	List<CommandeFournisseurDto> findAll();
	CommandeFournisseurDto getCommandeFournisseur(String uuid);
	void deleteCommandeFournisseur(String uuid);

}
