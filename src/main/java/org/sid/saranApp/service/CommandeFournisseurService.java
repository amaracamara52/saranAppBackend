package org.sid.saranApp.service;

import java.util.List;

import org.sid.saranApp.dto.CommandeFournisseurDto;

public interface CommandeFournisseurService {

	CommandeFournisseurDto addCommandeFournisseur(CommandeFournisseurDto commandeFournisseurDto);

	CommandeFournisseurDto addLivraisonCommandeFournisseur(CommandeFournisseurDto commandeFournisseurDto);

	CommandeFournisseurDto addStockFromCommandeAndLivraison(CommandeFournisseurDto commandeFournisseurDto);

	void deleteCommandeFournisseur(String uuid);

	List<CommandeFournisseurDto> findAll();

	CommandeFournisseurDto getCommandeFournisseur(String uuid);

	CommandeFournisseurDto updateCommandeFournisseur(CommandeFournisseurDto commandeFournisseurDto, String uuid);

}
