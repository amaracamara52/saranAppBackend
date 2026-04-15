package org.sid.saranApp.controller;

import java.util.List;

import org.sid.saranApp.dto.TypeShopDto;
import org.sid.saranApp.service.TypeShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class TypeShopController {
	
	@Autowired
	private TypeShopService typeShopService;
	
	@PostMapping("/typeShop")
	public TypeShopDto add(@RequestBody TypeShopDto typeShopDto) {
		return typeShopService.addTypeShop(typeShopDto);
	}
	
	@PutMapping("typeShop/{uuid}")
	public TypeShopDto update(@RequestBody TypeShopDto typeShopDto,@PathVariable String uuid) {
		return typeShopService.updateTypeShop(typeShopDto, uuid);
	}
	
	@GetMapping("/typeShop")
	public List<TypeShopDto> liste(){
		return typeShopService.listeTypeShop();
	}

}
