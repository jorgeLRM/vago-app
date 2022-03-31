package com.dosvales.vagoapp.dao.impl;

import java.io.Serializable;

import javax.ejb.Stateless;

import com.dosvales.vagoapp.dao.PurchaseInputDao;
import com.dosvales.vagoapp.dao.generic.GenericDaoImpl;
import com.dosvales.vagoapp.model.PurchaseInput;

@Stateless
public class PurchaseInputDaoImpl extends GenericDaoImpl<PurchaseInput, Long> implements PurchaseInputDao, Serializable {

	private static final long serialVersionUID = 1L;

}
