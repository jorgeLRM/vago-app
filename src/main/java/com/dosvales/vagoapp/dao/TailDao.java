package com.dosvales.vagoapp.dao;

import javax.ejb.Local;

import com.dosvales.vagoapp.dao.generic.GenericDao;
import com.dosvales.vagoapp.model.Tail;

@Local
public interface TailDao extends GenericDao<Tail, Long>{

}
