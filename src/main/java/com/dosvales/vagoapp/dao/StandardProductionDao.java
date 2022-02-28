package com.dosvales.vagoapp.dao;

import java.util.List;

import javax.ejb.Local;

import com.dosvales.vagoapp.dao.generic.GenericDao;
import com.dosvales.vagoapp.model.Producer;
import com.dosvales.vagoapp.model.ProductionStatus;
import com.dosvales.vagoapp.model.StandardProduction;

@Local
public interface StandardProductionDao extends GenericDao<StandardProduction, Long>{
	
	List<StandardProduction> findAllWithoutTail();
	
	List<StandardProduction> findAllAvailableForFormulation();
	
	List<StandardProduction> findAllFails();
	
	List<StandardProduction> findAllAvailable(Producer producer);
	
	StandardProduction findWithAssociations(Long id);
	
	List<StandardProduction> findAllByStatus(ProductionStatus status);
	
	List<StandardProduction> findAllInProcess();
	
	List<StandardProduction> findAllTerminated();
}
