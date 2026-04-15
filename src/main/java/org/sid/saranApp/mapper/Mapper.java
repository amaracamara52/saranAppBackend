package org.sid.saranApp.mapper;

import org.sid.saranApp.dto.*;
import org.sid.saranApp.dto.BoutiqueDto;
import org.sid.saranApp.dto.mobile.*;
import org.sid.saranApp.dto.version.ProduitStockDto;
import org.sid.saranApp.dto.version.TypeUniteDeVenteDto;
import org.sid.saranApp.enume.EnumRole;
import org.sid.saranApp.enume.TypeGestionUniteProduitEnum;
import org.sid.saranApp.model.*;
import org.sid.saranApp.service.StockUniteVenteService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.*;

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

	private static Utilisateur pickRepresentativeUtilisateur(Boutique boutique) {
		if (boutique.getUtilisateurs() == null || boutique.getUtilisateurs().isEmpty()) {
			return null;
		}
		return boutique.getUtilisateurs().stream()
				.filter(u -> u.getRole() != null && u.getRole().contains(EnumRole.BOUTIQUE))
				.findFirst()
				.orElseGet(() -> boutique.getUtilisateurs().stream()
						.filter(u -> u.getRole() != null && !u.getRole().isEmpty())
						.findFirst()
						.orElse(boutique.getUtilisateurs().get(0)));
	}

	public static BoutiqueDto toBoutiqueDto(Boutique boutique) {
		BoutiqueDto dto = new BoutiqueDto();
		dto.setAdresse(boutique.getAdresse());
		dto.setDescriptionBoutique(boutique.getDescriptionBoutique());
		dto.setEmailBoutique(boutique.getEmailBoutique());
		dto.setLibelleBoutique(boutique.getLibelleBoutique());
		dto.setPhoneBoutique(boutique.getPhoneBoutique());
		dto.setSiteBoutique(boutique.getSiteBoutique());
		dto.setUuid(boutique.getUuid());
		Utilisateur rep = pickRepresentativeUtilisateur(boutique);
		if (rep != null && rep.getRole() != null && !rep.getRole().isEmpty()) {
			dto.setRole(rep.getRole().get(0));
		}
		
		// Mapping des nouveaux champs
		dto.setDevise(boutique.getDevise());
		dto.setLangue(boutique.getLangue());
		dto.setSeuilAlerteStock(boutique.getSeuilAlerteStock());
		dto.setMethodeValorisation(boutique.getMethodeValorisation());
		dto.setApprovisionnementAutomatique(boutique.isApprovisionnementAutomatique());
		dto.setQuantiteACommander(boutique.getQuantiteACommander());
		dto.setImpressionTicket(boutique.isImpressionTicket());
		dto.setImpressionFacture(boutique.isImpressionFacture());
		dto.setDevis(boutique.isDevis());
		dto.setDette(boutique.isDette());
		
		if(boutique.getStoredFile() != null) {
			
			byte[] encodeBase64 = Base64.getEncoder().encode(boutique.getStoredFile().getBytes());
		    String base64Encoded;
			try {
				base64Encoded = new String(encodeBase64, "UTF-8");
				dto.setImage("data:"+boutique.getStoredFile().getType()+";base64,"+base64Encoded);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			dto.setUuidStoreFile(boutique.getStoredFile().getUuid());
		
		}

		return dto;
	}

	public static BoutiqueMobileDto toBoutiqueMobileDto(Boutique boutique){
		BoutiqueMobileDto dto = new BoutiqueMobileDto();
		dto.setBoutiqueName(dto.getBoutiqueName());
		dto.setBoutiqueAdress(boutique.getAdresse());
		dto.setUuid(boutique.getUuid());
		dto.setBoutiqueContact(boutique.getPhoneBoutique());
		dto.setUuid(boutique.getUuid());
		dto.setBoutiqueName(boutique.getLibelleBoutique());
		dto.setCountryCode(boutique.getCountryCode());
		dto.setBoutique(boutique.getEnumTypeShop());
		dto.setCode(boutique.getCode());
		dto.setMonnaieEnum(boutique.getMonnaie());
		
		// Mapping des nouveaux champs
		dto.setDevise(boutique.getDevise());
		dto.setLangue(boutique.getLangue());
		dto.setSeuilAlerteStock(boutique.getSeuilAlerteStock());
		dto.setMethodeValorisation(boutique.getMethodeValorisation());
		dto.setApprovisionnementAutomatique(boutique.isApprovisionnementAutomatique());
		dto.setQuantiteACommander(boutique.getQuantiteACommander());
		dto.setImpressionTicket(boutique.isImpressionTicket());
		dto.setImpressionFacture(boutique.isImpressionFacture());
		dto.setDevis(boutique.isDevis());
		dto.setDette(boutique.isDette());
		
		if(boutique.getStoredFile() != null){
			dto.setUuidStoredFile(boutique.getStoredFile().getUuid());

			byte[] encodeBase64 = Base64.getEncoder().encode(boutique.getStoredFile().getBytes());
			String base64Encoded;
			try {
				base64Encoded = new String(encodeBase64, "UTF-8");
				dto.setImage("data:"+boutique.getStoredFile().getType()+";base64,"+base64Encoded);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}


		return dto;
	}

	public static CustomerDto toCustomerDto(CustomerBoutique customerBoutique){
			CustomerDto dto = new CustomerDto();

			dto.setCustomerPhone(customerBoutique.getCustomerPhone());
			dto.setGenre(customerBoutique.getGenre());
			dto.setPassword(customerBoutique.getUtilisateur().getPassword());
			dto.setPrenom(customerBoutique.getPrenom());
			dto.setNom(customerBoutique.getNom());
			dto.setUuid(customerBoutique.getUuid());
			dto.setCustomerAdress(customerBoutique.getUtilisateur().getAdresse());
			dto.setRole(customerBoutique.getUtilisateur().getRole().get(0));
			dto.setCustomerEmail(customerBoutique.getCustomerEmail());

		    if(customerBoutique.getStoredFile() !=null){
				dto.setUuidStoredFile(customerBoutique.getStoredFile().getUuid());
				byte[] encodeBase64 = Base64.getEncoder().encode(customerBoutique.getStoredFile().getBytes());
				String base64Encoded;
				try {
					base64Encoded = new String(encodeBase64, "UTF-8");
					dto.setImageProfile("data:"+ customerBoutique.getStoredFile().getType()+";base64,"+base64Encoded);
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		return dto;
	}

	public static RegistrationDto toRegistrationDto(Utilisateur utilisateur,CustomerBoutique customerBoutique){
		RegistrationDto dto = new RegistrationDto();
		if(utilisateur.getBoutique() !=null){
			dto.setBoutique(Mapper.toBoutiqueMobileDto(utilisateur.getBoutique()));
		}
		dto.setCustomer(Mapper.toCustomerDto(customerBoutique));
//		if(customerBoutique !=null || utilisateur.getBoutique() != null || utilisateur != null){
//
//			dto.getResponse().setLoad(true);
//			dto.getResponse().setMessage("success");
//			dto.getResponse().setCode(200);
//		}else{
//			dto.getResponse().setLoad(false);
//			dto.getResponse().setMessage("fail");
//			dto.getResponse().setCode(400);
//		}

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
		dto.setCategorie(caracteristiqueArticle.getCategorie().getLibelle());
		dto.setUuidCategorie(caracteristiqueArticle.getCategorie().getUuid());
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
		caracteristiqueProduitDto.setLibelle(caracteristiqueProduit.getLibelle());
		
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
		//dto.setAdresse(commandeFournisseur.getFournisseur().getVille().getLibelle());
		dto.setTelephone(commandeFournisseur.getFournisseur().getTelephone());
		
		// Données enrichies (inspiré Odoo)
		dto.setMontantTotal(commandeFournisseur.getMontantTotal());
		dto.setLibelleBoutique(commandeFournisseur.getBoutique().getLibelleBoutique());
		dto.setLibelleFournisseur(commandeFournisseur.getFournisseur().getPrenom() + " " + 
			commandeFournisseur.getFournisseur().getNom());
		
		// Pays de provenance du fournisseur
		if (commandeFournisseur.getFournisseur().getPaysProvenance() != null) {
			dto.setPaysProvenanceFournisseur(commandeFournisseur.getFournisseur().getPaysProvenance().getLibelle());
		}
		
		// Calculer montant payé et restant
		double montantPaye = 0.0;
		if (commandeFournisseur.getPaiements() != null) {
			for (org.sid.saranApp.model.PaiementCommandeFournisseur paiement : commandeFournisseur.getPaiements()) {
				montantPaye += paiement.getMontantVerse();
			}
		}
		dto.setMontantPaye(montantPaye);
		dto.setMontantRestant(commandeFournisseur.getMontantTotal() - montantPaye);
		
		// Nombre de livraisons
		if (commandeFournisseur.getListeLivraisonCommandeFournisseur() != null) {
			dto.setNombreLivraisons(commandeFournisseur.getListeLivraisonCommandeFournisseur().size());
		} else {
			dto.setNombreLivraisons(0);
		}
		
		// Nombre de paiements
		if (commandeFournisseur.getPaiements() != null) {
			dto.setNombrePaiements(commandeFournisseur.getPaiements().size());
		} else {
			dto.setNombrePaiements(0);
		}
		
		// Charger explicitement les détails pour éviter les problèmes de lazy loading
		List<DetailCommandeFournisseur> detailCommandeFournisseurs = commandeFournisseur
				.getListeDetailCommandeFournisseur();
		List<DetailCommandeFournisseurDto> detailCommandeFournisseurDtos = new ArrayList<DetailCommandeFournisseurDto>();
		if (detailCommandeFournisseurs != null) {
			detailCommandeFournisseurs
					.forEach(detail -> detailCommandeFournisseurDtos.add(Mapper.toDetailCommandeFournisseurDto(detail)));
		}
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
		commandeVenteDto.setMontantCommandeImage(commandeVente.getMontantCommadeImage());
		if (commandeVente.getBoutique() != null) {
			commandeVenteDto.setUuidBoutique(commandeVente.getBoutique().getUuid());
		}
		if (commandeVente.getUtilisateur() != null) {
			commandeVenteDto.setUuidUtilisateur(commandeVente.getUtilisateur().getUuid());
			commandeVenteDto.setUtilisateur(commandeVente.getUtilisateur().getEmail());
		}
		commandeVenteDto.setStatus(commandeVente.getCommandeVenteEnum());
		commandeVenteDto.setTypeCommande(commandeVente.getTypeCommande());
		if (commandeVente.getModePaiement() != null) {
			commandeVenteDto.setUuidModePaiement(commandeVente.getModePaiement().getUuid());
			commandeVenteDto.setModePaiement(commandeVente.getModePaiement().getLibelle());
		}
		if (commandeVente.getClient() != null) {
			commandeVenteDto.setId_client(commandeVente.getClient().getUuid());
			commandeVenteDto.setClient(commandeVente.getClient().getPrenom() + " " + commandeVente.getClient().getNom());
		}
		List<LigneCommande> lignes = commandeVente.getListeLigneCommande();
		if (lignes == null || lignes.isEmpty()) {
			commandeVenteDto.setNombreArticle(0);
			commandeVenteDto.setLigneCommandeDtos(new ArrayList<>());
		} else {
			commandeVenteDto.setNombreArticle(lignes.size());
			List<LigneCommandeDto> ligneCommandeDtos = new ArrayList<>();
			lignes.forEach(val -> ligneCommandeDtos.add(Mapper.toLigneCommande(val)));
			commandeVenteDto.setLigneCommandeDtos(ligneCommandeDtos);
		}
		return commandeVenteDto;
	}

	public static ProduitStockDto toProduitStockDto(Produit produit){
		return toProduitStockDto(produit, null);
	}
	
	public static ProduitStockDto toProduitStockDto(Produit produit, StockUniteVenteService stockUniteVenteService){
		ProduitStockDto produitStockDto = new ProduitStockDto();
		produitStockDto.setUuid(produit.getUuid());
		produitStockDto.setFinish(produit.isFinish());
		if (produit.getFournisseur() != null) {
			produitStockDto.setFournisseur(produit.getFournisseur().getPrenom() + " " + produit.getFournisseur().getNom());
			produitStockDto.setUuidFournisseur(produit.getFournisseur().getUuid());
		} else {
			produitStockDto.setFournisseur(null);
			produitStockDto.setUuidFournisseur(null);
		}
		produitStockDto.setBoutique(produit.getBoutique().getLibelleBoutique());
		
		if (produit.getEmplacement() != null) {
			produitStockDto.setEmplacement(produit.getEmplacement().getEtagere()+":"+produit.getEmplacement().getRayon());
			produitStockDto.setUuidEmplacement(produit.getEmplacement().getUuid());
		}
		
		// Utiliser StockUniteVenteService pour calculer les quantités
		if (stockUniteVenteService != null) {
			int quantiteTotale = stockUniteVenteService.calculateTotalBaseQuantity(produit);
			produitStockDto.setQuantiteStock(quantiteTotale);
			produitStockDto.setQuantiteStockImage(quantiteTotale);
		produitStockDto.setQuantiteImageLivraison(quantiteTotale);
			
			// Prix catalogue (Produit) ; vente affichée = prix ligne unité ×1 si présent, sinon prix produit
			List<StockUniteVente> stocks = stockUniteVenteService.getStocksForProduit(produit);
			produitStockDto.setPrixAchat(produit.getPrixAchat());
			produitStockDto.setPrixVente(produit.getPrixVente());
			if (!stocks.isEmpty()) {
				Optional<StockUniteVente> base = stocks.stream()
						.filter(s -> s.getTypeUniteDeVente() != null && s.getTypeUniteDeVente().getUnite() == 1)
						.findFirst();
				StockUniteVente ref = base.orElse(stocks.get(0));
				if (ref.getPrice() > 0) {
					produitStockDto.setPrixVente(ref.getPrice());
				}
			}
			
			// Récupérer les unités de vente de l'article
			List<TypeUniteDeVente> unites = produit.getArticle().getTypeUniteDeVentes();
			List<TypeUniteDeVenteDto> typeUniteDeVenteDtos = new ArrayList<>();
			if (unites != null) {
				unites.forEach(u -> {
					TypeUniteDeVenteDto dto = new TypeUniteDeVenteDto();
					dto.setUuid(u.getUuid());
					dto.setTypeUniteEnum(u.getTypeUniteEnum());
					dto.setUnite(u.getUnite());
					// Récupérer la quantité depuis StockUniteVente
					int qtite = stockUniteVenteService.getStockForUnit(produit, u);
					dto.setQtite(qtite);
					// Récupérer le prix depuis StockUniteVente
					StockUniteVente stock = stocks.stream()
						.filter(s -> s.getTypeUniteDeVente().getUuid().equals(u.getUuid()))
						.findFirst()
						.orElse(null);
					if (stock != null) {
						dto.setPrice(stock.getPrice());
					}
					typeUniteDeVenteDtos.add(dto);
				});
			}
			produitStockDto.setTypeUniteDeVenteDtos(typeUniteDeVenteDtos);
		} else {
			// Fallback si le service n'est pas fourni
			produitStockDto.setQuantiteStock(0);
			produitStockDto.setQuantiteStockImage(0);
		produitStockDto.setQuantiteImageLivraison(0);
			produitStockDto.setPrixAchat(0);
			produitStockDto.setPrixVente(0);
			produitStockDto.setTypeUniteDeVenteDtos(new ArrayList<>());
		}
		
		produitStockDto.setQuantiteCommande(0);
		produitStockDto.setQuantitePublish(0);
		produitStockDto.setQuantiteLivraison(0);
		produitStockDto.setQuantiteVendu(produit.getQuantiteVendu());
		
		produitStockDto.setLibelleArticle(produit.getArticle().getLibelle());
		produitStockDto.setDescriptionArticle(produit.getArticle().getDescription());
		produitStockDto.setCodeProduit(produit.getArticle().getCodeProduit());
		TypeGestionUniteProduitEnum modeUnite = produit.getArticle().getTypeGestionUnite();
		produitStockDto.setTypeGestionUnite(modeUnite != null ? modeUnite : TypeGestionUniteProduitEnum.MIXTE);
		produitStockDto.setLibelleCategorie(produit.getArticle().getCategorie().getLibelle());
		produitStockDto.setUuidArticle(produit.getArticle().getUuid());
		produitStockDto.setDatePeremption(produit.getDatePeremption());
		produitStockDto.setSeuilMinimum(produit.getSeuilMinimum());
		produitStockDto.setUuidCategorie(produit.getArticle().getCategorie().getUuid());
		produitStockDto.setUuidBoutique(produit.getBoutique().getUuid());
		produitStockDto.setUuidUtilisateur(produit.getUtilisateur().getUuid());
		produitStockDto.setDateCommande(produit.getDateEnregistrement() != null ? produit.getDateEnregistrement() : new Date());
		
		// Type d'unité par défaut (peut être amélioré)
		if (produit.getArticle().getTypeUniteDeVentes() != null && !produit.getArticle().getTypeUniteDeVentes().isEmpty()) {
			produitStockDto.setTypeUniteEnum(produit.getArticle().getTypeUniteDeVentes().get(0).getTypeUniteEnum());
		}

		List<CaracteristiqueProduitDto>  caracteristiqueProduitDtos = new ArrayList<>();
		produit.getCaracteristiqueProduits().forEach(c -> caracteristiqueProduitDtos.add(Mapper.toCaracteristiqueProduit(c)));

		List<ProduitStoredDto> produitStoredDtos = new ArrayList<>();
		produit.getProduitStoreds().forEach(ps -> produitStoredDtos.add(Mapper.toProduitStoredDto(ps)));

		produitStockDto.setCaracteristiqueProduitDtos(caracteristiqueProduitDtos);
		produitStockDto.setProduitStoredDtos(produitStoredDtos);
		
		return produitStockDto;
	}

	public static ProduitStoredDto toProduitStoredDto(ProduitStored produitStored){
		ProduitStoredDto produitStoredDto = new ProduitStoredDto();
		produitStoredDto.setUuidStored(produitStored.getUuid());

		produitStoredDto.setUuidProduit(produitStored.getProduit().getUuid());

		produitStoredDto.setUuid(produitStored.getUuid());
		byte[] encodeBase64 = Base64.getEncoder().encode(produitStored.getStoredFile().getBytes());
		String base64Encoded;
		try {
			base64Encoded = new String(encodeBase64, "UTF-8");
			produitStoredDto.setImage("data:"+ produitStored.getStoredFile().getType()+";base64,"+base64Encoded);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return produitStoredDto;
	}

	public static TypeUniteDeVenteDto toTypeUniteDeVenteDto(TypeUniteDeVente typeUniteDeVente){
		TypeUniteDeVenteDto dto = new TypeUniteDeVenteDto();
		dto.setTypeUniteEnum(typeUniteDeVente.getTypeUniteEnum());
		if (typeUniteDeVente.getArticle() != null) {
			dto.setUuidProduit(typeUniteDeVente.getArticle().getUuid());
		}
		dto.setUuid(typeUniteDeVente.getUuid());
		dto.setPrice(typeUniteDeVente.getPrice());
		dto.setUnite(typeUniteDeVente.getUnite());
		dto.setQtite(0); // Sera mis à jour depuis StockUniteVente
		return dto;
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
		//dto.setPays(commune.getVille().getPays().getLibelle());
		dto.setUuid(commune.getUuid());
		//dto.setUuidPays(commune.getVille().getPays().getUuid());
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
		// Utiliser typeUniteDeVente si disponible, sinon utiliser le champ unite (String) pour compatibilité
		if (detailCommandeFournisseur.getTypeUniteDeVente() != null) {
			dto.setUnite(detailCommandeFournisseur.getTypeUniteDeVente().getTypeUniteEnum().name());
		} else if (detailCommandeFournisseur.getUnite() != null) {
			dto.setUnite(detailCommandeFournisseur.getUnite());
		} else {
			dto.setUnite("PIECE");
		}
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
//		dto.setUuidVille(fournisseur.getVille().getUuid());
//		dto.setVille(fournisseur.getVille().getLibelle());
		dto.setUuidUtilisateur(fournisseur.getUtilisateur().getUuid());
		dto.setNomComplet(fournisseur.getPrenom() + " " + fournisseur.getNom());
		
		// Gérer le pays de provenance
		if (fournisseur.getPaysProvenance() != null) {
			dto.setUuidPaysProvenance(fournisseur.getPaysProvenance().getUuid());
			dto.setLibellePaysProvenance(fournisseur.getPaysProvenance().getLibelle());
			dto.setCountryCodePaysProvenance(fournisseur.getPaysProvenance().getCountryCode());
		}
		
		return dto;
	}

	public static LigneCommandeDto toLigneCommande(LigneCommande ligneCommande) {
		LigneCommandeDto ligneCommandeDto = new LigneCommandeDto();
		ligneCommandeDto.setUuid(ligneCommande.getUuid());
		Produit p = ligneCommande.getProduit();
		if (p != null) {
			ligneCommandeDto.setUuidProduit(p.getUuid());
			ligneCommandeDto.setDatePeremption(p.getDatePeremption());
			if (p.getArticle() != null) {
				ligneCommandeDto.setArticle(p.getArticle().getLibelle());
			} else {
				ligneCommandeDto.setArticle("");
			}
			if (p.getEmplacement() != null) {
				ligneCommandeDto.setEmplacement(p.getEmplacement().getEtagere() + " " + p.getEmplacement().getCode() + " "
						+ p.getEmplacement().getRayon());
			} else {
				ligneCommandeDto.setEmplacement(null);
			}
			List<CaracteristiqueProduitDto> caracteristiqueProduitDtos = new ArrayList<>();
			if (p.getCaracteristiqueProduits() != null) {
				p.getCaracteristiqueProduits()
						.forEach(c -> caracteristiqueProduitDtos.add(Mapper.toCaracteristiqueProduit(c)));
			}
			ligneCommandeDto.setCaracteristiqueArticleDtos(caracteristiqueProduitDtos);

			// Prix de vente: priorité au prix de l'unité utilisée sur la ligne, sinon prix du produit.
			double prixVente = p.getPrixVente();
			if (ligneCommande.getTypeUniteDeVente() != null && ligneCommande.getTypeUniteDeVente().getPrice() > 0) {
				prixVente = ligneCommande.getTypeUniteDeVente().getPrice();
			}
			ligneCommandeDto.setPrixVente(prixVente);

			// Quantité stock: base Produit (fallback), puis reste après la ligne.
			int quantiteStock = Math.max(p.getQuantite(), 0);
			ligneCommandeDto.setQuantiteStock(quantiteStock);
			ligneCommandeDto.setQuantiteRestant(Math.max(quantiteStock - ligneCommande.getQuantite(), 0));
		} else {
			ligneCommandeDto.setArticle("");
			ligneCommandeDto.setCaracteristiqueArticleDtos(new ArrayList<>());
			ligneCommandeDto.setPrixVente(0);
			ligneCommandeDto.setQuantiteStock(0);
			ligneCommandeDto.setQuantiteRestant(0);
		}
		if (ligneCommande.getCommandeVente() != null) {
			ligneCommandeDto.setUuidCommandeVente(ligneCommande.getCommandeVente().getUuid());
		}
		if (ligneCommande.getBoutique() != null) {
			ligneCommandeDto.setUuidBoutique(ligneCommande.getBoutique().getUuid());
		}
		if (ligneCommande.getUtilisateur() != null) {
			ligneCommandeDto.setUuidUtilisateur(ligneCommande.getUtilisateur().getUuid());
			ligneCommandeDto.setUtilisateur(ligneCommande.getUtilisateur().getEmail());
		}
		if (ligneCommande.getTypeUniteDeVente() != null) {
			ligneCommandeDto.setUuidTypeUniteDeVente(ligneCommande.getTypeUniteDeVente().getUuid());
			if (ligneCommande.getTypeUniteDeVente().getTypeUniteEnum() != null) {
				ligneCommandeDto.setTypeUniteDeVente(ligneCommande.getTypeUniteDeVente().getTypeUniteEnum().name());
			}
		}
		ligneCommandeDto.setQuantite(ligneCommande.getQuantite());
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
		
		// Gérer le statut de vérification
		dto.setStatutVerification(livraisonCommandeFournisseur.getStatutVerification());
		dto.setVerifiee(livraisonCommandeFournisseur.isVerifiee());
		dto.setDateVerification(livraisonCommandeFournisseur.getDateVerification());
		dto.setCommentaireVerification(livraisonCommandeFournisseur.getCommentaireVerification());
		
		if (livraisonCommandeFournisseur.getUtilisateurVerificateur() != null) {
			dto.setUuidUtilisateurVerificateur(livraisonCommandeFournisseur.getUtilisateurVerificateur().getUuid());
			dto.setNomUtilisateurVerificateur(livraisonCommandeFournisseur.getUtilisateurVerificateur().getUsername());
		}
		
		return dto;

	}

	public static ModePaiementDto toModePaiementDto(ModePaiement modePaiement) {
		ModePaiementDto dto = new ModePaiementDto();
		dto.setDescription(modePaiement.getDescription());
		dto.setLibelle(modePaiement.getLibelle());
		dto.setUuidBoutique(modePaiement.getBoutique().getUuid());
		dto.setUuidUtilisateur(modePaiement.getUtilisateur().getUuid());
		dto.setBoutique(modePaiement.getBoutique().getLibelleBoutique());
		dto.setUuid(modePaiement.getUuid());
		return dto;
	}

	public static PaiementCommandeVenteDto topaiementCommandeVenteDto(
		PaiementCommandeVente paiementCommandeVente) {
		PaiementCommandeVenteDto dto = new PaiementCommandeVenteDto();
		dto.setUuid(paiementCommandeVente.getUuid());
	    dto.setDateVersement(paiementCommandeVente.getDateVersement());
	    dto.setMontantVerse(paiementCommandeVente.getMontantVerse());
	    dto.setUuidRecuVersement(paiementCommandeVente.getRecuVersement().getUuid());
		dto.setUuidBoutique(paiementCommandeVente.getBoutique().getUuid());
		
		dto.setUuidUtilisateur(paiementCommandeVente.getUtilisateur().getUuid());
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
		dto.setCapitale(pays.getCapitale());
		dto.setMonnaie(pays.getMonnaie());
        dto.setUuid(pays.getUuid());
//		dto.setUuidBouique(pays.getUtilisateur().getUuid());
//		dto.setUuidUtilisateur(pays.getUtilisateur().getUuid());
		return dto;
	}

	public static ProduitDto toProduit(Produit produit) {
		return toProduit(produit, null);
	}
	
	public static ProduitDto toProduit(Produit produit, StockUniteVenteService stockUniteVenteService) {
		ProduitDto produitDto = new ProduitDto();
		produitDto.setUuid(produit.getUuid());
		
		// Quantités / prix / unités de vente (point de vente)
		if (stockUniteVenteService != null) {
			int quantiteTotale = stockUniteVenteService.calculateTotalBaseQuantity(produit);
			produitDto.setQuantite(quantiteTotale);
			produitDto.setQuantiteImage(quantiteTotale);

			List<StockUniteVente> stocks = stockUniteVenteService.getStocksForProduit(produit);
			produitDto.setPrixAchat(produit.getPrixAchat());
			produitDto.setPrixVente(produit.getPrixVente());
			if (!stocks.isEmpty()) {
				Optional<StockUniteVente> base = stocks.stream()
						.filter(s -> s.getTypeUniteDeVente() != null && s.getTypeUniteDeVente().getUnite() == 1)
						.findFirst();
				StockUniteVente ref = base.orElse(stocks.get(0));
				if (ref.getPrice() > 0) {
					produitDto.setPrixVente(ref.getPrice());
				}
			}

			List<TypeUniteDeVente> unites = produit.getArticle().getTypeUniteDeVentes();
			List<TypeUniteDeVenteDto> typeUniteDeVenteDtos = new ArrayList<>();
			if (unites != null) {
				for (TypeUniteDeVente u : unites) {
					TypeUniteDeVenteDto dto = new TypeUniteDeVenteDto();
					dto.setUuid(u.getUuid());
					dto.setTypeUniteEnum(u.getTypeUniteEnum());
					dto.setUnite(u.getUnite());
					dto.setQtite(stockUniteVenteService.getStockForUnit(produit, u));
					StockUniteVente stock = stocks.stream()
							.filter(s -> s.getTypeUniteDeVente() != null
									&& u.getUuid().equals(s.getTypeUniteDeVente().getUuid()))
							.findFirst()
							.orElse(null);
					if (stock != null) {
						dto.setPrice(stock.getPrice());
					}
					typeUniteDeVenteDtos.add(dto);
				}
			}
			produitDto.setTypeUniteDeVenteDtos(typeUniteDeVenteDtos);
		} else {
			produitDto.setQuantite(0);
			produitDto.setQuantiteImage(0);
			produitDto.setPrixAchat(0);
			produitDto.setPrixVente(0);
			produitDto.setTypeUniteDeVenteDtos(new ArrayList<>());
		}
		
		produitDto.setQuantiteVendu(0);
		produitDto.setQuantiteLivraison(0);
		produitDto.setQuantitePublish(0);
		if (produit.getFournisseur() != null) {
			produitDto.setUuidFournisseur(produit.getFournisseur().getUuid());
		} else {
			produitDto.setUuidFournisseur(null);
		}
		produitDto.setUuidArticle(produit.getArticle().getUuid());
		produitDto.setArticle(produit.getArticle().getLibelle());
		produitDto.setCategorie(produit.getArticle().getCategorie().getLibelle());
		produitDto.setDatePeremption(produit.getDatePeremption());
		produitDto.setDateCommande(produit.getDateEnregistrement());

		if (produit.getEmplacement() != null) {
			produitDto.setEmplacement(produit.getEmplacement().getCode() + " ==> " + produit.getEmplacement().getRayon());
			produitDto.setEtagere(produit.getEmplacement().getEtagere());
			produitDto.setUuidEmplacement(produit.getEmplacement().getUuid());
		}

		produitDto.setFournisseur(produit.getFournisseur() != null
				? produit.getFournisseur().getPrenom() + " " + produit.getFournisseur().getNom()
				: "");
		produitDto.setUuidBoutique(produit.getBoutique().getUuid());
		produitDto.setUuidUtilisateur(produit.getUtilisateur().getUuid());
		produitDto.setFinish(produit.isFinish());
		
		List<CaracteristiqueProduit> caracteristiqueProduits = produit.getCaracteristiqueProduits();
		List<CaracteristiqueProduitDto> caracteristiqueProduitDtos = new ArrayList<CaracteristiqueProduitDto>();
		caracteristiqueProduits.forEach(p -> caracteristiqueProduitDtos.add(Mapper.toCaracteristiqueProduit(p)));
		produitDto.setCaracteristiqueArticleDtos(caracteristiqueProduitDtos);
		
		return produitDto;
	}

	/*********************************************************
	 * **********************Produit**************************
	 */

	public static Produit toProduitDto(ProduitDto produitDto) {
		Produit produit = new Produit();
		produit.setUuid(produitDto.getUuid());
		produit.setPrixAchat(produitDto.getPrixAchat());
		produit.setPrixVente(produitDto.getPrixVente());
		produit.setQuantite(produitDto.getQuantite());
		produit.setQuantiteCommande(produitDto.getQuantiteCommande());
		produit.setQuantiteLivraison(produitDto.getQuantiteLivraison());
		produit.setQuantitePublish(produitDto.getQuantitePublish());
		produit.setQuantiteImage(produitDto.getQuantiteImage());
		// Les quantités sont maintenant gérées via StockUniteVente
		// Cette méthode est utilisée pour créer un produit basique, les stocks seront gérés séparément
		produit.setDateEnregistrement(produitDto.getDateCommande());
		produit.setDatePeremption(produitDto.getDatePeremption());
		return produit;
	}

	public static QuartierDto toQuartierDto(Quartier quartier) {
		QuartierDto dto = new QuartierDto();
		dto.setCommune(quartier.getCommune().getLibelle());
		dto.setLibelle(quartier.getLibelle());
		//dto.setPays(quartier.getCommune().getVille().getPays().getLibelle());
		dto.setUuid(quartier.getUuid());
		dto.setUuidCommune(quartier.getCommune().getUuid());
	//	dto.setUuidPays(quartier.getCommune().getVille().getPays().getUuid());
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
		if (utilisateur.getBoutique() != null) {
			dto.setBoutique(utilisateur.getBoutique().getUuid());
			dto.setLibelleBoutique(utilisateur.getBoutique().getLibelleBoutique());
		}
//		dto.setTypeDomaine(utilisateur.getBoutiquePrincipale().getDomain().getType());
//		dto.setDomaine(utilisateur.getBoutiquePrincipale().getDomain().getLibelle());
//		dto.setUuidDomain(utilisateur.getBoutiquePrincipale().getDomain().getUuid());
		
        if (utilisateur.getBoutique() != null && utilisateur.getBoutique().getStoredFile() != null) {
			
			byte[] encodeBase64 = Base64.getEncoder().encode(utilisateur.getBoutique().getStoredFile().getBytes());
		    String base64Encoded;
			try {
				base64Encoded = new String(encodeBase64, "UTF-8");
				dto.setImage("data:"+utilisateur.getBoutique().getStoredFile().getType()+";base64,"+base64Encoded);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
		}
		return dto;

	}
	
	
	
	
	
	public static UtilisateurDto toUtilisateurLoginDto(Utilisateur utilisateur) {
		UtilisateurDto dto = new UtilisateurDto();
		dto.setUuid(utilisateur.getUuid());
		dto.setUsername(utilisateur.getUsername());
		dto.setPhone(utilisateur.getPhone());
		dto.setRole(utilisateur.getRole());
		dto.setEmail(utilisateur.getEmail());
		dto.setMotDePasse(utilisateur.getPassword());
		dto.setAdresse(utilisateur.getAdresse());
		if (utilisateur.getBoutique() != null) {
			dto.setBoutique(utilisateur.getBoutique().getUuid());
			dto.setLibelleBoutique(utilisateur.getBoutique().getLibelleBoutique());
		}
//		dto.setTypeDomaine(utilisateur.getBoutiquePrincipale().getDomain().getType());
//		dto.setDomaine(utilisateur.getBoutiquePrincipale().getDomain().getLibelle());
//		dto.setUuidDomain(utilisateur.getBoutiquePrincipale().getDomain().getUuid());
		

		dto.setMonnaie("GNF");
		
        if (utilisateur.getBoutique() != null && utilisateur.getBoutique().getStoredFile() != null) {
			
			byte[] encodeBase64 = Base64.getEncoder().encode(utilisateur.getBoutique().getStoredFile().getBytes());
		    String base64Encoded;
			try {
				base64Encoded = new String(encodeBase64, "UTF-8");
				dto.setImage("data:"+utilisateur.getBoutique().getStoredFile().getType()+";base64,"+base64Encoded);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
		}
		return dto;

	}

	public static VilleDto toVilleDto(Ville ville) {
		VilleDto dto = new VilleDto();
		dto.setLibelle(ville.getLibelle());
		//dto.setPays(ville.getPays().getLibelle());
		dto.setUuid(ville.getUuid());
		//dto.setUuidPays(ville.getPays().getUuid());
		dto.setUuidBoutique(ville.getBoutique().getUuid());
		dto.setUuidUtilisateur(ville.getUtilisateur().getUuid());
		return dto;
	}
	
	public static DomainBoutiqueDto toDomainBoutiqueDto(DomainBoutique domainBoutique) {
		DomainBoutiqueDto dto = new DomainBoutiqueDto();
		dto.setBoutique(domainBoutique.getBoutique().getLibelleBoutique());
		dto.setDomaine(domainBoutique.getDomain().getLibelle());
		return dto;
	}


	public static PubliciteOperationDto toPubliciteOperation(PubliciteOperation publiciteOperation){
		PubliciteOperationDto dto = new PubliciteOperationDto();
		dto.setFavorite(publiciteOperation.isFavorite());
		dto.setInterested(publiciteOperation.isInterested());
		dto.setLike(publiciteOperation.isLike());
		dto.setUuidPublicite(publiciteOperation.getPublicite().getUuid());
		dto.setUuid(publiciteOperation.getUuid());
		dto.setPublish(publiciteOperation.getPublicite().isPublish());
		dto.setUser(publiciteOperation.getUtilisateur().getUsername());

		return dto;
	}

	public static PubliciteParamDto toPubliciteParam(PubliciteParametre publiciteParametre){
		PubliciteParamDto dto = new PubliciteParamDto();
		dto.setUuidPublicite(publiciteParametre.getPublicite().getUuid());
		dto.setValue(publiciteParametre.getValue());
		dto.setValue(publiciteParametre.getLibelle());
		return dto;
	}

	public static PubliciteDto toPublicite(Publicite publicite){
       PubliciteDto  dto = new PubliciteDto();
	   dto.setPrice(publicite.getPrice());
	   dto.setDescription(publicite.getDescription());
	   dto.setCategorie(publicite.getCategorie().getLibelle());
	   dto.setLibelle(publicite.getLibelle());
	   dto.setQuantite(publicite.getQuantite());
	   dto.setUtilisateur(publicite.getUtilisateur().getUsername());
	   dto.setUuid(publicite.getUuid());
	   dto.setUuidCategorie(publicite.getCategorie().getUuid());
	   dto.setUuidUtilisateur(publicite.getUtilisateur().getUuid());

	   List<PubliciteOperation> publiciteOperations = publicite.getPubliciteOperations();
	   List<PubliciteParametre> publiciteParametres = publicite.getPubliciteParametres();

	   List<PubliciteOperationDto> publiciteOperationDtos = new ArrayList<>();
	   List<PubliciteParamDto> publiciteParamDtos = new ArrayList<>();

	   publiciteOperations.forEach(p -> publiciteOperationDtos.add(Mapper.toPubliciteOperation(p)));
	   publiciteParametres.forEach(p -> publiciteParamDtos.add(Mapper.toPubliciteParam(p)));

	   dto.setPubliciteOperationDtos(publiciteOperationDtos);
	   dto.setPubliciteParamDtos(publiciteParamDtos);
	   return dto;
	}

	public static MountedDto toMountedDto(Utilisateur utilisateur,List<Categorie> categories){
		MountedDto dto = new MountedDto();

		UserDto  userDto = new UserDto();

		userDto.setAddresse(utilisateur.getAdresse());
		userDto.setPassword(utilisateur.getPassword());
		//userDto.setImage("");
		userDto.setRole(utilisateur.getRole().get(0).toString());
		userDto.setUsername(utilisateur.getUsername());
		userDto.setPhone(utilisateur.getPhone());
		userDto.setEmail(utilisateur.getEmail());

		dto.setUser(userDto);
		dto.setBoutique(Mapper.toBoutiqueDto(utilisateur.getBoutique()));

		List<CategorieDto> categorieDtos = new ArrayList<>();
		categories.forEach(c->categorieDtos.add(Mapper.toCategorieDto(c)));

//		List<PubliciteDto> publiciteDtos = new ArrayList<>();
//		publicites.forEach(p->publiciteDtos.add(Mapper.toPublicite(p)));

		dto.setCategories(categorieDtos);
		//dto.setPublicites(publiciteDtos);



		return dto;
	}

	public static UserDto toUserDto(Utilisateur utilisateur){
		UserDto dto = new UserDto();
		dto.setEmail(utilisateur.getEmail());
		dto.setAddresse(utilisateur.getCustomer().getUtilisateur().getAdresse());

		if(utilisateur.getRole().get(0).equals(EnumRole.BOUTIQUE)){
			dto.setBoutique(utilisateur.getBoutique().getLibelleBoutique());
			dto.setNombreCommande(utilisateur.getBoutique().getCommandeVentes().size());
			if(utilisateur.getBoutique().getStoredFile() != null){
				byte[] encodeBase64 = Base64.getEncoder().encode(utilisateur.getBoutique().getStoredFile().getBytes());
				String base64Encoded;
				try {
					base64Encoded = new String(encodeBase64, "UTF-8");
					dto.setLogoBoutique("data:"+utilisateur.getBoutique().getStoredFile().getType()+";base64,"+base64Encoded);
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		dto.setUsername(utilisateur.getUsername());
		dto.setPhone(utilisateur.getPhone());
		dto.setUuid(utilisateur.getUuid());
		dto.setRole(utilisateur.getRole().toString());
		dto.setPassword(utilisateur.getPassword());
		dto.setNombreCommande(utilisateur.getListeCommandeVente().size());
		if(utilisateur.getCustomer().getStoredFile() !=null){
			byte[] encodeBase64 = Base64.getEncoder().encode(utilisateur.getCustomer().getStoredFile().getBytes());
			String base64Encoded;
			try {
				base64Encoded = new String(encodeBase64, "UTF-8");
				dto.setImageProfile("data:"+utilisateur.getCustomer().getStoredFile().getType()+";base64,"+base64Encoded);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			dto.setUuidImageProfile(utilisateur.getCustomer().getStoredFile().getUuid());
		}


		return dto;
	}
	
	public static TypeShopDto toTypeShopDto(TypeShop typeShop) {
		TypeShopDto dto = new TypeShopDto();
					dto.setCode(typeShop.getCode());
					dto.setLibelle(typeShop.getLibelle());
					dto.setUuid(typeShop.getUuid());
		return dto;
	}



	public static TransactionDto toTransactionDto(Transaction transaction) {
		TransactionDto dto = new TransactionDto();
		dto.setUuid(transaction.getUuid());
		dto.setReference(transaction.getReference());
		dto.setTypeTransaction(transaction.getType());
		dto.setMontant(transaction.getMontant());
		dto.setStatutTransaction(transaction.getStatut());
		dto.setDateTransaction(transaction.getDateTransaction());
		dto.setModePaiement(Mapper.toModePaiementDto(transaction.getModePaiement()));
		if (transaction.getCommandeVente() != null) {
			dto.setUuidCommandeVente(transaction.getCommandeVente().getUuid());
		}

		if(transaction.getStoredFile() != null){
			dto.setUuidReceipt(transaction.getStoredFile().getUuid());

			byte[] encodeBase64 = Base64.getEncoder().encode(transaction.getStoredFile().getBytes());
			String base64Encoded;
			try {
				base64Encoded = new String(encodeBase64, "UTF-8");
				dto.setReceipt("data:"+transaction.getStoredFile().getType()+";base64,"+base64Encoded);
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return dto;
	}


	public static Transaction toTransaction(TransactionDto dto) {
		Transaction transaction = new Transaction();

		transaction.setReference(dto.getReference());
		transaction.setType(dto.getTypeTransaction());
		transaction.setMontant(dto.getMontant());
		transaction.setDateTransaction(new Date());
		return transaction;
	}


	public static CaisseJournaliereDto toCaisseJournaliere(CaisseJournaliere caisse) {
		CaisseJournaliereDto dto = new CaisseJournaliereDto();
		dto.setUuid(caisse.getUuid());
		dto.setDateCaisse(caisse.getDateCaisse());
		dto.setSoldeOuverture(caisse.getSoldeOuverture());
		dto.setSoldeFermeture(caisse.getSoldeFermeture());
		dto.setTotalEncaissement(caisse.getTotalEncaissement());
		dto.setTotalDecaissement(caisse.getTotalDecaissement());
		dto.setStatutCaisse(caisse.getStatutCaisse());
		dto.setDateFermeture(caisse.getDateFermeture());

		// Calcul du solde théorique
		BigDecimal soldeCalcule = caisse.getSoldeOuverture()
				.add(caisse.getTotalEncaissement())
				.subtract(caisse.getTotalDecaissement());
		dto.setSoldeCalcule(soldeCalcule);

		return dto;
	}



}
