package com.dosvales.vagoapp.dao;

import java.util.List;

import javax.ejb.Local;

import com.dosvales.vagoapp.dao.generic.GenericDao;
import com.dosvales.vagoapp.model.EntityStatus;
import com.dosvales.vagoapp.model.Estate;

@Local
public interface EstateDao extends GenericDao<Estate, Long>{
	
	Estate findWithPlantations(Long id);
	
	List<Estate> findAllByStatus(EntityStatus status);
	
	Estate findByName(String name);
	
}
