package com.dosvales.vagoapp.service.generic;

import java.io.Serializable;
import java.util.List;

public interface GenericService<T, ID extends Serializable> {

	T findById(ID id);

	List<T> findAll();

	T save(T t);
	
	T update(T t);

	void delete(T t);

}
