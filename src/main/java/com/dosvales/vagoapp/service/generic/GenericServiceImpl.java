package com.dosvales.vagoapp.service.generic;

import java.io.Serializable;
import java.util.List;

import javax.transaction.Transactional;

import com.dosvales.vagoapp.dao.generic.GenericDao;

public abstract class GenericServiceImpl<T, ID extends Serializable> implements GenericService<T, ID>{
	
	@Override
	@Transactional
	public T findById(ID id) {
		return getDao().findById(id);
	}

	@Override
	@Transactional
	public List<T> findAll() {
		return getDao().findAll();
	}
	
	@Override
	@Transactional
	public T save(T t) {
		return getDao().save(t);
	}

	@Override
	@Transactional
	public void delete(T t) {
		getDao().delete(t);
	}

	@Override
	@Transactional
	public T update(T t) {
		return getDao().update(t);
	}
	
	public abstract GenericDao<T, ID> getDao();
}
