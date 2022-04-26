package com.dosvales.vagoapp.dao;

import java.util.List;

import javax.ejb.Local;

import com.dosvales.vagoapp.dao.generic.GenericDao;
import com.dosvales.vagoapp.model.EntityStatus;
import com.dosvales.vagoapp.model.Input;
import com.dosvales.vagoapp.model.InputCategory;

@Local
public interface InputDao extends GenericDao<Input, Long>{
	
	Input findByNameOrCode(String name, String code);
	
	List<Input> findAllByStatus(EntityStatus status);

	List<Input> findByCategory(InputCategory category);

	List<Input> findWhitoutBottles();
}