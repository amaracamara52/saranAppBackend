package org.sid.saranApp.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.sid.saranApp.dto.CommandeVenteDto;
import org.sid.saranApp.exception.Exception;
import org.sid.saranApp.mapper.Mapper;
import org.sid.saranApp.model.Boutique;
import org.sid.saranApp.model.Client;
import org.sid.saranApp.model.CommandeVente;
import org.sid.saranApp.model.Utilisateur;
import org.sid.saranApp.repository.BoutiqueRepository;
import org.sid.saranApp.repository.ClientRepository;
import org.sid.saranApp.repository.CommandeVenteRepository;
import org.sid.saranApp.repository.UtilisateurRepository;
import org.sid.saranApp.service.CommandeVenteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CommandeVenteServiceImpl implements CommandeVenteService{
	
	@Autowired
	private ClientRepository clientRepository;
	
	@Autowired
	private CommandeVenteRepository commandeVenteRepository;
	@Autowired
	private UtilisateurRepository utilisateurRepository;
	@Autowired 
	private BoutiqueRepository boutiqueRepository;
	
	 Logger logger = LoggerFactory.getLogger(CommandeVenteServiceImpl.class);
	
	@Override
	public CommandeVenteDto add(CommandeVenteDto commandeVenteDto) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		logger.info("hello {}",auth.getName());
		CommandeVenteDto response = new CommandeVenteDto();
		if(commandeVenteDto == null) {
			response.setCode(Exception.error);
			response.setMessage("Veuillez renseigner les champs");
			return response;
		}
		
		Optional<Client> clientOptional = clientRepository.findById(commandeVenteDto.getId_client());
		if(!clientOptional.isPresent()) {
			response.setCode(Exception.error);
			response.setMessage("Ce client n'exist pas");
			return response;
		}
		Optional<Utilisateur> utilisateurOptional = utilisateurRepository.findById(commandeVenteDto.getUuidUtilisateur());
		if(!utilisateurOptional.isPresent()) {
			response.setCode(Exception.error);
			response.setMessage("Cet Utilisateur n'exist pas");
			return response;
		}
		Optional<Boutique> boutiqueOptional = boutiqueRepository.findById(commandeVenteDto.getUuidBoutique());
		if(!utilisateurOptional.isPresent()) {
			response.setCode(Exception.error);
			response.setMessage("Cette Boutique n'exist pas");
			return response;
		}
		
		
		CommandeVente commandeVente = Mapper.toCommandeVenteDto(commandeVenteDto, clientOptional.get(), utilisateurOptional.get(), boutiqueOptional.get());
		CommandeVente commandeVenteSave = commandeVenteRepository.save(commandeVente);
		response=Mapper.toCommandeVente(commandeVenteSave);
		response.setCode(Exception.succes);
		response.setMessage("Enregistrement effectuer avec success");
		return response;
	}

	@Override
	public CommandeVenteDto update(CommandeVenteDto commandeVenteDto, String uuid) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		CommandeVenteDto response = new CommandeVenteDto();
		if(commandeVenteDto == null || uuid == null) {
			response.setCode(Exception.error);
			response.setMessage("Veuillez renseigner les champs");
			return response;
		}
		
		Optional<Client> clientOptional = clientRepository.findById(commandeVenteDto.getId_client());
		if(!clientOptional.isPresent()) {
			response.setCode(Exception.error);
			response.setMessage("Ce client n'exist pas");
			return response;
		}
		Optional<Utilisateur> utilisateurOptional = utilisateurRepository.findById(commandeVenteDto.getUuidUtilisateur());
		if(!utilisateurOptional.isPresent()) {
			response.setCode(Exception.error);
			response.setMessage("Cet Utilisateur n'exist pas");
			return response;
		}
		Optional<Boutique> boutiqueOptional = boutiqueRepository.findById(commandeVenteDto.getUuidBoutique());
		if(!utilisateurOptional.isPresent()) {
			response.setCode(Exception.error);
			response.setMessage("Cette Boutique n'exist pas");
			return response;
		}
		
		commandeVenteDto.setUuid(uuid);
		CommandeVente commandeVente = Mapper.toCommandeVenteDto(commandeVenteDto, clientOptional.get(),utilisateurOptional.get(),boutiqueOptional.get());
		CommandeVente commandeVenteSave = commandeVenteRepository.save(commandeVente);
		response=Mapper.toCommandeVente(commandeVenteSave);
		response.setCode(Exception.succes);
		response.setMessage("Modification effectuer avec success");
		return response;
	}

	@Override
	public CommandeVenteDto supprimer(String uuid) {
		CommandeVenteDto response = new CommandeVenteDto();
		if (uuid == null) {
			response.setCode(Exception.error);
			response.setMessage("L'Id ne peut pas etre nul");
			return response;
		}
		Optional<CommandeVente> commandeVenteOptionel = commandeVenteRepository.findById(uuid);
		if (!commandeVenteOptionel.isPresent()) {
			response.setCode(Exception.error);
			response.setMessage("Cette commande vente n'exist pas");
			return response;
		}
		
		commandeVenteRepository.deleteById(uuid);
		response.setCode(Exception.succes);
		response.setMessage("Suppression effectuée avec success");
		return response;
		
	}

	@Override
	public List<CommandeVenteDto> findAll() {
		List<CommandeVente> listeCommandeVente = commandeVenteRepository.findAll();
		List<CommandeVenteDto> listeCommandeVenteDto = new ArrayList<>(); 
		listeCommandeVente.forEach(val->{
			listeCommandeVenteDto.add(Mapper.toCommandeVente(val));
		});
		return listeCommandeVenteDto;
	}

	@Override
	public CommandeVenteDto getById(String uuid) {
		CommandeVenteDto response = new CommandeVenteDto();
		if(uuid == null) {
			response.setCode(Exception.error);
			response.setMessage("L'id ne doit pas etre null");
			return response;
		}
		Optional<CommandeVente> commandeVenteOptionel = commandeVenteRepository.findById(uuid);
		if(!commandeVenteOptionel.isPresent()) {
			response.setCode(Exception.error);
			response.setMessage("Cette commande vente n'exist pas");
			return response;
		}
		return Mapper.toCommandeVente(commandeVenteOptionel.get());
	}

}
