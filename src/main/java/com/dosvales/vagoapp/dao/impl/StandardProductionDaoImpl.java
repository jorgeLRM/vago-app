package com.dosvales.vagoapp.dao.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityGraph;
import javax.persistence.Query;

import com.dosvales.vagoapp.dao.StandardProductionDao;
import com.dosvales.vagoapp.dao.generic.GenericDaoImpl;
import com.dosvales.vagoapp.filter.ProductionFilter;
import com.dosvales.vagoapp.model.Producer;
import com.dosvales.vagoapp.model.ProductionStatus;
import com.dosvales.vagoapp.model.StandardProduction;

@Stateless
public class StandardProductionDaoImpl extends GenericDaoImpl<StandardProduction, Long> implements StandardProductionDao, Serializable {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public List<StandardProduction> findAllWithoutTail() {
		return em.createNativeQuery("SELECT * "
				+ "FROM production AS p "
				+ "LEFT JOIN tail AS t "
				+ "ON p.id = t.idStandardProduction "
				+ "WHERE t.idStandardProduction IS NULL "
				+ "AND p.typeProduction = 'StandardProduction' "
				+ "AND p.productionStatus = 'PRELIMINARYANALYSIS'", 
				StandardProduction.class)
				.getResultList();
	}

	@Override
	public List<StandardProduction> findAllAvailableForFormulation() {
		return em.createQuery("FROM StandardProduction p "
				+ "WHERE p.productionStatus = com.dosvales.vagoapp.model.ProductionStatus.PREPARATION "
				+ "OR p.productionStatus = com.dosvales.vagoapp.model.ProductionStatus.FORMULATION", 
				StandardProduction.class)
				.getResultList();
	}

	@Override
	public List<StandardProduction> findAllFails() {
		return em.createQuery("FROM StandardProduction p "
				+ "WHERE p.productionStatus = com.dosvales.vagoapp.model.ProductionStatus.REPROBATE "
				+ "ORDER BY p.id", 
				StandardProduction.class)
				.getResultList();
	}

	@Override
	public List<StandardProduction> findAllAvailable(Producer producer) {
		return em.createQuery("FROM StandardProduction p "
				+ "WHERE p.lotDetail.palenque.producer = :producer "
				+ "AND (p.productionStatus = com.dosvales.vagoapp.model.ProductionStatus.INBULK "
				+ "OR p.productionStatus = com.dosvales.vagoapp.model.ProductionStatus.FOROFFICEMIXING) "
				+ "AND p.totalVolume > 0 "
				+ "ORDER BY p.id", StandardProduction.class)
				.setParameter("producer", producer)
				.getResultList();
	}

	@Override
	public StandardProduction findWithAssociations(Long id) {
		EntityGraph<?> entityGraph = em.getEntityGraph("graph.StandardProduction.allRelationships");
		Map<String, Object> properties = new HashMap<>();
		properties.put("javax.persistence.fetchgraph", entityGraph);
		return em.find(StandardProduction.class, id, properties);
	}

	@Override
	public List<StandardProduction> findAllByStatus(ProductionStatus status) {
		String jpql = "FROM StandardProduction p WHERE p.productionStatus = :status";
		return em.createQuery(jpql, StandardProduction.class)
				.setParameter("status", status)
				.getResultList();
	}

	@Override
	public List<StandardProduction> findAllInProcess() {
		String jpql = "FROM StandardProduction p "
				+ "WHERE p.productionStatus != com.dosvales.vagoapp.model.ProductionStatus.INBULK "
				+ "AND p.productionStatus != com.dosvales.vagoapp.model.ProductionStatus.FORPALENQUEMIXING "
				+ "AND p.productionStatus != com.dosvales.vagoapp.model.ProductionStatus.FOROFFICEMIXING "
				+ "AND p.productionStatus != com.dosvales.vagoapp.model.ProductionStatus.CANCELED";
		return em.createQuery(jpql, StandardProduction.class)
				.getResultList();
	}

	@Override
	public List<StandardProduction> findAllTerminated() {
		String jpql = "FROM StandardProduction p "
				+ "WHERE p.productionStatus = com.dosvales.vagoapp.model.ProductionStatus.INBULK "
				+ "OR p.productionStatus = com.dosvales.vagoapp.model.ProductionStatus.FORPALENQUEMIXING "
				+ "OR p.productionStatus = com.dosvales.vagoapp.model.ProductionStatus.FOROFFICEMIXING";
		return em.createQuery(jpql, StandardProduction.class)
				.getResultList();
	}

	@Override
	public StandardProduction findByLot(String lot) {
		StandardProduction production = null;
		try {
			production = em.createQuery("FROM StandardProduction p WHERE UPPER(p.lot) = :lot", 
					StandardProduction.class)
					.setParameter("lot", lot.toUpperCase())
					.getSingleResult();
		} catch (Exception ex) {}
		return production;
	}
	
	//-----------------

	@SuppressWarnings("unchecked")
	@Override
	public List<StandardProduction> findAllByFilter(ProductionFilter filter) {
		String jpql = "SELECT sp FROM StandardProduction sp";
		if (filter.getYear() != null || filter.getProducer() != null) {
			jpql += " WHERE";
			if (filter.getYear() != null) {
				jpql += " YEAR(sp.lotDetail.registrationDate) = :year";
			}
			if (filter.getProducer() != null) {
				if (filter.getYear() != null) {
					jpql += " AND sp.lotDetail.palenque.producer = :producer";
				} else {
					jpql += " sp.producer.lotDetail.palenque.producer = :producer";
				}
			}
		}
		Query query = em.createQuery(jpql, StandardProduction.class);
		if (filter.getProducer() != null) {
			query.setParameter("producer", filter.getProducer());
		}
		if (filter.getYear() != null) {
			query.setParameter("year", filter.getYear());
		}
		return query.getResultList();
	}

}
