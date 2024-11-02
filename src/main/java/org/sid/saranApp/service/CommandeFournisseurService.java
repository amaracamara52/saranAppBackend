package org.sid.saranApp.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.sid.saranApp.dto.ClientDto;
import org.sid.saranApp.dto.CommandeFournisseurDto;
import org.sid.saranApp.dto.PageDataDto;
import org.sid.saranApp.enume.StatusCommandeFournisseurEnum;

public interface CommandeFournisseurService {

	CommandeFournisseurDto addCommandeFournisseur(CommandeFournisseurDto commandeFournisseurDto);

	CommandeFournisseurDto addLivraisonCommandeFournisseur(CommandeFournisseurDto commandeFournisseurDto);

	CommandeFournisseurDto addStockFromCommandeAndLivraison(CommandeFournisseurDto commandeFournisseurDto);

	void deleteCommandeFournisseur(String uuid);

	List<CommandeFournisseurDto> findAll();

	CommandeFournisseurDto getCommandeFournisseur(String uuid);

	CommandeFournisseurDto updateCommandeFournisseur(CommandeFournisseurDto commandeFournisseurDto, String uuid);
	
	PageDataDto<CommandeFournisseurDto> listeCommandeFournisseurs(int page,int size,StatusCommandeFournisseurEnum key);
	
	PageDataDto<CommandeFournisseurDto> listeCommandeFournisseurByDates(int page,int size,LocalDate   dateDebut,LocalDate  dateFin);
	
	void delete(String uuid);

}
