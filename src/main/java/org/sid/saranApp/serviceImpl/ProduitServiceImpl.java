package org.sid.saranApp.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.sid.saranApp.dto.ProduitDto;
import org.sid.saranApp.dto.PageDataDto;
import org.sid.saranApp.dto.ProduitDto;
import org.sid.saranApp.exception.Exception;
import org.sid.saranApp.mapper.Mapper;
import org.sid.saranApp.model.Produit;
import org.sid.saranApp.model.Boutique;
import org.sid.saranApp.model.EtagereRayon;
import org.sid.saranApp.model.LivraisonCommandeFournisseur;
import org.sid.saranApp.model.Produit;
import org.sid.saranApp.model.Utilisateur;
import org.sid.saranApp.repository.BoutiqueRepository;
import org.sid.saranApp.repository.EtagereRayonRepository;
import org.sid.saranApp.repository.LigneCommandeRepository;
import org.sid.saranApp.repository.LivraisonCommandeFournisseurRepository;
import org.sid.saranApp.repository.ProduitRepository;
import org.sid.saranApp.repository.UtilisateurRepository;
import org.sid.saranApp.service.ProduitService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ProduitServiceImpl implements ProduitService {

	@Autowired
	private ProduitRepository produitRepository;
	@Autowired
	private LivraisonCommandeFournisseurRepository livraisonCommandeFournisseurRepository;
	@Autowired
	private UtilisateurRepository utilisateurRepository;
	@Autowired
	private BoutiqueRepository boutiqueRepository;
	@Autowired
	private UtilisateurServiceImpl utilisateurServiceImpl;
	@Autowired
	private LigneCommandeRepository ligneCommandeRepository;
	@Autowired
	private EtagereRayonRepository etagereRayonRepository;

	Logger logger = LoggerFactory.getLogger(ProduitServiceImpl.class);

	@Override
	public ProduitDto add(ProduitDto produitDto) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		logger.info("hello {}", auth.getName());
		ProduitDto response = new ProduitDto();
		if (produitDto == null) {
			response.setCode(Exception.error);
			response.setMessage("Veuillez renseigner les champs");
			return response;
		}
		Optional<LivraisonCommandeFournisseur> livraisonCommandeFournisseurOptional = livraisonCommandeFournisseurRepository
				.findById(produitDto.getUuidLivraisonCommandeFournisseur());
		if (!livraisonCommandeFournisseurOptional.isPresent()) {
			response.setCode(Exception.error);
			response.setMessage("Cette livraison n'exist pas");
			return response;
		}
		Optional<Utilisateur> utilisateurOptional = utilisateurRepository.findById(produitDto.getUuidUtilisateur());
		if (!utilisateurOptional.isPresent()) {
			response.setCode(Exception.error);
			response.setMessage("Cet Utilisateur n'exist pas");
			return response;
		}
		Optional<Boutique> boutiqueOptional = boutiqueRepository.findById(produitDto.getUuidBoutique());
		if (!utilisateurOptional.isPresent()) {
			response.setCode(Exception.error);
			response.setMessage("Cette Boutique n'exist pas");
			return response;
		}

		Produit produit = Mapper.toProduitDto(produitDto, livraisonCommandeFournisseurOptional.get(),
				boutiqueOptional.get(), utilisateurOptional.get());
		Produit produitSave = produitRepository.save(produit);
		response = Mapper.toProduit(produitSave);
		response.setCode(Exception.succes);
		response.setMessage("Enregistrement effectuer avec success");
		return response;
	}

	@Override
	public List<ProduitDto> findAll() {
		List<Produit> listeProduit = produitRepository.findAll();
		List<ProduitDto> listeProduitDto = new ArrayList<>();
		listeProduit.forEach(val -> {
			listeProduitDto.add(Mapper.toProduit(val));
		});
		return listeProduitDto;
	}

	@Override
	public ProduitDto getById(String uuid) {
		ProduitDto response = new ProduitDto();
		if (uuid == null) {
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

	@Override
	public List<ProduitDto> listeStockInferieurA5() {
		// TODO Auto-generated method stub
		Utilisateur user = utilisateurServiceImpl.getCurentUtilisateur();
		List<Produit> produits = produitRepository.listeStockInferieurA5(user.getBoutique().getUuid());
		List<ProduitDto> produitDtos = new ArrayList<ProduitDto>();
		produits.forEach(produit -> produitDtos.add(Mapper.toProduit(produit)));
		return produitDtos;
	}

	@Override
	public List<ProduitDto> listeStockPerime() {
		// TODO Auto-generated method stub
		Utilisateur user = utilisateurServiceImpl.getCurentUtilisateur();
		List<Produit> produits = produitRepository.findProduitPerime(new Date(), user.getBoutique().getUuid());
		List<ProduitDto> produitDtos = new ArrayList<ProduitDto>();
		produits.forEach(produit -> produitDtos.add(Mapper.toProduit(produit)));
		return produitDtos;
	}

	@Override
	public List<ProduitDto> listeStockPerimeDans3mois() {
		// TODO Auto-generated method stub
		Utilisateur user = utilisateurServiceImpl.getCurentUtilisateur();
		List<Produit> produits = produitRepository.findProduitPerimeBy3mois(new Date(), user.getBoutique().getUuid());
		List<ProduitDto> produitDtos = new ArrayList<ProduitDto>();
		produits.forEach(produit -> produitDtos.add(Mapper.toProduit(produit)));
		return produitDtos;
	}

	@Override
	public List<ProduitDto> listeTopStock(int limit) {
		// TODO Auto-generated method stub
		Utilisateur user = utilisateurServiceImpl.getCurentUtilisateur();
//		List<LigneDto> ligneCommandes = ligneCommandeRepository.topCommandeVenteDto(limit,
//				user.getBoutique().getUuid());
		List<ProduitDto> dtos = new ArrayList<ProduitDto>();
//		ligneCommandes.forEach(ligne -> {
//			Produit produit = produitRepository.findById(ligne.getUuid()).orElseThrow(null);
//			ProduitDto dto = Mapper.toProduit(produit);
//			dto.setQuantiteVendu(ligne.getQuantite());
//		});
		return dtos;
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
		if (!produitOptional.isPresent()) {
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
	public ProduitDto update(ProduitDto produitDto, String uuid) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		Produit produit = produitRepository.findById(uuid).orElseThrow(null);

		EtagereRayon etagereRayon = etagereRayonRepository.findById(produitDto.getUuidEmplacement()).orElseThrow(null);

		produit.setPrixAchat(produitDto.getPrixAchat());
		produit.setPrixVente(produitDto.getPrixVente());
		produit.setQuantite(produit.getQuantite());
		produit.setDatePeremption(produitDto.getDatePeremption());

		produit.setEmplacement(etagereRayon);
		produit.setLivraisonCommandeFournisseur(produit.getLivraisonCommandeFournisseur());
		produit.setUtilisateur(utilisateurServiceImpl.getCurentUtilisateur());
		produit.setBoutique(utilisateurServiceImpl.getCurentUtilisateur().getBoutique());
		produit.setFinish(true);
		produit = produitRepository.save(produit);
		return Mapper.toProduit(produit);
	}

	@Override
	public PageDataDto<ProduitDto> listeProduits(int page, int size, String key) {
		// TODO Auto-generated method stub
		String uuidBoutique = utilisateurServiceImpl.getCurentUtilisateur().getBoutique().getUuid();
		PageDataDto<ProduitDto> pageDataDto = new PageDataDto<ProduitDto>();
		List<ProduitDto> produitDtos = new ArrayList<ProduitDto>();
		Pageable pageable = PageRequest.of(page, size);
		
		Page<Produit> produits = null;
		
		if(key != null) {
			produits = produitRepository.listeProduitByLibelle(key, key, pageable);
			produits.forEach(produit -> produitDtos.add(Mapper.toProduit(produit)));
		}
		
		if(key == null ) {
			
			produits = produitRepository.listeProduit(pageable);
			produits.forEach(produit -> produitDtos.add(Mapper.toProduit(produit)));
		}
		
		
		pageDataDto.setData(produitDtos);
		pageDataDto.getPage().setPageNumber(page);
		pageDataDto.getPage().setSize(size);
		pageDataDto.getPage().setTotalElements(produits.getTotalElements());
		pageDataDto.getPage().setTotalPages(produits.getTotalPages());
		return pageDataDto;
	}

	@Override
	public PageDataDto<ProduitDto> listeStockInferieurA5(int page, int size, String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageDataDto<ProduitDto> listeStockPerime(int page, int size, String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageDataDto<ProduitDto> listeStockPerimeDans3mois(int page, int size, String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ProduitDto> listeProduitAVendre() {
		String uuidBoutique = utilisateurServiceImpl.getCurentUtilisateur().getBoutique().getUuid();
		// TODO Auto-generated method stub
		List<Produit> listeProduit = produitRepository.listeStockAVendre(uuidBoutique);
		List<ProduitDto> listeProduitDto = new ArrayList<>();
		listeProduit.forEach(val -> {
			listeProduitDto.add(Mapper.toProduit(val));
		});
		return listeProduitDto;
	}

}
