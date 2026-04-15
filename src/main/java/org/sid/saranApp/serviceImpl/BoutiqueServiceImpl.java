package org.sid.saranApp.serviceImpl;

import org.sid.saranApp.dto.BoutiqueDto;
import org.sid.saranApp.dto.DomainBoutiqueDto;
import org.sid.saranApp.dto.PageDataDto;
import org.sid.saranApp.dto.UtilisateurDto;
import org.sid.saranApp.enume.EnumRole;
import org.sid.saranApp.mapper.Mapper;
import org.sid.saranApp.model.*;
import org.sid.saranApp.repository.*;
import org.sid.saranApp.exception.BusinessException;
import org.sid.saranApp.service.BoutiqueService;
import org.sid.saranApp.service.UtilisateurService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@Service
public class BoutiqueServiceImpl implements BoutiqueService {

	@Autowired
	private BoutiqueRepository boutiqueRepository;
	@Autowired
	private UtilisateurRepository utilisateurRepository;
	@Autowired
	private UtilisateurService utilisateurService;
	@Autowired
	private StoredFileRepository storedFileRepository;
	@Autowired
	private DomainBoutiqueRepository domainBoutiqueRepository;
	@Autowired
	private BoutiqueDomainRepository boutiqueDomainRepository;
	@Autowired
	private CategorieRepository categorieRepository;
	@Autowired
	private CaracteristiqueArticleServiceImpl articleServiceImpl;
	
	@Autowired
	private CaracteristiqueArticleRepository caracteristiqueArticleRepository;
	@Autowired
	private DomainCategorieParamRepository domainCategorieParamRepository;
	@Autowired
	private DomainRepository domainRepository;
	@Autowired
	private PaysRepository paysRepository;
	@Autowired
	private UtilisateurServiceImpl utilisateurServiceImpl;


	Logger logger = LoggerFactory.getLogger(JwtUserDetailsService.class);

