package com.dosvales.vagoapp.dao;

import javax.ejb.Local;

import com.dosvales.vagoapp.dao.generic.GenericDao;
import com.dosvales.vagoapp.model.ReformulatedProduction;

@Local
public interface ReformulatedProductionDao extends GenericDao<ReformulatedProduction, Long>{

}
