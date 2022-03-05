package com.dosvales.vagoapp.dao;

import javax.ejb.Local;

import com.dosvales.vagoapp.dao.generic.GenericDao;
import com.dosvales.vagoapp.model.Provider;

@Local
public interface ProviderDao extends GenericDao<Provider, Long>{
	
	Provider findByName(String name);
	
}
