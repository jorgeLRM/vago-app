package com.dosvales.vagoapp.dao;

import java.util.List;

import javax.ejb.Local;

import com.dosvales.vagoapp.dao.generic.GenericDao;
import com.dosvales.vagoapp.model.EntityStatus;
import com.dosvales.vagoapp.model.Palenque;

@Local
public interface PalenqueDao extends GenericDao<Palenque, Long>{
	
	Palenque findByName(String name);
	
	Palenque findWithLotsAndTubs(Long id);
	
	List<Palenque> findAllByStatus(EntityStatus status);
	
}
