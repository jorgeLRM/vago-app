package com.dosvales.vagoapp.dao.impl;

import java.io.Serializable;

import javax.ejb.Stateless;

import com.dosvales.vagoapp.dao.PhotoDao;
import com.dosvales.vagoapp.dao.generic.GenericDaoImpl;
import com.dosvales.vagoapp.model.Photo;

@Stateless
public class PhotoDaoImpl extends GenericDaoImpl<Photo, Long> implements PhotoDao, Serializable {

	private static final long serialVersionUID = 1L;
	
	
}
