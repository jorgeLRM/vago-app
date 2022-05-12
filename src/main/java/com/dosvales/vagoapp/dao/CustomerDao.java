package com.dosvales.vagoapp.dao;

import java.util.List;

import javax.ejb.Local;

import com.dosvales.vagoapp.dao.generic.GenericDao;
import com.dosvales.vagoapp.model.Customer;
import com.dosvales.vagoapp.model.EntityStatus;

@Local
public interface CustomerDao extends GenericDao<Customer, Long>{
	
	Customer findByName(String name);

	List<Customer> findAllByStatus(EntityStatus status);

	Customer findWithProductionOrders(Long id);

}