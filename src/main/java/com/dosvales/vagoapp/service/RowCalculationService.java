package com.dosvales.vagoapp.service;

import java.util.List;

import com.dosvales.vagoapp.model.Calculation;
import com.dosvales.vagoapp.model.RowCalculation;
import com.dosvales.vagoapp.service.generic.GenericService;

public interface RowCalculationService extends GenericService<RowCalculation, Long> {

	List<RowCalculation> findByCalculation(Calculation calculation);

}