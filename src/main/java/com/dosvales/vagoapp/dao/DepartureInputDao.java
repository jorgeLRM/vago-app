package com.dosvales.vagoapp.dao;

import javax.ejb.Local;

import com.dosvales.vagoapp.dao.generic.GenericDao;
import com.dosvales.vagoapp.model.DepartureInput;

@Local
public interface DepartureInputDao extends GenericDao<DepartureInput, Long> {

}