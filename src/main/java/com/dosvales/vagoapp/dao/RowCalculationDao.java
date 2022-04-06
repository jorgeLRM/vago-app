package com.dosvales.vagoapp.dao;

import java.util.List;

import javax.ejb.Local;

import com.dosvales.vagoapp.dao.generic.GenericDao;
import com.dosvales.vagoapp.model.Calculation;
import com.dosvales.vagoapp.model.RowCalculation;

@Local
public interface RowCalculationDao extends GenericDao<RowCalculation, Long> {

	List<RowCalculation> findByCalculation(Calculation calculation);
	
}