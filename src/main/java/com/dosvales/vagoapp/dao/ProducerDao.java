package com.dosvales.vagoapp.dao;

import java.util.List;

import javax.ejb.Local;

import com.dosvales.vagoapp.dao.generic.GenericDao;
import com.dosvales.vagoapp.model.EntityStatus;
import com.dosvales.vagoapp.model.Producer;
import com.dosvales.vagoapp.model.TypeProducer;

@Local
public interface ProducerDao extends GenericDao<Producer, Long>{
	
	Producer findByNameOrAbbreviation(String name, String abbreviation);
	List<Producer> findAllByTypeProducer(TypeProducer type);
	Producer findWithPalenquesAndEstates(Long id);
	List<Producer> findAllByStatus(EntityStatus status);
	
}
