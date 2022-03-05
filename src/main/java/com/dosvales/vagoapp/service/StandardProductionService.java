package com.dosvales.vagoapp.service;

import java.util.List;

import com.dosvales.vagoapp.model.Producer;
import com.dosvales.vagoapp.model.StandardProduction;
import com.dosvales.vagoapp.service.generic.GenericService;

public interface StandardProductionService extends GenericService<StandardProduction, Long>{
	
	List<StandardProduction> findAllWithoutTail();
	
	List<StandardProduction> findAllAvailableForFormulation();
	
	List<StandardProduction> findAllFails();
	
	List<StandardProduction> findAllAvailable(Producer producer);
	
	StandardProduction findWithAssociations(Long id);
	
	StandardProduction findByLot(String lot);
	
	List<StandardProduction> findAllCanceled();
	
	List<StandardProduction> findAllInProcess();
	
	List<StandardProduction> findAllTerminated();
	
}
