package com.dosvales.vagoapp.service.impl;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.inject.Named;
import javax.transaction.Transactional;

import com.dosvales.vagoapp.dao.ReformulatedProductionDao;
import com.dosvales.vagoapp.dao.generic.GenericDao;
import com.dosvales.vagoapp.model.ReformulatedProduction;
import com.dosvales.vagoapp.service.ReformulatedProductionService;
import com.dosvales.vagoapp.service.generic.GenericServiceImpl;

@Named
@Transactional(rollbackOn = Exception.class)
public class ReformulatedProductionServiceImpl extends GenericServiceImpl<ReformulatedProduction, Long> implements ReformulatedProductionService, Serializable {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private ReformulatedProductionDao dao;
	
	@Override
	public GenericDao<ReformulatedProduction, Long> getDao() {
		return dao;
	}

	@Override
	public ReformulatedProduction findByLot(String lot) {
		return dao.findByLot(lot);
	}

	@Override
	public ReformulatedProduction findWithAssociations(Long id) {
		return dao.findWithAssociations(id);
	}

}
