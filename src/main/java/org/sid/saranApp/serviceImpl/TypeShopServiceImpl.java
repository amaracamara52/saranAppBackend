package org.sid.saranApp.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.sid.saranApp.dto.TypeShopDto;
import org.sid.saranApp.mapper.Mapper;
import org.sid.saranApp.model.TypeShop;
import org.sid.saranApp.repository.TypeShopRepository;
import org.sid.saranApp.service.TypeShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TypeShopServiceImpl implements TypeShopService {
	
	@Autowired
	private TypeShopRepository typeShopRepository;

	@Override
	public TypeShopDto addTypeShop(TypeShopDto typeShopDto) {
		// TODO Auto-generated method stub
		TypeShop typeShop = new TypeShop();
		typeShop.setCode(typeShopDto.getCode());
		typeShop.setLibelle(typeShopDto.getLibelle());
		typeShop = typeShopRepository.save(typeShop);
		return Mapper.toTypeShopDto(typeShop);
	}

	@Override
	public TypeShopDto updateTypeShop(TypeShopDto typeShopDto, String uuid) {
		// TODO Auto-generated method stub
		TypeShop typeShop = typeShopRepository.findById(uuid).orElseThrow(()->new RuntimeException());
		typeShop.setCode(typeShopDto.getCode());
		typeShop.setLibelle(typeShopDto.getLibelle());
		typeShop = typeShopRepository.save(typeShop);
		return Mapper.toTypeShopDto(typeShop);
	}

	@Override
	public List<TypeShopDto> listeTypeShop() {
		// TODO Auto-generated method stub
		List<TypeShop>  typeShops = typeShopRepository.findAll();
		List<TypeShopDto> typeShopDtos = new ArrayList<TypeShopDto>();
		typeShops.forEach(t -> typeShopDtos.add(Mapper.toTypeShopDto(t)));
		return typeShopDtos;
	}

}
