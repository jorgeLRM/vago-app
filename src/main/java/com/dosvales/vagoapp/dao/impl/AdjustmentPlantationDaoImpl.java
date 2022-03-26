package com.dosvales.vagoapp.dao.impl;

import java.io.Serializable;

import javax.ejb.Stateless;

import com.dosvales.vagoapp.dao.AdjustmentPlantationDao;
import com.dosvales.vagoapp.dao.generic.GenericDaoImpl;
import com.dosvales.vagoapp.model.AdjustmentPlantation;

@Stateless
public class AdjustmentPlantationDaoImpl extends GenericDaoImpl<AdjustmentPlantation, Long> implements AdjustmentPlantationDao, Serializable {

	private static final long serialVersionUID = 1L;

}