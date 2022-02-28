package com.dosvales.vagoapp.service;

import java.util.List;

import com.dosvales.vagoapp.model.Palenque;
import com.dosvales.vagoapp.service.generic.GenericService;

public interface PalenqueService extends GenericService<Palenque, Long>{
	
	Palenque findByName(String name);
	
	Palenque findWithLotsAndTubs(Long id);
	
	List<Palenque> findAllActive();
	
	List<Palenque> findAllInactive();
	
	Palenque blockUnblockPalenque(Long id);
}
