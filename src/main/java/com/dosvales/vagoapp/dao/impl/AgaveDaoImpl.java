package com.dosvales.vagoapp.dao.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityGraph;

import com.dosvales.vagoapp.dao.AgaveDao;
import com.dosvales.vagoapp.dao.generic.GenericDaoImpl;
import com.dosvales.vagoapp.model.Agave;
import com.dosvales.vagoapp.model.EntityStatus;

@Stateless
public class AgaveDaoImpl extends GenericDaoImpl<Agave, Long> implements AgaveDao, Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public Agave findByName(String name) {
		String jpql = "SELECT a FROM Agave a WHERE UPPER(a.name) = :name";
		Agave agave = null;
		try {
			agave = em.createQuery(jpql, Agave.class)
					.setParameter("name", name.toUpperCase())
					.getSingleResult(); 
		} catch (Exception ex) {}
		return agave;
	}

	@Override
	public List<Agave> findAllByStatus(EntityStatus status) {
		String jpql = "FROM Agave a WHERE a.status = :status";
		return em.createQuery(jpql, Agave.class)
				.setParameter("status", status)
				.getResultList();
	}

	@Override
	public Agave findWithMagueyes(Long id) {
		EntityGraph<?> entityGraph = em.getEntityGraph("graph.Agave.magueyes");
		Map<String, Object> properties = new HashMap<>();
		properties.put("javax.persistence.fetchgraph", entityGraph);
		return em.find(Agave.class, id, properties);
	}
}
