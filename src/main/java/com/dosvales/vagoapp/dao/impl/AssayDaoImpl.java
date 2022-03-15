package com.dosvales.vagoapp.dao.impl;

import java.io.Serializable;

import javax.ejb.Stateless;

import com.dosvales.vagoapp.dao.AssayDao;
import com.dosvales.vagoapp.dao.generic.GenericDaoImpl;
import com.dosvales.vagoapp.model.Assay;

@Stateless
public class AssayDaoImpl extends GenericDaoImpl<Assay, Long> implements AssayDao, Serializable {

	private static final long serialVersionUID = 1L;
	
	
}
