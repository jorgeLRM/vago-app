package com.dosvales.vagoapp.service;

import java.util.List;

import com.dosvales.vagoapp.model.Maguey;
import com.dosvales.vagoapp.service.generic.GenericService;

public interface MagueyService extends GenericService<Maguey, Long>{
	
	Maguey blockUnblockMaguey(Long id);
	
	Maguey findByNameOrAbbreviation(String name, String abbreviation);
	
	Maguey findWithPhotos(Long id);
	
	Maguey findWithPlantations(Long id);
	
	List<Maguey> findAllActive();
	
	List<Maguey> findAllInactive();
	
}
