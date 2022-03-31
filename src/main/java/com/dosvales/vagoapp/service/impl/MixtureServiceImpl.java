package com.dosvales.vagoapp.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Named;
import javax.transaction.Transactional;

import com.dosvales.vagoapp.dao.MixtureDao;
import com.dosvales.vagoapp.dao.generic.GenericDao;
import com.dosvales.vagoapp.model.Mixture;
import com.dosvales.vagoapp.model.Production;
import com.dosvales.vagoapp.service.MixtureService;
import com.dosvales.vagoapp.service.generic.GenericServiceImpl;

@Named
@Transactional(rollbackOn = Exception.class)
public class MixtureServiceImpl extends GenericServiceImpl<Mixture, Long> implements MixtureService, Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private MixtureDao dao;
	
	@Override
	public List<Mixture> findAllByBaseProduction(Production baseProduction) {
		return dao.findAllByBaseProduction(baseProduction);
	}

	@Override
	public GenericDao<Mixture, Long> getDao() {	
		return dao;
	}

}
