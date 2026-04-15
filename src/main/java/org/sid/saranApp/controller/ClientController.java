package org.sid.saranApp.controller;

import org.sid.saranApp.dto.ClientDto;
import org.sid.saranApp.serviceImpl.ClientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class ClientController {
	
	@Autowired
	private ClientServiceImpl clientServiceImpl;
	
	@PostMapping("/client")
	public ClientDto add(@RequestBody ClientDto clientDto) {
		return clientServiceImpl.add(clientDto);
	}
	
	@PutMapping("/client/{uuid}")
	public ClientDto update(@RequestBody ClientDto clientDto,@PathVariable String uuid) {
		return clientServiceImpl.update(clientDto, uuid);
	}
	
	@DeleteMapping("/client/delete/{uuid}")
	public ClientDto supprimer(@PathVariable String uuid) {
		return clientServiceImpl.supprimer(uuid);
	}
	
	@GetMapping("/client")
	public List<ClientDto> findAll(){
		return clientServiceImpl.findAll();
	}
	
	@GetMapping("/client/getById/{uuid}")
	public ClientDto getById(@PathVariable String uuid) {
		return clientServiceImpl.getById(uuid);
	}
	
	@DeleteMapping("/client/{uuid}")
	void deleteClient(@PathVariable String uuid) {
		clientServiceImpl.delete(uuid);
	}

}
