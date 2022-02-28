package com.dosvales.vagoapp.dao;

import java.util.List;

import javax.ejb.Local;

import com.dosvales.vagoapp.dao.generic.GenericDao;
import com.dosvales.vagoapp.model.Maguey;
import com.dosvales.vagoapp.model.PhotoMaguey;

@Local
public interface PhotoMagueyDao extends GenericDao<PhotoMaguey, Long>{
	
	List<PhotoMaguey> findByMaguey(Maguey maguey);
	
}
