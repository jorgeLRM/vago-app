package com.dosvales.vagoapp.service.impl;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.inject.Named;
import javax.transaction.Transactional;

import com.dosvales.vagoapp.dao.TransferDao;
import com.dosvales.vagoapp.dao.generic.GenericDao;
import com.dosvales.vagoapp.model.Transfer;
import com.dosvales.vagoapp.service.TransferService;
import com.dosvales.vagoapp.service.generic.GenericServiceImpl;

@Named
@Transactional(rollbackOn = Exception.class)
public class TransferServiceImpl extends GenericServiceImpl<Transfer, Long> implements TransferService, Serializable {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private TransferDao dao;
	
	@Override
	public Transfer findByNumTransfer(String numTransfer) {
		return dao.findByNumTransfer(numTransfer);
	}

	@Override
	public GenericDao<Transfer, Long> getDao() {
		return dao;
	}

}
