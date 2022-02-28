package com.dosvales.vagoapp.dao.generic;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Implementation of <code>GenericDAO</code> using Hibernate.
 *
 * @param <T> The type of the domain object for which this instance is to be used
 */
public class GenericDaoImpl<T, ID extends Serializable> implements GenericDao<T, ID>{

	@PersistenceContext
	protected EntityManager em;
	
	@SuppressWarnings("unchecked")
    private final Class<T> persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	
	@Override
	public T findById(ID id) {
		return em.find(persistentClass, id);
	}

	@Override
	public List<T> findAll() {
		String query = String.format("SELECT e FROM %s e", persistentClass.getSimpleName());
		return em.createQuery(query, persistentClass)
				.getResultList();
	}

	@Override
	public T save(T t) {
		em.persist(t);
		em.flush();
		em.refresh(t);
		return t;
	}

	@Override
	public T update(T t) {
		return em.merge(t);
	}

	@Override
	public void delete(T t) {
		em.remove(em.merge(t));
	}

}
