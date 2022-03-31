package com.dosvales.vagoapp.dao.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import com.dosvales.vagoapp.dao.InputCategoryDao;
import com.dosvales.vagoapp.dao.generic.GenericDaoImpl;
import com.dosvales.vagoapp.model.EntityStatus;
import com.dosvales.vagoapp.model.InputCategory;

@Stateless
public class InputCategoryDaoImpl extends GenericDaoImpl<InputCategory, Long> implements InputCategoryDao, Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public InputCategory findByName(String name) {
		InputCategory insumoCategory = null;
		try {
			insumoCategory = em.createQuery("FROM InputCategory i WHERE UPPER(i.name) = :name", 
					InputCategory.class)
					.setParameter("name", name.toUpperCase())
					.getSingleResult();
		} catch (NoResultException ex) {}
		return insumoCategory;
	}

	@Override
	public List<InputCategory> findAllByStatus(EntityStatus status) {
		String jpql = "FROM InputCategory c WHERE c.status = :status";
		return em.createQuery(jpql, InputCategory.class)
				.setParameter("status", status)
				.getResultList();
	}

}
