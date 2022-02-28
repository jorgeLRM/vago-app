package com.dosvales.vagoapp.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Named;
import javax.transaction.Transactional;

import com.dosvales.vagoapp.dao.AgaveDao;
import com.dosvales.vagoapp.dao.generic.GenericDao;
import com.dosvales.vagoapp.model.Agave;
import com.dosvales.vagoapp.model.EntityStatus;
import com.dosvales.vagoapp.service.AgaveService;
import com.dosvales.vagoapp.service.generic.GenericServiceImpl;

@Named
@Transactional(rollbackOn = Exception.class)
public class AgaveServiceImpl extends GenericServiceImpl<Agave, Long> implements AgaveService, Serializable {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private AgaveDao dao;

	@Transactional
	@Override
	public Agave blockUnblockAgave(Long id) {
		Agave agave = dao.findById(id);
		EntityStatus status = agave.getStatus() == EntityStatus.ACTIVE ? EntityStatus.INACTIVE : EntityStatus.ACTIVE;
		agave.setStatus(status);
		return dao.update(agave);
	}

	@Override
	public Agave findByName(String name) {
		return dao.findByName(name);
	}

	@Override
	public List<Agave> findAllActive() {
		return dao.findAllByStatus(EntityStatus.ACTIVE);
	}

	@Override
	public List<Agave> findAllInactive() {
		return dao.findAllByStatus(EntityStatus.INACTIVE);
	}

	@Override
	public GenericDao<Agave, Long> getDao() {
		return dao;
	}

	@Override
	public Agave findWithMagueyes(Long id) {
		return dao.findWithMagueyes(id);
	}
}
