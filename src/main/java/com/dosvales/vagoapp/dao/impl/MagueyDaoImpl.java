package com.dosvales.vagoapp.dao.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityGraph;
import javax.persistence.NoResultException;

import com.dosvales.vagoapp.dao.MagueyDao;
import com.dosvales.vagoapp.dao.generic.GenericDaoImpl;
import com.dosvales.vagoapp.model.EntityStatus;
import com.dosvales.vagoapp.model.Maguey;

@Stateless
public class MagueyDaoImpl extends GenericDaoImpl<Maguey, Long> implements MagueyDao, Serializable {
	
	private static final long serialVersionUID = 1L;

	@Override
	public Maguey findByNameOrAbbreviation(String name, String abbreviation) {
		String jpql = "FROM Maguey m WHERE UPPER(m.name) = :name OR UPPER(m.abbreviation) = :abbreviation";
		Maguey maguey = null;
		try {
			maguey = em.createQuery(jpql, Maguey.class)
					.setParameter("name", name.toUpperCase())
					.setParameter("abbreviation", abbreviation.toUpperCase())
					.getSingleResult();
		}catch (NoResultException ex) {}
		return maguey;
	}

	@Override
	public Maguey findWithPhotos(Long id) {
		EntityGraph<?> entityGraph = em.getEntityGraph("graph.Maguey.photos");
		Map<String, Object> properties = new HashMap<>();
		properties.put("javax.persistence.fetchgraph", entityGraph);
		return em.find(Maguey.class, id, properties);
	}

	@Override
	public Maguey findWithPlantations(Long id) {
		EntityGraph<?> entityGraph = em.getEntityGraph("graph.Maguey.plantations");
		Map<String, Object> properties = new HashMap<>();
		properties.put("javax.persistence.fetchgraph", entityGraph);
		return em.find(Maguey.class, id, properties);
	}

	@Override
	public List<Maguey> findAllByStatus(EntityStatus status) {
		String jpql = "FROM Maguey m WHERE m.status = :status";
		return em.createQuery(jpql, Maguey.class)
				.setParameter("status", status)
				.getResultList();
	}

}
