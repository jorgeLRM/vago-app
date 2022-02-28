package com.dosvales.vagoapp.dao.impl;

import java.io.Serializable;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import com.dosvales.vagoapp.dao.TransferDao;
import com.dosvales.vagoapp.dao.generic.GenericDaoImpl;
import com.dosvales.vagoapp.model.Transfer;

@Stateless
public class TransferDaoImpl extends GenericDaoImpl<Transfer, Long> implements TransferDao, Serializable {
	
	private static final long serialVersionUID = 1L;

	@Override
	public Transfer findByNumTransfer(Integer numTransfer) {
		Transfer found = null;
		try {
			found = em.createQuery("FROM Transfer t WHERE t.numTransfer = :numTransfer",Transfer.class)
					.setParameter("numTransfer", numTransfer)
					.getSingleResult();
		}catch(NoResultException ex) {}
		return found;
	}

}
