package com.dosvales.vagoapp.dao.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityGraph;
import javax.persistence.NoResultException;

import com.dosvales.vagoapp.dao.PlantationDao;
import com.dosvales.vagoapp.dao.generic.GenericDaoImpl;
import com.dosvales.vagoapp.model.Plantation;

@Stateless
public class PlantationDaoImpl extends GenericDaoImpl<Plantation, Long> implements PlantationDao, Serializable {
	
	private static final long serialVersionUID = 1L;

	@Override
	public Plantation findWithCuttingDetail(Long id) {
		EntityGraph<?> entityGraph = em.getEntityGraph("graph.Plantation.cuttingDetails");
		Map<String, Object> properties = new HashMap<>();
		properties.put("javax.persistence.fetchgraph", entityGraph);
		return em.find(Plantation.class, id, properties);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Plantation> findAllReadyForCuttingByEstate(Long idEstate) {
		return em.createNativeQuery("SELECT * FROM plantation p INNER JOIN maguey m "
				+ "ON m.id = p.idMaguey INNER JOIN estate e "
				+ "ON e.id = p.idEstate WHERE TIMESTAMPDIFF(YEAR, p.plantingDate, now()) >= m.ageOfMaturation "
				+ "AND p.idEstate = :idEstate AND p.stock > 0", Plantation.class)
				.setParameter("idEstate", idEstate)
				.getResultList();
	}

	@Override
	public Plantation findByEstateMagueyAndPlantingDate(Plantation plantation) {
		Plantation found = null;
		try {
			found = em.createQuery("FROM Plantation p "
					+ "WHERE p.estate = :estate "
					+ "AND p.maguey = :maguey "
					+ "AND p.plantingDate = :plantingDate", Plantation.class)
					.setParameter("estate", plantation.getEstate())
					.setParameter("maguey", plantation.getMaguey())
					.setParameter("plantingDate", plantation.getPlantingDate())
					.getSingleResult();
		} catch(NoResultException ex) {}
		return found;
	}

}
