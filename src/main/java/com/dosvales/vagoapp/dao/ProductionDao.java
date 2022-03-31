package com.dosvales.vagoapp.dao;

import java.util.List;

import javax.ejb.Local;

import com.dosvales.vagoapp.dao.generic.GenericDao;
import com.dosvales.vagoapp.filter.ProductionFilter;
import com.dosvales.vagoapp.model.Producer;
import com.dosvales.vagoapp.model.Production;

@Local
public interface ProductionDao extends GenericDao<Production, Long>{
	
	Production findByLot(String lot);
	List<Production> findAllByFilter(ProductionFilter filter);
	List<Production> findAllAvailable(Producer producer);
	
}
