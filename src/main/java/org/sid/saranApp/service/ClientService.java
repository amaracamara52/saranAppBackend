package org.sid.saranApp.service;

import java.util.List;

import org.sid.saranApp.dto.ClientDto;

public interface ClientService {
	
	public ClientDto add(ClientDto clientDto);
	public ClientDto update(ClientDto clientDto, String uuid);
	public ClientDto supprimer(String uuid);
	public List<ClientDto> findAll();
	public ClientDto getById(String uuid);

}
