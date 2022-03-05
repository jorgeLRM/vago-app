package com.dosvales.vagoapp.dao.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityGraph;
import javax.persistence.NoResultException;

import com.dosvales.vagoapp.dao.CuttingDao;
import com.dosvales.vagoapp.dao.generic.GenericDaoImpl;
import com.dosvales.vagoapp.model.Cutting;
import com.dosvales.vagoapp.model.EntityStatus;

@Stateless
public class CuttingDaoImpl extends GenericDaoImpl<Cutting, Long> implements CuttingDao, Serializable {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public List<Cutting> findAllAvailable() {
		return em.createNativeQuery("SELECT * "
				+ "FROM cutting AS c "
				+ "INNER JOIN lotdetail AS l "
				+ "ON c.idLotDetail = l.id "
				+ "LEFT JOIN production AS p on l.id = p.idLotDetail "
				+ "WHERE p.idLotDetail IS NULL AND c.status = 'ACTIVE'", Cutting.class)
				.getResultList();
	}

	@Override
	public Cutting findWithCuttingDetail(Long id) {
		EntityGraph<?> entityGraph = em.getEntityGraph("graph.Cutting.cuttingDetails");
		Map<String, Object> properties = new HashMap<>();
		properties.put("javax.persistence.fetchgraph", entityGraph);
		return em.find(Cutting.class, id, properties);
	}

	@Override
	public Cutting findByGuideNumber(String guideNumber) {
		Cutting cutting = null;
		try {
			cutting = em.createQuery("FROM Cutting c WHERE UPPER(c.guideNumber) = :guideNumber", Cutting.class)
				.setParameter("guideNumber", guideNumber.toUpperCase())
				.getSingleResult();
		}catch(NoResultException ex) {}
		return cutting;
	}

	@Override
	public List<Cutting> findAllByStatus(EntityStatus status) {
		String jpql = "FROM Cutting c WHERE c.status = :status";
		return em.createQuery(jpql, Cutting.class)
				.setParameter("status", status)
				.getResultList();
	}

}
