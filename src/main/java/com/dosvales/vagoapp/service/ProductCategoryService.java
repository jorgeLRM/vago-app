package com.dosvales.vagoapp.service;

import java.util.List;

import com.dosvales.vagoapp.model.ProductCategory;
import com.dosvales.vagoapp.service.generic.GenericService;

public interface ProductCategoryService extends GenericService<ProductCategory, Long> {
	
	ProductCategory findByName(String name);

	ProductCategory blockUnblockCategory(Long id);

	List<ProductCategory> findAllActive();

	List<ProductCategory> findAllInactive();

}