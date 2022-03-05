package com.dosvales.vagoapp.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Named;
import javax.transaction.Transactional;

import com.dosvales.vagoapp.dao.StandardProductionDao;
import com.dosvales.vagoapp.dao.generic.GenericDao;
import com.dosvales.vagoapp.model.Producer;
import com.dosvales.vagoapp.model.ProductionStatus;
import com.dosvales.vagoapp.model.StandardProduction;
import com.dosvales.vagoapp.service.StandardProductionService;
import com.dosvales.vagoapp.service.generic.GenericServiceImpl;

@Named
@Transactional(rollbackOn = Exception.class)
public class StandardProductionServiceImpl extends GenericServiceImpl<StandardProduction, Long> implements StandardProductionService, Serializable {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private StandardProductionDao dao;
	
	@Override
	public List<StandardProduction> findAllWithoutTail() {
		return dao.findAllWithoutTail();
	}

	@Override
	public List<StandardProduction> findAllAvailableForFormulation() {
		return dao.findAllAvailableForFormulation();
	}

	@Override
	public List<StandardProduction> findAllFails() {
		return dao.findAllFails();
	}

	@Override
	public List<StandardProduction> findAllAvailable(Producer producer) {
		return dao.findAllAvailable(producer);
	}

	@Override
	public StandardProduction findWithAssociations(Long id) {
		return dao.findWithAssociations(id);
	}

	@Override
	public GenericDao<StandardProduction, Long> getDao() {
		return dao;
	}

	@Override
	public List<StandardProduction> findAllCanceled() {
		return dao.findAllByStatus(ProductionStatus.CANCELED);
	}

	@Override
	public List<StandardProduction> findAllInProcess() {
		return dao.findAllInProcess();
	}

	@Override
	public List<StandardProduction> findAllTerminated() {
		return dao.findAllTerminated();
	}

	@Override
	public StandardProduction findByLot(String lot) {
		return dao.findByLot(lot);
	}

}
