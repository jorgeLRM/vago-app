package com.dosvales.vagoapp.dao;

import java.util.List;

import com.dosvales.vagoapp.dao.generic.GenericDao;
import com.dosvales.vagoapp.model.Cutting;
import com.dosvales.vagoapp.model.EntityStatus;

public interface CuttingDao extends GenericDao<Cutting, Long>{
	
	List<Cutting> findAllAvailable();
	
	Cutting findWithCuttingDetail(Long id);
	
	Cutting findByGuideNumber(String guideNumber);
	
	List<Cutting> findAllByStatus(EntityStatus status);
	
}
