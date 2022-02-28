package com.dosvales.vagoapp.dao.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityGraph;

import com.dosvales.vagoapp.dao.StandardProductionDao;
import com.dosvales.vagoapp.dao.generic.GenericDaoImpl;
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
				+ "FROM Production AS p "
				+ "LEFT JOIN Tail AS t "
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
				+ "WHERE p.productionStatus = com.dosvales.vagoapp.model.ProductionStatus.REPROBATE", 
				StandardProduction.class)
				.getResultList();
	}

	@Override
	public List<StandardProduction> findAllAvailable(Producer producer) {
		return em.createQuery("FROM StandardProduction p "
				+ "WHERE p.lotDetail.producer = :producer "
				+ "AND (p.productionStatus = com.dosvales.vagoapp.model.ProductionStatus.INBULK "
				+ "OR p.productionStatus = com.dosvales.vagoapp.model.ProductionStatus.FOROFFICEMIXING) "
				+ "AND p.totalVolume > 0", StandardProduction.class)
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

}
