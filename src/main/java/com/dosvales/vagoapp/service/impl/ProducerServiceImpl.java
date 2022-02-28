package com.dosvales.vagoapp.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Named;
import javax.transaction.Transactional;

import com.dosvales.vagoapp.dao.ProducerDao;
import com.dosvales.vagoapp.dao.generic.GenericDao;
import com.dosvales.vagoapp.model.EntityStatus;
import com.dosvales.vagoapp.model.Producer;
import com.dosvales.vagoapp.model.TypeProducer;
import com.dosvales.vagoapp.service.ProducerService;
import com.dosvales.vagoapp.service.generic.GenericServiceImpl;

@Named
@Transactional(rollbackOn = Exception.class)
public class ProducerServiceImpl extends GenericServiceImpl<Producer, Long> implements ProducerService, Serializable {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private ProducerDao dao;
	
	@Override
	public GenericDao<Producer, Long> getDao() {
		return dao;
	}

	@Override
	public Producer findByNameOrAbbreviation(String name, String abbreviation) {
		return dao.findByNameOrAbbreviation(name, abbreviation);
	}

	@Override
	public Producer findWithPalenquesAndEstates(Long id) {
		return dao.findWithPalenquesAndEstates(id);
	}

	@Override
	public List<Producer> findAllMezcalProducers() {
		return dao.findAllByTypeProducer(TypeProducer.MEZCAL_PRODUCER);
	}

	@Override
	public List<Producer> findAllAgaveProducers() {
		return dao.findAllByTypeProducer(TypeProducer.AGAVE_PRODUCER);
	}

	@Override
	public List<Producer> findAllActive() {
		return dao.findAllByStatus(EntityStatus.ACTIVE);
	}

	@Override
	public List<Producer> findAllInactive() {
		return dao.findAllByStatus(EntityStatus.INACTIVE);
	}

	@Override
	public Producer blockUnblockProducer(Long id) {
		Producer producer = dao.findById(id);
		EntityStatus status = producer.getStatus() == EntityStatus.ACTIVE ? EntityStatus.INACTIVE : EntityStatus.ACTIVE;
		producer.setStatus(status);
		return dao.update(producer);
	}

}
