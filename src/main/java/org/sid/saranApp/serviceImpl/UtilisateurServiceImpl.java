package org.sid.saranApp.serviceImpl;

import org.sid.saranApp.dto.PageDataDto;
import org.sid.saranApp.dto.UtilisateurDto;
import org.sid.saranApp.enume.EnumRole;
import org.sid.saranApp.mapper.Mapper;
import org.sid.saranApp.model.Boutique;
import org.sid.saranApp.model.Pays;
import org.sid.saranApp.model.Utilisateur;
import org.sid.saranApp.repository.BoutiqueRepository;
import org.sid.saranApp.repository.PaysRepository;
import org.sid.saranApp.exception.BusinessException;
import org.sid.saranApp.repository.UtilisateurRepository;
import org.sid.saranApp.service.UtilisateurService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {

	@Autowired
	private UtilisateurRepository utilisateurRepository;
	@Autowired
	private BoutiqueRepository boutiqueRepository;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	private PaysRepository paysRepository;

	Logger logger = LoggerFactory.getLogger(UtilisateurServiceImpl.class);

	@Override
	public UtilisateurDto addUtilisateur(UtilisateurDto utilisateurDto) {
		Boutique boutique = boutiqueRepository.findById(utilisateurDto.getBoutique()).orElseThrow(() -> new BusinessException("Boutique non trouvée avec l'UUID: " + utilisateurDto.getBoutique()));
		// TODO Auto-generated method stub
		Utilisateur utilisateur = new Utilisateur();
		utilisateur.setBoutique(boutique);
		utilisateur.setUsername(utilisateurDto.getUsername());
		utilisateur.setRole(utilisateurDto.getRole());
		utilisateur.setPhone(utilisateurDto.getPhone());
		utilisateur.setEmail(utilisateurDto.getEmail());
		utilisateur.setPassword(passwordEncoder.encode(utilisateurDto.getMotDePasse()));
		utilisateur.setAdresse(utilisateurDto.getAdresse());
		Utilisateur utilisateurSave = utilisateurRepository.save(utilisateur);
		return Mapper.toUtilisateurDto(utilisateurSave);
	}

	@Override
	public void deleteUtilisateur(String uuid) {
		// TODO Auto-generated method stub
		Utilisateur utilisateur = utilisateurRepository.findById(uuid).orElseThrow(() -> new BusinessException("Utilisateur non trouvé avec l'UUID: " + uuid));
		utilisateurRepository.delete(utilisateur);

	}

	@Override
	@Transactional(readOnly = true)
	public List<UtilisateurDto> findAll() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Utilisateur current = getCurentUtilisateur();
		List<EnumRole> roles = current.getRole() != null ? current.getRole() : Collections.emptyList();

		List<Utilisateur> utilisateurs;
		if (hasAppRole(roles, authentication, EnumRole.SUPER_ADMIN)) {
			utilisateurs = utilisateurRepository.findAll();
		} else if (hasAppRole(roles, authentication, EnumRole.ADMIN)
				|| hasAppRole(roles, authentication, EnumRole.BOUTIQUE)) {
			// ADMIN + BOUTIQUE : même périmètre (souvent le « gérant boutique » a le rôle BOUTIQUE, pas ADMIN)
			if (current.getBoutique() == null) {
				throw new BusinessException("Aucune boutique associée : impossible de charger la liste des utilisateurs.");
			}
			List<String> scopeBoutiques = collectBoutiqueScopeWithDependants(current.getBoutique().getUuid());
			utilisateurs = utilisateurRepository.listeByBoutiques(scopeBoutiques);
		} else {
			utilisateurs = Collections.emptyList();
		}

		List<UtilisateurDto> utilisateurDtos = new ArrayList<>();
		utilisateurs.forEach(u -> utilisateurDtos.add(Mapper.toUtilisateurDto(u)));
		return utilisateurDtos;
	}

	private boolean hasAppRole(List<EnumRole> roles, Authentication authentication, EnumRole role) {
		if (roles.contains(role)) {
			return true;
		}
		if (authentication == null || authentication.getAuthorities() == null) {
			return false;
		}
		for (GrantedAuthority authority : authentication.getAuthorities()) {
			if (role.name().equals(authority.getAuthority())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Boutique racine + toutes les boutiques qui en dépendent (boutique_principale), sur toute la profondeur.
	 */
	private List<String> collectBoutiqueScopeWithDependants(String rootBoutiqueUuid) {
		Set<String> scope = new LinkedHashSet<>();
		scope.add(rootBoutiqueUuid);
		List<String> frontier = new ArrayList<>();
		frontier.add(rootBoutiqueUuid);
		while (!frontier.isEmpty()) {
			List<String> children = boutiqueRepository.findUuidsByBoutiquePrincipaleUuidIn(frontier);
			frontier.clear();
			for (String childUuid : children) {
				if (scope.add(childUuid)) {
					frontier.add(childUuid);
				}
			}
		}
		return new ArrayList<>(scope);
	}

	@Transactional(readOnly = true)
	public Utilisateur getCurentUtilisateur() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		// Vérifier si l'utilisateur est authentifié
		if (auth == null || auth.getName() == null || "anonymousUser".equals(auth.getName())) {
			throw new BusinessException("Utilisateur non authentifié");
		}
		
		String loginKey = auth.getName();
		return utilisateurRepository.findByEmailWithBoutique(loginKey)
				.or(() -> utilisateurRepository.findByEmail(loginKey))
				.orElseThrow(() -> new BusinessException("Utilisateur non trouvé"));
	}

	@Override
	public UtilisateurDto getUtilisateur(String uuid) {
		// TODO Auto-generated method stub
		Utilisateur utilisateur = utilisateurRepository.findById(uuid).orElseThrow(() -> new BusinessException("Utilisateur non trouvé avec l'UUID: " + uuid));
		return Mapper.toUtilisateurDto(utilisateur);
	}

	@Override
	public UtilisateurDto getUtilisateurByEmail(String email) {
		Optional<Utilisateur> optionalUtilisateur = utilisateurRepository.findByEmail(email);
		Utilisateur utilisateur = optionalUtilisateur.orElseThrow(() -> new BusinessException("Utilisateur non trouvé avec l'email: " + email));
		return Mapper.toUtilisateurDto(utilisateur);
	}

	@Override
	public UtilisateurDto updateUtilisateur(UtilisateurDto utilisateurDto, String uuid) {
		// TODO Auto-generated method stub
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		// Vérifier si l'utilisateur est authentifié
		if (auth == null || auth.getName() == null || "anonymousUser".equals(auth.getName())) {
			throw new BusinessException("Utilisateur non authentifié");
		}
		
		Utilisateur utilisateur = utilisateurRepository.findById(uuid).orElseThrow(() -> new BusinessException("Utilisateur non trouvé avec l'UUID: " + uuid));
		utilisateur.setUsername(utilisateurDto.getUsername());
		utilisateur.setPhone(utilisateurDto.getPhone());
		utilisateur.setRole(utilisateurDto.getRole());
		utilisateur.setEmail(utilisateurDto.getEmail());
		if (utilisateurDto.getMotDePasse() != null && !utilisateurDto.getMotDePasse().trim().isEmpty()) {
			utilisateur.setPassword(passwordEncoder.encode(utilisateurDto.getMotDePasse()));
		}
		utilisateur.setAdresse(utilisateurDto.getAdresse());
		utilisateur.setBoutique(utilisateur.getBoutique());
		Utilisateur utlilisateurSave = utilisateurRepository.save(utilisateur);
		return Mapper.toUtilisateurDto(utilisateur);
	}

	@Override
	public UtilisateurDto userLogin() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		// Vérifier si l'utilisateur est authentifié
		if (auth == null || auth.getName() == null || "anonymousUser".equals(auth.getName())) {
			throw new BusinessException("Utilisateur non authentifié");
		}
		
		Utilisateur utilisateur = utilisateurRepository.findByEmail(auth.getName()).orElseThrow(() -> new BusinessException("Utilisateur non trouvé"));


		return Mapper.toUtilisateurLoginDto(utilisateur);
	}

	@Override
	public PageDataDto<UtilisateurDto> listeUtilisateurs(int page, int size, String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(String uuid) {
		// TODO Auto-generated method stub
		utilisateurRepository.deleteById(uuid);
	}

	@Override
	public UtilisateurDto bloquerUtilisateur(String uuid) {
		// TODO Auto-generated method stub
		Utilisateur utilisateur = utilisateurRepository.findById(uuid).orElseThrow(() -> new BusinessException("Utilisateur non trouvé avec l'UUID: " + uuid));
		utilisateur.setEnabled(false);
		utilisateurRepository.save(utilisateur);
		return Mapper.toUtilisateurDto(utilisateur);
	}

	@Override
	public UtilisateurDto DebloquerUtilisateur(String uuid) {
		// TODO Auto-generated method stub
		Utilisateur utilisateur = utilisateurRepository.findById(uuid).orElseThrow(() -> new BusinessException("Utilisateur non trouvé avec l'UUID: " + uuid));
		utilisateur.setEnabled(true);
		utilisateurRepository.save(utilisateur);
		return Mapper.toUtilisateurDto(utilisateur);
	}


	public Utilisateur addUser(UtilisateurDto utilisateurDto) {

		// TODO Auto-generated method stub
		Utilisateur utilisateur = new Utilisateur();
		if(utilisateurDto.getBoutique() != null){
			Boutique boutique = boutiqueRepository.findById(utilisateurDto.getBoutique()).orElseThrow(() -> new BusinessException("Boutique non trouvée avec l'UUID: " + utilisateurDto.getBoutique()));
			utilisateur.setBoutique(boutique);
		}
		if(utilisateurDto.getBoutique() == null){
			utilisateur.setBoutique(null);
		}
		utilisateur.setUsername(utilisateurDto.getUsername());
		utilisateur.setRole(utilisateurDto.getRole());
		utilisateur.setPhone(utilisateurDto.getPhone());
		utilisateur.setEmail(utilisateurDto.getEmail());
		utilisateur.setPassword(passwordEncoder.encode(utilisateurDto.getMotDePasse()));
		utilisateur.setAdresse(utilisateurDto.getAdresse());
		Utilisateur utilisateurSave = utilisateurRepository.save(utilisateur);
		return utilisateur;
	}
}
