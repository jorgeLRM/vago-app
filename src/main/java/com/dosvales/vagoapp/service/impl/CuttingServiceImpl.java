package com.dosvales.vagoapp.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Named;
import javax.transaction.Transactional;

import com.dosvales.vagoapp.dao.CuttingDao;
import com.dosvales.vagoapp.dao.generic.GenericDao;
import com.dosvales.vagoapp.model.Cutting;
import com.dosvales.vagoapp.model.EntityStatus;
import com.dosvales.vagoapp.service.CuttingService;
import com.dosvales.vagoapp.service.generic.GenericServiceImpl;

@Named
@Transactional(rollbackOn = Exception.class)
public class CuttingServiceImpl extends GenericServiceImpl<Cutting, Long> implements CuttingService, Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private CuttingDao dao;
	
	@Override
	public GenericDao<Cutting, Long> getDao() {
		return dao;
	}

	@Override
	public List<Cutting> findAllAvailable() {
		return dao.findAllAvailable();
	}

	@Override
	public Cutting findWithCuttingDetail(Long id) {
		return dao.findWithCuttingDetail(id);
	}

	@Override
	public Cutting findWithProduction(Long id) {
		return dao.findWithProduction(id);
	}

	@Override
	public Cutting findByGuideNumber(String guideNumber) {
		return dao.findByGuideNumber(guideNumber);
	}

	@Override
	public void cancel(Cutting cutting) {
		cutting.setStatus(EntityStatus.INACTIVE);
		dao.update(cutting);
	}

	@Override
	public List<Cutting> findAllActive() {
		return dao.findAllByStatus(EntityStatus.ACTIVE);
	}

	@Override
	public List<Cutting> findAllInactive() {
		return dao.findAllByStatus(EntityStatus.INACTIVE);
	}

}
