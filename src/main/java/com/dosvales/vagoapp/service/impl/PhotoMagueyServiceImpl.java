package com.dosvales.vagoapp.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Named;
import javax.transaction.Transactional;

import com.dosvales.vagoapp.dao.PhotoMagueyDao;
import com.dosvales.vagoapp.dao.generic.GenericDao;
import com.dosvales.vagoapp.model.Maguey;
import com.dosvales.vagoapp.model.PhotoMaguey;
import com.dosvales.vagoapp.service.PhotoMagueyService;
import com.dosvales.vagoapp.service.generic.GenericServiceImpl;

@Named
@Transactional(rollbackOn = Exception.class)
public class PhotoMagueyServiceImpl extends GenericServiceImpl<PhotoMaguey, Long> implements PhotoMagueyService, Serializable{

	private static final long serialVersionUID = 1L;
	
	@EJB
	private PhotoMagueyDao dao;
	
	@Override
	public List<PhotoMaguey> findByMaguey(Maguey maguey) {
		return dao.findByMaguey(maguey);
	}

	@Override
	public GenericDao<PhotoMaguey, Long> getDao() {
		return dao;
	}

}
