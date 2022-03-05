package com.dosvales.vagoapp.service;

import com.dosvales.vagoapp.model.Provider;
import com.dosvales.vagoapp.service.generic.GenericService;

public interface ProviderService extends GenericService<Provider, Long>{
	
	Provider findByName(String name);
	
}
