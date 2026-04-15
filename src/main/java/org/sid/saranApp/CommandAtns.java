package org.sid.saranApp;

import org.sid.saranApp.constant.ConstantAPP;
import org.sid.saranApp.dto.UtilisateurDto;
import org.sid.saranApp.enume.EnumRole;
import org.sid.saranApp.model.Boutique;
import org.sid.saranApp.model.Domain;
import org.sid.saranApp.model.Pays;
import org.sid.saranApp.model.Utilisateur;
import org.sid.saranApp.repository.BoutiqueRepository;
import org.sid.saranApp.repository.DomainRepository;
import org.sid.saranApp.repository.PaysRepository;
import org.sid.saranApp.repository.UtilisateurRepository;
import org.sid.saranApp.serviceImpl.UtilisateurServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class CommandAtns {
	
	@Autowired
	private BoutiqueRepository boutiqueRepository;
	@Autowired
	private UtilisateurRepository utilisateurRepository;
	@Autowired
	private UtilisateurServiceImpl serviceImpl;
	@Autowired
	private DomainRepository domainRepository;
	@Autowired
	private PaysRepository paysRepository;
	
	@Bean
	void  addUser(){
		
		Boutique boutiqueFromDb = boutiqueRepository.getBoutique(ConstantAPP.BOUTIQUE);
		Boutique  boutique = new Boutique(); 
		
		
		
		
		UtilisateurDto utilisateur = new UtilisateurDto();

		Pays pays = new Pays();


		
		if(boutiqueFromDb == null) {
			pays.setMonnaie(ConstantAPP.MONNAIE);
			pays.setCapitale(ConstantAPP.CAPITALE);
			pays.setLibelle(ConstantAPP.PAYS);
			pays = paysRepository.save(pays);
			Domain domain = new Domain();
			domain.setLibelle("ATNS");
			domain.setDescription("ATNS");
			domain.setType("ATNS");
			
			domain = domainRepository.save(domain);
			
			boutique.setAdresse(pays.getLibelle());
			boutique.setCountryCode("GN");
			boutique.setDescriptionBoutique("atns");
			boutique.setEmailBoutique(ConstantAPP.BOUTIQUE_EMAIL);
			boutique.setLibelleBoutique(ConstantAPP.BOUTIQUE);
			boutique.setStoredFile(null);
			//boutique.setPays(ConstantAPP.PAYS);
			//boutique.setDomain(domain);
			
			boutique = boutiqueRepository.save(boutique);
			
			
			
			utilisateur.setAdresse(pays.getLibelle());
			utilisateur.setBoutique(boutique.getUuid());
			utilisateur.setEmail(ConstantAPP.SUPER_ADMIN_EMAIL);
			utilisateur.setMotDePasse(ConstantAPP.SUPER_ADMIN_PASSWORD);
			utilisateur.setUsername(ConstantAPP.SUPER_ADMIN_USERNAME);
			utilisateur.setPhone("621000000");
			List<EnumRole> enumRoles = new ArrayList<EnumRole>();
			enumRoles.add(EnumRole.SUPER_ADMIN);
			utilisateur.setRole(enumRoles);
			utilisateur = serviceImpl.addUtilisateur(utilisateur);

			
		}else {
			
			Utilisateur user = utilisateurRepository.findByEmail(ConstantAPP.SUPER_ADMIN_EMAIL).orElseThrow(null);
			
			
//			if(user.getEmail() != ConstantAPP.SUPER_ADMIN_EMAIL) {
//				utilisateur.setAdresse(ConstantAPP.PAYS);
//				utilisateur.setBoutique(boutiqueFromDb.getUuid());
//				utilisateur.setEmail(ConstantAPP.SUPER_ADMIN_EMAIL);
//				utilisateur.setMotDePasse(ConstantAPP.SUPER_ADMIN_PASSWORD);
//				utilisateur.setUsername(ConstantAPP.SUPER_ADMIN_USERNAME);
//				List<EnumRole> enumRoles = new ArrayList<EnumRole>();
//				enumRoles.add(EnumRole.SUPER_ADMIN);
//				utilisateur.setRole(enumRoles);
//				utilisateur = serviceImpl.addUtilisateur(utilisateur);
//			}
//				else {
////				utilisateur.setAdresse(ConstantAPP.PAYS);
////				utilisateur.setBoutique(boutiqueFromDb.getUuid());
////				utilisateur.setEmail(ConstantAPP.SUPER_ADMIN_EMAIL);
////				utilisateur.setMotDePasse(ConstantAPP.SUPER_ADMIN_PASSWORD);
////				utilisateur.setUsername(ConstantAPP.SUPER_ADMIN_USERNAME);
////				List<EnumRole> enumRoles = new ArrayList<EnumRole>();
////				enumRoles.add(EnumRole.SUPER_ADMIN);
////				utilisateur.setRole(enumRoles);
////				utilisateur = serviceImpl.addUtilisateur(utilisateur);
//			}
			
		}
	}

}
