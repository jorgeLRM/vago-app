package com.dosvales.vagoapp.dao.impl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import javax.ejb.Stateless;

import com.dosvales.vagoapp.dao.TagDao;
import com.dosvales.vagoapp.dao.generic.GenericDaoImpl;
import com.dosvales.vagoapp.model.Tag;
import com.dosvales.vagoapp.model.TypeMovement;

@Stateless
public class TagDaoImpl extends GenericDaoImpl<Tag, Long> implements TagDao, Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public List<Tag> findByTypeMovement(TypeMovement typeMovement) {
		String jpql = "FROM Tag t WHERE t.typeMovement = :typeMovement";
		return em.createQuery(jpql, Tag.class)
				.setParameter("typeMovement", typeMovement)
				.getResultList();
	}

	@Override
	public Integer existenceOfTags() {
		String jpql = "SELECT "
				+ "(SELECT SUM(t.maxNumber - t.minNumber + 1) FROM tag t WHERE t.typeMovement = 'INPUT') - "
				+ "(SELECT SUM(t.maxNumber - t.minNumber + 1) FROM tag t WHERE t.typeMovement = 'OUTPUT')";
		BigDecimal existence = (BigDecimal) em.createNativeQuery(jpql).getSingleResult();
		if (existence != null)
			return existence.intValue();
		else
			return 0;
	}

	@Override
	public Integer findLastConsecutive(TypeMovement typeMovement) {
		String jpql = "SELECT MAX(t.maxNumber) FROM Tag t WHERE t.typeMovement = ";
		jpql += (typeMovement == TypeMovement.INPUT) ? "'" + TypeMovement.INPUT + "'" : "'" + TypeMovement.OUTPUT + "'";
		BigInteger lastNumber = (BigInteger) em.createNativeQuery(jpql).getSingleResult();
		if (lastNumber != null)
			return lastNumber.intValue();
		else
			return 0;
	}
}