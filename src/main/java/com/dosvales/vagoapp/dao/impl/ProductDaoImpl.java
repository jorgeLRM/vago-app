package com.dosvales.vagoapp.dao.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityGraph;
import javax.persistence.NoResultException;

import com.dosvales.vagoapp.dao.ProductDao;
import com.dosvales.vagoapp.dao.generic.GenericDaoImpl;
import com.dosvales.vagoapp.model.EntityStatus;
import com.dosvales.vagoapp.model.Producer;
import com.dosvales.vagoapp.model.Product;

@Stateless
public class ProductDaoImpl extends GenericDaoImpl<Product, Long> implements ProductDao, Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public Product findByName(String name) {
		Product product = null;
		try {
			product = em.createQuery("FROM Product p WHERE UPPER(p.name) = :name", Product.class)
					.setParameter("name", name.toUpperCase())
					.getSingleResult();
		} catch (NoResultException ex) {}
		return product;
	}

	@Override
	public Product findWithInputs(Long id) {
		EntityGraph<?> entityGraph = em.getEntityGraph("graph.Product.productInputs");
		Map<String, Object> properties = new HashMap<>();
		properties.put("javax.persistence.fetchgraph", entityGraph);
		return em.find(Product.class, id, properties);
	}

	@Override
	public List<Product> findAllByStatus(EntityStatus status) {
		String jpql = "FROM Product p WHERE p.status = :status";
		return em.createQuery(jpql, Product.class)
				.setParameter("status", status)
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Product> findByProducer(Producer producer) {
		return em.createNativeQuery("FROM Product p INNER JOIN ProductCategory pc "
				+ "ON pc.id = p.idCategory "
				+ "WHERE pc.producer = :producer", Product.class)
				.setParameter("producer", producer)
				.getResultList();
	}
}