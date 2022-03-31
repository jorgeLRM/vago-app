package com.dosvales.vagoapp.dao.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import com.dosvales.vagoapp.dao.InputDao;
import com.dosvales.vagoapp.dao.generic.GenericDaoImpl;
import com.dosvales.vagoapp.model.EntityStatus;
import com.dosvales.vagoapp.model.Input;

@Stateless
public class InputDaoImpl extends GenericDaoImpl<Input, Long> implements InputDao, Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public Input findByNameOrCode(String name, String code) {
		Input input = null;
		try {
			input = em.createQuery("FROM Input i WHERE UPPER(i.name) = :name OR UPPER(i.code) = :code", Input.class)
					.setParameter("name", name.toUpperCase())
					.setParameter("code", code.toUpperCase())
					.getSingleResult();
		} catch (NoResultException ex) {}
		return input;
	}

	@Override
	public List<Input> findAllByStatus(EntityStatus status) {
		String jpql = "FROM Input i WHERE i.status = :status";
		return em.createQuery(jpql, Input.class)
				.setParameter("status", status)
				.getResultList();
	}

}
