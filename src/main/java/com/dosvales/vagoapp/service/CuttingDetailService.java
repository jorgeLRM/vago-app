package com.dosvales.vagoapp.service;

import java.util.List;

import com.dosvales.vagoapp.model.Cutting;
import com.dosvales.vagoapp.model.CuttingDetail;
import com.dosvales.vagoapp.service.generic.GenericService;

public interface CuttingDetailService extends GenericService<CuttingDetail, Long>{
	
	List<CuttingDetail> findAllByCutting(Cutting cutting);
	
}
