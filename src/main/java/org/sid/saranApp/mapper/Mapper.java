package org.sid.saranApp.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.sid.saranApp.dto.ArticleDto;
import org.sid.saranApp.dto.ArticleSelectDto;
import org.sid.saranApp.dto.BoutiqueDto;
import org.sid.saranApp.dto.BoutiquePaiementDto;
import org.sid.saranApp.dto.CaracteristiqueArticleDto;
import org.sid.saranApp.dto.CaracteristiqueProduitDto;
import org.sid.saranApp.dto.CategorieDto;
import org.sid.saranApp.dto.ClientDto;
import org.sid.saranApp.dto.CommandeFournisseurDto;
import org.sid.saranApp.dto.CommandeVenteDto;
import org.sid.saranApp.dto.CommuneDto;
import org.sid.saranApp.dto.ConnectionDto;
import org.sid.saranApp.dto.DetailCommandeFournisseurDto;
import org.sid.saranApp.dto.EtagereDto;
import org.sid.saranApp.dto.EtagereRayonDto;
import org.sid.saranApp.dto.FournisseurDto;
import org.sid.saranApp.dto.LigneCommandeDto;
import org.sid.saranApp.dto.LivraisonCommandeFournisseurDto;
import org.sid.saranApp.dto.ModePaiementDto;
import org.sid.saranApp.dto.PaiementCommandeFournisseurDto;
import org.sid.saranApp.dto.ParametreDto;
import org.sid.saranApp.dto.PaysDto;
import org.sid.saranApp.dto.ProduitDto;
import org.sid.saranApp.dto.QuartierDto;
import org.sid.saranApp.dto.RayonDto;
import org.sid.saranApp.dto.StoredFileDto;
import org.sid.saranApp.dto.StoredFileInfoDto;
import org.sid.saranApp.dto.UtilisateurDto;
import org.sid.saranApp.dto.VilleDto;
import org.sid.saranApp.enume.EnumRole;
import org.sid.saranApp.model.Article;
import org.sid.saranApp.model.Boutique;
import org.sid.saranApp.model.BoutiquePaiement;
import org.sid.saranApp.model.CaracteristiqueArticle;
import org.sid.saranApp.model.CaracteristiqueProduit;
import org.sid.saranApp.model.Categorie;
import org.sid.saranApp.model.Client;
import org.sid.saranApp.model.CommandeFournisseur;
import org.sid.saranApp.model.CommandeVente;
import org.sid.saranApp.model.Commune;
import org.sid.saranApp.model.Connection;
import org.sid.saranApp.model.DetailCommandeFournisseur;
import org.sid.saranApp.model.Etagere;
import org.sid.saranApp.model.EtagereRayon;
import org.sid.saranApp.model.Fournisseur;
import org.sid.saranApp.model.LigneCommande;
import org.sid.saranApp.model.LivraisonCommandeFournisseur;
import org.sid.saranApp.model.ModePaiement;
import org.sid.saranApp.model.PaiementCommandeFournisseur;
import org.sid.saranApp.model.Parametre;
import org.sid.saranApp.model.Pays;
import org.sid.saranApp.model.Produit;
import org.sid.saranApp.model.Quartier;
import org.sid.saranApp.model.Rayon;
import org.sid.saranApp.model.StoredFile;
import org.sid.saranApp.model.Utilisateur;
import org.sid.saranApp.model.Ville;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

public class Mapper {

	public static ArticleDto toArticleDto(Article article) {
		ArticleDto dto = new ArticleDto();
		dto.setUuid(article.getUuid());
		dto.setLibelle(article.getLibelle());
		dto.setDescription(article.getDescription());
		dto.setUuidCategorie(article.getCategorie().getUuid());
		dto.setUuidBoutique(article.getBoutique().getUuid());
		dto.setUuidUtilisateur(article.getUtilisateur().getUuid());
		dto.setCategorie(article.getCategorie().getLibelle());
		dto.setQuantiteDansCarton(article.getQuantiteDansCarton());
		return dto;
	}

	public static ArticleSelectDto toArticleSelectDto(Article article) {
		ArticleSelectDto dto = new ArticleSelectDto();
		dto.setName(article.getLibelle());
		dto.setUuid(article.getUuid());
		dto.setCategorie(article.getCategorie().getLibelle());
		return dto;
	}

