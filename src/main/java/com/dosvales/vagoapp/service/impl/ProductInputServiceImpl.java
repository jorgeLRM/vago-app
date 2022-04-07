package com.dosvales.vagoapp.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Named;
import javax.transaction.Transactional;

import com.dosvales.vagoapp.dao.ProductInputDao;
import com.dosvales.vagoapp.dao.generic.GenericDao;
import com.dosvales.vagoapp.model.Product;
import com.dosvales.vagoapp.model.ProductInput;
import com.dosvales.vagoapp.service.ProductInputService;
import com.dosvales.vagoapp.service.generic.GenericServiceImpl;

@Named
@Transactional(rollbackOn = Exception.class)
public class ProductInputServiceImpl extends GenericServiceImpl<ProductInput, Long> implements ProductInputService, Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private ProductInputDao dao;

	@Override
	public List<ProductInput> findAllByProduct(Product product) {
		return dao.findAllByProduct(product);
	}

	@Override
	public GenericDao<ProductInput, Long> getDao() {
		return dao;
	}
}