package com.dosvales.vagoapp.service;

import java.util.List;

import com.dosvales.vagoapp.model.Estate;
import com.dosvales.vagoapp.service.generic.GenericService;

public interface EstateService extends GenericService<Estate, Long>{
	
	Estate findWithPlantations(Long id);
	
	List<Estate> findAllActive();
	
	List<Estate> findAllInactive();
	
	Estate findByName(String name);
	
	Estate blockUnblockEstate(Long id);
	
}
