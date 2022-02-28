package com.dosvales.vagoapp.dao.impl;

import java.io.Serializable;

import javax.ejb.Stateless;

import com.dosvales.vagoapp.dao.ReformulatedProductionDao;
import com.dosvales.vagoapp.dao.generic.GenericDaoImpl;
import com.dosvales.vagoapp.model.ReformulatedProduction;

@Stateless
public class ReformulatedProductionDaoImpl extends GenericDaoImpl<ReformulatedProduction, Long> implements ReformulatedProductionDao, Serializable {

	private static final long serialVersionUID = 1L;
	
	
}
