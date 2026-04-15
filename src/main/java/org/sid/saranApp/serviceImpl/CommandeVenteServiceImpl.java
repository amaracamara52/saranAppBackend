package org.sid.saranApp.serviceImpl;

import org.sid.saranApp.dto.*;
import org.sid.saranApp.dto.request.CommandeVenteRequestDto;
import org.sid.saranApp.dto.request.ImportVenteHistoriqueLigneDto;
import org.sid.saranApp.dto.request.ImportVenteHistoriqueVenteDto;
import org.sid.saranApp.dto.request.LigneRequestDto;
import org.sid.saranApp.enume.EnumStatutCaisse;
import org.sid.saranApp.enume.EnumStatutTransaction;
import org.sid.saranApp.enume.EnumTypeTransaction;
import org.sid.saranApp.enume.EnumTypeCommande;
import org.sid.saranApp.enume.StatusCommandeVenteEnum;
import org.sid.saranApp.enume.TypeUniteEnum;
import org.sid.saranApp.mapper.Mapper;
import org.sid.saranApp.model.*;
import org.sid.saranApp.repository.*;
import org.sid.saranApp.service.CaisseJournaliereService;
import org.sid.saranApp.service.CommandeVenteService;
import org.sid.saranApp.service.TransactionService;
import org.sid.saranApp.service.ProduitService;
import org.sid.saranApp.service.StockUniteVenteService;
import org.sid.saranApp.service.ClientPartenaireService;
import org.sid.saranApp.service.OperationCaisseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.text.Normalizer;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CommandeVenteServiceImpl implements CommandeVenteService {

	private static final int RESP_ERROR = org.sid.saranApp.exception.Exception.error;
	private static final int RESP_SUCCES = org.sid.saranApp.exception.Exception.succes;

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private CommandeVenteRepository commandeVenteRepository;
	@Autowired
	private UtilisateurServiceImpl utilisateurServiceImpl;
	@Autowired
	private ProduitRepository produitRepository;
	@Autowired
	private LigneCommandeRepository ligneCommandeRepository;
	@Autowired
	private TypeUniteDeVenteRepository typeUniteDeVenteRepository;
	@Autowired
	private LivraisonCommandeVenteRepository livraisonCommandeVenteRepository;
	@Autowired
	private ModePaiementRepository  modePaiementRepository;
	@Autowired
	private ProduitService produitService;

	Logger logger = LoggerFactory.getLogger(CommandeVenteServiceImpl.class);

	@Autowired
	private TransactionService transactionService;
	@Autowired
	private CaisseJournaliereService caisseJournaliereService;
	@Autowired
	private StockUniteVenteService stockUniteVenteService;
	@Autowired
	private ClientPartenaireService clientPartenaireService;
	@Autowired
	private OperationCaisseService operationCaisseService;
	@Autowired
	private ClientPartenaireRepository clientPartenaireRepository;
	@Autowired
	private ArticleRepository articleRepository;
	@Autowired
	private PlatformTransactionManager platformTransactionManager;

	@Override
	public CommandeVenteDto add(CommandeVenteRequestDto commandeVenteDto) {
		logger.info("hello commmande vente {}",caisseJournaliereService.getCaisseActuelle().getStatutCaisse());
		CommandeVenteDto response = new CommandeVenteDto();
		if(caisseJournaliereService.getCaisseActuelle().getStatutCaisse() == EnumStatutCaisse.OUVERT){
			String uuid = UUID.randomUUID().toString().replace("-", "").toUpperCase();
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			logger.info("hello {}", auth.getName());

			CommandeVente commandeVente = new CommandeVente();
			commandeVente.setPaye(false);
			Utilisateur user = utilisateurServiceImpl.getCurentUtilisateur();

			Optional<ModePaiement> modeOpt = resolveModePaiementPourVente(user, commandeVenteDto.getUuidModePaiement());
			if (modeOpt.isEmpty()) {
				response.setMessage("Aucun mode de paiement configuré pour cette boutique.");
				response.setCode(RESP_ERROR);
				return response;
			}
			commandeVente.setModePaiement(modeOpt.get());
			commandeVente.setPaye(true);

			String idClient = commandeVenteDto.getId_client();
			if (idClient != null && !idClient.isBlank()) {
				Optional<Client> clientOptional = clientRepository.findById(idClient.trim());
				if (clientOptional.isPresent()) {
					Client cl = clientOptional.get();
					if (cl.getBoutique() != null && user.getBoutique() != null
						&& !cl.getBoutique().getUuid().equals(user.getBoutique().getUuid())) {
						logger.warn("Vente : client {} ignoré (autre boutique que l'utilisateur courant)", idClient);
					} else {
						commandeVente.setClient(cl);
						logger.info("Vente associée au client {}", idClient);
					}
				} else {
					logger.warn("Vente : uuid client inconnu {}, vente sans client lié", idClient);
				}
			}
			String cmd = prefixNumeroCommandeVente(user.getBoutique()) + "-" + uuid.substring(0, 6);
			//cmd = "CMD-"+ uuid.substring(0, 6);
			commandeVente.setNumeroCommande(cmd);
			commandeVente.setBoutique(user.getBoutique());
			commandeVente.setUtilisateur(user);
			commandeVente.setDatePaiement(new Date());
			commandeVente.setCommandeVenteEnum(commandeVenteDto.getStatus());
			commandeVente.setMontantCommade(commandeVenteDto.getMontantCommande());
			commandeVente.setMontantCommadeImage(commandeVenteDto.getMontantCommande());
			commandeVente.setTypeCommande(commandeVenteDto.getTypeCommande());
			
			// Gestion de la validation pour les commandes en gros
			// Les commandes en détail sont automatiquement validées
			// Les commandes en gros nécessitent une validation par un grossiste
			if (commandeVenteDto.getTypeCommande() != null && 
			    commandeVenteDto.getTypeCommande() == org.sid.saranApp.enume.EnumTypeCommande.EN_GROS) {
				// Vérifier que le client a un compte partenaire pour les commandes en gros
				if (commandeVente.getClient() == null) {
					response.setMessage("Un client est requis pour les commandes en gros");
					response.setCode(RESP_ERROR);
					logger.warn("Tentative de commande en gros sans client");
					return response;
				}
				
				// Vérifier que le client est partenaire de la boutique
				boolean estPartenaire = clientPartenaireService.estClientPartenaire(
					commandeVente.getClient().getUuid(),
					user.getBoutique().getUuid()
				);
				
				if (!estPartenaire) {
					response.setMessage("Le client doit avoir un compte partenaire pour passer des commandes en gros");
					response.setCode(RESP_ERROR);
					logger.warn("Tentative de commande en gros par un client non partenaire: {}", 
						commandeVente.getClient().getUuid());
					return response;
				}
				
				// Commande en gros : nécessite validation
				commandeVente.setValidee(false);
				logger.info("Commande en gros créée par client partenaire, en attente de validation");
			} else {
				// Commande en détail : automatiquement validée
				commandeVente.setValidee(true);
				logger.info("Commande en détail créée et automatiquement validée");
			}
			commandeVente = commandeVenteRepository.save(commandeVente);
			List<LigneRequestDto> ligneCommandeDtos = commandeVenteDto.getLigneCommandeDtos();
			
			// Vérifier qu'il y a des lignes de commande
			if (ligneCommandeDtos == null || ligneCommandeDtos.isEmpty()) {
				response.setMessage("Aucune ligne de commande fournie");
				response.setCode(RESP_ERROR);
				return response;
			}

			// Traiter chaque ligne de commande
			for (Iterator<LigneRequestDto> iterator = ligneCommandeDtos.iterator(); iterator.hasNext();) {
				LigneRequestDto ligneCommandeDto = iterator.next();
				
				if (ligneCommandeDto == null) {
					logger.warn("Ligne de commande null trouvée, ignorée");
					continue;
				}

				Produit produit = null;
				TypeUniteDeVente typeUniteDeVente = null;
				
				try {
					// Récupérer le produit
					produit = produitRepository.findById(ligneCommandeDto.getUuidProduit())
						.orElseThrow(() -> new RuntimeException("Produit non trouvé avec l'UUID: " + 
							ligneCommandeDto.getUuidProduit()));
					
					// Récupérer l'unité de vente
					typeUniteDeVente = typeUniteDeVenteRepository
						.findById(ligneCommandeDto.getUuidTypeUniteDeVente())
						.orElseThrow(() -> new RuntimeException("Unité de vente non trouvée avec l'UUID: " + 
							ligneCommandeDto.getUuidTypeUniteDeVente()));
					
					// Vérifier que la quantité est valide
					if (ligneCommandeDto.getQuantite() <= 0) {
						response.setMessage("La quantité doit être supérieure à 0 pour l'article " + 
							(produit.getArticle() != null ? produit.getArticle().getLibelle() : "N/A"));
						response.setCode(RESP_ERROR);
						continue;
					}
					
					// Vérifier si le stock est suffisant dans l'unité sélectionnée
					int stockDisponible = stockUniteVenteService.getStockForUnit(produit, typeUniteDeVente);
					if (stockDisponible < ligneCommandeDto.getQuantite()) {
						response.setMessage("La quantité de l'article " + 
							(produit.getArticle() != null ? produit.getArticle().getLibelle() : "N/A") + 
							" en " + typeUniteDeVente.getTypeUniteEnum() + 
							" est insuffisante. Stock disponible: " + stockDisponible);
						response.setCode(RESP_ERROR);
						continue;
					}

					// Retirer le stock de l'unité sélectionnée et mettre à jour toutes les autres unités
					boolean stockSuffisant = stockUniteVenteService.removeStockFromUnit(
							produit, 
							typeUniteDeVente, 
							ligneCommandeDto.getQuantite()
					);

					if (!stockSuffisant) {
						response.setMessage("La quantité de l'article " + 
							(produit.getArticle() != null ? produit.getArticle().getLibelle() : "N/A") + 
							" est insuffisante");
						response.setCode(RESP_ERROR);
						continue;
					}
					
					// Sauvegarder le produit avec les quantités mises à jour
					produitRepository.save(produit);

					// Appel de la vérification d'approvisionnement automatique
					try {
						produitService.verifierApprovisionnementApresVente(produit.getUuid());
					} catch (Throwable e) {
						logger.warn("Erreur lors de la vérification d'approvisionnement pour le produit {}: {}", 
							produit.getUuid(), e.getMessage());
						// Ne pas bloquer la vente si la vérification échoue
					}

					// Créer la ligne de commande
					LigneCommande ligne = new LigneCommande();
					ligne.setBoutique(utilisateurServiceImpl.getCurentUtilisateur().getBoutique());
					ligne.setCommandeVente(commandeVente);
					ligne.setProduit(produit);
					ligne.setQuantite(ligneCommandeDto.getQuantite());
					ligne.setTypeUniteDeVente(typeUniteDeVente);
					ligne.setUtilisateur(user);
					ligne = ligneCommandeRepository.save(ligne);

				} catch (IllegalArgumentException e) {
					logger.error("Paramètre invalide lors du traitement de la ligne de commande: {}", e.getMessage());
					response.setMessage("Erreur de validation: " + e.getMessage());
					response.setCode(RESP_ERROR);
					continue;
				} catch (RuntimeException e) {
					logger.error("Erreur lors du traitement de la ligne de commande: {}", e.getMessage());
					response.setMessage("Erreur: " + e.getMessage());
					response.setCode(RESP_ERROR);
					continue;
				} catch (Throwable e) {
					logger.error("Erreur inattendue lors du traitement de la ligne de commande: {}", e.getMessage(), e);
					response.setMessage("Erreur inattendue lors du traitement de la commande");
					response.setCode(RESP_ERROR);
					continue;
				}
			}




			if(commandeVenteDto.getLivraisonCommandeVenteDto() != null){
				String _uuid = UUID.randomUUID().toString().replace("-", "").toUpperCase();
				LivraisonCommandeVente l = new LivraisonCommandeVente();
				l.setCommandeVente(commandeVente);
				l.setDateLivraison(new Date());
				l.setTransporteur(commandeVenteDto.getLivraisonCommandeVenteDto().getTransporteur());
				l.setNumeroSuivi(_uuid.substring(0, 4));
				livraisonCommandeVenteRepository.save(l);
			}

			if(commandeVente.isPaye()){
				TransactionDto transactionDto = new TransactionDto();
				transactionDto.setUuidCommandeVente(commandeVente.getUuid());
				transactionDto.setStatutTransaction(EnumStatutTransaction.VALIDEE);
				transactionDto.setTypeTransaction(EnumTypeTransaction.ENCAISSEMENT);
				transactionDto.setModePaiement(Mapper.toModePaiementDto(commandeVente.getModePaiement()));
				BigDecimal bigDecimal = BigDecimal.valueOf(commandeVente.getMontantCommade());
				transactionDto.setMontant(bigDecimal);
				transactionService.addTransaction(transactionDto);
				
				// Créer automatiquement une opération de caisse pour la vente
				try {
					OperationCaisseDto operationDto = new OperationCaisseDto();
					operationDto.setTypeOperation(EnumTypeTransaction.ENCAISSEMENT);
					operationDto.setMontant(bigDecimal);
					operationDto.setLibelle("Vente - " + commandeVente.getNumeroCommande());
					operationDto.setTypeDetail("VENTE");
					operationDto.setUuidCommandeVente(commandeVente.getUuid());
					operationDto.setCommentaire("Encaissement automatique pour vente " + commandeVente.getNumeroCommande());
					
					// Si c'est une commande en gros, lier au client partenaire
					if (commandeVente.getTypeCommande() == EnumTypeCommande.EN_GROS && commandeVente.getClient() != null) {
						java.util.Optional<ClientPartenaire> clientPartenaireOpt = clientPartenaireRepository
							.findByClientAndBoutique(commandeVente.getClient(), user.getBoutique());
						if (clientPartenaireOpt.isPresent()) {
							operationDto.setUuidClientPartenaire(clientPartenaireOpt.get().getUuid());
						}
					}
					
					operationCaisseService.enregistrerOperation(operationDto);
				} catch (Throwable e) {
					logger.warn("Erreur lors de la création de l'opération de caisse pour la vente: {}", e.getMessage());
					// Ne pas bloquer la vente si l'opération de caisse échoue
				}
			}

		}else{
			response.setMessage("La caisse n'est pas ouverte");
			response.setCode(205);
		}


//		CommandeVente commandeVente = Mapper.toCommandeVenteDto(commandeVenteDto, clientOptional.get(),
//				utilisateurOptional.get(), boutiqueOptional.get());
//		CommandeVente commandeVenteSave = commandeVenteRepository.save(commandeVente);
//		response = Mapper.toCommandeVente(commandeVenteSave);

		return response;
	}

	@Override
	@Transactional(readOnly = true)
	public List<CommandeVenteDto> findAll() {
		List<CommandeVente> listeCommandeVente = commandeVenteRepository.listeCommandes(utilisateurServiceImpl.getCurentUtilisateur().getBoutique().getUuid());
		List<CommandeVenteDto> listeCommandeVenteDto = new ArrayList<>();
		listeCommandeVente.forEach(val -> {
			listeCommandeVenteDto.add(Mapper.toCommandeVente(val));
		});
		return listeCommandeVenteDto;
	}

	@Override
	@Transactional(readOnly = true)
	public CommandeVenteDto getById(String uuid) {
		CommandeVenteDto response = new CommandeVenteDto();
		if (uuid == null) {
			response.setCode(RESP_ERROR);
			response.setMessage("L'id ne doit pas etre null");
			return response;
		}
		Optional<CommandeVente> commandeVenteOptionel = commandeVenteRepository.findById(uuid);
		if (!commandeVenteOptionel.isPresent()) {
			response.setCode(RESP_ERROR);
			response.setMessage("Cette commande vente n'exist pas");
			return response;
		}
		return Mapper.toCommandeVente(commandeVenteOptionel.get());
	}

	@Override
	public CommandeVenteDto supprimer(String uuid) {
		CommandeVenteDto response = new CommandeVenteDto();
		if (uuid == null) {
			response.setCode(RESP_ERROR);
			response.setMessage("L'Id ne peut pas etre nul");
			return response;
		}
		Optional<CommandeVente> commandeVenteOptionel = commandeVenteRepository.findById(uuid);
		if (!commandeVenteOptionel.isPresent()) {
			response.setCode(RESP_ERROR);
			response.setMessage("Cette commande vente n'exist pas");
			return response;
		}

		commandeVenteRepository.deleteById(uuid);
		response.setCode(RESP_SUCCES);
		response.setMessage("Suppression effectuée avec success");
		return response;

	}

	@Override
	public CommandeVenteDto update(CommandeVenteDto commandeVenteDto, String uuid) {
		CommandeVenteDto response = new CommandeVenteDto();
		if (commandeVenteDto == null || uuid == null) {
			response.setCode(RESP_ERROR);
			response.setMessage("Veuillez renseigner les champs");
			return response;
		}

		Optional<Client> clientOptional = clientRepository.findById(commandeVenteDto.getId_client());
		if (!clientOptional.isPresent()) {

			response.setCode(RESP_ERROR);
			response.setMessage("Ce client n'exist pas");
			return response;
		}

		commandeVenteDto.setUuid(uuid);
		// CommandeVente commandeVente = Mapper.toCommandeVenteDto(commandeVenteDto,
		// clientOptional.get(),utilisateurOptional.get(),boutiqueOptional.get());
		// CommandeVente commandeVenteSave =
		// commandeVenteRepository.save(commandeVente);
		// response=Mapper.toCommandeVente(commandeVenteSave);
		response.setCode(RESP_SUCCES);
		response.setMessage("Modification effectuer avec success");
		return response;
	}

	@Override
	@Transactional(readOnly = true)
	public List<CommandeVenteDto> listeCommandeVenteByJour() {
		// TODO Auto-generated method stub
		Utilisateur user = utilisateurServiceImpl.getCurentUtilisateur();
		List<CommandeVente> listeCommandeVente = commandeVenteRepository.listeCommandePeriode(new Date(), new Date(), user.getBoutique().getUuid());
		List<CommandeVenteDto> listeCommandeVenteDto = new ArrayList<>();
		listeCommandeVente.forEach(val -> {
			listeCommandeVenteDto.add(Mapper.toCommandeVente(val));
		});
		return listeCommandeVenteDto;
	}

	

	@Override
	public List<CommandeVenteDto> topCommandeVente() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public List<CommandeVenteDto> historiqueCommandeVente(Date dateDebut, Date dateFin) {
		// TODO Auto-generated method stub
		Utilisateur user = utilisateurServiceImpl.getCurentUtilisateur();
		List<CommandeVente> listeCommandeVente = commandeVenteRepository.listeCommandePeriode(dateDebut,dateFin, user.getBoutique().getUuid());
		List<CommandeVenteDto> listeCommandeVenteDto = new ArrayList<>();
		listeCommandeVente.forEach(val -> {
			listeCommandeVenteDto.add(Mapper.toCommandeVente(val));
		});
		return listeCommandeVenteDto;
	}

	@Override
	public PageDataDto<CommandeVenteDto> listeCommandeVentes(int page, int size, String key) {
		// TODO Auto-generated method stub
		
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public PageDataDto<CommandeVenteDto> listeCommandeVenteByJour(int page, int size, String key) {
		// TODO Auto-generated method stub
		String uuidBoutique = utilisateurServiceImpl.getCurentUtilisateur().getBoutique().getUuid();
		PageDataDto<CommandeVenteDto> pageDataDto = new PageDataDto<CommandeVenteDto>();
		List<CommandeVenteDto> commandeVenteDtos = new ArrayList<CommandeVenteDto>();
		Pageable pageable = PageRequest.of(page, size);
		
		Page<CommandeVente> commandeVentes = null;
		
		if(key != null) {
			commandeVentes = commandeVenteRepository.listeCommandePeriodeByKey(new Date(), new Date(), uuidBoutique, key, pageable);
			commandeVentes.forEach(cmd -> commandeVenteDtos.add(Mapper.toCommandeVente(cmd)));
		}
		
		if( key == null) {
			
			commandeVentes = commandeVenteRepository.listeCommandePeriode(new Date(), new Date(), uuidBoutique, pageable);
			commandeVentes.forEach(cmd -> commandeVenteDtos.add(Mapper.toCommandeVente(cmd)));
		}
		
		
		pageDataDto.setData(commandeVenteDtos);
		pageDataDto.getPage().setPageNumber(page);
		pageDataDto.getPage().setSize(size);
		pageDataDto.getPage().setTotalElements(commandeVentes.getTotalElements());
		pageDataDto.getPage().setTotalPages(commandeVentes.getTotalPages());
		return pageDataDto;
	}

	@Override
	@Transactional(readOnly = true)
	public PageDataDto<CommandeVenteDto> historiqueCommandeVente(Date dateDebut, Date dateFin, int page, int size,
			String key) {
		// TODO Auto-generated method stub
		String uuidBoutique = utilisateurServiceImpl.getCurentUtilisateur().getBoutique().getUuid();
		PageDataDto<CommandeVenteDto> pageDataDto = new PageDataDto<CommandeVenteDto>();
		List<CommandeVenteDto> commandeVenteDtos = new ArrayList<CommandeVenteDto>();
		Pageable pageable = PageRequest.of(page, size);
		
		Page<CommandeVente> commandeVentes = null;
		
		if(key != null) {
			commandeVentes = commandeVenteRepository.listeCommandePeriodeByKey(dateDebut, dateFin, uuidBoutique, key, pageable);
			commandeVentes.forEach(cmd -> commandeVenteDtos.add(Mapper.toCommandeVente(cmd)));
		}
		
		if(key == null ) {
			
			commandeVentes = commandeVenteRepository.listeCommandePeriode(dateDebut, dateFin, uuidBoutique, pageable);
			commandeVentes.forEach(cmd -> commandeVenteDtos.add(Mapper.toCommandeVente(cmd)));
		}
		
		
		pageDataDto.setData(commandeVenteDtos);
		pageDataDto.getPage().setPageNumber(page);
		pageDataDto.getPage().setSize(size);
		pageDataDto.getPage().setTotalElements(commandeVentes.getTotalElements());
		pageDataDto.getPage().setTotalPages(commandeVentes.getTotalPages());
		return pageDataDto;
	}

	@Override
	public void delete(String uuid) {
		// TODO Auto-generated method stub
		commandeVenteRepository.deleteById(uuid);
	}

	/**
	 * Valide une commande de vente en gros (uniquement pour les grossistes)
	 * Les commandes en détail sont automatiquement validées lors de la création
	 */
	@Override
	public CommandeVenteDto validerCommandeEnGros(String uuidCommande) {
		logger.info("Validation de la commande en gros: {}", uuidCommande);
		
		try {
			Utilisateur utilisateur = utilisateurServiceImpl.getCurentUtilisateur();
			if (utilisateur == null || utilisateur.getBoutique() == null) {
				throw new RuntimeException("Utilisateur ou boutique non trouvé");
			}
			
			Boutique boutique = utilisateur.getBoutique();
			
			// Vérifier que la boutique est bien un grossiste
			if (boutique.getEnumTypeInstanceBoutique() != org.sid.saranApp.enume.EnumTypeInstanceBoutique.GROSSISTE) {
				throw new IllegalArgumentException("Seuls les grossistes peuvent valider les commandes en gros");
			}
			
			CommandeVente commandeVente = commandeVenteRepository.findById(uuidCommande)
				.orElseThrow(() -> new RuntimeException("Commande non trouvée"));
			
			// Vérifier que c'est bien une commande en gros
			if (commandeVente.getTypeCommande() != org.sid.saranApp.enume.EnumTypeCommande.EN_GROS) {
				throw new IllegalArgumentException("Cette commande n'est pas une commande en gros");
			}
			
			// Vérifier que la commande n'est pas déjà validée
			if (commandeVente.isValidee()) {
				throw new IllegalArgumentException("Cette commande est déjà validée");
			}
			
			// Valider la commande
			commandeVente.setValidee(true);
			commandeVente.setDateValidation(new Date());
			commandeVente.setUtilisateurValidateur(utilisateur);
			
			CommandeVente commandeVenteSave = commandeVenteRepository.save(commandeVente);
			
			logger.info("Commande en gros validée avec succès: {}", commandeVenteSave.getNumeroCommande());
			return Mapper.toCommandeVente(commandeVenteSave);
			
		} catch (Throwable e) {
			logger.error("Erreur lors de la validation de la commande en gros: {}", e.getMessage(), e);
			throw new RuntimeException("Erreur lors de la validation: " + e.getMessage());
		}
	}

	@Override
	public ImportVenteHistoriqueResultDto importVentesHistoriqueJson(List<ImportVenteHistoriqueVenteDto> ventes) {
		ImportVenteHistoriqueResultDto result = new ImportVenteHistoriqueResultDto();
		if (ventes == null) {
			return result;
		}
		result.setVentesLues(ventes.size());
		TransactionTemplate tpl = new TransactionTemplate(platformTransactionManager);
		tpl.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
		int index = 0;
		for (ImportVenteHistoriqueVenteDto v : ventes) {
			index++;
			if (v == null) {
				result.getErreurs().add("Vente " + index + " : corps null.");
				continue;
			}
			final int idx = index;
			try {
				String uuidCmd = tpl.execute(status -> importOneVenteHistoriqueTransaction(v));
				result.getCommandesCreeesUuids().add(uuidCmd);
				result.setVentesImportees(result.getVentesImportees() + 1);
			} catch (Throwable e) {
				logger.warn("Import vente historique {} : {}", idx, e.getMessage());
				result.getErreurs().add("Vente " + idx + " : " + e.getMessage());
			}
		}
		return result;
	}

	/**
	 * Une commande importée dans sa propre transaction (les autres ventes ne sont pas annulées en cas d'échec).
	 */
	private String importOneVenteHistoriqueTransaction(ImportVenteHistoriqueVenteDto dto) {
		Utilisateur user = utilisateurServiceImpl.getCurentUtilisateur();
		if (user == null || user.getBoutique() == null) {
			throw new IllegalStateException("Utilisateur ou boutique non trouvé");
		}
		Optional<ModePaiement> modeOpt = resolveModePaiementPourVente(user, dto.getUuidModePaiement());
		if (modeOpt.isEmpty()) {
			throw new IllegalStateException("Aucun mode de paiement configuré pour cette boutique.");
		}
		List<ImportVenteHistoriqueLigneDto> lignes = dto.getLigneCommandeDtos();
		if (lignes == null || lignes.isEmpty()) {
			throw new IllegalStateException("Aucune ligne de commande fournie");
		}
		String rand = UUID.randomUUID().toString().replace("-", "").toUpperCase();
		String numero = prefixNumeroCommandeVente(user.getBoutique()) + "-" + rand.substring(0, 6);
		CommandeVente commandeVente = new CommandeVente();
		commandeVente.setModePaiement(modeOpt.get());
		commandeVente.setPaye(dto.isPaye());
		commandeVente.setNumeroCommande(numero);
		commandeVente.setBoutique(user.getBoutique());
		commandeVente.setUtilisateur(user);
		commandeVente.setDatePaiement(parseDateVente(dto.getDateVente()));
		commandeVente.setCommandeVenteEnum(
				dto.getStatus() != null ? dto.getStatus() : StatusCommandeVenteEnum.VALIDE);
		commandeVente.setMontantCommade(dto.getMontantCommande());
		commandeVente.setMontantCommadeImage(dto.getMontantCommande());
		commandeVente.setTypeCommande(
				dto.getTypeCommande() != null ? dto.getTypeCommande() : EnumTypeCommande.DETAIL);

		String idClient = dto.getId_client();
		if (idClient != null && !idClient.isBlank()) {
			Optional<Client> clientOptional = clientRepository.findById(idClient.trim());
			if (clientOptional.isPresent()) {
				Client cl = clientOptional.get();
				if (cl.getBoutique() != null && user.getBoutique() != null
						&& !cl.getBoutique().getUuid().equals(user.getBoutique().getUuid())) {
					logger.warn("Import vente : client {} ignoré (autre boutique)", idClient);
				} else {
					commandeVente.setClient(cl);
				}
			}
		}

		commandeVente = commandeVenteRepository.save(commandeVente);
		String boutiqueUuid = user.getBoutique().getUuid();

		for (ImportVenteHistoriqueLigneDto ligneDto : lignes) {
			if (ligneDto == null) {
				continue;
			}
			if (ligneDto.getQuantite() <= 0) {
				throw new IllegalStateException("La quantité doit être supérieure à 0 pour chaque ligne");
			}
			Produit produit = resolveProduitPourImportHistorique(boutiqueUuid, ligneDto)
					.orElseThrow(() -> new IllegalStateException(
							"Produit introuvable (renseigner uuidProduit ou article existant dans la boutique) : "
									+ (ligneDto.getArticle() != null ? ligneDto.getArticle() : ligneDto.getUuidProduit())));
			TypeUniteDeVente typeUnite = resolveTypeUnitePourImportHistorique(produit, ligneDto.getUuidTypeUniteDeVente());
			if (typeUnite == null) {
				throw new IllegalStateException("Unité de vente introuvable pour l'article « "
						+ (produit.getArticle() != null ? produit.getArticle().getLibelle() : produit.getUuid()) + " »");
			}
			LigneCommande ligne = new LigneCommande();
			ligne.setBoutique(user.getBoutique());
			ligne.setCommandeVente(commandeVente);
			ligne.setProduit(produit);
			ligne.setQuantite(ligneDto.getQuantite());
			ligne.setTypeUniteDeVente(typeUnite);
			ligne.setUtilisateur(user);
			ligneCommandeRepository.save(ligne);
		}
		return commandeVente.getUuid();
	}

	private Optional<Produit> resolveProduitPourImportHistorique(String boutiqueUuid,
			ImportVenteHistoriqueLigneDto ligne) {
		if (ligne.getUuidProduit() != null && !ligne.getUuidProduit().isBlank()) {
			return produitRepository.findById(ligne.getUuidProduit().trim())
					.filter(p -> p.getBoutique() != null && boutiqueUuid.equals(p.getBoutique().getUuid()));
		}
		if (ligne.getArticle() != null && !ligne.getArticle().isBlank()) {
			return articleRepository.findFirstByBoutique_UuidAndLibelleIgnoreCase(boutiqueUuid, ligne.getArticle().trim())
					.flatMap(a -> produitRepository.findFirstByBoutique_UuidAndArticle_Uuid(boutiqueUuid, a.getUuid()));
		}
		return Optional.empty();
	}

	private TypeUniteDeVente resolveTypeUnitePourImportHistorique(Produit produit, String uuidTypeUnite) {
		if (uuidTypeUnite != null && !uuidTypeUnite.isBlank()) {
			Optional<TypeUniteDeVente> opt = typeUniteDeVenteRepository.findById(uuidTypeUnite.trim());
			if (opt.isPresent()) {
				TypeUniteDeVente t = opt.get();
				if (produit.getArticle() != null && t.getArticle() != null
						&& produit.getArticle().getUuid().equals(t.getArticle().getUuid())) {
					return t;
				}
			}
			return null;
		}
		Article art = produit.getArticle();
		if (art == null) {
			return null;
		}
		List<TypeUniteDeVente> list = typeUniteDeVenteRepository.findByArticle(art);
		if (list.isEmpty()) {
			return null;
		}
		for (TypeUniteDeVente t : list) {
			if (t.getTypeUniteEnum() == TypeUniteEnum.PIECE && t.getUnite() == 1) {
				return t;
			}
		}
		return list.get(0);
	}

	private static Date parseDateVente(String raw) {
		if (raw == null || raw.isBlank()) {
			return new Date();
		}
		String s = raw.trim();
		try {
			if (s.length() >= 10) {
				LocalDate ld = LocalDate.parse(s.substring(0, 10));
				return Date.from(ld.atStartOfDay(ZoneId.systemDefault()).toInstant());
			}
		} catch (DateTimeParseException ignored) {
			// fall through
		}
		try {
			return Date.from(Instant.parse(s));
		} catch (Exception ignored) {
			// fall through
		}
		try {
			return Date.from(LocalDateTime.parse(s, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
					.atZone(ZoneId.systemDefault()).toInstant());
		} catch (Exception ignored) {
			// fall through
		}
		return new Date();
	}

	/**
	 * Si un UUID est fourni et valide, l'utilise ; sinon espèces / cash (libellé) pour la boutique courante,
	 * ou à défaut le premier mode de la boutique, ou tout mode en base si aucun n'est lié à la boutique.
	 */
	private Optional<ModePaiement> resolveModePaiementPourVente(Utilisateur user, String requestedUuid) {
		if (requestedUuid != null && !requestedUuid.isBlank()) {
			Optional<ModePaiement> byId = modePaiementRepository.findById(requestedUuid.trim());
			if (byId.isPresent()) {
				return byId;
			}
		}
		Boutique boutique = user != null ? user.getBoutique() : null;
		String boutiqueUuid = boutique != null ? boutique.getUuid() : null;
		List<ModePaiement> all = modePaiementRepository.findAll();
		List<ModePaiement> pourBoutique = all.stream()
			.filter(m -> m.getBoutique() != null && boutiqueUuid != null
				&& boutiqueUuid.equals(m.getBoutique().getUuid()))
			.collect(Collectors.toList());
		List<ModePaiement> candidats = pourBoutique.isEmpty() ? all : pourBoutique;
		if (candidats.isEmpty()) {
			if (user == null || user.getBoutique() == null) {
				return Optional.empty();
			}
			logger.info("Aucun mode de paiement en base : creation automatique Espece pour la boutique {}",
				user.getBoutique().getUuid());
			return Optional.of(creerModePaiementEspecesDefaut(user));
		}
		for (ModePaiement m : candidats) {
			String lib = normaliserLibelleModePaiement(m.getLibelle());
			if (lib.contains("espece") || lib.contains("cash")) {
				return Optional.of(m);
			}
		}
		return Optional.of(candidats.get(0));
	}

	private static String normaliserLibelleModePaiement(String libelle) {
		if (libelle == null) {
			return "";
		}
		return Normalizer.normalize(libelle, Normalizer.Form.NFD)
			.replaceAll("\\p{M}+", "")
			.toLowerCase(Locale.ROOT);
	}

	/** Cree un mode Espece lie a la boutique (premiere vente sans configuration). */
	private ModePaiement creerModePaiementEspecesDefaut(Utilisateur user) {
		ModePaiement m = new ModePaiement();
		m.setLibelle("Espèces");
		m.setDescription("Créé automatiquement pour le point de vente");
		m.setBoutique(user.getBoutique());
		m.setUtilisateur(user);
		return modePaiementRepository.save(m);
	}

	/** Préfixe N° commande : TypeShop.code si renseigné, sinon enumTypeShop, sinon CMD. */
	private static String prefixNumeroCommandeVente(Boutique boutique) {
		if (boutique == null) {
			return "CMD";
		}
		if (boutique.getTypeShop() != null) {
			String code = boutique.getTypeShop().getCode();
			if (code != null && !code.isBlank()) {
				return code.trim();
			}
		}
		if (boutique.getEnumTypeShop() != null) {
			return boutique.getEnumTypeShop().name();
		}
		return "CMD";
	}

}
