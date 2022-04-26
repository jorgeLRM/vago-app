package com.dosvales.vagoapp.service;

import java.util.List;

import com.dosvales.vagoapp.model.Producer;
import com.dosvales.vagoapp.model.Product;
import com.dosvales.vagoapp.service.generic.GenericService;

public interface ProductService extends GenericService<Product, Long> {

	Product findByName(String name);

	Product blockUnblockProduct(Long id);

	Product findWithInputs(Long id);

	List<Product> findAllActive();

	List<Product> findAllInactive();

	List<Product> findByProducer(Producer producer);

}