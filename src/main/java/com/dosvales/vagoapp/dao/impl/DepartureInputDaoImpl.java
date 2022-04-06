package com.dosvales.vagoapp.dao.impl;

import java.io.Serializable;

import javax.ejb.Stateless;

import com.dosvales.vagoapp.dao.DepartureInputDao;
import com.dosvales.vagoapp.dao.generic.GenericDaoImpl;
import com.dosvales.vagoapp.model.DepartureInput;

@Stateless
public class DepartureInputDaoImpl extends GenericDaoImpl<DepartureInput, Long> implements DepartureInputDao, Serializable {

	private static final long serialVersionUID = 1L;

}