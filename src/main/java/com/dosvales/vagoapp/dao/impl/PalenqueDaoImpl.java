package com.dosvales.vagoapp.dao.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityGraph;
import javax.persistence.NoResultException;

import com.dosvales.vagoapp.dao.PalenqueDao;
import com.dosvales.vagoapp.dao.generic.GenericDaoImpl;
import com.dosvales.vagoapp.model.EntityStatus;
import com.dosvales.vagoapp.model.Palenque;

@Stateless
public class PalenqueDaoImpl extends GenericDaoImpl<Palenque, Long> implements PalenqueDao, Serializable{

	private static final long serialVersionUID = 1L;
	
	@Override
	public Palenque findByName(String name) {
		Palenque palenque = null;
		try {
			palenque = em.createQuery("FROM Palenque p WHERE UPPER(p.name) = :name", 
					Palenque.class)
					.setParameter("name", name.toUpperCase())
					.getSingleResult();
		} catch(NoResultException ex) {}
		return palenque;
	}

	@Override
	public Palenque findWithLotsAndTubs(Long id) {
		EntityGraph<?> entityGraph = em.getEntityGraph("graph.Palenque.LotsAndTubs");
		Map<String, Object> properties = new HashMap<>();
		properties.put("javax.persistence.fetchgraph", entityGraph);
		return em.find(Palenque.class, id, properties);
	}

	@Override
	public List<Palenque> findAllByStatus(EntityStatus status) {
		String jpql = "FROM Palenque p WHERE p.status = :status";
		return em.createQuery(jpql, Palenque.class)
				.setParameter("status", status)
				.getResultList();
	}

}