	public static BoutiqueDto toBoutiqueDto(Boutique boutique) {
		BoutiqueDto dto = new BoutiqueDto();
		dto.setAdresse(boutique.getAdresse());
		dto.setDescriptionBoutique(boutique.getDescriptionBoutique());
		dto.setEmailBoutique(boutique.getEmailBoutique());
		dto.setLibelleBoutique(boutique.getLibelleBoutique());
		dto.setPhoneBoutique(boutique.getPhoneBoutique());
		dto.setSiteBoutique(boutique.getSiteBoutique());
		dto.setTypeBoutique(boutique.getTypeBoutique());
		dto.setUuid(boutique.getUuid());

		return dto;
	}

	public static BoutiquePaiementDto toBoutiquePaiementDto(BoutiquePaiement boutiquePaiement) {
		BoutiquePaiementDto dto = new BoutiquePaiementDto();
		dto.setDateDebut(boutiquePaiement.getDateDebut());
		dto.setDateFin(boutiquePaiement.getDateFin());
		dto.setValide(boutiquePaiement.isValide());
		dto.setKey(boutiquePaiement.getKeyToken());
		dto.setLibelleBoutique(boutiquePaiement.getBoutique().getLibelleBoutique());
		dto.setRefPaiement(boutiquePaiement.getRefPaiement());
		dto.setTypePaiment(boutiquePaiement.getTypePaiment());
		dto.setUuid(boutiquePaiement.getUuid());
		dto.setUuidBoutique(boutiquePaiement.getBoutique().getUuid());
		dto.setUuidModePaiement(boutiquePaiement.getModePaiement().getUuid());
		dto.setUuidUtilisateur(boutiquePaiement.getUtilisateur().getUuid());
		dto.setModePaiement(boutiquePaiement.getModePaiement().getLibelle());
		return dto;
	}

	public static CaracteristiqueArticleDto toCaracteristiqueArticleDto(CaracteristiqueArticle caracteristiqueArticle) {
		CaracteristiqueArticleDto dto = new CaracteristiqueArticleDto();
		dto.setUuid(caracteristiqueArticle.getUuid());
		dto.setLibelle(caracteristiqueArticle.getLibelle());
		dto.setUuidArticle(caracteristiqueArticle.getArticle().getUuid());
		// dto.setUuidCaracteristiqueProduit(caracteristiqueArticle.getCaracteristiqueProduit().getUuid());
		return dto;

	}

	public static CaracteristiqueProduitDto toCaracteristiqueProduit(CaracteristiqueProduit caracteristiqueProduit) {
		CaracteristiqueProduitDto caracteristiqueProduitDto = new CaracteristiqueProduitDto();
		caracteristiqueProduitDto.setUuid(caracteristiqueProduit.getUuid());
		caracteristiqueProduitDto.setValue(caracteristiqueProduit.getValue());
		caracteristiqueProduitDto.setUuidProduit(caracteristiqueProduit.getProduit().getUuid());
		caracteristiqueProduitDto.setUuidUtilisateur(caracteristiqueProduit.getUtilisateur().getUuid());
		caracteristiqueProduitDto.setUuidBoutique(caracteristiqueProduit.getBoutique().getUuid());
		return caracteristiqueProduitDto;
	}

	public static CaracteristiqueProduit toCaracteristiqueProduitDto(
			CaracteristiqueProduitDto caracteristiqueProduitDto, Produit produit, Utilisateur utilisateur,
			Boutique boutique) {
		CaracteristiqueProduit caracteristiqueProduit = new CaracteristiqueProduit();
		caracteristiqueProduit.setUuid(caracteristiqueProduitDto.getUuid());
		caracteristiqueProduit.setValue(caracteristiqueProduitDto.getValue());
		caracteristiqueProduit.setProduit(produit);
		caracteristiqueProduit.setUtilisateur(utilisateur);
		caracteristiqueProduit.setBoutique(boutique);
		return caracteristiqueProduit;
	}

	public static CategorieDto toCategorieDto(Categorie categorie) {
		CategorieDto dto = new CategorieDto();
		dto.setUuid(categorie.getUuid());
		dto.setLibelle(categorie.getLibelle());
		dto.setDescription(categorie.getDescription());
		dto.setUuidBoutique(categorie.getBoutique().getUuid());
		dto.setUuidUtilisateur(categorie.getUtilisateur().getUuid());
		return dto;
	}

