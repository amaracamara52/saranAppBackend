package org.sid.saranApp.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.sid.saranApp.dto.LigneCommandeDto;
import org.sid.saranApp.exception.Exception;
import org.sid.saranApp.mapper.Mapper;
import org.sid.saranApp.model.Boutique;
import org.sid.saranApp.model.CommandeVente;
import org.sid.saranApp.model.LigneCommande;
import org.sid.saranApp.model.Produit;
import org.sid.saranApp.model.Utilisateur;
import org.sid.saranApp.repository.BoutiqueRepository;
import org.sid.saranApp.repository.CommandeVenteRepository;
import org.sid.saranApp.repository.LigneCommandeRepository;
import org.sid.saranApp.repository.ProduitRepository;
import org.sid.saranApp.repository.UtilisateurRepository;
import org.sid.saranApp.service.LigneCommandeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class LigneCommandeServiceImpl implements LigneCommandeService{
	
	@Autowired
	private ProduitRepository produitRepository;
	
	@Autowired
	private CommandeVenteRepository commandeVenteRepository;
	@Autowired
	private LigneCommandeRepository ligneCommandeRepository;
	@Autowired
	private UtilisateurRepository utilisateurRepository;
	@Autowired
	private BoutiqueRepository boutiqueRepository;
	
	 Logger logger = LoggerFactory.getLogger(LigneCommandeServiceImpl.class);
	
	@Override
	public LigneCommandeDto add(LigneCommandeDto ligneCommandeDto) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		logger.info("hello {}",auth.getName());
		LigneCommandeDto response = new LigneCommandeDto();
		if(ligneCommandeDto == null) {
			response.setCode(Exception.error);
			response.setMessage("Veuillez renseigner les champs");
			return response;
		}
		Optional<Produit> produitOptional = produitRepository.findById(ligneCommandeDto.getUuidProduit());
		if(!produitOptional.isPresent()) {
			response.setCode(Exception.error);
			response.setMessage("Ce produit n'exist pas");
			return response;
		}
		Optional<Utilisateur> utilisateurOptional = utilisateurRepository.findById(ligneCommandeDto.getUuidUtilisateur());
		if(!utilisateurOptional.isPresent()) {
			response.setCode(Exception.error);
			response.setMessage("Cet Utilisateur n'exist pas");
			return response;
		}
		Optional<Boutique> boutiqueOptional = boutiqueRepository.findById(ligneCommandeDto.getUuidBoutique());
		if(!utilisateurOptional.isPresent()) {
			response.setCode(Exception.error);
			response.setMessage("Cette Boutique n'exist pas");
			return response;
		}
		
		Optional<CommandeVente> commandeVenteOptional = commandeVenteRepository.findById(ligneCommandeDto.getUuidProduit());
		if(!produitOptional.isPresent()) {
			response.setCode(Exception.error);
			response.setMessage("Ce commandeVente n'exist pas");
			return response;
		}
		LigneCommande ligneCommande = Mapper.toLigneCommandeDto(ligneCommandeDto, produitOptional.get(), commandeVenteOptional.get(), utilisateurOptional.get(), boutiqueOptional.get());
		LigneCommande ligneCommandeSave = ligneCommandeRepository.save(ligneCommande);
		response=Mapper.toLigneCommande(ligneCommandeSave);
		response.setCode(Exception.succes);
		response.setMessage("Enregistrement effectuer avec success");
		return response;
	}

	@Override
	public LigneCommandeDto update(LigneCommandeDto ligneCommandeDto, String uuid) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		LigneCommandeDto response = new LigneCommandeDto();
		if(ligneCommandeDto == null) {
			response.setCode(Exception.error);
			response.setMessage("Veuillez renseigner les champs");
			return response;
		}
		Optional<Produit> produitOptional = produitRepository.findById(ligneCommandeDto.getUuidProduit());
		if(!produitOptional.isPresent()) {
			response.setCode(Exception.error);
			response.setMessage("Ce produit n'exist pas");
			return response;
		}
		Optional<CommandeVente> commandeVenteOptional = commandeVenteRepository.findById(ligneCommandeDto.getUuidProduit());
		if(!produitOptional.isPresent()) {
			response.setCode(Exception.error);
			response.setMessage("Ce commandeVente n'exist pas");
			return response;
		}
		Optional<Utilisateur> utilisateurOptional = utilisateurRepository.findById(ligneCommandeDto.getUuidUtilisateur());
		if(!utilisateurOptional.isPresent()) {
			response.setCode(Exception.error);
			response.setMessage("Cet Utilisateur n'exist pas");
			return response;
		}
		Optional<Boutique> boutiqueOptional = boutiqueRepository.findById(ligneCommandeDto.getUuidBoutique());
		if(!utilisateurOptional.isPresent()) {
			response.setCode(Exception.error);
			response.setMessage("Cette Boutique n'exist pas");
			return response;
		}
		
		
		ligneCommandeDto.setUuid(uuid);
		LigneCommande ligneCommande = Mapper.toLigneCommandeDto(ligneCommandeDto, produitOptional.get(), commandeVenteOptional.get(), utilisateurOptional.get(), boutiqueOptional.get());
		LigneCommande ligneCommandeSave = ligneCommandeRepository.save(ligneCommande);
		response=Mapper.toLigneCommande(ligneCommandeSave);
		response.setCode(Exception.succes);
		response.setMessage("La Modfication a été effectuée avec success");
		return response;
	}

	@Override
	public LigneCommandeDto supprimer(String uuid) {
		LigneCommandeDto response = new LigneCommandeDto();
		if (uuid == null) {
			response.setCode(Exception.error);
			response.setMessage("L'Id ne peut pas etre nul");
			return response;
		}
		Optional<LigneCommande> ligneCommandeOptionel = ligneCommandeRepository.findById(uuid);
		if (!ligneCommandeOptionel.isPresent()) {
			response.setCode(Exception.error);
			response.setMessage("Cette ligne commande n'exist pas");
			return response;
		}
		
		ligneCommandeRepository.deleteById(uuid);
		response.setCode(Exception.succes);
		response.setMessage("Suppression effectuée avec success");
		return response;
		
	}

	@Override
	public List<LigneCommandeDto> findAll() {
		List<LigneCommande> listeLigneCommande = ligneCommandeRepository.findAll();
		List<LigneCommandeDto> listeLigneCommandeDto = new ArrayList<>(); 
		listeLigneCommande.forEach(val->{
			listeLigneCommandeDto.add(Mapper.toLigneCommande(val));
		});
		return listeLigneCommandeDto;
	}

	@Override
	public LigneCommandeDto getById(String uuid) {
			LigneCommandeDto response = new LigneCommandeDto();
			if (uuid == null) {
				response.setCode(Exception.error);
				response.setMessage("L'Id ne peut pas etre nul");
				return response;
			}
			Optional<LigneCommande> ligneCommandeOptionel = ligneCommandeRepository.findById(uuid);
			if (!ligneCommandeOptionel.isPresent()) {
				response.setCode(Exception.error);
				response.setMessage("Cette ligne commande n'exist pas");
				return response;
			}
			return Mapper.toLigneCommande(ligneCommandeOptionel.get());
	}

}
