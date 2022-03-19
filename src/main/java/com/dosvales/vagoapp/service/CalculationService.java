package com.dosvales.vagoapp.service;

import java.util.List;

import com.dosvales.vagoapp.model.Calculation;
import com.dosvales.vagoapp.model.StandardProduction;
import com.dosvales.vagoapp.service.generic.GenericService;

public interface CalculationService extends GenericService<Calculation, Long> {

	Calculation findWithRows(Long id);

	List<Calculation> findByProduction(StandardProduction production);

}