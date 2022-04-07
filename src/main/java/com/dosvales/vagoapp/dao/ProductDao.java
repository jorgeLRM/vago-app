package com.dosvales.vagoapp.dao;

import java.util.List;

import javax.ejb.Local;

import com.dosvales.vagoapp.dao.generic.GenericDao;
import com.dosvales.vagoapp.model.EntityStatus;
import com.dosvales.vagoapp.model.Producer;
import com.dosvales.vagoapp.model.Product;

@Local
public interface ProductDao extends GenericDao<Product, Long> {

	Product findByName(String name);

	List<Product> findAllByStatus(EntityStatus status);

	List<Product> findByProducer(Producer producer);

}