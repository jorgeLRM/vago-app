package com.dosvales.vagoapp.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Named;
import javax.transaction.Transactional;

import com.dosvales.vagoapp.dao.PalenqueDao;
import com.dosvales.vagoapp.dao.generic.GenericDao;
import com.dosvales.vagoapp.model.EntityStatus;
import com.dosvales.vagoapp.model.Palenque;
import com.dosvales.vagoapp.service.PalenqueService;
import com.dosvales.vagoapp.service.generic.GenericServiceImpl;

@Named
@Transactional(rollbackOn = Exception.class)
public class PalenqueServiceImpl extends GenericServiceImpl<Palenque, Long> implements PalenqueService, Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private PalenqueDao dao;
	
	@Override
	public Palenque findByName(String name) {
		return dao.findByName(name);
	}

	@Override
	public Palenque findWithLotsAndTubs(Long id) {
		return dao.findWithLotsAndTubs(id);
	}

	@Override
	public List<Palenque> findAllActive() {
		return dao.findAllByStatus(EntityStatus.ACTIVE);
	}

	@Override
	public List<Palenque> findAllInactive() {
		return dao.findAllByStatus(EntityStatus.INACTIVE);
	}

	@Transactional
	@Override
	public Palenque blockUnblockPalenque(Long id) {
		Palenque palenque = dao.findById(id);
		EntityStatus status = palenque.getStatus() == EntityStatus.ACTIVE ? EntityStatus.INACTIVE : EntityStatus.ACTIVE;
		palenque.setStatus(status);
		return dao.update(palenque);
	}

	@Override
	public GenericDao<Palenque, Long> getDao() {
		return dao;
	}

}
