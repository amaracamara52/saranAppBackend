package org.sid.saranApp.serviceImpl;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.sid.saranApp.dto.CommandeFournisseurDto;
import org.sid.saranApp.dto.DetailCommandeFournisseurDto;
import org.sid.saranApp.enume.StatusCommandeFournisseurEnum;
import org.sid.saranApp.mapper.Mapper;
import org.sid.saranApp.model.Article;
import org.sid.saranApp.model.Boutique;
import org.sid.saranApp.model.CommandeFournisseur;
import org.sid.saranApp.model.DetailCommandeFournisseur;
import org.sid.saranApp.model.Fournisseur;
import org.sid.saranApp.model.LivraisonCommandeFournisseur;
import org.sid.saranApp.model.Produit;
import org.sid.saranApp.model.Utilisateur;
import org.sid.saranApp.repository.ArticleRepository;
import org.sid.saranApp.repository.BoutiqueRepository;
import org.sid.saranApp.repository.CommandeFournisseurRepository;
import org.sid.saranApp.repository.DetailCommandeFournisseurRepository;
import org.sid.saranApp.repository.FournisseurRepository;
import org.sid.saranApp.repository.LivraisonCommandeFournisseurRepository;
import org.sid.saranApp.repository.ProduitRepository;
import org.sid.saranApp.repository.UtilisateurRepository;
import org.sid.saranApp.service.CommandeFournisseurService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CommandeFournisseurServiceImpl implements CommandeFournisseurService {

	@Autowired
	private CommandeFournisseurRepository commandeFournisseurRepository;
	@Autowired
	private FournisseurRepository fournisseurRepository;
	@Autowired
	private BoutiqueRepository boutiqueRepository;
	@Autowired
	private UtilisateurRepository utilisateurRepository;
	@Autowired
	private DetailCommandeFournisseurRepository detailCommandeFournisseurRepository;
	@Autowired
	private ArticleRepository articleRepository;
	@Autowired
	private LivraisonCommandeFournisseurRepository livraisonCommandeFournisseurRepository;
	@Autowired
	private ProduitRepository produitRepository;

	Logger logger = LoggerFactory.getLogger(CommandeFournisseurServiceImpl.class);

	@Override
	public CommandeFournisseurDto addCommandeFournisseur(CommandeFournisseurDto commandeFournisseurDto) {
		// TODO Auto-generated method stub
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		logger.info("hello {}", auth.getName());
		CommandeFournisseur commandeFournisseur = new CommandeFournisseur();
		Utilisateur utilisateur = utilisateurRepository.findByEmail(auth.getName()).orElseThrow(null);
		Fournisseur fournisseur = fournisseurRepository.findById(commandeFournisseurDto.getUuidFournisseur())
				.orElseThrow(null);
		commandeFournisseur.setValeurMarchandise(commandeFournisseurDto.getValeurMarchandise());
		commandeFournisseur.setDateCommandeFournisseur(commandeFournisseurDto.getDateCommandeFournisseur());
		commandeFournisseur.setPaie(commandeFournisseurDto.isPaye());
		commandeFournisseur.setCommandeFournisseurEnum(StatusCommandeFournisseurEnum.ENREGISTRER);
		commandeFournisseur.setRefCommande("");
		commandeFournisseur.setBoutique(utilisateur.getBoutique());
		commandeFournisseur.setUtilisateur(utilisateur);
		commandeFournisseur.setFournisseur(fournisseur);
		CommandeFournisseur commandeFournisseurSave = commandeFournisseurRepository.save(commandeFournisseur);
		List<DetailCommandeFournisseurDto> detailCommandeFournisseurDtos = commandeFournisseurDto
				.getDetailCommandeFournisseurDtos();
		for (Iterator iterator = detailCommandeFournisseurDtos.iterator(); iterator.hasNext();) {
			DetailCommandeFournisseurDto detailCommandeFournisseurDto = (DetailCommandeFournisseurDto) iterator.next();
			DetailCommandeFournisseur detailCommandeFournisseur = new DetailCommandeFournisseur();

			Article article = articleRepository.findById(detailCommandeFournisseurDto.getUuidArticle())
					.orElseThrow(null);

			detailCommandeFournisseur.setArticle(article);
			detailCommandeFournisseur.setCommandeFournisseur(commandeFournisseurSave);
			detailCommandeFournisseur.setBoutique(utilisateur.getBoutique());
			detailCommandeFournisseur.setUnite(detailCommandeFournisseurDto.getUnite());
			detailCommandeFournisseur.setUtilisateur(utilisateur);
			detailCommandeFournisseur.setPrixAchat(detailCommandeFournisseurDto.getPrixAchat());
			detailCommandeFournisseur.setQuantite(detailCommandeFournisseurDto.getQuantite());
			detailCommandeFournisseurRepository.save(detailCommandeFournisseur);
		}
		return Mapper.toCommandeFournisseurDto(commandeFournisseurSave);
	}

	@Override
	public CommandeFournisseurDto addLivraisonCommandeFournisseur(CommandeFournisseurDto commandeFournisseurDto) {
		// TODO Auto-generated method stub
		LocalTime time = LocalTime.now();
		CommandeFournisseur commandeFournisseur = commandeFournisseurRepository
				.findById(commandeFournisseurDto.getUuid()).orElseThrow(null);
		List<DetailCommandeFournisseurDto> detailCommandeFournisseurs = commandeFournisseurDto
				.getDetailCommandeFournisseurDtos();
		for (Iterator iterator = detailCommandeFournisseurs.iterator(); iterator.hasNext();) {
			DetailCommandeFournisseurDto detailCommandeFournisseurDto = (DetailCommandeFournisseurDto) iterator.next();
			DetailCommandeFournisseur detailCommandeFournisseur = detailCommandeFournisseurRepository
					.findById(detailCommandeFournisseurDto.getUuid()).orElseThrow(null);
			LivraisonCommandeFournisseur livraison = new LivraisonCommandeFournisseur();
			livraison.setBoutique(commandeFournisseur.getBoutique());
			livraison.setCommandeFournisseur(commandeFournisseur);
			livraison.setDateLivraison(new Date());
			livraison.setHeure(time.toString());
			livraison.setQuantite(detailCommandeFournisseurDto.getQuantiteLivraison());
			livraison.setDetailCommandeFournisseur(detailCommandeFournisseur);
			livraison.setUtilisateur(commandeFournisseur.getUtilisateur());
			livraisonCommandeFournisseurRepository.save(livraison);
		}
		commandeFournisseur.setCommandeFournisseurEnum(StatusCommandeFournisseurEnum.RECU);
		commandeFournisseurRepository.save(commandeFournisseur);
		return Mapper.toCommandeFournisseurDto(commandeFournisseur);
	}

	@Override
	public CommandeFournisseurDto addStockFromCommandeAndLivraison(CommandeFournisseurDto commandeFournisseurDto) {
		// TODO Auto-generated method stub
		LocalTime time = LocalTime.now();
		CommandeFournisseur commandeFournisseur = commandeFournisseurRepository
				.findById(commandeFournisseurDto.getUuid()).orElseThrow(null);
		List<DetailCommandeFournisseurDto> detailCommandeFournisseurs = commandeFournisseurDto
				.getDetailCommandeFournisseurDtos();
		for (Iterator iterator = detailCommandeFournisseurs.iterator(); iterator.hasNext();) {
			DetailCommandeFournisseurDto detailCommandeFournisseurDto = (DetailCommandeFournisseurDto) iterator.next();
			DetailCommandeFournisseur detailCommandeFournisseur = detailCommandeFournisseurRepository
					.findById(detailCommandeFournisseurDto.getUuid()).orElseThrow(null);
			LivraisonCommandeFournisseur livraison = new LivraisonCommandeFournisseur();
			livraison.setBoutique(commandeFournisseur.getBoutique());
			livraison.setCommandeFournisseur(commandeFournisseur);
			livraison.setDateLivraison(new Date());
			livraison.setHeure(time.toString());
			livraison.setQuantite(detailCommandeFournisseurDto.getQuantiteLivraison());
			livraison.setDetailCommandeFournisseur(detailCommandeFournisseur);
			livraison.setUtilisateur(commandeFournisseur.getUtilisateur());
			livraison = livraisonCommandeFournisseurRepository.save(livraison);

			Produit produit = new Produit();

			produit.setBoutique(commandeFournisseur.getBoutique());
			produit.setLivraisonCommandeFournisseur(livraison);
			produit.setQuantite(livraison.getQuantite());
			produit.setQuantiteImage(livraison.getQuantite());

			if (detailCommandeFournisseur.getUnite().equals("PIECE")) {
				produit.setPrixAchat((int) detailCommandeFournisseur.getPrixAchat());
			}

			if (detailCommandeFournisseur.getUnite().equals("CARTON")) {
				int prixAchat = (int) (detailCommandeFournisseur.getQuantite()
						/ detailCommandeFournisseur.getPrixAchat());
				produit.setPrixAchat(prixAchat);
			}

			produit.setUtilisateur(commandeFournisseur.getUtilisateur());
			produitRepository.save(produit);
		}
		commandeFournisseur.setCommandeFournisseurEnum(StatusCommandeFournisseurEnum.STOCK_APPROVISIONNEE);
		commandeFournisseurRepository.save(commandeFournisseur);
		return Mapper.toCommandeFournisseurDto(commandeFournisseur);
	}

	@Override
	public void deleteCommandeFournisseur(String uuid) {
		// TODO Auto-generated method stub
		CommandeFournisseur commandeFournisseur = commandeFournisseurRepository.findById(uuid).orElseThrow(null);
		commandeFournisseurRepository.delete(commandeFournisseur);

	}

	@Override
	public List<CommandeFournisseurDto> findAll() {
		// TODO Auto-generated method stub
		List<CommandeFournisseur> commandeFournisseurs = commandeFournisseurRepository.findAll();
		List<CommandeFournisseurDto> commandeFournisseurDtos = new ArrayList<CommandeFournisseurDto>();
		commandeFournisseurs.forEach(commandeFournisseur -> commandeFournisseurDtos
				.add(Mapper.toCommandeFournisseurDto(commandeFournisseur)));
		return commandeFournisseurDtos;
	}

	@Override
	public CommandeFournisseurDto getCommandeFournisseur(String uuid) {
		// TODO Auto-generated method stub
		CommandeFournisseur commandeFournisseur = commandeFournisseurRepository.findById(uuid).orElseThrow(null);
		return Mapper.toCommandeFournisseurDto(commandeFournisseur);
	}

	@Override
	public CommandeFournisseurDto updateCommandeFournisseur(CommandeFournisseurDto commandeFournisseurDto,
			String uuid) {
		// TODO Auto-generated method stub
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Utilisateur utilisateur = utilisateurRepository.findByEmail(auth.getName()).orElseThrow(null);
		Fournisseur fournisseur = fournisseurRepository.findById(commandeFournisseurDto.getUuidFournisseur())
				.orElseThrow(null);
		Boutique boutique = boutiqueRepository.findById(commandeFournisseurDto.getUuidBoutique()).orElseThrow(null);
		CommandeFournisseur commandeFournisseur = commandeFournisseurRepository.findById(uuid).orElseThrow(null);
		commandeFournisseur.setDateCommandeFournisseur(commandeFournisseurDto.getDateCommandeFournisseur());
		commandeFournisseur.setPaie(commandeFournisseurDto.isPaye());
		commandeFournisseur.setCommandeFournisseurEnum(commandeFournisseurDto.getStatus());
		commandeFournisseur.setRefCommande(commandeFournisseurDto.getRefCommande());
		commandeFournisseur.setValeurMarchandise(commandeFournisseurDto.getValeurMarchandise());
		commandeFournisseur.setBoutique(boutique);
		commandeFournisseur.setUtilisateur(utilisateur);
		commandeFournisseur.setFournisseur(fournisseur);
		CommandeFournisseur commandeFournisseurSave = commandeFournisseurRepository.save(commandeFournisseur);
		return Mapper.toCommandeFournisseurDto(commandeFournisseurSave);
	}

}
