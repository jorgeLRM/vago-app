package com.dosvales.vagoapp.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Named;
import javax.transaction.Transactional;

import com.dosvales.vagoapp.dao.ProductCategoryDao;
import com.dosvales.vagoapp.dao.generic.GenericDao;
import com.dosvales.vagoapp.model.EntityStatus;
import com.dosvales.vagoapp.model.ProductCategory;
import com.dosvales.vagoapp.service.ProductCategoryService;
import com.dosvales.vagoapp.service.generic.GenericServiceImpl;

@Named
@Transactional(rollbackOn = Exception.class)
public class ProductCategoryServiceImpl extends GenericServiceImpl<ProductCategory, Long> implements ProductCategoryService, Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private ProductCategoryDao dao;

	@Override
	public ProductCategory findByName(String name) {
		return dao.findByName(name);
	}

	@Override
	public ProductCategory blockUnblockCategory(Long id) {
		ProductCategory category = dao.findById(id);
		EntityStatus status = category.getStatus() == EntityStatus.ACTIVE ? EntityStatus.INACTIVE : EntityStatus.ACTIVE;
		category.setStatus(status);
		return dao.update(category);
	}

	@Override
	public List<ProductCategory> findAllActive() {
		return dao.findAllByStatus(EntityStatus.ACTIVE);
	}

	@Override
	public List<ProductCategory> findAllInactive() {
		return dao.findAllByStatus(EntityStatus.INACTIVE);
	}

	@Override
	public GenericDao<ProductCategory, Long> getDao() {
		return dao;
	}

	@Override
	public ProductCategory findWithProducts(Long id) {
		return dao.findWithProducts(id);
	}
}