	public static ClientDto toClient(Client client) {
		ClientDto clientDto = new ClientDto();
		clientDto.setUuid(client.getUuid());
		clientDto.setEmail(client.getEmail());
		clientDto.setNom(client.getNom());
		clientDto.setPrenom(client.getPrenom());
		clientDto.setPhone(client.getPhone());
		clientDto.setUuidBoutique(client.getBoutique().getUuid());
		clientDto.setUuidUtilisateur(client.getUtilisateur().getUuid());
		return clientDto;
	}

	/************************************************************
	 * ***********************Client*****************************
	 */

	public static Client toClientDto(ClientDto clientDto, Boutique boutique, Utilisateur utilisateur) {
		Client client = new Client();
		client.setUuid(clientDto.getUuid());
		client.setEmail(clientDto.getEmail());
		client.setNom(clientDto.getNom());
		client.setPhone(clientDto.getPhone());
		client.setPrenom(clientDto.getPrenom());
		client.setUtilisateur(utilisateur);
		client.setBoutique(boutique);
		return client;
	}

	public static CommandeFournisseurDto toCommandeFournisseurDto(CommandeFournisseur commandeFournisseur) {
		CommandeFournisseurDto dto = new CommandeFournisseurDto();
		dto.setUuid(commandeFournisseur.getUuid());
		dto.setValeurMarchandise(commandeFournisseur.getValeurMarchandise());
		dto.setPaye(commandeFournisseur.isPaie());
		dto.setUuidBoutique(commandeFournisseur.getBoutique().getUuid());
		dto.setDateCommandeFournisseur(commandeFournisseur.getDateCommandeFournisseur());
		dto.setUuidFournisseur(commandeFournisseur.getFournisseur().getUuid());
		dto.setUuidBoutique(commandeFournisseur.getBoutique().getUuid());
		dto.setStatus(commandeFournisseur.getCommandeFournisseurEnum());
		dto.setNom(commandeFournisseur.getFournisseur().getNom());
		dto.setEmail(commandeFournisseur.getFournisseur().getEmail());
		dto.setPrenom(commandeFournisseur.getFournisseur().getPrenom());
		dto.setRefCommande(commandeFournisseur.getRefCommande());
		dto.setAdresse(commandeFournisseur.getFournisseur().getVille().getLibelle());
		dto.setTelephone(commandeFournisseur.getFournisseur().getTelephone());
		List<DetailCommandeFournisseur> detailCommandeFournisseurs = commandeFournisseur
				.getListeDetailCommandeFournisseur();
		List<DetailCommandeFournisseurDto> detailCommandeFournisseurDtos = new ArrayList<DetailCommandeFournisseurDto>();
		detailCommandeFournisseurs
				.forEach(detail -> detailCommandeFournisseurDtos.add(Mapper.toDetailCommandeFournisseurDto(detail)));
		dto.setDetailCommandeFournisseurDtos(detailCommandeFournisseurDtos);
		return dto;
	}

	public static CommandeVenteDto toCommandeVente(CommandeVente commandeVente) {
		CommandeVenteDto commandeVenteDto = new CommandeVenteDto();
		commandeVenteDto.setUuid(commandeVente.getUuid());
		commandeVenteDto.setNumeroCommande(commandeVente.getNumeroCommande());
		commandeVenteDto.setPaye(commandeVente.isPaye());
		commandeVenteDto.setMontantCommande(commandeVente.getMontantCommade());
		commandeVenteDto.setDatePaiement(commandeVente.getDatePaiement());
	//	commandeVenteDto.setId_client(commandeVente.getClient().getUuid());
		commandeVenteDto.setUuidBoutique(commandeVente.getBoutique().getUuid());
		commandeVenteDto.setUuidUtilisateur(commandeVente.getUtilisateur().getUuid());
		commandeVenteDto.setUtilisateur(commandeVente.getUtilisateur().getEmail());
		commandeVenteDto.setStatus(commandeVenteDto.getStatus());
		commandeVenteDto.setNombreArticle(commandeVente.getListeLigneCommande().size());
		
		List<LigneCommandeDto> ligneCommandeDtos = new ArrayList<LigneCommandeDto>();
		commandeVente.getListeLigneCommande().forEach(val -> ligneCommandeDtos.add(Mapper.toLigneCommande(val)));
		commandeVenteDto.setLigneCommandeDtos(ligneCommandeDtos);
		return commandeVenteDto;
	}

