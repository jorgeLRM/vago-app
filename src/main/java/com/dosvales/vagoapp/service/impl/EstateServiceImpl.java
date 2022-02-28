package com.dosvales.vagoapp.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Named;
import javax.transaction.Transactional;

import com.dosvales.vagoapp.dao.EstateDao;
import com.dosvales.vagoapp.dao.generic.GenericDao;
import com.dosvales.vagoapp.model.EntityStatus;
import com.dosvales.vagoapp.model.Estate;
import com.dosvales.vagoapp.service.EstateService;
import com.dosvales.vagoapp.service.generic.GenericServiceImpl;

@Named
@Transactional(rollbackOn = Exception.class)
public class EstateServiceImpl extends GenericServiceImpl<Estate, Long> implements EstateService, Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private EstateDao dao;
	
	@Override
	public Estate findWithPlantations(Long id) {
		return dao.findWithPlantations(id);
	}

	@Override
	public List<Estate> findAllActive() {
		return dao.findAllByStatus(EntityStatus.ACTIVE);
	}

	@Override
	public List<Estate> findAllInactive() {
		return dao.findAllByStatus(EntityStatus.INACTIVE);
	}

	@Override
	public Estate findByName(String name) {
		return dao.findByName(name);
	}

	@Transactional
	@Override
	public Estate blockUnblockEstate(Long id) {
		Estate estate = dao.findById(id);
		EntityStatus status = estate.getStatus() == EntityStatus.ACTIVE ? EntityStatus.INACTIVE : EntityStatus.ACTIVE;
		estate.setStatus(status);
		return dao.update(estate);
	}

	@Override
	public GenericDao<Estate, Long> getDao() {
		return dao;
	}

}
