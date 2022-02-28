package com.dosvales.vagoapp.service;

import java.time.LocalDate;
import java.util.List;

import com.dosvales.vagoapp.model.Formulation;
import com.dosvales.vagoapp.model.Production;
import com.dosvales.vagoapp.model.Tub;
import com.dosvales.vagoapp.service.generic.GenericService;

public interface FormulationService extends GenericService<Formulation, Long>{
	
	List<Formulation> findAllByProduction(Production production);
	
	Formulation findByProductionTubAndGridingDate(Production production, Tub tub, LocalDate gridingDate);
	
	List<Formulation> create(List<Formulation> formulations);
	
}
