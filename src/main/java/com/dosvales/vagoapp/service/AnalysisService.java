package com.dosvales.vagoapp.service;

import java.util.List;

import com.dosvales.vagoapp.model.Analysis;
import com.dosvales.vagoapp.service.generic.GenericService;

public interface AnalysisService extends GenericService<Analysis, Long>{
	
	Analysis findByFq(String fq);
	List<Analysis> findAllPreliminaryBodyAnalysis();
	List<Analysis> findAllPreliminaryTailAnalysis();
	List<Analysis> findAllOfficialAnalysis();
	
}
