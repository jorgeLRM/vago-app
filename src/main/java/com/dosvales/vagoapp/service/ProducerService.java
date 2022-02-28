package com.dosvales.vagoapp.service;

import java.util.List;

import com.dosvales.vagoapp.model.Producer;
import com.dosvales.vagoapp.service.generic.GenericService;

public interface ProducerService extends GenericService<Producer, Long>{
	
	Producer findByNameOrAbbreviation(String name, String abbreviation);
	
	Producer findWithPalenquesAndEstates(Long id);
	
	List<Producer> findAllMezcalProducers();
	
	List<Producer> findAllAgaveProducers();
	
	List<Producer> findAllActive();
	
	List<Producer> findAllInactive();
	
	Producer blockUnblockProducer(Long id);
}
