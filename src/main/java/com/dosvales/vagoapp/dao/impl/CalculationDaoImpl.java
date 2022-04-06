package com.dosvales.vagoapp.dao.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityGraph;

import com.dosvales.vagoapp.dao.CalculationDao;
import com.dosvales.vagoapp.dao.generic.GenericDaoImpl;
import com.dosvales.vagoapp.model.Calculation;
import com.dosvales.vagoapp.model.Production;

@Stateless
public class CalculationDaoImpl extends GenericDaoImpl<Calculation, Long> implements CalculationDao, Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public Calculation findWithRows(Long id) {
		EntityGraph<?> entityGraph = em.getEntityGraph("graph.Calculation.rowsCalculation");
		Map<String, Object> properties = new HashMap<>();
		properties.put("javax.persistence.fetchgraph", entityGraph);
		return em.find(Calculation.class, id, properties);
	}

	@Override
	public List<Calculation> findByProduction(Production production) {
		return em.createQuery("FROM Calculation c WHERE c.production = :production", Calculation.class)
				.setParameter("production", production)
				.getResultList();
	}
}