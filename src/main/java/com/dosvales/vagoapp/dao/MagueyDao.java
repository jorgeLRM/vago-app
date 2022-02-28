package com.dosvales.vagoapp.dao;

import java.util.List;

import javax.ejb.Local;

import com.dosvales.vagoapp.dao.generic.GenericDao;
import com.dosvales.vagoapp.model.EntityStatus;
import com.dosvales.vagoapp.model.Maguey;

@Local
public interface MagueyDao extends GenericDao<Maguey, Long>{
	
	Maguey findByNameOrAbbreviation(String name, String abbreviation);
	
	Maguey findWithPhotos(Long id);
	
	Maguey findWithPlantations(Long id);
	
	List<Maguey> findAllByStatus(EntityStatus status);
	
}