	/***********************************************************
	 * *********************Commande Vente**********************
	 */

//	public static CommandeVente toCommandeVenteDto(CommandeVenteDto commandeVenteDto, Client client,
//			Utilisateur utilisateur, Boutique boutique) {
//		CommandeVente commandeVente = new CommandeVente();
//		commandeVente.setUuid(commandeVenteDto.getUuid());
//		commandeVente.setNumeroCommande(commandeVenteDto.getNumeroCommande());
//		commandeVente.setPaye(commandeVenteDto.isPaye());
//		commandeVente.setMontantCommade(commandeVenteDto.getMontantCommande());
//		commandeVente.setDatePaiement(commandeVenteDto.getDatePaiement());
//		commandeVente.setBoutique(boutique);
//		commandeVente.setUtilisateur(utilisateur);
//		commandeVente.setClient(client);
//		return commandeVente;
//	}

	public static CommuneDto toCommuneDto(Commune commune) {
		CommuneDto dto = new CommuneDto();
		dto.setLibelle(commune.getLibelle());
		dto.setPays(commune.getVille().getPays().getLibelle());
		dto.setUuid(commune.getUuid());
		dto.setUuidPays(commune.getVille().getPays().getUuid());
		dto.setUuidVille(commune.getVille().getUuid());
		dto.setVille(commune.getVille().getLibelle());
		dto.setUuidBoutique(commune.getBoutique().getUuid());
		dto.setUuidUtilisateur(commune.getUtilisateur().getUuid());
		return dto;
	}

	public static ConnectionDto toConnectionDto(Connection connection) {
		ConnectionDto dto = new ConnectionDto();
		dto.setEmail(connection.getEmail());
		dto.setMotDePasse(connection.getMotdepasse());
		return dto;
	}

	public static DetailCommandeFournisseurDto toDetailCommandeFournisseurDto(
			DetailCommandeFournisseur detailCommandeFournisseur) {
		DetailCommandeFournisseurDto dto = new DetailCommandeFournisseurDto();
		dto.setUuid(detailCommandeFournisseur.getUuid());
		dto.setUuidBoutique(detailCommandeFournisseur.getBoutique().getUuid());
		dto.setUuidCommandeFournisseur(detailCommandeFournisseur.getCommandeFournisseur().getUuid());
		dto.setArticle(detailCommandeFournisseur.getArticle().getLibelle());
		dto.setCategorie(detailCommandeFournisseur.getArticle().getCategorie().getLibelle());
		dto.setPrixAchat(detailCommandeFournisseur.getPrixAchat());
		dto.setQuantite(detailCommandeFournisseur.getQuantite());
		dto.setDateCommande(detailCommandeFournisseur.getCommandeFournisseur().getDateCommandeFournisseur());
		dto.setDescription(detailCommandeFournisseur.getArticle().getDescription());
		dto.setStatus(detailCommandeFournisseur.getCommandeFournisseur().getCommandeFournisseurEnum());
		dto.setValeurMarchandise(detailCommandeFournisseur.getCommandeFournisseur().getValeurMarchandise());
		dto.setUuidArticle(detailCommandeFournisseur.getArticle().getUuid());
		dto.setUuidUtilisateur(detailCommandeFournisseur.getUtilisateur().getUuid());
		dto.setUnite(detailCommandeFournisseur.getUnite());
		dto.setQuantiteLivraison(detailCommandeFournisseur.getQuantite());

		return dto;
	}

	public static EtagereRayonDto toEtagereRayonDto(EtagereRayon etagereRayon) {
		EtagereRayonDto dto = new EtagereRayonDto();

		dto.setRayon(etagereRayon.getRayon());
		dto.setLibelle(etagereRayon.getEtagere());
		dto.setUuid(etagereRayon.getUuid());
		dto.setBoutique(etagereRayon.getBoutique().getLibelleBoutique());
		dto.setUuidUtilisateur(etagereRayon.getBoutique().getUuid());
		dto.setUtilisateur(etagereRayon.getUtilisateur().getEmail());
		dto.setUuidUtilisateur(etagereRayon.getUtilisateur().getUuid());
		dto.setCode(etagereRayon.getCode());
		dto.setEmplacement(dto.getLibelle() + " " + dto.getCode() + " " + dto.getRayon());
		return dto;
	}

