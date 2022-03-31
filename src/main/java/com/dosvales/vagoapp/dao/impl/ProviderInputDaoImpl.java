package com.dosvales.vagoapp.dao.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;

import com.dosvales.vagoapp.dao.ProviderInputDao;
import com.dosvales.vagoapp.dao.generic.GenericDaoImpl;
import com.dosvales.vagoapp.model.Input;
import com.dosvales.vagoapp.model.ProviderInput;

@Stateless
public class ProviderInputDaoImpl extends GenericDaoImpl<ProviderInput, Long> implements ProviderInputDao, Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public List<ProviderInput> findAllByInput(Input input) {
		return em.createQuery("FROM ProviderInput p WHERE p.input = :input", ProviderInput.class)
				.setParameter("input", input)
				.getResultList();
	}

}
