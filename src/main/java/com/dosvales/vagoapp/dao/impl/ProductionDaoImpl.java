package com.dosvales.vagoapp.dao.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;

import com.dosvales.vagoapp.dao.ProductionDao;
import com.dosvales.vagoapp.dao.generic.GenericDaoImpl;
import com.dosvales.vagoapp.model.Production;
import com.dosvales.vagoapp.model.TypeAnalysis;

@Stateless
public class ProductionDaoImpl extends GenericDaoImpl<Production, Long> implements ProductionDao, Serializable {
	
	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	public List<Production> findAllWithoutPreliminaryBodyAnalysis() {
		return em.createNativeQuery("SELECT * "
				+ "FROM production AS p "
				+ "LEFT JOIN analysis AS a "
				+ "ON p.id = a.idProduction "
				+ "WHERE (a.idProduction IS NULL "
				+ "OR (a.typeAnalysis != :typeAnalysis AND p.id NOT IN (SELECT a2.idProduction "
				+ "FROM analysis AS a2 WHERE a2.typeAnalysis = :typeAnalysis))) "
				+ "AND (p.productionStatus = 'FORMULATION' "
				+ "OR p.productionStatus = 'PREPARATIONREFORMULATION')", Production.class)
				.setParameter("typeAnalysis", TypeAnalysis.PRELIMINARY_BODY.name())
				.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Production> findAllWithoutPreliminaryTailAnalysis() {
		return em.createNativeQuery("SELECT * "
				+ "FROM production AS p "
				+ "LEFT JOIN analysis AS a "
				+ "ON p.id = a.idProduction "
				+ "WHERE (a.typeAnalysis != :typeAnalysis AND p.id NOT IN (SELECT a2.idProduction "
				+ "FROM analysis AS a2 WHERE a2.typeAnalysis = :typeAnalysis))) "
				+ "AND (p.productionStatus = 'PRELIMINARYPOSITIVE' "
				+ "OR p.productionStatus = 'PRELIMINARYNEGATIVE')", Production.class)
				.setParameter("typeAnalysis", TypeAnalysis.PRELIMINARY_BODY.name())
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Production> findAllWithoutOfficialAnalysis() {
		return em.createNativeQuery("SELECT * "
				+ "FROM production AS p "
				+ "LEFT JOIN analysis AS a "
				+ "ON p.id = a.idProduction "
				+ "WHERE (a.typeAnalysis != :typeAnalysis AND p.id NOT IN (SELECT a2.idProduction "
				+ "FROM analysis AS a2 WHERE a2.typeAnalysis = :typeAnalysis))) "
				+ "AND ((p.productionStatus = 'MIXTURE' AND p.typeProduction = 'StandardProduction')"
				+ "OR ((p.productionStatus = 'PRELIMINARYPOSITIVE' OR p.productionStatus = 'PRELIMINARYNEGATIVE') "
				+ "AND p.typeProduction = 'ReformulatedProduction'))", Production.class)
				.setParameter("typeAnalysis", TypeAnalysis.OFFICIAL.name())
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Production> findAllWithoutTransfer() {
		return em.createNativeQuery("SELECT * "
				+ "FROM production AS p "
				+ "LEFT JOIN transfer AS t "
				+ "ON p.id = t.idProduction "
				+ "WHERE t.idProduction IS NULL "
				+ "AND (p.productionStatus = 'INBULK' "
				+ "OR p.productionStatus = 'FOROFFICEMIXING')", Production.class)
				.getResultList();
	}
}
