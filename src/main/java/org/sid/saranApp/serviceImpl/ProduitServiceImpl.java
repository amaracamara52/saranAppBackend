package org.sid.saranApp.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.sid.saranApp.dto.ProduitDto;
import org.sid.saranApp.exception.Exception;
import org.sid.saranApp.mapper.Mapper;
import org.sid.saranApp.model.Boutique;
import org.sid.saranApp.model.LivraisonCommandeFournisseur;
import org.sid.saranApp.model.Produit;
import org.sid.saranApp.model.Utilisateur;
import org.sid.saranApp.repository.BoutiqueRepository;
import org.sid.saranApp.repository.LivraisonCommandeFournisseurRepository;
import org.sid.saranApp.repository.ProduitRepository;
import org.sid.saranApp.repository.UtilisateurRepository;
import org.sid.saranApp.service.ProduitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ProduitServiceImpl implements ProduitService{
	
	@Autowired
	private ProduitRepository produitRepository;
	@Autowired
	private LivraisonCommandeFournisseurRepository livraisonCommandeFournisseurRepository;
	@Autowired
	private UtilisateurRepository utilisateurRepository;
	@Autowired
	private BoutiqueRepository boutiqueRepository;
	
	 Logger logger = LoggerFactory.getLogger(ProduitServiceImpl.class);
	
	@Override
	public ProduitDto add(ProduitDto produitDto) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		logger.info("hello {}",auth.getName());
		ProduitDto response = new ProduitDto();
		if(produitDto == null) {
			response.setCode(Exception.error);
			response.setMessage("Veuillez renseigner les champs");
			return response;
		}
		Optional<LivraisonCommandeFournisseur> livraisonCommandeFournisseurOptional = livraisonCommandeFournisseurRepository.findById(produitDto.getUuidLivraisonCommandeFournisseur());
		if(!livraisonCommandeFournisseurOptional.isPresent()) {
			response.setCode(Exception.error);
			response.setMessage("Cette livraison n'exist pas");
			return response;
		}
		Optional<Utilisateur> utilisateurOptional = utilisateurRepository.findById(produitDto.getUuidUtilisateur());
		if(!utilisateurOptional.isPresent()) {
			response.setCode(Exception.error);
			response.setMessage("Cet Utilisateur n'exist pas");
			return response;
		}
		Optional<Boutique> boutiqueOptional = boutiqueRepository.findById(produitDto.getUuidBoutique());
		if(!utilisateurOptional.isPresent()) {
			response.setCode(Exception.error);
			response.setMessage("Cette Boutique n'exist pas");
			return response;
		}
		
		
		Produit produit = Mapper.toProduitDto(produitDto, livraisonCommandeFournisseurOptional.get(), boutiqueOptional.get(), utilisateurOptional.get());
		Produit produitSave = produitRepository.save(produit);
		response=Mapper.toProduit(produitSave);
		response.setCode(Exception.succes);
		response.setMessage("Enregistrement effectuer avec success");
		return response;
	}

	@Override
	public ProduitDto update(ProduitDto produitDto, String uuid) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		ProduitDto response = new ProduitDto();
		if (produitDto == null || uuid == null) {
			response.setCode(Exception.error);
			response.setMessage("Veuillez reinseigner les champs");
			return response;
		}
		
		Optional<Produit> produitOptional = produitRepository.findById(uuid);
		if(!produitOptional.isPresent()) {
			response.setCode(Exception.error);
			response.setMessage("Ce produit n'exist pas");
			return response;
		}
		Optional<LivraisonCommandeFournisseur> livraisonCommandeFournisseurOptional = livraisonCommandeFournisseurRepository.findById(produitDto.getUuidLivraisonCommandeFournisseur());
		if(!livraisonCommandeFournisseurOptional.isPresent()) {
			response.setCode(Exception.error);
			response.setMessage("Cette livraison n'exist pas");
			return response;
		}
		Optional<Utilisateur> utilisateurOptional = utilisateurRepository.findById(produitDto.getUuidUtilisateur());
		if(!utilisateurOptional.isPresent()) {
			response.setCode(Exception.error);
			response.setMessage("Cet Utilisateur n'exist pas");
			return response;
		}
		Optional<Boutique> boutiqueOptional = boutiqueRepository.findById(produitDto.getUuidBoutique());
		if(!utilisateurOptional.isPresent()) {
			response.setCode(Exception.error);
			response.setMessage("Cette Boutique n'exist pas");
			return response;
		}
		
		
		produitDto.setUuid(uuid);
		Produit produit = Mapper.toProduitDto(produitDto, livraisonCommandeFournisseurOptional.get(), boutiqueOptional.get(), utilisateurOptional.get());
		Produit produitSave = produitRepository.save(produit);
		response=Mapper.toProduit(produitSave);
		response.setCode(Exception.succes);
		response.setMessage("La modification à été effectuée avec success");
		return response;
	}

	@Override
	public ProduitDto supprimer(String uuid) {
		ProduitDto response = new ProduitDto();
		if (uuid == null) {
			response.setCode(Exception.error);
			response.setMessage("L'Id ne peut pas etre nul");
			return response;
		}
		Optional<Produit> produitOptional = produitRepository.findById(uuid);
		if(!produitOptional.isPresent()) {
			response.setCode(Exception.error);
			response.setMessage("Ce produit n'exist pas");
			return response;
		}
		
		produitRepository.deleteById(uuid);
		response.setCode(Exception.succes);
		response.setMessage("Suppression effectuée avec success");
		return response;
		
	}

	@Override
	public List<ProduitDto> findAll() {
		List<Produit> listeProduit = produitRepository.findAll();
		List<ProduitDto> listeProduitDto = new ArrayList<>(); 
		listeProduit.forEach(val->{
			listeProduitDto.add(Mapper.toProduit(val));
		});
		return listeProduitDto;
	}

	@Override
	public ProduitDto getById(String uuid) {
		ProduitDto response = new ProduitDto();
		if(uuid == null) {
			response.setCode(Exception.error);
			response.setMessage("L'id ne doit pas etre null");
			return response;
		}
		Optional<Produit> produitOptionel = produitRepository.findById(uuid);
		if (!produitOptionel.isPresent()) {
			response.setCode(Exception.error);
			response.setMessage("Ce client n'exist pas");
			return response;
		}
	
		return Mapper.toProduit(produitOptionel.get());

	}

}
