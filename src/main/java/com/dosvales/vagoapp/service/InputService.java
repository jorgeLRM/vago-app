package com.dosvales.vagoapp.service;

import java.util.List;

import com.dosvales.vagoapp.model.Input;
import com.dosvales.vagoapp.model.InputCategory;
import com.dosvales.vagoapp.service.generic.GenericService;

public interface InputService extends GenericService<Input, Long>{
	
	Input findByNameOrCode(String name, String code);
	
	Input blockUnblockInput(Long id);
	
	List<Input> findAllActive();
	
	List<Input> findAllInactive();

	List<Input> findByCategory(InputCategory category);

	List<Input> findWhitoutBottles();
}