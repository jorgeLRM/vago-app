package com.dosvales.vagoapp.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Named;
import javax.transaction.Transactional;

import com.dosvales.vagoapp.dao.RowCalculationDao;
import com.dosvales.vagoapp.dao.generic.GenericDao;
import com.dosvales.vagoapp.model.Calculation;
import com.dosvales.vagoapp.model.RowCalculation;
import com.dosvales.vagoapp.service.RowCalculationService;
import com.dosvales.vagoapp.service.generic.GenericServiceImpl;

@Named
@Transactional(rollbackOn = Exception.class)
public class RowCalculationServiceImpl extends GenericServiceImpl<RowCalculation, Long> implements RowCalculationService, Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private RowCalculationDao dao;

	@Override
	public List<RowCalculation> findByCalculation(Calculation calculation) {
		return dao.findByCalculation(calculation);
	}

	@Override
	public GenericDao<RowCalculation, Long> getDao() {
		return dao;
	}
}