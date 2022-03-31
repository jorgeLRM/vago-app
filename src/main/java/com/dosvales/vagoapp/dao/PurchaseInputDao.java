package com.dosvales.vagoapp.dao;

import javax.ejb.Local;

import com.dosvales.vagoapp.dao.generic.GenericDao;
import com.dosvales.vagoapp.model.PurchaseInput;

@Local
public interface PurchaseInputDao extends GenericDao<PurchaseInput, Long>{

}
