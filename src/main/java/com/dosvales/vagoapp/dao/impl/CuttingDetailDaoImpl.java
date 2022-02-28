package com.dosvales.vagoapp.dao.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;

import com.dosvales.vagoapp.dao.CuttingDetailDao;
import com.dosvales.vagoapp.dao.generic.GenericDaoImpl;
import com.dosvales.vagoapp.model.Cutting;
import com.dosvales.vagoapp.model.CuttingDetail;

@Stateless
public class CuttingDetailDaoImpl extends GenericDaoImpl<CuttingDetail, Long> implements CuttingDetailDao, Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public List<CuttingDetail> findAllByCutting(Cutting cutting) {
		return em.createQuery("FROM CuttingDetail cd WHERE cd.cutting = :cutting", 
				CuttingDetail.class)
				.setParameter("cutting", cutting)
				.getResultList();
	}

}