	@Override
	public BoutiqueDto addBoutique(BoutiqueDto boutiqueDto) {
		// TODO Auto-generated method stub
		String uuid = UUID.randomUUID().toString().replace("-", "").toUpperCase();
		Boutique boutique = new Boutique();
		//Pays pays = paysRepository.findById(boutiqueDto.getUuidPays()).orElseThrow(null);
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		logger.info("hello {}",auth.getName());
		StoredFile storedFile = storedFileRepository.findById(boutiqueDto.getUuidStoreFile())
				.orElseThrow(() -> new BusinessException("Fichier non trouvé pour l'UUID : " + boutiqueDto.getUuidStoreFile()));
		boutique.setAdresse(boutiqueDto.getAdresse());
		boutique.setDescriptionBoutique(boutiqueDto.getDescriptionBoutique());
		boutique.setEmailBoutique(boutiqueDto.getEmail());
		boutique.setLibelleBoutique(boutiqueDto.getLibelleBoutique());
		boutique.setPhoneBoutique(boutiqueDto.getPhoneBoutique());
		boutique.setSiteBoutique("h");
		boutique.setStoredFile(storedFile);
		boutique.setAdresse(boutiqueDto.getAdresse());
		boutique.setCountryCode(boutique.getCountryCode());
		boutique.setCode(uuid.substring(0, 6));
		boutique.setMonnaie(null);
		
		// Mapping des nouveaux champs
		boutique.setDevise(boutiqueDto.getDevise());
		boutique.setLangue(boutiqueDto.getLangue());
		boutique.setSeuilAlerteStock(boutiqueDto.getSeuilAlerteStock());
		boutique.setMethodeValorisation(boutiqueDto.getMethodeValorisation());
		boutique.setApprovisionnementAutomatique(boutiqueDto.isApprovisionnementAutomatique());
		boutique.setQuantiteACommander(boutiqueDto.getQuantiteACommander());
		boutique.setImpressionTicket(boutiqueDto.isImpressionTicket());
		boutique.setImpressionFacture(boutiqueDto.isImpressionFacture());
		boutique.setDevis(boutiqueDto.isDevis());
		boutique.setDette(boutiqueDto.isDette());
		Boutique boutiqueSave = boutiqueRepository.save(boutique);


		//add domain boutique
		for (DomainBoutiqueDto domainBoutiqueDto:boutiqueDto.getDomainBoutiqueDtos()){
			Domain domain = domainRepository.findById(domainBoutiqueDto.getUuidDomain())
					.orElseThrow(() -> new BusinessException("Domaine non trouvé : " + domainBoutiqueDto.getUuidDomain()));
			DomainBoutique domainBoutique =  new DomainBoutique();
			domainBoutique.setDomain(domain);
			domainBoutique.setBoutique(boutiqueSave);
			domainBoutique = domainBoutiqueRepository.save(domainBoutique);

			for(DomainCategorie domainCategorie:domain.getDomainCategories()){
				for (DomainCategorieParam domainCategorieParam:domainCategorie.getDomainCategorieParams()){
					Categorie categorie = new Categorie();
					categorie.setBoutique(boutiqueSave);
					categorie.setDescription(domainCategorie.getDescription());
					categorie.setLibelle(domainCategorie.getLibelle());
					categorie = categorieRepository.save(categorie);
					CaracteristiqueArticle caracteristiqueArticle = new CaracteristiqueArticle();
					caracteristiqueArticle.setCategorie(categorie);
					caracteristiqueArticle.setLibelle(domainCategorieParam.getLibelle());
					caracteristiqueArticle = caracteristiqueArticleRepository.save(caracteristiqueArticle);
				}
			}



		}
		
		List<String> domaines = boutiqueDto.getDomaines();
		
		
		UtilisateurDto dto = new UtilisateurDto();
		dto.setBoutique(boutiqueSave.getUuid());
		dto.setMotDePasse(boutiqueDto.getPassword());
		dto.setEmail(boutiqueDto.getEmail());
		dto.setPhone(boutiqueSave.getPhoneBoutique());
		dto.setUsername(boutiqueDto.getUtilisateur());
		dto.setAdresse(boutiqueDto.getAdresse());
		List<EnumRole> enumRoles = new ArrayList<EnumRole>();

		enumRoles.add(boutiqueDto.getRole());
		dto.setRole(enumRoles);
		
		// utilisateurService.addUtilisateur(dto);
		String utilisateur = utilisateurService.addUtilisateur(dto).getUuid();
		Utilisateur user = utilisateurRepository.findById(utilisateur)
				.orElseThrow(() -> new BusinessException("Utilisateur non trouvé : " + utilisateur));
		
		
		
//		for (Iterator iterator = domaines.iterator(); iterator.hasNext();) {
//			String string = (String) iterator.next();
//			Categorie categorie = new Categorie();
//			categorie.setBoutique(boutiqueSave);
//			categorie.setLibelle(string);
//			categorie.setUtilisateur(user);
//			categorie = categorieRepository.save(categorie);
//			String[] caracteristiques = null ;
//
//			List<DomainCategorieParam> domainCategorieParams = domainCategorieParamRepository.listeDomainCategorieParamByDomainCategorieLibelle(string);
//			for (Iterator iterator2 = domainCategorieParams.iterator(); iterator2.hasNext();) {
//				DomainCategorieParam domainCategorieParam = (DomainCategorieParam) iterator2.next();
//
//				    CaracteristiqueArticle caracteristiqueArticle = new CaracteristiqueArticle();
//		            caracteristiqueArticle.setCategorie(categorie);
//		            caracteristiqueArticle.setLibelle(domainCategorieParam.getLibelle());
//		            caracteristiqueArticle = caracteristiqueArticleRepository.save(caracteristiqueArticle);
//
//			}
//
//		}
		
//		for (Iterator iterator = domaines.iterator(); iterator.hasNext();) {
//			String string = (String) iterator.next();
//			DomainBoutique domainBoutique = domainBoutiqueRepository.getDomainBoutiqueByLibelle(string);
//			
//			BoutiqueDomain boutiqueDomain = new BoutiqueDomain();
//			boutiqueDomain.setBoutique(boutiqueSave);
//			boutiqueDomain.setDomainBoutique(domainBoutique);
//			boutiqueDomainRepository.save(boutiqueDomain);
//		}
//		
		
		
		
		return Mapper.toBoutiqueDto(boutiqueSave);
	}

