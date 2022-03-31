package com.dosvales.vagoapp.dao;

import java.util.List;

import javax.ejb.Local;

import com.dosvales.vagoapp.dao.generic.GenericDao;
import com.dosvales.vagoapp.model.Input;
import com.dosvales.vagoapp.model.ProviderInput;

@Local
public interface ProviderInputDao extends GenericDao<ProviderInput, Long>{
	
	List<ProviderInput> findAllByInput(Input input);
	
}
