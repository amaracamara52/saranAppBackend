package org.sid.saranApp.service;

import org.sid.saranApp.dto.ClientDto;
import org.sid.saranApp.dto.PageDataDto;

import java.util.List;

public interface ClientService {
	
	public ClientDto add(ClientDto clientDto);
	public ClientDto update(ClientDto clientDto, String uuid);
	public ClientDto supprimer(String uuid);
	public List<ClientDto> findAll();
	public ClientDto getById(String uuid);
	void delete(String uuid);
	PageDataDto<ClientDto> listeClients(int page,int size,String key);

}
