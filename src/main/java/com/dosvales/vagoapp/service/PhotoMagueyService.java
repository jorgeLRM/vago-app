package com.dosvales.vagoapp.service;

import java.util.List;

import com.dosvales.vagoapp.model.Maguey;
import com.dosvales.vagoapp.model.PhotoMaguey;
import com.dosvales.vagoapp.service.generic.GenericService;

public interface PhotoMagueyService extends GenericService<PhotoMaguey, Long>{
	
	List<PhotoMaguey> findByMaguey(Maguey maguey);
	
}
