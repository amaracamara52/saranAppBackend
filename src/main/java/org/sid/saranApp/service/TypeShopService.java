package org.sid.saranApp.service;

import java.util.List;

import org.sid.saranApp.dto.TypeShopDto;

public interface TypeShopService {
	
	TypeShopDto addTypeShop(TypeShopDto typeShopDto);
	TypeShopDto updateTypeShop(TypeShopDto typeShopDto,String uuid);
	List<TypeShopDto> listeTypeShop();

}
