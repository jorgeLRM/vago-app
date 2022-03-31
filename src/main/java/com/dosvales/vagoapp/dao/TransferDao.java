package com.dosvales.vagoapp.dao;

import javax.ejb.Local;

import com.dosvales.vagoapp.dao.generic.GenericDao;
import com.dosvales.vagoapp.model.Transfer;

@Local
public interface TransferDao extends GenericDao<Transfer, Long>{
	
	Transfer findByNumTransfer(String numTransfer);
	
}
