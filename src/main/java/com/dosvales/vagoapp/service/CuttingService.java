package com.dosvales.vagoapp.service;

import java.util.List;

import com.dosvales.vagoapp.model.Cutting;
import com.dosvales.vagoapp.service.generic.GenericService;

public interface CuttingService extends GenericService<Cutting, Long>{
	
	List<Cutting> findAllAvailable();
	
	Cutting findWithCuttingDetail(Long id);
	
	Cutting findWithProduction(Long id);
	
	Cutting findByGuideNumber(String guideNumber);
	
	void cancel(Cutting cutting);
	
	List<Cutting> findAllActive();
	
	List<Cutting> findAllInactive();
	
}
