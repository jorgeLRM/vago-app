package com.dosvales.vagoapp.dao;

import java.util.List;

import javax.ejb.Local;

import com.dosvales.vagoapp.dao.generic.GenericDao;
import com.dosvales.vagoapp.model.EntityStatus;
import com.dosvales.vagoapp.model.Provider;

@Local
public interface ProviderDao extends GenericDao<Provider, Long>{
	
	Provider findByName(String name);

	List<Provider> findAllByStatus(EntityStatus status);

	Provider findWithProviderInputs(Long id);

}