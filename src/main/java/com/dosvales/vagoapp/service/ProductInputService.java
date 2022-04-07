package com.dosvales.vagoapp.service;

import java.util.List;

import com.dosvales.vagoapp.model.Product;
import com.dosvales.vagoapp.model.ProductInput;
import com.dosvales.vagoapp.service.generic.GenericService;

public interface ProductInputService extends GenericService<ProductInput, Long> {

	List<ProductInput> findAllByProduct(Product product);

}