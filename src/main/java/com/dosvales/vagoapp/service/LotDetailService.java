package com.dosvales.vagoapp.service;

import com.dosvales.vagoapp.model.LotDetail;
import com.dosvales.vagoapp.service.generic.GenericService;

public interface LotDetailService extends GenericService<LotDetail, Long>{
	
	Integer countAllByYearAndProducer(Integer year, Long idProducer);
	
}
