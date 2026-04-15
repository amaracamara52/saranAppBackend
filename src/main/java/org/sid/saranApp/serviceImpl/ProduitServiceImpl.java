package org.sid.saranApp.serviceImpl;

import org.sid.saranApp.dto.CaracteristiqueProduitDto;
import org.sid.saranApp.dto.PageDataDto;
import org.sid.saranApp.dto.ProduitDto;
import org.sid.saranApp.dto.ProduitStoredDto;
import org.sid.saranApp.dto.version.ProduitStockDto;
import org.sid.saranApp.exception.Exception;
import org.sid.saranApp.mapper.Mapper;
import org.sid.saranApp.model.*;
import org.sid.saranApp.repository.*;
import org.sid.saranApp.service.ApprovisionnementAutomatiqueService;
import org.sid.saranApp.service.ProduitService;
import org.sid.saranApp.service.StockUniteVenteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

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
	@Autowired
	private CaracteristiqueProduitRepository caracteristiqueProduitRepository;
	@Autowired
	private ArticleRepository articleRepository;
	@Autowired
	private FournisseurRepository fournisseurRepository;
	@Autowired
	private StoredFileRepository storedFileRepository;
	@Autowired
	private ProduitStoredRepository produitStoredRepository;

	@Autowired
	private ApprovisionnementAutomatiqueService approvisionnementAutomatiqueService;
	@Autowired
	private StockUniteVenteService stockUniteVenteService;


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
		Optional<Article> articleOptional = articleRepository
				.findById(produitDto.getUuidArticle());
		if (!articleOptional.isPresent()) {
			response.setCode(Exception.error);
			response.setMessage("Cet article n'exist pas");
			return response;
		}

		Optional<Fournisseur> fournisseurOptional = fournisseurRepository
				.findById(produitDto.getUuidFournisseur());
		if (!articleOptional.isPresent()) {
			response.setCode(Exception.error);
			response.setMessage("Cet fournisseur n'exist pas");
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

		Produit produit = Mapper.toProduitDto(produitDto);
				produit.setBoutique(boutiqueOptional.get());
				produit.setUtilisateur(utilisateurOptional.get());
				produit.setArticle(articleOptional.get());
				produit.setFournisseur(fournisseurOptional.get());
		Produit produitSave = produitRepository.save(produit);
		
		logger.info("hello {}",produitDto.getCaracteristiqueArticleDtos().size());
		
		if(produitDto.getCaracteristiqueArticleDtos().size() != 0) {
			List<CaracteristiqueProduitDto> caracteristiqueProduitDtos = produitDto.getCaracteristiqueArticleDtos();
			for (Iterator iterator = caracteristiqueProduitDtos.iterator(); iterator.hasNext();) {
				CaracteristiqueProduitDto caracteristiqueProduitDto = (CaracteristiqueProduitDto) iterator.next();
				
				CaracteristiqueProduit caracteristiqueProduit = caracteristiqueProduitRepository.findById(caracteristiqueProduitDto.getUuid()).orElseThrow(null);

				caracteristiqueProduit.setLibelle(caracteristiqueProduitDto.getLibelle());
				caracteristiqueProduit.setValue(caracteristiqueProduitDto.getValue());
				
				logger.info("value {}",caracteristiqueProduitDto.getValue());
				
				caracteristiqueProduitRepository.save(caracteristiqueProduit);
			}
		}


		if(!produitDto.getProduitStoredDtos().isEmpty()){
			for(ProduitStoredDto produitStored:produitDto.getProduitStoredDtos()){
				StoredFile storedFile = storedFileRepository.findById(produitStored.getUuidStored()).orElseThrow(null);
				ProduitStored produiStored = new ProduitStored();
				produiStored.setProduit(produitSave);
				produiStored.setStoredFile(storedFile);
				produiStored = produitStoredRepository.save(produiStored);
			}
		}
		
		response = Mapper.toProduit(produitSave, stockUniteVenteService);
		response.setCode(Exception.succes);
		response.setMessage("Enregistrement effectuer avec success");
		return response;
	}

	@Override
	public List<ProduitStockDto> findAll() {
		List<Produit> listeProduit = produitRepository.listeProduits(utilisateurServiceImpl.getCurentUtilisateur().getBoutique().getUuid());
		List<ProduitStockDto> listeProduitDto = new ArrayList<>();
		listeProduit.forEach(val -> {
			listeProduitDto.add(Mapper.toProduitStockDto(val, stockUniteVenteService));
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



		return Mapper.toProduit(produitOptionel.get(), stockUniteVenteService);

	}

	/**
	 * Récupère la liste des produits avec un stock inférieur ou égal à 10 unités de base
	 * @return Liste des produits avec stock faible
	 */
	@Override
	public List<ProduitDto> listeStockInferieurA5() {
		try {
			Utilisateur user = utilisateurServiceImpl.getCurentUtilisateur();
			if (user == null || user.getBoutique() == null) {
				logger.warn("Utilisateur ou boutique non trouvé");
				return new ArrayList<>();
			}
			
			List<Produit> produits = produitRepository.findByBoutique(user.getBoutique());
			if (produits == null || produits.isEmpty()) {
				return new ArrayList<>();
			}
			
			List<ProduitDto> produitDtos = new ArrayList<>();
			
			// Filtrer les produits avec stock <= 10 en utilisant StockUniteVenteService
			for (Produit produit : produits) {
				try {
					int quantiteTotale = stockUniteVenteService.calculateTotalBaseQuantity(produit);
					if (quantiteTotale <= 10) {
						produitDtos.add(Mapper.toProduit(produit, stockUniteVenteService));
					}
				} catch (java.lang.Exception e) {
					logger.error("Erreur lors du calcul du stock pour le produit {}: {}", 
						produit.getUuid(), e.getMessage());
				}
			}
			return produitDtos;
		} catch (java.lang.Exception e) {
			logger.error("Erreur lors de la récupération des produits avec stock faible: {}", e.getMessage());
			return new ArrayList<>();
		}
	}

	@Override
	public List<ProduitDto> listeStockPerime() {
		// TODO Auto-generated method stub
		LocalDate dateAujourdhui = LocalDate.now();
		Utilisateur user = utilisateurServiceImpl.getCurentUtilisateur();
		List<Produit> produits = produitRepository.findProduitPerime(dateAujourdhui, user.getBoutique().getUuid());
		List<ProduitDto> produitDtos = new ArrayList<ProduitDto>();
		produits.forEach(produit -> produitDtos.add(Mapper.toProduit(produit, stockUniteVenteService)));
		return produitDtos;
	}

	@Override
	public List<ProduitDto> listeStockPerimeDans3mois() {
		// TODO Auto-generated method stub
		LocalDate dateAujourdhui = LocalDate.now();
		Utilisateur user = utilisateurServiceImpl.getCurentUtilisateur();
		List<Produit> produits = produitRepository.findProduitPerimeBy3mois(dateAujourdhui, user.getBoutique().getUuid());
		List<ProduitDto> produitDtos = new ArrayList<ProduitDto>();
		produits.forEach(produit -> produitDtos.add(Mapper.toProduit(produit, stockUniteVenteService)));
		return produitDtos;
	}

	@Override
	public List<ProduitDto> listeTopStock(int limit) {
		// TODO Auto-generated method stub
		Utilisateur user = utilisateurServiceImpl.getCurentUtilisateur();
//		List<LigneDto> ligneCommandes = ligneCommandeRepository.topCommandeVenteDto(limit,
//				user.getBoutiquePrincipale().getUuid());
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
			response.setMessage("L'Id ne peut pas etre null");
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

		// Les prix et quantités sont maintenant gérés via StockUniteVente
		// Mettre à jour les prix dans StockUniteVente si nécessaire
		if (produitDto.getPrixAchat() > 0 || produitDto.getPrixVente() > 0) {
			List<org.sid.saranApp.model.StockUniteVente> stocks = stockUniteVenteService.getStocksForProduit(produit);
			for (org.sid.saranApp.model.StockUniteVente stock : stocks) {
				if (produitDto.getPrixAchat() > 0) {
					stock.setPrice(produitDto.getPrixAchat());
				}
			}
		}
		
		produit.setDatePeremption(produitDto.getDatePeremption());

		produit.setEmplacement(etagereRayon);
		//produit.setLivraisonCommandeFournisseur(produit.getLivraisonCommandeFournisseur());
		produit.setUtilisateur(utilisateurServiceImpl.getCurentUtilisateur());
		produit.setBoutique(utilisateurServiceImpl.getCurentUtilisateur().getBoutique());
		produit.setFinish(true);
		produit = produitRepository.save(produit);
		
		
        logger.info("hello {}",produitDto.getCaracteristiqueArticleDtos().size());
		
		if(produitDto.getCaracteristiqueArticleDtos().size() != 0) {
			List<CaracteristiqueProduitDto> caracteristiqueProduitDtos = produitDto.getCaracteristiqueArticleDtos();
			for (Iterator iterator = caracteristiqueProduitDtos.iterator(); iterator.hasNext();) {
				CaracteristiqueProduitDto caracteristiqueProduitDto = (CaracteristiqueProduitDto) iterator.next();
				
				CaracteristiqueProduit caracteristiqueProduit = caracteristiqueProduitRepository.findById(caracteristiqueProduitDto.getUuid()).orElseThrow(null);
				
				caracteristiqueProduit.setValue(caracteristiqueProduitDto.getValue());
				
				logger.info("value {}",caracteristiqueProduitDto.getValue());
				
				caracteristiqueProduitRepository.save(caracteristiqueProduit);
			}
		}

		if(!produitDto.getProduitStoredDtos().isEmpty()){
			for (ProduitStoredDto produitStored: produitDto.getProduitStoredDtos()){

			}
		}
		return Mapper.toProduit(produit, stockUniteVenteService);
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
			produits.forEach(produit -> produitDtos.add(Mapper.toProduit(produit, stockUniteVenteService)));
		}
		
		if(key == null ) {
			
			produits = produitRepository.listeProduit(pageable);
			produits.forEach(produit -> produitDtos.add(Mapper.toProduit(produit, stockUniteVenteService)));
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

	/**
	 * Récupère la liste des produits disponibles à la vente
	 * (stock total en unité de base &gt; 0)
	 * @return Liste des produits disponibles à la vente
	 */
	@Override
	public List<ProduitDto> listeProduitAVendre() {
		try {
			Utilisateur utilisateur = utilisateurServiceImpl.getCurentUtilisateur();
			if (utilisateur == null || utilisateur.getBoutique() == null) {
				logger.warn("Utilisateur ou boutique non trouvé");
				return new ArrayList<>();
			}
			
			Boutique boutique = utilisateur.getBoutique();
			List<Produit> listeProduit = produitRepository.findByBoutique(boutique);
			
			if (listeProduit == null || listeProduit.isEmpty()) {
				return new ArrayList<>();
			}
			
			List<ProduitDto> listeProduitDto = new ArrayList<>();
			
			// Point de vente : tout produit avec stock > 0 (le flag isFinish n’est pas renseigné à la création catalogue).
			for (Produit produit : listeProduit) {
				try {
					int quantiteTotale = stockUniteVenteService.calculateTotalBaseQuantity(produit);
					if (quantiteTotale > 0) {
						listeProduitDto.add(Mapper.toProduit(produit, stockUniteVenteService));
					}
				} catch (java.lang.Exception e) {
					logger.error("Erreur lors du traitement du produit {}: {}", 
						produit.getUuid(), e.getMessage());
				}
			}
			return listeProduitDto;
		} catch (java.lang.Exception e) {
			logger.error("Erreur lors de la récupération des produits à vendre: {}", e.getMessage());
			return new ArrayList<>();
		}
	}

	@Override
	public void delete(String uuid) {
		// TODO Auto-generated method stub
		produitRepository.deleteById(uuid);
	}

	/**
	 * Vérifie si le stock d'un produit a atteint le seuil d'alerte après une vente
	 * et déclenche l'approvisionnement automatique si nécessaire
	 * @param uuidProduit UUID du produit à vérifier
	 */
	@Override
	public void verifierApprovisionnementApresVente(String uuidProduit) {
		if (uuidProduit == null || uuidProduit.trim().isEmpty()) {
			logger.warn("UUID du produit invalide pour la vérification d'approvisionnement");
			return;
		}
		
		try {
			logger.info("Vérification de l'approvisionnement automatique après vente pour le produit: {}", uuidProduit);
			
			// Récupérer le produit
			Produit produit = produitRepository.findById(uuidProduit)
				.orElseThrow(() -> new RuntimeException("Produit non trouvé avec l'UUID: " + uuidProduit));
			
			if (produit.getBoutique() == null) {
				logger.warn("Le produit {} n'a pas de boutique associée", uuidProduit);
				return;
			}
			
			// Vérifier si l'approvisionnement automatique est activé pour la boutique
			Boutique boutique = produit.getBoutique();
			if (!boutique.isApprovisionnementAutomatique()) {
				logger.debug("Approvisionnement automatique désactivé pour la boutique: {}", 
					boutique.getLibelleBoutique());
				return;
			}
			
			// Calculer la quantité totale en unité de base
			int quantiteTotale = stockUniteVenteService.calculateTotalBaseQuantity(produit);
			
			// Vérifier si le stock a atteint le seuil d'alerte
			if (quantiteTotale <= boutique.getSeuilAlerteStock()) {
				logger.info("Stock atteint le seuil d'alerte pour le produit: {} - Stock: {} - Seuil: {}", 
					produit.getArticle() != null ? produit.getArticle().getLibelle() : "N/A", 
					quantiteTotale, boutique.getSeuilAlerteStock());
				
				// Déclencher l'approvisionnement automatique
				try {
					approvisionnementAutomatiqueService.creerCommandeAutomatique(
						produit, boutique.getQuantiteACommander());
					
					logger.info("Commande automatique créée pour le produit: {} - Quantité: {}", 
						produit.getArticle() != null ? produit.getArticle().getLibelle() : "N/A", 
						boutique.getQuantiteACommander());
				} catch (java.lang.Exception e) {
					logger.error("Erreur lors de la création de la commande automatique pour le produit {}: {}", 
						uuidProduit, e.getMessage());
				}
			} else {
				logger.debug("Stock suffisant pour le produit: {} - Stock: {} - Seuil: {}", 
					produit.getArticle() != null ? produit.getArticle().getLibelle() : "N/A", 
					quantiteTotale, boutique.getSeuilAlerteStock());
			}
			
		} catch (RuntimeException e) {
			logger.error("Erreur lors de la récupération du produit {}: {}", uuidProduit, e.getMessage());
		} catch (java.lang.Exception e) {
			logger.error("Erreur inattendue lors de la vérification de l'approvisionnement automatique après vente pour le produit {}: {}", 
				uuidProduit, e.getMessage(), e);
		}
	}
}
