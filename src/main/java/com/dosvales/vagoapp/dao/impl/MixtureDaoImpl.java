package com.dosvales.vagoapp.dao.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;

import com.dosvales.vagoapp.dao.MixtureDao;
import com.dosvales.vagoapp.dao.generic.GenericDaoImpl;
import com.dosvales.vagoapp.model.Mixture;
import com.dosvales.vagoapp.model.Production;

@Stateless
public class MixtureDaoImpl extends GenericDaoImpl<Mixture, Long> implements MixtureDao, Serializable {
	
	private static final long serialVersionUID = 1L;

	@Override
	public List<Mixture> findAllByBaseProduction(Production baseProduction) {
		return em.createQuery("FROM Mixture m WHERE m.baseProduction = :baseProduction", Mixture.class)
				.setParameter("baseProduction", baseProduction)
				.getResultList();
	}

}
