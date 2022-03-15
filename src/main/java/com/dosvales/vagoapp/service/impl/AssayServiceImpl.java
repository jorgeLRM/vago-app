package com.dosvales.vagoapp.service.impl;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.inject.Named;
import javax.transaction.Transactional;

import com.dosvales.vagoapp.dao.AssayDao;
import com.dosvales.vagoapp.dao.generic.GenericDao;
import com.dosvales.vagoapp.model.Assay;
import com.dosvales.vagoapp.service.AssayService;
import com.dosvales.vagoapp.service.generic.GenericServiceImpl;

@Named
@Transactional(rollbackOn = Exception.class)
public class AssayServiceImpl extends GenericServiceImpl<Assay, Long> implements AssayService, Serializable {
	
	private static final long serialVersionUID = 1L;

	@EJB
	private AssayDao dao;
	
	@Override
	public GenericDao<Assay, Long> getDao() {
		return dao;
	}

}
