package com.dosvales.vagoapp.dao.impl;

import java.io.Serializable;

import javax.ejb.Stateless;

import com.dosvales.vagoapp.dao.LotDetailDao;
import com.dosvales.vagoapp.dao.generic.GenericDaoImpl;
import com.dosvales.vagoapp.model.LotDetail;

@Stateless
public class LotDetailDaoImpl extends GenericDaoImpl<LotDetail, Long> implements LotDetailDao, Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public Integer countAllByYearAndProducer(Integer year, Long idProducer) {
		return em.createNativeQuery("SELECT * "
				+ "FROM lotdetail AS l "
				+ "INNER JOIN palenque AS p "
				+ "ON l.idPalenque = p.id "
				+ "WHERE EXTRACT(year FROM l.registrationDate) = :year "
				+ "AND p.idProducer = :idProducer", LotDetail.class)
				.setParameter("year", year)
				.setParameter("idProducer", idProducer)
				.getResultList()
				.size();
	}

}
