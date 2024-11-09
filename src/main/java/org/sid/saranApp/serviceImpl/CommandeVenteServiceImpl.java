package org.sid.saranApp.serviceImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.sid.saranApp.dto.CommandeVenteDto;
import org.sid.saranApp.dto.LigneCommandeDto;
import org.sid.saranApp.dto.PageDataDto;
import org.sid.saranApp.enume.EnumTypeBoutique;
import org.sid.saranApp.enume.StatusCommandeVenteEnum;
import org.sid.saranApp.exception.Exception;
import org.sid.saranApp.mapper.Mapper;
import org.sid.saranApp.model.Client;
import org.sid.saranApp.model.CommandeVente;
import org.sid.saranApp.model.LigneCommande;
import org.sid.saranApp.model.Produit;
import org.sid.saranApp.model.Utilisateur;
import org.sid.saranApp.repository.BoutiqueRepository;
import org.sid.saranApp.repository.ClientRepository;
import org.sid.saranApp.repository.CommandeVenteRepository;
import org.sid.saranApp.repository.LigneCommandeRepository;
import org.sid.saranApp.repository.ProduitRepository;
import org.sid.saranApp.repository.UtilisateurRepository;
import org.sid.saranApp.service.CommandeVenteService;
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
public class CommandeVenteServiceImpl implements CommandeVenteService {

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	private CommandeVenteRepository commandeVenteRepository;
	@Autowired
	private UtilisateurRepository utilisateurRepository;
	@Autowired
	private BoutiqueRepository boutiqueRepository;
	@Autowired
	private UtilisateurServiceImpl utilisateurServiceImpl;
	@Autowired
	private ProduitRepository produitRepository;
	@Autowired
	private LigneCommandeRepository ligneCommandeRepository;

	Logger logger = LoggerFactory.getLogger(CommandeVenteServiceImpl.class);

	@Override
	public CommandeVenteDto add(CommandeVenteDto commandeVenteDto) {
		String uuid = UUID.randomUUID().toString().replace("-", "").toUpperCase();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		logger.info("hello {}", auth.getName());
		CommandeVenteDto response = new CommandeVenteDto();
		CommandeVente commandeVente = new CommandeVente();
		Utilisateur user = utilisateurServiceImpl.getCurentUtilisateur();
//		if (commandeVenteDto == null) {
//			response.setCode(Exception.error);
//			response.setMessage("Veuillez renseigner les champs");
//			return response;
//		}
//
//		Optional<Client> clientOptional = clientRepository.findById(commandeVenteDto.getId_client());
//		if (!clientOptional.isPresent()) {
//			commandeVente.setClient(null);
//			response.setCode(Exception.error);
//			response.setMessage("Ce client n'exist pas");
//			return response;
//		}
		String cmd = "";
		if(utilisateurServiceImpl.getCurentUtilisateur().getBoutique().getTypeBoutique() == EnumTypeBoutique.SUPERMARCHE) {
			cmd = "SUP-" + uuid.substring(0, 6);
		}
		
		if(utilisateurServiceImpl.getCurentUtilisateur().getBoutique().getTypeBoutique() == EnumTypeBoutique.BOUTIQUE) {
			cmd = "BOU-" + uuid.substring(0, 6);
		}
		
		if(utilisateurServiceImpl.getCurentUtilisateur().getBoutique().getTypeBoutique() == EnumTypeBoutique.PHARMACIE) {
			cmd = "PHAR-" + uuid.substring(0, 6);
		}
		commandeVente.setNumeroCommande(cmd);
		commandeVente.setBoutique(user.getBoutique());
		commandeVente.setUtilisateur(user);
		commandeVente.setDatePaiement(new Date());
		commandeVente.setCommandeVenteEnum(StatusCommandeVenteEnum.ENREGISTRER);
		commandeVente.setMontantCommade(commandeVenteDto.getMontantCommande());

		commandeVente = commandeVenteRepository.save(commandeVente);

		List<LigneCommandeDto> ligneCommandeDtos = commandeVenteDto.getLigneCommandeDtos();

		for (Iterator iterator = ligneCommandeDtos.iterator(); iterator.hasNext();) {
			LigneCommandeDto ligneCommandeDto = (LigneCommandeDto) iterator.next();

			Produit produit = produitRepository.findById(ligneCommandeDto.getUuidProduit()).orElseThrow(null);

			// LigneCommande ligne = new LigneCommande();

			int operationQtite = produit.getQuantiteImage() - ligneCommandeDto.getQuantite();
			LigneCommande ligne = new LigneCommande();
			if (operationQtite >= 0) {

				ligne.setBoutique(utilisateurServiceImpl.getCurentUtilisateur().getBoutique());
				ligne.setCommandeVente(commandeVente);
				ligne.setProduit(produit);
				ligne.setQuantite(ligneCommandeDto.getQuantite());
				ligne.setUniteSortie("PIECE");
				ligne.setUtilisateur(user);
				ligne = ligneCommandeRepository.save(ligne);

				if (!ligne.getUuid().isEmpty()) {
					produit.setQuantiteImage(operationQtite);
					produitRepository.save(produit);
				}

			} else {
				response.setCode(Exception.error);
				response.setMessage("La quantite de l'article " + ligne.getProduit().getLivraisonCommandeFournisseur()
						.getDetailCommandeFournisseur().getArticle().getLibelle() + " insuffisant");
			}

		}

//		CommandeVente commandeVente = Mapper.toCommandeVenteDto(commandeVenteDto, clientOptional.get(),
//				utilisateurOptional.get(), boutiqueOptional.get());
//		CommandeVente commandeVenteSave = commandeVenteRepository.save(commandeVente);
//		response = Mapper.toCommandeVente(commandeVenteSave);

		return response;
	}

