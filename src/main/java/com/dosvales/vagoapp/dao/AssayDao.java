package com.dosvales.vagoapp.dao;

import javax.ejb.Local;

import com.dosvales.vagoapp.dao.generic.GenericDao;
import com.dosvales.vagoapp.model.Assay;

@Local
public interface AssayDao extends GenericDao<Assay, Long>{

}
