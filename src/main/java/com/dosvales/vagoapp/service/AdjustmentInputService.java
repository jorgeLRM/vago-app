package com.dosvales.vagoapp.service;

import java.util.List;

import com.dosvales.vagoapp.model.AdjustmentInput;
import com.dosvales.vagoapp.model.TypeAdjustment;
import com.dosvales.vagoapp.service.generic.GenericService;

public interface AdjustmentInputService extends GenericService<AdjustmentInput, Long> {

	List<AdjustmentInput> findAllByTypeAdjustment(TypeAdjustment typeAdjustment);

}