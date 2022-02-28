package com.dosvales.vagoapp.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Named;
import javax.transaction.Transactional;

import com.dosvales.vagoapp.dao.ProductionDao;
import com.dosvales.vagoapp.dao.generic.GenericDao;
import com.dosvales.vagoapp.model.Production;
import com.dosvales.vagoapp.service.ProductionService;
import com.dosvales.vagoapp.service.generic.GenericServiceImpl;

@Named
@Transactional(rollbackOn = Exception.class)
public class ProductionServiceImpl extends GenericServiceImpl<Production, Long> implements ProductionService, Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private ProductionDao dao;
	
	@Override
	public List<Production> findAllWithoutPreliminaryAnalysis() {
		return dao.findAllWithoutPreliminaryAnalysis();
	}

	@Override
	public List<Production> findAllWithoutOfficialAnalysis() {
		return dao.findAllWithoutOfficialAnalysis();
	}

	@Override
	public List<Production> findAllWithoutTransfer() {
		return dao.findAllWithoutTransfer();
	}

	@Override
	public GenericDao<Production, Long> getDao() {
		return dao;
	}

}
