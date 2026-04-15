package org.sid.saranApp.controller;

import org.sid.saranApp.enume.*;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/enum")
public class EnumController {
	
	@GetMapping("/typeBoutique")
	public List<EnumTypeBoutique> getEnumTypeBoutique(){
		return new ArrayList<EnumTypeBoutique>(EnumSet.allOf(EnumTypeBoutique.class));
	}


	@GetMapping("/genre")
	public List<EnumGenre> getGenre(){
		return new ArrayList<EnumGenre>(EnumSet.allOf(EnumGenre.class));
	}


	@GetMapping("/role")
	public List<EnumRole> getRole(){
		return new ArrayList<EnumRole>(EnumSet.allOf(EnumRole.class));
	}

	@GetMapping("/statutOnlineOrder")
	public List<EnumStatutOnlineOrder> getStatutRoleOnlineOrder(){
		return new ArrayList<EnumStatutOnlineOrder>(EnumSet.allOf(EnumStatutOnlineOrder.class));
	}


	@GetMapping("/typeInstanceBoutique")
	public List<EnumTypeInstanceBoutique> getTypeInstanceBoutique(){
		return new ArrayList<EnumTypeInstanceBoutique>(EnumSet.allOf(EnumTypeInstanceBoutique.class));
	}


	@GetMapping("/typeShop")
	public List<EnumTypeShop> getTypeShop(){
		return new ArrayList<EnumTypeShop>(EnumSet.allOf(EnumTypeShop.class));
	}


	@GetMapping("/statutCommandeVente")
	public List<StatusCommandeVenteEnum> getStatutCommandeVente(){
		return new ArrayList<StatusCommandeVenteEnum>(EnumSet.allOf(StatusCommandeVenteEnum.class));
	}


	@GetMapping("/typeUnite")
	public List<TypeUniteEnum> getTypeUnite(){
		return new ArrayList<TypeUniteEnum>(EnumSet.allOf(TypeUniteEnum.class));
	}


}
