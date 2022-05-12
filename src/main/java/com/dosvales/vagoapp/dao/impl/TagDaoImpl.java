package com.dosvales.vagoapp.dao.impl;

import java.io.Serializable;
import java.math.BigDecimal;
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
		String jpqlInt = "SELECT SUM(t.quantity) FROM tag t WHERE t.typeMovement = 'INPUT'";
		String jpqlOut = "SELECT SUM(t.quantity) FROM tag t WHERE t.typeMovement = 'OUTPUT'";
		BigDecimal input = (BigDecimal) em.createNativeQuery(jpqlInt).getSingleResult();
		BigDecimal output = (BigDecimal) em.createNativeQuery(jpqlOut).getSingleResult();
		if (input != null && output != null)
			return input.intValue() - output.intValue();
		else if (input == null && output != null)
			return output.intValue();
		else if (input != null && output == null)
			return input.intValue();
		else
			return 0;
	}

	@Override
	public Long findLastConsecutive(TypeMovement typeMovement) {
		// TODO Auto-generated method stub
		return null;
	}
}