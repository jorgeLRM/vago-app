package com.dosvales.vagoapp.dao.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityGraph;
import javax.persistence.NoResultException;

import com.dosvales.vagoapp.dao.AnalysisDao;
import com.dosvales.vagoapp.dao.generic.GenericDaoImpl;
import com.dosvales.vagoapp.model.Analysis;
import com.dosvales.vagoapp.model.Production;

@Stateless
public class AnalysisDaoImpl extends GenericDaoImpl<Analysis, Long> implements AnalysisDao, Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public Analysis findByFq(String fq) {
		Analysis found = null;
		try {
			found = em.createQuery("FROM Analysis a WHERE UPPER(a.fq) = :fq", 
					Analysis.class)
					.setParameter("fq", fq.toUpperCase())
					.getSingleResult();
		}catch(NoResultException ex) {}
		return found;
	}

	@Override
	public Analysis findWithParameters(Long id) {
		EntityGraph<?> entityGraph = em.getEntityGraph("graph.Analysis.parameters");
		Map<String, Object> properties = new HashMap<>();
		properties.put("javax.persistence.fetchgraph", entityGraph);
		return em.find(Analysis.class, id, properties);
	}

	@Override
	public List<Analysis> findAllByProduction(Production production) {
		return em.createQuery("FROM Analysis a WHERE a.production  = :production", 
				Analysis.class)
				.setParameter("production", production)
				.getResultList();
	}

}
