package com.dosvales.vagoapp.dao.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityGraph;
import javax.persistence.NoResultException;

import com.dosvales.vagoapp.dao.ProductCategoryDao;
import com.dosvales.vagoapp.dao.generic.GenericDaoImpl;
import com.dosvales.vagoapp.model.EntityStatus;
import com.dosvales.vagoapp.model.ProductCategory;

@Stateless
public class ProductCategoryDaoImpl extends GenericDaoImpl<ProductCategory, Long> implements ProductCategoryDao, Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public ProductCategory findByName(String name) {
		ProductCategory productCategory = null;
		try {
			productCategory = em.createQuery("FROM ProductCategory p WHERE UPPER(p.name) = :name", ProductCategory.class)
					.setParameter("name", name.toUpperCase())
					.getSingleResult();
		} catch (NoResultException ex) {}
		return productCategory;
	}

	@Override
	public List<ProductCategory> findAllByStatus(EntityStatus status) {
		return em.createQuery("FROM ProductCategory p WHERE p.status = :status", ProductCategory.class)
				.setParameter("status", status)
				.getResultList();
	}

	@Override
	public ProductCategory findWithProducts(Long id) {
		EntityGraph<?> entityGraph = em.getEntityGraph("graph.ProductCategory.products");
		Map<String, Object> properties = new HashMap<>();
		properties.put("javax.persistence.fetchgraph", entityGraph);
		return em.find(ProductCategory.class, id, properties);
	}
}