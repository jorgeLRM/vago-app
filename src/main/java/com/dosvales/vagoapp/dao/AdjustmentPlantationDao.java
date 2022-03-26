package com.dosvales.vagoapp.dao;

import javax.ejb.Local;

import com.dosvales.vagoapp.dao.generic.GenericDao;
import com.dosvales.vagoapp.model.AdjustmentPlantation;

@Local
public interface AdjustmentPlantationDao extends GenericDao<AdjustmentPlantation, Long> {

}