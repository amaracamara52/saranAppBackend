package org.sid.saranApp.service;

import java.util.List;

import org.sid.saranApp.dto.UtilisateurDto;

public interface UtilisateurService {
	UtilisateurDto addUtilisateur(UtilisateurDto utilisateurDto);

	void deleteUtilisateur(String uuid);

	List<UtilisateurDto> findAll();

	UtilisateurDto getUtilisateur(String uuid);

	UtilisateurDto getUtilisateurByEmail(String email);

	UtilisateurDto updateUtilisateur(UtilisateurDto utilisateurDto, String uuid);

	UtilisateurDto userLogin();

}
