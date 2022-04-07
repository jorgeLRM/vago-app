package com.dosvales.vagoapp.dao.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;

import com.dosvales.vagoapp.dao.ProductInputDao;
import com.dosvales.vagoapp.dao.generic.GenericDaoImpl;
import com.dosvales.vagoapp.model.Product;
import com.dosvales.vagoapp.model.ProductInput;

@Stateless
public class ProductInputDaoImpl extends GenericDaoImpl<ProductInput, Long> implements ProductInputDao, Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public List<ProductInput> findAllByProduct(Product product) {
		return em.createQuery("FROM ProductInput p WHERE p.product = :product", ProductInput.class)
				.setParameter("product", product)
				.getResultList();
	}
}