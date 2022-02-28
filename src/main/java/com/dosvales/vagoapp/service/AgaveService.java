package com.dosvales.vagoapp.service;

import java.util.List;

import com.dosvales.vagoapp.model.Agave;
import com.dosvales.vagoapp.service.generic.GenericService;

public interface AgaveService extends GenericService<Agave, Long>{
	
	Agave blockUnblockAgave(Long id);

	Agave findByName(String name);
	
	List<Agave> findAllActive();
	
	List<Agave> findAllInactive();
	
	Agave findWithMagueyes(Long id);
}
