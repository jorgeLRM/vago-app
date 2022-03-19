package com.dosvales.vagoapp.dao;

import java.util.List;

import javax.ejb.Local;

import com.dosvales.vagoapp.dao.generic.GenericDao;
import com.dosvales.vagoapp.model.Calculation;
import com.dosvales.vagoapp.model.StandardProduction;

@Local
public interface CalculationDao extends GenericDao<Calculation, Long> {

	Calculation findWithRows(Long id);

	List<Calculation> findByProduction(StandardProduction production);

}