	@Override
	public List<CommandeVenteDto> findAll() {
		List<CommandeVente> listeCommandeVente = commandeVenteRepository.listeCommandes(utilisateurServiceImpl.getCurentUtilisateur().getBoutique().getUuid());
		List<CommandeVenteDto> listeCommandeVenteDto = new ArrayList<>();
		listeCommandeVente.forEach(val -> {
			listeCommandeVenteDto.add(Mapper.toCommandeVente(val));
		});
		return listeCommandeVenteDto;
	}

	@Override
	public CommandeVenteDto getById(String uuid) {
		CommandeVenteDto response = new CommandeVenteDto();
		if (uuid == null) {
			response.setCode(Exception.error);
			response.setMessage("L'id ne doit pas etre null");
			return response;
		}
		Optional<CommandeVente> commandeVenteOptionel = commandeVenteRepository.findById(uuid);
		if (!commandeVenteOptionel.isPresent()) {
			response.setCode(Exception.error);
			response.setMessage("Cette commande vente n'exist pas");
			return response;
		}
		return Mapper.toCommandeVente(commandeVenteOptionel.get());
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
	public CommandeVenteDto update(CommandeVenteDto commandeVenteDto, String uuid) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		CommandeVenteDto response = new CommandeVenteDto();
		if (commandeVenteDto == null || uuid == null) {
			response.setCode(Exception.error);
			response.setMessage("Veuillez renseigner les champs");
			return response;
		}

		Optional<Client> clientOptional = clientRepository.findById(commandeVenteDto.getId_client());
		if (!clientOptional.isPresent()) {

			response.setCode(Exception.error);
			response.setMessage("Ce client n'exist pas");
			return response;
		}

		commandeVenteDto.setUuid(uuid);
		// CommandeVente commandeVente = Mapper.toCommandeVenteDto(commandeVenteDto,
		// clientOptional.get(),utilisateurOptional.get(),boutiqueOptional.get());
		// CommandeVente commandeVenteSave =
		// commandeVenteRepository.save(commandeVente);
		// response=Mapper.toCommandeVente(commandeVenteSave);
		response.setCode(Exception.succes);
		response.setMessage("Modification effectuer avec success");
		return response;
	}

	@Override
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

}