	public static EtagereDto toEtagereStockDto(Etagere etagere) {
		EtagereDto dto = new EtagereDto();
		dto.setCapacite(etagere.getCapacite());
		dto.setFull(etagere.isFull());
		dto.setLibelle(etagere.getLibelle());
		dto.setUuid(etagere.getUuid());
		return dto;
	}

	/*********************************************************
	 * ***************Gestion Fournisseur*********************
	 */

	public static FournisseurDto toFournisseurDto(Fournisseur fournisseur) {
		FournisseurDto dto = new FournisseurDto();
		dto.setNom(fournisseur.getNom());
		dto.setPrenom(fournisseur.getPrenom());
		dto.setTelephone(fournisseur.getTelephone());
		dto.setEmail(fournisseur.getEmail());
		dto.setUuid(fournisseur.getUuid());
		dto.setUuidBoutique(fournisseur.getBoutique().getUuid());
		dto.setUuidVille(fournisseur.getVille().getUuid());
		dto.setVille(fournisseur.getVille().getLibelle());
		dto.setUuidUtilisateur(fournisseur.getUtilisateur().getUuid());
		dto.setNomComplet(fournisseur.getPrenom() + " " + fournisseur.getNom());
		return dto;
	}

	public static LigneCommandeDto toLigneCommande(LigneCommande ligneCommande) {
		LigneCommandeDto ligneCommandeDto = new LigneCommandeDto();
		ligneCommandeDto.setUuid(ligneCommande.getUuid());
		// ligneCommandeDto.setQte(ligneCommande.getQuantite());
		ligneCommandeDto.setUuidProduit(ligneCommande.getProduit().getUuid());
		ligneCommandeDto.setUuidCommandeVente(ligneCommande.getCommandeVente().getUuid());
		ligneCommandeDto.setUuidBoutique(ligneCommande.getBoutique().getUuid());
		ligneCommandeDto.setUuidUtilisateur(ligneCommande.getUtilisateur().getUuid());
		ligneCommandeDto.setArticle(ligneCommande.getProduit().getLivraisonCommandeFournisseur().getDetailCommandeFournisseur().getArticle().getLibelle());
		ligneCommandeDto.setDatePeremption(ligneCommande.getProduit().getDatePeremption());
		ligneCommandeDto.setEmplacement(ligneCommande.getProduit().getEmplacement().getEtagere()+" "+ligneCommande.getProduit().getEmplacement().getCode()+" "+ligneCommande.getProduit().getEmplacement().getRayon());
		ligneCommandeDto.setPrixVente(ligneCommande.getProduit().getPrixVente());
		ligneCommandeDto.setQuantite(ligneCommande.getQuantite());
		ligneCommandeDto.setQuantiteStock(ligneCommande.getProduit().getQuantiteImage());
		
		return ligneCommandeDto;
	}

	/*********************************************************
	 * *********************Ligne Commande********************
	 */

	public static LigneCommande toLigneCommandeDto(LigneCommandeDto ligneCommandeDto, Produit produit,
			CommandeVente commandeVente, Utilisateur utilisateur, Boutique boutique) {
		LigneCommande ligneCommande = new LigneCommande();
		ligneCommande.setUuid(ligneCommandeDto.getUuid());
		// ligneCommande.setQte(ligneCommandeDto.getQte());
		ligneCommande.setProduit(produit);
		ligneCommande.setCommandeVente(commandeVente);
		ligneCommande.setBoutique(boutique);
		ligneCommande.setUtilisateur(utilisateur);
		
		return ligneCommande;
	}

	public static LivraisonCommandeFournisseurDto toLivraisonCommandeFournisseurDto(
			LivraisonCommandeFournisseur livraisonCommandeFournisseur) {
		LivraisonCommandeFournisseurDto dto = new LivraisonCommandeFournisseurDto();
		dto.setUuid(livraisonCommandeFournisseur.getUuid());
		dto.setDateLivraison(livraisonCommandeFournisseur.getDateLivraison());
		dto.setHeure(livraisonCommandeFournisseur.getHeure());
		dto.setUuidBoutique(livraisonCommandeFournisseur.getBoutique().getUuid());
		dto.setUuidCommandeFournisseur(livraisonCommandeFournisseur.getCommandeFournisseur().getUuid());
		dto.setQuantite(livraisonCommandeFournisseur.getQuantite());
		dto.setPrix(livraisonCommandeFournisseur.getPrix());
		dto.setUuidUtilisateur(livraisonCommandeFournisseur.getUtilisateur().getUuid());
		dto.setUuidDetailCommandeFournisseur(livraisonCommandeFournisseur.getDetailCommandeFournisseur().getUuid());
		return dto;

	}

