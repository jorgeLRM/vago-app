package com.dosvales.vagoapp.service;

import java.util.List;

import com.dosvales.vagoapp.model.Provider;
import com.dosvales.vagoapp.service.generic.GenericService;

public interface ProviderService extends GenericService<Provider, Long>{

	Provider blockUnblockProvider(Long id);

	Provider findByName(String name);

	List<Provider> findAllActive();

	List<Provider> findAllInactive();

	Provider findWithProviderInputs(Long id);

}