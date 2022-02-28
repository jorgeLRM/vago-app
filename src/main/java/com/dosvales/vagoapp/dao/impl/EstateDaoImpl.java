package com.dosvales.vagoapp.dao.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityGraph;
import javax.persistence.NoResultException;

import com.dosvales.vagoapp.dao.EstateDao;
import com.dosvales.vagoapp.dao.generic.GenericDaoImpl;
import com.dosvales.vagoapp.model.EntityStatus;
import com.dosvales.vagoapp.model.Estate;

@Stateless
public class EstateDaoImpl extends GenericDaoImpl<Estate, Long> implements EstateDao, Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public Estate findWithPlantations(Long id) {
		EntityGraph<?> entityGraph = em.getEntityGraph("graph.Estate.plantations");
		Map<String, Object> properties = new HashMap<>();
		properties.put("javax.persistence.fetchgraph", entityGraph);
		return em.find(Estate.class, id, properties);
	}

	@Override
	public List<Estate> findAllByStatus(EntityStatus status) {
		String jpql = "FROM Estate e WHERE e.status = :status";
		return em.createQuery(jpql, Estate.class)
				.setParameter("status", status)
				.getResultList();
	}

	@Override
	public Estate findByName(String name) {
		Estate estate = null;
		try {
			estate = em.createQuery("FROM Estate e WHERE UPPER(e.name) = :name", Estate.class)
					.setParameter("name", name.toUpperCase())
					.getSingleResult();
		} catch(NoResultException ex) {}
		return estate;
	}

}