	public static ModePaiementDto toModePaiementDto(ModePaiement modePaiement) {
		ModePaiementDto dto = new ModePaiementDto();
		dto.setDescription(modePaiement.getDescription());
		dto.setLibelle(modePaiement.getLibelle());
		dto.setUuidBoutique(modePaiement.getBoutique().getUuid());
		dto.setUuidUtilisateur(modePaiement.getUtilisateur().getUuid());
		dto.setUuid(modePaiement.getUuid());
		return dto;
	}

	public static PaiementCommandeFournisseurDto toPaiementCommandeFournisseurDto(
			PaiementCommandeFournisseur paiementCommandeFournisseur) {
		PaiementCommandeFournisseurDto dto = new PaiementCommandeFournisseurDto();
		dto.setUuid(paiementCommandeFournisseur.getUuid());
		dto.setDatePaiement(paiementCommandeFournisseur.getDatePaiement());
		dto.setPrixPayes(paiementCommandeFournisseur.getPrixPayes());
		dto.setUuidBoutique(paiementCommandeFournisseur.getBoutique().getUuid());
		dto.setUuidCommandeFournisseur(paiementCommandeFournisseur.getCommandeFournisseur().getUuid());
		dto.setUuidUtilisateur(paiementCommandeFournisseur.getUtilisateur().getUuid());
		return dto;
	}

	public static ParametreDto toParametreDto(Parametre parametre) {
		ParametreDto dto = new ParametreDto();
		dto.setUuid(parametre.getUuid());
		dto.setDescription(parametre.getDescription());
		dto.setDateDebut(parametre.getDateDebut());
		dto.setDateFin(parametre.getDateFin());
		dto.setLibelle(parametre.getLibelle());
		dto.setValeur(parametre.getValeur());
		dto.setTypeParametre(parametre.getTypeParametre());
		dto.setUuidBoutique(parametre.getBoutique().getUuid());
		dto.setUuidProduit(parametre.getProduit().getUuid());
		
		return dto;
	}

	public static PaysDto toPaysDto(Pays pays) {
		PaysDto dto = new PaysDto();
		dto.setLibelle(pays.getLibelle());
		dto.setUuid(pays.getUuid());
		dto.setUuidBouique(pays.getBoutique().getUuid());
		dto.setUuidUtilisateur(pays.getUtilisateur().getUuid());
		return dto;
	}

	public static ProduitDto toProduit(Produit produit) {
		ProduitDto produitDto = new ProduitDto();
		produitDto.setUuid(produit.getUuid());
		produitDto.setPrixAchat(produit.getPrixAchat());
		produitDto.setPrixVente(produit.getPrixVente());
		produitDto.setQuantite(produit.getQuantite());
		
		produitDto.setArticle(
				produit.getLivraisonCommandeFournisseur().getDetailCommandeFournisseur().getArticle().getLibelle());
		produitDto.setCategorie(produit.getLivraisonCommandeFournisseur().getDetailCommandeFournisseur().getArticle()
				.getCategorie().getLibelle());
		produitDto.setDatePeremption(produit.getDatePeremption());
		produitDto.setDateCommande(produit.getLivraisonCommandeFournisseur().getDetailCommandeFournisseur().getCommandeFournisseur().getDateCommandeFournisseur());
				
		
		if (produit.getEmplacement() != null) {
			produitDto
					.setEmplacement(produit.getEmplacement().getCode() + " ==> " + produit.getEmplacement().getRayon());
			produitDto.setEtagere(produit.getEmplacement().getEtagere());
			produitDto.setUuidEmplacement(produit.getEmplacement().getUuid());
		}

		produitDto.setFournisseur(
				produit.getLivraisonCommandeFournisseur().getCommandeFournisseur().getFournisseur().getPrenom() + " "
						+ produit.getLivraisonCommandeFournisseur().getCommandeFournisseur().getFournisseur().getNom());
		produitDto.setQuantiteImage(produit.getQuantiteImage());
		produitDto.setStatusCommandeFournisseurEnum(
				produit.getLivraisonCommandeFournisseur().getCommandeFournisseur().getCommandeFournisseurEnum());
		produitDto.setUuidLivraisonCommandeFournisseur(produit.getLivraisonCommandeFournisseur().getUuid());
		produitDto.setUuidBoutique(produit.getBoutique().getUuid());
		produitDto.setUuidUtilisateur(produit.getUtilisateur().getUuid());
		produitDto.setUnite(produit.getLivraisonCommandeFournisseur().getDetailCommandeFournisseur().getUnite());
		produitDto.setFinish(produit.isFinish());
		return produitDto;
	}

