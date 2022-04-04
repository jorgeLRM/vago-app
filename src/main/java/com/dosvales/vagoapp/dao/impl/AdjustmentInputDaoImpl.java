package com.dosvales.vagoapp.dao.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;

import com.dosvales.vagoapp.dao.AdjustmentInputDao;
import com.dosvales.vagoapp.dao.generic.GenericDaoImpl;
import com.dosvales.vagoapp.model.AdjustmentInput;
import com.dosvales.vagoapp.model.TypeAdjustment;

@Stateless
public class AdjustmentInputDaoImpl extends GenericDaoImpl<AdjustmentInput, Long> implements AdjustmentInputDao, Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public List<AdjustmentInput> findAllByTypeAdjustment(TypeAdjustment typeAdjustment) {
		String jpql = "FROM AdjustmentInput a WHERE a.typeAdjustment = :typeAdjustment";
		return em.createQuery(jpql, AdjustmentInput.class)
				.setParameter("typeAdjustment", typeAdjustment)
				.getResultList();
	}

}