package com.dosvales.vagoapp.service.impl;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.inject.Named;
import javax.transaction.Transactional;

import com.dosvales.vagoapp.dao.TailDao;
import com.dosvales.vagoapp.dao.generic.GenericDao;
import com.dosvales.vagoapp.model.Tail;
import com.dosvales.vagoapp.service.TailService;
import com.dosvales.vagoapp.service.generic.GenericServiceImpl;

@Named
@Transactional(rollbackOn = Exception.class)
public class TailServiceImpl extends GenericServiceImpl<Tail, Long> implements TailService, Serializable {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private TailDao dao;
	
	@Override
	public GenericDao<Tail, Long> getDao() {
		return dao;
	}

}
