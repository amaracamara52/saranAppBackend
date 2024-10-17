package org.sid.saranApp.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.sid.saranApp.dto.CaracteristiqueProduitDto;
import org.sid.saranApp.exception.Exception;
import org.sid.saranApp.mapper.Mapper;
import org.sid.saranApp.model.Boutique;
import org.sid.saranApp.model.CaracteristiqueProduit;
import org.sid.saranApp.model.Produit;
import org.sid.saranApp.model.Utilisateur;
import org.sid.saranApp.repository.BoutiqueRepository;
import org.sid.saranApp.repository.CaracteristiqueProduitRepository;
import org.sid.saranApp.repository.ProduitRepository;
import org.sid.saranApp.repository.UtilisateurRepository;
import org.sid.saranApp.service.CaracteristiqueProduitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CaracteristiqueProduitServiceImpl implements CaracteristiqueProduitService{
	
	@Autowired
	private CaracteristiqueProduitRepository caracteristiqueProduitRepository;
	
	@Autowired
	private ProduitRepository produitRepository;
	@Autowired 
	private UtilisateurRepository utilisateurRepository;
	@Autowired
	private BoutiqueRepository boutiqueRepository;
	
	 Logger logger = LoggerFactory.getLogger(CaracteristiqueProduitServiceImpl.class);

	@Override
	public CaracteristiqueProduitDto add(CaracteristiqueProduitDto caracteristiqueProduitDto) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		logger.info("hello {}",auth.getName());
		CaracteristiqueProduitDto response = new CaracteristiqueProduitDto();
		if(caracteristiqueProduitDto == null) {
			response.setCode(Exception.error);
			response.setMessage("Veuillez renseigner les champs");
			return response;
		}
		Optional<Produit> produitOptional = produitRepository.findById(caracteristiqueProduitDto.getUuidProduit());
		if(!produitOptional.isPresent()) {
			response.setCode(Exception.error);
			response.setMessage("Cet produit n'exist pas");
			return response;
		}
		Optional<Utilisateur> utilisateurOptional = utilisateurRepository.findById(caracteristiqueProduitDto.getUuidUtilisateur());
		if(!utilisateurOptional.isPresent()) {
			response.setCode(Exception.error);
			response.setMessage("Cet Utilisateur n'exist pas");
			return response;
		}
		Optional<Boutique> boutiqueOptional = boutiqueRepository.findById(caracteristiqueProduitDto.getUuidBoutique());
		if(!produitOptional.isPresent()) {
			response.setCode(Exception.error);
			response.setMessage("Cette Boutique n'exist pas");
			return response;
		}
		
		
		CaracteristiqueProduit caracteristiqueProduit = Mapper.toCaracteristiqueProduitDto(caracteristiqueProduitDto, produitOptional.get(), utilisateurOptional.get(), boutiqueOptional.get());
		CaracteristiqueProduit caracteristiqueProduitSave = caracteristiqueProduitRepository.save(caracteristiqueProduit);
		response=Mapper.toCaracteristiqueProduit(caracteristiqueProduitSave);
		response.setCode(Exception.succes);
		response.setMessage("Enregistrement effectuer avec success");
		return response;

	}

	@Override
	public CaracteristiqueProduitDto update(CaracteristiqueProduitDto caracteristiqueProduitDto, String uuid) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		CaracteristiqueProduitDto response = new CaracteristiqueProduitDto();
		if (caracteristiqueProduitDto == null || uuid == null) {
			response.setCode(Exception.error);
			response.setMessage("Veuillez reinseigner les champs");
			return response;
		}
		
		Optional<Produit> produitOptional = produitRepository.findById(caracteristiqueProduitDto.getUuidProduit());
		if(!produitOptional.isPresent()) {
			response.setCode(Exception.error);
			response.setMessage("Ce produit n'exist pas");
			return response;
		}
		Optional<Utilisateur> utilisateurOptional = utilisateurRepository.findById(caracteristiqueProduitDto.getUuidUtilisateur());
		if(!produitOptional.isPresent()) {
			response.setCode(Exception.error);
			response.setMessage("Ce produit n'exist pas");
			return response;
		}
		Optional<Boutique> boutiqueOptional = boutiqueRepository.findById(caracteristiqueProduitDto.getUuidBoutique());
		if(!produitOptional.isPresent()) {
			response.setCode(Exception.error);
			response.setMessage("Ce produit n'exist pas");
			return response;
		}
		
		caracteristiqueProduitDto.setUuid(uuid);
		CaracteristiqueProduit caracteristiqueProduit = Mapper.toCaracteristiqueProduitDto(caracteristiqueProduitDto, produitOptional.get(), utilisateurOptional.get(), boutiqueOptional.get());
		CaracteristiqueProduit caracteristiqueSave = caracteristiqueProduitRepository.save(caracteristiqueProduit);
		response=Mapper.toCaracteristiqueProduit(caracteristiqueSave);
		response.setCode(Exception.succes);
		response.setMessage("La modification à été effectuée avec success");
		return response;
	}

	@Override
	public CaracteristiqueProduitDto supprimer(String uuid) {
		CaracteristiqueProduitDto response = new CaracteristiqueProduitDto();
		if (uuid == null) {
			response.setCode(Exception.error);
			response.setMessage("L'Id ne peut pas etre nul");
			return response;
		}
		Optional<CaracteristiqueProduit> caracteristiqueProduitDtoOptionel = caracteristiqueProduitRepository.findById(uuid);
		if (!caracteristiqueProduitDtoOptionel.isPresent()) {
			response.setCode(Exception.error);
			response.setMessage("Ce client n'exist pas");
			return response;
		}
		
		caracteristiqueProduitRepository.deleteById(uuid);
		response.setCode(Exception.succes);
		response.setMessage("Suppression effectuée avec success");
		return response;
		
	}

	@Override
	public List<CaracteristiqueProduitDto> findAll() {
		List<CaracteristiqueProduit> listeCaracteristiqueProduit = caracteristiqueProduitRepository.findAll();
		List<CaracteristiqueProduitDto> listeCaracteristiqueProduitDto = new ArrayList<>(); 
		listeCaracteristiqueProduit.forEach(val->{
			listeCaracteristiqueProduitDto.add(Mapper.toCaracteristiqueProduit(val));
		});
		return listeCaracteristiqueProduitDto;
	}

	@Override
	public CaracteristiqueProduitDto getById(String uuid) {
		CaracteristiqueProduitDto response = new CaracteristiqueProduitDto();
		if(uuid == null) {
			response.setCode(Exception.error);
			response.setMessage("L'id ne doit pas etre null");
			return response;
		}
		Optional<CaracteristiqueProduit> caracteristiqueProduitOptionel = caracteristiqueProduitRepository.findById(uuid);
		if(!caracteristiqueProduitOptionel.isPresent()) {
			response.setCode(Exception.error);
			response.setMessage("Ce Caracteristique n'exist pas");
			return response;
		}
		Optional<CaracteristiqueProduit> caracteristiqueProduitOptional = caracteristiqueProduitRepository.findById(uuid);
		return Mapper.toCaracteristiqueProduit(caracteristiqueProduitOptional.get());
	}
	
}
