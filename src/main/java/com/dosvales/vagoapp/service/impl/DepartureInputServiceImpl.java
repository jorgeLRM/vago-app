package com.dosvales.vagoapp.service.impl;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.inject.Named;
import javax.transaction.Transactional;

import com.dosvales.vagoapp.dao.DepartureInputDao;
import com.dosvales.vagoapp.dao.generic.GenericDao;
import com.dosvales.vagoapp.model.DepartureInput;
import com.dosvales.vagoapp.service.DepartureInputService;
import com.dosvales.vagoapp.service.generic.GenericServiceImpl;

@Named
@Transactional(rollbackOn = Exception.class)
public class DepartureInputServiceImpl extends GenericServiceImpl<DepartureInput, Long> implements DepartureInputService, Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private DepartureInputDao dao;

	@Override
	public GenericDao<DepartureInput, Long> getDao() {
		return dao;
	}
}