	@Override
	public void deleteBoutique(String uuid) {
		// TODO Auto-generated method stub
		boutiqueRepository.deleteById(uuid);

	}

	@Override
	public BoutiqueDto synchronization(String code) {
		Utilisateur user = utilisateurServiceImpl.getCurentUtilisateur();
		Boutique boutique = boutiqueRepository.findByCode(code)
				.orElseThrow(() -> new BusinessException("Boutique non trouvée pour le code : " + code));
		if(!boutique.getCode().isEmpty()){
			boutique.setBoutiquePrincipale(user.getBoutique());
			boutique = boutiqueRepository.save(boutique);
		}
		return Mapper.toBoutiqueDto(boutique);
	}

	@Override
	public BoutiqueDto getByCode(String code) {
		Boutique boutique = boutiqueRepository.findByCode(code)
				.orElseThrow(() -> new BusinessException("Boutique non trouvée pour le code : " + code));
		return Mapper.toBoutiqueDto(boutique);
	}

	@Override
	public List<BoutiqueDto> findAll() {
		// TODO Auto-generated method stub
		List<Boutique> boutiques = boutiqueRepository.findAll();
		List<BoutiqueDto> boutiqueDtos = new ArrayList<BoutiqueDto>();
		boutiques.forEach(boutique -> boutiqueDtos.add(Mapper.toBoutiqueDto(boutique)));
		return boutiqueDtos;
	}

	@Override
	public BoutiqueDto getBoutique(String uuid) {
		// TODO Auto-generated method stub
		Boutique boutique = boutiqueRepository.findById(uuid)
				.orElseThrow(() -> new BusinessException("Boutique non trouvée : " + uuid));
		return Mapper.toBoutiqueDto(boutique);
	}

	@Override
	public BoutiqueDto updateBoutique(BoutiqueDto boutiqueDto, String uuid) {
		Domain domain = domainRepository.findById(boutiqueDto.getUuidDomain())
				.orElseThrow(() -> new BusinessException("Domaine non trouvé : " + boutiqueDto.getUuidDomain()));
		// TODO Auto-generated method stub
		Boutique boutique = boutiqueRepository.findById(uuid)
				.orElseThrow(() -> new BusinessException("Boutique non trouvée : " + uuid));
		boutique.setAdresse(boutiqueDto.getAdresse());
		boutique.setDescriptionBoutique(boutiqueDto.getDescriptionBoutique());
		boutique.setEmailBoutique(boutiqueDto.getEmailBoutique());
		boutique.setLibelleBoutique(boutiqueDto.getLibelleBoutique());
		boutique.setPhoneBoutique(boutiqueDto.getPhoneBoutique());
		boutique.setSiteBoutique(boutiqueDto.getSiteBoutique());
		
		// Mise à jour des nouveaux champs
		boutique.setDevise(boutiqueDto.getDevise());
		boutique.setLangue(boutiqueDto.getLangue());
		boutique.setSeuilAlerteStock(boutiqueDto.getSeuilAlerteStock());
		boutique.setMethodeValorisation(boutiqueDto.getMethodeValorisation());
		boutique.setApprovisionnementAutomatique(boutiqueDto.isApprovisionnementAutomatique());
		boutique.setQuantiteACommander(boutiqueDto.getQuantiteACommander());
		boutique.setImpressionTicket(boutiqueDto.isImpressionTicket());
		boutique.setImpressionFacture(boutiqueDto.isImpressionFacture());
		boutique.setDevis(boutiqueDto.isDevis());
		boutique.setDette(boutiqueDto.isDette());
		Boutique boutiqueSave = boutiqueRepository.save(boutique);
		return Mapper.toBoutiqueDto(boutiqueSave);
	}

	@Override
	public PageDataDto<BoutiqueDto> listeBoutiques(int page, int size, String key) {
		// TODO Auto-generated method stub
		return null;
	}

}
