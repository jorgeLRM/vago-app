package com.dosvales.vagoapp.service;

import java.util.List;

import com.dosvales.vagoapp.filter.ProductionFilter;
import com.dosvales.vagoapp.model.Producer;
import com.dosvales.vagoapp.model.Production;
import com.dosvales.vagoapp.service.generic.GenericService;

public interface ProductionService extends GenericService<Production, Long>{
	
	Production findByLot(String lot);
	List<Production> findAllByFilter(ProductionFilter filter);
	List<Production> findAllAvailable(Producer producer);
	
}
