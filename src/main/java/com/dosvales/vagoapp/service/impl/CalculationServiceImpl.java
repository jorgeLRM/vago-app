package com.dosvales.vagoapp.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Named;
import javax.transaction.Transactional;

import com.dosvales.vagoapp.dao.CalculationDao;
import com.dosvales.vagoapp.dao.generic.GenericDao;
import com.dosvales.vagoapp.model.Calculation;
import com.dosvales.vagoapp.model.Production;
import com.dosvales.vagoapp.service.CalculationService;
import com.dosvales.vagoapp.service.generic.GenericServiceImpl;

@Named
@Transactional(rollbackOn = Exception.class)
public class CalculationServiceImpl extends GenericServiceImpl<Calculation, Long> implements CalculationService, Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private CalculationDao dao;

	@Override
	public Calculation findWithRows(Long id) {
		return dao.findWithRows(id);
	}

	@Override
	public List<Calculation> findByProduction(Production production) {
		return dao.findByProduction(production);
	}

	@Override
	public GenericDao<Calculation, Long> getDao() {
		return dao;
	}
}