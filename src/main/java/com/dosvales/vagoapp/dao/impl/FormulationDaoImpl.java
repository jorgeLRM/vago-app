package com.dosvales.vagoapp.dao.impl;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import com.dosvales.vagoapp.dao.FormulationDao;
import com.dosvales.vagoapp.dao.generic.GenericDaoImpl;
import com.dosvales.vagoapp.model.Formulation;
import com.dosvales.vagoapp.model.Production;
import com.dosvales.vagoapp.model.Tub;

@Stateless
public class FormulationDaoImpl extends GenericDaoImpl<Formulation, Long> implements FormulationDao, Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public List<Formulation> findAllByProduction(Production production) {
		return em.createQuery("FROM Formulation f WHERE f.production = :production", Formulation.class)
				.setParameter("production", production)
				.getResultList();
	}

	@Override
	public Formulation findByProductionTubAndGridingDate(Production production, Tub tub, LocalDate gridingDate) {
		Formulation found = null;
		try {
			found = em.createQuery("FROM Formulation f "
					+ "WHERE f.production = :production "
					+ "AND f.tub = :tub "
					+ "AND f.gridingDate = :gridingDate", Formulation.class)
					.setParameter("production", production)
					.setParameter("tub", tub)
					.setParameter("gridingDate", gridingDate)
					.getSingleResult();
		} catch (NoResultException ex) {}
		return found;
	}

}
