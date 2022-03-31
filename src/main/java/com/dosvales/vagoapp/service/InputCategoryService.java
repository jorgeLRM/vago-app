package com.dosvales.vagoapp.service;

import java.util.List;

import com.dosvales.vagoapp.model.InputCategory;
import com.dosvales.vagoapp.service.generic.GenericService;

public interface InputCategoryService extends GenericService<InputCategory, Long>{
	
	InputCategory blockUnblockAgave(Long id);
	
	InputCategory findByName(String name);
	
	List<InputCategory> findAllActive();
	
	List<InputCategory> findAllInactive();
	
}
