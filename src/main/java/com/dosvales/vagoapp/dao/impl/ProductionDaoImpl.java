package com.dosvales.vagoapp.dao.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import com.dosvales.vagoapp.dao.ProductionDao;
import com.dosvales.vagoapp.dao.generic.GenericDaoImpl;
import com.dosvales.vagoapp.filter.ProductionFilter;
import com.dosvales.vagoapp.model.Producer;
import com.dosvales.vagoapp.model.Production;

@Stateless
public class ProductionDaoImpl extends GenericDaoImpl<Production, Long> implements ProductionDao, Serializable {
	
	private static final long serialVersionUID = 1L;

	@Override
	public Production findByLot(String lot) {
		Production production = null;
		try {
			production = em.createQuery("FROM Production p WHERE UPPER(p.lot) = :lot", 
					Production.class)
					.setParameter("lot", lot.toUpperCase())
					.getSingleResult();
		} catch (Exception ex) {}
		return production;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Production> findAllByFilter(ProductionFilter filter) {
		String jpql = "FROM Production p";
		if (filter.getYear() != null || filter.getProducer() != null) {
			jpql += " WHERE";
			if (filter.getYear() != null) {
				jpql += " YEAR(p.lotDetail.registrationDate) = :year";
			}
			if (filter.getProducer() != null) {
				if (filter.getYear() != null) {
					jpql += " AND p.lotDetail.palenque.producer = :producer";
				} else {
					jpql += " p.producer.lotDetail.palenque.producer = :producer";
				}
			}
		}
		Query query = em.createQuery(jpql, Production.class);
		if (filter.getProducer() != null) {
			query.setParameter("producer", filter.getProducer());
		}
		if (filter.getYear() != null) {
			query.setParameter("year", filter.getYear());
		}
		return query.getResultList();
	}

	@Override
	public List<Production> findAllAvailable(Producer producer) {
		return em.createQuery("FROM Production p "
				+ "WHERE (p.productionStatus = com.dosvales.vagoapp.model.ProductionStatus.INBULK "
				+ "OR p.productionStatus = com.dosvales.vagoapp.model.ProductionStatus.FORPALENQUEMIXING "
				+ "OR p.productionStatus = com.dosvales.vagoapp.model.ProductionStatus.FOROFFICEMIXING) "
				+ "AND p.stock > 0 "
				+ "AND p.lotDetail.palenque.producer = :producer", Production.class)
				.setParameter("producer", producer)
				.getResultList();
	}
}
