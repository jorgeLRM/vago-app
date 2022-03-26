package com.dosvales.vagoapp.service;

import java.util.List;

import com.dosvales.vagoapp.model.Estate;
import com.dosvales.vagoapp.model.Plantation;
import com.dosvales.vagoapp.service.generic.GenericService;

public interface PlantationService extends GenericService<Plantation, Long>{
	
	Plantation findWithCuttingDetail(Long id);
	
	List<Plantation> findAllReadyForCuttingByEstate(Long idEstate);
	
	Plantation findByEstateMagueyAndPlantingDate(Plantation plantation);
	
	List<Plantation> create(List<Plantation> plantations);

	List<Plantation> findByEstate(Estate estate); // Metodo agregado
}