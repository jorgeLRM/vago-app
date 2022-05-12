package com.dosvales.vagoapp.dao.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityGraph;

import com.dosvales.vagoapp.dao.ProviderDao;
import com.dosvales.vagoapp.dao.generic.GenericDaoImpl;
import com.dosvales.vagoapp.model.EntityStatus;
import com.dosvales.vagoapp.model.Provider;

@Stateless
public class ProviderDaoImpl extends GenericDaoImpl<Provider, Long> implements ProviderDao, Serializable {
	
	private static final long serialVersionUID = 1L;

	@Override
	public Provider findByName(String name) {
		Provider provider = null;
		try {
			provider = em.createQuery("FROM Provider p WHERE UPPER(p.name) = :name", 
					Provider.class)
					.setParameter("name", name.toUpperCase())
					.getSingleResult();
		} catch (Exception ex) {}
		return provider;
	}

	@Override
	public List<Provider> findAllByStatus(EntityStatus status) {
		String jpql = "FROM Provider p WHERE p.status = :status";
		return em.createQuery(jpql, Provider.class)
				.setParameter("status", status)
				.getResultList();
	}

	@Override
	public Provider findWithProviderInputs(Long id) {
		EntityGraph<?> entityGraph = em.getEntityGraph("graph.Provider.providerInputs");
		Map<String, Object> properties = new HashMap<>();
		properties.put("javax.persistence.fetchgraph", entityGraph);
		return em.find(Provider.class, id, properties);
	}
}