package com.dosvales.vagoapp.service;

import java.util.List;

import com.dosvales.vagoapp.model.Production;
import com.dosvales.vagoapp.service.generic.GenericService;

public interface ProductionService extends GenericService<Production, Long>{
	
	List<Production> findAllWithoutPreliminaryAnalysis();
	
	List<Production> findAllWithoutOfficialAnalysis();
	
	List<Production> findAllWithoutTransfer();
	
	
}
