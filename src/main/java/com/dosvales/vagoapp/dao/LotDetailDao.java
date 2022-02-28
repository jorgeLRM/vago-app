package com.dosvales.vagoapp.dao;

import javax.ejb.Local;

import com.dosvales.vagoapp.dao.generic.GenericDao;
import com.dosvales.vagoapp.model.LotDetail;

@Local
public interface LotDetailDao extends GenericDao<LotDetail, Long>{
	
	Integer countAllByYearAndProducer(Integer year, Long idProducer);
	
}
