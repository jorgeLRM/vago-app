package com.dosvales.vagoapp.dao;

import java.util.List;

import javax.ejb.Local;

import com.dosvales.vagoapp.dao.generic.GenericDao;
import com.dosvales.vagoapp.model.Mixture;
import com.dosvales.vagoapp.model.Production;

@Local
public interface MixtureDao extends GenericDao<Mixture, Long>{
	
	List<Mixture> findAllByBaseProduction(Production baseProduction);
	
}
