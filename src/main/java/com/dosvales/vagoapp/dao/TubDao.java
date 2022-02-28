package com.dosvales.vagoapp.dao;

import java.time.LocalDate;
import java.util.List;

import javax.ejb.Local;

import com.dosvales.vagoapp.dao.generic.GenericDao;
import com.dosvales.vagoapp.model.EntityStatus;
import com.dosvales.vagoapp.model.Palenque;
import com.dosvales.vagoapp.model.Tub;

@Local
public interface TubDao extends GenericDao<Tub, Long>{
	
	Tub findByNumberAndPalenque(Integer number, Palenque palenque);
	
	Tub findWithFormulations(Long id);
	
	List<Tub> findByPalenque(Palenque palenque);
	
	List<Tub> findTubsAvailableByDate(LocalDate date);
	
	List<Tub> findAllByStatus(EntityStatus status);
	
}
