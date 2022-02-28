package com.dosvales.vagoapp.dao;

import java.time.LocalDate;
import java.util.List;

import javax.ejb.Local;

import com.dosvales.vagoapp.dao.generic.GenericDao;
import com.dosvales.vagoapp.model.Formulation;
import com.dosvales.vagoapp.model.Production;
import com.dosvales.vagoapp.model.Tub;

@Local
public interface FormulationDao extends GenericDao<Formulation, Long>{
	
	List<Formulation> findAllByProduction(Production production);
	
	Formulation findByProductionTubAndGridingDate(Production production, Tub tub, LocalDate gridingDate);
	
}
