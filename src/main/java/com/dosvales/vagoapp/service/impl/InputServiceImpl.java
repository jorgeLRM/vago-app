package com.dosvales.vagoapp.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Named;
import javax.transaction.Transactional;

import com.dosvales.vagoapp.dao.InputDao;
import com.dosvales.vagoapp.dao.generic.GenericDao;
import com.dosvales.vagoapp.model.EntityStatus;
import com.dosvales.vagoapp.model.Input;
import com.dosvales.vagoapp.service.InputService;
import com.dosvales.vagoapp.service.generic.GenericServiceImpl;

@Named
@Transactional(rollbackOn = Exception.class)
public class InputServiceImpl extends GenericServiceImpl<Input, Long> implements InputService, Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private InputDao dao;
	
	@Override
	public Input findByNameOrCode(String name, String code) {
		return dao.findByNameOrCode(name, code);
	}

	@Override
	public GenericDao<Input, Long> getDao() {
		return dao;
	}

	@Override
	public Input blockUnblockInput(Long id) {
		Input input = dao.findById(id);
		EntityStatus status = input.getStatus() == EntityStatus.ACTIVE ? EntityStatus.INACTIVE : EntityStatus.ACTIVE;
		input.setStatus(status);
		return dao.update(input);
	}

	@Override
	public List<Input> findAllActive() {
		return dao.findAllByStatus(EntityStatus.ACTIVE);
	}

	@Override
	public List<Input> findAllInactive() {
		return dao.findAllByStatus(EntityStatus.INACTIVE);
	}

}
