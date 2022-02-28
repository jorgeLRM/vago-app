package com.dosvales.vagoapp.dao.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import com.dosvales.vagoapp.dao.AnalysisDao;
import com.dosvales.vagoapp.dao.generic.GenericDaoImpl;
import com.dosvales.vagoapp.model.Analysis;
import com.dosvales.vagoapp.model.TypeAnalysis;

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
	public List<Analysis> findAllByTypeAnalysis(TypeAnalysis type) {
		return em.createQuery("FROM Analysis a WHERE a.typeAnalysis  = :typeAnalysis", 
				Analysis.class)
				.setParameter("typeAnalysis", type)
				.getResultList();
	}

}
