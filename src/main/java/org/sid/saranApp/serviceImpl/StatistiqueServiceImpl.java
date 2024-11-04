/**
 *
 */
package org.sid.saranApp.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.sid.saranApp.dto.CommandeFournisseurDto;
import org.sid.saranApp.dto.CommandeVenteDto;
import org.sid.saranApp.dto.StatistiqueCommandeFournisseurDto;
import org.sid.saranApp.dto.StatistiqueCommandeVenteDto;
import org.sid.saranApp.mapper.Mapper;
import org.sid.saranApp.model.CommandeFournisseur;
import org.sid.saranApp.model.CommandeVente;
import org.sid.saranApp.model.Utilisateur;
import org.sid.saranApp.repository.CommandeFournisseurRepository;
import org.sid.saranApp.repository.CommandeVenteRepository;
import org.sid.saranApp.repository.UtilisateurRepository;
import org.sid.saranApp.service.StatistiqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class StatistiqueServiceImpl  {

	@Autowired
	private CommandeFournisseurRepository commandeFournisseurRepository;
	@Autowired
	private CommandeVenteRepository commandeVenteRepository;
	@Autowired
	private UtilisateurRepository utilisateurRepository;

//	@Override
//	public StatistiqueCommandeFournisseurDto statistiqueCommandeFournisseurByPeriode(Date dateDebut, Date dateFin,
//			String status) {
//		// TODO Auto-generated method stub
//		StatistiqueCommandeFournisseurDto dto = new StatistiqueCommandeFournisseurDto();
//		List<CommandeFournisseur> commandeFournisseurs = new ArrayList<CommandeFournisseur>();
//		int somme = 0;
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//
//		Utilisateur utilisateur = utilisateurRepository.findByEmail(auth.getName()).orElseThrow(null);
//
//		if (dateDebut != null && dateFin != null) {
//			commandeFournisseurs = commandeFournisseurRepository.listeCommandePeriode(dateDebut, dateFin,
//					utilisateur.getBoutique().getUuid());
//		}
//
//		if (dateDebut != null && dateFin != null && status != null) {
//			commandeFournisseurs = commandeFournisseurRepository.listeCommandePeriodeByStatus(dateDebut, dateFin,
//					utilisateur.getBoutique().getUuid(), status);
//		}
//
//		List<CommandeFournisseurDto> commandeFournisseurDtos = new ArrayList<CommandeFournisseurDto>();
//		commandeFournisseurs.forEach(cmd -> commandeFournisseurDtos.add(Mapper.toCommandeFournisseurDto(cmd)));
//
//		dto.setMontant(somme);
//
//		dto.setCommandeFournisseurDtos(commandeFournisseurDtos);
//
//		return dto;
//	}
//
//	@Override
//	public StatistiqueCommandeVenteDto statistiqueCommandeVenteByPeriode(Date dateDebut, Date dateFin, String status) {
//		// TODO Auto-generated method stub
//
//		StatistiqueCommandeVenteDto dto = new StatistiqueCommandeVenteDto();
//		List<CommandeVente> commandeVentes = new ArrayList<CommandeVente>();
//		int somme = 0;
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//
//		Utilisateur utilisateur = utilisateurRepository.findByEmail(auth.getName()).orElseThrow(null);
//
//		if (dateDebut != null && dateFin != null) {
//			commandeVentes = commandeVenteRepository.listeCommandePeriode(dateDebut, dateFin,
//					utilisateur.getBoutique().getUuid());
//			somme = commandeVenteRepository.sommeCommandeVente(dateDebut, dateFin, utilisateur.getBoutique().getUuid());
//		}
//
//		if (dateDebut == null && dateFin == null) {
//			Date today = new Date();
//			commandeVentes = commandeVenteRepository.listeCommandePeriode(today, today,
//					utilisateur.getBoutique().getUuid());
//			somme = commandeVenteRepository.sommeCommandeVente(today, today, utilisateur.getBoutique().getUuid());
//		}
//
//		List<CommandeVenteDto> commandeVenDtos = new ArrayList<CommandeVenteDto>();
//		commandeVentes.forEach(cmd -> commandeVenDtos.add(Mapper.toCommandeVente(cmd)));
//
//		dto.setMontant(somme);
//
//		dto.setCommandeVenteDtos(commandeVenDtos);
//
//		return dto;
//	}

}
