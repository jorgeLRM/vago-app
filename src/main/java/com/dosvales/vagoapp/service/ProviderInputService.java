package com.dosvales.vagoapp.service;

import java.util.List;

import com.dosvales.vagoapp.model.Input;
import com.dosvales.vagoapp.model.ProviderInput;
import com.dosvales.vagoapp.service.generic.GenericService;

public interface ProviderInputService extends GenericService<ProviderInput, Long>{
	
	List<ProviderInput> findAllByInput(Input input);
	
}
