package com.dosvales.vagoapp.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Named;
import javax.transaction.Transactional;

import com.dosvales.vagoapp.dao.ProviderDao;
import com.dosvales.vagoapp.dao.generic.GenericDao;
import com.dosvales.vagoapp.model.EntityStatus;
import com.dosvales.vagoapp.model.Provider;
import com.dosvales.vagoapp.service.ProviderService;
import com.dosvales.vagoapp.service.generic.GenericServiceImpl;

@Named
@Transactional(rollbackOn = Exception.class)
public class ProviderServiceImpl extends GenericServiceImpl<Provider, Long> implements ProviderService, Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private ProviderDao dao;
	
	@Override
	public GenericDao<Provider, Long> getDao() {
		return dao;
	}

	@Override
	public Provider findByName(String name) {
		return dao.findByName(name);
	}

	@Override
	public List<Provider> findAllActive() {
		return dao.findAllByStatus(EntityStatus.ACTIVE);
	}

	@Override
	public List<Provider> findAllInactive() {
		return dao.findAllByStatus(EntityStatus.INACTIVE);
	}

}
