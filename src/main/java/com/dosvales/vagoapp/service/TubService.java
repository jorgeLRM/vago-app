package com.dosvales.vagoapp.service;

import java.time.LocalDate;
import java.util.List;

import com.dosvales.vagoapp.model.Palenque;
import com.dosvales.vagoapp.model.Tub;
import com.dosvales.vagoapp.service.generic.GenericService;

public interface TubService extends GenericService<Tub, Long>{
	
	Tub findByNumberAndPalenque(Integer number, Palenque palenque);
	
	Tub findWithFormulations(Long id);
	
	List<Tub> findByPalenque(Palenque palenque);
	
	List<Tub> findTubsAvailableByDate(LocalDate date);
	
	List<Tub> findAllActive();
	
	List<Tub> findAllInactive();
	
	Tub blockUnblockTub(Long id);
	
}
