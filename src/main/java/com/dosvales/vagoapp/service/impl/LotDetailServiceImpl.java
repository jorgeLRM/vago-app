package com.dosvales.vagoapp.service.impl;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.inject.Named;
import javax.transaction.Transactional;

import com.dosvales.vagoapp.dao.LotDetailDao;
import com.dosvales.vagoapp.dao.generic.GenericDao;
import com.dosvales.vagoapp.model.LotDetail;
import com.dosvales.vagoapp.service.LotDetailService;
import com.dosvales.vagoapp.service.generic.GenericServiceImpl;

@Named
@Transactional(rollbackOn = Exception.class)
public class LotDetailServiceImpl extends GenericServiceImpl<LotDetail, Long> implements LotDetailService, Serializable {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private LotDetailDao dao;
	
	@Override
	public Integer countAllByYearAndProducer(Integer year, Long idProducer) {
		return dao.countAllByYearAndProducer(year, idProducer);
	}

	@Override
	public GenericDao<LotDetail, Long> getDao() {
		return dao;
	}

}
