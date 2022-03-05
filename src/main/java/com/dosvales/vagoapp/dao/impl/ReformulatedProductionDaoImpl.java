package com.dosvales.vagoapp.dao.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityGraph;

import com.dosvales.vagoapp.dao.ReformulatedProductionDao;
import com.dosvales.vagoapp.dao.generic.GenericDaoImpl;
import com.dosvales.vagoapp.model.ReformulatedProduction;

@Stateless
public class ReformulatedProductionDaoImpl extends GenericDaoImpl<ReformulatedProduction, Long> implements ReformulatedProductionDao, Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public ReformulatedProduction findByLot(String lot) {
		ReformulatedProduction reformulation = null;
		try {
			reformulation = em.createQuery("From ReformulatedProduction r WHERE UPPER(r.lot) = :lot"
					, ReformulatedProduction.class)
					.setParameter("lot", lot.toUpperCase())
					.getSingleResult();
		} catch (Exception ex) {}
		return reformulation;
	}

	@Override
	public ReformulatedProduction findWithAssociations(Long id) {
		EntityGraph<?> entityGraph = em.getEntityGraph("graph.ReformulatedProduction.reformulatedDetails");
		Map<String, Object> properties = new HashMap<>();
		properties.put("javax.persistence.fetchgraph", entityGraph);
		return em.find(ReformulatedProduction.class, id, properties);
	}
	
	
}
