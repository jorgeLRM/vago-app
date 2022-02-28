package com.dosvales.vagoapp.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Named;
import javax.transaction.Transactional;

import com.dosvales.vagoapp.dao.CuttingDetailDao;
import com.dosvales.vagoapp.dao.generic.GenericDao;
import com.dosvales.vagoapp.model.Cutting;
import com.dosvales.vagoapp.model.CuttingDetail;
import com.dosvales.vagoapp.service.CuttingDetailService;
import com.dosvales.vagoapp.service.generic.GenericServiceImpl;

@Named
@Transactional(rollbackOn = Exception.class)
public class CuttingDetailServiceImpl extends GenericServiceImpl<CuttingDetail, Long> implements CuttingDetailService, Serializable {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private CuttingDetailDao dao;
	
	@Override
	public List<CuttingDetail> findAllByCutting(Cutting cutting) {
		return dao.findAllByCutting(cutting);
	}

	@Override
	public GenericDao<CuttingDetail, Long> getDao() {
		return dao;
	}

}
