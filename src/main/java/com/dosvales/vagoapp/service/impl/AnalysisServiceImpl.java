package com.dosvales.vagoapp.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Named;
import javax.transaction.Transactional;

import com.dosvales.vagoapp.dao.AnalysisDao;
import com.dosvales.vagoapp.dao.generic.GenericDao;
import com.dosvales.vagoapp.model.Analysis;
import com.dosvales.vagoapp.model.TypeAnalysis;
import com.dosvales.vagoapp.service.AnalysisService;
import com.dosvales.vagoapp.service.generic.GenericServiceImpl;

@Named
@Transactional(rollbackOn = Exception.class)
public class AnalysisServiceImpl extends GenericServiceImpl<Analysis, Long> implements AnalysisService, Serializable {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private AnalysisDao dao;
	
	@Override
	public Analysis findByFq(String fq) {
		return dao.findByFq(fq);
	}

	@Override
	public List<Analysis> findAllPreliminaryAnalysis() {
		return dao.findAllByTypeAnalysis(TypeAnalysis.PRELIMINARY);
	}

	@Override
	public List<Analysis> findAllOfficialAnalysis() {
		return dao.findAllByTypeAnalysis(TypeAnalysis.OFFICIAL);
	}
	
	@Override
	public GenericDao<Analysis, Long> getDao() {
		return dao;
	}

}
