package org.sid.saranApp.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.sid.saranApp.dto.ClientDto;
import org.sid.saranApp.dto.PageDataDto;
import org.sid.saranApp.exception.Exception;
import org.sid.saranApp.mapper.Mapper;
import org.sid.saranApp.model.Client;
import org.sid.saranApp.model.Utilisateur;
import org.sid.saranApp.repository.BoutiqueRepository;
import org.sid.saranApp.repository.ClientRepository;
import org.sid.saranApp.repository.UtilisateurRepository;
import org.sid.saranApp.service.ClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl implements ClientService {

	@Autowired
	private ClientRepository clientRepository;
	@Autowired
	private UtilisateurRepository utilisateurRepository;
	@Autowired
	private BoutiqueRepository boutiqueRepository;

	Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);

	@Override
	public ClientDto add(ClientDto clientDto) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		logger.info("hello {}", auth.getName());
		ClientDto response = new ClientDto();
		if (clientDto == null) {
			response.setCode(Exception.error);
			response.setMessage("Veuiillez renseigner les champs");
			return response;
		}

		if (clientRepository.existsByEmail(clientDto.getEmail())) {
			response.setCode(Exception.error);
			response.setMessage("Cet adresse email existe déjà");
			return response;
		}
		Utilisateur utilisateurOptional = utilisateurRepository.findByEmail(auth.getName()).orElseThrow(null);
//		if(!utilisateurOptional.isPresent()) {
//			response.setCode(Exception.error);
//			response.setMessage("Cet Utilisateur n'exist pas");
//			return response;
//		}
//		Optional<Boutique> boutiqueOptional = boutiqueRepository.findById(clientDto.getUuidBoutique());
//		if(!utilisateurOptional.isPresent()) {
//			response.setCode(Exception.error);
//			response.setMessage("Cette Boutique n'exist pas");
//			return response;
//		}

		Client client = Mapper.toClientDto(clientDto, utilisateurOptional.getBoutique(), utilisateurOptional);
		Client clientSave = clientRepository.save(client);
		response = Mapper.toClient(clientSave);
		response.setCode(Exception.succes);
		response.setMessage("Enregistrement effectuer avec success");
		return response;
	}

	@Override
	public List<ClientDto> findAll() {
		List<Client> listeClient = clientRepository.findAll();
		List<ClientDto> listeClientDto = new ArrayList<>();
		listeClient.forEach(val -> {
			listeClientDto.add(Mapper.toClient(val));
		});
		return listeClientDto;
	}

	@Override
	public ClientDto getById(String uuid) {
		ClientDto response = new ClientDto();
		if (uuid == null) {
			response.setCode(Exception.error);
			response.setMessage("L'id ne doit pas etre null");
			return response;
		}
		Optional<Client> clientOptionel = clientRepository.findById(uuid);
		if (!clientOptionel.isPresent()) {
			response.setCode(Exception.error);
			response.setMessage("Ce client n'exist pas");
			return response;
		}
		Optional<Client> clientOptional = clientRepository.findById(uuid);
		return Mapper.toClient(clientOptional.get());
	}

	@Override
	public ClientDto supprimer(String uuid) {
		ClientDto response = new ClientDto();
		if (uuid == null) {
			response.setCode(Exception.error);
			response.setMessage("L'Id ne peut pas etre nul");
			return response;
		}
		Optional<Client> clientOptionel = clientRepository.findById(uuid);
		if (!clientOptionel.isPresent()) {
			response.setCode(Exception.error);
			response.setMessage("Ce client n'exist pas");
			return response;
		}

		clientRepository.deleteById(uuid);
		response.setCode(Exception.succes);
		response.setMessage("Suppression effectuée avec success");
		return response;

	}

	@Override
	public ClientDto update(ClientDto clientDto, String uuid) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		ClientDto response = new ClientDto();
		if (clientDto == null || uuid == null) {
			response.setCode(Exception.error);
			response.setMessage("Veuillez reinseigner les champs");
			return response;
		}

		Optional<Client> clientOptional = clientRepository.findById(uuid);
		if (!clientOptional.isPresent()) {
			response.setCode(Exception.error);
			response.setMessage("Ce client n'exist pas");
			return response;
		}
//		Optional<Utilisateur> utilisateurOptional = utilisateurRepository.findById(clientDto.getUuidUtilisateur());
//		if (!utilisateurOptional.isPresent()) {
//			response.setCode(Exception.error);
//			response.setMessage("Cet Utilisateur n'exist pas");
//			return response;
//		}
//		Optional<Boutique> boutiqueOptional = boutiqueRepository.findById(clientDto.getUuidBoutique());
//		if (!utilisateurOptional.isPresent()) {
//			response.setCode(Exception.error);
//			response.setMessage("Cette Boutique n'exist pas");
//			return response;
//		}
		Utilisateur utilisateurOptional = utilisateurRepository.findByEmail(auth.getName()).orElseThrow(null);
		clientDto.setUuid(uuid);
		Client client = Mapper.toClientDto(clientDto, utilisateurOptional.getBoutique(), utilisateurOptional);
		Client clientSave = clientRepository.save(client);
		response = Mapper.toClient(clientSave);
		response.setCode(Exception.succes);
		response.setMessage("La modification à été effectuée avec success");
		return response;
	}

	@Override
	public PageDataDto<ClientDto> listeClients(int page, int size, String key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(String uuid) {
		// TODO Auto-generated method stub
		clientRepository.deleteById(uuid);
	}

}
