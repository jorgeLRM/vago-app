package com.dosvales.vagoapp.dao.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Stateless;

import com.dosvales.vagoapp.dao.CustomerDao;
import com.dosvales.vagoapp.dao.generic.GenericDaoImpl;
import com.dosvales.vagoapp.model.Customer;
import com.dosvales.vagoapp.model.EntityStatus;

@Stateless
public class CustomerDaoImpl extends GenericDaoImpl<Customer, Long> implements CustomerDao, Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public Customer findByName(String name) {
		Customer customer = null;
		try {
			customer = em.createQuery("FROM Customer c WHERE UPPER(c.name) = :name", 
					Customer.class)
					.setParameter("name", name.toUpperCase())
					.getSingleResult();
		} catch (Exception ex) {}
		return customer;
	}

	@Override
	public List<Customer> findAllByStatus(EntityStatus status) {
		String jpql = "FROM Customer c WHERE c.status = :status";
		return em.createQuery(jpql, Customer.class)
				.setParameter("status", status)
				.getResultList();
	}
	
	
}
