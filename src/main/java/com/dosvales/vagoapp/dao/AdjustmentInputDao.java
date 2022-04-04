package com.dosvales.vagoapp.dao;

import java.util.List;

import javax.ejb.Local;

import com.dosvales.vagoapp.dao.generic.GenericDao;
import com.dosvales.vagoapp.model.AdjustmentInput;
import com.dosvales.vagoapp.model.TypeAdjustment;

@Local
public interface AdjustmentInputDao extends GenericDao<AdjustmentInput, Long> {

	List<AdjustmentInput> findAllByTypeAdjustment(TypeAdjustment typeAdjustment);

}