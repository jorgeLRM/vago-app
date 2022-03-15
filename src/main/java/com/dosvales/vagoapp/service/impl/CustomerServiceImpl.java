package com.dosvales.vagoapp.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Named;
import javax.transaction.Transactional;

import com.dosvales.vagoapp.dao.CustomerDao;
import com.dosvales.vagoapp.dao.generic.GenericDao;
import com.dosvales.vagoapp.model.Customer;
import com.dosvales.vagoapp.model.EntityStatus;
import com.dosvales.vagoapp.service.CustomerService;
import com.dosvales.vagoapp.service.generic.GenericServiceImpl;

@Named
@Transactional(rollbackOn = Exception.class)
public class CustomerServiceImpl extends GenericServiceImpl<Customer, Long> implements CustomerService, Serializable {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private CustomerDao dao;
	
	@Override
	public Customer findByName(String name) {
		return dao.findByName(name);
	}

	@Override
	public List<Customer> findAllActive() {
		return dao.findAllByStatus(EntityStatus.ACTIVE);
	}

	@Override
	public List<Customer> findAllInactive() {
		return dao.findAllByStatus(EntityStatus.INACTIVE);
	}

	@Override
	public GenericDao<Customer, Long> getDao() {
		return dao;
	}

}
