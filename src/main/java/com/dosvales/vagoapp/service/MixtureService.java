package com.dosvales.vagoapp.service;

import java.util.List;

import com.dosvales.vagoapp.model.Mixture;
import com.dosvales.vagoapp.model.Production;
import com.dosvales.vagoapp.service.generic.GenericService;

public interface MixtureService extends GenericService<Mixture, Long>{
	
	List<Mixture> findAllByBaseProduction(Production baseProduction);
	
}
