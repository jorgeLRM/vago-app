package com.dosvales.vagoapp.dao.impl;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityGraph;
import javax.persistence.NoResultException;

import com.dosvales.vagoapp.dao.TubDao;
import com.dosvales.vagoapp.dao.generic.GenericDaoImpl;
import com.dosvales.vagoapp.model.EntityStatus;
import com.dosvales.vagoapp.model.Palenque;
import com.dosvales.vagoapp.model.Tub;

@Stateless
public class TubDaoImpl extends GenericDaoImpl<Tub, Long> implements TubDao, Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public Tub findByNumberAndPalenque(Integer number, Palenque palenque) {
		Tub tub = null;
		try {
			tub = em.createQuery("FROM Tub t WHERE t.numberTub = :numberTub AND t.palenque = :palenque", 
					Tub.class)
				.setParameter("numberTub", number)
				.setParameter("palenque", palenque)
				.getSingleResult();
		} catch (NoResultException ex) {}
		return tub;
	}

	@Override
	public Tub findWithFormulations(Long id) {
		EntityGraph<?> entityGraph = em.getEntityGraph("graph.Tub.formulations");
		Map<String, Object> properties = new HashMap<>();
		properties.put("javax.persistence.fetchgraph", entityGraph);
		return em.find(Tub.class, id, properties);
	}

	@Override
	public List<Tub> findByPalenque(Palenque palenque) {
		return em.createQuery("FROM Tub t WHERE t.palenque = :palenque ORDER BY t.id DESC", Tub.class)
				.setParameter("palenque", palenque)
				.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Tub> findTubsAvailableByDate(LocalDate date) {
		return em.createNativeQuery("SELECT * FROM tub t WHERE NOT EXISTS (SELECT NULL FROM formulation f WHERE t.id = f.idtub AND f.destilationdate > :date AND f.status = 'ACTIVE') AND t.status = 'ACTIVE' ORDER BY t.numbertub DESC", Tub.class)
				.setParameter("date", date)
				.getResultList();
	}

	@Override
	public List<Tub> findAllByStatus(EntityStatus status) {
		String jpql = "FROM Tub t WHERE t.status = :status";
		return em.createQuery(jpql, Tub.class)
				.setParameter("status", status)
				.getResultList();
	}

}
