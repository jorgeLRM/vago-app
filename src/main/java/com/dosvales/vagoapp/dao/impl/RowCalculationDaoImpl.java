package com.dosvales.vagoapp.dao.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;

import com.dosvales.vagoapp.dao.RowCalculationDao;
import com.dosvales.vagoapp.dao.generic.GenericDaoImpl;
import com.dosvales.vagoapp.model.Calculation;
import com.dosvales.vagoapp.model.RowCalculation;

@Stateless
public class RowCalculationDaoImpl extends GenericDaoImpl<RowCalculation, Long> implements RowCalculationDao, Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public List<RowCalculation> findByCalculation(Calculation calculation) {
		return em.createQuery("FROM RowCalculation rc WHERE rc.calculation = :calculation", 
				RowCalculation.class)
				.setParameter("calculation", calculation)
				.getResultList();
	}
}