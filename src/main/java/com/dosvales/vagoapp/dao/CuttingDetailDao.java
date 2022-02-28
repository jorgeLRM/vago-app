package com.dosvales.vagoapp.dao;

import java.util.List;

import javax.ejb.Local;

import com.dosvales.vagoapp.dao.generic.GenericDao;
import com.dosvales.vagoapp.model.Cutting;
import com.dosvales.vagoapp.model.CuttingDetail;

@Local
public interface CuttingDetailDao extends GenericDao<CuttingDetail, Long>{
	
	List<CuttingDetail> findAllByCutting(Cutting cutting);
	
}
