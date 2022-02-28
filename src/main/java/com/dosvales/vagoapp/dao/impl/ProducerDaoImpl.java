package com.dosvales.vagoapp.dao.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityGraph;
import javax.persistence.NoResultException;

import com.dosvales.vagoapp.dao.ProducerDao;
import com.dosvales.vagoapp.dao.generic.GenericDaoImpl;
import com.dosvales.vagoapp.model.EntityStatus;
import com.dosvales.vagoapp.model.Producer;
import com.dosvales.vagoapp.model.TypeProducer;

@Stateless
public class ProducerDaoImpl extends GenericDaoImpl<Producer, Long> implements ProducerDao, Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public Producer findByNameOrAbbreviation(String name, String abbreviation) {
		Producer producer = null;
		try {
			producer = em.createQuery("FROM Producer p WHERE UPPER(p.name) = :name OR UPPER(p.abbreviation) = :abbreviation", 
					Producer.class)
					.setParameter("name", name.toUpperCase())
					.setParameter("abbreviation", abbreviation.toUpperCase())
					.getSingleResult();
		} catch(NoResultException ex) {}
		return producer;
	}

	@Override
	public List<Producer> findAllByTypeProducer(TypeProducer type) {
		return em.createQuery("FROM Producer p WHERE p.typeProducer = :typeProducer "
				+ "OR p.typeProducer = com.dosvales.vagoapp.model.TypeProducer.BOTH "
				+ "AND p.status = com.dosvales.vagoapp.model.EntityStatus.ACTIVE", 
				Producer.class)
				.setParameter("typeProducer", type)
				.getResultList();
	}

	@Override
	public Producer findWithPalenquesAndEstates(Long id) {
		EntityGraph<?> entityGraph = em.getEntityGraph("graph.Producer.palenquesAndEstates");
		Map<String, Object> properties = new HashMap<>();
		properties.put("javax.persistence.fetchgraph", entityGraph);
		return em.find(Producer.class, id, properties);
	}

	@Override
	public List<Producer> findAllByStatus(EntityStatus status) {
		String jpql = "FROM Producer p WHERE p.status = :status";
		return em.createQuery(jpql, Producer.class)
				.setParameter("status", status)
				.getResultList();
	}

}
