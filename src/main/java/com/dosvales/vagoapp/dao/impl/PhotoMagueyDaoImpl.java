package com.dosvales.vagoapp.dao.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;

import com.dosvales.vagoapp.dao.PhotoMagueyDao;
import com.dosvales.vagoapp.dao.generic.GenericDaoImpl;
import com.dosvales.vagoapp.model.Maguey;
import com.dosvales.vagoapp.model.PhotoMaguey;

@Stateless
public class PhotoMagueyDaoImpl extends GenericDaoImpl<PhotoMaguey, Long> implements PhotoMagueyDao, Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public List<PhotoMaguey> findByMaguey(Maguey maguey) {
		return em.createQuery("FROM PhotoMaguey pm WHERE pm.maguey = :maguey", 
				PhotoMaguey.class)
				.setParameter("maguey", maguey)
				.getResultList();
	}

}
