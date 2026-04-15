package org.sid.saranApp.service;

import org.sid.saranApp.dto.CommandeFournisseurDto;
import org.sid.saranApp.dto.CommandeFournisseurPatchDto;
import org.sid.saranApp.dto.PageDataDto;
import org.sid.saranApp.enume.StatusCommandeFournisseurEnum;

import java.time.LocalDate;
import java.util.List;

public interface CommandeFournisseurService {

	CommandeFournisseurDto addCommandeFournisseur(CommandeFournisseurDto commandeFournisseurDto);

	CommandeFournisseurDto addLivraisonCommandeFournisseur(CommandeFournisseurDto commandeFournisseurDto);

	CommandeFournisseurDto addStockFromCommandeAndLivraison(CommandeFournisseurDto commandeFournisseurDto);

	void deleteCommandeFournisseur(String uuid);

	List<CommandeFournisseurDto> findAll();

	CommandeFournisseurDto getCommandeFournisseur(String uuid);

	CommandeFournisseurDto updateCommandeFournisseur(CommandeFournisseurDto commandeFournisseurDto, String uuid);

	/** Mise à jour légère : statut métier et/ou champ payé. */
	CommandeFournisseurDto patchCommandeFournisseur(String uuid, CommandeFournisseurPatchDto patch);
	
	PageDataDto<CommandeFournisseurDto> listeCommandeFournisseurs(int page,int size,StatusCommandeFournisseurEnum key);
	
	PageDataDto<CommandeFournisseurDto> listeCommandeFournisseurByDates(int page,int size,LocalDate   dateDebut,LocalDate  dateFin);
	
	void delete(String uuid);

}
