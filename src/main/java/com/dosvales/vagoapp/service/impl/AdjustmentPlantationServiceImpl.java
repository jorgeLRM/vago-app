package com.dosvales.vagoapp.service.impl;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.inject.Named;
import javax.transaction.Transactional;

import com.dosvales.vagoapp.dao.AdjustmentPlantationDao;
import com.dosvales.vagoapp.dao.generic.GenericDao;
import com.dosvales.vagoapp.model.AdjustmentPlantation;
import com.dosvales.vagoapp.service.AdjustmentPlantationService;
import com.dosvales.vagoapp.service.generic.GenericServiceImpl;

@Named
@Transactional(rollbackOn = Exception.class)
public class AdjustmentPlantationServiceImpl extends GenericServiceImpl<AdjustmentPlantation, Long> implements AdjustmentPlantationService, Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private AdjustmentPlantationDao dao;

	@Override
	public GenericDao<AdjustmentPlantation, Long> getDao() {
		return dao;
	}
}