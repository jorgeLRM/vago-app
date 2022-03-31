package com.dosvales.vagoapp.service.impl;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.inject.Named;
import javax.transaction.Transactional;

import com.dosvales.vagoapp.dao.PurchaseInputDao;
import com.dosvales.vagoapp.dao.generic.GenericDao;
import com.dosvales.vagoapp.model.PurchaseInput;
import com.dosvales.vagoapp.service.PurchaseInputService;
import com.dosvales.vagoapp.service.generic.GenericServiceImpl;

@Named
@Transactional(rollbackOn = Exception.class)
public class PurchaseInputServiceImpl extends GenericServiceImpl<PurchaseInput, Long> implements PurchaseInputService, Serializable {
	
	private static final long serialVersionUID = 1L;

	@EJB
	private PurchaseInputDao dao;
	
	@Override
	public GenericDao<PurchaseInput, Long> getDao() {
		return dao;
	}

}
