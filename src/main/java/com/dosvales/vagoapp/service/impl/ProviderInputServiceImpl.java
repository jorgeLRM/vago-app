package com.dosvales.vagoapp.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Named;
import javax.transaction.Transactional;

import com.dosvales.vagoapp.dao.ProviderInputDao;
import com.dosvales.vagoapp.dao.generic.GenericDao;
import com.dosvales.vagoapp.model.Input;
import com.dosvales.vagoapp.model.ProviderInput;
import com.dosvales.vagoapp.service.ProviderInputService;
import com.dosvales.vagoapp.service.generic.GenericServiceImpl;

@Named
@Transactional(rollbackOn = Exception.class)
public class ProviderInputServiceImpl extends GenericServiceImpl<ProviderInput, Long> implements ProviderInputService, Serializable {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private ProviderInputDao dao;
	
	@Override
	public List<ProviderInput> findAllByInput(Input input) {
		return dao.findAllByInput(input);
	}

	@Override
	public GenericDao<ProviderInput, Long> getDao() {
		return dao;
	}

}
