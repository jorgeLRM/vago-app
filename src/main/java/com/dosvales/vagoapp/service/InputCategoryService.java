package com.dosvales.vagoapp.service;

import java.util.List;

import com.dosvales.vagoapp.model.InputCategory;
import com.dosvales.vagoapp.service.generic.GenericService;

public interface InputCategoryService extends GenericService<InputCategory, Long>{

	InputCategory findWithInputs(Long id);

	InputCategory blockUnblockAgave(Long id);
	
	InputCategory findByNameAndNomenclature(String name, String nomenclature);
	
	List<InputCategory> findAllActive();
	
	List<InputCategory> findAllInactive();
	
}