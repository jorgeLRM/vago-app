package com.dosvales.vagoapp.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Named;
import javax.transaction.Transactional;

import com.dosvales.vagoapp.dao.MagueyDao;
import com.dosvales.vagoapp.dao.generic.GenericDao;
import com.dosvales.vagoapp.model.EntityStatus;
import com.dosvales.vagoapp.model.Maguey;
import com.dosvales.vagoapp.service.MagueyService;
import com.dosvales.vagoapp.service.generic.GenericServiceImpl;

@Named
@Transactional(rollbackOn = Exception.class)
public class MagueyServiceImpl extends GenericServiceImpl<Maguey, Long> implements MagueyService, Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private MagueyDao dao;

	@Override
	public Maguey findByNameOrAbbreviation(String name, String abbreviation) {
		return dao.findByNameOrAbbreviation(name, abbreviation);
	}

	@Override
	public Maguey findWithPhotos(Long id) {
		return dao.findWithPlantations(id);
	}

	@Override
	public List<Maguey> findAllActive() {
		return dao.findAllByStatus(EntityStatus.ACTIVE);
	}

	@Override
	public List<Maguey> findAllInactive() {
		return dao.findAllByStatus(EntityStatus.INACTIVE);
	}

	@Override
	public GenericDao<Maguey, Long> getDao() {
		return dao;
	}

	@Override
	public Maguey findWithPlantations(Long id) {
		return dao.findWithPlantations(id);
	}

	@Transactional
	@Override
	public Maguey blockUnblockMaguey(Long id) {
		Maguey maguey = dao.findById(id);
		EntityStatus status = maguey.getStatus() == EntityStatus.ACTIVE ? EntityStatus.INACTIVE : EntityStatus.ACTIVE;
		maguey.setStatus(status);
		return dao.update(maguey);
	}

}
