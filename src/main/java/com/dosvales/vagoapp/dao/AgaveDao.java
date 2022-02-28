package com.dosvales.vagoapp.dao;

import java.util.List;

import javax.ejb.Local;

import com.dosvales.vagoapp.dao.generic.GenericDao;
import com.dosvales.vagoapp.model.Agave;
import com.dosvales.vagoapp.model.EntityStatus;

@Local
public interface AgaveDao extends GenericDao<Agave, Long>{
	
	Agave findByName(String name);
	
	List<Agave> findAllByStatus(EntityStatus status);
	
	Agave findWithMagueyes(Long id);
}
	