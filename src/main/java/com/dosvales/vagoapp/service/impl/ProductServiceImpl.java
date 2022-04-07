package com.dosvales.vagoapp.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Named;
import javax.transaction.Transactional;

import com.dosvales.vagoapp.dao.ProductDao;
import com.dosvales.vagoapp.dao.generic.GenericDao;
import com.dosvales.vagoapp.model.EntityStatus;
import com.dosvales.vagoapp.model.Producer;
import com.dosvales.vagoapp.model.Product;
import com.dosvales.vagoapp.service.ProductService;
import com.dosvales.vagoapp.service.generic.GenericServiceImpl;

@Named
@Transactional(rollbackOn = Exception.class)
public class ProductServiceImpl extends GenericServiceImpl<Product, Long> implements ProductService, Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private ProductDao dao;

	@Override
	public Product findByName(String name) {
		return dao.findByName(name);
	}
	
	@Override
	public Product blockUnblockProduct(Long id) {
		Product product = dao.findById(id);
		EntityStatus status = product.getStatus() == EntityStatus.ACTIVE ? EntityStatus.INACTIVE : EntityStatus.ACTIVE;
		product.setStatus(status);
		return dao.update(product);
	}

	@Override
	public List<Product> findAllActive() {
		return dao.findAllByStatus(EntityStatus.ACTIVE);
	}

	@Override
	public List<Product> findAllInactive() {
		return dao.findAllByStatus(EntityStatus.INACTIVE);
	}

	@Override
	public List<Product> findByProducer(Producer producer) {
		return dao.findByProducer(producer);
	}

	@Override
	public GenericDao<Product, Long> getDao() {
		return dao;
	}
}