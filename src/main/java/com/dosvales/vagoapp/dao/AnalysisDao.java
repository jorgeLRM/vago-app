package com.dosvales.vagoapp.dao;

import java.util.List;

import javax.ejb.Local;

import com.dosvales.vagoapp.dao.generic.GenericDao;
import com.dosvales.vagoapp.model.Analysis;
import com.dosvales.vagoapp.model.TypeAnalysis;

@Local
public interface AnalysisDao extends GenericDao<Analysis, Long>{
	
	Analysis findByFq(String fq);
	
	List<Analysis> findAllByTypeAnalysis(TypeAnalysis type);
	
}
