package com.dosvales.vagoapp.dao;

import java.util.List;

import javax.ejb.Local;

import com.dosvales.vagoapp.dao.generic.GenericDao;
import com.dosvales.vagoapp.model.Production;

@Local
public interface ProductionDao extends GenericDao<Production, Long>{
	
	List<Production> findAllWithoutPreliminaryBodyAnalysis();
	List<Production> findAllWithoutPreliminaryTailAnalysis();
	List<Production> findAllWithoutOfficialAnalysis();
	List<Production> findAllWithoutTransfer();
	
}
