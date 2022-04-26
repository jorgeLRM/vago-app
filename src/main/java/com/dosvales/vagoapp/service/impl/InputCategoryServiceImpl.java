package com.dosvales.vagoapp.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Named;
import javax.transaction.Transactional;

import com.dosvales.vagoapp.dao.InputCategoryDao;
import com.dosvales.vagoapp.dao.generic.GenericDao;
import com.dosvales.vagoapp.model.EntityStatus;
import com.dosvales.vagoapp.model.InputCategory;
import com.dosvales.vagoapp.service.InputCategoryService;
import com.dosvales.vagoapp.service.generic.GenericServiceImpl;

@Named
@Transactional(rollbackOn = Exception.class)
public class InputCategoryServiceImpl extends GenericServiceImpl<InputCategory, Long> implements InputCategoryService, Serializable {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private InputCategoryDao dao;
	
	@Override
	public InputCategory findByNameAndNomenclature(String name, String nomenclature) {
		return dao.findByNameAndNomenclature(name, nomenclature);
	}

	@Override
	public GenericDao<InputCategory, Long> getDao() {
		return dao;
	}

	@Override
	public InputCategory blockUnblockAgave(Long id) {
		InputCategory category = dao.findById(id);
		EntityStatus status = category.getStatus() == EntityStatus.ACTIVE ? EntityStatus.INACTIVE : EntityStatus.ACTIVE;
		category.setStatus(status);
		return dao.update(category);
	}

	@Override
	public List<InputCategory> findAllActive() {
		return dao.findAllByStatus(EntityStatus.ACTIVE);
	}

	@Override
	public List<InputCategory> findAllInactive() {
		return dao.findAllByStatus(EntityStatus.INACTIVE);
	}
}
