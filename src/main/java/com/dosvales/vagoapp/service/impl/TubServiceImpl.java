package com.dosvales.vagoapp.service.impl;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Named;
import javax.transaction.Transactional;

import com.dosvales.vagoapp.dao.TubDao;
import com.dosvales.vagoapp.dao.generic.GenericDao;
import com.dosvales.vagoapp.model.EntityStatus;
import com.dosvales.vagoapp.model.Palenque;
import com.dosvales.vagoapp.model.Tub;
import com.dosvales.vagoapp.service.TubService;
import com.dosvales.vagoapp.service.generic.GenericServiceImpl;

@Named
@Transactional(rollbackOn = Exception.class)
public class TubServiceImpl extends GenericServiceImpl<Tub, Long> implements TubService, Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private TubDao dao;
	
	@Override
	public Tub findByNumberAndPalenque(Integer number, Palenque palenque) {
		return dao.findByNumberAndPalenque(number, palenque);
	}

	@Override
	public Tub findWithFormulations(Long id) {
		return dao.findWithFormulations(id);
	}

	@Override
	public List<Tub> findByPalenque(Palenque palenque) {
		return dao.findByPalenque(palenque);
	}

	@Override
	public List<Tub> findTubsAvailableByDate(LocalDate date) {
		return dao.findTubsAvailableByDate(date);
	}

	@Override
	public List<Tub> findAllActive() {
		return dao.findAllByStatus(EntityStatus.ACTIVE);
	}

	@Override
	public List<Tub> findAllInactive() {
		return dao.findAllByStatus(EntityStatus.INACTIVE);
	}

	@Transactional
	@Override
	public Tub blockUnblockTub(Long id) {
		Tub tub = dao.findById(id);
		EntityStatus status = tub.getStatus() == EntityStatus.ACTIVE ? EntityStatus.INACTIVE : EntityStatus.ACTIVE;
		tub.setStatus(status);
		return dao.update(tub);
	}

	@Override
	public GenericDao<Tub, Long> getDao() {
		return dao;
	}

}
