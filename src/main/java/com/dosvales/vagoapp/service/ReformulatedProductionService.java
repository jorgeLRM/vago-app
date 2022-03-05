package com.dosvales.vagoapp.service;

import com.dosvales.vagoapp.model.ReformulatedProduction;
import com.dosvales.vagoapp.service.generic.GenericService;

public interface ReformulatedProductionService extends GenericService<ReformulatedProduction, Long>{
	
	ReformulatedProduction findByLot(String lot);
	
	ReformulatedProduction findWithAssociations(Long id);
}
