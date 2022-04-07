package com.dosvales.vagoapp.dao;

import java.util.List;

import javax.ejb.Local;

import com.dosvales.vagoapp.dao.generic.GenericDao;
import com.dosvales.vagoapp.model.EntityStatus;
import com.dosvales.vagoapp.model.ProductCategory;

@Local
public interface ProductCategoryDao extends GenericDao<ProductCategory, Long> {

	ProductCategory findByName(String name);

	List<ProductCategory> findAllByStatus(EntityStatus status);

}