	/*********************************************************
	 * **********************Produit**************************
	 */

	public static Produit toProduitDto(ProduitDto produitDto, LivraisonCommandeFournisseur livraisonCommandeFournisseur,
			Boutique boutique, Utilisateur utilisateur) {
		Produit produit = new Produit();
		produit.setUuid(produitDto.getUuid());
		produit.setPrixAchat(produitDto.getPrixAchat());
		produit.setPrixVente(produitDto.getPrixVente());
		produit.setQuantite(produit.getQuantite());
		produit.setLivraisonCommandeFournisseur(livraisonCommandeFournisseur);
		produit.setUtilisateur(utilisateur);
		produit.setBoutique(boutique);
		return produit;
	}

	public static QuartierDto toQuartierDto(Quartier quartier) {
		QuartierDto dto = new QuartierDto();
		dto.setCommune(quartier.getCommune().getLibelle());
		dto.setLibelle(quartier.getLibelle());
		dto.setPays(quartier.getCommune().getVille().getPays().getLibelle());
		dto.setUuid(quartier.getUuid());
		dto.setUuidCommune(quartier.getCommune().getUuid());
		dto.setUuidPays(quartier.getCommune().getVille().getPays().getUuid());
		dto.setUuidVille(quartier.getCommune().getVille().getUuid());
		dto.setVille(quartier.getCommune().getVille().getLibelle());
		dto.setUuidBoutique(quartier.getBoutique().getUuid());
		dto.setUuidUtilisateur(quartier.getUtilisateur().getUuid());
		return dto;
	}

	public static RayonDto toRayonDto(Rayon rayon) {
		RayonDto dto = new RayonDto();
		dto.setCode(rayon.getCode());
		dto.setLibelle(rayon.getLibelle());
		dto.setUuid(rayon.getUuid());
		return dto;
	}

	public static StoredFileDto toStoredFileDto(StoredFile storedFile) {
		StoredFileDto dto = new StoredFileDto();
		dto.setUuid(storedFile.getUuid());
		dto.setBytes(storedFile.getBytes());
		dto.setName(storedFile.getName());
		dto.setType(storedFile.getType());
		return dto;
	}

	public static StoredFileInfoDto toStoredFileInfoDto(StoredFile storedFile) {
		StoredFileInfoDto dto = new StoredFileInfoDto();
		dto.setUuid(storedFile.getUuid());
		dto.setName(storedFile.getName());
		return dto;
	}

	public static UserDetails toUserDetails(Utilisateur user) {
		Collection<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
		for (Iterator iterator = user.getRole().iterator(); iterator.hasNext();) {
			EnumRole enumRole = (EnumRole) iterator.next();
			authorities.add(new SimpleGrantedAuthority(enumRole.name()));
		}
		UserDetails userdetails = new User(user.getEmail(), user.getPassword(), authorities);

		return userdetails;
	}

	public static UtilisateurDto toUtilisateurDto(Utilisateur utilisateur) {
		UtilisateurDto dto = new UtilisateurDto();
		dto.setUuid(utilisateur.getUuid());
		dto.setUsername(utilisateur.getUsername());
		dto.setPhone(utilisateur.getPhone());
		dto.setRole(utilisateur.getRole());
		dto.setEmail(utilisateur.getEmail());
		dto.setMotDePasse(utilisateur.getPassword());
		dto.setAdresse(utilisateur.getAdresse());
		// dto.setBoutique(utilisateur.getBoutique().getLibelleBoutique());
		return dto;

	}

	public static VilleDto toVilleDto(Ville ville) {
		VilleDto dto = new VilleDto();
		dto.setLibelle(ville.getLibelle());
		dto.setPays(ville.getPays().getLibelle());
		dto.setUuid(ville.getUuid());
		dto.setUuidPays(ville.getPays().getUuid());
		dto.setUuidBoutique(ville.getBoutique().getUuid());
		dto.setUuidUtilisateur(ville.getUtilisateur().getUuid());
		return dto;
	}

}
