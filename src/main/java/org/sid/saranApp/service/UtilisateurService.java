package org.sid.saranApp.service;

import java.util.List;

import org.sid.saranApp.dto.UtilisateurDto;

public interface UtilisateurService {
	UtilisateurDto addUtilisateur(UtilisateurDto utilisateurDto);
	UtilisateurDto updateUtilisateur(UtilisateurDto utilisateurDto, String uuid);
	UtilisateurDto getUtilisateur(String uuid);
	List<UtilisateurDto> findAll();
	UtilisateurDto getUtilisateurByEmail(String email);
	void deleteUtilisateur(String uuid);

}
