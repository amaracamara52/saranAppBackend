package org.sid.saranApp.service;

import java.util.List;

import org.sid.saranApp.dto.CategorieDto;
import org.sid.saranApp.dto.ClientDto;
import org.sid.saranApp.dto.PageDataDto;

public interface ClientService {
	
	public ClientDto add(ClientDto clientDto);
	public ClientDto update(ClientDto clientDto, String uuid);
	public ClientDto supprimer(String uuid);
	public List<ClientDto> findAll();
	public ClientDto getById(String uuid);
	void delete(String uuid);
	PageDataDto<ClientDto> listeClients(int page,int size,String key);

}
