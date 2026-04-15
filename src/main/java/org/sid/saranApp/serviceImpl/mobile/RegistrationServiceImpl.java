package org.sid.saranApp.serviceImpl.mobile;

import org.sid.saranApp.dto.DomainBoutiqueDto;
import org.sid.saranApp.dto.DomainDto;
import org.sid.saranApp.dto.UtilisateurDto;
import org.sid.saranApp.dto.mobile.*;
import org.sid.saranApp.enume.EnumRole;
import org.sid.saranApp.enume.EnumTypeInstanceBoutique;
import org.sid.saranApp.enume.MonnaieEnum;
import org.sid.saranApp.mapper.Mapper;
import org.sid.saranApp.model.*;
import org.sid.saranApp.repository.*;
import org.sid.saranApp.service.mobile.RegistrationService;
import org.sid.saranApp.serviceImpl.UtilisateurServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RegistrationServiceImpl implements RegistrationService {
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private BoutiqueRepository boutiqueRepository;
    @Autowired
    private UtilisateurServiceImpl utilisateurService;
    @Autowired
    private PaysRepository paysRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private StoredFileRepository storedFileRepository;
    @Autowired
    private DomainBoutiqueRepository domainBoutiqueRepository;
    @Autowired
    private DomainRepository domainRepository;
    @Autowired
    private CategorieRepository categorieRepository;
    @Autowired
    private CaracteristiqueArticleRepository caracteristiqueArticleRepository;
    @Autowired
    private TypeShopRepository typeShopRepository;

    Logger logger = LoggerFactory.getLogger(RegistrationServiceImpl.class);

    @Override
    public RegistrationDto addRegistration(RegistrationDto registrationDto) {
        Boutique boutique = new Boutique();
        RegistrationDto registrationDto1 = new RegistrationDto();
        TypeShop typeShop = typeShopRepository.findById(registrationDto.getBoutique().getUuidTypeShop()).orElseThrow(null);
        Optional<Utilisateur> utilisateur = utilisateurRepository.findByEmail(registrationDto.getCustomer().getCustomerPhone());
        ResponseDto responseDto = new ResponseDto();
        UtilisateurDto dto = new UtilisateurDto();
        if(!utilisateur.isPresent()){
            // Pays pays = paysRepository.findById(registrationDto.getBoutique().getUuidPays()).orElseThrow(null);
                if(registrationDto.getBoutique() !=null){
                    String uuid = UUID.randomUUID().toString().replace("-", "").toUpperCase();
                    // StoredFile storedFileBoutique = storedFileRepository.findById(registrationDto.getBoutique().getUuidStoredFile()).orElseThrow(null);
                    boutique.setCountryCode(registrationDto.getBoutique().getCountryCode());
                    boutique.setEmailBoutique(registrationDto.getCustomer().getCustomerEmail());
                    boutique.setEnumTypeShop(registrationDto.getBoutique().getBoutique());
                    boutique.setStoredFile(null);
                    boutique.setTypeShop(typeShop);
                    boutique.setMonnaie(null);
                    boutique.setCode(uuid.substring(0, 6));
                    boutique.setPhoneBoutique(registrationDto.getBoutique().getBoutiqueContact());
                    boutique.setAdresse(registrationDto.getBoutique().getBoutiqueAdress());
                    boutique.setLibelleBoutique(registrationDto.getBoutique().getBoutiqueName());
//                    if(!registrationDto.getBoutique().getUuidBoutique().equals(null)){
//                        Boutique boutiquePrincipale = boutiqueRepository.findById(registrationDto.getBoutique().getUuidBoutique()).orElseThrow(null);
//                        boutique.setEnumTypeInstanceBoutique(EnumTypeInstanceBoutique.AUXILIAIRE);
//                        boutiquePrincipale.setBoutiquePrincipale(boutiquePrincipale);
//
//
//                    }
                    boutique.setEnumTypeInstanceBoutique(EnumTypeInstanceBoutique.PRINCIPALE);
                    boutique.setBoutiquePrincipale(null);

                    boutique = boutiqueRepository.save(boutique);
                    dto.setBoutique(boutique.getUuid());

                    Mapper.toBoutiqueMobileDto(boutique);
                }



                dto.setMotDePasse(registrationDto.getCustomer().getPassword());
                dto.setEmail(registrationDto.getCustomer().getCustomerEmail());
                dto.setPhone(registrationDto.getCustomer().getCustomerPhone());
                dto.setUsername(registrationDto.getCustomer().getPrenom()+" "+registrationDto.getCustomer().getNom());
                dto.setAdresse(registrationDto.getCustomer().getCustomerAdress());
                List<EnumRole> enumRoles = new ArrayList<>();
                enumRoles.add(registrationDto.getCustomer().getRole());
                dto.setRole(enumRoles);

                Utilisateur  newUtilisateur = utilisateurService.addUser(dto);;

                CustomerBoutique customerBoutique = new CustomerBoutique();
                if(registrationDto.getCustomer() != null){

                    // StoredFile storedFile = storedFileRepository.findById(registrationDto.getCustomer().getUuidStoredFile()).orElseThrow(null);

                    customerBoutique.setCustomerPhone(registrationDto.getCustomer().getCustomerPhone());
                    customerBoutique.setGenre(registrationDto.getCustomer().getGenre());
                    customerBoutique.setNom(registrationDto.getCustomer().getNom());
                    customerBoutique.setPrenom(registrationDto.getCustomer().getPrenom());
                    customerBoutique.setCustomerEmail(registrationDto.getCustomer().getCustomerEmail());
                    customerBoutique.setStoredFile(null);
                    customerBoutique.setUtilisateur(newUtilisateur);
                    logger.info("utilisateur {}",customerBoutique.getUtilisateur().getUuid());
                    customerBoutique = customerRepository.save(customerBoutique);
                    dto.setBoutique(null);

                    registrationDto1 = Mapper.toRegistrationDto(newUtilisateur,customerBoutique);

                    if(boutique.getUuid() != null && customerBoutique.getUuid() != null && newUtilisateur.getUuid() != null){
                        responseDto.setMessage("success");
                        responseDto.setCode(200);
                        if(registrationDto1 != null){
                            responseDto.setLoad(true);
                        }else{
                            responseDto.setLoad(false);
                        }
                        registrationDto1.setResponse(responseDto);
                    }else{
                        responseDto.setMessage("success");
                        responseDto.setCode(200);
                        if(registrationDto1 != null){
                            responseDto.setLoad(true);
                        }else{
                            responseDto.setLoad(false);
                        }
                        registrationDto1.setResponse(responseDto);
                    }

//                    if(!customerBoutique.getUuid().isEmpty() && !newUtilisateur.getUuid().isEmpty()){
//
//                    }
                }





        }else{
            logger.info("uuid {}", utilisateur.get().getUuid());
                responseDto.setMessage("utilisateur existe");
                responseDto.setCode(200);

                registrationDto1 = Mapper.toRegistrationDto(utilisateur.get(),utilisateur.get().getCustomer());
                if(registrationDto1 != null){
                    responseDto.setLoad(true);
                }else{
                    responseDto.setLoad(false);
                }
                registrationDto1.setResponse(responseDto);
        }


        return registrationDto1;
    }

    @Override
    public RegistrationDto updateRegistration(RegistrationDto registrationDto, String uuid) {
        return null;
    }

    @Override
    public UserDto getRegistration() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Utilisateur utilisateur = utilisateurRepository.findByEmail(auth.getName()).orElseThrow(null);
        UserDto  userDto = Mapper.toUserDto(utilisateur);
         if(utilisateur.getUuid() != null){
             userDto.setCode(200);
             userDto.setLoad(true);
             userDto.setMessage("User existe");
         }else{
             userDto.setCode(400);
             userDto.setLoad(false);
             userDto.setMessage("not found");
         }
        return userDto;
    }

    @Override
    public List<RegistrationDto> findAllRegistration() {
        List<RegistrationDto> registrationDtos = new ArrayList<>();
        List<Utilisateur> utilisateurs = utilisateurRepository.findAll();
        utilisateurs.forEach(r -> {
            RegistrationDto registrationDto = Mapper.toRegistrationDto(r,r.getCustomer());
            registrationDto.getResponse().setCode(200);
            registrationDto.getResponse().setLoad(true);
            registrationDto.getResponse().setMessage("Liste charge");
            registrationDtos.add(registrationDto);
        });
        return registrationDtos;
    }

    @Override
    public ShopMountedDto shopMounted() {
        ShopMountedDto shopMountedDto = new ShopMountedDto();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Utilisateur utilisateur = utilisateurRepository.findByEmail(auth.getName()).orElseThrow(null);

        shopMountedDto.setBoutiqueMobileDto(Mapper.toBoutiqueMobileDto(utilisateur.getBoutique()));

        StatistiqueDto statistiqueDto = new StatistiqueDto();
        statistiqueDto.setNombreTotalDeVente(utilisateur.getBoutique().getCommandeVentes().size());

        double montantCommande = 0;
        double montantCommandeArticle = 0;

        montantCommande = utilisateur.getBoutique().getCommandeVentes().stream().mapToDouble(CommandeVente::getMontantCommade).sum();

        // Le prix est maintenant dans StockUniteVente, pas directement dans Produit
        // Calculer le montant total des produits depuis StockUniteVente si nécessaire
        // montantCommandeArticle += utilisateur.getBoutique().getProduits().stream().mapToDouble(Produit::getPrixVente).sum();

        statistiqueDto.setMontantTotalDeArticle(montantCommandeArticle);
        statistiqueDto.setMontantTotalDeVente(montantCommande);
        statistiqueDto.setNombreTotalDeArticle(utilisateur.getBoutique().getProduits().size());

        shopMountedDto.setStatistiqueDto(statistiqueDto);


        return shopMountedDto;
    }

    @Override
    public RegistrationDto getRegistrationByUserConnected() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Utilisateur utilisateur = utilisateurRepository.findByEmail(auth.getName()).orElseThrow(null);
        return Mapper.toRegistrationDto(utilisateur,utilisateur.getCustomer());
    }

    @Override
    public RegistrationDto finalizeConfigProfil(String uuidProfileImage, String uuidLogo, List<DomainDto> domainDtos, MonnaieEnum monnaie) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Utilisateur utilisateur = utilisateurRepository.findByEmail(auth.getName()).orElseThrow(null);

        CustomerBoutique customerBoutique = customerRepository.findById(utilisateur.getCustomer().getUuid()).orElseThrow(null);
        StoredFile storedFileProfile = storedFileRepository.findById(uuidProfileImage).orElseThrow(null);
        customerBoutique.setStoredFile(storedFileProfile);
        customerRepository.save(customerBoutique);

        Boutique boutique = boutiqueRepository.findById(utilisateur.getBoutique().getUuid()).orElseThrow(null);
        StoredFile  storedFile = storedFileRepository.findById(uuidLogo).orElseThrow(null);
        boutique.setStoredFile(storedFile);
        boutique.setMonnaie(monnaie);
        boutique = boutiqueRepository.save(boutique);


        for (DomainDto domainDto: domainDtos){
            Domain domain = domainRepository.findById(domainDto.getUuid()).orElseThrow(null);
            DomainBoutique domainBoutique =  new DomainBoutique();
            domainBoutique.setDomain(domain);
            domainBoutique.setBoutique(boutique);
            domainBoutique = domainBoutiqueRepository.save(domainBoutique);

            for(DomainCategorie domainCategorie:domain.getDomainCategories()){
                Categorie categorie = new Categorie();
                categorie.setBoutique(boutique);
                categorie.setDescription(domainCategorie.getDescription());
                categorie.setLibelle(domainCategorie.getLibelle());
                categorie.setUtilisateur(utilisateur);
                categorie = categorieRepository.save(categorie);
                for (DomainCategorieParam domainCategorieParam:domainCategorie.getDomainCategorieParams()){
                    CaracteristiqueArticle caracteristiqueArticle = new CaracteristiqueArticle();
                    caracteristiqueArticle.setCategorie(categorie);
                    caracteristiqueArticle.setLibelle(domainCategorieParam.getLibelle());
                    caracteristiqueArticle = caracteristiqueArticleRepository.save(caracteristiqueArticle);
                }
            }



        }

        return Mapper.toRegistrationDto(utilisateur,utilisateur.getCustomer());
    }
}
