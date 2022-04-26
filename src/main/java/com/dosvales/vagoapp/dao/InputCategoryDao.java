package com.dosvales.vagoapp.dao;

import java.util.List;

import javax.ejb.Local;

import com.dosvales.vagoapp.dao.generic.GenericDao;
import com.dosvales.vagoapp.model.EntityStatus;
import com.dosvales.vagoapp.model.InputCategory;

@Local
public interface InputCategoryDao extends GenericDao<InputCategory, Long>{
	
	InputCategory findByNameAndNomenclature(String name, String nomenclature);
	
	List<InputCategory> findAllByStatus(EntityStatus status);
	
}
