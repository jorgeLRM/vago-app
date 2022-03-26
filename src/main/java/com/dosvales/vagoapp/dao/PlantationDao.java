package com.dosvales.vagoapp.dao;

import java.util.List;

import javax.ejb.Local;

import com.dosvales.vagoapp.dao.generic.GenericDao;
import com.dosvales.vagoapp.model.Estate;
import com.dosvales.vagoapp.model.Plantation;

@Local
public interface PlantationDao extends GenericDao<Plantation, Long>{
	
	Plantation findWithCuttingDetail(Long id);
	
	List<Plantation> findAllReadyForCuttingByEstate(Long idEstate);
	
	Plantation findByEstateMagueyAndPlantingDate(Plantation plantation);

	List<Plantation> findByEstate(Estate estate);	// Metodo agregado
}