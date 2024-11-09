package org.sid.saranApp.serviceImpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.sid.saranApp.dto.BoutiqueDto;
import org.sid.saranApp.dto.PageDataDto;
import org.sid.saranApp.dto.UtilisateurDto;
import org.sid.saranApp.enume.EnumRole;
import org.sid.saranApp.mapper.Mapper;
import org.sid.saranApp.model.Boutique;
import org.sid.saranApp.model.BoutiqueDomain;
import org.sid.saranApp.model.DomainBoutique;
import org.sid.saranApp.model.StoredFile;
import org.sid.saranApp.repository.BoutiqueDomainRepository;
import org.sid.saranApp.repository.BoutiqueRepository;
import org.sid.saranApp.repository.DomainBoutiqueRepository;
import org.sid.saranApp.repository.StoredFileRepository;
import org.sid.saranApp.repository.UtilisateurRepository;
import org.sid.saranApp.service.BoutiqueService;
import org.sid.saranApp.service.UtilisateurService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	Logger logger = LoggerFactory.getLogger(JwtUserDetailsService.class);

	@Override
	public BoutiqueDto addBoutique(BoutiqueDto boutiqueDto) {
		// TODO Auto-generated method stub
		Boutique boutique = new Boutique();

//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		logger.info("hello {}",auth.getName());
		StoredFile storedFile = storedFileRepository.findById(boutiqueDto.getUuidStoreFile()).orElseThrow(null);
		boutique.setAdresse(boutiqueDto.getAdresse());
		boutique.setDescriptionBoutique(boutiqueDto.getDescriptionBoutique());
		boutique.setEmailBoutique(boutiqueDto.getEmail());
		boutique.setLibelleBoutique(boutiqueDto.getLibelleBoutique());
		boutique.setPhoneBoutique(boutiqueDto.getPhoneBoutique());
		boutique.setSiteBoutique("h");
		boutique.setTypeBoutique(boutiqueDto.getTypeBoutique());
		boutique.setStoredFile(storedFile);
		boutique.setAdresse(boutiqueDto.getAdresse());
		boutique.setPays(boutiqueDto.getAdresse());
		Boutique boutiqueSave = boutiqueRepository.save(boutique);
		
		List<String> domaines = boutiqueDto.getDomaines();
		
		for (Iterator iterator = domaines.iterator(); iterator.hasNext();) {
			String string = (String) iterator.next();
			DomainBoutique domainBoutique = domainBoutiqueRepository.getDomainBoutiqueByLibelle(string);
			
			BoutiqueDomain boutiqueDomain = new BoutiqueDomain();
			boutiqueDomain.setBoutique(boutiqueSave);
			boutiqueDomain.setDomainBoutique(domainBoutique);
			boutiqueDomainRepository.save(boutiqueDomain);
		}
		
		
		UtilisateurDto dto = new UtilisateurDto();
		dto.setBoutique(boutiqueSave.getUuid());
		dto.setMotDePasse(boutiqueDto.getPassword());
		dto.setEmail(boutiqueDto.getEmail());
		dto.setPhone(boutiqueSave.getPhoneBoutique());
		dto.setUsername(boutiqueDto.getUtilisateur());
		dto.setAdresse(boutiqueDto.getAdresse());
		List<EnumRole> enumRoles = new ArrayList<EnumRole>();
		enumRoles.add(EnumRole.ADMIN);
		dto.setRole(enumRoles);
		
		utilisateurService.addUtilisateur(dto);
		
		return Mapper.toBoutiqueDto(boutiqueSave);
	}

	@Override
	public void deleteBoutique(String uuid) {
		// TODO Auto-generated method stub
		boutiqueRepository.deleteById(uuid);

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
		Boutique boutique = boutiqueRepository.findById(uuid).orElseThrow(null);
		return Mapper.toBoutiqueDto(boutique);
	}

	@Override
	public BoutiqueDto updateBoutique(BoutiqueDto boutiqueDto, String uuid) {
		// TODO Auto-generated method stub
		Boutique boutique = boutiqueRepository.findById(uuid).orElseThrow(null);
		boutique.setAdresse(boutiqueDto.getAdresse());
		boutique.setDescriptionBoutique(boutiqueDto.getDescriptionBoutique());
		boutique.setEmailBoutique(boutiqueDto.getEmailBoutique());
		boutique.setLibelleBoutique(boutiqueDto.getLibelleBoutique());
		boutique.setPhoneBoutique(boutiqueDto.getPhoneBoutique());
		boutique.setSiteBoutique(boutiqueDto.getSiteBoutique());
		boutique.setTypeBoutique(boutiqueDto.getTypeBoutique());
		Boutique boutiqueSave = boutiqueRepository.save(boutique);
		return Mapper.toBoutiqueDto(boutiqueSave);
	}

	@Override
	public PageDataDto<BoutiqueDto> listeBoutiques(int page, int size, String key) {
		// TODO Auto-generated method stub
		return null;
	}

}
