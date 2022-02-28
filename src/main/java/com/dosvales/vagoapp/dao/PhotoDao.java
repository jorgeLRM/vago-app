package com.dosvales.vagoapp.dao;

import javax.ejb.Local;

import com.dosvales.vagoapp.dao.generic.GenericDao;
import com.dosvales.vagoapp.model.Photo;

@Local
public interface PhotoDao extends GenericDao<Photo, Long>{

}
