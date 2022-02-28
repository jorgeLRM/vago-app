package com.dosvales.vagoapp.dao.impl;

import java.io.Serializable;

import javax.ejb.Stateless;

import com.dosvales.vagoapp.dao.TailDao;
import com.dosvales.vagoapp.dao.generic.GenericDaoImpl;
import com.dosvales.vagoapp.model.Tail;

@Stateless
public class TailDaoImpl extends GenericDaoImpl<Tail, Long> implements TailDao, Serializable {

	private static final long serialVersionUID = 1L;

}
