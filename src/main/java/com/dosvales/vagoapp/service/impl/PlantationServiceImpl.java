package com.dosvales.vagoapp.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Named;
import javax.transaction.Transactional;

import com.dosvales.vagoapp.dao.PlantationDao;
import com.dosvales.vagoapp.dao.generic.GenericDao;
import com.dosvales.vagoapp.model.Estate;
import com.dosvales.vagoapp.model.Plantation;
import com.dosvales.vagoapp.service.PlantationService;
import com.dosvales.vagoapp.service.generic.GenericServiceImpl;

@Named
@Transactional(rollbackOn = Exception.class)
public class PlantationServiceImpl extends GenericServiceImpl<Plantation, Long> implements PlantationService, Serializable {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private PlantationDao dao;

	@Override
	public Plantation findWithCuttingDetail(Long id) {
		return dao.findWithCuttingDetail(id);
	}

	@Override
	public List<Plantation> findAllReadyForCuttingByEstate(Long idEstate) {
		return dao.findAllReadyForCuttingByEstate(idEstate);
	}

	@Override
	public Plantation findByEstateMagueyAndPlantingDate(Plantation plantation) {
		return dao.findByEstateMagueyAndPlantingDate(plantation);
	}

	@Override
	@Transactional
	public List<Plantation> create(List<Plantation> plantations) {
		List<Plantation> registeredPlantations = new ArrayList<Plantation>();
		plantations.forEach(pl -> {
			Plantation newPlantation = dao.save(pl);
			registeredPlantations.add(newPlantation);
		});
		return registeredPlantations;
	}

	@Override
	public GenericDao<Plantation, Long> getDao() {
		return dao;
	}

	// Metodo agregado
	@Override
	public List<Plantation> findByEstate(Estate estate) {
		return dao.findByEstate(estate);
	}
}