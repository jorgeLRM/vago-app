package com.dosvales.vagoapp.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Named;
import javax.transaction.Transactional;

import com.dosvales.vagoapp.dao.AdjustmentInputDao;
import com.dosvales.vagoapp.dao.generic.GenericDao;
import com.dosvales.vagoapp.model.AdjustmentInput;
import com.dosvales.vagoapp.model.TypeAdjustment;
import com.dosvales.vagoapp.service.AdjustmentInputService;
import com.dosvales.vagoapp.service.generic.GenericServiceImpl;

@Named
@Transactional(rollbackOn = Exception.class)
public class AdjustmentInputServiceImpl extends GenericServiceImpl<AdjustmentInput, Long> implements AdjustmentInputService, Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private AdjustmentInputDao dao;

	@Override
	public List<AdjustmentInput> findAllByTypeAdjustment(TypeAdjustment typeAdjustment) {
		return dao.findAllByTypeAdjustment(typeAdjustment);
	}

	@Override
	public GenericDao<AdjustmentInput, Long> getDao() {
		return dao;
	}	
}