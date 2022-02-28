package com.dosvales.vagoapp.service.impl;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Named;
import javax.transaction.Transactional;

import com.dosvales.vagoapp.dao.FormulationDao;
import com.dosvales.vagoapp.dao.generic.GenericDao;
import com.dosvales.vagoapp.model.Formulation;
import com.dosvales.vagoapp.model.Production;
import com.dosvales.vagoapp.model.Tub;
import com.dosvales.vagoapp.service.FormulationService;
import com.dosvales.vagoapp.service.generic.GenericServiceImpl;

@Named
@Transactional(rollbackOn = Exception.class)
public class FormulationServiceImpl extends GenericServiceImpl<Formulation, Long> implements FormulationService, Serializable {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private FormulationDao dao;
	
	@Override
	public List<Formulation> findAllByProduction(Production production) {
		return dao.findAllByProduction(production);
	}

	@Override
	public Formulation findByProductionTubAndGridingDate(Production production, Tub tub, LocalDate gridingDate) {
		return dao.findByProductionTubAndGridingDate(production, tub, gridingDate);
	}

	@Override
	public List<Formulation> create(List<Formulation> formulations) {
		List<Formulation> registeredFormulation = new ArrayList<Formulation>();
		formulations.forEach(formulation -> {
			Formulation newFormulation = dao.save(formulation);
			registeredFormulation.add(newFormulation);
		});
		return registeredFormulation;
	}

	@Override
	public GenericDao<Formulation, Long> getDao() {
		return dao;
	}

}
