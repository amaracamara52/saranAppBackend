package org.sid.saranApp.controller;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import org.sid.saranApp.enume.EnumTypeBoutique;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class EnumController {
	
	@GetMapping("/enumTypeBoutique")
	public List<EnumTypeBoutique> getEnumTypeBoutique(){
		return new ArrayList<EnumTypeBoutique>(EnumSet.allOf(EnumTypeBoutique.class));
	}

}
