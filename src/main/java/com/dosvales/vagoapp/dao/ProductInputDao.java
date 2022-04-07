package com.dosvales.vagoapp.dao;

import java.util.List;

import javax.ejb.Local;

import com.dosvales.vagoapp.dao.generic.GenericDao;
import com.dosvales.vagoapp.model.Product;
import com.dosvales.vagoapp.model.ProductInput;

@Local
public interface ProductInputDao extends GenericDao<ProductInput, Long> {

	List<ProductInput> findAllByProduct(Product product);

}